package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class HttpServletResponse {
    Socket socket;
    public List<Cookie> cookies;
    public HttpServletResponse(Socket socket) {
        this.socket = socket;
        this.cookies = new ArrayList<>();
    }
    public PrintWriter getWriter() throws IOException {
        return new PrintWriter(socket.getOutputStream());
    }

    void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }
}
