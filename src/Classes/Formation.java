package Classes;

public class Formation {
    private int id;  // Auto-generated ID from the database
    private String titre;
    private String description;
    private String formateur;
    private double prix;

    public Formation(int id, String titre, String description, double prix, String formateur) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.formateur = formateur;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFormateur() {
        return formateur;
    }

    public void setFormateur(String formateur) {
        this.formateur = formateur;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
}
