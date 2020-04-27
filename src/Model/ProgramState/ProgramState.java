package Model.ProgramState;

import Model.ADTs.*;
import Model.Exceptions.MyException;
import Model.Statements.IStatement;
import Model.Values.StringValue;
import Model.Values.Value;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class ProgramState {
    private IStack<IStatement> executionStack;
    private IDictionary<String, Value> symbolTable;
    private IList<Value> output;
    private IDictionary<StringValue, BufferedReader> fileTable;
    private IHeap<Value> heap;
    private ILatchTable<Integer, Integer> latchTable;
    private IBarrier<Pair<Integer, List<Integer>>> barrierTable;
    private ISemaphoreTable semaphoreTable;
    private int id;
    private static int globalID = 1;
    private IStatement originalProgram;
    public IDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public ISemaphoreTable getSemaphoreTable() {
        return semaphoreTable;
    }

    public void setSemaphoreTable(ISemaphoreTable semaphoreTable) {
        this.semaphoreTable = semaphoreTable;
    }

    public IBarrier<Pair<Integer, List<Integer>>> getBarrierTable(){
        return this.barrierTable;
    }

    public void setBarrierTable(IBarrier<Pair<Integer, List<Integer>>> bt){
        this.barrierTable = bt;
    }

    public ILatchTable<Integer, Integer> getLatchTable (){
        return this.latchTable;
    }

    public void setLatchTable(ILatchTable<Integer, Integer> latchTable) {
        this.latchTable = latchTable;
    }

    public void setFileTable(IDictionary<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public IStack<IStatement> getExecutionStack() {
        return executionStack;
    }

    @Override
    public String toString() {
        return "ProgramState:\n{\n" +
                "ID: " + this.id + "\n" +
                "executionStack = " + executionStack.toString() + "\n" +
                "symbolTable = " + symbolTable.toString() + "\n" +
                "heap = " + heap.toString() + "\n" +
                "output = " + output.toString() +
                "latchTable = " + this.latchTable.toString() + "\n" +
                "barrierTable = " + this.barrierTable.toString() + "\n" +
                "semaphoreTable = " + this.semaphoreTable.toString() + "\n" +
                '}'+ "\n\n";
    }

    public IHeap<Value> getHeap() {
        return heap;
    }

    public void setHeap(IHeap<Value> heap) {
        this.heap = heap;
    }

    public void setExecutionStack(IStack<IStatement> executionStack) {
        this.executionStack = executionStack;
    }

    public IDictionary<String, Value> getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(IDictionary<String, Value> symbolTable) {
        this.symbolTable = symbolTable;
    }

    public IList<Value> getOutput() {
        return output;
    }

    public void setOutput(IList<Value> output) {
        this.output = output;
    }

    public int getId (){
        return this.id;
    }

    public void setId(int newId){
        this.id = newId;
    }

    public ProgramState(IStack<IStatement> executionStack, IDictionary<String, Value> symbolTable, IList<Value> output, IDictionary<StringValue,BufferedReader> fileTable,IStatement originalProgram, IHeap<Value> heap, ILatchTable<Integer, Integer> latchTable, IBarrier<Pair<Integer, List<Integer>>> bt, ISemaphoreTable semTable) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.output = output;
        this.originalProgram = originalProgram;
        this.fileTable = fileTable;
        this.heap = heap;
        this.executionStack.push(this.originalProgram);
        this.latchTable = latchTable;
        this.barrierTable = bt;
        this.semaphoreTable = semTable;
        id = getGlobalID();
    }

    public ProgramState(IStatement originalProgram){
        this.executionStack = new MyStack<IStatement>();
        this.symbolTable = new MyDictionary<String,Value>();
        this.output = new MyList<Value>();
        this.fileTable = new MyDictionary<String,BufferedReader>();
        this.heap = new MyHeap<>();
        this.executionStack.push(originalProgram);
        this.latchTable = new MyLatchTable<>();
        this.barrierTable = new MyBarrier<>();
        this.semaphoreTable = new MySemaphoreTable();
        id = 1;
    }

    public Boolean isNotCompleted(){
        return !this.executionStack.isEmpty();
    }

    public ProgramState executeOneStep() throws MyException, IOException {
        if(this.executionStack.isEmpty())
            throw new MyException("PrgState stack is empty");
        IStatement currentStatement = this.executionStack.pop();
        return currentStatement.execute(this);
    }

    public synchronized static int getGlobalID(){
        globalID*=10;
        return globalID;
    }
}
