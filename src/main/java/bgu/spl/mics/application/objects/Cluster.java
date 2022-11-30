package bgu.spl.mics.application.objects;


import bgu.spl.mics.MessageBusImpl;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Passive object representing the cluster.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Cluster {
	private static final Cluster instance = new Cluster();
	private LinkedList<GPU> GPUS;
	private LinkedList<CPU> CPUS;
	private ConcurrentHashMap<GPU, Queue<DataBatch>> toDo;
	private ConcurrentHashMap<GPU, Queue<DataBatch>> toRet;
	private LinkedList<String> modelNames;
	private int returnedFromCpu;
	private int gpuTimeUnits;
	private int cpuTimeUnits;
	private int cpuProcessed;



	private Cluster() {
		this.GPUS = null;
		this.CPUS = null;
		toDo = new ConcurrentHashMap<>();
		toRet = new ConcurrentHashMap<>();
		modelNames = new LinkedList<String>();
		returnedFromCpu = 0;
		gpuTimeUnits = 0;
		cpuTimeUnits=0;
		cpuProcessed=0;

	}
	public int getCpuProcessed() {
		return cpuProcessed;
	}

	public synchronized void setCpuProcessed() {
		cpuProcessed++;
	}
	public int getGpuTimeUnits() {
		return gpuTimeUnits;
	}

	public synchronized void setGpuTimeUnits() {
		gpuTimeUnits++;
	}

	public int getCpuTimeUnits() {
		return cpuTimeUnits;
	}

	public synchronized void setCpuTimeUnits() {
		cpuTimeUnits++;

	}
	public int getReturnedFromCpu() {
		return returnedFromCpu;
	}

	public void addModel(String model) {
		modelNames.add(model);
	}
	public LinkedList<String> getModelNames(){
		return modelNames;
	}

	public LinkedList<GPU> getGPUS() {
		return GPUS;
	}

	public synchronized void setGPUSPU(LinkedList<GPU> gpus, LinkedList<CPU> cpus) {
		if (this.GPUS == null && this.CPUS == null) {
			this.GPUS = gpus;
			for (GPU gpu : GPUS) {
				toDo.put(gpu, new ConcurrentLinkedQueue<>());
				toRet.put(gpu, new ConcurrentLinkedQueue<>());
			}
			this.CPUS = cpus;
		}
	}


	public LinkedList<CPU> getCPUS() {
		return CPUS;
	}

	/**
	 * Retrieves the single instance of this class.
	 */
	//singelton
	public static Cluster getInstance() {
		return instance;
	}

	public void addToQ(GPU gpu, DataBatch dataBatch) {
		toDo.get(gpu).add(dataBatch);
	}

	public void addToRet(GPU gpu, DataBatch dataBatch) {
		toRet.get(gpu).add(dataBatch);
		returnedFromCpu++;
	}

	public GPU getGpu() {
		GPU gpu = GPUS.remove();
		GPUS.addLast(gpu);
		return gpu;
	}

	public void tickCPU() {
		for (GPU gpu : toDo.keySet()) {
			if (!toDo.get(gpu).isEmpty()) {
				for (CPU cpu : CPUS) {
					if (!cpu.isProcessing()) {
						cpu.AcceptBatch(gpu, toDo.get(gpu).remove());
						break; //for just add one per cpu
					}
				}
			}
		}
	}

	public void tickGPU() {
		for (GPU gpu : toDo.keySet()) {
			if (!toRet.get(gpu).isEmpty()) {
				while (!gpu.isFull()&&!toDo.get(gpu).isEmpty())
					gpu.trainBatch(toDo.get(gpu).remove());
			}
		}
	}
}