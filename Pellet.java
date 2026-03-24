import MG2D.*;
import MG2D.geometrie.*;

public class Pellet {

    private int gridX;
    private int gridY;

    private int tailleCase;
    private int offsetX;
    private int offsetY;

    private Cercle forme;

    private boolean mange = false;

    private int points = 10;
    
    public Pellet(int gridX, int gridY, int tailleCase, int offsetX, int offsetY) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.tailleCase = tailleCase;
        this.offsetX = offsetX;
        this.offsetY = offsetY;

        int pixelX = offsetX + gridX * tailleCase + tailleCase / 2;
        int pixelY = offsetY + gridY * tailleCase + tailleCase / 2;
        this.forme = new Cercle(Couleur.JAUNE, new Point(pixelX, pixelY), 3, true);
    }

    public boolean estMange(int playerX, int playerY) {
        if(!mange && gridX == playerX && gridY == playerY) {
            mange = true;
            return true;
        }
        return false;
    }

    public void respawn() {
        mange = false;
        int pixelX = offsetX + gridX * tailleCase + tailleCase / 2;
        int pixelY = offsetY + gridY * tailleCase + tailleCase / 2;
        forme.setO(new Point(pixelX, pixelY));
    }

    public Cercle getForme() {
        if(mange) {
            return null;
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
