package Servlet;

import java.util.HashMap;
import java.util.Map;

public class ServletInfo {
    Map<String, Class<? extends HttpServlet>> servletInfo = new HashMap<>();
    Map<String, String> pathInfo;

    Map<String, HttpServlet> instClasses;
    public String docBase;
    public String path;

    public void addServlet(String name, Class<? extends HttpServlet> sClass) {
        if (servletInfo.containsKey(name)) {
            return;
        }
        servletInfo.put(name, sClass);
    }

    public void addServletPath(String name, String path) {
        if (servletInfo.containsKey(name)) {
            return;
        }
        pathInfo.put(path, name);
    }

    public HttpServlet getServlet(String name) throws InstantiationException, IllegalAccessException {
        if (instClasses.containsKey(name)) {
            return instClasses.get(name);
        }

        HttpServlet s = servletInfo.get(name).newInstance();
        instClasses.put(name, s);
        return s;
    }

    public HttpServlet getServletContext() {
        if (instClasses.containsKey("ServletContext")) {
            return instClasses.get("ServletContext");
        }

        HttpServlet defaultServlet = new ServletContext(docBase, path);
        instClasses.put("ServletContext", defaultServlet);
        return defaultServlet;
    }

    public String getNameByPath(String path) {
        return pathInfo.get(path);
    }
}
