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
import sample.model.Faculty;
import sample.model.Specialty;

public class FacultiesController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Faculty> fucTable;

    @FXML
    private TableColumn<Faculty, String> faclTab;

    @FXML
    private Button exitButton;

    @FXML
    private TableView<Specialty> specTable;

    @FXML
    private TableColumn<Specialty, String> specTab;

    @FXML
    private Button addBtn;

    @FXML
    private TextField facField;

    @FXML
    private TextField specField;

    @FXML
    private TextField findfacField;

    @FXML
    private Button findBtn;


    private final ObservableList<Faculty> listFac = FXCollections.observableArrayList();
    private final ObservableList<Specialty> listSpec = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        exitButton.setOnAction(actionEvent -> {
            Windows.windows.newWindow("views/adminWindow.fxml", exitButton);
        });

        findBtn.setOnAction(actionEvent -> {
            if(findfacField.getText().equals("")){
                try {
                    initSpec(showAllSpec());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                try {
                    initSpec(showSpec());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            initFaculties(showAllFaculties());
            initSpec(showAllSpec());
        } catch (IOException e) {
            e.printStackTrace();
        }

        addBtn.setOnAction(actionEvent -> {
            addNewSpec();
            facField.setText("");
            specField.setText("");
            try {
                initSpec(showAllSpec());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void addNewSpec(){
        if(validation()) {
            Client.connectionToServer.sendMsg("addSpec");
            Client.connectionToServer.sendMsg(specField.getText() + " " + facField.getText());
            if(Client.connectionToServer.checkAddUserInDb()){
                showMessage("Специальность добавлена !");
                facField.setText("");
                specField.setText("");
            }
            else {
                showMessage("Что то пошло не так !");
            }
        }else{
            showMessage("Заполните пустые поля !");
        }
    }

    private void initFaculties(LinkedList<Faculty> listDb){
        listFac.clear();
        listFac.addAll(listDb);

        faclTab.setCellValueFactory(new PropertyValueFactory<Faculty, String>("title"));

        fucTable.setItems(listFac);
    }

    private LinkedList<Faculty> showAllFaculties() throws IOException {
        Client.connectionToServer.sendMsg("showFaculties");
        return Client.connectionToServer.showAllFaculties();
    }

    private void initSpec(LinkedList<Specialty> listDb){
        listSpec.clear();
        listSpec.addAll(listDb);

        specTab.setCellValueFactory(new PropertyValueFactory<Specialty, String>("title"));

        specTable.setItems(listSpec);
    }

    private LinkedList<Specialty> showAllSpec() throws IOException {
        Client.connectionToServer.sendMsg("showSpec");
        return Client.connectionToServer.showAllSpec();
    }

    private LinkedList<Specialty> showSpec() throws IOException {
        Client.connectionToServer.sendMsg("showSpecByFac");
        Client.connectionToServer.sendMsg(findfacField.getText());
        return Client.connectionToServer.showAllSpec();
    }

    private void showMessage(String massage){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success ...");
        alert.setHeaderText(null);
        alert.setContentText(massage);
        alert.showAndWait();
    }

    private boolean validation (){
        if(!facField.getText().equals("") || !specField.getText().equals("")){
            return true;
        }
        return false;
    }
}
