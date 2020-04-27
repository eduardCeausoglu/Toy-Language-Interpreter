package Model.Types;

import Model.Values.IntValue;

public class IntType implements Type {
    public boolean equals(Object second){
        if(second instanceof IntType)
            return true;
        else
            return false;
    }
    public String toString(){
        return "int ";
    }
    public IntValue defaultValue(){return new IntValue(0);}
}