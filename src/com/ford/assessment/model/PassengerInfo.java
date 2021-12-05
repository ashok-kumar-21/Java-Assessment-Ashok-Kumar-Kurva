package com.ford.assessment.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class PassengerInfo {
    long passengerId;
    String name;
    char sex;
    int age;
    String startingPoint;
    String endingPoint;
    Date travelDate;
    boolean isSpecialSeatNeeded;
    boolean isWomanOnlySeatNeeded;

    public PassengerInfo() {
    }

    public PassengerInfo(long passengerId, String name, char sex, int age, String startingPoint, String endingPoint, Date travelDate, boolean isSpecialSeatNeeded, boolean isWomanOnlySeatNeeded) {
        this.passengerId = passengerId;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
        this.travelDate = travelDate;
        this.isSpecialSeatNeeded = isSpecialSeatNeeded;
        this.isWomanOnlySeatNeeded = isWomanOnlySeatNeeded;
    }

    public long getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(long passengerId) {
        this.passengerId = passengerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getEndingPoint() {
        return endingPoint;
    }

    public void setEndingPoint(String endingPoint) {
        this.endingPoint = endingPoint;
    }

    public Date getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public boolean isSpecialSeatNeeded() {
        return isSpecialSeatNeeded;
    }

    public void setSpecialSeatNeeded(boolean specialSeatNeeded) {
        isSpecialSeatNeeded = specialSeatNeeded;
    }

    public boolean isWomanOnlySeatNeeded() {
        return isWomanOnlySeatNeeded;
    }

    public void setWomanOnlySeatNeeded(boolean womanOnlySeatNeeded) {
        isWomanOnlySeatNeeded = womanOnlySeatNeeded;
    }

    public String getSeatPreference(){
        if(this.isWomanOnlySeatNeeded)
            return "Woman Only";
        else if(this.isSpecialSeatNeeded)
            return "Special";
        else
            return "Normal";
    }

    @Override
    public String toString() {
        return "PassengerInfo{" +
                "passengerId=" + passengerId +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", startingPoint='" + startingPoint + '\'' +
                ", endingPoint='" + endingPoint + '\'' +
                ", travelDate=" + travelDate +
                ", isSpecialSeatNeeded=" + isSpecialSeatNeeded +
                ", isWomanOnlySeatNeeded=" + isWomanOnlySeatNeeded +
                '}';
    }

    @Override
    public boolean equals(Object obj){
        if(obj == this){
            return true;
        }
        if(!(obj instanceof PassengerInfo))
            return false;
        PassengerInfo passengerInfo = (PassengerInfo) obj;
        return passengerId == passengerInfo.passengerId && name.equals(passengerInfo.name)
                && (Character.compare(sex,passengerInfo.sex) == 0) && age == passengerInfo.age
                && startingPoint.equalsIgnoreCase(passengerInfo.startingPoint) &&
                endingPoint.equals(passengerInfo.endingPoint) && travelDate.equals(passengerInfo.travelDate)
                && isSpecialSeatNeeded == passengerInfo.isSpecialSeatNeeded && isWomanOnlySeatNeeded == passengerInfo.isWomanOnlySeatNeeded ;

    }

    public static List<PassengerInfo> populatePassenger(PassengerInfo passengerInfo){
        List<PassengerInfo> passengerInfoList = new ArrayList<>();
        passengerInfoList.add(passengerInfo);
        return passengerInfoList;

    }
}
