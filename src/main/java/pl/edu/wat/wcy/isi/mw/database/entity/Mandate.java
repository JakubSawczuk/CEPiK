package pl.edu.wat.wcy.isi.mw.database.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "mandate")
public class Mandate
{

    @GeneratedValue
    @Id
    private int idMandate;
    private int points;
    private String cause;
    private LocalDateTime dateMandate;

    @ManyToOne
    @JoinColumn(name = "peselPol")
    private
    Policeman policeman;

    @ManyToOne
    @JoinColumn(name = "peselDrv")
    private
    Driver driver;

    public LocalDateTime getDateMandate() {
        return dateMandate;
    }

    public void setDateMandate(LocalDateTime dateMandate) {
        this.dateMandate = dateMandate;
    }

    public Policeman getPoliceman() {
        return policeman;
    }

    public void setPoliceman(Policeman policeman) {
        this.policeman = policeman;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public void setIdMandate(int idMandate) {
        this.idMandate = idMandate;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public int getIdMandate() {
        return idMandate;
    }

    public int getPoints() {
        return points;
    }

    public String getCause() {
        return cause;
    }
}
