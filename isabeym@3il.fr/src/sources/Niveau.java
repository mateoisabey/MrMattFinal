package sources;

import objets.EtatRocher;
import objets.ObjetPlateau;
import objets.Pomme;
import objets.Rocher;
import objets.Vide;
public class Niveau {

    // Les objets sur le plateau du niveau
    private ObjetPlateau[][] plateau;
    // Position du joueur
    private int joueurX;
    private int joueurY;
    private Niveau changerNiveau;
    private boolean aGagne;
    private boolean aPerdu;
    private int nbDeplacement;
    private boolean intermediaire;
    private int nbPommeRestante;


    // Autres attributs que vous jugerez nécessaires...

    /**
     * Constructeur public : crée un niveau vide
     * @author MateoIsabey
     */
    private Niveau() {
        this.plateau = new ObjetPlateau[0][0];
        this.intermediaire = false;
        this.nbPommeRestante = 0;
        this.nbDeplacement = 0;
    }
    /**
     * Constructeur public : crée un niveau depuis un fichier.
     * @param chemin
     * @author MateoIsabey
     */
    public Niveau(String chemin) {
        this();
        this.chargerNiveau(chemin);
    }


    /**
     * @method chargerNiveau
     * @param chemin
     * @author MateoIsabey
     *
     * Permet de charger les niveaux
     */
    public void chargerNiveau(String chemin) {
        String fichNiv = Utils.lireFichier(chemin);
        String[] tabLigne = fichNiv.split("\n");
        int tailleH = Integer.valueOf(tabLigne[0]);
        int tailleV = Integer.valueOf(tabLigne[1]);
        this.plateau = new ObjetPlateau[tailleH][tailleV];
        int curr1 = 0;
        int curr2 = 0;
        for (int i = 2; i < tabLigne.length; i++) {
            for (int j = 0; j < tabLigne[i].length(); j++){
                char currChar = tabLigne[i].charAt(j);
                ObjetPlateau obj = null;
                if ("-H#+* ".indexOf(currChar) >= 0){
                    obj = ObjetPlateau.depuisCaractere(currChar);
                }
                 if (currChar == 'H') {
                     this.joueurX = curr1;
                     this.joueurY = curr2;
                 }
                 if (currChar == '+') {
                     this.nbPommeRestante++;
                 }
                 this.plateau[curr1++][curr2] = obj;
            }
            curr1 = 0;
            curr2++ ;
        }

    }

    /**
     * @method chargerNiveau
     * @param sourceX
     * @param sourceY
     * @param destinationX
     * @param destinationY
     * @author MateoIsabey
     *
     * Permet d"echanger les position de deux element.
     */
    private void echanger(int sourceX, int sourceY, int destinationX, int destinationY) {
        ObjetPlateau temp = this.plateau[sourceX][sourceY];
        this.plateau[sourceX][sourceY] = this.plateau[destinationX][destinationY];
        this.plateau[destinationX][destinationY] = temp;
    }


    /**
     * @method Afficher
     * Produit une sortie du niveau sur la sortie standard.
     * ................
     */
    public void afficher() {
        for (int j = 0; j < this.plateau[0].length; j++ ) {
            for (int i = 0; i < this.plateau.length; i++) {
                System.out.print(this.plateau[i][j].afficher());
            }
            System.out.println();

        }
        System.out.println("Pommes restantes : " + this.nbPommeRestante);
        System.out.println("Déplacements : " + this.nbDeplacement);
    }

    // TODO : patron visiteur du Rocher...
    /**
     * @method etatSuivantVisiteur
     * permets d'implémenter le patron visiteur
     * ................
     */
    public void etatSuivantVisiteur(Rocher r, int x, int y) {
        switch (r.getEtatRocher()){

            case CHUTE :
                if (y < this.plateau[x].length - 1) {
                    if (this.plateau[x][y + 1].estVide()) {
                        if(y + 1 < this.joueurY && x == this.joueurX){
                            this.echanger(x, y ,x, y +1);
                            this.aPerdu=true;
                            this.aGagne=false;
                            this.intermediaire = false;
                            break;
                        }
                        this.echanger(x, y ,x, y +1);
                        return;
                    } else if (this.plateau[x][y + 1].estGlissant()) {
                        if (x > 0  && this.plateau[x - 1][y + 1].estVide()) {
                            this.echanger(x, y ,x - 1, y +1);
                            return;
                        } else if (x < this.plateau.length - 1 && this.plateau[x +1][y + 1].estVide()) {
                            this.echanger(x, y ,x + 1, y +1);
                            return;
                        }
                    }

                }
                r.setEtatRocher(EtatRocher.FIXE);

            case FIXE :
                if(y < this.plateau[x].length - 1  && this.plateau[x][y + 1].estVide()) {
                    r.setEtatRocher(EtatRocher.CHUTE);
                }
                break;

        }
        this.intermediaire = (r.getEtatRocher() == EtatRocher.CHUTE || this.intermediaire);
    }

    /**
     * @method EtatSuivant
     * Calcule l'état suivant du niveau
     * @author MateoIsabey
     */
    public void etatSuivant() {
        this.intermediaire = false;
        for(int i = this.plateau.length - 1; i >= 0; i--) {
            for(int j = this.plateau[i].length - 1; j >= 0; j--) {
                this.plateau[i][j].visiterPlateauCalculEtatSuivant(this, i, j);
            }
        }

        this.aGagne = this.nbPommeRestante == 0;
    }


    // Illustrez les Javadocs manquantes lorsque vous coderez ces méthodes !

    /**
     * @method enCours
     * definit si une partie est en cours
     * @author MateoIsabey
     */
    public boolean enCours() {
        return !(this.aGagne || this.aPerdu);
    }

    /**
     * @method deplacer
     * @param x
     * @param y
     * permet de déplacer le joueur
     * @author MateoIsabey
     */
    public void deplacer(int x, int y) {
        int tempX = this.joueurX + x;
        int tempY = this.joueurY + y;
        this.nbDeplacement++;

        if (this.plateau[tempX][tempY].estMarchable()) {
            if (this.plateau[tempX][tempY].afficher() == "+") {
                this.nbPommeRestante--;
            }
            if (!(this.plateau[tempX][tempY].estVide())) {
                this.plateau[tempX][tempY] = new Vide();
            }

            this.echanger(tempX,tempY,this.joueurX, this.joueurY);

        } else if (this.plateau[tempX][tempY].estPoussable()) {

            int tempP = this.joueurX + x * 2;
            this.echanger(tempX,tempY,tempP, tempY);
            this.echanger(tempX,tempY, this.joueurX, this.joueurY);

        }
        this.joueurX = tempX;
        this.joueurY = tempY;
    }

    /**
     * @method deplacementPossible
     * @param x
     * @param y
     * verifie si un deplacement et possible
     * @return boolean
     * @author MateoIsabey
     */
    private boolean deplacementPossible(int x, int y) {
        int tempX = this.joueurX + x;
        int tempY = this.joueurY + y;

        if ((x != 0 || y != 0) && tempX <= this.plateau.length - 1 && tempX >= 0 && tempY <= this.plateau[0].length - 1 && tempY >= 0) {
            if (this.plateau[tempX][tempY].estMarchable()) {
                return true;
            } else if (this.plateau[tempX][tempY].estPoussable() && y == 0) {
                int tempP = this.joueurX + x * 2;
                if ((tempP >= 0 || tempP <= this.plateau.length - 1) && this.plateau[tempP][tempY].estVide()) {
                    return true;
                }
            }
        }
        return false;

    }

    // Joue la commande C passée en paramètres
    /**
     * @method deplacementPossible
     * @param c
     * permet de faire jouer le joueur (lancer les commandes)
     * @return boolean
     * @author MateoIsabey
     */
    public boolean jouer(Commande c) {
        boolean result = true;
        switch (c) {
            case HAUT: {
                result = this.deplacementPossible(- 1, 0);
                if (result) {
                    this.deplacer(- 1, 0);
                }
                break;
            }
            case BAS: {
                System.out.println(this.plateau[this.joueurX][this.joueurY + 1].afficher());
                result = this.deplacementPossible(1, 0);
                if (result) {
                    this.deplacer(1, 0);
                }
                break;
            }
            case GAUCHE: {
                result = this.deplacementPossible(0, - 1);
                if (result) {
                    this.deplacer(0, - 1);
                }
                break;
            }

            case DROITE: {
                result = this.deplacementPossible(0, 1);
                if (result) {
                    this.deplacer(0, 1);
                }
                break;
            }
            case QUITTER: {
                this.aPerdu = true;
                this.afficherEtatFinal();
                break;
            }
            case ANNULER: {
                //todo
            }
            default: {
                System.out.println("Choix incorrect");
                break;
            }

        }
        return result;
    }
    /**
     * @method afficherEtatFinal
     * permet d'afficher si la partie est gagner ou perdu
     * @author MateoIsabey
     */
    public void afficherEtatFinal() {
        if (this.aGagne) {
            System.out.println("C'est gagné");
        } else if (this.aPerdu) {
            System.out.println("NOOOOOOOON c'est perdu");
        }

    }

    /**
     * @method estIntermediaire
     * permet de rendre la main a l'utilisateur au bon moment
     */
    public boolean estIntermediaire() {
        return this.enCours() && this.intermediaire;
    }



}
