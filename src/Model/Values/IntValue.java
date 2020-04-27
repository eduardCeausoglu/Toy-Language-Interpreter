package Model.Values;

import Model.Types.IntType;
import Model.Types.Type;

public class IntValue implements Value {

    private int val;
    public IntValue(int val){
        this.val = val;
    }
    public int getValue(){
        return this.val;
    }
    public String toString(){
        return String.valueOf(this.val);
    }
    public Type getType(){
        return new IntType();
    }
}