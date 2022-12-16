package Servlet;

import java.util.HashMap;
import java.util.Map;

public class RequestContent {
    String method;
    String path;
    String protocol;
    Map<String, String> headers = new HashMap<>();
}
