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

public class ClientWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exitBtn;

    @FXML
    private Button addMarkBtn;


    @FXML
    private TableView<Mark> usersTable;

    @FXML
    private TableColumn<Mark, Integer> idTab;

    @FXML
    private TableColumn<Mark, String> nameTab;

    @FXML
    private TableColumn<Mark, String> surnameTab;

    @FXML
    private TableColumn<Mark, String> groupTab;

    @FXML
    private TableColumn<Mark, String> subTab;

    @FXML
    private TableColumn<Mark, String> markTab;

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField groupField;

    @FXML
    private TextField subField;

    @FXML
    private TextField markField;

    @FXML
    private Button chooseButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button redactionButton;

    @FXML
    private Button studBtn;

    private final ObservableList<Mark> listTask = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        exitBtn.setOnAction(actionEvent -> {
            Windows.windows.newWindow("views/sample.fxml", exitBtn);
        });

        studBtn.setOnAction(actionEvent -> {
            Windows.windows.newWindow("views/students.fxml", studBtn);
        });

        try {
            initMarks(showAllMarks());
        } catch (IOException e) {
            e.printStackTrace();
        }

        chooseButton.setOnAction(event -> {
            initFieldToRedaction();
        });

        redactionButton.setOnAction(event -> {
            if(usersTable.getSelectionModel().getSelectedItem() != null) {
                Client.connectionToServer.sendMsg("redactionMark");
                Client.connectionToServer.sendMsg(idField.getText()+" "+nameField.getText()+" "+surnameField.getText()+" "
                        + groupField.getText()+" "+subField.getText()+" " +markField.getText());
                try {
                    initMarks(showAllMarks());
                    idField.setText("");
                    nameField.setText("");
                    surnameField.setText("");
                    groupField.setText("");
                    subField.setText("");
                    markField.setText("");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                showMessage("Выберите строку !");
            }
        });

        deleteButton.setOnAction(actionEvent -> {
            if(usersTable.getSelectionModel().getSelectedItem() != null) {
                    Client.connectionToServer.sendMsg("deleteMark");
                    Client.connectionToServer.sendMsg(idField.getText());
                    try {
                        initMarks(showAllMarks());
                        idField.setText("");
                        nameField.setText("");
                        surnameField.setText("");
                        groupField.setText("");
                        subField.setText("");
                        markField.setText("");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            else{
                showMessage("Выберите строку !");
            }
        });

        addMarkBtn.setOnAction(actionEvent -> {
            Windows.windows.newWindow("views/addMark.fxml", addMarkBtn);
        });

    }

    private void initMarks(LinkedList<Mark> listDb){
        listTask.clear();
        listTask.addAll(listDb);

        idTab.setCellValueFactory(new PropertyValueFactory<Mark, Integer>("id"));
        nameTab.setCellValueFactory(new PropertyValueFactory<Mark, String>("name"));
        surnameTab.setCellValueFactory(new PropertyValueFactory<Mark, String>("surname"));
        groupTab.setCellValueFactory(new PropertyValueFactory<Mark, String>("group_number"));
        subTab.setCellValueFactory(new PropertyValueFactory<Mark, String>("subject"));
        markTab.setCellValueFactory(new PropertyValueFactory<Mark, String>("mark"));

        usersTable.setItems(listTask);
    }

    private void initFieldToRedaction(){
        LinkedList<Mark> listUsersDb = new LinkedList<>();
        listUsersDb.addAll(listTask);
        if(usersTable.getSelectionModel().getSelectedItem() != null) {
            int count = usersTable.getSelectionModel().getSelectedCells().get(0).getRow();
            idField.setEditable(false);
            idField.setText(String.valueOf(listTask.get(count).getId()));
            nameField.setText(listTask.get(count).getName());
            surnameField.setText(listTask.get(count).getSurname());
            groupField.setText(listTask.get(count).getGroup_number());
            subField.setText(listTask.get(count).getSubject());
            markField.setText(listTask.get(count).getMark());
        }
    }

    private LinkedList<Mark> showAllMarks() throws IOException {
        Client.connectionToServer.sendMsg("showMarks");
        return Client.connectionToServer.showMarks();
    }

    private void showMessage(String massage){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success ...");
        alert.setHeaderText(null);
        alert.setContentText(massage);
        alert.showAndWait();
    }
}
