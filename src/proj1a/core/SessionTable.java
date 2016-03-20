package proj1a.core;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class SessionTable extends ConcurrentHashMap<String, Session>{
	
	private Session getSession (String sessionID) {
		System.out.println("Session table sessionID :: " + sessionID);
		Session session;
		if (get(sessionID) != null) {
			session = get(sessionID);
			System.out.println("===not null");
		} else {
			System.out.println("new Session");
			session = new Session();
			put(session.getId(), session);
		}
		return session;
	}

	public SessionCookie getSessionCookie(String sessionID) {
		Session session = getSession(sessionID);
		return session.getCookie();
	}
	
	public Session createSession() {
		Session session = new Session();
		put(session.getId(), session);
		return session;
	}
	
	public SessionCookie createNewSession() {
		Session session = createSession();
		return session.getCookie();
	}
	
	public SessionCookie updateSession(String sessionID, String newState) {
		Session session = getSession(sessionID);
		System.out.println("update before " + session.getId());
		session.updateState(newState);
		session.refresh();
		
		System.out.println("update after " + session.getId());
		
		return session.getCookie();
	}
	
	public String getSessionState(String sessionID) {
		Session session = getSession(sessionID);
		return session.getState();
	}
	
	public Date getExpirationTS(String sessionID) {
		Session session = getSession(sessionID);
		return session.getExpirationTS();
	}
	
	public SessionCookie refreshSession(String sessionID) {
		Session session = getSession(sessionID);
		session.refresh();
		return session.getCookie();
	}
	
	public SessionCookie invalidateSession(String sessionID) {
		Session session = getSession(sessionID);
		session.invalidate();
		SessionCookie cookie = session.getCookie();
		remove(sessionID);
		return cookie;
	}
}


