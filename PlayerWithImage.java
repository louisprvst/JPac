import MG2D.*;
import MG2D.geometrie.*;

public class PlayerWithImage {
    
    // Position dans la grille (en cases, pas en pixels)
    private int gridX;
    private int gridY;
    
    // Référence à la map pour vérifier les collisions
    private int[][] map;
    
    // Constantes pour l'affichage
    private int tailleCase;
    private int offsetX;
    private int offsetY;
    
    // Forme graphique du joueur (image ou cercle fallback)
    private Cercle forme;
    
    // Vies du joueur
    private int vies = 3;
    
    // Score du joueur
    private int score = 0;
    
    // Mode power-up
    private boolean powerUpActif = false;
    private int timerPowerUp = 0;
    
    // Direction du mouvement (0=haut, 1=bas, 2=gauche, 3=droite, -1=aucun)
    private int directionActuelle = -1;
    private int directionDesiree = -1;
    
    public PlayerWithImage(int startX, int startY, int[][] map, int tailleCase, int offsetX, int offsetY) {
        this.gridX = startX;
        this.gridY = startY;
        this.map = map;
        this.tailleCase = tailleCase;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        
        // Créer le cercle jaune pour Pac-Man
        int pixelX = offsetX + gridX * tailleCase + tailleCase / 2;
        int pixelY = offsetY + gridY * tailleCase + tailleCase / 2;
        this.forme = new Cercle(Couleur.JAUNE, new Point(pixelX, pixelY), tailleCase / 2, true);
    }
    
    // Définir la direction désirée
    public void setDirectionDesiree(int direction) {
        this.directionDesiree = direction;
    }
    
    // Effectuer le mouvement automatique
    public void bouger() {
        // D'abord essayer la direction désirée
        if(directionDesiree != -1) {
            if(essayerDirection(directionDesiree)) {
                directionActuelle = directionDesiree;
                return;
            }
        }
        
        // Si la direction désirée ne fonctionne pas, continuer dans la direction actuelle
        if(directionActuelle != -1) {
            essayerDirection(directionActuelle);
        }
    }
    
    // Essayer de se déplacer dans une direction
    private boolean essayerDirection(int direction) {
        switch(direction) {
            case 0: // Haut
                if (peutSeDeplacer(gridX, gridY - 1)) {
                    gridY--;
                    mettreAJourPosition();
                    return true;
                }
                break;
            case 1: // Bas
                if (peutSeDeplacer(gridX, gridY + 1)) {
                    gridY++;
                    mettreAJourPosition();
                    return true;
                }
                break;
            case 2: // Gauche
                if (peutSeDeplacer(gridX - 1, gridY)) {
                    gridX--;
                    mettreAJourPosition();
                    return true;
                }
                break;
            case 3: // Droite
                if (peutSeDeplacer(gridX + 1, gridY)) {
                    gridX++;
                    mettreAJourPosition();
                    return true;
                }
                break;
        }
        return false;
    }
    
    // Vérifie si le joueur peut se déplacer à la position (x, y) de la grille
    private boolean peutSeDeplacer(int x, int y) {
        // Vérifier les limites de la map
        if (y < 0 || y >= map.length || x < 0 || x >= map[0].length) {
            return true; // Permet de sortir (pour tunnel latéral style Pac-Man)
        }
        // Vérifier si ce n'est pas un mur (1 = mur, 0 = passage)
        return map[y][x] == 0;
    }
    
    // Met à jour la position graphique du joueur
    private void mettreAJourPosition() {
        // Gérer le tunnel (sortie sur les côtés)
        if (gridX < 0) {
            gridX = map[0].length - 1;
        } else if (gridX >= map[0].length) {
            gridX = 0;
        }
        
        // Convertir position grille en pixels
        int pixelX = offsetX + gridX * tailleCase + tailleCase / 2;
        int pixelY = offsetY + gridY * tailleCase + tailleCase / 2;
        forme.setO(new Point(pixelX, pixelY));
    }
    
    // Getter pour la forme graphique
    public Cercle getForme() {
        return forme;
    }
    
    // Getters pour les positions
    public int getGridX() {
        return gridX;
    }
    
    public int getGridY() {
        return gridY;
    }
    
    // Gestion des vies
    public void perdreVie() {
        vies--;
    }
    
    public int getVies() {
        return vies;
    }
    
    public boolean estMort() {
        return vies <= 0;
    }
    
    // Gestion du score
    public void ajouterScore(int points) {
        score += points;
    }
    
    public int getScore() {
        return score;
    }
    
    public void reinitialiserScore() {
        score = 0;
    }
    
    // Relancer le joueur à une nouvelle position
    public void relancer(int newGridX, int newGridY) {
        gridX = newGridX;
        gridY = newGridY;
        mettreAJourPosition();
    }
    
    // Gestion du power-up
    public void activerPowerUp() {
        powerUpActif = true;
        timerPowerUp = 100; // 100 * 100ms = 10 secondes
    }
    
    public void mettreAJourPowerUp() {
        if(powerUpActif) {
            timerPowerUp--;
            if(timerPowerUp <= 0) {
                powerUpActif = false;
            }
        }
    }
    
    public boolean isPowerUpActif() {
        return powerUpActif;
    }
}
