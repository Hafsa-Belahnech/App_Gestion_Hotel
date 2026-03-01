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
- Clients : Gestion des informations personnelles (Nom, Ville, Téléphone...)
- Réservations : Enregistrement des séjours (Chambre, Client, DateArrivee...)


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

 > # Vidéos démonstratives
  
https://github.com/user-attachments/assets/8c35fa09-8b1e-4fe3-8b93-05e5cc53702a

- Ajout/ Suppression / Mise à jour des chambres :

https://github.com/user-attachments/assets/f2f5bc2e-50be-4868-88f2-7f1ef99a3739

https://github.com/user-attachments/assets/a8807ce8-a7bc-4a7b-aeaa-8b2e4ee8a6b8

https://github.com/user-attachments/assets/c1a1f8fe-8139-4a5f-9e8e-609d62ab0717



- Ajout / Suppression / Mise à jour des clients :

https://github.com/user-attachments/assets/8cf2ebe0-263c-43ff-98fd-0320ee7e223c

https://github.com/user-attachments/assets/f7ef8d24-4d66-4436-ac92-c97bf85ad371

https://github.com/user-attachments/assets/61ca3553-83ef-4074-bae8-49277178117e




- Ajout / Confirmation / Mise à jour du statut des réservations :

https://github.com/user-attachments/assets/a58fb687-e6d7-4603-9872-5c00810bd726

<img width="1781" height="673" alt="image" src="https://github.com/user-attachments/assets/d422ef37-1ddb-4e66-924b-77b34c5b4ffc" />




- Filtrage et recherche :

https://github.com/user-attachments/assets/93ed5e48-ebb4-4afd-98bc-58389e13dbbd


  
- Statistiques et graphes :

https://github.com/user-attachments/assets/428f07dc-31ed-46b0-9615-d07685dd7276

> # Réalisé par :
 > Hafsa Belahnech & Nessaiba Messaadiyene 






