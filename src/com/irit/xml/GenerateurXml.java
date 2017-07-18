package com.irit.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

/**
 * Created by mkostiuk on 18/07/2017.
 */
public class GenerateurXml {

    public String getDocXml(String udn, String num) throws TransformerException, ParserConfigurationException {

        String namespace = "";
        Document doc;
        DocumentBuilderFactory db = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        builder = db.newDocumentBuilder();
        doc = builder.newDocument();
        Element root = doc.createElementNS(namespace, "Recuperateur");
        doc.appendChild(root);

        Element u = doc.createElementNS(namespace, "UDN");
        root.appendChild(u);
        u.appendChild(doc.createTextNode(udn));

        Element p = doc.createElementNS(namespace, "PAGECOURANTE");
        root.appendChild(p);
        p.appendChild(doc.createTextNode(num));

        DOMSource source = new DOMSource(doc);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(source, result);

        return writer.toString();
    }
}
