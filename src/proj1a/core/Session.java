package proj1a.core;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Session {
	private String id;
	private String version;
	private String state;
	private String wqIdentifiers;
	private SessionCookie cookie;
	private Date currTime;
	private Date expirationTS;
	private final int DEFAULT_AGE = 60;

	public Session() {
		id = UUID.randomUUID().toString();
		state = "Hello, User!";
		version = "1";
		wqIdentifiers = "0_0";
		createCookie(id, version, wqIdentifiers);
		
		Calendar calendar = Calendar.getInstance();
		currTime = calendar.getTime();
		calendar.add(Calendar.SECOND, DEFAULT_AGE);
		expirationTS = calendar.getTime();
		
	}
	
	public SessionCookie getCookie() {
		return cookie;
	}
	
	public void createCookie(String id, String version, String wqIdentifiers) {
		cookie = new SessionCookie("CS5300PROJ1SESSION", id, version, 1, wqIdentifiers, DEFAULT_AGE); 
	}
	
	private String getNewVersion() {
		Integer _version = Integer.parseInt(version);
		_version++;
		String newVersion = _version.toString();
		return newVersion;
	}
	
	public void refresh() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, DEFAULT_AGE);
		expirationTS = calendar.getTime();
		version = getNewVersion();
		cookie.setVersion(cookie.getVersion() + 1);
		cookie.setMaxAge(DEFAULT_AGE);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getState() {
		return state;
	}
	
	public void updateState(String newState) {
		state = newState;
	}

	public String getWqIdentifiers() {
		return wqIdentifiers;
	}

	public void setWqIdentifiers(String wqIdentifiers) {
		this.wqIdentifiers = wqIdentifiers;
	}

	public Date getCurrTime() {
		return currTime;
	}
	
	public Date getExpirationTS() {
		return expirationTS;
	}
	
	public void setExpirationTS(Date expirationTS) {
		this.expirationTS = expirationTS;
	}

	public void setCookie(SessionCookie cookie) {
		this.cookie = cookie;
	}
	
	public void invalidate() {
		cookie.setMaxAge(0);
	}
}
