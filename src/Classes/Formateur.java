package Classes;

import java.util.ArrayList;

class Formateur extends Utilisateur {
    private ArrayList<Formation> formations = new ArrayList<>();

    Formateur(String nom, String email, String motDePasse) {
        super(nom, email, motDePasse);

    }

    void ajouterFormation(Formation formation) {
        formations.add(formation);
    }
}
