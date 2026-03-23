import MG2D.*;
import MG2D.geometrie.*;
import java.awt.Font;
import java.awt.Color;

public class SelectionDifficulte {
    
    private Fenetre f;
    private ClavierBorneArcade clavier;
    private int selection = 0;
    private int difficulteChoisie = -1;
    
    private int largeurBouton = 420;
    private int hauteurBouton = 85;
    private int margeSelection = 16;
    
    public SelectionDifficulte(Fenetre f, ClavierBorneArcade clavier) {
        this.f = f;
        this.clavier = clavier;
    }
    
    public void afficher() {
        f.effacer();
        f.setBackground(Color.BLACK);
        
        // Titre "CHOOSE DIFFICULTY"
        Font fontTitre = new Font("Arial", Font.BOLD, 80);
        Texte titre = new Texte(Couleur.JAUNE, "DIFFICULTY", fontTitre, new Point(100, 60));
        f.ajouter(titre);
        
        // Décoration: fantômes
        Cercle fantomeBleu = new Cercle(Couleur.BLEU, new Point(620, 70), 30, true);
        f.ajouter(fantomeBleu);
        
        // === BOUTON 1: EASY ===
        int xBouton = (720 - largeurBouton) / 2;
        int yBouton1 = 160;
        
        // Fond blanc
        Rectangle btn1 = new Rectangle(Couleur.BLANC, new Point(xBouton, yBouton1), largeurBouton, hauteurBouton, true);
        f.ajouter(btn1);
        
        // Sélection si c'est le bouton actuel
        if (selection == 0) {
            Rectangle sel1 = new Rectangle(Couleur.ROUGE, new Point(xBouton - margeSelection, yBouton1 - margeSelection), 
                    largeurBouton + 2*margeSelection, hauteurBouton + 2*margeSelection, false);
            f.ajouter(sel1);
        }
        
        // Texte du bouton EASY
        Font fontBouton = new Font("Arial", Font.BOLD, 50);
        Texte textEasy = new Texte(Couleur.NOIR, "EASY", fontBouton, new Point(260, 165));
        f.ajouter(textEasy);
        
        // Description EASY
        Font fontDesc = new Font("Arial", Font.PLAIN, 13);
        Texte descEasy = new Texte(Couleur.VERT, "All ghosts move randomly", fontDesc, new Point(220, 200));
        f.ajouter(descEasy);
        
        // === BOUTON 2: MEDIUM ===
        int yBouton2 = 300;
        
        // Fond blanc
        Rectangle btn2 = new Rectangle(Couleur.BLANC, new Point(xBouton, yBouton2), largeurBouton, hauteurBouton, true);
        f.ajouter(btn2);
        
        // Sélection si c'est le bouton actuel
        if (selection == 1) {
            Rectangle sel2 = new Rectangle(Couleur.ROUGE, new Point(xBouton - margeSelection, yBouton2 - margeSelection), 
                    largeurBouton + 2*margeSelection, hauteurBouton + 2*margeSelection, false);
            f.ajouter(sel2);
        }
        
        // Texte du bouton MEDIUM
        Texte textMedium = new Texte(Couleur.NOIR, "MEDIUM", fontBouton, new Point(215, 305));
        f.ajouter(textMedium);
        
        // Description MEDIUM
        Texte descMedium = new Texte(Couleur.ORANGE, "2 ghosts chase, 2 are random", fontDesc, new Point(190, 340));
        f.ajouter(descMedium);
        
        // === BOUTON 3: HARD ===
        int yBouton3 = 440;
        
        // Fond blanc
        Rectangle btn3 = new Rectangle(Couleur.BLANC, new Point(xBouton, yBouton3), largeurBouton, hauteurBouton, true);
        f.ajouter(btn3);
        
        // Sélection si c'est le bouton actuel
        if (selection == 2) {
            Rectangle sel3 = new Rectangle(Couleur.ROUGE, new Point(xBouton - margeSelection, yBouton3 - margeSelection), 
                    largeurBouton + 2*margeSelection, hauteurBouton + 2*margeSelection, false);
            f.ajouter(sel3);
        }
        
        // Texte du bouton HARD
        Texte textHard = new Texte(Couleur.NOIR, "HARD", fontBouton, new Point(250, 445));
        f.ajouter(textHard);
        
        // Description HARD
        Texte descHard = new Texte(Couleur.ROUGE, "All 4 ghosts chase you relentlessly", fontDesc, new Point(175, 480));
        f.ajouter(descHard);
        
        // Instructions en bas
        Font fontInstr = new Font("Arial", Font.PLAIN, 14);
        Texte instr = new Texte(Couleur.CYAN, "UP/DOWN to select  |  Press Y to confirm", fontInstr, new Point(130, 550));
        f.ajouter(instr);
        
        f.rafraichir();
    }
    
    public void gererEntree() {
        // Flèche haut pour monter dans la sélection
        if (clavier.getJoyJ1HautTape()) {
            if (selection > 0) {
                selection--;
            }
        }
        
        // Flèche bas pour descendre dans la sélection
        if (clavier.getJoyJ1BasTape()) {
            if (selection < 2) {
                selection++;
            }
        }
        
        // Bouton Y pour confirmer la sélection
        if (clavier.getBoutonJ2YTape()) {
            difficulteChoisie = selection;
        }
    }
    
    public int getDifficulteChoisie() {
        return difficulteChoisie;
    }
    
    public void reset() {
        selection = 0;
        difficulteChoisie = -1;
    }
}
