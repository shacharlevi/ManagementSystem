package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.messages.TickBroadcast;

import java.util.concurrent.TimeUnit;

/**
 * TimeService is the global system timer There is only one instance of this micro-service.
 * It keeps track of the amount of ticks passed since initialization and notifies
 * all other micro-services about the current time tick using {@link TickBroadcast}.
 * This class may not hold references for objects which it is not responsible for.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class TimeService extends MicroService{
	private int speed;
	private int duration;
	public TimeService() {
		super("TimeMicroService");
	}
	public TimeService(int speed, int duration){
		this();
		this.speed=speed; //the speed is the time of each tick
		this.duration=duration;
	}

	@Override
	protected void initialize() {

		for (int i = 0; i < (duration/speed); i++) {
			sendBroadcast(new TickBroadcast(i*speed));
			try {
				Thread.sleep(speed); // sleep for tick duration
			} catch (InterruptedException ignored){}
		}
		sendBroadcast(new TerminateBroadcast());
		terminate();//terminate all MS as soon as possible

	}

}
