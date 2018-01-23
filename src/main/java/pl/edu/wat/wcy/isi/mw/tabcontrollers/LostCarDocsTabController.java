package pl.edu.wat.wcy.isi.mw.tabcontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import pl.edu.wat.wcy.isi.mw.LoginScreen;
import pl.edu.wat.wcy.isi.mw.NewAlert;
import pl.edu.wat.wcy.isi.mw.database.entity.RegistrationDocument;
import pl.edu.wat.wcy.isi.mw.database.entity.TemporaryAuthorisation;
import pl.edu.wat.wcy.isi.mw.database.entity.WithdrawnAuthorisation;

import java.time.LocalDateTime;

public class LostCarDocsTabController extends CarTabController{

    @FXML public GridPane LostCarDocsTabPane;
    @FXML ComboBox <String> SearchMethodComboBox;
    private GridPane prevSearchPane = null;

    public void changeSearchMethod() {
        String searchMethod = SearchMethodComboBox.getValue();
        GridPane newSearch = super.getSearch(searchMethod);
        if (newSearch != null) {
            if (prevSearchPane != null) LostCarDocsTabPane.getChildren().remove(prevSearchPane);
            LostCarDocsTabPane.add(newSearch,0,2,1,2);
            prevSearchPane = newSearch;
        }
    }

    public void commitData(Object obj) {
        LoginScreen.entityManager.getTransaction().begin();
        LoginScreen.entityManager.persist(obj);
        LoginScreen.entityManager.getTransaction().commit();
    }

    public RegistrationDocument getIdRegistrationDocument(String vin) {
        return (RegistrationDocument) LoginScreen.entityManager
                .createQuery("SELECT d FROM registrationdocument d  WHERE vin = ?1")
                .setParameter(1, vin)
                .getSingleResult();
    }

    public void withdrawnRegistraionDocument(String vin) {
        try {
            int idAuthDrivingLicense = getIdRegistrationDocument(vin).getIdAuth();
            RegistrationDocument registrationDocument = new RegistrationDocument();
            registrationDocument.setIdAuth(idAuthDrivingLicense);

            WithdrawnAuthorisation withdrawnAuthorisation = new WithdrawnAuthorisation();
            withdrawnAuthorisation.setDataWithdrawn(LocalDateTime.now());
            withdrawnAuthorisation.setRegistrationDocument(registrationDocument);

            commitData(withdrawnAuthorisation);
            NewAlert newAlert = new NewAlert("Information", "Zgloszenie zostalo dodane",
                    "Dodanie zgloszenia utraty dowodu rejestracyjnego przebieglo pomyslnie");
        } catch (IndexOutOfBoundsException e) {
            NewAlert newAlert = new NewAlert("Error", "Błąd w wyszukiwaniu",
                    "Sprawdz poprawność wpisanego VINu");
        }
    }

    public void addTemporaryAuthorisation(String vin){
        try {
            int idAuthDrivingLicense = getIdRegistrationDocument(vin).getIdAuth();
            RegistrationDocument registrationDocument = new RegistrationDocument();
            registrationDocument.setIdAuth(idAuthDrivingLicense);

            TemporaryAuthorisation temporaryAuthorisation = new TemporaryAuthorisation();
            temporaryAuthorisation.setDateTempAuth(LocalDateTime.now());
            temporaryAuthorisation.setExpirationDateTempAuth(LocalDateTime.now().plusDays(7));
            temporaryAuthorisation.setRegistrationDocument(registrationDocument);

            commitData(temporaryAuthorisation);
            NewAlert newAlert = new NewAlert("Information", "Zgloszenie zostalo dodane",
                    "Dodanie zgloszenia wystawienia tymczasowego prawa jazdy przebieglo pomyslnie");
        } catch (IndexOutOfBoundsException e) {
            NewAlert newAlert = new NewAlert("Error", "Błąd w wyszukiwaniu",
                    "Sprawdz poprawność wpisanego VINu");
        }
    }


}