import MG2D.*;
import MG2D.geometrie.*;

public class Player {

    private int gridX;
    private int gridY;

    private int[][] map;

    private int tailleCase;
    private int offsetX;
    private int offsetY;

    private Texture spriteUp;
    private Texture spriteDown;
    private Texture spriteLeft;
    private Texture spriteRight;
    private Texture spriteActuelle;

    private Cercle forme;

    private int vies = 3;

    private int score = 0;

    private boolean powerUpActif = false;
    private int timerPowerUp = 0;

    private long invulnerableUntilMs = 0;

    private int directionActuelle = -1;
    private int directionDesiree = -1;
    
    public Player(int startX, int startY, int[][] map, int tailleCase, int offsetX, int offsetY) {
        this.gridX = startX;
        this.gridY = startY;
        this.map = map;
        this.tailleCase = tailleCase;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        
        int pixelX = offsetX + gridX * tailleCase;
        int pixelY = offsetY + gridY * tailleCase;
        Point pos = new Point(pixelX, pixelY);

        try {
            this.spriteUp = new Texture("assets/pacman_up_1.png", pos);
            this.spriteDown = new Texture("assets/pacman_down_1.png", pos);
            this.spriteLeft = new Texture("assets/pacman_left_1.png", pos);
            this.spriteRight = new Texture("assets/pacman_right_1.png", pos);
            this.spriteActuelle = this.spriteRight;
        } catch (Exception e) {
            System.out.println("Erreur chargement sprites Pac-Man: " + e.getMessage());
            int centerX = pixelX + tailleCase / 2;
            int centerY = pixelY + tailleCase / 2;
            this.forme = new Cercle(Couleur.JAUNE, new Point(centerX, centerY), tailleCase / 2, true);
        }
    }

    public void setDirectionDesiree(int direction) {
        this.directionDesiree = direction;
    }

    public void bouger() {
        if(directionDesiree != -1) {
            if(essayerDirection(directionDesiree)) {
                directionActuelle = directionDesiree;
                return;
            }
        }

        if(directionActuelle != -1) {
            essayerDirection(directionActuelle);
        }
    }

    private boolean essayerDirection(int direction) {
        switch(direction) {
            case 0:
                if (peutSeDeplacer(gridX, gridY + 1)) {
                    gridY++;
                    if (spriteUp != null) spriteActuelle = spriteUp;
                    mettreAJourPosition();
                    return true;
                }
                break;
            case 1:
                if (peutSeDeplacer(gridX, gridY - 1)) {
                    gridY--;
                    if (spriteDown != null) spriteActuelle = spriteDown;
                    mettreAJourPosition();
                    return true;
                }
                break;
            case 2:
                if (peutSeDeplacer(gridX - 1, gridY)) {
                    gridX--;
                    if (spriteLeft != null) spriteActuelle = spriteLeft;
                    mettreAJourPosition();
                    return true;
                }
                break;
            case 3:
                if (peutSeDeplacer(gridX + 1, gridY)) {
                    gridX++;
                    if (spriteRight != null) spriteActuelle = spriteRight;
                    mettreAJourPosition();
                    return true;
                }
                break;
        }
        return false;
    }

    private boolean peutSeDeplacer(int x, int y) {
        if (y < 0 || y >= map.length || x < 0 || x >= map[0].length) {
            return true;
        }
        return map[y][x] == 0;
    }

    private void mettreAJourPosition() {
        if (gridX < 0) {
            gridX = map[0].length - 1;
        } else if (gridX >= map[0].length) {
            gridX = 0;
        }

        int pixelX = offsetX + gridX * tailleCase;
        int pixelY = offsetY + gridY * tailleCase;
        Point pos = new Point(pixelX, pixelY);

        if (spriteActuelle != null) {
            spriteActuelle.setA(pos);
        }

        if (forme != null) {
            int centerX = pixelX + tailleCase / 2;
            int centerY = pixelY + tailleCase / 2;
            forme.setO(new Point(centerX, centerY));
        }
    }

    public Cercle getForme() {
        return forme;
    }

    public Texture getSprite() {
        return spriteActuelle;
    }

    public boolean hasSpriteActuelle() {
        return spriteActuelle != null;
    }

    public int getGridX() {
        return gridX;
    }
    
    public int getGridY() {
        return gridY;
    }

    public void perdreVie() {
        vies--;
    }
    
    public int getVies() {
        return vies;
    }
    
    public boolean estMort() {
        return vies <= 0;
    }

    public void ajouterScore(int points) {
        score += points;
    }
    
    public int getScore() {
        return score;
    }

    public void relancer(int newGridX, int newGridY) {
        gridX = newGridX;
        gridY = newGridY;
        mettreAJourPosition();
    }

    public void activerPowerUp() {
        powerUpActif = true;
        timerPowerUp = 100;
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

    public void activerInvulnerabilite(int dureeMs) {
        invulnerableUntilMs = System.currentTimeMillis() + dureeMs;
    }

    public boolean isInvulnerable() {
        return System.currentTimeMillis() < invulnerableUntilMs;
    }
}
