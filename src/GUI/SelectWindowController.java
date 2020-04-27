package GUI;

import Controller.Controller;
import Model.ADTs.MyDictionary;
import Model.Exceptions.MyException;
import Model.Expressions.*;
import Model.ProgramState.ProgramState;
import Model.Statements.*;
import Model.Statements.Barrier.AwaitStatement;
import Model.Statements.Barrier.NewBarrierStatement;
import Model.Statements.Latch.AwaitLatchStatement;
import Model.Statements.Latch.CountDownLatchStatement;
import Model.Statements.Latch.NewLatchStatement;
import Model.Types.*;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;
import Repository.IRepository;
import Repository.Repository;
import com.sun.org.apache.xpath.internal.operations.Variable;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import jdk.nashorn.internal.ir.Assignment;
import Model.Statements.Semaphore.AcquireStatement;
import Model.Statements.Semaphore.NewSemaphoreStatement;
import Model.Statements.Semaphore.ReleaseStatement;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SelectWindowController implements Initializable{
    @FXML
    private Button runBttn;
    @FXML
    private ListView<IStatement> selectItemListView;

    private MainWindowController mainWindowController;

    public MainWindowController getMainWindowController(){
        return this.mainWindowController;
    }
    public void setMainWindowController(MainWindowController mwc){
        this.mainWindowController = mwc;
    }

    @FXML
    private IStatement selectExample(ActionEvent actionEvent){
        return selectItemListView.getItems().get(selectItemListView.getSelectionModel().getSelectedIndex());
    }

    private List<IStatement> initExamples(){
        IStatement ex1= new CompoundStatement(new VariableDeclarationStatement("v",new IntType()), new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v"))));
        IStatement ex2 = new CompoundStatement( new VariableDeclarationStatement("a",new IntType()),  new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),
                new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+',new ValueExpression(new IntValue(2)),new ArithmeticExpression('*',new ValueExpression(new IntValue(3)),
                        new ValueExpression(new IntValue(5))))),  new CompoundStatement(new AssignmentStatement("b",new ArithmeticExpression('+',new VariableExpression("a"),
                        new ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));
        IStatement ex3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()), new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true)))
                , new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignmentStatement("v",new ValueExpression(new IntValue(2))), new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));
        IStatement ex4 = new CompoundStatement(
                new VariableDeclarationStatement("varf",new StringType()),new CompoundStatement(
                new AssignmentStatement("varf",new ValueExpression(new StringValue("test.in"))),new CompoundStatement(
                new OpenFileStatement(new VariableExpression("varf")),new CompoundStatement(
                new VariableDeclarationStatement("varc",new IntType()),new CompoundStatement(
                new ReadFileStatement(new VariableExpression("varf"),"varc"),new CompoundStatement(
                new PrintStatement(new VariableExpression("varc")),new CompoundStatement(
                new ReadFileStatement(new VariableExpression("varf"),"varc") ,new CompoundStatement(new PrintStatement(new VariableExpression("varc")),new CloseFileStatement(new VariableExpression("varf"))))))))));
        IStatement ex5 = new CompoundStatement(
                new VariableDeclarationStatement("v",new IntType()),
                new CompoundStatement(
                        new AssignmentStatement("v",new ValueExpression(new IntValue(10))),
                        new WhileStatement(
                                new RelationalExpression(new VariableExpression("v"),new ValueExpression(new IntValue(0)),">"),
                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                        new AssignmentStatement( "v",new ArithmeticExpression('-',new VariableExpression("v"),new ValueExpression(new IntValue(1))))
                                )
                        )
                ));
        IStatement ex6 = new CompoundStatement(
                new VariableDeclarationStatement("v",new ReferenceType(new IntType())),
                new CompoundStatement(
                        new NewStatement("v",new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new PrintStatement(new HeapReadExpression(new VariableExpression("v"))), new CompoundStatement(
                                new VariableDeclarationStatement("a",new ReferenceType(new ReferenceType(new  IntType()))), new CompoundStatement(
                                new NewStatement("a",new VariableExpression("v")),new CompoundStatement(
                                new NewStatement("v",new ValueExpression(new IntValue(30))),
                                new PrintStatement(new ArithmeticExpression('+' ,new HeapReadExpression(new HeapReadExpression( new VariableExpression("a"))),new ValueExpression(new IntValue(5))))))))));
        IStatement ex7 = new CompoundStatement(
                new VariableDeclarationStatement("v",new ReferenceType(new IntType())),
                new CompoundStatement(
                        new NewStatement("v",new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new PrintStatement(new HeapReadExpression(new VariableExpression("v"))), new CompoundStatement(
                                new VariableDeclarationStatement("a",new ReferenceType(new ReferenceType(new  IntType()))), new CompoundStatement(
                                new NewStatement("a",new VariableExpression("v")),new CompoundStatement(
                                new HeapWriteStatement("v",new ValueExpression(new IntValue(30))),
                                new PrintStatement(new ArithmeticExpression('+' ,new HeapReadExpression(new HeapReadExpression( new VariableExpression("a"))),new ValueExpression(new IntValue(5))))))))));

        IStatement ex8 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("a",new ReferenceType(new IntType())),
                        new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(10))),
                                new CompoundStatement(new NewStatement("a",new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(new ForkStatement(new CompoundStatement(new HeapWriteStatement("a",new ValueExpression(new IntValue(30))),
                                                new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(32))),
                                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),new PrintStatement(new HeapReadExpression(new VariableExpression("a"))))))),new CompoundStatement(new PrintStatement(new VariableExpression("v")),new PrintStatement(new HeapReadExpression(new VariableExpression("a"))))))
                        )));
        IStatement forked = new CompoundStatement(new HeapWriteStatement("a",new ValueExpression(new IntValue(30))),
                new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(32))),
                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),new PrintStatement(new HeapReadExpression(new VariableExpression("a"))))));
        IStatement ex9 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("a",new ReferenceType(new IntType())),
                        new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(10))),
                                new CompoundStatement(new NewStatement("a",new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(new ForkStatement(forked),new CompoundStatement(new PrintStatement(new VariableExpression("v")),new PrintStatement(new HeapReadExpression(new VariableExpression("a"))))))
                        )));
        IStatement ex10 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("a",new ReferenceType(new IntType())),
                        new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new StringValue("aa"))),
                                new CompoundStatement(new NewStatement("a",new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(new ForkStatement(forked),new CompoundStatement(new PrintStatement(new VariableExpression("v")),new PrintStatement(new HeapReadExpression(new VariableExpression("a"))))))
                        )));
        IStatement ex11= new CompoundStatement(new VariableDeclarationStatement("v",new BoolType()), new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v"))));
        IStatement ex12= new CompoundStatement(new VariableDeclarationStatement("v",new IntType()), new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new BoolValue(true))), new PrintStatement(new VariableExpression("v"))));

        IStatement exConditionalAssignment = new CompoundStatement(new VariableDeclarationStatement("bvar", new BoolType()),
                        new CompoundStatement(new VariableDeclarationStatement("cvar", new IntType()),
                                new CompoundStatement(new AssignmentStatement("bvar", new ValueExpression(new BoolValue(true))),
                                        new CompoundStatement(new ConditionalStatement("cvar", new VariableExpression("bvar"), new ValueExpression(new IntValue(100)), new ValueExpression(new IntValue(200))),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("cvar")),
                                                        new CompoundStatement(new ConditionalStatement("cvar", new ValueExpression(new BoolValue(false)), new ValueExpression(new IntValue(100)), new ValueExpression(new IntValue(200))),
                                                                new PrintStatement(new VariableExpression("cvar"))))))));

        IStatement exWait = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new WaitStatement(10),
                                new PrintStatement(new ArithmeticExpression('*', new VariableExpression("v"), new ValueExpression(new IntValue(10)))))));

        IStatement exSwitch = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new IntValue(1))),
                        new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                                new CompoundStatement(new AssignmentStatement("b", new ValueExpression(new IntValue(2))),
                                        new CompoundStatement(new VariableDeclarationStatement("c", new IntType()),
                                                new CompoundStatement(new AssignmentStatement("c", new ValueExpression(new IntValue(5))),
                                                        new CompoundStatement(new SwitchStatement(new ArithmeticExpression('*', new VariableExpression("a"), new ValueExpression(new IntValue(10))),
                                                                                                  new ArithmeticExpression('*', new VariableExpression("b"), new VariableExpression("c")),
                                                                                                  new ValueExpression(new IntValue(10)),
                                                                                                  new CompoundStatement(new PrintStatement(new VariableExpression("a")), new PrintStatement(new VariableExpression("b"))),
                                                                                                  new CompoundStatement(new PrintStatement(new ValueExpression(new IntValue(100))), new PrintStatement(new ValueExpression(new IntValue(200)))),
                                                                                                  new PrintStatement(new ValueExpression(new IntValue(300)))) ,
                                                                new PrintStatement(new ValueExpression(new IntValue(300))))))))));

        IStatement exRepeatUntil = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new IntValue(0))),
                        new CompoundStatement(new RepeatUntilStatement(
                                                                       new CompoundStatement(new ForkStatement(new CompoundStatement(
                                                                                                               new PrintStatement(new VariableExpression("a")),
                                                                                                               new AssignmentStatement("a", new ArithmeticExpression('-', new VariableExpression("a"), new ValueExpression(new IntValue(1)))))),
                                                                                            new AssignmentStatement("a", new ArithmeticExpression('+', new VariableExpression("a"), new ValueExpression(new IntValue(1))))),
                                                                       new RelationalExpression(new VariableExpression("a"), new ValueExpression(new IntValue(3)), "==")),
                                             new PrintStatement(new ArithmeticExpression('*', new VariableExpression("a"), new ValueExpression(new IntValue(10)))))));

        IStatement exMul = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new IntValue(2))),
                                new CompoundStatement(new AssignmentStatement("b", new ValueExpression(new IntValue(3))),
                                                      new IfStatement(
                                                                      new VariableExpression("a"),
                                                                      new PrintStatement(new MulExpression(new VariableExpression("a"), new VariableExpression("b"))),
                                                                      new PrintStatement(new VariableExpression("a"))
                                                                     )
                                                      ))));

        IStatement exFor = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                                                  new ForStatement(
                                                                   new AssignmentStatement("a", new ValueExpression(new IntValue(1))),
                                                                   new RelationalExpression(new VariableExpression("a"), new ValueExpression(new IntValue(5)), "<"),
                                                                   new PrintStatement(new VariableExpression("a")),
                                                                   new AssignmentStatement("a", new ArithmeticExpression('+', new VariableExpression("a"), new ValueExpression(new IntValue(1))))
                                                                  )
                                                );

        IStatement forkedLatch3 = new ForkStatement(new CompoundStatement(new HeapWriteStatement("v3", new ArithmeticExpression('*', new HeapReadExpression(new VariableExpression("v3")), new ValueExpression(new IntValue(10)))),
                new CompoundStatement(new PrintStatement(new HeapReadExpression(new VariableExpression("v3"))),new CountDownLatchStatement("cnt"))));
        IStatement forkedLatch2 = new ForkStatement(new CompoundStatement(new HeapWriteStatement("v2", new ArithmeticExpression('*', new HeapReadExpression(new VariableExpression("v2")), new ValueExpression(new IntValue(10)))),
                new CompoundStatement(new PrintStatement(new HeapReadExpression(new VariableExpression("v2"))),
                        new CompoundStatement(new CountDownLatchStatement("cnt"), forkedLatch3))));
        IStatement forkedLatch1=  new ForkStatement(new CompoundStatement(new HeapWriteStatement("v1", new ArithmeticExpression('*', new HeapReadExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(10)))),
                new CompoundStatement(new PrintStatement(new HeapReadExpression(new VariableExpression("v1"))),
                        new CompoundStatement(new CountDownLatchStatement("cnt"),
                                forkedLatch2))));
        IStatement exCountDownLatch = new CompoundStatement(new VariableDeclarationStatement("v1", new ReferenceType(new IntType())),
                new CompoundStatement(new VariableDeclarationStatement("v2", new ReferenceType(new IntType())),
                        new CompoundStatement(new VariableDeclarationStatement("v3", new ReferenceType(new IntType())),
                                new CompoundStatement(new VariableDeclarationStatement("cnt",new IntType()),
                                        new CompoundStatement(new NewStatement("v1",new ValueExpression(new IntValue(2))),
                                                new CompoundStatement(new NewStatement("v2",new ValueExpression(new IntValue(3))),
                                                        new CompoundStatement(new NewStatement("v3",new ValueExpression(new IntValue(4))),
                                                                new CompoundStatement(new NewLatchStatement("cnt", new HeapReadExpression(new VariableExpression("v2"))),
                                                                        new CompoundStatement(forkedLatch1,
                                                                                new CompoundStatement(new AwaitLatchStatement("cnt"),
                                                                                        new CompoundStatement(new PrintStatement(new ValueExpression(new IntValue(100))),
                                                                                                new CompoundStatement(new CountDownLatchStatement("cnt"),
                                                                                                        new PrintStatement(new ValueExpression(new IntValue(100)))))))
                                                                ))))))));

        IStatement forkedBarrier1 = new ForkStatement(new CompoundStatement(new AwaitStatement(new VariableExpression("cnt")),
                new CompoundStatement(new HeapWriteStatement("v1", new ArithmeticExpression('*', new HeapReadExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(10)))),
                        new PrintStatement(new HeapReadExpression(new VariableExpression("v1"))))));
        IStatement forkedBarrier2 = new ForkStatement(new CompoundStatement(new AwaitStatement(new VariableExpression("cnt")),
                new CompoundStatement(new HeapWriteStatement("v2", new ArithmeticExpression('*', new HeapReadExpression(new VariableExpression("v2")), new ValueExpression(new IntValue(10)))),
                        new CompoundStatement(new HeapWriteStatement("v2", new ArithmeticExpression('*', new HeapReadExpression(new VariableExpression("v2")), new ValueExpression(new IntValue(10)))),
                                new PrintStatement(new HeapReadExpression(new VariableExpression("v2")))))));

        IStatement exBarrier =  new CompoundStatement(new VariableDeclarationStatement("v1", new ReferenceType(new IntType())),
                new CompoundStatement(new VariableDeclarationStatement("v2", new ReferenceType(new IntType())),
                        new CompoundStatement(new VariableDeclarationStatement("v3", new ReferenceType(new IntType())),
                                new CompoundStatement(new VariableDeclarationStatement("cnt",new IntType()),
                                        new CompoundStatement(new NewStatement("v1",new ValueExpression(new IntValue(2))),
                                                new CompoundStatement(new NewStatement("v2",new ValueExpression(new IntValue(3))),
                                                        new CompoundStatement(new NewStatement("v3",new ValueExpression(new IntValue(4))),
                                                                new CompoundStatement(new NewBarrierStatement("cnt", new HeapReadExpression(new VariableExpression("v2"))),
                                                                        new CompoundStatement(forkedBarrier1,
                                                                                new CompoundStatement(forkedBarrier2,
                                                                                        new CompoundStatement(new AwaitStatement(new VariableExpression("cnt")),
                                                                                                new PrintStatement(new HeapReadExpression(new VariableExpression("v3"))))))))))))));

        IStatement forked16 = new CompoundStatement(new AcquireStatement(new VariableExpression("cnt")),
                new CompoundStatement(new HeapWriteStatement("v1",new ArithmeticExpression('*',new HeapReadExpression(new VariableExpression("v1")),new ValueExpression(new IntValue(10)))),
                        new CompoundStatement(new PrintStatement(new HeapReadExpression(new VariableExpression("v1"))), new ReleaseStatement(new VariableExpression("cnt")))));

        IStatement forked162 = new CompoundStatement(new AcquireStatement(new VariableExpression("cnt")),
                new CompoundStatement(new HeapWriteStatement("v1",new ArithmeticExpression('*',new HeapReadExpression(new VariableExpression("v1")),new ValueExpression(new IntValue(10)))),
                        new CompoundStatement(new HeapWriteStatement("v1", new ArithmeticExpression('*',new HeapReadExpression(new VariableExpression("v1")),new ValueExpression(new IntValue(2)))),
                                new CompoundStatement(new PrintStatement(new HeapReadExpression(new VariableExpression("v1"))),new ReleaseStatement(new VariableExpression("cnt"))))));

        IStatement exSemaphore = new CompoundStatement(new VariableDeclarationStatement("v1",new ReferenceType(new IntType())),
                new CompoundStatement(new VariableDeclarationStatement("cnt", new IntType()),
                        new CompoundStatement(new NewStatement("v1",new ValueExpression(new IntValue(2))),
                                new CompoundStatement(new NewSemaphoreStatement(new VariableExpression("cnt"), new HeapReadExpression(new VariableExpression("v1")),new ValueExpression(new IntValue(1))),
                                        new CompoundStatement(new ForkStatement(forked16),
                                                new CompoundStatement(new ForkStatement(forked162),
                                                        new CompoundStatement(new AcquireStatement(new VariableExpression("cnt")),
                                                                new CompoundStatement(new PrintStatement(new ArithmeticExpression('-',new HeapReadExpression(new VariableExpression("v1")),new ValueExpression(new IntValue(1)))),
                                                                        new ReleaseStatement(new VariableExpression("cnt"))))))))));

        List<IStatement> list = new ArrayList<>();
        list.add(ex1);
        list.add(ex2);
        list.add(ex3);
        list.add(ex4);
        list.add(ex5);
        list.add(ex6);
        list.add(ex7);
        list.add(ex8);
        list.add(ex9);
        list.add(ex10);
        list.add(ex11);
        list.add(ex12);
        list.add(exConditionalAssignment);
        list.add(exWait);
        list.add(exSwitch);
        list.add(exRepeatUntil);
        list.add(exMul);
        list.add(exFor);
        list.add(exCountDownLatch);
        list.add(exBarrier);
        list.add(exSemaphore);
        return list;
    }

    private void displayExamples() {
        List<IStatement> examples = initExamples();
        selectItemListView.setItems(FXCollections.observableArrayList(examples));
        runBttn.setOnAction
                (actionEvent ->{
                            try{
                                int idx = selectItemListView.getSelectionModel().getSelectedIndex();
                                IStatement selectedPrg = selectItemListView.getItems().get(idx);
                                idx++;
                                ProgramState prgState = new ProgramState(selectedPrg);
                                IRepository repo = new Repository("log" + idx + ".txt");
                                Controller ctrl = new Controller(repo);
                                ctrl.addProgram(prgState);
                                try{
                                    selectedPrg.typeCheck(new MyDictionary<String, Type>());
                                    mainWindowController.setController(ctrl);
                                }
                                catch(MyException e){
                                    Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                                    alert.show();
                                }
                            }
                            catch(IndexOutOfBoundsException e){
                                Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a program first");
                                alert.show();
                            }
                        }
                );
    }

    public void initialize(URL url, ResourceBundle resourceBundle){
        displayExamples();
    }
}
