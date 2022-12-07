package objets;
/**
 * @author MateoIsabey
 */
import sources.Niveau;

public class Pomme  extends ObjetPlateau {

    private final String caractere;

    public Pomme (){
        caractere = "+";
    }
    public String afficher() {
        return this.caractere;
    }

    public boolean estVide() {
            return false;
    }
    
    public boolean estMarchable() {
        return true;
    }

    public boolean estPoussable(){
        return false;
    }

    public boolean estGlissant(){
        return false;
    }

    public void visiterPlateauCalculEtatSuivant(Niveau niveau, int x, int y) {

    }
}