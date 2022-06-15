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

public class AddMarkController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField groupField;

    @FXML
    private Button addMarkButton;

    @FXML
    private TextField subjectField;

    @FXML
    private TextField markField;

    @FXML
    private Button exitButton;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField nameField;

    @FXML
    void initialize() {
        exitButton.setOnAction(actionEvent -> {
            Windows.windows.newWindow("views/clientWindow.fxml", exitButton);
        });

        addMarkButton.setOnAction(actionEvent -> {
            addNewMark();
        });

    }

    private void addNewMark(){
        if(validation()) {
            Client.connectionToServer.sendMsg("addMark");
            Client.connectionToServer.sendMsg(nameField.getText() + " " + surnameField.getText() + " " + groupField.getText() + " " + subjectField.getText() + " " + markField.getText());
            if(Client.connectionToServer.checkAddUserInDb()){
                showMessage("Оценка поставлена !");
                nameField.setText("");
                surnameField.setText("");
                groupField.setText("");
                subjectField.setText("");
                markField.setText("");
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
                !groupField.getText().equals("") || !subjectField.getText().equals("") || !markField.getText().equals("")){
            return true;
        }
        return false;
    }
}
