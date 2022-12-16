import Servlet.HttpServletRequest;
import Servlet.HttpServletResponse;
import Servlet.OpenClass;
import WarInfo.ServletWarInfo;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class HttpTask implements Runnable {
    Socket socket;
    Map<String, ServletWarInfo> map;

    public HttpTask(Socket socket, Map<String, ServletWarInfo> map) {
        this.socket = socket;
        this.map = map;
    }

    @Override
    public void run() {
        try {
            HttpServletResponse httpServletResponse = new HttpServletResponse(socket);
            HttpServletRequest httpServletRequest = new HttpServletRequest(socket);
            OpenClass.open(map, httpServletResponse, httpServletRequest);
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
