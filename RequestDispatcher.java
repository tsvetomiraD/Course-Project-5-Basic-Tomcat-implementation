package Servlet;

import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class RequestDispatcher {
    Map<String, Class<? extends HttpServlet>> servletInfo = new HashMap<>();
    Map<String, String> pathInfo = new HashMap<>();

    Map<String, HttpServlet> instClasses = new HashMap<>();
    public String path;
    public String docBase;
    JavaClassLoader classLoader = new JavaClassLoader();

    public void addServlet(String name, Class<? extends HttpServlet> sClass) {
        if (servletInfo.containsKey(name)) {
            return;
        }
        servletInfo.put(name, sClass);
    }

    public void addServletPath(String name, String path) {
        if (pathInfo.containsKey(name)) {
            return;
        }
        pathInfo.put(path, name);
    }

    public HttpServlet getServlet(String name) throws Exception {
        return classLoader.invokeClass(name);
        /*if (instClasses.containsKey(name)) {
            return instClasses.get(name);
        }

        HttpServlet s = servletInfo.get(name).newInstance();
        System.out.println(s);
        instClasses.put(name, s);
        return s;*/
    }

    public HttpServlet getDefaultServlet() throws Exception {
        return classLoader.invokeDefaultClass();
        /*if (instClasses.containsKey("DefaultServlet")) {
            return instClasses.get("DefaultServlet");
        }

        HttpServlet defaultServlet = new DefaultServlet(docBase, path);
        instClasses.put("DefaultServlet", defaultServlet);
        return defaultServlet;*/
    }

    public String getNameByPath(String path) {
        return pathInfo.get(path);
    }
}
