package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.objects.Model;
import bgu.spl.mics.application.objects.Student;
import com.sun.org.apache.xpath.internal.operations.Mod;

public class TrainModelEvent  implements Event {
    Model model;

    public TrainModelEvent(Model model) {
        this.model = model;
        model.setStatus(Model.Status.Training );
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
