package com.irit.xml;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by mkostiuk on 18/07/2017.
 */
public class LecteurXml {

    private String udn;
    private String commande;

    public LecteurXml(String xml) throws IOException, SAXException, ParserConfigurationException {

        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();

        DefaultHandler handler = new DefaultHandler() {

            boolean isUdn = false;
            boolean isCommande = false;

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) {
                if (qName.equals("UDN"))
                    isUdn = true;
                if (qName.equals("Commande"))
                    isCommande = true;
            }

            @Override
            public void characters(char ch[], int start, int length) {
                if (isCommande) {
                    isCommande = false;
                    commande = new String(ch, start, length);
                }
                if (isUdn) {
                    isUdn = false;
                    udn = new String(ch, start, length);
                }
            }
        };
        sp.parse(new InputSource(new StringReader(xml)), handler);
    }

    public String getUdn() {
        return udn;
    }

    public String getCommande() {
        return commande;
    }
}
