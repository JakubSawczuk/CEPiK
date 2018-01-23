package pl.edu.wat.wcy.isi.mw.database.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "driver")
public class Driver
{
    @Id
    private String peselDrv;
    private String surnameDrv;
    private String firstNameDrv;
    private String secondNameDrv;

    @ManyToMany(mappedBy = "driverList")
    private List<Vehicle> vehiclesList = new ArrayList<Vehicle>();

    @ManyToOne
    @JoinColumn(name = "adress_IdAdress")
    private Adress adress;

    public String getPeselDrv() {
        return peselDrv;
    }

    public void setPeselDrv(String peselDrv) {
        this.peselDrv = peselDrv;
    }

    public String getSurnameDrv() {
        return surnameDrv;
    }

    public void setSurnameDrv(String surnameDrv) {
        this.surnameDrv = surnameDrv;
    }

    public String getFirstNameDrv() {
        return firstNameDrv;
    }

    public void setFirstNameDrv(String firstNameDrv) {
        this.firstNameDrv = firstNameDrv;
    }

    public String getSecondNameDrv() {
        return secondNameDrv;
    }

    public void setSecondNameDrv(String secondNameDrv) {
        this.secondNameDrv = secondNameDrv;
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }
}
