package pl.edu.wat.wcy.isi.mw.database.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 * Created by Kuba on 2018-01-18.
 */
@Entity(name = "oc")
public class OC {

    @Id
    private int idOc;

    private LocalDateTime oCDate;
    private LocalDateTime oCExpirationDate;

    @ManyToOne
    @JoinColumn(name = "vin")
    private
    Vehicle vehicle;

    public int getIdOc() {
        return idOc;
    }

    public void setIdOc(int idOc) {
        this.idOc = idOc;
    }

    public LocalDateTime getoCDate() {
        return oCDate;
    }

    public void setoCDate(LocalDateTime oCDate) {
        this.oCDate = oCDate;
    }

    public LocalDateTime getoCExpirationDate() {
        return oCExpirationDate;
    }

    public void setoCExpirationDate(LocalDateTime oCExpirationDate) {
        this.oCExpirationDate = oCExpirationDate;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
