# SigService-Posts

📱 Android Posts App

Application native performante pour la consultation d'articles, optimisée pour Android 15 avec une architecture robuste et moderne.

🛠 Instructions de Build
Ouvrir : Importer le projet dans Android Studio.

Sync : Laisser Gradle synchroniser les dépendances.

Run : Cliquer sur Run (Sélectionner le variant debug).

Release : Pour l'APK final, utiliser Build > Generate Signed Bundle / APK.

⚙️ Environnement Technique
IDE : Android Studio Meerkat Feature Drop | 2024.3.2 Patch 1

Target SDK : 35 (Android 15)

Min SDK : 24 (Android 7.0)

Architecture : MVVM avec Dependency Injection (DI) pour une modularité accrue.

💡 Choix Techniques

Min SDK 24 (Android 7.0) :

Large Portée : Assure une compatibilité avec plus de 95% des appareils Android actifs dans le monde.

Support Java 8+ : Permet l'utilisation des expressions Lambda et des Streams sans alourdir le code.


🚀 Améliorations Possibles

Offline Storage (Room) : Implémenter une base de données locale avec Room pour mettre en cache les articles et permettre une consultation sans connexion internet.

Bookmark System : Ajouter une fonctionnalité de "Favoris" pour permettre aux utilisateurs de sauvegarder des posts spécifiques.

Skeleton Loading : Remplacer les barres de chargement par des placeholders animés (Shimmer effect).

Advanced DI : Approfondir l'injection de dépendances (Hilt/Dagger) pour la gestion des repositories et des sources de données.

Shared Element Transitions : Animer l'icône de l'article de manière fluide lors du passage de la liste au détail.
