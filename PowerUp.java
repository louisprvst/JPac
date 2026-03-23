import MG2D.*;
import MG2D.geometrie.*;

public class PowerUp {
    
    private int x; // Position en cases
    private int y;
    private int tailleCase;
    private int offsetX;
    private int offsetY;
    private boolean mange;
    
    public PowerUp(int x, int y, int tailleCase, int offsetX, int offsetY) {
        this.x = x;
        this.y = y;
        this.tailleCase = tailleCase;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.mange = false;
    }
    
    // Afficher le power-up
    public void afficher(Fenetre f) {
        if(!mange) {
            int xPixel = offsetX + x * tailleCase + tailleCase / 2;
            int yPixel = offsetY + y * tailleCase + tailleCase / 2;
            
            // Grosse boule orange (beaucoup plus grosse que les pellets)
            Cercle boule = new Cercle(Couleur.ORANGE, new Point(xPixel, yPixel), 12, true);
            f.ajouter(boule);
        }
    }
    
    // Vérifier si le joueur a mangé le power-up
    public boolean verifierCollision(int playerX, int playerY) {
        if(!mange && playerX == x && playerY == y) {
            mange = true;
            return true;
        }
        return false;
    }
    
    // Getter
    public int getX() { return x; }
    public int getY() { return y; }
    public boolean isMange() { return mange; }
    
    // Réinitialiser le power-up
    public void reset() {
        mange = false;
    }
}
