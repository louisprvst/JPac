import MG2D.*;
import MG2D.geometrie.*;
import java.awt.Font;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Menu {

    private static final int NOMBRE_SCORES_AFFICHES = 5;
    private static final int LARGEUR_FENETRE = 720;
    private static final int HAUTEUR_FENETRE = 600;
    private static final int X_CENTRE = LARGEUR_FENETRE / 2;
    
    private Fenetre f;
    private ClavierBorneArcade clavier;
    private boolean playPressed = false;
    private boolean quitPressed = false;
    
    public Menu(Fenetre f, ClavierBorneArcade clavier) {
        this.f = f;
        this.clavier = clavier;
    }
    
    public void afficher() {
        f.effacer();
        f.setBackground(Color.BLACK);
        
        Carre fond = new Carre(Couleur.NOIR, new Point(LARGEUR_FENETRE / 2, HAUTEUR_FENETRE / 2), LARGEUR_FENETRE, true);
        f.ajouter(fond);

        Font fontTitre = new Font("Arial", Font.BOLD, 110);
        String texteTitre = "JPAC";
        Texte titre = new Texte(Couleur.BLEU, texteTitre, fontTitre, new Point(X_CENTRE, 520));
        f.ajouter(titre);

        Font fontPlay = new Font("Arial", Font.BOLD, 30);
        String textePlay = "PRESS BUTTON 1 FOR PLAY";
        Texte textPlay = new Texte(Couleur.BLANC, textePlay, fontPlay, new Point(X_CENTRE, 396));
        f.ajouter(textPlay);

        Font fontQuit = new Font("Arial", Font.PLAIN, 20);
        Texte textQuit = new Texte(Couleur.CYAN, "PRESS F TO QUIT", fontQuit, new Point(X_CENTRE, 360));
        f.ajouter(textQuit);

        String[] topScores = chargerTopScores();
        Font fontLigne = new Font("Arial", Font.BOLD, 28);
        int yPremierScore = 315;
        for (int i = 0; i < topScores.length; i++) {
            String ligne = (i + 1) + ". " + topScores[i];
            Texte scoreTexte = new Texte(Couleur.BLANC, ligne, fontLigne, new Point(X_CENTRE, yPremierScore - i * 45));
            f.ajouter(scoreTexte);
        }
        
        f.rafraichir();
    }

    private String[] chargerTopScores() {
        String[] top5 = new String[NOMBRE_SCORES_AFFICHES];
        for (int i = 0; i < NOMBRE_SCORES_AFFICHES; i++) {
            top5[i] = "AAA-SCORE";
        }
        File fichier = new File("highscore");

        if (fichier.exists() && fichier.isFile()) {
            BufferedReader lecteur = null;
            try {
                lecteur = new BufferedReader(new FileReader(fichier));
                String ligne;
                int index = 0;
                while ((ligne = lecteur.readLine()) != null && index < NOMBRE_SCORES_AFFICHES) {
                    ligne = ligne.trim();
                    if (ligne.length() == 0) {
                        continue;
                    }

                    // Le fichier highscore est deja trie, on lit dans l'ordre.
                    top5[index] = ligne.toUpperCase();
                    index++;
                }
            } catch (IOException e) {
                // En cas d'erreur de lecture, on garde simplement un top vide.
            } finally {
                if (lecteur != null) {
                    try {
                        lecteur.close();
                    } catch (IOException e) {
                        // Rien a faire.
                    }
                }
            }
        }

        return top5;
    }

    public void gererEntree() {
        if (clavier.getBoutonJ2YTape()) {
            playPressed = true;
        }
        if (clavier.getBoutonJ1ATape()) {
            quitPressed = true;
        }
    }
    
    public boolean isPlayPressed() {
        return playPressed;
    }

    public boolean isQuitPressed() {
        return quitPressed;
    }
    
    public void reset() {
        playPressed = false;
        quitPressed = false;
    }
}
