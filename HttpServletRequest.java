package Servlet;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.*;

public class HttpServletRequest {
    BufferedReader reader;
    RequestContent requestContent;
    Socket socket;
    List<Cookie> cookies;
    HttpSession session = null;
    public HttpServletRequest(Socket socket, List<Cookie> cookies) throws Exception {
        this.socket = socket;
        this.cookies = cookies;
        openReader();
    }

    Cookie[] getCookies() {
        return cookies.toArray(Cookie[]::new);
    }

    HttpSession	getSession() {
        return session;
    }

    HttpSession	getSession(boolean create) {
        if (create) {
            session = new HttpSession();
        }

        return session;
    }

    public void openReader() throws Exception {
        InputStream is = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        this.requestContent = getRequestInfo(reader);
    }

    public BufferedReader getReader() {
        return reader;
    }

    public String getPath() {
        return requestContent.path;
    }

    public String getPathInfo() {
        int index = requestContent.path.indexOf("\\");
        index = requestContent.path.indexOf("\\", index + 1);
        return requestContent.path.substring(index);
    }

    public String getHeader(String s) {
        return requestContent.headers.get(s);
    }

    public String getMethod() {
        return requestContent.method;
    }
    public String getProtocol() {
        return requestContent.protocol;
    }

    public Enumeration<String> getHeaderNames() {
        return Collections.enumeration(requestContent.headers.keySet());
    }

    private static RequestContent getRequestInfo(BufferedReader reader) throws Exception {
        RequestContent requestContent = new RequestContent();
        Map<String, String> headers = new HashMap<>();

        String line = reader.readLine();
        if (line == null)
            throw new InvalidPropertiesFormatException("Line is null");

        String[] info = line.split(" ");
        requestContent.method = info[0];
        requestContent.path = info[1].replace("/", "\\");
        requestContent.protocol = info[2];
        line = reader.readLine();

        while (line != null && !line.equals("")) {
            String[] h = line.split(": ");
            headers.put(h[0], h[1]);
            line = reader.readLine();
        }
        requestContent.headers = headers;

        return requestContent;
    }
}

