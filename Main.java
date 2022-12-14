import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        openSocket();
    }

    private static void openSocket() throws IOException {
        ExecutorService pool = Executors.newFixedThreadPool(1);
        try (ServerSocket s = new ServerSocket(80)) {
            while (true) {
                Socket socket = s.accept();
                HttpTask h = new HttpTask(socket);
                pool.submit(h);
                //h.run();
            }
        }
    }
}

class HttpTask implements Runnable {
    Socket socket;

    public HttpTask(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            GetRequest getRequest = new GetRequest(socket);

            RequestContent requestContent = getRequest.getInfo(reader);
            HttpServletResponse httpServletResponse = new HttpServletResponse(socket);
            HttpServletRequest httpServletRequest = new HttpServletRequest(reader, requestContent);

            Pattern pattern = Pattern.compile("\\\\posts(\\/[a-zA-z1-9]+)*");
            Matcher match = pattern.matcher(requestContent.path);
            if (match.matches()) {
                PostsInfo postsInfo = new PostsInfo();

                switch (requestContent.method) {
                    case "GET" -> postsInfo.doGet(httpServletRequest, httpServletResponse);
                    case "POST" -> postsInfo.doPost(httpServletRequest, httpServletResponse);
                    case "DELETE" -> postsInfo.doDelete(httpServletRequest, httpServletResponse);
                    case "PUT" -> postsInfo.doPut(httpServletRequest, httpServletResponse);
                    default -> throw new RuntimeException("Invalid method");
                }
            }

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

class GetRequest {
    Socket socket;

    GetRequest(Socket socket) {
        this.socket = socket;
    }

    public RequestContent getInfo(BufferedReader reader) throws Exception {
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

class RequestContent {
    String method;
    String path;
    String protocol;
    Map<String, String> headers = new HashMap<>();

    public RequestContent() {
    }
}