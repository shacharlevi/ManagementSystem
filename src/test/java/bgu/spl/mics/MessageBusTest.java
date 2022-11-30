//package bgu.spl.mics;
//
//import bgu.spl.mics.example.messages.ExampleEvent;
//import bgu.spl.mics.example.services.MS;
//import com.sun.xml.internal.bind.v2.TODO;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
//public class MessageBusTest {
//    MS micro;
//    MessageBus bus;
//
//    @Before
//    public void setUp() throws Exception {
//        micro = new MS("david");
//        bus = MessageBusImpl.getInstance();
//        bus.register(micro);
//    }
//
//    @After
//    public void TesttearDown() throws Exception {
//        bus.unregister(micro);
//    }

////    @Test
////    public void TestsubscribeEvent() {
////        ExampleEvent mess1 = new ExampleEvent("birthday");
////        bus.subscribeEvent(mess1.getClass(), micro);
////        try {
////            bus.sendEvent(mess1);
////            micro.Message_is_sent();
////            assertEquals(mess1, micro.getMessage());
////        } catch (Exception error) {
////            fail();
////        }
////    }
//
//    @Test
//    public void TestsubscribeBroadcast() {
//        Broadcast broadcast = new Broadcast() {
//        };
//        bus.subscribeBroadcast(broadcast.getClass(), micro);
//        try {
//            bus.sendBroadcast(broadcast);
//            micro.Message_is_sent();
//            assertEquals(broadcast, micro.getMessage());
//        } catch (Exception error) {
//            fail();
//        }
//    }
//
//    @Test
//    public void Testcomplete() {
//        ExampleEvent mess = new ExampleEvent("");
//        bus.subscribeEvent(mess.getClass(), micro );
//        Future<String> a = bus.sendEvent(mess);
//        bus.complete(mess, "");
//        try {
//            assertEquals( a.get() , "");
//        } catch (Exception error) {
//            fail();
//        }
//    }
//
//    @Test
//    public void TestsendBroadcast() {
//        Broadcast broadcast = new Broadcast() {};
//        MS micro2 = new MS("Dana");
//        bus.register(micro2);
//        bus.subscribeBroadcast(broadcast.getClass(), micro);
//        bus.subscribeBroadcast(broadcast.getClass(), micro2);
//        try {
//            bus.sendBroadcast(broadcast);
//            micro.Message_is_sent();
//            micro2.Message_is_sent();
//            assertEquals(broadcast, micro.getMessage());
//            assertEquals(broadcast, micro2.getMessage());
//
//        } catch (Exception error) {
//            fail();
//        }
//        bus.unregister(micro2);
//
//    }
//
//    @Test
//    public void TestsendEvent() {
//        ExampleEvent mess1 = new ExampleEvent("birthday");
//        ExampleEvent mess2 = new ExampleEvent("wedding");
//        Future<String> a = bus.sendEvent(mess1);
//        assertNull(a);
//        MS micro2 = new MS("Dana");
//        bus.register(micro2);
//        bus.subscribeEvent(mess1.getClass(), micro);
//        bus.subscribeEvent(mess1.getClass(), micro2);
//        try {
//            bus.sendEvent(mess1);
//            bus.sendEvent(mess2);
//            micro.Message_is_sent();
//            micro2.Message_is_sent();
//            assertEquals(mess1, micro.getMessage());
//            assertEquals(mess2, micro2.getMessage());
//        } catch (Exception error) {
//            fail();
//        }
//        bus.unregister(micro2);
//    }
//
//
//    @Test
//    public void TestawaitMessage() throws Exception {
//
//        ExampleEvent mess = new ExampleEvent("birthday");
//        bus.subscribeEvent(mess.getClass(), micro);
//        micro.setMessage(mess);
//        bus.sendEvent(mess);
//        Message comp = bus.awaitMessage(micro);
//        assertEquals(micro.getMessage(), comp);
//    }
//}