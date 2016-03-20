Project 1a
Name: Giri Kuncoro
NetId: gk256


## High Level Class Description

1. **SessionHandler**  The main servlet class that extends HttpServlet. It handles HTTP requests from clients through both GET and POST request. The endpoint is set to be "/hello" in the `web.xml`.

2. **SessionTable**  Maintain the session id to session data, which is critical part in session state management. SessionTable extends concurrent hashmap which supports read with free locking and handle the conflicting writes.

3. **Session**  Maintain all relevant information/data for a session. It contains session id, session version, session state, wq identifiers, expiration timestamp (from the session cookie). This is the data that is mapped in the session table with session id.

4. **SessionCookie** Class that extends Java cookie class, contains session id, version number and wq identifiers attributes. Cookie age is set to be 5 mins as default.


## Generating Session ID and Session Table

UUID generator (java.util.UUID) is used to generate the session id uniquely. The session id is mapped to the session data which contains all relevant information, which is maintained in the concurrent hashmap. The concurrent hashmap supports full concurrency of reading and adjustable expected concurrency for updates. Even though all operations are thread safe, retrieval operations do not entail locking, and no any support to lock entire table to prevent all access.


## Cookie Format

The cookie ontains cookie name CS5300PROJ1SESSION and session id. The session id will correspond to the relevant information session object has in the session table.


## Version Number

Version number is initialized to 0 and updated on each client request in the session object (incremented by 1). The version number is displayed on the web, which is different from session id.


## Servlet Handler

This project is implemented on one servlet class that handles different operations according to the arguments client sent via HTTP GET request in JSON format.

1. **Init page load:** This is the first client request, so the cookie is not yet exist and the server creates new session. HTTP request is performed when the page is loaded, then the page displays all related response such as version number, session ID, expiration timestamp, current timestamp, and the message.

2. **Page refresh (browser refresh):** When user refreshes page via browser, session ID is already in the session table since this is not the first client request. The cookie already contains session ID that exists, so after refresh the version number is incremented and expiration time is reset in cookie and session.

3. **Replace:** When user clicks replace button, the new message is forwarded to the server and replaces the session state in the session data. Same as all behavior, the expiration timestamp is renewed and version number is incremented as well. The message is limited in the text area to 512 characters to limt session state to 512 bytes (1 character = 1 byte).

4. **Refresh:** When user clicks refresh button, expiration time is renewed and version number is incremented.

5. **Logout:** When user logs out, the session data in table is invalidated by setting max age to 0 so browser can throw away the cookie. The related session data is not yet removed from the table, since there is a daemon that cleans invalidate session data every 5 mins. After user logsout, a logout page will be automatically rendered.


## Cleaning Up Expired Sessions

In order to clean up the expired session on session table, every 5 mins cleanUpDaemon will remove the expired sessions from the session table, so that the session table will not grow without bound. This is implemented in separate thread and run in the background concurrently.


## Client Side Scripting

Written in JSP format, HTTP request is performed through AJAX call on document ready and event handler via jQUery. The page style is based on Bootstrap theme.


## How To Run Application

The web application is run on Apache Tomcat 8 Server. The `.war` file is also provided in this project, it is deployable by just dropping the `.war` file into `tomcat/webapps` folder. Navigate to below link to start running:
http://localhost:8080/proj1a
