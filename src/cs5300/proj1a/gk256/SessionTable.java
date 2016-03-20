package cs5300.proj1a.gk256;

import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SessionTable that implements concurrentHashMap to holds sessionID and session data 
 */
public class SessionTable extends ConcurrentHashMap<String, Session>{

	/**
	 * Getting session data from SessionTable
	 * @param sessionID
	 * @return session data
	 */
	private Session getSession (String sessionID) {
		Session session;
		if (get(sessionID) != null) {
			session = get(sessionID);
		} else {
			session = new Session();
			put(session.getId(), session);
		}
		return session;
	}

	/**
	 * Getting session cookie based on the session data
	 * @param sessionID
	 * @return session cookie
	 */
	public SessionCookie getSessionCookie(String sessionID) {
		Session session = getSession(sessionID);
		return session.getCookie();
	}
	
	/**
	 * Create session and put it in SessionTable
	 * @return session data
	 */
	public Session createSession() {
		Session session = new Session();
		put(session.getId(), session);
		return session;
	}
	
	/**
	 * Create new session and put it in SessionTable
	 * @return session cookie
	 */
	public SessionCookie createNewSession() {
		Session session = createSession();
		return session.getCookie();
	}
	
	/**
	 * Update the existing session data with newState
	 * @param sessionID, newState
	 * @return updated session cookie
	 */
	public SessionCookie updateSession(String sessionID, String newState) {
		Session session = getSession(sessionID);
		session.updateState(newState);
		session.refresh();
		
		return session.getCookie();
	}
	
	/**
	 * Getting session state from existing session data
	 * @param sessionID
	 * @return session state
	 */
	public String getSessionState(String sessionID) {
		Session session = getSession(sessionID);
		return session.getState();
	}
	
	/**
	 * Getting expiration time stamp of a session
	 * @param sessionID
	 * @return expiration time stamp
	 */
	public Date getExpirationTS(String sessionID) {
		Session session = getSession(sessionID);
		return session.getExpirationTS();
	}
	
	/**
	 * Refresh a session and update the expiration time stamp and version
	 * @param sessionID
	 * @return updated cookie
	 */
	public SessionCookie refreshSession(String sessionID) {
		Session session = getSession(sessionID);
		session.refresh();
		return session.getCookie();
	}
	
	/**
	 * Invalidate session when client terminate the session
	 * @param sessionID
	 * @return updated cookie
	 */
	public SessionCookie invalidateSession(String sessionID) {
		Session session = getSession(sessionID);
		session.invalidate();
		SessionCookie cookie = session.getCookie();
		remove(sessionID);
		return cookie;
	}
	
	/**
	 * Using iterator to cleanup expired session data in sessionTable
	 */
	public void cleanInvalidSession() {
		Iterator<Entry<String, Session>> it = this.entrySet().iterator();
		while(it.hasNext()) {
			Entry<String, Session> entry = it.next();
			if(entry.getValue().getExpirationTS().before(new Date())) {
				it.remove();
			}
		}
	}
}


