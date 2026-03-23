import MG2D.*;
import MG2D.geometrie.*;
import java.awt.Font;

public class Affichage {
    
    private Fenetre fenetre;
    private Font maPolice;
    
    public Affichage(Fenetre fenetre) {
        this.fenetre = fenetre;
        this.maPolice = new Font("Arial", Font.PLAIN, 20);
    }
    
    // Affiche les vies en haut à gauche sous forme de cercles jaunes
    public void afficherVies(int nbVies) {
        // Afficher les vies comme des petits cercles jaunes
        for(int i = 0; i < nbVies; i++) {
            Cercle vie = new Cercle(Couleur.JAUNE, new Point(20 + i * 30, 20), 8, true);
            fenetre.ajouter(vie);
        }
    }
    
    // Affiche le score en haut à droite avec du texte
    public void afficherScore(int score) {
        // Afficher le score en blanc avec une police normale
        Texte textScore = new Texte(Couleur.BLANC, "Score: " + score, maPolice, new Point(550, 30));
        fenetre.ajouter(textScore);
    }
    
    // Affiche un message de game over avec des carrés
    public void afficherGameOver() {
        // Fond noir
        Carre fond = new Carre(Couleur.NOIR, new Point(100, 150), 520, true);
        fenetre.ajouter(fond);
        
        // Bordure rouge
        Carre bordure = new Carre(Couleur.ROUGE, new Point(100, 150), 520, false);
        fenetre.ajouter(bordure);
    }
}
