package pl.edu.wat.wcy.isi.mw.database.entity;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "authorisationloss")
class AuthorisationLoss
{
    @GeneratedValue
    @Id
    private int idALoss;
    private String commentALoss;
    private LocalDateTime dateFindingALoss;
    private LocalDateTime dateALoss;

    @ManyToOne
    @JoinColumn(name = "IdAuth")
    DrivingLicense drivingLicense;

    @ManyToOne
    @JoinColumn(name = "Aut_IdAuth")
    RegistrationDocument registrationDocument;

    public LocalDateTime getDateFindingALoss() {
        return dateFindingALoss;
    }

    public void setDateFindingALoss(LocalDateTime dateFindingALoss) {
        this.dateFindingALoss = dateFindingALoss;
    }

    public LocalDateTime getDateALoss() {
        return dateALoss;
    }

    public void setDateALoss(LocalDateTime dateALoss) {
        this.dateALoss = dateALoss;
    }


    public int getIdALoss() {
        return idALoss;
    }

    public String getCommentALoss() {
        return commentALoss;
    }

    public void setCommentALoss(String commentALoss) {
        this.commentALoss = commentALoss;
    }
}
