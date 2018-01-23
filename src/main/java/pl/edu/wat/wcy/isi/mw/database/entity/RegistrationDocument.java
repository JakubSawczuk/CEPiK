package pl.edu.wat.wcy.isi.mw.database.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Kuba on 2018-01-18.
 */
@Entity(name = "registrationdocument")
public class RegistrationDocument {


    @Id
    private int idAuth;

    private Timestamp dateAuth;
    private Timestamp expirationDateAuth;
    private String commentAuth;

    @ManyToOne
    @JoinColumn(name = "vin")
    private
    Vehicle vehicle;


    public int getIdAuth() {
        return idAuth;
    }

    public void setIdAuth(int idAuth) {
        this.idAuth = idAuth;
    }

    public Timestamp getDateAuth() {
        return dateAuth;
    }

    public void setDateAuth(Timestamp dateAuth) {
        this.dateAuth = dateAuth;
    }

    public Timestamp getExpirationDateAuth() {
        return expirationDateAuth;
    }

    public void setExpirationDateAuth(Timestamp expirationDateAuth) {
        this.expirationDateAuth = expirationDateAuth;
    }

    public String getCommentAuth() {
        return commentAuth;
    }

    public void setCommentAuth(String commentAuth) {
        this.commentAuth = commentAuth;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
