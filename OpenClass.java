package Servlet;

import WarInfo.ServletWarInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OpenClass {
    public static void open(Map<String, ServletWarInfo> map, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) throws Exception {
        String path = httpServletRequest.getPathInfo();
        ArrayList<String> pathPieces = getPathPieces(path);
        String startPath = pathPieces.get(0);
        HttpServlet httpServlet = getHttpServlet(startPath, map);
        navigate(httpServlet, httpServletResponse, httpServletRequest);
    }

    private static void navigate(HttpServlet httpServlet, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) throws IOException {
        if (httpServlet == null) {
            return; // todo check what original does
        }

        switch (httpServletRequest.getMethod()) {
            case "GET" -> httpServlet.doGet(httpServletRequest, httpServletResponse);
            case "DELETE" -> httpServlet.doDelete(httpServletRequest, httpServletResponse);
            case "POST" -> httpServlet.doPost(httpServletRequest, httpServletResponse);
            case "PUT" -> httpServlet.doPut(httpServletRequest, httpServletResponse);
            default -> throw new RuntimeException("Invalid method");
        }
    }

    private static HttpServlet getHttpServlet(String startPath, Map<String, ServletWarInfo> map) throws InstantiationException, IllegalAccessException {
        for (Map.Entry<String, ServletWarInfo> m : map.entrySet()) {
            String url = m.getValue().url;
            if (url.contains(startPath)) {
                return m.getValue().className.newInstance();
            }
        }

        return null;
    }

    private static ArrayList<String> getPathPieces(String path) {
        Pattern p = Pattern.compile("(\\w+)");
        Matcher matcher = p.matcher(path);

        ArrayList<String> arr = new ArrayList<>();
        while (matcher.find()) {
            arr.add(matcher.group());
        }

        return arr;
    }
}
