package com.example.shimk.trial;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.firebase.client.Firebase;

public class EventEdit extends AppCompatActivity {

    Firebase firebase = GlobalEvent.getFirebase();
    Event currentEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        Intent intent = getIntent();
        Event event = (Event)intent.getSerializableExtra("event");
    }

    public void editValues(View v) {

        EditText mEdit;
        CheckBox amPm;
        mEdit = (EditText)findViewById(R.id.editEventText);
        String eventName = mEdit.getText().toString();
        boolean changeName = isEmpty(eventName);

        mEdit = (EditText)findViewById(R.id.editInfoText);
        String eventInfo = mEdit.getText().toString();
        boolean changeInfo = isEmpty(eventInfo);

        mEdit = (EditText)findViewById(R.id.editMonth);
        String sMonth = mEdit.getText().toString();
        boolean changeMonth = isEmpty(sMonth);
        int month = -1;
        if (changeMonth) {
            month = Integer.parseInt(mEdit.getText().toString());
        }

        mEdit = (EditText)findViewById(R.id.editYear);
        String sYear = mEdit.getText().toString();
        boolean changeYear = isEmpty(sYear);
        int year = -1;
        if (changeYear) {
            year = Integer.parseInt(mEdit.getText().toString());
        }

        mEdit = (EditText)findViewById(R.id.editDay);
        String sDay = mEdit.getText().toString();
        boolean changeDay = isEmpty(sDay);
        int day = -1;
        if (changeDay) {
            day = Integer.parseInt(mEdit.getText().toString());
        }

        mEdit = (EditText)findViewById(R.id.editHour);
        String sHour = mEdit.getText().toString();
        boolean changeHour = isEmpty(sHour);
        int hour = -1;
        if (changeHour) {
            hour = Integer.parseInt(mEdit.getText().toString());
        }

        mEdit = (EditText)findViewById(R.id.editMinute);
        String sMinute = mEdit.getText().toString();
        boolean changeMinute = isEmpty(sMinute);
        int minute = -1;
        if (changeMinute) {
            minute = Integer.parseInt(mEdit.getText().toString());
        }

        amPm = (CheckBox)findViewById(R.id.amPm);
        boolean isAm = !amPm.isChecked();

        boolean[] changes = {changeName, changeInfo, changeDay, changeMonth, changeYear, changeHour, changeMinute, isAm};
        String[] changedStrings = {eventName, eventInfo};
        int[] changedNums = {day, month, year, hour, minute};


        changeValues(changes, changedStrings, changedNums);

        int[] newDate = new int[3];
        int[] newTime = new int[2];

        if (changeName) {
            currentEvent.setName(eventName);
        }
        if (changeInfo) {
            currentEvent.setInfo(eventInfo);
        }
        if (changeDay) {
            newDate[0] = day;
        }
        if (changeMonth) {
            newDate[1] = month;
        }
        if (changeYear) {
            newDate[2] = year;
        }
        if (changeHour) {
            newTime[0] = hour;
        }
        if (changeMinute) {
            newTime[1] = minute;
        }
        currentEvent.setDate(newDate);
        currentEvent.setTime(newTime);
        currentEvent.setAM(isAm);

        Event replacementEvent = currentEvent;
        GlobalEvent.remEvent(currentEvent);
        GlobalEvent.addEvent(replacementEvent, true);

        startViewer(replacementEvent);

    }

    public boolean isEmpty(String value) {
        if (value != null) {
            return true;
        }
        return false;
    }

    public void changeValues(boolean[] changes, String[] changedStrings, int[] changedNums) {
        String code = currentEvent.getCode();
        String[] fields = {"name", "info", "day", "month", "year", "hour", "minute", "isAM"};
        for (int i = 0; i < fields.length; i++) {
            if (changes[i]) {
                if (i == 0 || i == 1) {
                    firebase.child("events").child(code).child(fields[i]).setValue(changedStrings[i]);
                }
                else if (i > 1 && i < 7) {
                    firebase.child("events").child(code).child(fields[i]).setValue(changedNums[i]);
                }
                else if (i == 7) {
                    firebase.child("events").child(code).child(fields[i]).setValue(changes[i]);
                }
            }
        }
    }

    public void startViewer(Event event) {
        Intent intent = new Intent(this, EventViewer.class);
        intent.putExtra("event", event);
        startActivity(intent);

    }
}
