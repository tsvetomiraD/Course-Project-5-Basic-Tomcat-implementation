import java.io.*;

public class HttpServletRequest {
    BufferedReader reader;
    RequestContent requestContent;
    public HttpServletRequest (BufferedReader reader, RequestContent requestContent) {
        this.reader = reader;
        this.requestContent = requestContent;
    }
    BufferedReader getReader() {
        return reader;
    }

    String getPathInfo() {
        return requestContent.path;
    }

    String getHeader(String var1) {
        return requestContent.headers.get(var1);
    }
}
