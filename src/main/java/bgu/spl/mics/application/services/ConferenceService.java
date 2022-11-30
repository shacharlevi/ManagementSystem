package bgu.spl.mics.application.services;

import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.PublishConferenceBroadcast;
import bgu.spl.mics.application.messages.PublishResultsEvent;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.objects.ConferenceInformation;

/**
 * Conference service is in charge of
 * aggregating good results and publishing them via the {@link PublishConferenceBroadcast},
 * after publishing results the conference will unregister from the system.
 * This class may not hold references for objects which it is not responsible for.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class ConferenceService extends MicroService {
    ConferenceInformation cinfo;
    public ConferenceService(String name, ConferenceInformation cinfo) {
        super(cinfo.getName());
        this.cinfo=cinfo;
    }

    public ConferenceInformation getCinfo() {
        return cinfo;
    }


    @Override
    protected void initialize() {

        //aggregating all the models to the successful array in conference information
        subscribeEvent(PublishResultsEvent.class,(event)->{
                    cinfo.addSuccsessfulEvent(event.getModel());

                }
        );
        //sending all the good results at a certain paste via a broadcast
        subscribeBroadcast(TickBroadcast.class,(message)-> {
            if(message.getTimePassed()==cinfo.getDate()){
                sendBroadcast(new PublishConferenceBroadcast(cinfo));
                terminate();
            }
        });
        subscribeBroadcast(TerminateBroadcast.class,(message)->{
            terminate();
        });
    }
}
