package cs5300.proj1a.gk256;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Session that holds all required information hold in the server, e.g. sessionID, version, sessionState, etc.
 */
public class Session {
	private String id;
	private String version;
	private String state;
	private String wqIdentifiers;
	private SessionCookie cookie;
	private Date currTime;
	private Date expirationTS;
	private final int COOKIE_AGE = 300;
	private final String COOKIE_NAME = "CS5300PROJ1SESSION";

	/**
	 * Session constructor that initialize with new UUID, default sessionState, default version, and expiration time stamp
	 */
	public Session() {
		id = UUID.randomUUID().toString();
		state = "Hello, User!";
		version = "0";
		wqIdentifiers = "0_0";
		createCookie(id, version, wqIdentifiers);
		
		Calendar calendar = Calendar.getInstance();
		currTime = calendar.getTime();
		calendar.add(Calendar.SECOND, COOKIE_AGE);
		expirationTS = calendar.getTime();
		
	}
	
	/**
	 * Getter for cookie
	 * @return session cookie
	 */
	public SessionCookie getCookie() {
		return cookie;
	}
	
	/**
	 * Creating new cookie with name of "CS5300PROJ1SESSION"
	 * @return session cookie
	 */
	public void createCookie(String id, String version, String wqIdentifiers) {
		cookie = new SessionCookie(COOKIE_NAME, id, version, 0, wqIdentifiers, COOKIE_AGE); 
	}
	
	/**
	 * Incrementing session version everytime operation is handled
	 * @return updated session version
	 */
	private String getNewVersion() {
		Integer _version = Integer.parseInt(version);
		_version++;
		String newVersion = _version.toString();
		return newVersion;
	}
	
	/**
	 * Refresh the session by updating the version number and expired time
	 */
	public void refresh() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, COOKIE_AGE);
		expirationTS = calendar.getTime();
		version = getNewVersion();
		cookie.setVersion(cookie.getVersion() + 1);
		cookie.setMaxAge(COOKIE_AGE);
	}

	/**
	 * Getter for sessionID
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter for sessionID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Getter for session version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Setter for session version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Getter for sessionState
	 */
	public String getState() {
		return state;
	}
	
	/**
	 * Setter for sessionState
	 * @param new session state
	 */
	public void updateState(String newState) {
		state = newState;
	}

	/**
	 * Getter for wq indetifiers (location data)
	 */
	public String getWqIdentifiers() {
		return wqIdentifiers;
	}

	/**
	 * Setter for wq indetifiers (location data)
	 */
	public void setWqIdentifiers(String wqIdentifiers) {
		this.wqIdentifiers = wqIdentifiers;
	}

	/**
	 * Getter for current time
	 */
	public Date getCurrTime() {
		return currTime;
	}
	
	/**
	 * Getter for expiration time stamp
	 */
	public Date getExpirationTS() {
		return expirationTS;
	}
	
	/**
	 * Setter for expiration time stamp
	 */
	public void setExpirationTS(Date expirationTS) {
		this.expirationTS = expirationTS;
	}

	/**
	 * Setter for session cookie
	 */
	public void setCookie(SessionCookie cookie) {
		this.cookie = cookie;
	}
	
	/**
	 * Invalidate the session cookie, set the age to 0
	 */
	public void invalidate() {
		cookie.setMaxAge(0);
	}
}
