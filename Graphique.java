import MG2D.*;
import MG2D.geometrie.*;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;

public class Graphique {
    
    private Fenetre f = new Fenetre("JPac", 720, 600);
    private int tailleCase = 25;
    private int offsetX;
    private int offsetY;
    private Player player;
    private ArrayList<Fantome> fantomes;
    private ArrayList<Pellet> pellets;
    private ArrayList<PowerUp> powerUps;
    private Font policeHud = new Font("Arial", Font.PLAIN, 20);

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
        {1,1,1,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,1,1,1},
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

    public void createMap(){
        f.effacer();
        f.setBackground(Color.BLACK);

        Carre fondNoir = new Carre(Couleur.NOIR, new Point(360, 300), 720, true);
        f.ajouter(fondNoir);

        int nbLignes = map.length;
        int nbColonnes = map[0].length;

        int largeurGrille = nbColonnes * tailleCase;
        int hauteurGrille = nbLignes * tailleCase;
        offsetX = (720 - largeurGrille) / 2;
        offsetY = (600 - hauteurGrille) / 2;

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
        f.rafraichir();
    }
    
    // Créer le joueur à une position de départ
    public void creerPlayer(int startX, int startY) {
        player = new Player(startX, startY, map, tailleCase, offsetX, offsetY);
        if (player.getForme() != null) {
            f.ajouter(player.getForme());
        }
    }
    
    // Créer les fantômes
    public void creerFantomes() {
        fantomes = new ArrayList<Fantome>();
        int centreX = 14;
        int centreY = 9;
        
        Fantome f1 = new Fantome(centreX, centreY, map, tailleCase, offsetX, offsetY, Couleur.ROUGE, "blinky", true);
        fantomes.add(f1);
        if (f1.getForme() != null) {
            f.ajouter(f1.getForme());
        }
        
        Fantome f2 = new Fantome(centreX, centreY, map, tailleCase, offsetX, offsetY, Couleur.ROSE, "pinky", true);
        fantomes.add(f2);
        if (f2.getForme() != null) {
            f.ajouter(f2.getForme());
        }
        
        Fantome f3 = new Fantome(centreX, centreY, map, tailleCase, offsetX, offsetY, Couleur.CYAN, "inky", true);
        fantomes.add(f3);
        if (f3.getForme() != null) {
            f.ajouter(f3.getForme());
        }
        
        Fantome f4 = new Fantome(centreX, centreY, map, tailleCase, offsetX, offsetY, Couleur.ORANGE, "clyde", true);
        fantomes.add(f4);
        if (f4.getForme() != null) {
            f.ajouter(f4.getForme());
        }
    }
    
    // Créer les pellets sur toutes les cases accessibles
    public void creerPellets() {
        pellets = new ArrayList<Pellet>();
        
        int nbLignes = map.length;
        int nbColonnes = map[0].length;
        
        for(int j = 0; j < nbLignes; j++) {
            for(int i = 0; i < nbColonnes; i++) {
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
    
    public void rafraichir() {
        f.effacer();
        f.setBackground(Color.BLACK);

        Carre fondNoir = new Carre(Couleur.NOIR, new Point(360, 300), 720, true);
        f.ajouter(fondNoir);

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
        
        for(Pellet pellet : pellets) {
            if(pellet.getForme() != null) {
                f.ajouter(pellet.getForme());
            }
        }

        for(PowerUp powerUp : powerUps) {
            powerUp.afficher(f);
        }

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
        
        afficherVies(player.getVies());
        afficherScore(player.getScore());
        
        f.rafraichir();
    }
    
    public void creerPowerUps() {
        powerUps = new ArrayList<PowerUp>();

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
        if (player.isInvulnerable()) {
            return 0;
        }

        int pointsGagnes = 0;
        boolean collisionSansPowerUp = false;

        for(Fantome fantome : fantomes) {
            if(fantome.getGridX() == player.getGridX() && fantome.getGridY() == player.getGridY()) {
                if(player.isPowerUpActif()) {
                    // Plusieurs fantomes peuvent etre empiles au centre: on les traite tous.
                    fantome.respawn();
                    pointsGagnes += 200;
                } else {
                    collisionSansPowerUp = true;
                }
            }
        }

        if (collisionSansPowerUp) {
            player.perdreVie();
            relancerJoueur();
            relancerFantomes();
            player.activerInvulnerabilite(500);
            return 0;
        }

        return pointsGagnes;
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
    
    public void relancerJoueur() {
        player.relancer(1, 1);
    }

    public void relancerFantomes() {
        for (Fantome fantome : fantomes) {
            fantome.respawn();
        }
    }

    private void afficherVies(int nbVies) {
        for (int i = 0; i < nbVies; i++) {
            Cercle vie = new Cercle(Couleur.JAUNE, new Point(20 + i * 30, 20), 8, true);
            f.ajouter(vie);
        }
    }

    private void afficherScore(int score) {
        Texte texteScore = new Texte(Couleur.BLANC, "Score: " + score, policeHud, new Point(550, 30));
        f.ajouter(texteScore);
    }

}