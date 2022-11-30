package bgu.spl.mics.application.objects;

import com.sun.org.apache.xpath.internal.operations.Mod;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Passive object representing single student.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Student {
    /**
     * Enum representing the Degree the student is studying for.
     */
    public enum Degree {
        MSc, PhD
    }

    private String name;
    private String department;
    private Degree status;
    private ArrayList<Model> models;
    private int publications;
    private int papersRead;
    private  ConcurrentLinkedQueue<ModelOut> tested;
//    private LinkedList<ModelOut> conPublications;


    public Student(String name, String department, String status, ArrayList<Model> models) {
        if (status.equals("MSc")) {
            this.status = Degree.MSc;
        } else {
            this.status = Degree.PhD;
        }
        this.name = name;
        this.department = department;
        this.models = models;
        publications = 0;
        papersRead = 0;
        tested=new ConcurrentLinkedQueue<>();


    }



    public ArrayList<Model> getModels() {
        return models;
    }

    public ConcurrentLinkedQueue<ModelOut> getTested() {
        return tested;
    }

    public void setTested(ConcurrentLinkedQueue<ModelOut> tested) {
        this.tested = tested;
    }

    public void addToTested(Model model) {
            tested.add((new ModelOut(model.getName(),new OutData(model.getType(),model.getSize()),model.getStatus(),model.getResult())));
    }

    public ModelOut getModelByName(String modelName) {
        for (ModelOut model : tested) {
            if (model.getName().equals(modelName)) {
                return model;
            }
        }
        return null;
    }

    public void setModels(ArrayList<Model> models) {
        this.models = models;

    }
    public synchronized void readPaper() {
        /*todo: add papper to publishModule List<String>
        set to the Modole of student Out*/

        papersRead++;
    }


    public synchronized void publishPaper() {
        publications++;
   //     conPublications.add(new ModelOut(model.getName(),new OutData(model.getType(),model.getSize()),model.getStatus(),model.getResult()));
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public Degree getStatus() {
        return status;
    }

    public int getPublications() {
        return publications;
    }

    public int getPapersRead() {
        return papersRead;
    }
}
