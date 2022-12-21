package Servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OpenClass {
    public static void open(ServletInfo s, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) throws Exception {
        String path = httpServletRequest.getPathInfo();
        ArrayList<String> pathPieces = getPathPieces(path);
        String startPath = pathPieces.get(0);
        HttpServlet httpServlet = getHttpServlet(startPath, s);
        navigate(httpServlet, httpServletResponse, httpServletRequest);
    }

    private static void navigate(HttpServlet httpServlet, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) throws IOException {
        switch (httpServletRequest.getMethod()) {
            case "GET" -> httpServlet.doGet(httpServletRequest, httpServletResponse);
            case "DELETE" -> httpServlet.doDelete(httpServletRequest, httpServletResponse);
            case "POST" -> httpServlet.doPost(httpServletRequest, httpServletResponse);
            case "PUT" -> httpServlet.doPut(httpServletRequest, httpServletResponse);
            default -> throw new RuntimeException("Invalid method");
        }
    }

    private static HttpServlet getHttpServlet(String startPath, ServletInfo s) throws InstantiationException, IllegalAccessException {
        String name = s.getNameByPath(startPath);
        if (name == null) {
            return s.getServletContext();
        }
        return s.getServlet(name);
    }

    private static ArrayList<String> getPathPieces(String path) {
        Pattern p = Pattern.compile("(\\w+)");
        Matcher matcher = p.matcher(path);

        ArrayList<String> arr = new ArrayList<>();
        int count = 0;
        while (matcher.find()) {
            if (count == 0) {
                count++;
                continue;
            }
            arr.add(matcher.group());
            count++;
        }

        return arr;
    }
}
