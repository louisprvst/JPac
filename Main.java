import MG2D.*;

public class Main {

    private static final int DELAI_BOUCLE_JEU_MS = 80;

    public static void main(String[] args) {
        ClavierBorneArcade clavier = new ClavierBorneArcade();

        while (true) {
            boolean lancerPartie = attendreLancementDepuisMenu(clavier);
            if (!lancerPartie) {
                return;
            }

            int scoreFinal = jouerPartie(clavier);
            System.out.println("GAME OVER ! Vous avez perdu toutes vos vies !");
            System.out.println("Score final: " + scoreFinal);

            // Seuls les scores qualifies ouvrent l'ecran de saisie pseudo.
            if (HighScoreManager.estUnTopScore(scoreFinal)) {
                String pseudo = HighScoreManager.demanderPseudo(clavier, scoreFinal);
                HighScoreManager.insererScoreDansTop5(pseudo, scoreFinal);
                System.out.println("Nouveau score enregistre: " + pseudo + "-" + scoreFinal);
            }
        }
    }

    private static boolean attendreLancementDepuisMenu(ClavierBorneArcade clavier) {
        Fenetre fMenus = new Fenetre("JPac Menu", 720, 600);
        fMenus.addKeyListener(clavier);

        Menu menu = new Menu(fMenus, clavier);
        menu.reset();
        while (!menu.isPlayPressed()) {
            menu.afficher();
            menu.gererEntree();
            if (menu.isQuitPressed()) {
                fMenus.fermer();
                return false;
            }
            dormir(50);
        }

        fMenus.fermer();
        return true;
    }

    private static int jouerPartie(ClavierBorneArcade clavier) {
        Graphique g = new Graphique();
        g.createMap();
        g.creerPlayer(1, 1);
        g.creerFantomes();
        g.creerPellets();
        g.creerPowerUps();
        g.rafraichir();
        g.getFenetre().addKeyListener(clavier);

        while (true) {
            if (clavier.getJoyJ1HautTape()) {
                g.getPlayer().setDirectionDesiree(0);
            } else if (clavier.getJoyJ1BasTape()) {
                g.getPlayer().setDirectionDesiree(1);
            } else if (clavier.getJoyJ1GaucheTape()) {
                g.getPlayer().setDirectionDesiree(2);
            } else if (clavier.getJoyJ1DroiteTape()) {
                g.getPlayer().setDirectionDesiree(3);
            }

            g.getPlayer().bouger();

            int pointsGagnes = g.verifierPellets();
            if (pointsGagnes > 0) {
                g.getPlayer().ajouterScore(pointsGagnes);
            }

            if (g.tousLesPelletsMange()) {
                g.respawnPellets();
                g.respawnPowerUps();
            }

            g.verifierPowerUps();
            g.getPlayer().mettreAJourPowerUp();

            for (Fantome fantome : g.getFantomes()) {
                fantome.bouger(g.getPlayer().getGridX(), g.getPlayer().getGridY(), g.getPlayer().isPowerUpActif());
            }

            int pointsFantome = g.verifierCollisionsFantomes();
            if (pointsFantome > 0) {
                g.getPlayer().ajouterScore(pointsFantome);
                System.out.println("Fantome mange ! +" + pointsFantome + " points. Score total: " + g.getPlayer().getScore());
            }

            g.rafraichir();

            if (g.getPlayer().estMort()) {
                int score = g.getPlayer().getScore();
                g.getFenetre().fermer();
                return score;
            }

            dormir(DELAI_BOUCLE_JEU_MS);
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