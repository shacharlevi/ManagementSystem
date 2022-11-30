package bgu.spl.mics;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {
    private Map<Event<?>, Future> events;
    private Map<MicroService, Queue<Message>> Q_Todo; //map of <microservice,queues of messages (events and brodcasts) for each micro service to take care of>
    private Object lockQ_Todo;
    private Map<MicroService, List<Class<?>>> micro_subscribedTo;//<micro service, list of messages(brodcasts&events) subscribed to each one>
    private Map<Class<?>, Queue<MicroService>> events_MSubscribedTo;//<events/broadcasts,the micro service subscribed to it>
    private Object lockevents_MSubscribedTo;
    private static final MessageBusImpl instance = new MessageBusImpl();

    private MessageBusImpl() {
        events = new ConcurrentHashMap<>();
        Q_Todo = new ConcurrentHashMap<>();
        lockQ_Todo = new Object();
        events_MSubscribedTo = new ConcurrentHashMap<>();
        lockevents_MSubscribedTo = new Object();
        micro_subscribedTo = new ConcurrentHashMap<>();

    }

    //singelton
    public static MessageBusImpl getInstance() {
        return instance;
    }

    @Override
    //todo: check
    public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
        if (!Q_Todo.containsKey(m)) {
            throw new IllegalArgumentException("This microservice is not registered");
        }
        synchronized (lockevents_MSubscribedTo) {
            if (!events_MSubscribedTo.containsKey(type))
                events_MSubscribedTo.put(type, new LinkedList<>());
            events_MSubscribedTo.get(type).add(m);
            if (!micro_subscribedTo.containsKey(m))
                micro_subscribedTo.put(m, new LinkedList<>());
            micro_subscribedTo.get(m).add(type);
            lockevents_MSubscribedTo.notifyAll();
        }

    }

    @Override
    //todo: check
    public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
        if (!Q_Todo.containsKey(m)) {
            throw new IllegalArgumentException("This microservice is not registered");
        }
        synchronized (lockevents_MSubscribedTo) {
            if (!events_MSubscribedTo.containsKey(type)) {
                events_MSubscribedTo.put(type, new LinkedList<>());
            }
            events_MSubscribedTo.get(type).add(m);
            if (!micro_subscribedTo.containsKey(m)) {
                micro_subscribedTo.put(m,new LinkedList<>());
            }
            micro_subscribedTo.get(m).add(type);
            //events_MSubscribedTo.putIfAbsent(type, new LinkedList<>());
            lockevents_MSubscribedTo.notifyAll();
        }

    }

    @Override
    public <T> void complete(Event<T> e, T result) {
        events.get(e).resolve(result);
    }

    @Override
    public void sendBroadcast(Broadcast b) {
        if (events_MSubscribedTo.containsKey(b.getClass())) {
            synchronized (lockevents_MSubscribedTo) {
                synchronized (lockQ_Todo) {
                    for (MicroService micro : events_MSubscribedTo.get(b.getClass())) {
                        Q_Todo.get(micro).add(b);
                    }
                    lockQ_Todo.notifyAll(); //will notify all the microservices that are waiting for a message (at awaitMessage)
                    lockevents_MSubscribedTo.notifyAll();

                }
            }
        }
    }

    @Override
    public <T> Future<T> sendEvent(Event<T> e) {
        synchronized (lockevents_MSubscribedTo) {
            synchronized (lockQ_Todo) {
                if (events_MSubscribedTo.containsKey(e.getClass()) && !(events_MSubscribedTo.get(e.getClass()).isEmpty())) {
                    MicroService micro = events_MSubscribedTo.get(e.getClass()).remove();
                    Q_Todo.get(micro).add(e);
                    events_MSubscribedTo.get(e.getClass()).add(micro);
                    Future<T> output = new Future<T>();
                    events.put(e, output);

                    lockQ_Todo.notifyAll(); //will notify all the microservices that are waiting for a message (at awaitMessage)
                    lockevents_MSubscribedTo.notifyAll();
                    return events.get(e);
                } else {
                    return null;
                }
            }
        }

    }

    @Override
    public void register(MicroService m) {
        synchronized (lockQ_Todo) {
            if (!Q_Todo.containsKey(m)) {
                Q_Todo.put(m, new LinkedList<>());
                //   Q_Todo.putIfAbsent(m.getClass(), new LinkedList<>());
                micro_subscribedTo.putIfAbsent(m, new LinkedList<>());
                lockQ_Todo.notifyAll();
            }
        }
    }


    @Override
    public void unregister(MicroService m) {
        synchronized (lockevents_MSubscribedTo) {
            if (Q_Todo.containsKey(m)) {
                Q_Todo.remove(m);
                for (Class<?> mess : micro_subscribedTo.get(m)) {
                    events_MSubscribedTo.get(mess).remove(m);
                }
                micro_subscribedTo.remove(m);
            }
            lockevents_MSubscribedTo.notifyAll();
        }
    }

    @Override
    public Message awaitMessage(MicroService m) throws InterruptedException {
        synchronized (lockQ_Todo) {
            if (Q_Todo.containsKey(m)) {
                while (Q_Todo.get(m).isEmpty()) {
                    lockQ_Todo.wait();
                }
                lockQ_Todo.notifyAll();
                return Q_Todo.get(m).remove();
            } else {
                throw new IllegalArgumentException("This microservice is not registered");
            }

        }

    }


}
