package com.irit.upnp;

import com.irit.xml.GenerateurXml;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.binding.annotations.AnnotationLocalServiceBinder;
import org.fourthline.cling.model.DefaultServiceManager;
import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.meta.*;
import org.fourthline.cling.model.types.DeviceType;
import org.fourthline.cling.model.types.UDADeviceType;
import org.fourthline.cling.model.types.UDN;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;


/**
 * Created by mkostiuk on 18/07/2017.
 */
public class RecuperateurServer implements Runnable {

    private LocalService<PageService> pageService;
    private LocalService<CommandeService> commandeService;
    private String numPage;
    private String udn;
    private GenerateurXml gen;

    @Override
    public void run() {
        final UpnpService upnpService = new UpnpServiceImpl();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                upnpService.shutdown();
            }
        });

        gen = new GenerateurXml();
        numPage = "0";

        try {
            upnpService.getRegistry().addDevice(
                    createDevice()
            );
        } catch (ValidationException e) {
            e.printStackTrace();
        }

        commandeService.getManager().getImplementation().getPropertyChangeSupport().addPropertyChangeListener(
                evt -> {
                    if (evt.getPropertyName().equals("commande")) {
                        String commande = (String) evt.getNewValue();

                        if (commande.equals("DROITE"))
                            numPage = new Integer(Integer.parseInt(numPage) + 1).toString();
                        if (commande.equals("GAUCHE"))
                            numPage = new Integer(Integer.parseInt(numPage) - 1).toString();

                        System.out.println("Numéro Page : " + numPage);

                        try {
                            pageService.getManager().getImplementation().setPage(gen.getDocXml(udn, numPage));
                        } catch (TransformerException e) {
                            e.printStackTrace();
                        } catch (ParserConfigurationException e) {
                            e.printStackTrace();
                        } catch (SAXException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

    private LocalDevice createDevice() throws ValidationException {

        DeviceIdentity identity =
                new DeviceIdentity(
                        UDN.uniqueSystemIdentifier("Recuperateur Page")
                );

        DeviceType type =
                new UDADeviceType("Diapo", 1);

        DeviceDetails details =
                new DeviceDetails(
                        "Recuperateur Page",					// Friendly Name
                        new ManufacturerDetails(
                                "UPS-IRIT",								// Manufacturer
                                ""),								// Manufacturer URL
                        new ModelDetails(
                                "RecuperateurPage",						// Model Name
                                "Composant qui récupère et transmet le numéro de la page du lecteur de diapo",	// Model Description
                                "v1" 								// Model Number
                        )
                );
        pageService =
                new AnnotationLocalServiceBinder().read(PageService.class);
        pageService.setManager(
                new DefaultServiceManager(pageService, PageService.class)
        );
        commandeService =
                new AnnotationLocalServiceBinder().read(CommandeService.class);
        commandeService.setManager(
                new DefaultServiceManager(commandeService,CommandeService.class)
        );



        //new Fenetre(voteService,commandeProfesseurService,rapportService,questionService).setVisible(true);

        udn = identity.getUdn().toString();

        return new LocalDevice(
                identity, type, details,
                new LocalService[] {pageService, commandeService}
        );
    }
}
