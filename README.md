# Gestion de Réservations Hôtel 
 Application de bureau développée en Java Swing pour la gestion complète d'un hôtel. Elle permet l'administration des chambres, des clients et des réservations, avec des fonctionnalités avancées de vérification de disponibilité, de statistiques et de sécurité.
# Table des Matières  
  > - Fonctionnalités  
  > - Technologies Utilisées  
  > - Architecture du Projet  
  > - Sécurité  
  > - Auteurs  

# Fonctionnalités
Conformément au cahier des charges, l'application couvre les besoins suivants :  

> # Gestion des Entités (CRUD)
- Chambres : Ajout, modification, suppression (Numéro, Type, Prix/Nuit...)
- Clients : Gestion des informations personnelles (Nom, Ville, Téléphone...), ajout, suppression et modification
- Réservations : Enregistrement des séjours (Chambre, Client, DateArrivee...), annulation et confirmation


> # Règles Métiers & Validations
- Prévention des chevauchements : Impossible de réserver une chambre déjà occupée sur la période sélectionnée    
- Recherche de disponibilité : Trouver les chambres libres entre deux dates données    
- Historique : Lister toutes les réservations d'un client spécifique    
- Validation des saisies : Champs obligatoires, formats de dates et numériques contrôlés    
> # Recherche & Filtrage
- Filtrage des chambres par type (Single, Double, Suite...)   
- Filtrage des réservations par période   
- Barre de recherche dynamique sur les clients et les chambres    
> # Statistiques & Graphiques
- Intégration de JFreeChart   
- Graphique d'occupation : Taux d'occupation par mois (Jours réservés / Jours totaux)  
- Requêtes SQL d'agrégation pour des données fiables    

> # Sécurité & Déploiement
- Authentification : Login administrateur avec mots de passe hachés (aucun stockage en clair)
- Installateur Windows : Package complet généré avec Inno Setup (setup.exe)

> # Diagrammes de classes et de cas d'utilisattion

<img width="979" height="394" alt="Capture d’écran 2026-02-28 223544" src="https://github.com/user-attachments/assets/4552f7c8-4e96-4fcc-b162-7870d1ead794" />
<img width="1600" height="860" alt="Capture d’écran 2026-03-01 000649" src="https://github.com/user-attachments/assets/7ba2b1d6-2744-43cd-8529-abe278c2c7fe" />


# Technologies utilisées
> Ce projet a été développé en utilisant les technologies suivantes :
 - Langage de programmation : Java (JDK 8/11/17)
 - Interface graphique : Java Swing
 - Base de données : MySQL
 - Accès aux données : JDBC (Java Database Connectivity)
 - Architecture : MVC léger (Model – View – Controller)
 - Tests : JUnit / Tests Console
 - Graphiques statistiques : JFreeChart
 - Sécurité : Hashage des mots de passe (SHA-256 / BCrypt)
 - Packaging et Installation : JAR exécutable, Inno Setup


# Architecture du Projet
Le projet suit une architecture en couches pour assurer la maintenabilité et la séparation des responsabilités 
<img width="896" height="1200" alt="image" src="https://github.com/user-attachments/assets/72b49cc0-cf0d-4006-a1a6-4f131e0a30c5" />



# Sécurité 
- Utilisation de requêtes préparées (PreparedStatement) pour éviter les injections SQL
- Gestion des exceptions JDBC  
- Messages d’erreur clairs pour l’utilisateur  
- Gestion des mots de passe, hachage 



# Vidéo démo :

https://github.com/user-attachments/assets/7bc4fbf9-2790-4f0b-b0c3-13dade2ecc62


> # Réalisé par :
> # Hafsa Belahnech & Nessaiba Messaadiyene 






