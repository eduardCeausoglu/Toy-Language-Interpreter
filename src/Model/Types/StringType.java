package Model.Types;

import Model.Values.StringValue;
import Model.Values.Value;

public class StringType implements Type {
    @Override
    public boolean equals(Object second) {
        if (second instanceof StringType)
            return true;
        else return false;
    }

    @Override
    public String toString() {
        return "string ";
    }

    @Override
    public Value defaultValue() {
        return new StringValue("");
    }
}