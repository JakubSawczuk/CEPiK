package pl.edu.wat.wcy.isi.mw.database.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Kuba on 2018-01-18.
 */
@Entity(name = "drivinglicense")
public class DrivingLicense {

    @Id
    @GeneratedValue
    private int idAuth;

    private Timestamp dateAuth;
    private Timestamp expirationDateAuth;

    private String commentAuth;
    private String KategoryDL;

    @ManyToOne
    @JoinColumn(name = "PeselDrv")
    Driver driver;

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

    public String getKategoryDL() {
        return KategoryDL;
    }

    public void setKategoryDL(String kategoryDL) {
        KategoryDL = kategoryDL;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
