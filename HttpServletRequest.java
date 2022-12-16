package Servlet;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class HttpServletRequest {
    BufferedReader reader;
    RequestContent requestContent;
    Socket socket;
    public HttpServletRequest(Socket socket) throws Exception {
        this.socket = socket;
        openReader();
    }

    public void openReader() throws Exception {
        InputStream is = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        this.requestContent = GetRequest.getRequestInfo(reader);
    }

    public BufferedReader getReader() {
        return reader;
    }

    public String getPathInfo() {
        return requestContent.path;
    }

    public String getHeader(String s) {
        return requestContent.headers.get(s);
    }

    public String getMethod() {
        return requestContent.method;
    }
}
