import MG2D.*;
import MG2D.geometrie.*;
import java.awt.Font;
import java.awt.Color;

public class Menu {
    
    private Fenetre f;
    private ClavierBorneArcade clavier;
    private boolean playPressed = false;
    
    private int largeurBouton = 350;
    private int hauteurBouton = 90;
    private int margeSelection = 18;
    
    public Menu(Fenetre f, ClavierBorneArcade clavier) {
        this.f = f;
        this.clavier = clavier;
    }
    
    public void afficher() {
        f.effacer();
        f.setBackground(Color.BLACK);
        
        // Fond noir
        Carre fond = new Carre(Couleur.NOIR, new Point(360, 300), 720, true);
        f.ajouter(fond);
        
        // === DÉCORATION: Petits fantômes en haut ===
        // Fantôme rouge à gauche
        Cercle fantomeRouge = new Cercle(Couleur.ROUGE, new Point(100, 60), 25, true);
        f.ajouter(fantomeRouge);
        
        // Fantôme bleu au centre
        Cercle fantomeBleu = new Cercle(Couleur.BLEU, new Point(360, 50), 28, true);
        f.ajouter(fantomeBleu);
        
        // Fantôme orange à droite
        Cercle fantomeOrange = new Cercle(Couleur.ORANGE, new Point(620, 60), 25, true);
        f.ajouter(fantomeOrange);
        
        // === TITRE "JPac" - ÉNORME et en JAUNE ===
        Font fontTitre = new Font("Arial", Font.BOLD, 140);
        Texte titre = new Texte(Couleur.JAUNE, "JPac", fontTitre, new Point(150, 95));
        f.ajouter(titre);
        
        // === Sous-titre descriptif ===
        Font fontDesc = new Font("Arial", Font.BOLD, 22);
        Texte sousTitre = new Texte(Couleur.CYAN, "Eat the Pellets • Avoid the Ghosts", fontDesc, new Point(80, 240));
        f.ajouter(sousTitre);
        
        // === BOUTON PLAY avec design comme Pong ===
        int xBouton = (720 - largeurBouton) / 2;
        int yBouton = 320;
        
        // Fond blanc du bouton
        Rectangle boutonPlay = new Rectangle(Couleur.BLANC, new Point(xBouton, yBouton), largeurBouton, hauteurBouton, true);
        f.ajouter(boutonPlay);
        
        // Sélection (rectangle rouge épais autour du bouton)
        Rectangle selection = new Rectangle(Couleur.ROUGE, new Point(xBouton - margeSelection, yBouton - margeSelection), 
                largeurBouton + 2*margeSelection, hauteurBouton + 2*margeSelection, false);
        f.ajouter(selection);
        
        // Texte du bouton PLAY en noir
        Font fontBouton = new Font("Arial", Font.BOLD, 60);
        Texte textPlay = new Texte(Couleur.NOIR, "PLAY", fontBouton, new Point(250, 325));
        f.ajouter(textPlay);
        
        // === Petites pellets autour du titre pour la décoration ===
        for (int i = 0; i < 5; i++) {
            Cercle pellet = new Cercle(Couleur.JAUNE, new Point(50 + i * 140, 290), 4, true);
            f.ajouter(pellet);
        }
        
        // === INSTRUCTIONS en bas ===
        Font fontInstructions = new Font("Arial", Font.PLAIN, 15);
        Texte instr1 = new Texte(Couleur.ROSE, "Press Y button to start the game", fontInstructions, new Point(185, 460));
        f.ajouter(instr1);
        
        Texte instr2 = new Texte(Couleur.CYAN, "Arrow keys: Move  |  Eat pellets for points", fontInstructions, new Point(110, 485));
        f.ajouter(instr2);
        
        Texte instr3 = new Texte(Couleur.ORANGE, "Yellow circles = Power-ups (10 sec invincibility)", fontInstructions, new Point(85, 510));
        f.ajouter(instr3);
        
        Texte instr4 = new Texte(Couleur.BLANC, "Eat ghosts during power-up = +200 points", fontInstructions, new Point(130, 535));
        f.ajouter(instr4);
        
        f.rafraichir();
    }
    
    public void gererEntree() {
        // Bouton Y pour lancer le jeu (comme Pong)
        if (clavier.getBoutonJ2YTape()) {
            playPressed = true;
        }
    }
    
    public boolean isPlayPressed() {
        return playPressed;
    }
    
    public void reset() {
        playPressed = false;
    }
}
