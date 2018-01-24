package pl.edu.wat.wcy.isi.mw.tabcontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import pl.edu.wat.wcy.isi.mw.LoginScreen;
import pl.edu.wat.wcy.isi.mw.NewAlert;
import pl.edu.wat.wcy.isi.mw.database.entity.*;

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

    public DrivingLicense getIdDrivingLicense(String pesel) {
        return (DrivingLicense) LoginScreen.entityManager
                .createQuery("SELECT d FROM drivinglicense d  WHERE PeselDrv = ?1")
                .setParameter(1, pesel)
                .getSingleResult();
    }

    private List<WithdrawnAuthorisation> queryChceckReturnDateDrivingLicense(int IdAuth) {
        return LoginScreen.entityManager
                .createQuery("SELECT e FROM withdrawnauthorisation e WHERE IdAuth = ?1")
                .setParameter(1, IdAuth)
                .getResultList();
    }

    public String getPenaltyDriver(String pesel) {
        return LoginScreen.entityManager
                .createQuery("SELECT SUM(points) FROM mandate d  WHERE PeselDrv = ?1 AND dateMandate > ?2")
                .setParameter(1, pesel)
                .setParameter(2, LocalDateTime.now().minusYears(1))
                .getSingleResult().toString();
    }


    public void addMandate(String cause, String peselDrv, String points) {
        boolean validity = false;
        int idAuthDrivingLicense = getIdDrivingLicense(peselDrv).getIdAuth();
        try {
            List<WithdrawnAuthorisation> withdrawnAuthorisationList = queryChceckReturnDateDrivingLicense(idAuthDrivingLicense);
            WithdrawnAuthorisation withdrawnAuthorisationLast = withdrawnAuthorisationList.get(withdrawnAuthorisationList.size() - 1);
            validity = LocalDateTime.now().isBefore(withdrawnAuthorisationLast.getReturnDateWithdrawn());
        }catch (IndexOutOfBoundsException e) {
        }if (validity) {
            new NewAlert("Information", "Blad dodawania zgloszenia", "Nie mozna dodac punktow karnych dla osoby z zatrzymanym prawem jazdy");
        } else {
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

            new NewAlert("Information", "Mandat zostal dodany pomyslnie",
                    "Mandat zostal dodany pomyslnie");

            checkPenaltyDriver(peselDrv);
        }
    }

    public void checkPenaltyDriver(String pesel) {
        try {
            if (Integer.parseInt(getPenaltyDriver(pesel)) > Integer.parseInt("24")) {
                new NewAlert("Information", "Kierowca przekroczyl dozwzwolona liczbe punktow karnych",
                        "System odbiera uprawnienia kierowcy wystawiajac jednoczesnie tymczasowe pozowlenie");
                LostPersonDocsTabController lostPersonDocsTabController = new LostPersonDocsTabController();
                lostPersonDocsTabController.withdrawnDrivingLicense(pesel);
            }
        } catch (Exception e) {
            new NewAlert("Error", "Blad przy odbieraniu uprawnien kierowcy",
                    "Wystapil blad w systemie. Nie zostalo zrealizowane zabranie uprawnien kierowcy i " +
                            "wystawienie pozwolenia tymczasowego ");
        }
    }

}