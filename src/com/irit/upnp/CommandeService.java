package com.irit.upnp;

import org.fourthline.cling.binding.annotations.*;

import java.beans.PropertyChangeSupport;

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
    public void setCommande(@UpnpInputArgument(name = "Commande") String c) {
        String oldValue = commande;
        commande = c;

        getPropertyChangeSupport().firePropertyChange("commande", oldValue, commande);
    }
}
