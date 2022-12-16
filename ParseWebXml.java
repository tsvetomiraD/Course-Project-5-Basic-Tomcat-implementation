package WarInfo;

import Servlet.HttpServlet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ParseWebXml {
    static Map<String, ServletWarInfo> serMap = new HashMap<>();
    public Map<String, ServletWarInfo> parse() throws Exception{
        File file = new File("src/web.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);
        doc.getDocumentElement().normalize();
        getServlet(doc, "servlet", "servlet-name", "servlet-class");
        getServletMappers(doc, "servlet-mapping", "servlet-name", "url-pattern");
        return serMap;
    }

    private static void getServlet(Document doc, String tagName, String name, String sClass) throws ClassNotFoundException {
        NodeList servlet = doc.getElementsByTagName(tagName);
        for (int i = 0; i < servlet.getLength(); i++) {
            Node node = servlet.item(i);
            Element eElement = (Element) node;
            String serName = eElement.getElementsByTagName(name).item(0).getTextContent();
            Class<? extends HttpServlet> serClass = (Class<? extends HttpServlet>) Class.forName(eElement.getElementsByTagName(sClass).item(0).getTextContent());
            serMap.put(serName, new ServletWarInfo(serClass, serName));
        }
    }

    private static void getServletMappers(Document doc, String tagName, String name, String urlInfo) {
        NodeList servlet = doc.getElementsByTagName(tagName);
        for (int i = 0; i < servlet.getLength(); i++) {
            Node node = servlet.item(i);
            Element eElement = (Element) node;
            String serName = eElement.getElementsByTagName(name).item(0).getTextContent();
            String url = eElement.getElementsByTagName(urlInfo).item(0).getTextContent();
            ServletWarInfo s = serMap.get(serName);
            s.url = url;
        }
    }
}
