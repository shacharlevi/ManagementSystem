package bgu.spl.mics.application.objects;

/**
 * Passive object representing a data used by a model.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */

public class DataBatch {
    private Data data;
    private int start_index;
    public Model m;
    public DataBatch(Data data,int index){
        this.data=data;
        start_index=index;
    }
    public DataBatch(Data data,int index,Model m){
        this.data=data;
        start_index=index;
        this.m=m;
    }


    public int getStart_index() {
        return start_index;
    }

    public void setStart_index(int start_index) {
        this.start_index = start_index;
    }

    public Data getData(){
        return data;
    }
}
