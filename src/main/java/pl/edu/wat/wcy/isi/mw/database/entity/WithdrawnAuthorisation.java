package pl.edu.wat.wcy.isi.mw.database.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "withdrawnauthorisation")
public class WithdrawnAuthorisation {

    @Id
    @GeneratedValue
    private int idWithdrawn;

    private LocalDateTime dataWithdrawn;
    private LocalDateTime returnDateWithdrawn;

    @ManyToOne
    @JoinColumn(name = "IdAuth")
    private
    DrivingLicense drivingLicense;

    @ManyToOne
    @JoinColumn(name = "Aut_IdAuth")
    private
    RegistrationDocument registrationDocument;

    public LocalDateTime getDataWithdrawn() {
        return dataWithdrawn;
    }

    public void setDataWithdrawn(LocalDateTime dataWithdrawn) {
        this.dataWithdrawn = dataWithdrawn;
    }

    public LocalDateTime getReturnDateWithdrawn() {
        return returnDateWithdrawn;
    }

    public void setReturnDateWithdrawn(LocalDateTime returnDateWithdrawn) {
        this.returnDateWithdrawn = returnDateWithdrawn;
    }

    public DrivingLicense getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(DrivingLicense drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public RegistrationDocument getRegistrationDocument() {
        return registrationDocument;
    }

    public void setRegistrationDocument(RegistrationDocument registrationDocument) {
        this.registrationDocument = registrationDocument;
    }

    public int getIdWithdrawn() {
        return idWithdrawn;
    }

    public void setIdWithdrawn(int idWithdrawn) {
        this.idWithdrawn = idWithdrawn;
    }
}
