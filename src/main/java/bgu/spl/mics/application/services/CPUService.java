package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.objects.CPU;
import bgu.spl.mics.application.objects.Cluster;

/**
 * This class may not hold references for objects which it is not responsible for.
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class CPUService extends MicroService {
    private Cluster cluster;

    public CPUService(String name) {
        super(name);
        cluster = Cluster.getInstance();
    }

    @Override
    protected void initialize() {
        subscribeBroadcast(TickBroadcast.class, (message) -> {
            for (CPU cpu : cluster.getCPUS()) {
                cpu.tick();
            }
            cluster.tickCPU();
        });

        subscribeBroadcast(TerminateBroadcast.class, (message) -> {
            terminate();
        });
    }}