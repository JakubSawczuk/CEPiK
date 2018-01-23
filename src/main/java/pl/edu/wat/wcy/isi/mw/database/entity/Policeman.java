package pl.edu.wat.wcy.isi.mw.database.entity;

import javax.persistence.*;

@Entity(name = "policeman")
public class Policeman
{
    @Id
    private String peselPol;
    private String surnamePol;
    private String firstNamePol;
    private String secondNamePol;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "adress_IdAdress")
    Adress adress;

    public String getPeselPol() {
        return peselPol;
    }

    public void setPeselPol(String peselPol) {
        this.peselPol = peselPol;
    }

    public String getSurnamePol() {
        return surnamePol;
    }

    public void setSurnamePol(String surnamePol) {
        this.surnamePol = surnamePol;
    }

    public String getFirstNamePol() {
        return firstNamePol;
    }

    public void setFirstNamePol(String firstNamePol) {
        this.firstNamePol = firstNamePol;
    }

    public String getSecondNamePol() {
        return secondNamePol;
    }

    public void setSecondNamePol(String secondNamePol) {
        this.secondNamePol = secondNamePol;
    }
}
