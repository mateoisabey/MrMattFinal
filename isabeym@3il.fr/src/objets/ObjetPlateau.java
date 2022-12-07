package objets;
/**
 * @author MateoIsabey
 */
import sources.Niveau;

public abstract class ObjetPlateau {
    /**
     * Fabrique des objets
     * @param chr le symbole à produire
     * @return la classe ObjetPlateau correspondante
     */
    public static ObjetPlateau depuisCaractere(char chr) {
        ObjetPlateau nouveau = null;
        switch(chr) {
            case '-':
                nouveau = new Herbe();
                break;
            case '+':
                nouveau = new Pomme();
                break;
            case '*':
                nouveau = new Rocher();
                break;
            case ' ':
                nouveau = new Vide();
                break;
            case '#':
                nouveau = new Mur();
                break;
            case 'H':
                nouveau = new Joueur();
                break;
        }
        return nouveau;
    }
    /// Autres fonctions à réaliser ici...
    public abstract String afficher();

    public abstract boolean estVide();
    public abstract boolean estMarchable() ;

    public abstract boolean estPoussable();

    public abstract boolean estGlissant() ;

    public  void visiterPlateauCalculEtatSuivant(Niveau niveau, int x, int y) {};
}
