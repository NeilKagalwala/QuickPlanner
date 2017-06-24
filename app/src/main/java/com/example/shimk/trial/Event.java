package com.example.shimk.trial;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by shimk on 5/13/2016.
 */
@SuppressWarnings("serial")
public class Event implements Serializable {
    private String name;
    private String info;
    private int[] date;
    private int[] time;
    private boolean isAM;
    private String code;
    private boolean isOwner;

    public Event(String newName,String newInfo,int month, int day, int year, int hour, int minute, boolean am, boolean isOwnerr) {
        name = newName;
        info = newInfo;
        date = new int[]{day, month, year};
        time = new int[]{hour, minute};
        isAM = am;
        isOwner = isOwnerr;
        code = createCode();

    }
    public Event(String newName,String newInfo,int month, int day, int year, int hour, int minute, boolean am, boolean isOwnerr, String codeEntry) {
        name = newName;
        info = newInfo;
        date = new int[]{day, month, year};
        time = new int[]{hour, minute};
        isAM = am;
        isOwner = isOwnerr;
        code = codeEntry;

    }

    public String createCode() {
        String[] letters = new String[] {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        String code = "";
        Random rand = new Random();
        for (int i = 0; i < 6; i++) {
           if (rand.nextBoolean()) {
               code = code + generateInt();
           }
            else {
               code+=generateLetter(letters);
           }

        }
        return code;
    }

    public int generateInt() {
        Random rand = new Random();
        return rand.nextInt(10);
    }


    public String generateLetter(String[] letters) {
        Random rand = new Random();
        return letters[rand.nextInt(26)];
    }


    public String getInfo() {
        return info;
    }

    public int[] getDate() {
        return date;
    }

    public int[] getTime() {
        return time;
    }

    public boolean isAM() {
        return isAM;
    }

    public String getName() {

        return name;
    }

    public boolean isOwner() {
        return isOwner;
    }
    public String getCode() {return code;}


    public void setName(String name) {
        this.name = name;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setDate(int[] date) {
        this.date = date;
    }

    public void setTime(int[] time) {
        this.time = time;
    }

    public void setAM(boolean AM) {
        isAM = AM;
    }
}
