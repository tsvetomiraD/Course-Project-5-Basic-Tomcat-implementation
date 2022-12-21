package Parsers;

import Servlet.HttpServlet;
import Servlet.ServletInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class ParseWebXml {
    static ServletInfo servletInfo = new ServletInfo();
    public ServletInfo parse(String path, String docBase) throws Exception{
        File file = new File("src/web.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);
        doc.getDocumentElement().normalize();
        servletInfo.docBase = docBase;
        servletInfo.path = path;
        getServlet(doc, "servlet", "servlet-name", "servlet-class");
        getServletMappers(doc, "servlet-mapping", "servlet-name", "url-pattern", path);
        return servletInfo;
    }

    private static void getServlet(Document doc, String tagName, String name, String sClass) throws ClassNotFoundException {
        NodeList servlet = doc.getElementsByTagName(tagName);
        for (int i = 0; i < servlet.getLength(); i++) {
            Node node = servlet.item(i);
            Element eElement = (Element) node;
            String serName = eElement.getElementsByTagName(name).item(0).getTextContent();
            Class<? extends HttpServlet> serClass = (Class<? extends HttpServlet>) Class.forName(eElement.getElementsByTagName(sClass).item(0).getTextContent());
            servletInfo.addServlet(serName, serClass);
        }
    }

    private static void getServletMappers(Document doc, String tagName, String name, String urlInfo, String path) {
        NodeList servlet = doc.getElementsByTagName(tagName);
        for (int i = 0; i < servlet.getLength(); i++) {
            Node node = servlet.item(i);
            Element eElement = (Element) node;
            String serName = eElement.getElementsByTagName(name).item(0).getTextContent();
            String url = path + eElement.getElementsByTagName(urlInfo).item(0).getTextContent();
            servletInfo.addServletPath(serName, url);
        }
    }
}
