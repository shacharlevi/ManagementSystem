package bgu.spl.mics.application.objects;

import bgu.spl.mics.Future;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Passive object representing a single GPU.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */

public class GPU {


    /**
     * Enum representing the type of the GPU.
     */
    enum Type {RTX3090, RTX2080, GTX1080}

    /*
    @INV: num_batches>=counterDB,model!=null

     */
    private Type type;
    private Model model;
    private Cluster cluster;

    private int num_processed_batches; //detrmined by its type(the amout of *processed* batches it can store)
    private int time_of_train;//detrmined by its type
    private int counterDB; //the amount of batches the gpu processed

    private Map<DataBatch, Integer> inProcess;
    private ConcurrentHashMap<Model, Integer> modelToTrain =new ConcurrentHashMap<>();

    public GPU(Type type, Model model) {
        this.type = type;
        this.model = model;
        this.cluster = Cluster.getInstance();
        // beginButches = model.getDataSize();
        counterDB = 0;
        if (type == Type.RTX3090) {
            time_of_train = 1;
            num_processed_batches = 32;
        } else if (type == Type.RTX2080) {
            time_of_train = 2;
            num_processed_batches = 16;
        } else {
            time_of_train = 4;
            num_processed_batches = 8;
        }

        inProcess = new ConcurrentHashMap<DataBatch, Integer>();

    }

    public GPU(String stype) {
        if (stype.equals("RTX3090")) {
            this.type = Type.RTX3090;
        } else if (stype.equals("RTX2080")) {
            this.type = Type.RTX2080;
        } else {
            this.type = Type.GTX1080;
        }

        this.model = null;
        this.cluster = Cluster.getInstance();
        // beginButches = model.getDataSize();
        counterDB = 0;
        if (type == Type.RTX3090) {
            time_of_train = 1;
            num_processed_batches = 32;
        } else if (type == Type.RTX2080) {
            time_of_train = 2;
            num_processed_batches = 16;
        } else {
            time_of_train = 3;
            num_processed_batches = 8;
        }
        inProcess = new ConcurrentHashMap<DataBatch, Integer>();


    }

    public boolean isFull() {
        return inProcess.size() >= num_processed_batches;
    }

    public void setModel(Model model) {
        this.model = model;
        AcceptData(model.getData());

    }

    public Model getModel() {
        return model;
    }

    public void AcceptData(Data data) {
        int num = data.getSize() / 1000;
        if (data.getSize() % 1000 > 0) {
            num++;
        }
        modelToTrain.put(model, num);
        for (int i = 0; i < num; i++) {
            cluster.addToQ(this, new DataBatch(data, i, model));
        }
    }

    /*
    /@pre unProcessed.size==0
    @post- unProcessed.size()==getModelSize/1000
     */

    public void trainBatch(DataBatch d) {
        inProcess.put(d, this.time_of_train);
    }

    public void tick() {
        if (!inProcess.isEmpty()) {
            // System.out.println("gpu train :");
            cluster.setGpuTimeUnits();
            for (DataBatch databatch : inProcess.keySet()) {
                inProcess.put(databatch, inProcess.get(databatch) - 1);
                if (inProcess.get(databatch) == 0) {
                    //    System.out.println("gpu train :"+modelToTrain.get(databatch.m));
                    inProcess.remove(databatch);
                    modelToTrain.put(databatch.m, modelToTrain.get(databatch.m) - 1);
                }
            }

        }
        for (Model m : modelToTrain.keySet()) {
            if (modelToTrain.get(m) == 0) {
                m.setStatus(Model.Status.Trained);
                m.getStudent().addToTested(m);
                modelToTrain.remove(m);
            }
        }
    }

//    public int getModelSize() {
//        return beginButches;
//    }

    public int getCounterDB() {
        return counterDB;
    }


    public void splitToBatches() {
        //the method will split the unprocessed data in batches of 1000 samples using the object databatch.
        //the method will use the model.data.size to get the num of samples and will split them into the
        //link list by batches of 1000 samples
        //saves the size of the image/text/singular
        //  int typeOfData= Model.data.size;
    }


    /*
     *@pre:model.status== PreTrained,
     * @post: @pre unProcessed.size()-1==unProcessed.size(),@pre before+1,cluster.gettoDoSize()+1,cluster.gettoDoSize()
     */
    public void sendBatch() {
        //this method will send the unprocessed data that was split(using splitToBatches)
        //to the cluster using the object databatch(which contains a 1000 samples each).
        //changes status of model to training
    }


}
