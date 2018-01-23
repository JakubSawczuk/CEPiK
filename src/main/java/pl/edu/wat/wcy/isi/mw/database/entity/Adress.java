package pl.edu.wat.wcy.isi.mw.database.entity;

import javax.persistence.*;

@Entity(name = "adress")
public class Adress {

    @GeneratedValue
    @Id
    private int idAdress;
    private String city;
    private String street;
    private String buildingNr;
    @Column(nullable = false)
    private int residenceNr;


    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getBuildingNr() {
        return buildingNr;
    }

    public int getResidenceNr() {
        return residenceNr;
    }

    public int getIdAdress() {
        return idAdress;
    }

    public void setIdAdress(int idAdress) {
        this.idAdress = idAdress;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setBuildingNr(String buildingNr) {
        this.buildingNr = buildingNr;
    }

    public void setResidenceNr(int residenceNr) {
        this.residenceNr = residenceNr;
    }
}
