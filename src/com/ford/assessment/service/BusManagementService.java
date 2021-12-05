package com.ford.assessment.service;

import com.ford.assessment.model.BusDetails;
import com.ford.assessment.model.BusMaster;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BusManagementService {
    private static BufferedWriter bWriter;
    static File file1 =  new File("C:\\Users\\kashok23\\Documents\\Reservation - BusManagement\\Error.txt");

    public BusManagementService() {
    }

    public static java.sql.Date utilToSqlDateConverter(java.util.Date utDate) {
        java.sql.Date sqlDate = null;
        if (utDate != null) {
            sqlDate = new java.sql.Date(utDate.getTime());
        }
        return sqlDate;
    }

    public static java.util.Date stringToDateConverter(String stringDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d-MMM-yy");
        dateFormat.setLenient(false);
        try {
            return dateFormat.parse(stringDate);
        } catch (ParseException pe) {
            return null;
        }
    }

    public static void  writeBusDetailsErrorToTextFile(String message, BusDetails busDetails){
        try {
            bWriter = new BufferedWriter(new FileWriter(file1,true));
            bWriter.write(message+" -> "+busDetails + "\n");
            bWriter.flush();
            bWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void  writeBusMasterErrorToTextFile(String message, BusMaster busMaster){
        try {
            bWriter = new BufferedWriter(new FileWriter(file1,true));
            bWriter.write(message+" -> "+busMaster + "\n");
            bWriter.flush();
            bWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
