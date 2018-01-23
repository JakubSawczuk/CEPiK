package pl.edu.wat.wcy.isi.mw.tabcontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;

public class SearchPersonTabController extends PersonTabController{

    @FXML GridPane SearchPersonTabPane;
    @FXML ComboBox <String> SearchMethodComboBox;
    private GridPane prevSearchPane = null;

    public void changeSearchMethod() {
        String searchMethod = SearchMethodComboBox.getValue();
        GridPane newSearch = super.getSearch(searchMethod);
        if (newSearch != null) {
            if (prevSearchPane != null) SearchPersonTabPane.getChildren().remove(prevSearchPane);
            if (SearchPersonTabPane != null)
                SearchPersonTabPane.add(newSearch,0,2,1,2);
            else System.exit(0);
            prevSearchPane = newSearch;
        }
    }
}
