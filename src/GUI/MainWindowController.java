package GUI;

import Controller.Controller;
import Model.ADTs.IStack;
import Model.ADTs.ITuple;
import Model.Exceptions.MyException;
import Model.ProgramState.ProgramState;
import Model.Statements.IStatement;
import Model.Values.StringValue;
import Model.Values.Value;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;


public class MainWindowController implements Initializable{

    @FXML
    private TableView<Map.Entry<Integer, ITuple>> semaphoreTableView;
    @FXML
    private TableColumn<Map.Entry<Integer, ITuple>,String> semaphoreTableIndexes;
    @FXML
    private TableColumn<Map.Entry<Integer, ITuple>,String> semaphoreTableTuples;
    @FXML
    private TableView<Map.Entry<Integer, Pair<Integer, List<Integer>>>> barrierTableView;
    @FXML
    private TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, String> barrierTableIndexes;
    @FXML
    private TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, String> barrierTableValues;
    @FXML
    private TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, String> barrierTableList;
    @FXML
    private TableView<Map.Entry<Integer, Integer>> latchTableView;
    @FXML
    private TableColumn<Map.Entry<Integer, Integer>, String> latchTableLocations;
    @FXML
    private TableColumn<Map.Entry<Integer, Integer>, String> latchTableValue;
    @FXML
    private ListView<String> exeStackListView;
    @FXML
    private ListView<String> outListView;
    @FXML
    private ListView<Integer> prgStateIdsListView;
    @FXML
    private ListView<String> fileTableListView;
    @FXML
    private Button oneStepButton;
    @FXML
    private TableView<Map.Entry<Integer, Value>> heapTableTableView;
    @FXML
    private TableView<Map.Entry<String, Value>> symTableTableView;
    @FXML
    private TableColumn<Map.Entry<Integer, Value>, Integer> heapTableAddr;
    @FXML
    private TableColumn<Map.Entry<Integer, Value>, String> heapTableVal;
    @FXML
    private TableColumn<Map.Entry<Integer, Value>, String> symTableVar;
    @FXML
    private TableColumn<Map.Entry<Integer, Value>, String> symTableVal;
    @FXML
    private TextField noOfPrgStatesTextField;

    private Controller controller;

    public Controller getController(){
        return this.controller;
    }

    public void setController(Controller ctrl){
        this.controller = ctrl;
        populateNoOfPrgStatesTextField();
        populatePrgStateIdsListView();
        oneStepButton.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        this.controller = null;
        //heap
        heapTableAddr.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        heapTableVal.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue() + " "));
        //sym table
        symTableVar.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey() + " "));
        symTableVal.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue() + " "));

        latchTableLocations.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getValue() + " "));
        latchTableValue.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getValue() + " "));
        oneStepButton.setDisable(true);

        barrierTableIndexes.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getKey() + " "));
        barrierTableList.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getValue().getValue() + " "));
        barrierTableValues.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getValue().getKey() + " "));

        semaphoreTableIndexes.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getKey()+ " "));
        semaphoreTableTuples.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getValue() + " "));
    }

    private void changeProgramStateHandler(ProgramState currentPrgState){
        if(currentPrgState == null)
            return;
        try{
            populateNoOfPrgStatesTextField();
            populatePrgStateIdsListView();
            populateHeapTableTableView(currentPrgState);
            populateOutListView(currentPrgState);
            populateFileTableListView(currentPrgState);
            populateExeStackListView(currentPrgState);
            populateSymTableTableView(currentPrgState);
            populateLatchTableView(currentPrgState);
            populateBarrierTableView(currentPrgState);
            populateSemaphoreTableView(currentPrgState);
        }
        catch(MyException e){
            Alert error = new Alert(Alert.AlertType.ERROR, e.getMessage());
            error.show();
        }
    }

    public void oneStepHandler(ActionEvent actionEvent){
        if(controller == null){
            Alert error = new Alert(Alert.AlertType.ERROR, "No program was selected!");
            error.show();
            oneStepButton.setDisable(true);
            return;
        }
        ProgramState prgState = getSelectedPrgState();
        if(prgState != null && !prgState.isNotCompleted()){
            Alert error = new Alert(Alert.AlertType.ERROR, "Nothing left to execute!");
            error.show();
            return;
        }
        try{
            controller.executeOneStep();
            changeProgramStateHandler(prgState);
            if(controller.getRepo().getPrgList().size()==0)
                oneStepButton.setDisable(true);
        }
        catch(MyException e){
            Alert error = new Alert(Alert.AlertType.ERROR, e.getMessage());
            error.show();
        }
    }

    private void populateNoOfPrgStatesTextField(){
        noOfPrgStatesTextField.setText(String.valueOf(controller.getRepo().getPrgList().size()));
    }

    private void populatePrgStateIdsListView(){
        prgStateIdsListView.setItems(FXCollections.observableArrayList(controller.getRepo().getPrgList().stream().map(ProgramState::getId).collect(Collectors.toList())));
        prgStateIdsListView.refresh();
    }

    private void populateHeapTableTableView(ProgramState prgState){
        heapTableTableView.setItems(FXCollections.observableList(new ArrayList<>(prgState.getHeap().getContent().entrySet())));
        heapTableTableView.refresh();
    }

    private void populateOutListView(ProgramState prgState) throws MyException{
        outListView.setItems(FXCollections.observableArrayList(prgState.getOutput().getContent()));
    }

    private void populateFileTableListView(ProgramState prgState){
        Map<StringValue, BufferedReader> hm = prgState.getFileTable().getContent();
        String s = hm.keySet().toString();
        fileTableListView.setItems(FXCollections.observableArrayList(s));
        //fileTableView.setItems(FXCollections.observableArrayList(givenProgramState.getFileTable().getContent().keySet()));
    }

    private void populateExeStackListView(ProgramState prgState){
        IStack<IStatement> stack = prgState.getExecutionStack();
        List<String> stackOutput = new ArrayList<>();
        for(IStatement stm : stack.getValues()){
            stackOutput.add(stm.toString());
        }
        Collections.reverse(stackOutput);
        exeStackListView.setItems(FXCollections.observableArrayList(stackOutput));
    }

    private void populateSymTableTableView(ProgramState prgState){
        symTableTableView.setItems(FXCollections.observableList(new ArrayList<>(prgState.getSymbolTable().getContent().entrySet())));
        symTableTableView.refresh();
    }

    private void populateLatchTableView(ProgramState givenProgramState){
        latchTableView.setItems(FXCollections.observableList(new ArrayList<>(givenProgramState.getLatchTable().getLatchTable().entrySet())));
        latchTableView.refresh();
    }

    private void populateBarrierTableView(ProgramState givenProgramState){
        barrierTableView.setItems(FXCollections.observableList(new ArrayList<>(givenProgramState.getBarrierTable().getBarrierTable().entrySet())));
        barrierTableView.refresh();
    }

    private void populateSemaphoreTableView(ProgramState givenProgramState){
        semaphoreTableView.setItems(FXCollections.observableList(new ArrayList<>(givenProgramState.getSemaphoreTable().getSemaphoreTable().getContent().entrySet())));
        semaphoreTableView.refresh();
    }

    private ProgramState getSelectedPrgState(){
        if(prgStateIdsListView.getSelectionModel().getSelectedIndex()==-1)
            return null;
        int id = prgStateIdsListView.getSelectionModel().getSelectedItem();
        return controller.getRepo().getProgramById(id);
    }
}
