package com.irit.upnp;

import com.irit.xml.LecteurXml;
import org.fourthline.cling.binding.annotations.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

/**
 * Created by mkostiuk on 18/07/2017.
 */
@UpnpService(
        serviceId = @UpnpServiceId("CommandeService"),
        serviceType = @UpnpServiceType(value = "CommandeService", version = 1)
)
public class CommandeService {

    private final PropertyChangeSupport propertyChangeSupport;

    public CommandeService() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

    @UpnpStateVariable(name = "Commande")
    private String commande = "";

    @UpnpAction(name = "SetCommande")
    public void setCommande(@UpnpInputArgument(name = "Commande") String c) throws ParserConfigurationException, SAXException, IOException {
        String oldValue = commande;
        commande = c;

        LecteurXml lec = new LecteurXml(commande);

        getPropertyChangeSupport().firePropertyChange("commande", oldValue, lec.getCommande());
    }
}
