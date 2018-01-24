package pl.edu.wat.wcy.isi.mw.searchmethods;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import pl.edu.wat.wcy.isi.mw.LoginScreen;
import pl.edu.wat.wcy.isi.mw.NewAlert;
import pl.edu.wat.wcy.isi.mw.Person;
import pl.edu.wat.wcy.isi.mw.SearchController;
import pl.edu.wat.wcy.isi.mw.database.entity.*;
import pl.edu.wat.wcy.isi.mw.tabcontrollers.LostPersonDocsTabController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SearchByName extends SearchController {

    @FXML
    public GridPane grid;
    @FXML
    public TextField text;
    @FXML
    public TextField firstName;
    @FXML
    public TextField surname;
    @FXML
    public TextField streetName;
    @FXML
    public TextField cityName;
    public Button firstButton;
    private TableView<Person> tableView;
    private ArrayList<String> personVehicles;


    public DrivingLicense getIdDrivingLicense(String pesel) {
        return (DrivingLicense) LoginScreen.entityManager
                .createQuery("SELECT d FROM drivinglicense d  WHERE PeselDrv = ?1")
                .setParameter(1, pesel)
                .getSingleResult();
    }

    public List<DrivingLicense> queryGetDrivingLicenseByPesel(int index) {
        return LoginScreen.entityManager
                .createQuery("SELECT d FROM drivinglicense d WHERE peselDrv=?1 ")
                .setParameter(1, queryGetDriverByName().get(index).getPeselDrv())
                .getResultList();
    }

    public List<Long> queryGetPenaltyDriver(int index) {
        return LoginScreen.entityManager
                .createQuery("SELECT SUM(points) FROM mandate d  WHERE PeselDrv = ?1 AND dateMandate > ?2")
                .setParameter(1, queryGetDriverByName().get(index).getPeselDrv())
                .setParameter(2, LocalDateTime.now().minusYears(1))
                .getResultList();
    }


    public List<Driver> queryGetDriverByName() {
        return LoginScreen.entityManager
                .createQuery("SELECT d FROM driver d JOIN d.adress ad WHERE d.firstNameDrv=?1 " +
                        "AND d.surnameDrv=?2", Driver.class)
                .setParameter(1, firstName.getText())
                .setParameter(2, surname.getText())
                .getResultList();

    }

    public List<Adress> queryGetAdressByName(int index) {
        return LoginScreen.entityManager
                .createQuery("SELECT ad FROM driver d JOIN d.adress ad WHERE d.peselDrv=?1 " +
                        "AND ad.city=?2 AND ad.street=?3", Adress.class)
                .setParameter(1, (queryGetDriverByName().get(index).getPeselDrv()))
                .setParameter(2, cityName.getText())
                .setParameter(3, streetName.getText())
                .getResultList();
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


    private TableColumn<Person, String> makeColumn(String text, String value) {
        TableColumn<Person, String> tableColumn = new TableColumn<Person, String>(text);
        tableColumn.setCellValueFactory(new PropertyValueFactory<Person, String>(value));
        return tableColumn;
    }

    private void makeTable() {
        tableView = new TableView<Person>();
        tableView.setEditable(true);
        makeColumns();
        grid.add(tableView, 0, 1, 3, 1);
        GridPane.setMargin(tableView, new Insets(0, 20, 5, 20));
    }

    public void checkWithDrawnAndTemporaryAuthorisationDL() {

        try {
            int idAuthDrivingLicense = getIdDrivingLicense(getPESEL()).getIdAuth();
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
                new NewAlert("Information", "Nie wazne prawo jazdy",
                        "Prawo jazdy kierowcy zostalo zatrzymane: " + formattedwithdrawnAuth);

                if (validityTemporaryAuth)
                    new NewAlert("Information", "Nie wazne tymczasowe prawo jazdy",
                            "Tymczasowe prawo jazdy utracilo waznosc: " + formattedTemporaryAuth);
            }

        } catch (Exception e) {

        }
    }

    private void makeColumns() {
        TableColumn<Person, String> firstName = makeColumn("Imię", "firstName");
        TableColumn<Person, String> secondName = makeColumn("Drugie imie", "secondName");
        TableColumn<Person, String> surName = makeColumn("Nazwisko", "surname");
        TableColumn<Person, String> pesel = makeColumn("PESEL", "pesel");
        TableColumn<Person, String> city = makeColumn("Miasto", "city");
        TableColumn<Person, String> street = makeColumn("Ulica", "street");
        TableColumn<Person, String> buildingNr = makeColumn("Numer domu", "buildingNr");
        TableColumn<Person, String> residenceNr = makeColumn("Numer mieszkania", "residenceNr");
        TableColumn<Person, String> dateAuth = makeColumn("Data wydania prawa jazdy", "dateAuth");
        TableColumn<Person, String> expDateAuth = makeColumn("Data wygasniecia waznosci prawa jazdy", "expDateAuth");
        TableColumn<Person, String> categoryDL = makeColumn("Kategorie", "categoryDL");
        TableColumn<Person, String> commentAuth = makeColumn("Specjalne uprawnienia", "commentAuth");
        TableColumn<Person, String> penalty = makeColumn("Punkty karne", "penalty");
        tableView.getColumns().addAll(firstName, secondName, surName, pesel, city, street, buildingNr, residenceNr,
                dateAuth, expDateAuth, categoryDL, commentAuth, penalty);
    }

    private String getPESEL() {
        Person person = tableView.getSelectionModel().getSelectedItem();
        if (person != null) return person.getPesel();
        else return null;
    }

    private void savePersonDataToTable() {
        for (int i = 0; i < queryGetDriverByName().size(); i++) {
            Person person = new Person();
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDateTime dateTimeExp = queryGetDrivingLicenseByPesel(i).get(0).getExpirationDateAuth();
                LocalDateTime dateTime = queryGetDrivingLicenseByPesel(i).get(0).getDateAuth();
                String formattedDateTimeExp = dateTimeExp.format(formatter);
                String formattedDateTime = dateTime.format(formatter);

                person.setPesel(queryGetDriverByName().get(i).getPeselDrv());
                person.setSurname(queryGetDriverByName().get(i).getSurnameDrv());
                person.setFirstName(queryGetDriverByName().get(i).getFirstNameDrv());
                person.setSecondName(queryGetDriverByName().get(i).getSecondNameDrv());
                person.setResidenceNr(Integer.toString(queryGetAdressByName(i).get(0).getResidenceNr()));
                person.setCity(queryGetAdressByName(i).get(0).getCity());
                person.setStreet(queryGetAdressByName(i).get(0).getStreet());
                person.setBuildingNr(queryGetAdressByName(i).get(0).getBuildingNr());
                person.setCategoryDL(queryGetDrivingLicenseByPesel(i).get(0).getKategoryDL());
                person.setCommentAuth(queryGetDrivingLicenseByPesel(i).get(0).getCommentAuth());
                person.setExpDateAuth(formattedDateTimeExp);
                person.setDateAuth(formattedDateTime);
            } catch (IndexOutOfBoundsException e) {
                new NewAlert("Error", "Blad wyszukwiania",
                        "Nie zostal znaleziony kierowca o peselu: " + queryGetDriverByName().get(i).getPeselDrv());
            }

            try {
                person.setPenalty(Long.toString(queryGetPenaltyDriver(i).get(0)));
            } catch (Exception e) {
                person.setPenalty("0");
            }

            tableView.getItems().add(person);
        }
    }

    public void nameSearchClicked() {

        if (queryGetDriverByName().size() != 0) {
            if (queryGetAdressByName(0).size() != 0) {
                makeTable();
                savePersonDataToTable();
                makeButton();
                checkWithDrawnAndTemporaryAuthorisationDL();
            } else {
                new NewAlert("Error", "Blad w wyszukiwaniu",
                        "Nie zostal znaleziony kierowca o takim adresie zameldowania");
            }
        } else {
            new NewAlert("Error", "Blad w wyszukiwaniu",
                    "Nie zostal znaleziony kierowca o takim imieniu i nazwisku");
        }
    }

    public List<Vehicle> queryGetOwnerVehiclesByPesel(String pesel) {
        return LoginScreen.entityManager
                .createQuery("SELECT vh FROM vehicle vh JOIN  vh.driverList dr WHERE dr.peselDrv=?1")
                .setParameter(1, pesel)
                .getResultList();
    }

    public void getOwnerVehiclesByPesel() {
        personVehicles = new ArrayList<String>();
        List<Vehicle> ownerVehicle = queryGetOwnerVehiclesByPesel(getPESEL());
        if (ownerVehicle.size() != 0) {
            for (Vehicle anOwnerVehicle : ownerVehicle) {
                personVehicles.add(anOwnerVehicle.getVin());
            }
        } else {
            new NewAlert("Information", "Blad w wyszukiwaniu",
                    "Kierowca nie posiada samochodow");
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
            if (getPESEL() == null) {
                new NewAlert("Error", "Blad wyswietlania pojazdow", "Nie zostal wybrany PESEL");
            } else {
                getOwnerVehiclesByPesel();
                super.searchCar(personVehicles, grid);
            }
        } else if (type.equals("LostPersonDocsTabPane")) {
            if (getPESEL() == null) {
                new NewAlert("Error", "Blad odbierania dokumentow", "Nie zostal wybrany PESEL");
            } else {
                LostPersonDocsTabController lostPersonDocsTabController = new LostPersonDocsTabController();
                lostPersonDocsTabController.withdrawnDrivingLicense(getPESEL());
            }
        } else if (type.equals("CreateTicketTabPane"))
            super.addCommentWindow(getPESEL(), text.getText());
    }
}

