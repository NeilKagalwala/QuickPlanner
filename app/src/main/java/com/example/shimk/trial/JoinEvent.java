package com.example.shimk.trial;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.core.Tag;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JoinEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_event);

    }

    public void joinNewEvent(View v) {
        EditText text;
        text = (EditText)findViewById(R.id.eventCodeEntry);
        final String codeEntry = text.getText().toString();
        String entry = codeEntry;

        Firebase fb = GlobalEvent.getFirebase();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String entry = codeEntry;
                    if (dataSnapshot.child("events").hasChild(entry)) {
                        String name = dataSnapshot.child("events").child(entry).child("name").getValue(String.class);
                        String info = dataSnapshot.child("events").child(entry).child("info").getValue(String.class);
                        int day = dataSnapshot.child("events").child(entry).child("day").getValue(int.class);
                        int month = dataSnapshot.child("events").child(entry).child("month").getValue(int.class);
                        int year = dataSnapshot.child("events").child(entry).child("year").getValue(int.class);
                        int hour = dataSnapshot.child("events").child(entry).child("hour").getValue(int.class);
                        int minute = dataSnapshot.child("events").child(entry).child("minute").getValue(int.class);
                        boolean isAm = dataSnapshot.child("events").child(entry).child("isAM").getValue(boolean.class);

                        Event joinedEvent = new Event (name, info, month, day, year, hour, minute, isAm, false, entry);
                        GlobalEvent.addEvent(joinedEvent, false);
                        startViewer(joinedEvent);
                    }
                    else {
                        redirect();
                    }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.w("myApp", "Failed to read value.", databaseError.toException());
            }
        });


//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Event joinedEvent;
//                ArrayList<Event> events = dataSnapshot.getValue(ArrayList.class);
//                for (int i = 0; i < events.size(); i++) {
//                    Event event = events.get(i);
//                    if (event.getCode().equals(codeEntry)) {
//                        joinedEvent = event;
//                        GlobalEvent.addEvent(event, false);
//                        startViewer(event);
//                    }
//                }
//                //displayError();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w("myApp", "Failed to read value.", error.toException());
//            }
//        });


    }
    public void redirect() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void startViewer(Event event) {
        Intent intent = new Intent(this, EventViewer.class);
        intent.putExtra("event", event);
        startActivity(intent);

    }

    public void displayError(){
        TextView text = (TextView)findViewById(R.id.joinErrorMessage);
        text.setVisibility(View.VISIBLE);
    }
        //Find event from database
        //If event is in database, add to locally stored events
        //If event is not in database, prompt user to re enter code


    }

