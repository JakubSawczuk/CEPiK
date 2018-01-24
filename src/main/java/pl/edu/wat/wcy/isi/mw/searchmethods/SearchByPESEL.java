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
import pl.edu.wat.wcy.isi.mw.tabcontrollers.LostPersonDocsTabController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SearchByPESEL extends SearchController {
    @FXML
    public TextField text;
    @FXML
    public TextField PESELnumber;
    @FXML
    public GridPane grid;
    @FXML
    public Button firstButton;
    private TableView<TabRow> Table = null;
    private ArrayList<String> personVehicles;

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

    private List<TemporaryAuthorisation> queryChceckExpDateTemporaryAuth(int IdAuth) {
        return LoginScreen.entityManager
                .createQuery("SELECT e FROM temporaryauthorisation e WHERE IdAuth = ?1")
                .setParameter(1, IdAuth)
                .getResultList();
    }


    public List<Adress> queryGetAdressByPesel() {
        return LoginScreen.entityManager
                .createQuery("SELECT ad FROM driver d JOIN d.adress  ad WHERE d.peselDrv=?1")
                .setParameter(1, PESELnumber.getText())
                .getResultList();
    }

    public List<Driver> queryGetDriverByPesel() {
        return LoginScreen.entityManager
                .createQuery("SELECT e FROM driver e WHERE e.peselDrv = ?1")
                .setParameter(1, PESELnumber.getText())
                .getResultList();
    }

    public List<DrivingLicense> queryGetDrivingLicenseByPesel() {
        return LoginScreen.entityManager
                .createQuery("SELECT d FROM drivinglicense d WHERE peselDrv=?1 ")
                .setParameter(1, PESELnumber.getText())
                .getResultList();
    }

    public String queryGetPenaltyDriver() {
        return LoginScreen.entityManager
                .createQuery("SELECT SUM(points) FROM mandate d  WHERE PeselDrv = ?1 AND dateMandate > ?2")
                .setParameter(1, PESELnumber.getText())
                .setParameter(2, LocalDateTime.now().minusYears(1))
                .getSingleResult().toString();
    }

    public void checkWithDrawnAndTemporaryAuthorisationDL() {
        try {
            int idAuthDrivingLicense = getIdDrivingLicense(PESELnumber.getText()).getIdAuth();

            List<WithdrawnAuthorisation> withdrawnAuthorisationList = queryChceckReturnDateDrivingLicense(idAuthDrivingLicense);
            WithdrawnAuthorisation withdrawnAuthorisationLast = withdrawnAuthorisationList.get(withdrawnAuthorisationList.size() - 1);
            boolean validityWithDrawnLicense = LocalDateTime.now().isBefore(withdrawnAuthorisationLast.getReturnDateWithdrawn());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            List<TemporaryAuthorisation> temporaryAuthorisationList = queryChceckExpDateTemporaryAuth(idAuthDrivingLicense);
            TemporaryAuthorisation temporaryAuthorisationLast = temporaryAuthorisationList.get(temporaryAuthorisationList.size() - 1);
            boolean validityTemporaryAuth = LocalDateTime.now().isBefore(temporaryAuthorisationLast.getExpirationDateTempAuth());

            LocalDateTime withdrawnAuth = withdrawnAuthorisationLast.getDataWithdrawn();
            String formattedwithdrawnAuth = withdrawnAuth.format(formatter);
            LocalDateTime temporaryAuth = temporaryAuthorisationLast.getExpirationDateTempAuth();
            String formattedTemporaryAuth = temporaryAuth.format(formatter);

            if (validityWithDrawnLicense) {
                new NewAlert("Information", "Niewazne prawo jazdy",
                        "Prawo jazdy kierowcy zostalo zatrzymane: " + formattedwithdrawnAuth);

                if (validityTemporaryAuth)
                    new NewAlert("Information", "Niewazne tymczasowe prawo jazdy",
                            "Tymczasowe prawo jazdy traci waznosc: " + formattedTemporaryAuth);
            }

        } catch (Exception e) {

        }
    }

    public void PESELsearchClicked() {
        if (queryGetDriverByPesel().size() != 0) {
            checkWithDrawnAndTemporaryAuthorisationDL();
            try {
                makeTable(queryGetDriverByPesel().get(0), queryGetAdressByPesel().get(0),
                        queryGetDrivingLicenseByPesel().get(0), queryGetPenaltyDriver());
            } catch (NullPointerException e) {
                makeTable(queryGetDriverByPesel().get(0), queryGetAdressByPesel().get(0),
                        queryGetDrivingLicenseByPesel().get(0), "0");
            }
            makeButton();

        } else {
            if (Table != null) grid.getChildren().remove(Table);
            new NewAlert("Error", "Blad w wyszukiwaniu",
                    "Nie zostal znaleziony kierowca o takim PESELu");
        }
    }

    public List<Vehicle> queryGetOwnerVehiclesByPesel() {
        return LoginScreen.entityManager
                .createQuery("SELECT vh FROM vehicle vh JOIN  vh.driverList dr WHERE dr.peselDrv=?1")
                .setParameter(1, PESELnumber.getText())
                .getResultList();
    }

    public void getOwnerVehiclesByPesel() {
        try {
            personVehicles = new ArrayList<String>();
            List<Vehicle> ownerVehicle = queryGetOwnerVehiclesByPesel();
            for (Vehicle anOwnerVehicle : ownerVehicle) {
                personVehicles.add(anOwnerVehicle.getVin());
            }
        } catch (IndexOutOfBoundsException e) {
            new NewAlert("Information", "Blad w wyszukiwaniu",
                    "Kierowca nie posiada samochodow");
        } catch (HibernateException e) {
            new NewAlert("Information", "Blad w wyszukiwaniu",
                    "Sprawdz polaczenie internetowe");
        }
    }

    private void makeButton() {
        String type = grid.getParent().getId();
        if (type.equals("CreateTicketTabPane")) {
            super.enableButton("Wystaw mandat", firstButton);
            super.enableText("Liczba punktów", text);
        } else if (type.equals("LostPersonDocsTabPane"))
            super.enableButton("Zgłoś odebranie", firstButton);
        else if (type.equals("SearchPersonTabPane"))
            super.enableButton("Pokaż pojazdy", firstButton);
    }


    public void firstButtonClicked() {
        String type = grid.getParent().getId();

        if (type.equals("SearchPersonTabPane")) {
            getOwnerVehiclesByPesel();
            super.searchCar(personVehicles, grid);
        } else if (type.equals("LostPersonDocsTabPane")) {
            LostPersonDocsTabController lostPersonDocsTabController = new LostPersonDocsTabController();
            lostPersonDocsTabController.withdrawnDrivingLicense(PESELnumber.getText());
        } else if (type.equals("CreateTicketTabPane")) {
            super.addCommentWindow(PESELnumber.getText(), text.getText());
        }
    }

    private void makeTable(Driver driver, Adress adress, DrivingLicense drivingLicense, String driverPenalty) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime dateTimeExp = drivingLicense.getExpirationDateAuth();
        LocalDateTime dateTime = drivingLicense.getDateAuth();
        String formattedDateTimeExp = dateTimeExp.format(formatter);
        String formattedDateTime = dateTime.format(formatter);

        if (Table != null) grid.getChildren().remove(Table);
        Table = super.newTable(grid, 1);
        Table.getItems().add(new TabRow("PESEL", driver.getPeselDrv()));
        Table.getItems().add(new TabRow("Imie", driver.getFirstNameDrv()));
        Table.getItems().add(new TabRow("Drugie imie", driver.getSecondNameDrv()));
        Table.getItems().add(new TabRow("Nazwisko", driver.getSurnameDrv()));
        Table.getItems().add(new TabRow("Miasto", adress.getCity()));
        Table.getItems().add(new TabRow("Ulica", adress.getStreet()));
        Table.getItems().add(new TabRow("Numer budynku", adress.getBuildingNr()));
        Table.getItems().add(new TabRow("Numer mieszkania", String.valueOf(adress.getResidenceNr())));
        Table.getItems().add(new TabRow("Data wydania prawa jazdy", formattedDateTime));
        Table.getItems().add(new TabRow("Data wygasniecia waznosci prawa jazdy", formattedDateTimeExp));
        Table.getItems().add(new TabRow("Kategorie", drivingLicense.getKategoryDL()));
        Table.getItems().add(new TabRow("Specjalne uprawnienia", drivingLicense.getCommentAuth()));
        Table.getItems().add(new TabRow("Punkty karne", driverPenalty));

    }
}
