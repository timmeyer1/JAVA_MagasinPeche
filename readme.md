# Magasin de pêche - Peche 3000
## 1. Démarrage
### 1.1 Prérequis
- MariaDB / MySQL
- Maven
- JDK 17 (Amazon Corretto 17.0.13.)

### 1.2 Créez une base de donnée
```
CREATE DATABASE peche_magasin;
```
*(vous pouvez modifier le nom de la base de donnée dans `src/main/application.properties`)*


### 1.3 Clonez le projet

Assurez-vous d'avoir installé toutes les dépendances

## 2. Exécuter les fixtures
`src/main/java/com/magasinpeche/data/DataLoader.java`

Il vous suffit d'enlever le ".save" et de démarrer le projet ensuite.

🔴 Attention, si vous relancez le projet, les fixtures se superposent entraînant ainsi des bugs. 🔴

- Login admin: `admin@admin.fr - admin123`
- Login user: `test@test.fr - test123`