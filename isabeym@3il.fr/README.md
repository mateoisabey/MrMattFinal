Question : pourquoi cette classe est-elle abstraite? (ObjectPlateau)
On ne peut pas instancier l'object ObjectPlateau

Question : pourquoi cette méthode est-elle privée? (private Niveau())
La methode est privé, car c'est un constructeur qui est appelé lorsque le vrai constructeur est appelé.


Question : pourquoi ces deux méthodes sont-elles privées? (methode de déplacement)
Elles ne sont appellé que dans la classe.

Question : quel est le problème d’une telle implémentation, d’après le cours?
Il faut eviter les "instanceOf" c'est indicateur d'une mauvaise implementation.

Question : pourquoi la méthode etatSuivant() est-elle publique?
Elle est publique car elle doit etre appelé dans la classe main(Jeu)

Implementation du modele observateur a ete compliqué pour moi je ne voyais pas l'utilité dans un premier temps.
Et la physique des rochers etait compliquer à conceptualiser.