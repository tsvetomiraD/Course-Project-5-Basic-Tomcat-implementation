package Servlet;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;

public class GetRequest {
    public static RequestContent getRequestInfo(BufferedReader reader) throws Exception {
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
