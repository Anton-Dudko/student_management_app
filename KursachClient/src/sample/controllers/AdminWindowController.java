package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Client;
import sample.Windows;
import sample.model.User;

public class AdminWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private Button exitBtn;

    @FXML
    private Button facultiesBtn;

    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, Integer> idUserTab;

    @FXML
    private TableColumn<User, String> emailTab;

    @FXML
    private TableColumn<User, String> nameTab;

    @FXML
    private TableColumn<User, String> surnameTab;

    @FXML
    private TableColumn<User, String> lastnameTab;

    @FXML
    private TableColumn<User, String> passwordTab;

    @FXML
    private TableColumn<User, Integer> rollTab;

    @FXML
    private TextField idField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField rollField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField lastnameField;

    @FXML
    private Button chooseButton;

    @FXML
    private Button redactionButton;

    @FXML
    private Button deleteAccountButton;
    @FXML
    private Button addTeacherBtn;


    private final ObservableList<User> listUsers = FXCollections.observableArrayList();

    @FXML
    void initialize() {


        exitBtn.setOnAction(actionEvent -> {
            Windows.windows.newWindow("views/sample.fxml", exitBtn);
        });

        addTeacherBtn.setOnAction(actionEvent -> {
            Windows.windows.newWindow("views/addTeacher.fxml", addTeacherBtn);
        });

        try {
            initUsers(showAllUsers());
        } catch (IOException e) {
            e.printStackTrace();
        }

        chooseButton.setOnAction(event -> {
            initFieldToRedaction();
        });

        redactionButton.setOnAction(event -> {
            if(usersTable.getSelectionModel().getSelectedItem() != null) {
                Client.connectionToServer.sendMsg("redactionUser");
                Client.connectionToServer.sendMsg(idField.getText()+" "+nameField.getText()+" "+surnameField.getText()+" "
                        + lastnameField.getText()+" "+emailField.getText()+" " +passwordField.getText()+" "+rollField.getText());
                try {
                    initUsers(showAllUsers());
                    idField.setText("");
                    nameField.setText("");
                    surnameField.setText("");
                    lastnameField.setText("");
                    emailField.setText("");
                    passwordField.setText("");
                    rollField.setText("");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                showMessage("Выберите строку !");
            }
        });

        deleteAccountButton.setOnAction(actionEvent -> {
            if(usersTable.getSelectionModel().getSelectedItem() != null) {
                if(!User.currentUser.getEmail().equals(emailField.getText())){
                    Client.connectionToServer.sendMsg("deleteUser");
                    Client.connectionToServer.sendMsg(idField.getText());
                    try {
                        initUsers(showAllUsers());
                        idField.setText("");
                        nameField.setText("");
                        surnameField.setText("");
                        lastnameField.setText("");
                        emailField.setText("");
                        passwordField.setText("");
                        rollField.setText("");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    showMessage("Вы не можете удалить свой аккаунт !");
                    idField.setText("");
                    emailField.setText("");
                    passwordField.setText("");
                    rollField.setText("");
                }
            }
            else{
                showMessage("Выберите строку !");
            }
        });

        facultiesBtn.setOnAction(actionEvent -> {
            Windows.windows.newWindow("views/faculties.fxml", facultiesBtn);
        });

    }

    private LinkedList<User> showAllUsers() throws IOException {
        Client.connectionToServer.sendMsg("showAllUser");
        return Client.connectionToServer.showAllUsers();
    }

    private void initUsers(LinkedList<User> listDb){
        listUsers.clear();
        listUsers.addAll(listDb);
        idUserTab.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        nameTab.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        surnameTab.setCellValueFactory(new PropertyValueFactory<User, String>("surname"));
        lastnameTab.setCellValueFactory(new PropertyValueFactory<User, String>("lastname"));
        emailTab.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        passwordTab.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        rollTab.setCellValueFactory(new PropertyValueFactory<User, Integer>("roll"));
        usersTable.setItems(listUsers);
    }

    private void initFieldToRedaction(){
        LinkedList<User> listUsersDb = new LinkedList<>();
        listUsersDb.addAll(listUsers);
        if(usersTable.getSelectionModel().getSelectedItem() != null) {
            int count = usersTable.getSelectionModel().getSelectedCells().get(0).getRow();
            idField.setEditable(false);
            idField.setText(String.valueOf(listUsers.get(count).getId()));
            nameField.setText(listUsers.get(count).getName());
            surnameField.setText(listUsers.get(count).getSurname());
            lastnameField.setText(listUsers.get(count).getLastname());
            emailField.setText(listUsers.get(count).getEmail());
            passwordField.setText(listUsers.get(count).getPassword());
            rollField.setText(String.valueOf(listUsers.get(count).getRoll()));
        }
    }

    private void showMessage(String massage){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success ...");
        alert.setHeaderText(null);
        alert.setContentText(massage);
        alert.showAndWait();
    }
}


