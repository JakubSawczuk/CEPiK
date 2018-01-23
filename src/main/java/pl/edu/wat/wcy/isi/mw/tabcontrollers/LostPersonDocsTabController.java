package pl.edu.wat.wcy.isi.mw.tabcontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import pl.edu.wat.wcy.isi.mw.LoginScreen;
import pl.edu.wat.wcy.isi.mw.NewAlert;
import pl.edu.wat.wcy.isi.mw.database.entity.DrivingLicense;
import pl.edu.wat.wcy.isi.mw.database.entity.TemporaryAuthorisation;
import pl.edu.wat.wcy.isi.mw.database.entity.WithdrawnAuthorisation;

import java.time.LocalDateTime;

public class LostPersonDocsTabController extends PersonTabController {

    @FXML GridPane LostPersonDocsTabPane;
    @FXML ComboBox <String> SearchMethodComboBox;
    private GridPane prevSearchPane = null;

    public void changeSearchMethod() {
        String searchMethod = SearchMethodComboBox.getValue();
        GridPane newSearch = super.getSearch(searchMethod);
        if (newSearch != null) {
            if (prevSearchPane != null) LostPersonDocsTabPane.getChildren().remove(prevSearchPane);
            if (LostPersonDocsTabPane != null)
                LostPersonDocsTabPane.add(newSearch,0,2,1,2);
            else System.exit(0);
            prevSearchPane = newSearch;
        }
    }

    public void commitData(Object obj) {
        LoginScreen.entityManager.getTransaction().begin();
        LoginScreen.entityManager.persist(obj);
        LoginScreen.entityManager.getTransaction().commit();
    }

    public DrivingLicense getIdDrivingLicense(String pesel) {
        return (DrivingLicense) LoginScreen.entityManager
                .createQuery("SELECT d FROM drivinglicense d  WHERE PeselDrv = ?1")
                .setParameter(1, pesel)
                .getSingleResult();
    }

    public void withdrawnDrivingLicense(String pesel) {
        try {
            int idAuthDrivingLicense = getIdDrivingLicense(pesel).getIdAuth();
            DrivingLicense drivingLicense = new DrivingLicense();
            drivingLicense.setIdAuth(idAuthDrivingLicense);

            WithdrawnAuthorisation withdrawnAuthorisation = new WithdrawnAuthorisation();
            withdrawnAuthorisation.setDataWithdrawn(LocalDateTime.now());
            withdrawnAuthorisation.setReturnDateWithdrawn(LocalDateTime.now().plusMonths(3));
            withdrawnAuthorisation.setDrivingLicense(drivingLicense);

            commitData(withdrawnAuthorisation);
            NewAlert newAlert = new NewAlert("Information", "Zgloszenie zostalo dodane",
                    "Dodanie zgloszenia utraty prawa jazdy przebieglo pomyslnie");
        } catch (IndexOutOfBoundsException e) {
            NewAlert newAlert = new NewAlert("Error", "Błąd w wyszukiwaniu",
                    "Sprawdz poprawność wpisanego PESELu");
        }
    }

    public void addTemporaryAuthorisation(String pesel){
        try {
            int idAuthDrivingLicense = getIdDrivingLicense(pesel).getIdAuth();
            DrivingLicense drivingLicense = new DrivingLicense();
            drivingLicense.setIdAuth(idAuthDrivingLicense);

            TemporaryAuthorisation temporaryAuthorisation = new TemporaryAuthorisation();
            temporaryAuthorisation.setDateTempAuth(LocalDateTime.now());
            temporaryAuthorisation.setExpirationDateTempAuth(LocalDateTime.now().plusDays(7));
            temporaryAuthorisation.setDrivingLicense(drivingLicense);

            commitData(temporaryAuthorisation);
            NewAlert newAlert = new NewAlert("Information", "Zgloszenie zostalo dodane",
                    "Dodanie zgloszenia wydania pozwolenia tymczasowego na prawo jazdy przebieglo pomyslnie");
        } catch (IndexOutOfBoundsException e) {
            NewAlert newAlert = new NewAlert("Error", "Błąd w wyszukiwaniu",
                    "Sprawdz poprawność wpisanego PESELu");
        }
    }
}
