import MG2D.*;
import MG2D.geometrie.*;

public class PowerUp {

    private int x;
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

    public void afficher(Fenetre f) {
        if(!mange) {
            int xPixel = offsetX + x * tailleCase + tailleCase / 2;
            int yPixel = offsetY + y * tailleCase + tailleCase / 2;

            Cercle boule = new Cercle(Couleur.ROUGE, new Point(xPixel, yPixel), 3, true);
            f.ajouter(boule);
        }
    }

    public boolean verifierCollision(int playerX, int playerY) {
        if(!mange && playerX == x && playerY == y) {
            mange = true;
            return true;
        }
        return false;
    }
}
