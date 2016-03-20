package proj1a.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SessionHandler() {
        super();
    }

    public void init() throws ServletException{
    	
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionCookie cookie;
		String sessionID = getSessionID(request.getCookies());
		String args = request.getParameter("param");
		
		System.out.println("new handler  ############");
		System.out.println("session id main   " + sessionID);
		System.out.println("args :: " + args);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~");
		
		if(sessionID == null) {
			// create new cookie if no session
			cookie = sessionTable.createNewSession();
			sessionID = cookie.getSessionID();
			
			System.out.println("session ID do not exist!");
		} 
		else {
			cookie = sessionTable.getSessionCookie(sessionID);
			
			System.out.println("session ID exist!");
			System.out.println("session cookie outside before" + cookie.getValue());
			
			if(args.equals("refresh") || args.equals("init")) {
				sessionTable.refreshSession(sessionID);
			} 
			else if(args.equals("replace")) {
				String newState = request.getParameter("msg");
				cookie = sessionTable.updateSession(sessionID, newState);
				
			} 
			else if(args.equals("logout")) {
				cookie = sessionTable.invalidateSession(sessionID);
				sessionID = null;
			}
		
			
			System.out.println("session cookie after" + cookie.getValue());
		} 
		
		String message = sessionTable.getSessionState(sessionID);
		String session = cookie.getValue();
		String version = "" + cookie.getVersion();
		String wqIdentifiers = cookie.getWqIdentifiers();
		String cookieValue = session + "_" + version + "_" + wqIdentifiers;
		String currTime = new SimpleDateFormat("EEE, dd MMM YYYY HH:mm:ss 'GMT'").format(Calendar.getInstance().getTime());
		String expire = new SimpleDateFormat("EEE, dd MMM YYYY HH:mm:ss 'GMT'").format(sessionTable.getExpirationTS(sessionID));
		
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
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
	
	public void destroy() {
		
	}
	
}


