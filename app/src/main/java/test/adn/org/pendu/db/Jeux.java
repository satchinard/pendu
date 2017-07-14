package test.adn.org.pendu.db;

/**
 * Created by cagecfi on 14/07/2017.
 */

public class Jeux {

    private String pseudo;
    private String email;
    private String niveau;
    private String score;
    private String date;
    private String reussite;

    public Jeux() {
    }

    public Jeux(String pseudo, String email, String niveau, String score, String date, String reussite) {
        this.pseudo = pseudo;
        this.email = email;
        this.niveau = niveau;
        this.score = score;
        this.date = date;
        this.reussite = reussite;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReussite() {
        return reussite;
    }

    public void setReussite(String reussite) {
        this.reussite = reussite;
    }
}
