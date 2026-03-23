import MG2D.*;
import MG2D.geometrie.*;
import java.util.ArrayList;

public class Graphique {
    
    private Fenetre f = new Fenetre("JPac", 720, 600);
    private int tailleCase = 25;
    private int offsetX;
    private int offsetY;
    private Player player;
    private ArrayList<Fantome> fantomes;
    private ArrayList<Pellet> pellets;
    private ArrayList<PowerUp> powerUps;
    private Affichage affichage;
    private int difficulte; // 0=Easy, 1=Medium, 2=Hard

    // 0 = chemin, 1 = mur
    private int[][] map = {
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,1,1,1,0,1,1,1,1,1,0,0,1,1,0,0,1,1,1,1,1,0,1,1,1,0,1},
        {1,0,1,1,1,0,1,1,1,1,1,0,0,1,1,0,0,1,1,1,1,1,0,1,1,1,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,0,1},
        {1,0,0,0,0,0,1,1,0,0,0,0,0,1,1,0,0,0,0,0,1,1,0,0,0,0,0,1},
        {1,1,1,1,1,0,1,1,1,1,1,0,0,1,1,0,0,1,1,1,1,1,0,1,1,1,1,1},
        {1,1,1,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,1,1,1,1,1},
        {1,1,1,1,1,0,1,1,0,1,1,1,0,0,0,0,1,1,1,0,1,1,0,1,1,1,1,1},
        {0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0},
        {1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1},
        {1,1,1,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,1,1,1},
        {1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,1,1,1,0,1,1,1,1,1,0,0,1,1,0,0,1,1,1,1,1,0,1,1,1,0,1},
        {1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1},
        {1,1,1,0,1,0,1,1,0,1,1,1,1,1,1,1,1,1,1,0,1,1,0,1,0,1,1,1},
        {1,0,0,0,0,0,1,1,0,0,0,0,0,1,1,0,0,0,0,0,1,1,0,0,0,0,0,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
    };

    // Constructeur par défaut
    public Graphique() {
        this.difficulte = 2; // Hard par défaut
    }
    
    // Constructeur avec difficulté
    public Graphique(int difficulte) {
        this.difficulte = difficulte;
    }

    public void createMap(){
        f.effacer();
        
        // Ajouter un fond noir
        Carre fondNoir = new Carre(Couleur.NOIR, new Point(360, 300), 720, true);
        f.ajouter(fondNoir);

        int nbLignes = map.length;
        int nbColonnes = map[0].length;

        // Centrer automatiquement la grille
        int largeurGrille = nbColonnes * tailleCase;
        int hauteurGrille = nbLignes * tailleCase;
        offsetX = (720 - largeurGrille) / 2;
        offsetY = (600 - hauteurGrille) / 2;

        for(int j = 0 ; j < nbLignes ; j++){
            for(int i = 0 ; i < nbColonnes ; i++){

                int x = offsetX + i * tailleCase ;
                int y = offsetY + j * tailleCase ;

                if(map[j][i] == 1){
                    // Mur bleu
                    Carre mur = new Carre(Couleur.BLEU,new Point(x,y),tailleCase,true);
                    f.ajouter(mur);
                }
                else{
                    // Passage gris/noir
                    Carre passage = new Carre(Couleur.NOIR,new Point(x,y),tailleCase,true);
                    f.ajouter(passage);
                }
            }
        }
        
        // Initialiser l'affichage
        affichage = new Affichage(f);
        f.rafraichir();
    }
    
    // Créer le joueur à une position de départ
    public void creerPlayer(int startX, int startY) {
        player = new Player(startX, startY, map, tailleCase, offsetX, offsetY);
        f.ajouter(player.getForme());
    }
    
    // Créer les fantômes
    public void creerFantomes() {
        fantomes = new ArrayList<Fantome>();
        
        // 0=Easy (aléatoire), 1=Medium (2 chasing, 2 random), 2=Hard (tous chasing)
        boolean blinkyChase = (difficulte >= 1);
        boolean pinkyChase = (difficulte >= 2);
        boolean inkyChase = (difficulte >= 1);
        boolean clydeChase = (difficulte >= 2);
        
        // Fantôme rouge (Blinky)
        Fantome f1 = new Fantome(5, 5, map, tailleCase, offsetX, offsetY, Couleur.ROUGE, "blinky", blinkyChase);
        fantomes.add(f1);
        f.ajouter(f1.getForme());
        
        // Fantôme rose (Pinky)
        Fantome f2 = new Fantome(22, 5, map, tailleCase, offsetX, offsetY, Couleur.ROSE, "pinky", pinkyChase);
        fantomes.add(f2);
        f.ajouter(f2.getForme());
        
        // Fantôme cyan (Inky)
        Fantome f3 = new Fantome(5, 14, map, tailleCase, offsetX, offsetY, Couleur.CYAN, "inky", inkyChase);
        fantomes.add(f3);
        f.ajouter(f3.getForme());
        
        // Fantôme orange (Clyde)
        Fantome f4 = new Fantome(22, 14, map, tailleCase, offsetX, offsetY, Couleur.ORANGE, "clyde", clydeChase);
        fantomes.add(f4);
        f.ajouter(f4.getForme());
    }
    
    // Créer les pellets sur toutes les cases accessibles
    public void creerPellets() {
        pellets = new ArrayList<Pellet>();
        
        int nbLignes = map.length;
        int nbColonnes = map[0].length;
        
        for(int j = 0; j < nbLignes; j++) {
            for(int i = 0; i < nbColonnes; i++) {
                // Créer un pellet sur chaque case accessible (pas un mur)
                if(map[j][i] == 0) {
                    Pellet pellet = new Pellet(i, j, tailleCase, offsetX, offsetY);
                    pellets.add(pellet);
                }
            }
        }
    }
    
    // Vérifier si un pellet a été mangé et retourner les points
    public int verifierPellets() {
        int pointsGagnes = 0;
        
        for(Pellet pellet : pellets) {
            if(pellet.estMange(player.getGridX(), player.getGridY())) {
                pointsGagnes += pellet.getPoints();
            }
        }
        
        return pointsGagnes;
    }
    
    // Vérifie si tous les pellets ont été mangés
    public boolean tousLesPelletsMange() {
        for(Pellet pellet : pellets) {
            if(!pellet.estMange()) {
                return false;
            }
        }
        return true;
    }
    
    // Respawn tous les pellets
    public void respawnPellets() {
        for(Pellet pellet : pellets) {
            pellet.respawn();
        }
    }
    
    // Vérifier si les fantômes ont attrapé le joueur
    public boolean jeuTermine() {
        for(Fantome fantome : fantomes) {
            if(fantome.attrapJoueur(player)) {
                return true;
            }
        }
        return false;
    }
    
    // Rafraîchir l'affichage (à appeler après chaque déplacement)
    public void rafraichir() {
        f.effacer();
        
        // Redessiner la map
        int nbLignes = map.length;
        int nbColonnes = map[0].length;
        
        for(int j = 0 ; j < nbLignes ; j++){
            for(int i = 0 ; i < nbColonnes ; i++){
                int x = offsetX + i * tailleCase ;
                int y = offsetY + j * tailleCase ;

                if(map[j][i] == 1){
                    Carre mur = new Carre(Couleur.BLEU,new Point(x,y),tailleCase,true);
                    f.ajouter(mur);
                }
                else{
                    Carre passage = new Carre(Couleur.NOIR,new Point(x,y),tailleCase,true);
                    f.ajouter(passage);
                }
            }
        }
        
        // Afficher tous les pellets qui n'ont pas été mangés
        for(Pellet pellet : pellets) {
            if(pellet.getForme() != null) {
                f.ajouter(pellet.getForme());
            }
        }
        
        // Afficher tous les power-ups
        for(PowerUp powerUp : powerUps) {
            powerUp.afficher(f);
        }
        
        // Redessiner le joueur et les fantômes (sprites ou fallback cercles)
        if (player.hasSpriteActuelle()) {
            f.ajouter(player.getSprite());
        } else {
            f.ajouter(player.getForme());
        }
        
        for(Fantome fantome : fantomes) {
            if (fantome.hasSpriteActuelle()) {
                f.ajouter(fantome.getSprite());
            } else {
                f.ajouter(fantome.getForme());
            }
        }
        
        // Afficher les vies et le score
        affichage.afficherVies(player.getVies());
        affichage.afficherScore(player.getScore());
        
        f.rafraichir();
    }
    
    // Créer les 4 power-ups dans la map
    public void creerPowerUps() {
        powerUps = new ArrayList<PowerUp>();
        
        // 4 power-ups aux 4 coins du terrain (loin du départ du joueur)
        powerUps.add(new PowerUp(1, 18, tailleCase, offsetX, offsetY));
        powerUps.add(new PowerUp(26, 18, tailleCase, offsetX, offsetY));
        powerUps.add(new PowerUp(26, 1, tailleCase, offsetX, offsetY));
        powerUps.add(new PowerUp(1, 18, tailleCase, offsetX, offsetY));
        powerUps.add(new PowerUp(26, 1, tailleCase, offsetX, offsetY));
    }
    
    // Vérifier si le joueur a mangé un power-up
    public boolean verifierPowerUps() {
        for(PowerUp powerUp : powerUps) {
            if(powerUp.verifierCollision(player.getGridX(), player.getGridY())) {
                player.activerPowerUp();
                return true;
            }
        }
        return false;
    }
    
    // Vérifier les collisions avec les fantômes (et les manger si power-up actif)
    public int verifierCollisionsFantomes() {
        for(Fantome fantome : fantomes) {
            if(fantome.getGridX() == player.getGridX() && fantome.getGridY() == player.getGridY()) {
                if(player.isPowerUpActif()) {
                    // Le joueur a mangé le fantôme: +200 points et respawn
                    fantome.respawn();
                    return 200;
                } else {
                    // Le fantôme a mangé le joueur
                    player.perdreVie();
                    relancerJoueur();
                    return 0;
                }
            }
        }
        return 0;
    }
    
    // Getters
    public Player getPlayer() {
        return player;
    }
    
    public ArrayList<Fantome> getFantomes() {
        return fantomes;
    }
    
    public int[][] getMap() {
        return map;
    }
    
    public Fenetre getFenetre() {
        return f;
    }
    
    // Relancer le joueur à la position de départ
    public void relancerJoueur() {
        player.relancer(1, 1);
    }

}