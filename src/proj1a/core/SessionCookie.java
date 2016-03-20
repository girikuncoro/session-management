package proj1a.core;

import javax.servlet.http.Cookie;

public class SessionCookie extends Cookie {
	
	private static final long serialVersionUID = 1L;
	private String sessionID;
	private String versionNumber;
	private String wqIdentifiers;
	
	public SessionCookie(String name, String value) {
		super(name, value);
	}
	
	public SessionCookie(String name, String sessionID, String versionNumber, Integer version, String wqIdentifiers, Integer maxAge) {
		super(name, sessionID);
		this.sessionID = sessionID;
		this.setVersion(version);
		this.versionNumber = versionNumber;
		this.wqIdentifiers = wqIdentifiers;
		this.setMaxAge(maxAge);
	}

	public String getSessionID() {
		return sessionID;
	}
	
	public String getVersionNumber() {
		return versionNumber;
	}
	
	public String getWqIdentifiers() {
		return wqIdentifiers;
	}
}
