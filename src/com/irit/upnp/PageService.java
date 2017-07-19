package com.irit.upnp;

import org.fourthline.cling.binding.annotations.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

/**
 * Created by mkostiuk on 18/07/2017.
 */
@UpnpService(
        serviceType = @UpnpServiceType(value = "PageService", version = 1),
        serviceId = @UpnpServiceId("PageService")
)
public class PageService {

    private final PropertyChangeSupport propertyChangeSupport;

    public PageService() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

    @UpnpStateVariable(name = "Page")
    private String page = "0";

    @UpnpAction(name = "SetPage")
    public void setPage(@UpnpInputArgument(name = "Page") String p) throws ParserConfigurationException, SAXException, IOException {
        String oldValue = page;
        page = p;


        getPropertyChangeSupport().firePropertyChange("Page", oldValue, page);
    }
}
