package pl.edu.wat.wcy.isi.mw;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.sql.SQLException;

public class LoginScreen extends Application {

    private static Stage stage;
    private static Scene menu;
    @FXML
    private TextField login;
    @FXML
    private PasswordField password;

    public static EntityManagerFactory entityManagerFactory;
    public static EntityManager entityManager;
    public static String peselPol;

    public void loginButton() {
        String userLogin = login.getText();
        String userPass = password.getText();
        if (userLogin.equals(userPass)) {
            try {
                peselPol = userLogin;
                entityManagerFactory = Persistence.createEntityManagerFactory("cepik");
                entityManager = entityManagerFactory.createEntityManager();
                stage.setScene(menu);
            } catch (Exception e) {
                NewAlert newAlert = new NewAlert("Error", "Brak polaczenia",
                        "Sprawdz polaczenie z internetem");
                newAlert.ErrorInLogin();
            }


            if (login.getText().length() == 0) {
                peselPol = "84025099595";
            } else {
                peselPol = login.getText();
            }

        } else
            new NewAlert("Error", "Błąd", "Wprowadzono złe hasło");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Parent parent = FXMLLoader.load(ProgramController.loadFXML("LoginScreen.fxml"));
        Scene loginScreen = new Scene(parent, 440, 329);
        parent = FXMLLoader.load(ProgramController.loadFXML("Program.fxml"));
        menu = new Scene(parent, 700, 500);
        primaryStage.setTitle("CEPiK");
        primaryStage.setScene(loginScreen);
        primaryStage.setResizable(false);
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                entityManager.close();
                entityManagerFactory.close();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}