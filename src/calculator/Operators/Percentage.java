package calculator.Operators;

import calculator.Number;
import calculator.Operator;

//TODO
public class Percentage extends Operator {

    @Override
    public int getPriority() {
        return 2;
    }

//    @Override
//    public Number calc(Number a, Number b) {
//        return null;
//    }

    @Override
    public Number calc(Number a, Number b){
        return new Number((a.getValue()/b.getValue())*100);    }

    @Override
    public Number calc(Number b) {
        return new Number(b.getValue()/100);}

    @Override
    public String toString() {
        return "%";
    }
}

