package Model.Values;

import Model.Types.Type;
import Model.Types.StringType;
public class StringValue implements Value {

    private String value;
    public StringValue(String value){this.value = value;}
    public String getValue(){return this.value;}

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }

    @Override
    public Type getType() {
        return new StringType();
    }
}