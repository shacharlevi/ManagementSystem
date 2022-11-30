package bgu.spl.mics.application.objects;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Passive object representing information on a conference.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class ConferenceInformation {

    private String name;
    private int date;
    private List<Model> successful;
    private LinkedList<ModelOut> conPublications;

    public ConferenceInformation(String name, int date){
        this.name=name;
        this.date=date;
        successful= new ArrayList<>();
    }

    public LinkedList<ModelOut> getConPublications() {
        return conPublications;
    }

    public void setConPublications(LinkedList<ModelOut> conPublications) {
        this.conPublications = conPublications;
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

    public void setSuccessful(List<Model> successful) {
        this.successful = successful;
    }

    public void addSuccsessfulEvent (Model model){
        successful.add(model);
        conPublications.add(new ModelOut(model.getName(),new OutData(model.getType(),model.getSize()),model.getStatus(),model.getResult()));

    }

    public List<Model> getSuccessful(){
        return successful;
    }
}

