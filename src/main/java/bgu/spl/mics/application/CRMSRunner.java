package bgu.spl.mics.application;

import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.application.objects.*;
import bgu.spl.mics.application.services.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import bgu.spl.mics.application.objects.Cluster;
import bgu.spl.mics.application.objects.Model;
import bgu.spl.mics.application.objects.Student;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This is the Main class of Compute Resources Management System application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output a text file.
 */
public class CRMSRunner {
    public static void main(String[] args) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Reader r = new FileReader("example_input.json");) {
            JsonElement json = gson.fromJson(r, JsonElement.class);
            String jsonInString = gson.toJson(json);
            Input input = new Gson().fromJson(jsonInString, Input.class);

            CPUService CPUService = new CPUService("CPU");
            GPUService GPUService = new GPUService("GPU");
            int TickTime = input.TickTime;
            int Duration = input.Duration;
            TimeService time = new TimeService(TickTime, Duration);    //new timeService
            List<StudentService> studentServicesList = new ArrayList<>();
            for (Student s : input.getStudents()) {
                for (Model model : s.getModels()) {
                    model.init(s);
                }
                s.setTested(new ConcurrentLinkedQueue<>());
                studentServicesList.add(new StudentService(s.getName(), s));
            }
            List<ConferenceService> conServiceList = new ArrayList<>();
            for (ConferenceInformation cinfo : input.getConferences()) {
                cinfo.setSuccessful(new LinkedList<>());
                conServiceList.add(new ConferenceService(cinfo.getName(), cinfo));
                cinfo.setConPublications(new LinkedList<>());
            }
            LinkedList<GPU> newG = new LinkedList<>();
            for (String g : input.getGPUS()) {
                newG.add(new GPU(g));
            }
            LinkedList<CPU> newC = new LinkedList<>();
            for (Integer c : input.getCPUS()) {
                newC.add(new CPU(c));
            }
            Cluster.getInstance().setGPUSPU(newG, newC);
            //closing reader
            r.close();
            //init all threads
            List<Thread> threads = new ArrayList<>();
            threads.add(new Thread(CPUService));
            threads.add(new Thread(GPUService));
            for (ConferenceService c : conServiceList) {
                threads.add(new Thread(c));
            }
            for (StudentService s : studentServicesList) {
                threads.add(new Thread(s));
            }
            threads.add(new Thread(time));
            //start all threads
            MessageBusImpl.getInstance();

            for (Thread t : threads) {
                t.start();
            }
            for (Thread t : threads) { //we need to wait that all the threads are done
                t.join();
            }
            //output

            List<StudentOut> studentsOut = new LinkedList<>();
            List<ConferenceOut> conferencesOut = new LinkedList<>();
            int cpuTimeUsed = Cluster.getInstance().getCpuTimeUnits();
            int gpuTimeUsed = Cluster.getInstance().getGpuTimeUnits();
            int batchesProcessed = Cluster.getInstance().getCpuProcessed();
            for (StudentService s : studentServicesList) {
                Student student = s.getStudent();
                StudentOut outStudent = new StudentOut(student.getName(), student.getDepartment(), student.getStatus(), student.getPublications(), student.getPapersRead());
                if (!(student.getTested().isEmpty()) ){
                    for (ModelOut model : student.getTested()) {
                        outStudent.addModel(model);
                    }
                }
                studentsOut.add(outStudent);
            }
            for (ConferenceService conService : conServiceList) {
                ConferenceInformation con = conService.getCinfo();
                ConferenceOut conOut =new ConferenceOut(con.getName(), con.getDate());
                conOut.setPublications(con.getConPublications());
                conferencesOut.add(conOut);
            }

            Output out = new Output(studentsOut, conferencesOut, cpuTimeUsed, gpuTimeUsed, batchesProcessed);
            String j = gson.toJson(out);
            Files.write(Paths.get("Output.json"), Arrays.asList(j.split("\n")));

        } catch (Exception ignore) {
            ignore.printStackTrace();

        }

    }

}


