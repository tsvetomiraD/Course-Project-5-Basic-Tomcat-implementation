import Servlet.HttpServletRequest;
import Servlet.HttpServletResponse;
import Servlet.OpenClass;
import Servlet.ServletContext;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class HttpTask implements Runnable {
    Socket socket;
    ServletContext s;

    public HttpTask(Socket socket, ServletContext s) {
        this.socket = socket;
        this.s = s;
    }

    @Override
    public void run() {
        try {
            HttpServletResponse httpServletResponse = new HttpServletResponse(socket);
            HttpServletRequest httpServletRequest = new HttpServletRequest(socket);
            OpenClass.open(s, httpServletResponse, httpServletRequest);
        } catch (Exception e) {
            //System.out.println(e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
