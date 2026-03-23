import MG2D.*;
import MG2D.geometrie.*;

public class AffichageChiffres {
    
    private Fenetre fenetre;
    
    public AffichageChiffres(Fenetre fenetre) {
        this.fenetre = fenetre;
    }
    
    // Affiche un chiffre sous forme de carrés simples
    private void afficherChiffre(int chiffre, int x, int y, Couleur couleur) {
        // Affiche chaque chiffre (0-9) sous forme simplifiée avec des petits carrés
        switch(chiffre) {
            case 0:
                afficherSegment(x, y, couleur, true, true, true, false, true, true, true);
                break;
            case 1:
                afficherSegment(x, y, couleur, false, true, false, false, true, false, false);
                break;
            case 2:
                afficherSegment(x, y, couleur, true, true, false, true, false, true, true);
                break;
            case 3:
                afficherSegment(x, y, couleur, true, true, false, true, true, false, true);
                break;
            case 4:
                afficherSegment(x, y, couleur, false, true, true, true, true, false, false);
                break;
            case 5:
                afficherSegment(x, y, couleur, true, false, true, true, true, false, true);
                break;
            case 6:
                afficherSegment(x, y, couleur, true, false, true, true, true, true, true);
                break;
            case 7:
                afficherSegment(x, y, couleur, true, true, false, false, true, false, false);
                break;
            case 8:
                afficherSegment(x, y, couleur, true, true, true, true, true, true, true);
                break;
            case 9:
                afficherSegment(x, y, couleur, true, true, true, true, true, false, true);
                break;
        }
    }
    
    // Affiche les segments d'un chiffre (représentation 7-segments simplifiée)
    private void afficherSegment(int x, int y, Couleur couleur, 
                                boolean haut, boolean topRight, boolean botRight,
                                boolean milieu, boolean topLeft, boolean botLeft, boolean bas) {
        int size = 3;
        
        if(haut)     fenetre.ajouter(new Cercle(couleur, new Point(x + 5, y), size, true));
        if(topRight) fenetre.ajouter(new Cercle(couleur, new Point(x + 10, y - 5), size, true));
        if(botRight) fenetre.ajouter(new Cercle(couleur, new Point(x + 10, y - 15), size, true));
        if(milieu)   fenetre.ajouter(new Cercle(couleur, new Point(x + 5, y - 10), size, true));
        if(topLeft)  fenetre.ajouter(new Cercle(couleur, new Point(x, y - 5), size, true));
        if(botLeft)  fenetre.ajouter(new Cercle(couleur, new Point(x, y - 15), size, true));
        if(bas)      fenetre.ajouter(new Cercle(couleur, new Point(x + 5, y - 20), size, true));
    }
    
    // Affiche un nombre entier
    public void afficherNombre(int nombre, int x, int y, Couleur couleur) {
        String str = String.valueOf(nombre);
        int xPos = x;
        
        for(char c : str.toCharArray()) {
            int chiffre = c - '0';
            afficherChiffre(chiffre, xPos, y, couleur);
            xPos += 20;
        }
    }
}
