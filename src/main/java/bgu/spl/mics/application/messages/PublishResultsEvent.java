package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.objects.Model;
import com.sun.org.apache.xpath.internal.operations.Mod;

public class PublishResultsEvent implements Event {
    //need to add to the confrenceinformation the sucsseful model
    Model model;

    public PublishResultsEvent(Model model){
        this.model=model;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
