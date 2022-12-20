package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

public class DefaultServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(req.getMethod());
        printWriter.write(req.getProtocol());
        Enumeration<String> headerNames = req.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            printWriter.write(req.getHeader(headerNames.nextElement()));
        }
        printWriter.flush();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            PrintWriter printWriter = resp.getWriter();
            printWriter.write(req.getMethod());
            printWriter.write(req.getProtocol());
            Enumeration<String> headerNames = req.getHeaderNames();
            while(headerNames.hasMoreElements()) {
                printWriter.write(req.getHeader(headerNames.nextElement()));
            }
            printWriter.flush();
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(req.getMethod());
        printWriter.write(req.getProtocol());
        Enumeration<String> headerNames = req.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            printWriter.write(req.getHeader(headerNames.nextElement()));
        }
        printWriter.flush();
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(req.getMethod());
        printWriter.write(req.getProtocol());
        Enumeration<String> headerNames = req.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            printWriter.write(req.getHeader(headerNames.nextElement()));
        }
        printWriter.flush();
    }
}
