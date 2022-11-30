package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.messages.TestModelEvent;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.messages.TrainModelEvent;
import bgu.spl.mics.application.objects.*;

/**
 * GPU service is responsible for handling the
 * This class may not hold references for objects which it is not responsible for.
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class GPUService extends MicroService {
    Cluster cluster;

    public GPUService(String name) {
        super(name);
        cluster = Cluster.getInstance();
    }

    @Override
    protected void initialize() {
        subscribeEvent(TrainModelEvent.class, (event) -> {
            GPU gpu = Cluster.getInstance().getGpu();
            gpu.setModel(event.getModel());

        });
        subscribeBroadcast(TickBroadcast.class, (message) -> {
            for (GPU gpu : cluster.getGPUS()) {
                if (gpu.getModel() != null) {
                    gpu.tick();
                }
            }
            cluster.tickGPU();
        });
        subscribeEvent(TestModelEvent.class, (message) -> {
            if (message.getModel().getStudent().getStatus() == Student.Degree.PhD) {
                if (Math.random() < 0.8)
                    message.getModel().setResult(Model.Results.Good);
                else
                    message.getModel().setResult(Model.Results.Bad);
            } else {
                if (Math.random() < 0.6)
                    message.getModel().setResult(Model.Results.Good);
                else
                    message.getModel().setResult(Model.Results.Bad);
            }
            message.getModel().setStatus(Model.Status.Tested);
            ModelOut modelOut =message.getModel().getStudent().getModelByName(message.getModel().getName());
            modelOut.setStatus(Model.Status.Tested);
            modelOut.setResults(message.getModel().getResult());
            complete(message, true);
        });





        subscribeBroadcast(TerminateBroadcast.class, (message) -> {
            terminate();
        });

    }
}
