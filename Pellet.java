import MG2D.*;
import MG2D.geometrie.*;

public class Pellet {
    
    // Position dans la grille
    private int gridX;
    private int gridY;
    
    // Constantes pour l'affichage
    private int tailleCase;
    private int offsetX;
    private int offsetY;
    
    // Forme graphique du pellet
    private Cercle forme;
    
    // Indique si le pellet a été mangé
    private boolean mange = false;
    
    // Points donnés par ce pellet
    private int points = 10;
    
    public Pellet(int gridX, int gridY, int tailleCase, int offsetX, int offsetY) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.tailleCase = tailleCase;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        
        // Créer un petit cercle jaune pour le pellet
        int pixelX = offsetX + gridX * tailleCase + tailleCase / 2;
        int pixelY = offsetY + gridY * tailleCase + tailleCase / 2;
        this.forme = new Cercle(Couleur.JAUNE, new Point(pixelX, pixelY), 3, true);
    }
    
    // Vérifie si le joueur a mangé ce pellet
    public boolean estMange(int playerX, int playerY) {
        if(!mange && gridX == playerX && gridY == playerY) {
            mange = true;
            return true;
        }
        return false;
    }
    
    // Réinitialiser le pellet
    public void respawn() {
        mange = false;
        int pixelX = offsetX + gridX * tailleCase + tailleCase / 2;
        int pixelY = offsetY + gridY * tailleCase + tailleCase / 2;
        forme.setO(new Point(pixelX, pixelY));
    }
    
    // Getters
    public Cercle getForme() {
        if(mange) {
            return null;  // Ne pas afficher si mangé
        }
        return forme;
    }
    
    public boolean estMange() {
        return mange;
    }
    
    public int getPoints() {
        return points;
    }
    
    public int getGridX() {
        return gridX;
    }
    
    public int getGridY() {
        return gridY;
    }
}
