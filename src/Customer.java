/**
 * Sumit Aggarwal 000793607; code not copied; not let anyone else copy
 */
public class Customer {

    private int ni;
    private int time;

    public Customer(int items){
        this.ni = items;
    }

    public int getNi(){
        return this.ni;
    }

    public int calculateTime(){
        return this.time = 45 + 5 * this.ni;
    }

    @Override
    public String toString(){
        return "[" + this.ni + "(" + this.time + " s)]";
    }

}
