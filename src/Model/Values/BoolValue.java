package Model.Values;

import Model.Types.BoolType;
import Model.Types.Type;

public class BoolValue implements Value {
    private boolean val;
    public BoolValue(boolean val){
        this.val = val;
    }
    public Boolean getValue(){
        return this.val;
    }
    public String toString(){
        return String.valueOf(this.val);
    }
    public Type getType(){
        return new BoolType();
    }
}