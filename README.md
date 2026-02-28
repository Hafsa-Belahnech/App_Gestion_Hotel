# Gestion de Réservations Hôtel 
 Application de bureau développée en Java Swing pour la gestion complète d'un hôtel. Elle permet l'administration des chambres, des clients et des réservations, avec des fonctionnalités avancées de vérification de disponibilité, de statistiques et de sécurité.
# Table des Matières  
  > - Fonctionnalités  
  > - Technologies Utilisées  
  > - Architecture du Projet  
  > - Sécurité  
  > - Captures d'Écran  
  > - Auteurs  
# Fonctionnalités
Conformément au cahier des charges, l'application couvre les besoins suivants :  
> # Gestion des Entités (CRUD)
- Chambres : Ajout, modification, suppression (Numéro, Type, Prix/Nuit...)

https://github.com/user-attachments/assets/acd2293f-8514-4a98-932a-b492cdde4cb3

- Clients : Gestion des informations personnelles (Nom, Ville, Téléphone...)
  
https://github.com/user-attachments/assets/46ca02bd-e061-40b0-be80-419c135452b5
  
- Réservations : Enregistrement des séjours (Chambre, Client, DateArrivee...)

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
- Authentification : Login administrateur avec mots de passe hachés (aucun stockage en clair)
- Installateur Windows : Package complet généré avec Inno Setup (setup.exe)

> # Diagrammes de classes et du cas d'utilisattion

<img width="979" height="394" alt="Capture d’écran 2026-02-28 223544" src="https://github.com/user-attachments/assets/4552f7c8-4e96-4fcc-b162-7870d1ead794" />

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
> <img width="1010" height="996" alt="image" src="https://github.com/user-attachments/assets/7be9de12-4bd3-4ebc-ae20-e6ecc78a5987" />
> <img width="935" height="939" alt="image" src="https://github.com/user-attachments/assets/0867a821-07c7-4fc2-b110-2181d18fa0be" />

# Sécurité 
- Utilisation de requêtes préparées (PreparedStatement) pour éviter les injections SQL
- Gestion des exceptions JDBC  
- Messages d’erreur clairs pour l’utilisateur  
- Gestion des mots de passe, hashage :
 <img width="1561" height="864" alt="image" src="https://github.com/user-attachments/assets/4277a6ee-a9a0-4a4f-a9cb-1baf8135f1cd" />

 # Captures d'écran : 
 - Inscription & Connexion pour l'utilisateur (admin, client, réceptioniste) avec code de vérification par email :
 <img width="1407" height="643" alt="image" src="https://github.com/user-attachments/assets/4142c154-e7d2-471d-b574-e345d0f5c861" />
 <img width="549" height="114" alt="image" src="https://github.com/user-attachments/assets/403bde13-9143-4186-bceb-415f663970b6" />
 <img width="571" height="1280" alt="image" src="https://github.com/user-attachments/assets/d3660dd4-3b75-463f-af3f-e2966c896b6a" />

 <img width="729" height="811" alt="image" src="https://github.com/user-attachments/assets/cb064c1d-8906-4c3a-b096-873c42b748f6" />
 <img width="553" height="84" alt="image" src="https://github.com/user-attachments/assets/678a1690-08f3-456a-b01c-ee64273fbd0e" />
 <img width="738" height="1600" alt="image" src="https://github.com/user-attachments/assets/7147b4ce-ed3d-4243-9b0c-55d0f7744aa4" />

 > # Vidéo démonstrative
  
https://github.com/user-attachments/assets/8c35fa09-8b1e-4fe3-8b93-05e5cc53702a

- Ajout/ Suppression / Mise à jour des chambres :
- Ajout / Suppression / Mise à jour des clients :
- Ajout / Suppression / Mise à jour des réservations :










 
 




