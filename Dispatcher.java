package Servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dispatcher {
    public static void dispatch(ServletContext s, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) throws Exception {
        String path = httpServletRequest.getPath();
        ArrayList<String> pathPieces = getPathPieces(path);

        String disPath = pathPieces.get(0);
        String startPath = pathPieces.get(1);
        HttpServlet httpServlet = getHttpServlet(disPath, startPath, s);

        navigate(httpServlet, httpServletResponse, httpServletRequest);
    }

    private static void navigate(HttpServlet httpServlet, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) throws IOException {

        switch (httpServletRequest.getMethod()) {
            case "GET" -> httpServlet.doGet(httpServletRequest, httpServletResponse);
            case "DELETE" -> httpServlet.doDelete(httpServletRequest, httpServletResponse);
            case "POST" -> httpServlet.doPost(httpServletRequest, httpServletResponse);
            case "PUT" -> httpServlet.doPut(httpServletRequest, httpServletResponse);
        }
    }

    private static HttpServlet getHttpServlet(String path, String startPath, ServletContext s) throws Exception {
        RequestDispatcher requestDispatcher = s.getRequestDispatcher(path);
        String name = requestDispatcher.getNameByPath("/" + startPath);

        if (name == null) {
            return requestDispatcher.getDefaultServlet();
        }

        return requestDispatcher.getServlet(name);
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
