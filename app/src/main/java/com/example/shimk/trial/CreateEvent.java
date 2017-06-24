package com.example.shimk.trial;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CreateEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
    }
    public void createNewEvent(View v) throws IOException{
        Event event = creatingNewEvent();
        if (event != null) {
            Intent intent = new Intent(this, EventViewer.class);
            intent.putExtra("event", event);
            startActivity(intent);
        }
        if(event == null) {
            TextView text;
            text = (TextView)findViewById(R.id.errorMessage);
            text.setVisibility(View.VISIBLE);
        }
    }


    public Event creatingNewEvent()throws IOException{
        EditText mEdit;
        CheckBox amPm;
        mEdit = (EditText)findViewById(R.id.editEventText);
        String eventName = mEdit.getText().toString();
        mEdit = (EditText)findViewById(R.id.editInfoText);
        String eventInfo = mEdit.getText().toString();


        mEdit = (EditText)findViewById(R.id.editMonth);
        int month = Integer.parseInt(mEdit.getText().toString());

        mEdit = (EditText)findViewById(R.id.editYear);
        int year = Integer.parseInt(mEdit.getText().toString());
        mEdit = (EditText)findViewById(R.id.editDay);
        int day = Integer.parseInt(mEdit.getText().toString());
        if (month > 12 || month <= 0)  {
            return null;
        }
        if (month == 1 || month == 3 || month == 5|| month == 7|| month == 8 || month == 10|| month == 12 ){
            if( day > 31 || day < 1 ) {
                return null;
            }
        }
        else if (month == 4 || month == 6 || month == 9 || month == 11) {
            if ( 30 < day || day < 1) {
                return null;
            }
        }
        else {
            if (year%4 != 0) {
                if (day > 28 || day < 1 ) {
                    return null;
                }
            }
            else {
                if (day > 29 || day < 1) {
                    return null;
                }
            }
        }

        mEdit = (EditText)findViewById(R.id.editHour);
        int hour = Integer.parseInt(mEdit.getText().toString());
        if(hour > 12 || hour <= 0)  {
            return null;
        }

        mEdit = (EditText)findViewById(R.id.editMinute);
        int minute = Integer.parseInt(mEdit.getText().toString());
        if (minute < 0 || minute >= 60) {
            return null;
        }

        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(c.getTime());
        int yearr = Integer.parseInt(date.substring(0, 4));
        int monthh = Integer.parseInt(date.substring(5, 7));
        int dayy = Integer.parseInt(date.substring(8));

        if (yearr > year || (yearr == year && monthh > month) || (yearr == year && monthh == month && dayy > day)) {
            return null;
        }

        amPm = (CheckBox)findViewById(R.id.amPm);
        boolean isAm = !amPm.isChecked();


        Event newEvent = new Event(eventName, eventInfo, month, day, year, hour, minute, isAm, true);

        Intent intent = new Intent(this, EventViewer.class);
        intent.putExtra("event", newEvent);
        //Move event into database
        GlobalEvent.addEvent(newEvent, true);
        GlobalEvent.saveLocal(CreateEvent.this);
        startActivity(intent);
        return newEvent;


    }

//    public void saveEvent() {
//
//    }

//    public void saveEvent() {
//        String filename = "events";
//        FileOutputStream outputStream;
//        ArrayList<Event> events = GlobalEvent.getAllEvents();
//
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        ObjectOutputStream oos = new ObjectOutputStream(bos);
//
//        byte[] bytes = bos.toByteArray();
//
//        try {
//            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
//            //outputStream.write(events);
//            oos.writeObject(events);
//            outputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }



}
