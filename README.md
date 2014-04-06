# ASIAventure ?
ASIAventure est un RPG en console, réalisé en Java à l'INSA de Rouen pour le cours de programmation avancée de la première année du cycle ingénieur "Architecture des Systèmes d'Information". Ce jeu a un objectif **pédagogique** et a pour but de comprendre et d'appliquer les mécanismes avancés de la programmation orientée objet. Certaines parties de ce jeu ont été programmées mais ne sont pas présentées lorsque l'on "joue" au jeu.

## Dépendances
Pour fonctionner correctement, les fichiers suivants doivent se trouver aux localisations indiquées :

- JUnit 4.4-11 à l'adresse `/usr/share/java/junit4-4.11.jar`
- Hamcrest Core à l'adresse `/usr/share/java/hamcrest-core.jar`
- Hamcrest Library à l'adresse `/usr/share/java/hamcrest-library.jar`

## Comment jouer ?
### Compilation
Pour supprimer les fichiers générés, compiler le code source et les tests unitaires puis lancer les tests unitaires :

	make

### Exécution
Pour "jouer" au jeu :

	make jeu

Cette commande affiche le menu principal. Il est nécessaire de **charger une partie** avant de jouer. La manière la plus simple de faire ceci est de charger le fichier de description se trouvant à l'emplacement `fichiersJeu/chargement.txt`. Ce fichier indique quels éléments seront créés dans le monde où doit se dérouler le jeu. La marche complète à suivre :

1. Compilation du code source : `make`
2. Lancement du jeu : `make jouer`
3. Chargement d'un fichier de description : `2`
4. Indication de l'emplacement du fichier : `fichiersJeu/chargement.txt`
5. Démarrage du jeu : `1`

### Commandes disponibles dans le jeu
Le jeu étant assez simple, les commandes disponibles dans le jeu sont limitées :

- Prendre un objet se trouvant dans une pièce : `prendre %nomObjet%`
- Poser un objet se trouvant dans l'inventaire du personnage : `poser %nomObjet%`
- Ouvrir une porte fermée : `ouvrirPorte %nomPorte%`
- Ouvrir une porte à l'aide d'un objet : `ouvrirPorte %nomPorte% avec %nomObjet%`
- Franchir une porte : `franchir %nomPorte%`

## Sauvegarde et chargement d'une partie
Il est possible d'enregistrer l'avancement actuel dans le jeu lorsqu'on souhaite interrompre sa partie. Logiquement, il est possible de charger sa sauvegarde et de reprendre à l'avancement sauvegardé.

### Sauvegarde
Depuis le menu du jeu :

1. Choisir le choix `3`
2. Indiquer l'emplacement de la sauvegarde. Il est conseillé d'enregistrer cette sauvegarde dans le dossier `fichiersJeu`. Par exemple `fichiersJeu/sauvegarde.txt`
3. Quitter le jeu, choix `5`

### Chargement
Depuis le menu du jeu :

1. Choisir le choix `4`
2. Indiquer l'emplacement de la sauvegarde, préalablement enregistrée. Par exemple `fichiersJeu/sauvegarde.txt`
3. Jouer au jeu, choix `1`