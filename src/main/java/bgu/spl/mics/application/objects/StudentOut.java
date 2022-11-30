package bgu.spl.mics.application.objects;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StudentOut {
    private String name;
    private String department;
    private Student.Degree status;
    private int publications;
    private int papersRead;
    private ArrayList<ModelOut> trainedModels;

public StudentOut(String name, String department, Student.Degree status, int publications, int papersRead) {
        this.name = name;
        this.publications = publications;
        this.papersRead = papersRead;
        this.department=department;
        this.status=status;
        this.trainedModels=new ArrayList<>();;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }


    public Student.Degree getStatus() {
        return status;
    }

    public void setStatus(Student.Degree status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ModelOut> getModels() {
        return trainedModels;
    }

    public void setTrainedModels(ArrayList<ModelOut> trainedModels) {
        this.trainedModels = trainedModels;
    }

    public void addModel(ModelOut model) {
        trainedModels.add(model);
    }

    public int getPublications() {
        return publications;
    }

    public void setPublications(int publications) {
        this.publications = publications;
    }

    public int getPapersRead() {
        return papersRead;
    }

    public void setPapersRead(int papersRead) {
        this.papersRead = papersRead;
    }

    public ArrayList<ModelOut> getTrainedModels() {
        return trainedModels;
    }

}
