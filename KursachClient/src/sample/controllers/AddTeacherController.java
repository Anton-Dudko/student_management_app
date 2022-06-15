package sample.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.Client;
import sample.Windows;
import sample.model.User;

public class AddTeacherController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button editProfileButton;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button exitButton;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField lastnameField;

    @FXML
    void initialize() {
        exitButton.setOnAction(actionEvent -> {
            Windows.windows.newWindow("views/adminWindow.fxml", exitButton);
        });

        editProfileButton.setOnAction(actionEvent -> {
            addNewTeacher();
        });
    }


    private void addNewTeacher(){
        if(validation()) {
            Client.connectionToServer.sendMsg("addUser");
            Client.connectionToServer.sendMsg(nameField.getText() + " " + surnameField.getText() + " " + lastnameField.getText() + " " + emailField.getText() + " " + passwordField.getText());
            if(Client.connectionToServer.checkAddUserInDb()){
                showMessage("Преподаватель успешно добавлен !");
                nameField.setText("");
                surnameField.setText("");
                lastnameField.setText("");
                emailField.setText("");
                passwordField.setText("");
            }
            else {
                showMessage("Что то пошло не так !");
            }
        }else{
            showMessage("Заполните пустые поля !");
        }
    }


    private void showMessage(String massage){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success ...");
        alert.setHeaderText(null);
        alert.setContentText(massage);
        alert.showAndWait();
    }

    private boolean validation (){
        if(!nameField.getText().equals("") || !surnameField.getText().equals("") ||
                !lastnameField.getText().equals("") || !emailField.getText().equals("") || !passwordField.getText().equals("")){
            return true;
        }
        return false;
    }
}
