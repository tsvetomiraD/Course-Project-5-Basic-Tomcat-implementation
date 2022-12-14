import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class HttpServletResponse {
    Socket socket;
    public HttpServletResponse (Socket socket) {
        this.socket = socket;
    }
    PrintWriter getWriter() throws IOException {
        System.out.println("Get writer");
        return new PrintWriter(socket.getOutputStream());
    }
}
