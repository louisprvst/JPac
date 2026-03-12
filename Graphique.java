import MG2D.*;
import MG2D.geometrie.*;

public class Graphique {
    
    private Fenetre f = new Fenetre("JPac", 720, 600);
    private int tailleCase = 25;
    private int offsetX;
    private int offsetY;
    private Player player;

    // 0 = chemin, 1 = mur
    private int[][] map = {
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,1,1,1,0,1,1,1,1,1,0,0,1,1,0,0,1,1,1,1,1,0,1,1,1,0,1},
        {1,0,1,1,1,0,1,1,1,1,1,0,0,1,1,0,0,1,1,1,1,1,0,1,1,1,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,0,1},
        {1,0,0,0,0,0,1,1,0,0,0,0,0,1,1,0,0,0,0,0,1,1,0,0,0,0,0,1},
        {1,1,1,1,1,0,1,1,1,1,1,0,0,1,1,0,0,1,1,1,1,1,0,1,1,1,1,1},
        {1,1,1,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,1,1,1,1,1},
        {1,1,1,1,1,0,1,1,0,1,1,1,0,0,0,0,1,1,1,0,1,1,0,1,1,1,1,1},
        {0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0},
        {1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1},
        {1,1,1,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,1,1,1},
        {1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,1,1,1,0,1,1,1,1,1,0,0,1,1,0,0,1,1,1,1,1,0,1,1,1,0,1},
        {1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1},
        {1,1,1,0,1,0,1,1,0,1,1,1,1,1,1,1,1,1,1,0,1,1,0,1,0,1,1,1},
        {1,0,0,0,0,0,1,1,0,0,0,0,0,1,1,0,0,0,0,0,1,1,0,0,0,0,0,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
    };

    public void createMap(){
        f.effacer();

        int nbLignes = map.length;
        int nbColonnes = map[0].length;

        // Centrer automatiquement la grille
        int largeurGrille = nbColonnes * tailleCase;
        int hauteurGrille = nbLignes * tailleCase;
        offsetX = (720 - largeurGrille) / 2;
        offsetY = (600 - hauteurGrille) / 2;

        for(int j = 0 ; j < nbLignes ; j++){
            for(int i = 0 ; i < nbColonnes ; i++){

                int x = offsetX + i * tailleCase ;
                int y = offsetY + j * tailleCase ;

                if(map[j][i] == 1){
                    // Mur bleu
                    Carre mur = new Carre(Couleur.BLEU,new Point(x,y),tailleCase,true);
                    f.ajouter(mur);
                }
                else{
                    // Passage gris/noir
                    Carre passage = new Carre(Couleur.NOIR,new Point(x,y),tailleCase,true);
                    f.ajouter(passage);
                }
            }
        }
        f.rafraichir();
    }
    
    // Créer le joueur à une position de départ
    public void creerPlayer(int startX, int startY) {
        player = new Player(startX, startY, map, tailleCase, offsetX, offsetY);
        f.ajouter(player.getForme());
    }
    
    // Rafraîchir l'affichage (à appeler après chaque déplacement)
    public void rafraichir() {
        f.rafraichir();
    }
    
    // Getters
    public Player getPlayer() {
        return player;
    }
    
    public int[][] getMap() {
        return map;
    }
    
    public Fenetre getFenetre() {
        return f;
    }

}