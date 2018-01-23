package pl.edu.wat.wcy.isi.mw.database.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "technicaltest")
public class TechnicalTest {

    @GeneratedValue
    @Id
    private int idTest;

    private int mileAge;
    private int idMotorworks;
    private String clauses;
    private LocalDateTime dateTest;
    //private LocalDate dateTest;
    private LocalDateTime expirationDateTest;

    @ManyToOne
    @JoinColumn(name = "vin")
    Vehicle vehicle;

    public LocalDateTime getExpirationDateTest() {
        return expirationDateTest;
    }

    public void setExpirationDateTest(LocalDateTime expirationDateTest) {
        this.expirationDateTest = expirationDateTest;
    }

    public LocalDateTime getDateTest() {
        return dateTest;
    }

    public void setDateTest(LocalDateTime dateTest) {
        this.dateTest = dateTest;
    }

    public int getIdTest() {
        return idTest;
    }

    public void setIdTest(int idTest) {
        this.idTest = idTest;
    }

    public int getMileAge() {
        return mileAge;
    }

    public void setMileAge(int mileAge) {
        this.mileAge = mileAge;
    }

    public int getIdMotorworks() {
        return idMotorworks;
    }

    public void setIdMotorworks(int idMotorworks) {
        this.idMotorworks = idMotorworks;
    }

    public String getClauses() {
        return clauses;
    }

    public void setClauses(String clauses) {
        this.clauses = clauses;
    }
}
