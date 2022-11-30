package bgu.spl.mics.application.objects;

import java.util.LinkedList;


/**
 * Passive object representing a single CPU.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class CPU {
    private int numOfCores;
    private DataBatch processing;
    private GPU gpu;
    private Cluster cluster;
    int ticks;

    public CPU(int numOfCores) {
        cluster= Cluster.getInstance();
        this.numOfCores = numOfCores;
        processing = null;
        ticks=0;
    }

    public int getNumOfCores() {
        return numOfCores;
    }

    public void setNumOfCores(int numOfCores) {
        this.numOfCores = numOfCores;
    }


    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }

    public int getTicks(){
        return ticks;
    }
    /*
    @post ticks!=0, @pre processing.size ==processing.size()-1
     */
    public void AcceptBatch(GPU gpu,DataBatch databatch) {
        Data.Type datatype = databatch.getData().getTypeofData();
        if(datatype== Data.Type.Images)
            ticks= Math.max((32*4/numOfCores),1);
        else if(datatype== Data.Type.Text)
            ticks= Math.max((32*2/numOfCores),1);
        else
            ticks= Math.max((32/numOfCores),1);
        processing=databatch;
        this.gpu=gpu;

    }
    public Boolean isProcessing(){
        return processing!=null;
    }

    public void tick() {
        if(isProcessing()){
            ticks--;
            cluster.setCpuTimeUnits();
            if(ticks==0) {
                cluster.setCpuProcessed();
                cluster.addToRet(gpu,processing);
                processing = null;
                gpu=null;
            }
        }
    }

      /*
    @post:  @pre: processing.size()==processing.size()+1, @pre: processed.size()+1== processed.size()
     */

    public DataBatch processBatch() {
        //will go over the processing linklist, remove the first element,
        //  will "process it"- will wait the ticks amount of time and will add to the processed array
        return null;
    }
    /*
    @pre: processing!=null
    @post: @pre cluster.gettoRet()+1==cluster.gettoRet()
     */
    public void updateTicks() {
        //will check if(curr_time-batch_time>10)
        //send to cluster
    }
}
