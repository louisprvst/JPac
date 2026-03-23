public class Main {

    public static void main(String[] args) {
        
        // Créer le clavier
        ClavierBorneArcade clavier = new ClavierBorneArcade();
        
        // Créer une fenetre temporaire pour les menus
        MG2D.Fenetre fMenus = new MG2D.Fenetre("JPac Menu", 720, 600);
        fMenus.addKeyListener(clavier);
        
        // === ÉCRAN D'ACCUEIL ===
        Menu menu = new Menu(fMenus, clavier);
        while(!menu.isPlayPressed()) {
            menu.afficher();
            menu.gererEntree();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        fMenus.fermer();
        
        // === SÉLECTION DE DIFFICULTÉ ===
        MG2D.Fenetre fDifficulty = new MG2D.Fenetre("JPac Difficulty", 720, 600);
        fDifficulty.addKeyListener(clavier);
        
        SelectionDifficulte selectionDifficute = new SelectionDifficulte(fDifficulty, clavier);
        while(selectionDifficute.getDifficulteChoisie() == -1) {
            selectionDifficute.afficher();
            selectionDifficute.gererEntree();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int difficulteChoisie = selectionDifficute.getDifficulteChoisie();
        System.out.println("Difficulté choisie: " + (difficulteChoisie == 0 ? "EASY" : difficulteChoisie == 1 ? "MEDIUM" : "HARD"));
        fDifficulty.fermer();
        
        // === DÉMARRER LE JEU ===
        // Créer et afficher la map avec la difficulté choisie
        Graphique g = new Graphique(difficulteChoisie);
        g.createMap();
        
        // Créer le joueur à une position de départ
        g.creerPlayer(1, 1);
        
        // Créer les fantômes (selon la difficulté)
        g.creerFantomes();
        
        // Créer les pellets
        g.creerPellets();
        
        // Créer les power-ups
        g.creerPowerUps();
        
        g.rafraichir();
        
        // Ajouter le keyboard listener à la fenetre du jeu
        g.getFenetre().addKeyListener(clavier);
        
        // Boucle de jeu
        while(true) {
            
            // Gérer les entrées clavier pour définir la direction désirée
            if(clavier.getJoyJ1HautTape()) {
                g.getPlayer().setDirectionDesiree(0); // 0 = haut
            }
            else if(clavier.getJoyJ1BasTape()) {
                g.getPlayer().setDirectionDesiree(1); // 1 = bas
            }
            else if(clavier.getJoyJ1GaucheTape()) {
                g.getPlayer().setDirectionDesiree(2); // 2 = gauche
            }
            else if(clavier.getJoyJ1DroiteTape()) {
                g.getPlayer().setDirectionDesiree(3); // 3 = droite
            }
            
            // Effectuer le mouvement automatique du joueur
            g.getPlayer().bouger();
            g.rafraichir();
            
            // Vérifier si le joueur a mangé des pellets
            int pointsGagnes = g.verifierPellets();
            if(pointsGagnes > 0) {
                g.getPlayer().ajouterScore(pointsGagnes);
                System.out.println("Pellet mangé ! +"+pointsGagnes+" points. Score total: "+g.getPlayer().getScore());
            }
            
            // Vérifier si tous les pellets ont été mangés
            if(g.tousLesPelletsMange()) {
                System.out.println("Tous les pellets ont été mangés ! Respawn des pellets...");
                g.respawnPellets();
            }
            
            // Vérifier si le joueur a mangé un power-up
            if(g.verifierPowerUps()) {
                System.out.println("Power-up mangé ! Mode invincible pendant 10 secondes ! Les fantômes fuient maintenant !");
            }
            
            // Mettre à jour l'état du power-up
            g.getPlayer().mettreAJourPowerUp();
            
            // Bouger les fantômes (avec vérification du power-up)
            for(Fantome fantome : g.getFantomes()) {
                fantome.bouger(g.getPlayer().getGridX(), g.getPlayer().getGridY(), g.getPlayer().isPowerUpActif());
            }
            g.rafraichir();
            
            // Vérifier les collisions avec les fantômes
            int pointsFantome = g.verifierCollisionsFantomes();
            if(pointsFantome > 0) {
                g.getPlayer().ajouterScore(pointsFantome);
                System.out.println("Fantôme mangé ! +"+pointsFantome+" points. Score total: "+g.getPlayer().getScore());
            }
            
            // Vérifier si le jeu est terminé (game over)
            if(g.getPlayer().estMort()) {
                System.out.println("GAME OVER ! Vous avez perdu toutes vos vies !");
                System.out.println("Score final: "+g.getPlayer().getScore());
                break;
            }
            
            // Attendre un peu pour limiter la vitesse de déplacement
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}