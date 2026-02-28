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

https://github.com/user-attachments/assets/acd2293f-8514-4a98-932a-b492cdde4cb3

- Clients : Gestion des informations personnelles (Nom, Ville, Téléphone).
  
https://github.com/user-attachments/assets/46ca02bd-e061-40b0-be80-419c135452b5
  
- Réservations : Enregistrement des séjours (Chambre, Client, Dates).

https://github.com/user-attachments/assets/5802a95a-cd4b-4dfc-8da5-15d0a70fa7d4


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

> # Diagrammes de classes et du cas d'utilisattion









# Architecture du Projet
Le projet suit une architecture en couches pour assurer la maintenabilité et la séparation des responsabilités 
> <img width="1010" height="996" alt="image" src="https://github.com/user-attachments/assets/7be9de12-4bd3-4ebc-ae20-e6ecc78a5987" />
> <img width="935" height="939" alt="image" src="https://github.com/user-attachments/assets/0867a821-07c7-4fc2-b110-2181d18fa0be" />





