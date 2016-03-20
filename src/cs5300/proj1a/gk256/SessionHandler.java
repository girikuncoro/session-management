package cs5300.proj1a.gk256;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class SessionHandler
 */

public class SessionHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	SessionTable sessionTable = new SessionTable();
	private static final long CLEANUP_TIME = 1000 * 60 * 5; // cleanupDaemon starts every 5 mins
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SessionHandler() {
        super();
    }

    /**
     * Initializing servlet by cleaning up daemon
     */
    public void init() throws ServletException{
    	cleanUpDaemon();
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionCookie cookie;
		String message = "";
		String expire = "";
		String sessionID = getSessionID(request.getCookies());
		String args = request.getParameter("param");
		
		// the first client request, it has no cookie
		if(sessionID == null) {
			cookie = sessionTable.createNewSession();
			sessionID = cookie.getSessionID();
		} 
		// client already has cookie, process the refresh, replace, and logout operation
		else {
			cookie = sessionTable.getSessionCookie(sessionID);
			
			if(args.equals("refresh") || args.equals("init")) {
				sessionTable.refreshSession(sessionID);
			} 
			else if(args.equals("replace")) {
				String newState = request.getParameter("msg");
				
				// ignore state if empty
				if(newState == "") newState = sessionTable.getSessionState(sessionID);
				cookie = sessionTable.updateSession(sessionID, newState);
				
			} 
			else if(args.equals("logout")) {
				cookie = sessionTable.invalidateSession(sessionID);
				sessionID = null;
			}
		} 
		
		String session = cookie.getValue();
		String version = "" + cookie.getVersion();
		String wqIdentifiers = cookie.getWqIdentifiers();
		String cookieValue = session + "_" + version + "_" + wqIdentifiers;
		String currTime = new SimpleDateFormat("EEE, dd MMM YYYY HH:mm:ss 'GMT'").format(Calendar.getInstance().getTime());
		
		// if user is not logged out, populate the msg and expire time
		if(sessionID != null) {
			message = sessionTable.getSessionState(sessionID);
			expire = new SimpleDateFormat("EEE, dd MMM YYYY HH:mm:ss 'GMT'").format(sessionTable.getExpirationTS(sessionID));
		}
		
		// response back to client as JSON response
		response.setContentType("appilcation/json");
		response.setCharacterEncoding("utf-8");
		response.addCookie(cookie);
		PrintWriter out = response.getWriter();
		
		JSONObject json = new JSONObject();
		json.put("message", message);
		json.put("session", session);
		json.put("version", version);
		json.put("cookie", cookieValue);
		json.put("curr", currTime);
		json.put("expire", expire);
		
		out.print(json.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// sampe handling with get request
		doGet(request, response);
	}
	
	/**
	 * Getting sessionID from client's cookie, null if not exist
	 * @param list of cookies from client
	 * @return sessionID
	 */
	private String getSessionID(Cookie[] cookies) {
		String sessionID = null;
		if (cookies != null) {
			for (int i=0; i<cookies.length; i++) {
				if(cookies[i].getName().equals("CS5300PROJ1SESSION")) {
					sessionID = cookies[i].getValue();
					break;
				}
			}
		}
		System.out.println("SessionID " + sessionID);
		return sessionID;
	}
	
	/**
	 * Starting cleanUpDaemon as background process to cleanup invalidate session in sessionTable
	 */
	public void cleanUpDaemon() {
		System.out.println("daemon starts");
		Thread cleanUpThreadPool = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					sessionTable.cleanInvalidSession();
					System.out.println("daemon running");
					try {
						System.out.println("daemon sleeping");
						Thread.sleep(CLEANUP_TIME);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		});
		cleanUpThreadPool.setDaemon(true);
		cleanUpThreadPool.start();
	}
	
	/**
	 * Destroy the servlet lifecycle, ignore for this assignment
	 */
	public void destroy() { }
}


