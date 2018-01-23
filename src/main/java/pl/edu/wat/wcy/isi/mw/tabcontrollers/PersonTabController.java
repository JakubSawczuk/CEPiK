package pl.edu.wat.wcy.isi.mw.tabcontrollers;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import pl.edu.wat.wcy.isi.mw.ProgramController;
import java.io.IOException;

class PersonTabController {

    GridPane getSearch(String searchMethod) {
        GridPane newSearch = null;
        if (searchMethod.equals("Numer PESEL"))
                newSearch = getGridPane("searchmethods/SearchByPESEL.fxml");
        else if (searchMethod.equals("Dane osobowe"))
            newSearch = getGridPane("searchmethods/SearchByName.fxml");
        return newSearch;
    }

    private GridPane getGridPane(String location) {
        GridPane newSearch = null;
        try {
            newSearch = FXMLLoader.load(ProgramController.loadFXML(location));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newSearch;
    }
}