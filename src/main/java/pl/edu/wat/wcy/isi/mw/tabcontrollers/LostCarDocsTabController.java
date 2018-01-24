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
import java.util.List;

public class LostCarDocsTabController extends CarTabController {

    @FXML
    public GridPane LostCarDocsTabPane;
    @FXML
    ComboBox<String> SearchMethodComboBox;
    private GridPane prevSearchPane = null;

    private List<WithdrawnAuthorisation> queryChceckReturnDateRegistrationDocument(int Aut_IdAuth) {
        return LoginScreen.entityManager
                .createQuery("SELECT e FROM withdrawnauthorisation e WHERE Aut_IdAuth = ?1")
                .setParameter(1, Aut_IdAuth)
                .getResultList();
    }

    public void changeSearchMethod() {
        String searchMethod = SearchMethodComboBox.getValue();
        GridPane newSearch = super.getSearch(searchMethod);
        if (newSearch != null) {
            if (prevSearchPane != null) LostCarDocsTabPane.getChildren().remove(prevSearchPane);
            LostCarDocsTabPane.add(newSearch, 0, 2, 1, 2);
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
        boolean validityWithdrawnRegisterDocument = false;
        int idAuthDrivingLicense = getIdRegistrationDocument(vin).getIdAuth();

        try {
            List<WithdrawnAuthorisation> withdrawnAuthorisationList = queryChceckReturnDateRegistrationDocument(idAuthDrivingLicense);
            WithdrawnAuthorisation withdrawnAuthorisationLast = withdrawnAuthorisationList.get(withdrawnAuthorisationList.size() - 1);
            try {
                validityWithdrawnRegisterDocument = LocalDateTime.now().isBefore(withdrawnAuthorisationLast.getReturnDateWithdrawn());
            } catch (NullPointerException e) {
                validityWithdrawnRegisterDocument = true;
            }

            if (validityWithdrawnRegisterDocument) {
                new NewAlert("Information", "Blad dodawania zgloszenia",
                        "Dowod rejestracyjny zostal juz odebrany wczesniej");
            } else {
                try {
                    RegistrationDocument registrationDocument = new RegistrationDocument();
                    registrationDocument.setIdAuth(idAuthDrivingLicense);

                    WithdrawnAuthorisation withdrawnAuthorisation = new WithdrawnAuthorisation();
                    withdrawnAuthorisation.setDataWithdrawn(LocalDateTime.now());
                    withdrawnAuthorisation.setRegistrationDocument(registrationDocument);

                    commitData(withdrawnAuthorisation);
                    new NewAlert("Information", "Zgloszenie zostalo dodane",
                            "Dodanie zgloszenia utraty dowodu rejestracyjnego przebieglo pomyslnie");
                } catch (IndexOutOfBoundsException e) {
                    new NewAlert("Error", "Błąd w wyszukiwaniu",
                            "Sprawdz poprawność wpisanego VINu");
                }
            }
        } catch (Exception e) {
            new NewAlert("Error", "Blad krytyczny",
                    "Wystapil blad krytyczny aplikacji. Zglos go przelozonemu");
        }
    }

    public void addTemporaryAuthorisation(String vin) {
        try {
            int idAuthDrivingLicense = getIdRegistrationDocument(vin).getIdAuth();
            RegistrationDocument registrationDocument = new RegistrationDocument();
            registrationDocument.setIdAuth(idAuthDrivingLicense);

            TemporaryAuthorisation temporaryAuthorisation = new TemporaryAuthorisation();
            temporaryAuthorisation.setDateTempAuth(LocalDateTime.now());
            temporaryAuthorisation.setExpirationDateTempAuth(LocalDateTime.now().plusDays(7));
            temporaryAuthorisation.setRegistrationDocument(registrationDocument);

            commitData(temporaryAuthorisation);

            new NewAlert("Information", "Zgloszenie zostalo dodane",
                    "Dodanie zgloszenia wystawienia tymczasowego dowodu rejestracyjnego przebieglo pomyslnie");

            addTemporaryAuthorisation(vin);
        } catch (IndexOutOfBoundsException e) {
            new NewAlert("Error", "Błąd w wyszukiwaniu",
                    "Sprawdz poprawność wpisanego VINu");
        }
    }


}