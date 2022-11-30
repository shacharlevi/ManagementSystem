package bgu.spl.mics.application.objects;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ConferenceOut {
    private String name;
    private int date;
    private List<ModelOut> publications;

    public ConferenceOut(String name, int date){
        this.name=name;
        this.date=date;
        this.publications=new LinkedList<>();
        }

    public void addToPublications(ModelOut model){

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public List<ModelOut> getPublications() {
        return publications;
    }

    public void setPublications(List<ModelOut> publications) {
        this.publications = publications;
    }
}
