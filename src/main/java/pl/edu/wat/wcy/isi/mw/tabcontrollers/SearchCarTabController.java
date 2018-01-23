package pl.edu.wat.wcy.isi.mw.tabcontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;

public class SearchCarTabController extends CarTabController{

    @FXML GridPane SearchCarTabPane;
    @FXML ComboBox <String> SearchMethodComboBox;
    private GridPane prevSearchPane = null;

    public void changeSearchMethod() {
        String searchMethod = SearchMethodComboBox.getValue();
        GridPane newSearch = super.getSearch(searchMethod);
        if (newSearch != null) {
            if (prevSearchPane != null) SearchCarTabPane.getChildren().remove(prevSearchPane);
            if (SearchCarTabPane != null)
                SearchCarTabPane.add(newSearch,0,2,1,2);
            else System.exit(0);
            prevSearchPane = newSearch;
        }
    }
}
