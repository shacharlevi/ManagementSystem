package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.application.objects.ConferenceInformation;

public class PublishConferenceBroadcast implements Broadcast {
    ConferenceInformation cinfo;


    public PublishConferenceBroadcast(ConferenceInformation cinfo) {
        this.cinfo = cinfo;
    }

    public ConferenceInformation getCinfo() {
        return cinfo;
    }

    public void setCinfo(ConferenceInformation cinfo) {
        this.cinfo = cinfo;
    }
}