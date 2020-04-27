package Model.Values;

import Model.Types.ReferenceType;
import Model.Types.Type;

public class ReferenceValue implements Value {
    int address;
    Type locationType;

    public ReferenceValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    @Override
    public String toString() {
        return "("+ address + "," + locationType.toString() +  ")";
    }

    public int getAddress() {
        return address;
    }
    public Type getLocationType(){return locationType;}
    @Override
    public Type getType() {
        return new ReferenceType(locationType);
    }
}