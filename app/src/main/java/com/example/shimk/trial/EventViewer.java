package com.example.shimk.trial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EventViewer extends AppCompatActivity {

    private Event currentEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_viewer);
        Intent intent = getIntent();
        Event event = (Event)intent.getSerializableExtra("event");
        loadEvent(event);
    }

    public void loadEvent(Event event) {
            //TextView android:editable = "true";
            currentEvent = event;
            TextView text = (TextView)findViewById(R.id.eventCode);
            text.setText(event.getCode());
            text = (TextView)findViewById(R.id.eventName);
            text.setText(event.getName());
            text = (TextView)findViewById(R.id.eventInfo);
            text.setText(event.getInfo());
            text = (TextView)findViewById(R.id.eventDate);
            int[] date = event.getDate();
            String dateString = "";
            String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        String month = months[date[1]-1];
        dateString = dateString + date[0] + " " + month + " " + date[2];
//            for (int i = 0; i < date.length; i++) {
//                dateString = dateString + date[i] + "/";
//            }
//            dateString = dateString.substring(0, dateString.length() - 1);
//            dateString.substring(0, dateString.length());
        text.setText(dateString);

            int[] time = event.getTime();
        String timeString = "";
        timeString = timeString + time[0] + ":" + time[1];
        if (event.isAM()) {
            timeString+= " " + "AM";
        }
        else {
            timeString+= " " + "PM";
        }

        text = (TextView)findViewById(R.id.eventTime);
        text.setText(timeString);
    }

    public void editEvent (View v) {
        if (currentEvent.isOwner()) {
            Intent intent = new Intent(this, EventEdit.class);
            intent.putExtra("event",currentEvent);
            startActivity(intent);
        }
    }

    public void returnMain(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void deleteEvent(View v) {
        GlobalEvent.remEvent(currentEvent);
        returnMain(v);
    }
}
