# Gestion de Réservations Hôtel 
 Application de bureau développée en Java Swing pour la gestion complète d'un hôtel. Elle permet l'administration des chambres, des clients et des réservations, avec des fonctionnalités avancées de vérification de disponibilité, de statistiques et de sécurité.
# Table des Matières  
  > - Fonctionnalités  
  > - Technologies Utilisées  
  > - Architecture du Projet  
  > - Installation et Configuration  
  > - Utilisation  
  > - Sécurité  
  > - Captures d'Écran  
  > - Auteurs  
# Fonctionnalités
Conformément au cahier des charges, l'application couvre les besoins suivants :  
> # Gestion des Entités (CRUD)
- Chambres : Ajout, modification, suppression (Numéro, Type, Prix/Nuit).  
- Clients : Gestion des informations personnelles (Nom, Ville, Téléphone).  
- Réservations : Enregistrement des séjours (Chambre, Client, Dates).  
> # Règles Métiers & Validations
- Prévention des chevauchements : Impossible de réserver une chambre déjà occupée sur la période sélectionnée.  
- Recherche de disponibilité : Trouver les chambres libres entre deux dates données.  
- Historique : Lister toutes les réservations d'un client spécifique.  
- Validation des saisies : Champs obligatoires, formats de dates et numériques contrôlés.  
> # Recherche & Filtrage
- Filtrage des chambres par type (Single, Double, Suite...).  
- Filtrage des réservations par période.  
- Barre de recherche dynamique sur les clients et les chambres.  
> # Statistiques & Graphiques
- Intégration de JFreeChart.  
- Graphique d'occupation : Taux d'occupation par mois (Jours réservés / Jours totaux).  
- Requêtes SQL d'agrégation pour des données fiables.  

> # Sécurité & Déploiement
- Authentification : Login administrateur avec mots de passe hachés (aucun stockage en clair).
- Installateur Windows : Package complet généré avec Inno Setup (setup.exe).

# Diagrammes de classes et du cas d'utilisattion

















##  Architecture du Projet
Le projet suit une architecture en couches pour assurer la maintenabilité et la séparation des responsabilités
