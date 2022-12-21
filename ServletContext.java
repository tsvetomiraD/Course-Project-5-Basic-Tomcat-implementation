package Servlet;

import java.io.*;
import java.util.Arrays;
import java.util.Date;

public class ServletContext extends HttpServlet {
    public String docBase;
    public String path;
    
    public ServletContext(String baseDir, String path) {
        this.docBase = baseDir;
        this.path = path;
    }
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter printWriter = resp.getWriter();
        String searchFile = docBase + req.getPathInfo().substring(path.length());
        File f = new File(searchFile);
        if (!f.exists()) {
            printWriter.write("Not found: 404");
            printWriter.flush();
        }

        if (f.isFile()) {
            try {
                writeResponseBody(f, printWriter);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return;
        }

        if (checkForIndexHtml(f, printWriter))
            return;


        try {
            setHtml(f, printWriter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setHtml(File f, PrintWriter printWriter) throws Exception {
        printWriter.write(Arrays.toString("<html>".getBytes("UTF-8")));

        for (File fileEntry : f.listFiles()) {
            String httpResponse = new Date() + "   " + "<a href='./" + fileEntry.getName() + "'>" + fileEntry.getName() + "</a>" + "</br>";
            printWriter.write(Arrays.toString(httpResponse.getBytes("UTF-8")));
        }
        printWriter.write(Arrays.toString("</html>".getBytes("UTF-8")));
    }

    public boolean checkForIndexHtml(File f, PrintWriter printWriter) throws IOException {
        File[] matches = f.listFiles((dir, name) -> name.startsWith("index") && name.endsWith(".html"));

        if (matches.length != 0) {
            BufferedReader reader = new BufferedReader(new FileReader(matches[0]));
            String line = reader.readLine();
            while (line != null) {
                printWriter.println(line);
                printWriter.flush();
                line = reader.readLine();
            }
            reader.close();
            printWriter.close();
            return true;
        }
        return false;
    }
    private void writeResponseBody(File f, PrintWriter printWriter) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(f));
        String httpResponse = br.readLine();
        while (httpResponse != null) {
            printWriter.write(Arrays.toString(httpResponse.getBytes("UTF-8")));
            printWriter.write(Arrays.toString("\n".getBytes("UTF-8")));
            printWriter.flush();
            httpResponse = br.readLine();
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter printWriter = resp.getWriter();
        printWriter.write("Method not allowed");
        printWriter.flush();
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter printWriter = resp.getWriter();
        printWriter.write("Method not allowed");
        printWriter.flush();
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter printWriter = resp.getWriter();
        printWriter.write("Method not allowed");
        printWriter.flush();
    }
}
