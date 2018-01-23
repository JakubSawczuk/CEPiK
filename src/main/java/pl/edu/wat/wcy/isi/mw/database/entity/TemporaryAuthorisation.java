package pl.edu.wat.wcy.isi.mw.database.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Kuba on 2018-01-18.
 */
@Entity(name = "temporaryauthorisation")
public class TemporaryAuthorisation {

    @Id
    @GeneratedValue
    private int idTempAuth;

    private LocalDateTime dateTempAuth;
    private LocalDateTime expirationDateTempAuth;

    @ManyToOne
    @JoinColumn(name = "IdAuth")
    private
    DrivingLicense drivingLicense;

    @ManyToOne
    @JoinColumn(name = "Aut_IdAuth")
    private
    RegistrationDocument registrationDocument;

    public int getIdTempAuth() {
        return idTempAuth;
    }

    public void setIdTempAuth(int idTempAuth) {
        this.idTempAuth = idTempAuth;
    }

    public LocalDateTime getDateTempAuth() {
        return dateTempAuth;
    }

    public void setDateTempAuth(LocalDateTime dateTempAuth) {
        this.dateTempAuth = dateTempAuth;
    }

    public LocalDateTime getExpirationDateTempAuth() {
        return expirationDateTempAuth;
    }

    public void setExpirationDateTempAuth(LocalDateTime expirationDateTempAuth) {
        this.expirationDateTempAuth = expirationDateTempAuth;
    }

    public RegistrationDocument getRegistrationDocument() {
        return registrationDocument;
    }

    public void setRegistrationDocument(RegistrationDocument registrationDocument) {
        this.registrationDocument = registrationDocument;
    }

    public DrivingLicense getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(DrivingLicense drivingLicense) {
        this.drivingLicense = drivingLicense;
    }
}
