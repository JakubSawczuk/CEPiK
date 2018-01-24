package pl.edu.wat.wcy.isi.mw;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.edu.wat.wcy.isi.mw.tabcontrollers.CreateTicketTabController;

import java.io.IOException;
import java.util.ArrayList;

public class SearchController {

    //Generuje nową tabelę (grid jako miejsce gdzie ma ise dodać)
    //rowIndex == 1 dla Modułów z searchmethods, == 2 z tabcontrollers
    protected TableView<TabRow> newTable(GridPane grid, int rowIndex) {
        TableView<TabRow> tableView = new TableView<TabRow>();
        tableView.setEditable(true);
        TableColumn<TabRow, String> first = new TableColumn<TabRow, String>("Nazwa parametru");
        first.setCellValueFactory(new PropertyValueFactory<TabRow, String>("first"));
        TableColumn<TabRow, String> second = new TableColumn<TabRow, String>("Wartość");
        second.setCellValueFactory(new PropertyValueFactory<TabRow, String>("second"));
        tableView.getColumns().add(first);
        tableView.getColumns().add(second);
        grid.add(tableView, 0, rowIndex, 3, 1);
        GridPane.setMargin(tableView, new Insets(0, 20, 5, 20));
        return tableView;
    }

    //Włącza ukryty przycisk (nazwa na przycisku i przycisk)
    protected void enableButton(String value, Button button) {
        button.setText(value);
        button.setVisible(true);
        button.setDisable(false);
    }

    //Włącza ukryty tekst (podpis, teskt)
    protected void enableText(String value, TextField text) {
        text.setPromptText(value);
        text.setEditable(true);
        text.setVisible(true);
    }

    //Potrzebne do innych metod, wydobywa wskaźnik do dodawania kart
    private static TabPane getTabPane(GridPane grid) {
        TabPane tabPane = null;
        for (Node n = grid.getParent(); n!= null && tabPane == null; n= n.getParent())
            if (n instanceof TabPane) tabPane = (TabPane)n;
        return tabPane;
    }

    //Potrzebne do innych metod, otwiera pliki FXML
    private Object openFXML (String name) {
        Object object = null;
        try { object = FXMLLoader.load(ProgramController.loadFXML(name));
        } catch (IOException e) { e.printStackTrace(); }
        return object;
    }

    //Tworzy nowe karty z wyszukaniami pojazdów (lista z VIN-ami, grid)
    protected void searchCar(ArrayList<String> stringArrayList, GridPane grid) {
        TabPane tabPane = SearchController.getTabPane(grid);
        for (String VIN : stringArrayList) {
            Tab tab = (Tab) openFXML("tabs/SearchCarTab.fxml");
            tabPane.getTabs().add(tab);
            GridPane newSearch = (GridPane) openFXML("searchmethods/SearchByVIN.fxml");
            ((GridPane)tab.getContent()).add(newSearch, 0, 2, 1, 2);
            for (Node node : newSearch.getChildren())
                if (node instanceof TextField) ((TextField) node).setText(VIN);
        }
    }

    //Tworzy nowe karty z wyszukaniami osób (lista z PESEL-ami, grid)
    protected void searchPerson(ArrayList<String> stringArrayList, GridPane grid) {
        TabPane tabPane = SearchController.getTabPane(grid);
        for (String pesel: stringArrayList) {
            Tab tab = (Tab)openFXML("tabs/SearchPersonTab.fxml");
            tabPane.getTabs().add(tab);
            GridPane newSearch = (GridPane)openFXML("searchmethods/SearchByPESEL.fxml");
            ((GridPane)tab.getContent()).add(newSearch, 0, 2, 1, 2);
            for (Node node : newSearch.getChildren())
                if (node instanceof TextField) ((TextField) node).setText(pesel);
        }
    }

    //Tworzy kartę szukania OC
    protected void searchOC (String VIN, GridPane grid) {
        TabPane tabPane = SearchController.getTabPane(grid);
        Tab tab = (Tab)openFXML("tabs/CheckOC.fxml");
        tabPane.getTabs().add(tab);
        for (Node node : ((GridPane)tab.getContent()).getChildren())
            if (node instanceof TextField) ((TextField) node).setText(VIN);
    }

    //Tworzy kartę szukania badania technicznego
    protected void searchTechnic (String VIN, GridPane grid) {
        TabPane tabPane = SearchController.getTabPane(grid);
        Tab tab = (Tab)openFXML("tabs/CheckTechnic.fxml");
        tabPane.getTabs().add(tab);
        for (Node node : ((GridPane)tab.getContent()).getChildren())
            if (node instanceof TextField) ((TextField) node).setText(VIN);
    }

    private void addComment(String comment, String PESEL, String points) {
        CreateTicketTabController createTicketTabController = new CreateTicketTabController();
        try {
            if(Integer.parseInt(points) < 0) throw new Exception();
            createTicketTabController.addMandate(comment, PESEL, points);
        }catch (Exception e){
            new NewAlert("Error", "Blad przy dodawaniu mandatu",
                    "Sprawdz poprawnosc danych");
        }
    }

    //Tworzy okienko do dodania komentarza
    protected void addCommentWindow (final String PESEL, final String points) {
        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(5);
        final Stage stage = new Stage();
        root.getChildren().add(new Label("Treść komentarza:"));
        final TextArea textArea = new TextArea();
        root.getChildren().add(textArea);
        Button button = new Button("Zapisz");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addComment(textArea.getText(), PESEL, points);
                stage.close();
            }
        });
        root.getChildren().add(button);
        Scene scene = new Scene(root, 320, 150);
        stage.setTitle("Komentarz");
        stage.setScene(scene);
        stage.show();
    }
}