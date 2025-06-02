package Classes;

import java.util.ArrayList;
import Exceptions.FormationDejaInscriteException;

class Etudiant extends Utilisateur {
    private ArrayList<Formation> inscriptions = new ArrayList<>();

    Etudiant(String nom, String email, String motDePasse) {
        super(nom, email, motDePasse);

    }

    void sinsicrireFormation(Formation formation) {
        try {
            for (Formation f : inscriptions) {
                if (f.getTitre().equals(formation.getTitre())) {
                    throw new FormationDejaInscriteException(
                            "Formation already registered for: " + formation.getTitre());
                }
            }
            if (inscriptions.contains(formation)) {
                inscriptions.add(formation);
            }

        } catch (FormationDejaInscriteException e) {
            e.printStackTrace();
        }

    }
}