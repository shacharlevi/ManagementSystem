package bgu.spl.mics.application;

import bgu.spl.mics.application.objects.ConferenceOut;
import bgu.spl.mics.application.objects.StudentOut;

import java.util.List;

public class Output {
    List<StudentOut> students;
    List<ConferenceOut> conferences;
    int cpuTimeUsed;
    int gpuTimeUsed;
    int batchesProcessed;

    public Output(List<StudentOut> students,List<ConferenceOut> conferences, int cpuTimeUsed, int gpuTimeUsed, int batchesProcessed) {
        this.students = students;
        this.conferences = conferences;
        this.cpuTimeUsed = cpuTimeUsed;
        this.gpuTimeUsed = gpuTimeUsed;
        this.batchesProcessed = batchesProcessed;
    }

    public List<StudentOut> getStudents() {
        return students;
    }

    public void setStudents(List<StudentOut> students) {
        this.students = students;
    }

    public List<ConferenceOut> getConferences() {
        return conferences;
    }

    public void setConferences(List<ConferenceOut> conferences) {
        this.conferences = conferences;
    }

    public int getCpuTimeUsed() {
        return cpuTimeUsed;
    }

    public void setCpuTimeUsed(int cpuTimeUsed) {
        this.cpuTimeUsed = cpuTimeUsed;
    }

    public int getGpuTimeUsed() {
        return gpuTimeUsed;
    }

    public void setGpuTimeUsed(int gpuTimeUsed) {
        this.gpuTimeUsed = gpuTimeUsed;
    }

    public int getBatchesProcessed() {
        return batchesProcessed;
    }

    public void setBatchesProcessed(int batchesProcessed) {
        this.batchesProcessed = batchesProcessed;
    }
}
