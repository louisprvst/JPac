import MG2D.*;
import MG2D.geometrie.*;
import java.util.Random;

public class Fantome {

    private int gridX;
    private int gridY;

    private int[][] map;

    private int tailleCase;
    private int offsetX;
    private int offsetY;

    private Texture spriteNormal;
    private Texture spriteBleu;
    private Texture spriteActuelle;

    private Cercle forme;

    private Couleur couleur;

    private Random random;
    private int compteur = 0;
    private int delaiDeplacement;
    private int directionCourante = -1;
    
    public Fantome(int startX, int startY, int[][] map, int tailleCase, int offsetX, int offsetY, Couleur couleur, String ghostName) {
        this(startX, startY, map, tailleCase, offsetX, offsetY, couleur, ghostName, true);
    }
    
    public Fantome(int startX, int startY, int[][] map, int tailleCase, int offsetX, int offsetY, Couleur couleur, String ghostName, boolean poursuiteActive) {
        this.gridX = startX;
        this.gridY = startY;
        this.map = map;
        this.tailleCase = tailleCase;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.couleur = couleur;
        this.random = new Random();
        this.delaiDeplacement = random.nextInt(2) + 2;

        int pixelX = offsetX + gridX * tailleCase;
        int pixelY = offsetY + gridY * tailleCase;
        Point pos = new Point(pixelX, pixelY);

        try {
            this.spriteNormal = new Texture("assets/" + ghostName + ".png", pos);
            this.spriteBleu = new Texture("assets/blue_ghost.png", pos);
            this.spriteActuelle = this.spriteNormal;
        } catch (Exception e) {
            System.out.println("Erreur chargement sprite fantôme " + ghostName + ": " + e.getMessage());
            int centerX = pixelX + tailleCase / 2;
            int centerY = pixelY + tailleCase / 2;
            this.forme = new Cercle(couleur, new Point(centerX, centerY), tailleCase / 2, true);
        }
    }

    public void bouger(int playerX, int playerY, boolean playerPowerUp) {
        compteur++;

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

        if (compteur >= delaiDeplacement) {
            compteur = 0;

            int direction = choisirDirectionAleatoire();

            if (direction != -1) {
                switch(direction) {
                    case 0:
                        if (peutSeDeplacer(gridX, gridY - 1)) {
                            gridY--;
                        }
                        break;
                    case 1:
                        if (peutSeDeplacer(gridX, gridY + 1)) {
                            gridY++;
                        }
                        break;
                    case 2:
                        if (peutSeDeplacer(gridX - 1, gridY)) {
                            gridX--;
                        }
                        break;
                    case 3:
                        if (peutSeDeplacer(gridX + 1, gridY)) {
                            gridX++;
                        }
                        break;
                }

                directionCourante = direction;
                mettreAJourPosition();
            }
        }
    }

    private int choisirDirectionAleatoire() {
        int[] directionsValides = new int[4];
        int nbDirectionsValides = 0;

        for (int d = 0; d < 4; d++) {
            if (peutSeDeplacer(gridX + getDeltaX(d), gridY + getDeltaY(d))) {
                directionsValides[nbDirectionsValides] = d;
                nbDirectionsValides++;
            }
        }

        if (nbDirectionsValides == 0) {
            return -1;
        }

        int directionOpposee = directionOpposee(directionCourante);
        if (nbDirectionsValides > 1 && directionOpposee != -1) {
            int[] directionsSansDemiTour = new int[4];
            int nbSansDemiTour = 0;
            for (int i = 0; i < nbDirectionsValides; i++) {
                if (directionsValides[i] != directionOpposee) {
                    directionsSansDemiTour[nbSansDemiTour] = directionsValides[i];
                    nbSansDemiTour++;
                }
            }
            if (nbSansDemiTour > 0) {
                directionsValides = directionsSansDemiTour;
                nbDirectionsValides = nbSansDemiTour;
            }
        }

        if (directionCourante != -1) {
            for (int i = 0; i < nbDirectionsValides; i++) {
                if (directionsValides[i] == directionCourante && random.nextInt(100) < 70) {
                    return directionCourante;
                }
            }
        }

        return directionsValides[random.nextInt(nbDirectionsValides)];
    }

    private int directionOpposee(int direction) {
        if (direction == 0) return 1;
        if (direction == 1) return 0;
        if (direction == 2) return 3;
        if (direction == 3) return 2;
        return -1;
    }
    
    private int getDeltaX(int direction) {
        if (direction == 2) return -1;
        if (direction == 3) return 1;
        return 0;
    }

    private int getDeltaY(int direction) {
        if (direction == 0) return -1;
        if (direction == 1) return 1;
        return 0;
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

    public boolean attrapJoueur(Player joueur) {
        return gridX == joueur.getGridX() && gridY == joueur.getGridY();
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

    public void respawn() {
        gridX = 14;
        gridY = 9;
        mettreAJourPosition();
    }
}