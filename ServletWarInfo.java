package WarInfo;

import Servlet.HttpServlet;

public class ServletWarInfo {
    String name;
    public Class<? extends HttpServlet> className;
    public String url;

    public ServletWarInfo(Class<? extends HttpServlet> sClass, String name) {
        this.className = sClass;
        this.name = name;
    }
}
