package pl.edu.wat.wcy.isi.mw.searchmethods;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.hibernate.HibernateException;
import pl.edu.wat.wcy.isi.mw.LoginScreen;
import pl.edu.wat.wcy.isi.mw.NewAlert;
import pl.edu.wat.wcy.isi.mw.SearchController;
import pl.edu.wat.wcy.isi.mw.TabRow;
import pl.edu.wat.wcy.isi.mw.database.entity.*;
import pl.edu.wat.wcy.isi.mw.tabcontrollers.LostCarDocsTabController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SearchByRegisterNumber extends SearchController {

    @FXML
    public TextField registerNumber;
    @FXML
    public TextField text;
    @FXML
    public GridPane grid;
    @FXML
    public Button firstButton;
    @FXML
    public Button secondButton;
    @FXML
    public Button thirdButton;
    private TableView<TabRow> table = null;
    private ArrayList<String> vehiclePerson;


    public RegistrationDocument getIdRegistrationDocument(String VIN) {
        return (RegistrationDocument) LoginScreen.entityManager
                .createQuery("SELECT d FROM registrationdocument d  WHERE vin = ?1")
                .setParameter(1, VIN)
                .getSingleResult();
    }

    private List<WithdrawnAuthorisation> queryChceckReturnDateRegistrationDocument(int Aut_IdAuth) {
        return LoginScreen.entityManager
                .createQuery("SELECT e FROM withdrawnauthorisation e WHERE Aut_IdAuth = ?1")
                .setParameter(1, Aut_IdAuth)
                .getResultList();
    }

    private List<TemporaryAuthorisation> queryChceckExpDateTemporaryAuth(int Aut_IdAuth) {
        return LoginScreen.entityManager
                .createQuery("SELECT e FROM temporaryauthorisation e WHERE Aut_IdAuth = ?1")
                .setParameter(1, Aut_IdAuth)
                .getResultList();
    }

    private List<Vehicle> queryGetVehicleByRegistraionNumber() {
        return LoginScreen.entityManager
                .createQuery("SELECT e FROM vehicle e WHERE e.registrationNumber = ?1")
                .setParameter(1, registerNumber.getText())
                .getResultList();
    }

    public List<Driver> queryGetVehiclesOwnerByVin(String pesel) {
        return LoginScreen.entityManager
                .createQuery("SELECT vh FROM driver vh JOIN  vh.vehiclesList dr WHERE dr.vin=?1")
                .setParameter(1, pesel)
                .getResultList();
    }

    public void getVehiclesOwnerByVin() {
        vehiclePerson = new ArrayList<String>();
        List<Driver> vehicleOwner = queryGetVehiclesOwnerByVin(getVIN());
        if (vehicleOwner.size() != 0) {
            for (Driver aVehicleOwner : vehicleOwner) {
                vehiclePerson.add(aVehicleOwner.getPeselDrv());
            }
        } else {
             new NewAlert("Information", "Blad w wyszukiwaniu",
                    "Wlasciciel nie jest kierowca");
        }
    }

    public void checkWithDrawnAndTemporaryAuthorisationRD() {
        boolean validityWithdrawnRegisterDocument = false;

        try {
            int idAuthDrivingLicense = getIdRegistrationDocument(getVIN()).getIdAuth();
            List<WithdrawnAuthorisation> withdrawnAuthorisationList = queryChceckReturnDateRegistrationDocument(idAuthDrivingLicense);
            WithdrawnAuthorisation withdrawnAuthorisationLast = withdrawnAuthorisationList.get(withdrawnAuthorisationList.size() - 1);
            try {
                validityWithdrawnRegisterDocument = LocalDateTime.now().isBefore(withdrawnAuthorisationLast.getReturnDateWithdrawn());
            } catch (NullPointerException e) {
                validityWithdrawnRegisterDocument = true;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            List<TemporaryAuthorisation> temporaryAuthorisationList = queryChceckExpDateTemporaryAuth(idAuthDrivingLicense);
            TemporaryAuthorisation temporaryAuthorisationLast = temporaryAuthorisationList.get(temporaryAuthorisationList.size() - 1);
            boolean validityTemporaryAuth = LocalDateTime.now().isBefore(temporaryAuthorisationLast.getExpirationDateTempAuth());

            LocalDateTime withdrawnAuth = withdrawnAuthorisationLast.getDataWithdrawn();
            String formattedwithdrawnAuth = withdrawnAuth.format(formatter);
            LocalDateTime temporaryAuth = temporaryAuthorisationLast.getExpirationDateTempAuth();
            String formattedTemporaryAuth = temporaryAuth.format(formatter);

            if (validityWithdrawnRegisterDocument) {
                new NewAlert("Information", "Nie wazny dowod rejestracyjny",
                        "Dowod rejestracyjny pojazdu zostal zatrzymany: " + formattedwithdrawnAuth);
                if (validityTemporaryAuth)
                    new NewAlert("Information", "Nie wazne tymczasowy dowod rejestracyjny",
                            "Tymczasowy dowod rejestracyjny utracil waznosc: " + formattedTemporaryAuth);
            }


        } catch (Exception e) {

        }
    }

    private void makeCarTable(Vehicle vehicle) {
        table = super.newTable(grid, 1);
        table.getItems().add(new TabRow("VIN", vehicle.getVin()));
        table.getItems().add(new TabRow("Numer rejestracyjny", vehicle.getRegistrationNumber()));
        table.getItems().add(new TabRow("Marka", vehicle.getBrand()));
        table.getItems().add(new TabRow("Model", vehicle.getModel()));
        table.getItems().add(new TabRow("Kategoria", vehicle.getCategory()));
        table.getItems().add(new TabRow("Typ", vehicle.getType()));
        table.getItems().add(new TabRow("Wersja", vehicle.getVersion()));
        table.getItems().add(new TabRow("Wariant", vehicle.getVariant()));
        table.getItems().add(new TabRow("Data pierwszej rejestracji", vehicle.getDateFirstRegistration()));
        table.getItems().add(new TabRow("Numer rejestracyjny", vehicle.getRegistrationNumber()));
        table.getItems().add(new TabRow("Rok prodkcji", Integer.toString(vehicle.getProductionYear())));
        table.getItems().add(new TabRow("Waga pojazdu", Integer.toString(vehicle.getGrossVehicleWeightRating())));
    }

    public void RegisterNumberSearchClicked() {
        try {
            makeCarTable(queryGetVehicleByRegistraionNumber().get(0));
            checkWithDrawnAndTemporaryAuthorisationRD();
            makeButton();
        } catch (HibernateException e) {
            new NewAlert("Error", "Brak połączenia", "Sprawdz połączenie internetowe");
        } catch (IndexOutOfBoundsException e) {
            new NewAlert("Error", "Błąd w wyszukiwaniu", "Sprawdz poprawność wpisanego numeru rejestracyjnego");
        }
    }

    //Wydobywa z tabeli numer VIN
    private String getVIN() {
        String VIN = null;
        for (TabRow tr : table.getItems()) {
            if (tr.getFirst().equals("VIN")) VIN = tr.getSecond();
        }
        return VIN;
    }

    //Tworzy przyciski
    private void makeButton() {
        String type = grid.getParent().getId();
        if (type.equals("LostCarDocsTabPane"))
            super.enableButton("Zgłoś odebranie", firstButton);
        else if (type.equals("SearchCarTabPane")) {
            super.enableButton("Wyszukaj właściciela", firstButton);
            super.enableButton("Sprawdź OC", secondButton);
            super.enableButton("Sprawdź badanie Techniczne", thirdButton);
        }
    }

    public void firstButtonClicked() {
        String type = grid.getParent().getId();
        if (type.equals("SearchCarTabPane")) {
            if (getVIN() == null) {
                new NewAlert("Error", "Blad wyswietlania wlascicieli", "Nie zostal wybrany VIN");
            } else {
                getVehiclesOwnerByVin();
                super.searchPerson(vehiclePerson, grid);
            }
        } else if (type.equals("LostCarDocsTabPane")) {
            if (getVIN() == null) {
                new NewAlert("Error", "Blad odbierania dokumentow", "Nie zostal wybrany numer VIN");
            } else {
                LostCarDocsTabController lostCarDocsTabController = new LostCarDocsTabController();
                lostCarDocsTabController.withdrawnRegistraionDocument(getVIN());
            }
        }
    }

    public void secondButtonClicked() {
        super.searchOC(queryGetVehicleByRegistraionNumber().get(0).getVin(), grid);
    }

    public void thirdButtonClicked() {
        super.searchTechnic(queryGetVehicleByRegistraionNumber().get(0).getVin(), grid);
    }
}
