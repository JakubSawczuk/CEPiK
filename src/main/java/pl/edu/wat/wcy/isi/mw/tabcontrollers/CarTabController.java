package pl.edu.wat.wcy.isi.mw.tabcontrollers;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import pl.edu.wat.wcy.isi.mw.NewAlert;
import pl.edu.wat.wcy.isi.mw.ProgramController;

import java.io.IOException;
import java.net.URL;

class CarTabController {

    GridPane getSearch(String searchMethod) {
        GridPane newSearch = null;
        try {
            if (searchMethod.equals("Numer VIN"))
                newSearch = getGridPane("searchmethods/SearchByVIN.fxml");
            else if (searchMethod.equals("Numer rejestracyjny"))
                newSearch = getGridPane("searchmethods/SearchByRegisterNumber.fxml");
        }catch (NullPointerException e) {
            new NewAlert("Error", "Blad wybierania kryteriow szukania", "Wybierz kryterium szukania");
        }
        return newSearch;
    }

    private GridPane getGridPane(String name) {
        GridPane newSearch = null;
        try {
            URL url = ProgramController.loadFXML(name);
            newSearch = FXMLLoader.load(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newSearch;
    }
}
