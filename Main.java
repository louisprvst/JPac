public class Main {

    public static void main(String[] args) {

        // Créer et afficher la map
        Graphique g = new Graphique();
        g.createMap();
        
        // Créer le joueur à une position de départ
        g.creerPlayer(1, 1);
        g.rafraichir();
        
        // Récupérer le clavier de la borne
        ClavierBorneArcade clavier = new ClavierBorneArcade();
        g.getFenetre().addKeyListener(clavier);
        
        // Boucle de jeu
        while(true) {
            
            // Déplacement
            if(clavier.getJoyJ1HautTape()) {
                g.getPlayer().deplacerHaut();
                g.rafraichir();
            }
            else if(clavier.getJoyJ1BasTape()) {
                g.getPlayer().deplacerBas();
                g.rafraichir();
            }
            else if(clavier.getJoyJ1GaucheTape()) {
                g.getPlayer().deplacerGauche();
                g.rafraichir();
            }
            else if(clavier.getJoyJ1DroiteTape()) {
                g.getPlayer().deplacerDroite();
                g.rafraichir();
            }
            
            // Attendre un peu pour limiter la vitesse de déplacement
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}