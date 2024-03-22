package Entite;

import java.util.Objects;

public class Client {
    private int IdClient, NumTel;
    private String Nom , Prenom, Adresse;
    public Client(){}

    public Client(int IdClient, String nom, String prenom, String adresse, int NumTel) {
        this.IdClient = IdClient;
        this.NumTel = NumTel;
        this.Nom = nom;
        this.Prenom = prenom;
        this.Adresse = adresse;
    }

    public int getIdClient() {
        return IdClient;
    }

    public void setIdClient(int IdClient) {
        this.IdClient = IdClient;
    }

    public int getNumTel() {
        return NumTel;
    }

    public void setNumTel(int Numtel) {
        NumTel = Numtel;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public String getAdresse() {
        return Adresse;
    }

    public void setAdresse(String adresse) {
        Adresse = adresse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client client)) return false;
        return IdClient == client.IdClient;
    }

    @Override
    public int hashCode() {
        return Objects.hash(IdClient);
    }

    @Override
    public String toString() {
        return "\n {" +
                "Cin=" + IdClient +
                ", Nom='" + Nom + '\'' +
                ", Prenom='" + Prenom + '\'' +
                ", Adresse='" + Adresse + '\'' +
                ", NumTel=" + NumTel +
                "}\n";
    }

}
