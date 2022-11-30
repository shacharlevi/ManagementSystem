package bgu.spl.mics.application;

import bgu.spl.mics.application.objects.ConferenceInformation;
import bgu.spl.mics.application.objects.Student;

import java.util.LinkedList;
import java.util.List;

public class Input {
    private List<Student> Students;
    private LinkedList<String> GPUS;
    private LinkedList<Integer> CPUS;
    private ConferenceInformation[] Conferences;
    int TickTime;
    int Duration;

    public List<Student> getStudents() {
        return Students;
    }

    public void setStudents(List<Student> students) {
        Students = students;
    }

    public LinkedList<String> getGPUS() {
        return GPUS;
    }

    public void setGPUS(LinkedList<String> GPUS) {
        this.GPUS = GPUS;
    }

    public LinkedList<Integer> getCPUS() {
        return CPUS;
    }

    public void setCPUS(LinkedList<Integer> CPUS) {
        this.CPUS = CPUS;
    }

    public ConferenceInformation[] getConferences() {
        return Conferences;
    }

    public void setConferences(ConferenceInformation[] conferences) {
        Conferences = conferences;
    }

    public int getTickTime() {
        return TickTime;
    }

    public void setTickTime(int tickTime) {
        TickTime = tickTime;
    }

    public int getDuration() {
        return Duration;
    }

    public void setDuration(int duration) {
        Duration = duration;
    }
}
