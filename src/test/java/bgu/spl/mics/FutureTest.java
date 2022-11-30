package bgu.spl.mics;

import bgu.spl.mics.Future;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class FutureTest {
    private static Future<String> future;

    @Before
    public void setUp() throws Exception {
        future = new Future<String>();
    }
    @Test
    public void Testget() throws InterruptedException {
        assertTrue( !(future.isDone() ) );
        future.resolve("finalOutput");
        assertEquals( future.get(), "finalOutput");
        assertTrue( future.isDone() );
    }
    @Test
    public void Testresolve() throws InterruptedException{
        assertFalse(future.isDone());
        future.resolve("test");
        assertEquals(future.get(), "test");
        assertTrue( future.isDone()  );
    }
    @Test
    public void TestisDone() throws InterruptedException{
        assertFalse(future.isDone());
        future.resolve("test");
        assertTrue( future.isDone() );
    }

    @Test
    public void TestgetTime() throws InterruptedException {
        assertFalse(future.isDone());
        future.get(50, TimeUnit.MILLISECONDS);
        assertFalse(future.isDone());
        future.resolve("test");
        assertTrue(future.isDone());
        assertEquals(future.get(50, TimeUnit.MILLISECONDS), "test");
    }
}