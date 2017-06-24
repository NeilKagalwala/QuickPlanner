package com.example.shimk.trial;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        ListView lv = (ListView) findViewById(R.id.eventList);
        setListView(lv);

//        Firebase fb = GlobalEvent.getFirebase();
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("events");


//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                ArrayList<Event> events = dataSnapshot.getValue(ArrayList.class);
//
//                Context context = getApplicationContext();
//
//                ArrayAdapter<Event> arrayAdapter = new ArrayAdapter<Event>(context, android.R.layout.simple_list_item_1, events);
//
//                lv.setAdapter(arrayAdapter);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w("myApp", "Failed to read value.", error.toException());
//            }
//        });
    }

    public void setListView(ListView lv) {
        ArrayList<Event> events = GlobalEvent.getAllEvents();
        ArrayList<String> eventName = new ArrayList<String>();
        for (int i = 0; i < events.size(); i ++) {
            Event event = events.get(i);
            String name = event.getName() + ": " + event.getCode();
            eventName.add(name);
        }
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, eventName);
        lv.setAdapter(listAdapter);
        openEventInfo();
    }

    public void joinEvent(View v) {
        Intent intent = new Intent(this, JoinEvent.class);
        startActivity(intent);
    }

    public void createEvent (View v) {
        Intent intent = new Intent(this, CreateEvent.class);
        startActivity(intent);
    }

    public void openEventInfo () {
        ListView lv = (ListView) findViewById(R.id.eventList);
        lv.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<Event> allEvents = GlobalEvent.getAllEvents();
                Event event = allEvents.get(position);
                openEventView(event);
            }


        });
    }

    public void openEventView(Event event) {
        Intent intent = new Intent(this, EventViewer.class);
        intent.putExtra("event", event);
        startActivity(intent);
    }

}
