package pl.edu.wat.wcy.isi.mw;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;
import java.net.URL;

public class ProgramController {

    public static URL loadFXML(String name) {
        return LoginScreen.class.getClassLoader().getResource(name);
    }

    @FXML
    TabPane ProgramTabs;

    private void open(String name) {
        Tab tab = null;
        try {
            tab = FXMLLoader.load(ProgramController.loadFXML(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ProgramTabs.getTabs().add(tab);
    }

    public void openSearchCar() {
        open("tabs/SearchCarTab.fxml");
    }

    public void OpenDocsLost() {
        open("tabs/LostCarDocsTab.fxml");
    }

    public void openSearchPerson() {
        open("tabs/SearchPersonTab.fxml");
    }

    public void openCreateTicket() {
        open("tabs/CreateTicketTab.fxml");
    }

    public void openLostPersonDocs() {
        open("tabs/LostPersonDocsTab.fxml");
    }
}
