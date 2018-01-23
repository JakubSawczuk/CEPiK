package pl.edu.wat.wcy.isi.mw.searchmethods;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import pl.edu.wat.wcy.isi.mw.LoginScreen;
import pl.edu.wat.wcy.isi.mw.NewAlert;
import pl.edu.wat.wcy.isi.mw.SearchController;
import pl.edu.wat.wcy.isi.mw.TabRow;
import pl.edu.wat.wcy.isi.mw.database.entity.Adress;
import pl.edu.wat.wcy.isi.mw.database.entity.Driver;
import pl.edu.wat.wcy.isi.mw.database.entity.Vehicle;
import pl.edu.wat.wcy.isi.mw.tabcontrollers.LostPersonDocsTabController;

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


    public List<Adress> queryGetAdressByPesel() {
        return LoginScreen.entityManager
                .createQuery("Select ad from driver d join d.adress  ad where d.peselDrv=?1")
                .setParameter(1, PESELnumber.getText())
                .getResultList();
    }

    public List<Driver> queryGetDriverByPesel() {
        return LoginScreen.entityManager
                .createQuery("SELECT e FROM driver e WHERE e.peselDrv = ?1")
                .setParameter(1, PESELnumber.getText())
                .getResultList();
    }

    public void PESELsearchClicked() {
        if (queryGetDriverByPesel().size() != 0) {
            makeTable(queryGetDriverByPesel().get(0), queryGetAdressByPesel().get(0));
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

    public void getOwnerVehiclesByPesel(){
        personVehicles = new ArrayList<String>();
        List<Vehicle> ownerVehicle = queryGetOwnerVehiclesByPesel();
        if(ownerVehicle.size() != 0) {
            for (Vehicle anOwnerVehicle : ownerVehicle) {
                personVehicles.add(anOwnerVehicle.getVin());
            }
        }else {
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
            getOwnerVehiclesByPesel();
            super.searchCar(personVehicles, grid);
        } else if (type.equals("LostPersonDocsTabPane")) {
            LostPersonDocsTabController lostPersonDocsTabController = new LostPersonDocsTabController();
            lostPersonDocsTabController.withdrawnDrivingLicense(PESELnumber.getText());
            lostPersonDocsTabController.addTemporaryAuthorisation(PESELnumber.getText());
        } else if (type.equals("CreateTicketTabPane")) {
            super.addCommentWindow(PESELnumber.getText(), text.getText());
        }
    }

    private void makeTable(Driver driver, Adress adress) {
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
    }
}
