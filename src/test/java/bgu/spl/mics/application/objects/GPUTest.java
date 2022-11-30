package bgu.spl.mics.application.objects;

import bgu.spl.mics.Future;
import bgu.spl.mics.application.objects.Data;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GPUTest {
//    GPU gpu1;
//    Model model;
//    Cluster cluster;
//    Data data;
//
//    @Before
//    public void setUp() throws Exception {
//        Data data = new Data(Data.Type.Images, 1000);
//        ArrayList<Model> models = new ArrayList<Model>();
//        model = new Model("YOLO10", "Images", 1000);
//        models.add(model);
//        Student student = new Student("shachar", "Computer Science", "MSc", models);
//        cluster = Cluster.getInstance();
//        gpu1 = new GPU(GPU.Type.RTX3090, model);
//
//    }
////
////    @Test
////    public void TestSplitToBatches() throws Exception {
////        assertEquals(gpu1.getUnprocessedSize(),0);
////        gpu1.splitToBatches();
////        assertEquals((gpu1.getModelSize()/1000),gpu1.getUnprocessedSize());
////    }
//
////    @Test
////    public void TestsendBatch() {
////        gpu1.splitToBatches();
////        int sizebefore= gpu1.getUnprocessedSize();
////        int before = cluster.gettoDoSize();
////        gpu1.sendBatch();
////        assertEquals(sizebefore-1,gpu1.getUnprocessedSize());
////        assertEquals(before+1,cluster.gettoDoSize());
////    }
//
//    @Test
//    public void TesttrainBatch() throws Exception {
////        assertEquals(0,gpu1.getProcessesSize());
////        DataBatch databatch = new DataBatch(data, 0);
////        gpu1.trainBatch(databatch);
////        assertEquals(1,gpu1.getProcessesSize());
////        int beginC= gpu1.getCounterDB();
////        assertEquals(beginC, 0);
////        Data data=new Data(Data.Type.Images,1000);
////        DataBatch databatch= new DataBatch(data,0);
////        gpu1.trainBatch(databatch);
////        assertEquals(gpu1.getCounterDB(),1);
//    }
//
//    @Test
//    public void TestTick() throws Exception {
////        Model model= gpu1.tick();
////        assertEquals(Model.Status.Tested,model.getStatus());
//
//
////        gpu1.splitToBatches();
////        gpu1.sendBatch();
////        Data data=new Data(Data.Type.Images,1000);
////        DataBatch databatch= new DataBatch(data);
////        gpu1.trainBatch(databatch);
////        Future<Model> f= gpu1.doneModel();
////        assertTrue(f.isDone());
//    }
//
//    @Test
//    public void TestSetModel() throws Exception {
//        gpu1.setModel(model);
//        assertEquals(model,gpu1.getModel());
//    }
}