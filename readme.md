# Magasin de pêche
## Démarrage
Assurez-vous d'avoir installé MariaDB.

Créez une base de donnée
```
CREATE DATABASE peche_magasin;
```
(modifier dans application.properties)


Clonez le projet

Assurez-vous d'avoir installé toutes les dépendances

## Exécuter les fixtures
`src/main/java/com/magasinpeche/data/DataLoader.java`

Il vous suffit d'enlever le ".save" et de démarrer le projet ensuite.

🔴 Attention, si vous relancez le projet, les fixtures se superposent entraînant ainsi des bugs. 🔴