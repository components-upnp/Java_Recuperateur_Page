package com.irit.upnp;

import org.fourthline.cling.binding.annotations.*;

import java.beans.PropertyChangeSupport;

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
    public void setPage(@UpnpInputArgument(name = "Page") String p) {
        String oldValue = page;
        page = p;

        getPropertyChangeSupport().firePropertyChange("Page", oldValue, page);
    }
}
