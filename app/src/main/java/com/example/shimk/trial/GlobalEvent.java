package com.example.shimk.trial;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shimk on 5/19/2016.
 */
public class GlobalEvent extends Application {
    private static ArrayList<Event> allEvents = new ArrayList<Event>();

    private static Firebase firebase = new Firebase(Config.FIREBASE_URL);

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference myRef = database.getReference("events");

//    private static Map<String, Event> events = new HashMap<String, Event>();

    public static Firebase getFirebase() {
        return firebase;
    }

    public static boolean addEvent(Event event, boolean newEvent) {

        for (int i = 0; i < allEvents.size(); i++) {
            if (allEvents.get(i).getCode().equals(event.getCode())) {
                return false;
            }
        }
        allEvents.add(event);

 //       String code = event.getCode();
 //       events.put(code, event);
//
        if (newEvent) {
            saveEvents(event);
        }
        return true;
    }

    public static void remEvent(Event event) {
        for (int i = 0; i < allEvents.size(); i++) {
            if(event.getCode().equals(allEvents.get(i).getCode())) {
                allEvents.remove(i);
                if (event.isOwner()) {
                    firebase.child("events").child(event.getCode()).removeValue();
                }
                break;
            }
        }
    }

    public static void saveEvents(Event event) {
        //firebase.child("events").setValue(allEvents);
        //firebase.child("events").push().setValue(event);

        String code = event.getCode();
        String name = event.getName();
        String info = event.getInfo();
        int[] date = event.getDate();
        int day = date[0];
        int month = date[1];
        int year = date[2];
        int[] time = event.getTime();
        int hour = time[0];
        int minute = time[1];
        boolean isAm = event.isAM();

        firebase.child("events").child(code).child("name").setValue(name);
        firebase.child("events").child(code).child("info").setValue(info);
        firebase.child("events").child(code).child("day").setValue(day);
        firebase.child("events").child(code).child("month").setValue(month);
        firebase.child("events").child(code).child("year").setValue(year);
        firebase.child("events").child(code).child("hour").setValue(hour);
        firebase.child("events").child(code).child("minute").setValue(minute);
        firebase.child("events").child(code).child("isAM").setValue(isAm);


    }

    public static byte[] serializeObject(Object o) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                ObjectOutput out = new ObjectOutputStream(bos);
                out.writeObject(o);
                out.close();

                // Get the bytes of the serialized object
                byte[] buf = bos.toByteArray();

                return buf;
            } catch(IOException ioe) {
                Log.e("serializeObject", "error", ioe);

                return null;
            }
    }

    public static void saveLocal(Context context) throws FileNotFoundException, IOException{
        String fileName = "eventData";
        byte[] bytes = serializeObject(allEvents);
        FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        fos.write(bytes);
        fos.close();
    }


    public static ArrayList<Event> getAllEvents() {
        return allEvents;
    }
}
