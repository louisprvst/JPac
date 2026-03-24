import MG2D.*;
import MG2D.geometrie.*;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HighScoreManager {

    private static final int TAILLE_STOCKAGE = 10;
    private static final String FICHIER_HIGHSCORE = "highscore";
    private static final int LARGEUR_FENETRE = 720;
    private static final int HAUTEUR_FENETRE = 600;
    private static final int X_CENTRE = LARGEUR_FENETRE / 2;

    public static boolean estUnTopScore(int score) {
        int[] scores = lireTopScores();
        for (int i = 0; i < TAILLE_STOCKAGE; i++) {
            if (scores[i] == Integer.MIN_VALUE || score > scores[i]) {
                return true;
            }
        }
        return false;
    }

    public static String demanderPseudo(ClavierBorneArcade clavier, int scoreFinal) {
        Fenetre fPseudo = new Fenetre("New High Score", 720, 600);
        fPseudo.addKeyListener(clavier);
        char[] lettres = {'A', 'A', 'A'};
        int index = 0;

        while (true) {
            fPseudo.effacer();
            fPseudo.setBackground(Color.BLACK);
            fPseudo.ajouter(new Carre(Couleur.NOIR, new Point(X_CENTRE, HAUTEUR_FENETRE / 2), LARGEUR_FENETRE, true));

            Font titreFont = new Font("Arial", Font.BOLD, 48);
            fPseudo.ajouter(new Texte(Couleur.JAUNE, "NEW HIGH SCORE", titreFont, new Point(X_CENTRE, 520)));

            Font scoreFont = new Font("Arial", Font.BOLD, 30);
            fPseudo.ajouter(new Texte(Couleur.BLANC, "SCORE: " + scoreFinal, scoreFont, new Point(X_CENTRE, 455)));

            Font instrFont = new Font("Arial", Font.PLAIN, 20);
            fPseudo.ajouter(new Texte(Couleur.CYAN, "LEFT/RIGHT: LETTER   UP/DOWN: CHANGE   BUTTON 1: CONFIRM", instrFont, new Point(X_CENTRE, 110)));

            Font lettreFont = new Font("Arial", Font.BOLD, 90);
            int baseX = X_CENTRE - 135;
            int yLettres = 280;
            int espace = 135;
            for (int i = 0; i < 3; i++) {
                int x = baseX + i * espace;
                // La lettre active est mise en rouge pour simuler la roulette courante.
                Couleur c = (i == index) ? Couleur.ROUGE : Couleur.BLANC;
                fPseudo.ajouter(new Texte(c, String.valueOf(lettres[i]), lettreFont, new Point(x, yLettres)));
            }

            fPseudo.rafraichir();

            if (clavier.getJoyJ1GaucheTape()) {
                index = (index + 2) % 3;
            }
            if (clavier.getJoyJ1DroiteTape()) {
                index = (index + 1) % 3;
            }
            if (clavier.getJoyJ1HautTape()) {
                lettres[index] = lettreSuivante(lettres[index]);
            }
            if (clavier.getJoyJ1BasTape()) {
                lettres[index] = lettrePrecedente(lettres[index]);
            }
            if (clavier.getBoutonJ2YTape()) {
                fPseudo.fermer();
                return new String(lettres);
            }

            dormir(50);
        }
    }

    public static void insererScoreDansTop5(String pseudo, int score) {
        String[] pseudos = new String[TAILLE_STOCKAGE];
        int[] scores = new int[TAILLE_STOCKAGE];
        for (int i = 0; i < TAILLE_STOCKAGE; i++) {
            pseudos[i] = "AAA";
            scores[i] = Integer.MIN_VALUE;
        }
        lireTopComplet(pseudos, scores);

        for (int i = 0; i < TAILLE_STOCKAGE; i++) {
            if (scores[i] == Integer.MIN_VALUE || score > scores[i]) {
                // Decale le tableau vers le bas avant insertion au bon rang.
                for (int j = TAILLE_STOCKAGE - 1; j > i; j--) {
                    scores[j] = scores[j - 1];
                    pseudos[j] = pseudos[j - 1];
                }
                scores[i] = score;
                pseudos[i] = pseudo;
                break;
            }
        }

        ecrireTop(pseudos, scores);
    }

    private static char lettreSuivante(char c) {
        return (c == 'Z') ? 'A' : (char) (c + 1);
    }

    private static char lettrePrecedente(char c) {
        return (c == 'A') ? 'Z' : (char) (c - 1);
    }

    private static int[] lireTopScores() {
        int[] scores = new int[TAILLE_STOCKAGE];
        for (int i = 0; i < TAILLE_STOCKAGE; i++) {
            scores[i] = Integer.MIN_VALUE;
        }
        File fichier = new File(FICHIER_HIGHSCORE);
        if (!fichier.exists() || !fichier.isFile()) {
            return scores;
        }

        BufferedReader lecteur = null;
        try {
            lecteur = new BufferedReader(new FileReader(fichier));
            String ligne;
            int i = 0;
            while ((ligne = lecteur.readLine()) != null && i < TAILLE_STOCKAGE) {
                ligne = ligne.trim();
                if (ligne.length() == 0) {
                    continue;
                }
                int sep = ligne.lastIndexOf('-');
                if (sep <= 0 || sep >= ligne.length() - 1) {
                    continue;
                }
                try {
                    scores[i] = Integer.parseInt(ligne.substring(sep + 1).trim());
                    i++;
                } catch (NumberFormatException e) {
                    // Ligne invalide ignoree.
                }
            }
        } catch (IOException e) {
            // En cas d'erreur, on conserve les valeurs par defaut.
        } finally {
            if (lecteur != null) {
                try {
                    lecteur.close();
                } catch (IOException e) {
                    // Rien
                }
            }
        }
        return scores;
    }

    private static void lireTopComplet(String[] pseudos, int[] scores) {
        File fichier = new File(FICHIER_HIGHSCORE);
        if (!fichier.exists() || !fichier.isFile()) {
            return;
        }

        BufferedReader lecteur = null;
        try {
            lecteur = new BufferedReader(new FileReader(fichier));
            String ligne;
            int i = 0;
            while ((ligne = lecteur.readLine()) != null && i < TAILLE_STOCKAGE) {
                ligne = ligne.trim();
                if (ligne.length() == 0) {
                    continue;
                }

                int sep = ligne.lastIndexOf('-');
                if (sep <= 0 || sep >= ligne.length() - 1) {
                    continue;
                }

                String pseudo = ligne.substring(0, sep).trim().toUpperCase();
                if (pseudo.length() > 3) {
                    pseudo = pseudo.substring(0, 3);
                }

                try {
                    int score = Integer.parseInt(ligne.substring(sep + 1).trim());
                    pseudos[i] = pseudo;
                    scores[i] = score;
                    i++;
                } catch (NumberFormatException e) {
                    // Ligne invalide ignoree.
                }
            }
        } catch (IOException e) {
            // Lecture impossible: pas de mise a jour.
        } finally {
            if (lecteur != null) {
                try {
                    lecteur.close();
                } catch (IOException e) {
                    // Rien
                }
            }
        }
    }

    private static void ecrireTop(String[] pseudos, int[] scores) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(FICHIER_HIGHSCORE, false));
            for (int i = 0; i < TAILLE_STOCKAGE; i++) {
                if (scores[i] == Integer.MIN_VALUE) {
                    writer.write("AAA-0");
                } else {
                    writer.write(pseudos[i] + "-" + scores[i]);
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erreur ecriture highscore: " + e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    // Rien
                }
            }
        }
    }

    private static void dormir(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}