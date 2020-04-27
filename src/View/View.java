//package View;
//
//import Controller.Controller;
//import Model.ADTs.*;
//import Model.Exceptions.MyException;
//import Model.Expressions.ArithmeticExpression;
//import Model.Expressions.ValueExpression;
//import Model.Expressions.VariableExpression;
//import Model.ProgramState.ProgramState;
//import Model.Statements.*;
//import Model.Types.BoolType;
//import Model.Types.IntType;
//import Model.Values.BoolValue;
//import Model.Values.IntValue;
//import Model.Values.Value;
//
//import java.io.IOException;
//import java.util.Scanner;
//
//public class View {
//    private Controller controller;
//    private Scanner keyboard = new Scanner(System.in);
//    private IStatement ex1= new CompoundStatement(new VariableDeclarationStatement("v",new IntType()), new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v"))));
//    private IStatement ex2 = new CompoundStatement( new VariableDeclarationStatement("a",new IntType()),  new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),
//            new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+',new ValueExpression(new IntValue(2)),new ArithmeticExpression('*',new ValueExpression(new IntValue(3)),
//                    new ValueExpression(new IntValue(5))))),  new CompoundStatement(new AssignmentStatement("b",new ArithmeticExpression('+',new VariableExpression("a"),
//                    new ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));
//    private IStatement ex3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()), new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true)))
//            , new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignmentStatement("v",new ValueExpression(new IntValue(2))), new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));
//    private ProgramState programState;
//    private int printFlag;
//    public View(Controller controller) {
//        this.controller = controller;
//        this.printFlag=1;
//    }
//
//    private void initProgram(IStatement statement){
//        IStack<IStatement> exeStack = new MyStack<IStatement>();
//        IDictionary<String,Value> symbolTable = new MyDictionary<String,Value>();
//        IList<Value> output = new MyList<Value>();
//        //this.programState = new ProgramState(exeStack,symbolTable,output,statement);
//        this.programState = new ProgramState(statement);
//        this.controller.addProgram(this.programState);
//    }
//    private void chooseProgram() throws MyException {
//        System.out.println("Available programs:\n" +
//                "Press: 1 for 'int v;v=2;print(v)'\n" +
//                "2 for 'int a;int b; a=2+3*5;b=a+1;Print(b)'\n" +
//                "3 for 'bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)'");
//        //int option;
//        try {
//            //option = Integer.valueOf(this.keyboard.nextLine());
//            Scanner scanner = new Scanner(System.in);
//            String option = scanner.nextLine();
//            switch (option) {
//                case "1":
//                    this.initProgram(ex1);
//                    break;
//                case "2":
//                    this.initProgram(ex2);
//                    break;
//                case "3":
//                    this.initProgram(ex3);
//                    break;
//                default:
//                    throw new MyException("Wrong cmd.");
//            }
//        } catch (NumberFormatException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//    private void chooseExecMode() throws MyException, IOException {
//        System.out.println("1 for one-step\n" +
//                "2 for all steps\n" +
//                "3 to change the print flag  (Print flag is currently set to" + this.printFlag+")\n" +
//                "0 to exit");
//        //int option;
//        try {
//            //option = Integer.valueOf(this.keyboard.nextLine());
//            Scanner scanner = new Scanner(System.in);
//            String option = scanner.nextLine();
//            switch (option) {
//                case "1":
//                    this.controller.executeOneStep(this.programState);
//                    break;
//                case "2":
//                    this.controller.executeAllStep();
//                    throw new MyException("Exit");
//                case "3":
//                    this.printFlag=1-this.printFlag;
//                    break;
//                case "0":
//                    throw new MyException("Exit");
//                default:
//                    throw new MyException("Wrong cmd.");
//            }
//        }
//        catch(MyException exc){
//            throw exc;
//        }
//    }
//
//    public void run() {
//        try {
//            chooseProgram();
//        } catch (MyException e) {
//            System.out.println(e.getMessage());
//            return;
//        }
//        while (true){
//            try{
//                chooseExecMode();
//            }
//            catch (MyException e){
//                System.out.println(e.getMessage());
//                if (e.getMessage().equals("Exit"))
//                    break;
//            }
//            catch(IOException e)
//            {
//                System.out.println("IOException");
//            }
//        }
//    }
//
//
//}