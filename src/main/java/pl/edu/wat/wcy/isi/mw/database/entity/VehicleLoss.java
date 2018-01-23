package pl.edu.wat.wcy.isi.mw.database.entity;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "vehicleloss")
class VehicleLoss
{
    @GeneratedValue
    @Id
    private int idVLoss;
    private String commentVLoss;
    private LocalDateTime dateEvent;
    private LocalDateTime dateFindingVLoss;

    @ManyToOne
    @JoinColumn(name = "vin")
    private
    Vehicle vehicle;

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDateTime getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(LocalDateTime dateEvent) {
        this.dateEvent = dateEvent;
    }

    public LocalDateTime getDateFindingVLoss() {
        return dateFindingVLoss;
    }

    public void setDateFindingVLoss(LocalDateTime dateFindingVLoss) {
        this.dateFindingVLoss = dateFindingVLoss;
    }

    public int getIdVLoss() {
        return idVLoss;
    }

    public void setIdVLoss(int idVLoss) {
        this.idVLoss = idVLoss;
    }

    public String getCommentVLoss() {
        return commentVLoss;
    }

    public void setCommentVLoss(String commentVLoss) {
        this.commentVLoss = commentVLoss;
    }
}
