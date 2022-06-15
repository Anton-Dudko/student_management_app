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
import sample.model.Mark;
import sample.model.Student;

public class StudentsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Student> usersTable;

    @FXML
    private TableColumn<Student, Integer> idTab;

    @FXML
    private TableColumn<Student, String> nameTab;

    @FXML
    private TableColumn<Student, String> surnameTab;

    @FXML
    private TableColumn<Student, String> facTab;

    @FXML
    private TableColumn<Student, String> specTab;

    @FXML
    private TableColumn<Student, String> groupTab;

    @FXML
    private Button exitBtn;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField facField;

    @FXML
    private TextField specField;

    @FXML
    private TextField groupField;

    @FXML
    private Button addBtn;

    private final ObservableList<Student> listStud = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        exitBtn.setOnAction(actionEvent -> {
            Windows.windows.newWindow("views/clientWindow.fxml", exitBtn);
        });

        try {
            initTasks(showAllStudents());
        } catch (IOException e) {
            e.printStackTrace();
        }

        addBtn.setOnAction(actionEvent -> {
            addNewStudent();
            try {
                initTasks(showAllStudents());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private void initTasks(LinkedList<Student> listDb){
        listStud.clear();
        listStud.addAll(listDb);

        idTab.setCellValueFactory(new PropertyValueFactory<Student, Integer>("id"));
        nameTab.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        surnameTab.setCellValueFactory(new PropertyValueFactory<Student, String>("surname"));
        facTab.setCellValueFactory(new PropertyValueFactory<Student, String>("faculty"));
        specTab.setCellValueFactory(new PropertyValueFactory<Student, String>("specialty"));
        groupTab.setCellValueFactory(new PropertyValueFactory<Student, String>("group_number"));

        usersTable.setItems(listStud);
    }

    private LinkedList<Student> showAllStudents() throws IOException {
        Client.connectionToServer.sendMsg("showStudents");
        return Client.connectionToServer.showStudents();
    }

    private void addNewStudent(){
        if(validation()) {
            Client.connectionToServer.sendMsg("addStudent");
            Client.connectionToServer.sendMsg(nameField.getText() + " " + surnameField.getText() + " " + facField.getText() + " " + specField.getText() + " " + groupField.getText());
            if(Client.connectionToServer.checkAddUserInDb()){
                showMessage("Студент добавлен !");
                nameField.setText("");
                surnameField.setText("");
                groupField.setText("");
                specField.setText("");
                facField.setText("");
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
                !facField.getText().equals("") || !specField.getText().equals("") || !groupField.getText().equals("")){
            return true;
        }
        return false;
    }
}
