package Model.ADTs;

import java.util.List;

public class MyTuple implements ITuple {
    private Integer first, third;

    @Override
    public String toString() {
        return "(" + first + " , " + second.toString() + ", " + third + ")";
    }

    public MyTuple(Integer first, List<Integer> second, Integer third) {
        this.first = first;
        this.third = third;
        this.second = second;
    }

    private List<Integer> second;
    @Override
    public int getFirst() {
        return first;
    }

    @Override
    public List<Integer> getSecond() {
        return second;
    }

    @Override
    public int getThird() {
        return third;
    }
}