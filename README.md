# JPac

**PREVOST Louis** | **EL MOUTAOUAKIL Nadir**

JPac est un mini jeu type Pac-Man pour la borne arcade de l'IUT.
Objectif: manger toutes les billes pour marquer des points, eviter les fantomes, et survivre le plus longtemps possible.

## Collaboration et Maintenance

Ce projet a été développé dans le cadre du cours de **Maintenance Applicative**. Nous avons utilisé **GitHub** comme outil de collaboration pour gérer le développement, les versions et les intégrations.

Grâce à ce workflow GitHub, nous utilisons:
- **GitHub Issues**: pour tracker les bugs, les améliorations et les tâches
- **GitHub Deployments**: pour fournir des versions stables et testées aux utilisateurs

Lien du repository: https://github.com/louisprvst/JPac

## Gameplay

- Deplacement: joystick (haut, bas, gauche, droite)
- Billes jaunes: +10 points
- Billes rouges (power-up): rend le joueur temporairement fort
- Fantomes: si power-up actif, les toucher rapporte des points
- Vies: la partie se termine quand toutes les vies sont perdues
- Scores: top 10 stocke dans `highscore`, top 5 affiche dans le menu

## Installation et lancement

### Lancer le jeu sur un ordinateur (PC)

Depuis `borne_arcade/projet/JPac`:

```bash
javac Main.java
java Main
```

### Deployer le jeu sur la borne arcade

1. Copier/mettre a jour le dossier `projet/JPac` dans le repo de la borne.
2. Verifier que ces fichiers existent:
   - `projet/JPac/Main.java`
   - `projet/JPac/bouton.txt`
   - `projet/JPac/description.txt`
   - `projet/JPac/assets/`
3. Compiler les jeux depuis la racine `borne_arcade`:

```bash
./compilation.sh
```

4. Lancer le menu borne:

```bash
./lancerBorne.sh
```