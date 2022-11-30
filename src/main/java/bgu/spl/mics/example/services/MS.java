package bgu.spl.mics.example.services;

import bgu.spl.mics.Message;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;

public class MS extends MicroService {
    Message message;

    public MS(String a) {
        super(a);
    }

    @Override
    protected void initialize() {

    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public void Message_is_sent() {
        try {
            message = MessageBusImpl.getInstance().awaitMessage(this);
        } catch (Exception ignored) {

        }
    }
}
