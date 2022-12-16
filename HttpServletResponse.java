package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class HttpServletResponse {
    Socket socket;
    public HttpServletResponse(Socket socket) {
        this.socket = socket;
    }
    public PrintWriter getWriter() throws IOException {
        return new PrintWriter(socket.getOutputStream());
    }
}
