package Servlet;

import java.util.Map;

public class ServletContext {
    Map<String, Class<? extends HttpServlet>> servletInfo;
    Map<String, String> pathInfo;

    Map<String, HttpServlet> instClasses;

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

    public HttpServlet getDefaultServlet() {
        if (instClasses.containsKey("DefaultServlet")) {
            return instClasses.get("DefaultServlet");
        }

        HttpServlet defaultServlet = new DefaultServlet();
        instClasses.put("DefaultServlet", defaultServlet);
        return defaultServlet;
    }

    public String getNameByPath(String path) {
        return pathInfo.get(path);
    }
}