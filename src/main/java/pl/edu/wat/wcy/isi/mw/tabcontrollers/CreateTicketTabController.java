package pl.edu.wat.wcy.isi.mw.tabcontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import pl.edu.wat.wcy.isi.mw.LoginScreen;
import pl.edu.wat.wcy.isi.mw.NewAlert;
import pl.edu.wat.wcy.isi.mw.database.entity.Driver;
import pl.edu.wat.wcy.isi.mw.database.entity.Mandate;
import pl.edu.wat.wcy.isi.mw.database.entity.Policeman;
import pl.edu.wat.wcy.isi.mw.database.entity.RegistrationDocument;

import javax.management.Query;
import java.time.LocalDateTime;
import java.util.List;

public class CreateTicketTabController extends PersonTabController {

    @FXML
    GridPane CreateTicketTabPane;
    @FXML
    ComboBox<String> SearchMethodComboBox;
    private GridPane prevSearchPane = null;

    public void changeSearchMethod() {
        String searchMethod = SearchMethodComboBox.getValue();
        GridPane newSearch = super.getSearch(searchMethod);
        if (newSearch != null) {
            if (prevSearchPane != null) CreateTicketTabPane.getChildren().remove(prevSearchPane);
            if (CreateTicketTabPane != null)
                CreateTicketTabPane.add(newSearch, 0, 2, 1, 2);
            else System.exit(0);
            prevSearchPane = newSearch;
        }
    }

    public void addMandate(String cause, String peselDrv, String points) {
        Mandate mandate = new Mandate();

        Driver driver = new Driver();
        driver.setPeselDrv(peselDrv);

        Policeman policeman = new Policeman();
        policeman.setPeselPol(LoginScreen.peselPol);

        mandate.setDateMandate(LocalDateTime.now());
        mandate.setPoints(Integer.parseInt(points));
        mandate.setDriver(driver);
        mandate.setPoliceman(policeman);
        mandate.setCause(cause);

        LoginScreen.entityManager.getTransaction().begin();
        LoginScreen.entityManager.persist(mandate);
        LoginScreen.entityManager.getTransaction().commit();
    }

    public void checkPenaltyDriver(String pesel) {
        try {
            if (Integer.parseInt(getPenaltyDriver(pesel)) > Integer.parseInt("24")) {
                new NewAlert("Information", "Kierowca przekroczyl dozwzwolona liczbe punktow karnych",
                        "System odbiera uprawnienia kierowcy wystawiajac jednoczesnie tymczasowe pozowlenie");
                LostPersonDocsTabController lostPersonDocsTabController = new LostPersonDocsTabController();
                lostPersonDocsTabController.withdrawnDrivingLicense(pesel);
                lostPersonDocsTabController.addTemporaryAuthorisation(pesel);
            }
        } catch (Exception e) {
            new NewAlert("Error", "Blad przy odbieraniu uprawnien kierowcy",
                    "Wystapil blad w systemie. Nie zostalo zrealizowane zabranie uprawnien kierowcy i " +
                            "wystawienie pozwolenia tymczasowego ");
        }
    }

    public String getPenaltyDriver(String pesel) {
        return LoginScreen.entityManager
                .createQuery("SELECT SUM(points) FROM mandate d  WHERE PeselDrv = ?1 AND dateMandate > ?2")
                .setParameter(1, pesel)
                .setParameter(2, LocalDateTime.now().minusYears(1))
                .getSingleResult().toString();
    }
}