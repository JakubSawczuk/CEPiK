package pl.edu.wat.wcy.isi.mw.tabcontrollers;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.hibernate.HibernateException;
import pl.edu.wat.wcy.isi.mw.LoginScreen;
import pl.edu.wat.wcy.isi.mw.NewAlert;
import pl.edu.wat.wcy.isi.mw.SearchController;
import pl.edu.wat.wcy.isi.mw.TabRow;
import pl.edu.wat.wcy.isi.mw.database.entity.OC;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CheckOCController extends SearchController {
    public GridPane CheckOCTabPane;
    public TextField VINNumber;

    private List<OC> queryGetOCbyVIN() {
        return LoginScreen.entityManager
                .createQuery("SELECT e FROM oc e WHERE vin = ?1")
                .setParameter(1, VINNumber.getText())
                .getResultList();
    }

    private void makeOCTable(OC oc) {

        boolean validity = LocalDateTime.now().isAfter(oc.getoCExpirationDate());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime dateTime = oc.getoCDate();
        LocalDateTime dateTimeExp = oc.getoCExpirationDate();
        String formattedDateTime = dateTime.format(formatter);
        String formattedDateTimeExp = dateTimeExp.format(formatter);

        TableView<TabRow> tableView = super.newTable(CheckOCTabPane, 2);
        tableView.getItems().add(new TabRow("VIN", VINNumber.getText()));
        tableView.getItems().add(new TabRow("Data zakupu OC", formattedDateTime));
        tableView.getItems().add(new TabRow("Data wygasniecia OC", formattedDateTimeExp));

        if (validity) {
            new NewAlert("Information", "Niewazne OC", "OC samochodu jest niewazne");
        }
    }

    public void Search() {
        try {
            makeOCTable(queryGetOCbyVIN().get(0));
        } catch (HibernateException e) {
            new NewAlert("Error", "Brak połączenia", "Sprawdz połączenie internetowe");
        } catch (IndexOutOfBoundsException e) {
            new NewAlert("Error", "Błąd w wyszukiwaniu", "Sprawdz poprawność wpisanego VINu");
        }

    }
}
