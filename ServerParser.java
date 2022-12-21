package Parsers;

import Servlet.HttpServlet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;

public class ServerParser {
    public String path;
    public String reloadable;
    public String docBase;

    public void parse() throws Exception {
        File file = new File("src/server.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);
        doc.getDocumentElement().normalize();
        getContext(doc);
    }

    private void getContext(Document doc) {
        Node servlet = doc.getElementsByTagName("Context").item(0);
        Element e = (Element) servlet;
        path = e.getAttribute("path");
        reloadable = e.getAttribute("reloadable");
        docBase = e.getAttribute("docBase");
    }
}
