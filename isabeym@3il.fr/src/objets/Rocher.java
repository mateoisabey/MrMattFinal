package objets;

/**
 * @author MateoIsabey
 */
import static objets.EtatRocher.CHUTE;
import static objets.EtatRocher.FIXE;

import sources.Niveau;
public class Rocher  extends ObjetPlateau {

    private final String caractere;
    private EtatRocher etatRocher;

    public Rocher(){
        caractere = "*";
        etatRocher = FIXE;
    }

    public String afficher() {
        return this.caractere;
    }

    public boolean estVide() {
            return false;
    }

    public boolean estMarchable() {
        return false;
    }

    public boolean estPoussable(){
        return true;
    }

    public boolean estGlissant(){
        return true;
    }



    public void visiterPlateauCalculEtatSuivant(Niveau niveau, int x, int y) {
            niveau.etatSuivantVisiteur(this,x,y);

    }

    public EtatRocher getEtatRocher() {
        return etatRocher;
    }

    public void setEtatRocher(EtatRocher etatRocher) {
        this.etatRocher = etatRocher;
    }
}