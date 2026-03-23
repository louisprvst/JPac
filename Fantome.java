import MG2D.*;
import MG2D.geometrie.*;
import java.util.Random;

public class Fantome {
    
    // Position dans la grille (en cases, pas en pixels)
    private int gridX;
    private int gridY;
    private int startX;
    private int startY;
    
    // Référence à la map pour vérifier les collisions
    private int[][] map;
    
    // Constantes pour l'affichage
    private int tailleCase;
    private int offsetX;
    private int offsetY;
    
    // Sprites du fantôme
    private Texture spriteNormal;
    private Texture spriteBleu;
    private Texture spriteActuelle;
    
    // Fallback: cercle coloré
    private Cercle forme;
    
    // Couleur du fantôme
    private Couleur couleur;
    
    // Pour le mouvement aléatoire
    private Random random;
    private int compteur = 0;
    private int delaiDeplacement;
    private boolean poursuiteActive; // true = chasse le joueur, false = mouvement aléatoire
    
    // Noms des ghosts pour charger les bonnes images
    private String ghostName;
    
    public Fantome(int startX, int startY, int[][] map, int tailleCase, int offsetX, int offsetY, Couleur couleur, String ghostName) {
        this(startX, startY, map, tailleCase, offsetX, offsetY, couleur, ghostName, true);
    }
    
    public Fantome(int startX, int startY, int[][] map, int tailleCase, int offsetX, int offsetY, Couleur couleur, String ghostName, boolean poursuiteActive) {
        this.gridX = startX;
        this.gridY = startY;
        this.startX = startX;
        this.startY = startY;
        this.map = map;
        this.tailleCase = tailleCase;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.couleur = couleur;
        this.ghostName = ghostName;
        this.poursuiteActive = poursuiteActive;
        this.random = new Random();
        this.delaiDeplacement = random.nextInt(2) + 1; // Délai aléatoire entre 1 et 2
        
        // Charger les sprites du fantôme
        int pixelX = offsetX + gridX * tailleCase;
        int pixelY = offsetY + gridY * tailleCase;
        Point pos = new Point(pixelX, pixelY);
        
        try {
            // Charger les sprites normal et bleu pour ce fantôme
            this.spriteNormal = new Texture("assets/ghosts/" + ghostName + ".png", pos);
            this.spriteBleu = new Texture("assets/ghosts/blue_ghost.png", pos);
            this.spriteActuelle = this.spriteNormal;
        } catch (Exception e) {
            System.out.println("Erreur chargement sprite fantôme " + ghostName + ": " + e.getMessage());
            // Fallback: créer le cercle coloré
            int centerX = pixelX + tailleCase / 2;
            int centerY = pixelY + tailleCase / 2;
            this.forme = new Cercle(couleur, new Point(centerX, centerY), tailleCase / 2, true);
        }
    }
    
    // Mouvement aléatoire du fantôme
    public void bouger(int playerX, int playerY, boolean playerPowerUp) {
        compteur++;
        
        // Changer le sprite du fantôme si power-up actif
        if(playerPowerUp) {
            if (spriteBleu != null) {
                spriteActuelle = spriteBleu;
            } else if (forme != null) {
                forme.setCouleur(Couleur.BLEU);
            }
        } else {
            if (spriteNormal != null) {
                spriteActuelle = spriteNormal;
            } else if (forme != null) {
                forme.setCouleur(couleur);
            }
        }
        
        // Le fantôme se déplace selon son délai personnel
        if (compteur >= delaiDeplacement) {
            compteur = 0;
            
            int direction = -1;
            
            // Si le joueur a un power-up actif, fuir
            if(playerPowerUp) {
                direction = choisirDirectionFuite(playerX, playerY);
            } else if (poursuiteActive) {
                // Si la poursuite est active, chasser le joueur
                direction = choisirDirectionPoursuite(playerX, playerY);
            } else {
                // Sinon mouvement aléatoire
                direction = choisirDirectionAleatoire();
            }
            
            // Si une direction valide a été trouvée, bouger
            if (direction != -1) {
                switch(direction) {
                    case 0: // Haut
                        if (peutSeDeplacer(gridX, gridY - 1)) {
                            gridY--;
                        }
                        break;
                    case 1: // Bas
                        if (peutSeDeplacer(gridX, gridY + 1)) {
                            gridY++;
                        }
                        break;
                    case 2: // Gauche
                        if (peutSeDeplacer(gridX - 1, gridY)) {
                            gridX--;
                        }
                        break;
                    case 3: // Droite
                        if (peutSeDeplacer(gridX + 1, gridY)) {
                            gridX++;
                        }
                        break;
                }
                
                mettreAJourPosition();
            }
        }
    }
    
    // Choisir une direction pour fuir le joueur
    private int choisirDirectionFuite(int playerX, int playerY) {
        // Calculer les distances pour chaque direction
        int[] distances = new int[4];
        int[] directionsValides = new int[4];
        int nbDirectionsValides = 0;
        
        // 0 = Haut
        if(peutSeDeplacer(gridX, gridY - 1)) {
            distances[nbDirectionsValides] = Math.abs(gridX - playerX) + Math.abs((gridY - 1) - playerY);
            directionsValides[nbDirectionsValides] = 0;
            nbDirectionsValides++;
        }
        
        // 1 = Bas
        if(peutSeDeplacer(gridX, gridY + 1)) {
            distances[nbDirectionsValides] = Math.abs(gridX - playerX) + Math.abs((gridY + 1) - playerY);
            directionsValides[nbDirectionsValides] = 1;
            nbDirectionsValides++;
        }
        
        // 2 = Gauche
        if(peutSeDeplacer(gridX - 1, gridY)) {
            distances[nbDirectionsValides] = Math.abs((gridX - 1) - playerX) + Math.abs(gridY - playerY);
            directionsValides[nbDirectionsValides] = 2;
            nbDirectionsValides++;
        }
        
        // 3 = Droite
        if(peutSeDeplacer(gridX + 1, gridY)) {
            distances[nbDirectionsValides] = Math.abs((gridX + 1) - playerX) + Math.abs(gridY - playerY);
            directionsValides[nbDirectionsValides] = 3;
            nbDirectionsValides++;
        }
        
        // S'il n'y a pas de direction valide
        if (nbDirectionsValides == 0) {
            return -1;
        }
        
        // Trouver la direction avec la plus grande distance (fuite)
        int maxDistance = -1;
        int directionFuite = directionsValides[0];
        for(int i = 0; i < nbDirectionsValides; i++) {
            if(distances[i] > maxDistance) {
                maxDistance = distances[i];
                directionFuite = directionsValides[i];
            }
        }
        
        return directionFuite;
    }
    
    // Choisir une direction pour poursuivre le joueur
    private int choisirDirectionPoursuite(int playerX, int playerY) {
        // Calculer les distances pour chaque direction
        int[] distances = new int[4];
        int[] directionsValides = new int[4];
        int nbDirectionsValides = 0;
        
        // 0 = Haut
        if(peutSeDeplacer(gridX, gridY - 1)) {
            distances[nbDirectionsValides] = Math.abs(gridX - playerX) + Math.abs((gridY - 1) - playerY);
            directionsValides[nbDirectionsValides] = 0;
            nbDirectionsValides++;
        }
        
        // 1 = Bas
        if(peutSeDeplacer(gridX, gridY + 1)) {
            distances[nbDirectionsValides] = Math.abs(gridX - playerX) + Math.abs((gridY + 1) - playerY);
            directionsValides[nbDirectionsValides] = 1;
            nbDirectionsValides++;
        }
        
        // 2 = Gauche
        if(peutSeDeplacer(gridX - 1, gridY)) {
            distances[nbDirectionsValides] = Math.abs((gridX - 1) - playerX) + Math.abs(gridY - playerY);
            directionsValides[nbDirectionsValides] = 2;
            nbDirectionsValides++;
        }
        
        // 3 = Droite
        if(peutSeDeplacer(gridX + 1, gridY)) {
            distances[nbDirectionsValides] = Math.abs((gridX + 1) - playerX) + Math.abs(gridY - playerY);
            directionsValides[nbDirectionsValides] = 3;
            nbDirectionsValides++;
        }
        
        // S'il n'y a pas de direction valide
        if (nbDirectionsValides == 0) {
            return -1;
        }
        
        // Trouver la direction avec la plus petite distance (poursuite)
        int minDistance = Integer.MAX_VALUE;
        int directionPoursuite = directionsValides[0];
        for(int i = 0; i < nbDirectionsValides; i++) {
            if(distances[i] < minDistance) {
                minDistance = distances[i];
                directionPoursuite = directionsValides[i];
            }
        }
        
        return directionPoursuite;
    }
    
    // Mouvement aléatoire
    private int choisirDirectionAleatoire() {
        // Chercher toutes les directions possibles
        int[] directionsValides = new int[4];
        int nbDirectionsValides = 0;
        
        for (int d = 0; d < 4; d++) {
            if (peutSeDeplacer(gridX + getDeltaX(d), gridY + getDeltaY(d))) {
                directionsValides[nbDirectionsValides] = d;
                nbDirectionsValides++;
            }
        }
        
        // S'il n'y a pas de direction valide, rester sur place
        if (nbDirectionsValides == 0) {
            return -1; // Signal pour ne pas bouger
        }
        
        // Choisir aléatoirement parmi les directions valides
        return directionsValides[random.nextInt(nbDirectionsValides)];
    }
    
    // Helper pour obtenir le delta X selon la direction
    private int getDeltaX(int direction) {
        if (direction == 2) return -1;      // Gauche
        if (direction == 3) return 1;       // Droite
        return 0;                           // Haut ou Bas
    }
    
    // Helper pour obtenir le delta Y selon la direction
    private int getDeltaY(int direction) {
        if (direction == 0) return 1;       // Haut (Y+ en bas)
        if (direction == 1) return -1;      // Bas (Y- en haut)
        return 0;                           // Gauche ou Droite
    }
    
    // Vérifie si le fantôme peut se déplacer à la position (x, y) de la grille
    private boolean peutSeDeplacer(int x, int y) {
        // Vérifier les limites de la map
        if (y < 0 || y >= map.length || x < 0 || x >= map[0].length) {
            return true; // Permet de sortir (pour tunnel latéral style Pac-Man)
        }
        // Vérifier si ce n'est pas un mur (1 = mur, 0 = passage)
        return map[y][x] == 0;
    }
    
    // Met à jour la position graphique du fantôme
    private void mettreAJourPosition() {
        // Gérer le tunnel (sortie sur les côtés)
        if (gridX < 0) {
            gridX = map[0].length - 1;
        } else if (gridX >= map[0].length) {
            gridX = 0;
        }
        
        // Convertir position grille en pixels
        int pixelX = offsetX + gridX * tailleCase;
        int pixelY = offsetY + gridY * tailleCase;
        Point pos = new Point(pixelX, pixelY);
        
        // Mettre à jour la position du sprite
        if (spriteActuelle != null) {
            spriteActuelle.setA(pos);
        }
        
        // Fallback avec cercle si les sprites ne sont pas chargés
        if (forme != null) {
            int centerX = pixelX + tailleCase / 2;
            int centerY = pixelY + tailleCase / 2;
            forme.setO(new Point(centerX, centerY));
        }
    }
    
    // Vérifie si le fantôme a attrapé le joueur
    public boolean attrapJoueur(Player joueur) {
        return gridX == joueur.getGridX() && gridY == joueur.getGridY();
    }
    
    // Getter pour la forme graphique (fallback)
    public Cercle getForme() {
        return forme;
    }
    
    // Getter pour le sprite
    public Texture getSprite() {
        return spriteActuelle;
    }
    
    // Getter pour vérifier si on a des sprites
    public boolean hasSpriteActuelle() {
        return spriteActuelle != null;
    }
    
    // Getters pour les positions
    public int getGridX() {
        return gridX;
    }
    
    public int getGridY() {
        return gridY;
    }
    
    // Respawn le fantôme au milieu de la map
    public void respawn() {
        gridX = 14;  // Milieu horizontal (28/2 = 14)
        gridY = 9;   // Milieu vertical
        mettreAJourPosition();
    }
}
