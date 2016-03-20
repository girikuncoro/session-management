package cs5300.proj1a.gk256;

import javax.servlet.http.Cookie;

/**
 * Session cookie implements Java Cookie class, holds required information in cookie, i.e. sessionID, version, and wqIdentifiers
 */
public class SessionCookie extends Cookie {

	private String sessionID;
	private String versionNumber;
	private String wqIdentifiers;
	
	/**
	 * Constructor from Java cookie class
	 */
	public SessionCookie(String name, String value) {
		super(name, value);
	}
	
	/**
	 * Constructor that initialize cookie name, sessionID, version, maxAge, wqIdentifiers
	 */
	public SessionCookie(String name, String sessionID, String versionNumber, Integer version, String wqIdentifiers, Integer maxAge) {
		super(name, sessionID);
		this.sessionID = sessionID;
		this.setVersion(version);
		this.versionNumber = versionNumber;
		this.wqIdentifiers = wqIdentifiers;
		this.setMaxAge(maxAge);
	}

	/**
	 * Getter for sessionID
	 */
	public String getSessionID() {
		return sessionID;
	}
	
	/**
	 * Getter for versionNumber
	 */
	public String getVersionNumber() {
		return versionNumber;
	}
	
	/**
	 * Getter for wqIdentifiers
	 */
	public String getWqIdentifiers() {
		return wqIdentifiers;
	}
}
