# Java_Recuperateur_Page
Récupère et transmet le numéro de la page courante (lors d'une présentation)

<strong>Description :</strong>

Ce composant permet d'ajouter une fonctionnalité au lecteur de PDF, celle de connaître le numéro de la slide courante, lors d'une
présentation par exemple.
Ce composant ne présente pas d'interface graphique.

<strong>Lancement de l'application :</strong>

Afin de lancer ce composant, il faut exécuter le script lanceur.bat contenu dans l'archive au nom de ce composant contenue dans 
le répertoire target du projet. Afin de générer cette archive, il faut lancer la phase Maven install après avoir choisi le profil
Windows ou Linux selon le système d'exploitation de votre machine.

<strong>Spécifications UPnP : </strong>

Ce composant offre les services UPnP CommandeService et PageService dont voici la description :

  1) CommandeService :
    a) public void setCommande(String commande) : reçoit un message XML contenant la commande emise par une télécommande UPnP.
    La commande DROITE aura pour effet d'incrémenter le numéro de la slide courante et GAUCHE de la décrémenter.
  2) PageService :
    a) public void setPage(String page) : envoie un message XML via un événement UPnP "Page" le numéro de la slide courante.

Voici le schéma représentant le composant :

![alt tag](https://github.com/components-upnp/Java_Recuperateur_Page/blob/master/Recuperateur.png)

<strong>Maintenance :</strong>

Ce projet est un projet Maven dont la phase d'exécution install créé l'exécutable de l'application. 
Aucun test unitaire n'a été produit pour l'instant.
