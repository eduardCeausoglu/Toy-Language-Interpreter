package Model.Statements;

import Model.ADTs.IDictionary;
import Model.ADTs.IStack;
import Model.Exceptions.MyException;
import Model.Expressions.ArithmeticExpression;
import Model.Expressions.Expression;
import Model.Expressions.RelationalExpression;
import Model.ProgramState.ProgramState;
import Model.Types.Type;
import Model.Values.Value;

public class SwitchStatement implements IStatement {

    private Expression exp;
    private Expression exp1;
    private Expression exp2;
    private IStatement stmt1;
    private IStatement stmt2;
    private IStatement stmt3;

    public SwitchStatement(Expression e, Expression e1 ,Expression e2, IStatement s1, IStatement s2, IStatement s3){
        this.exp = e;
        this.exp1 = e1;
        this.exp2 = e2;
        this.stmt1 = s1;
        this.stmt2 = s2;
        this.stmt3 = s3;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException{
        IStack<IStatement> stack = state.getExecutionStack();
        IfStatement s = new IfStatement(new RelationalExpression(this.exp, this.exp1, "=="), this.stmt1,
                new IfStatement(new RelationalExpression(this.exp, this.exp2, "=="), this.stmt2, this.stmt3));
        stack.push(s);
        return null;
    }

    @Override
    public String toString(){
        return "switch(" + this.exp + ")(case(" + this.exp1.toString() + ")" + this.stmt1.toString() + ")(case(" + this.exp2.toString()
                + ")" + this.stmt2.toString() + ")(default:" + this.stmt3.toString();
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
}
