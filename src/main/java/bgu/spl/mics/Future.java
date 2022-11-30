package bgu.spl.mics;

import java.util.concurrent.TimeUnit;

/**
 * A Future object represents a promised result - an object that will
 * eventually be resolved to hold a result of some operation. The class allows
 * Retrieving the result once it is available.
 * <p>
 * Only private methods may be added to this class.
 * No public constructor is allowed except for the empty constructor.
 */
public class Future<T> {
    private T result = null;
    private boolean done = false;

    /**
     * This should be the the only public constructor in this class.
     */
    public Future() {
    }

    /**
     * retrieves the result the Future object holds if it has been resolved.
     * This is a blocking method! It waits for the computation in case it has
     * not been completed.
     * <p>
     *
     * @return return the result of type T if it is available, if not wait until it is available.
     * @post done=true , result !=null
     */

    //todo: check if synchronize with lock
    public T get()  {
        while (!done) {
            try {
                wait();
            }
            catch(InterruptedException ignore){}
        }
        return result;
    }

    /**
     * Resolves the result of this Future object.
     *
     * @post this.result=result , done=true
     */
    //todo: check if synchronize with lock
    public synchronized void resolve(T result) {
        this.result = result;
        done = true;
    }

    /**
     * @return true if this object has been resolved, false otherwise
     * @pre done==false, result !=null
     * @post done==true, result !=null
     */
    public boolean isDone() {
        return done;
    }


    /**
     * retrieves the result the Future object holds if it has been resolved,
     * This method is non-blocking, it has a limited amount of time determined
     * by {@code timeout}
     * <p>
     *

     * @pre timeout>unit
     */
    public T get(long timeout, TimeUnit unit) {
        if (!done) {
            try {
                unit.timedWait(this, timeout);
            } catch (Exception ignored) {
                return result;
            }
        } else {
            return result;
        }
        return null;
    }

}
