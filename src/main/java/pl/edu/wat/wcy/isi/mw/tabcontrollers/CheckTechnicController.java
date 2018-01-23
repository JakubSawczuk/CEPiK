package pl.edu.wat.wcy.isi.mw.tabcontrollers;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.hibernate.HibernateException;
import pl.edu.wat.wcy.isi.mw.LoginScreen;
import pl.edu.wat.wcy.isi.mw.NewAlert;
import pl.edu.wat.wcy.isi.mw.SearchController;
import pl.edu.wat.wcy.isi.mw.TabRow;
import pl.edu.wat.wcy.isi.mw.database.entity.TechnicalTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CheckTechnicController extends SearchController {
    public GridPane CheckTechnicTabPane;
    public TextField VINNumber;


    private List<TechnicalTest> queryGetTechnicalTestyVIN() {
        return LoginScreen.entityManager
                .createQuery("SELECT e FROM technicaltest e WHERE vin = ?1")
                .setParameter(1, VINNumber.getText())
                .getResultList();
    }

    public void Search() {
        try {
            makeTechnicTable(queryGetTechnicalTestyVIN().get(0));
        } catch (HibernateException e) {
            NewAlert newAlert = new NewAlert("Error", "Brak połączenia", "Sprawdz połączenie internetowe");
        } catch (IndexOutOfBoundsException e) {
            NewAlert newAlert = new NewAlert("Error", "Błąd w wyszukiwaniu", "Sprawdz poprawność wpisanego VINu");
        }
    }

    private void makeTechnicTable(TechnicalTest technicalTest) {
        boolean validity = LocalDateTime.now().isAfter(technicalTest.getExpirationDateTest());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime dateTime = technicalTest.getDateTest();
        LocalDateTime dateTimeExp = technicalTest.getExpirationDateTest();
        String formattedDateTime = dateTime.format(formatter);
        String formattedDateTimeExp = dateTimeExp.format(formatter);

        TableView<TabRow> tableView = super.newTable(CheckTechnicTabPane, 2);
        tableView.getItems().add(new TabRow("VIN", VINNumber.getText()));
        tableView.getItems().add(new TabRow("Data zrobienia przegladu technicznego", formattedDateTime));
        tableView.getItems().add(new TabRow("Data wygasniecia przegladu technicznego", formattedDateTimeExp));
        tableView.getItems().add(new TabRow("Zapisane kilometry pod czas ostatniego przeladu technicznego",
                Integer.toString(technicalTest.getMileAge())));
        tableView.getItems().add(new TabRow("Uwagi", technicalTest.getClauses()));

        if (validity) {
            NewAlert newAlert = new NewAlert("Information", "Niewazne badania techniczne",
                    "Badania techniczne samochodu jest niewazne");
        }
    }
}
