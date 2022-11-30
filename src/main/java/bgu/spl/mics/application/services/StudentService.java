package bgu.spl.mics.application.services;

import bgu.spl.mics.Future;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.*;
import bgu.spl.mics.application.objects.Model;
import bgu.spl.mics.application.objects.Student;

import java.util.ArrayList;

/**
 * Student is responsible for sending the {@link TrainModelEvent},
 * {@link TestModelEvent} and {@link PublishResultsEvent}.
 * In addition, it must sign up for the conference publication broadcasts.
 * This class may not hold references for objects which it is not responsible for.
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */


public class StudentService extends MicroService {

    Student student;

    public StudentService(String name,Student student) {
        super(name);
        this.student=student;
    }
    public Student getStudent() {
        return student;
    }
    @Override
    protected void initialize() {
        subscribeBroadcast(TerminateBroadcast.class,(message)->{
            terminate();
        });
        //sub
        //the student needs to signup for the conference publicition broadcasts and update read\publish
        subscribeBroadcast(PublishConferenceBroadcast.class, (message) -> {
            for (Model model : message.getCinfo().getSuccessful()) {
                if (!student.equals(model.getStudent())) {
                    student.readPaper(); //read other students papers
                }
                else {
                    student.publishPaper(); // published paper
                }
            }
        });

        subscribeBroadcast(TickBroadcast.class,(message)->{
            ArrayList<Model> rModels= new ArrayList();
            for (Model model : student.getModels()) {
                if(model.getStatus()==Model.Status.Tested) {

                    if (model.getResult() == Model.Results.Good) {
                        sendEvent(new PublishResultsEvent(model));
                    }
                    rModels.add(model);
                }
                else if(model.getStatus()==Model.Status.Trained){
                    sendEvent(new TestModelEvent(model));
                }
            }
            student.getModels().removeAll(rModels);
        });
        //send
        for (Model model : student.getModels()) {
                Future future = sendEvent(new TrainModelEvent(model));
        }

    }

}
