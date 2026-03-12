import MG2D.geometrie.*;

public class Player {
    
    // Position dans la grille (en cases, pas en pixels)
    private int gridX;
    private int gridY;
    
    // Référence à la map pour vérifier les collisions
    private int[][] map;
    
    // Constantes pour l'affichage
    private int tailleCase;
    private int offsetX;
    private int offsetY;
    
    // Forme graphique du joueur
    private Cercle forme;
    
    public Player(int startX, int startY, int[][] map, int tailleCase, int offsetX, int offsetY) {
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
    
    // Déplacement vers le haut
    public void deplacerHaut() {
        if (peutSeDeplacer(gridX, gridY + 1)) {
            gridY++;
            mettreAJourPosition();
        }
    }
    
    // Déplacement vers le bas
    public void deplacerBas() {
        if (peutSeDeplacer(gridX, gridY - 1)) {
            gridY--;
            mettreAJourPosition();
        }
    }
    
    // Déplacement vers la gauche
    public void deplacerGauche() {
        if (peutSeDeplacer(gridX - 1, gridY)) {
            gridX--;
            mettreAJourPosition();
        }
    }
    
    // Déplacement vers la droite
    public void deplacerDroite() {
        if (peutSeDeplacer(gridX + 1, gridY)) {
            gridX++;
            mettreAJourPosition();
        }
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
}
