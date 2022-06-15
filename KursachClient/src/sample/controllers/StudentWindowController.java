package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Client;
import sample.Windows;
import sample.model.Mark;

public class StudentWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Mark> usersTable;

    @FXML
    private TableColumn<Mark, String> nameTab;

    @FXML
    private TableColumn<Mark, String> surnameTab;

    @FXML
    private TableColumn<Mark, String> groupNumTab;

    @FXML
    private TableColumn<Mark, String> subjectTab;

    @FXML
    private TableColumn<Mark, String> markTab;

    @FXML
    private Button exitButton;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField groupField;

    @FXML
    private Button findBtn;

    private final ObservableList<Mark> listTask = FXCollections.observableArrayList();

    @FXML
    void initialize() {

        exitButton.setOnAction(actionEvent -> {
            Windows.windows.newWindow("views/sample.fxml", exitButton);
        });

        findBtn.setOnAction(actionEvent -> {
            if(surnameField.getText().equals("") || groupField.getText().equals("")){
                try {
                    initTasks(showAllMarks());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                try {
                    initTasks(showMarks());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            initTasks(showAllMarks());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initTasks(LinkedList<Mark> listDb){
        listTask.clear();
        listTask.addAll(listDb);

        nameTab.setCellValueFactory(new PropertyValueFactory<Mark, String>("name"));
        surnameTab.setCellValueFactory(new PropertyValueFactory<Mark, String>("surname"));
        groupNumTab.setCellValueFactory(new PropertyValueFactory<Mark, String>("group_number"));
        subjectTab.setCellValueFactory(new PropertyValueFactory<Mark, String>("subject"));
        markTab.setCellValueFactory(new PropertyValueFactory<Mark, String>("mark"));

        usersTable.setItems(listTask);
    }

    private LinkedList<Mark> showAllMarks() throws IOException {
        Client.connectionToServer.sendMsg("showMarks");
        return Client.connectionToServer.showMarks();
    }

    private LinkedList<Mark> showMarks() throws IOException {
        Client.connectionToServer.sendMsg("showMarksByGroup");
        Client.connectionToServer.sendMsg(surnameField.getText() + " " + groupField.getText());
        return Client.connectionToServer.showMarks();
    }
}
