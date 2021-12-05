package com.ford.assessment.model;

import java.util.ArrayList;
import java.util.List;

public class Reservation {
    int seatNumber;
    PassengerInfo passengerInfo;
    BusMaster busMaster;

    public Reservation() {
    }

    public Reservation(int seatNumber, PassengerInfo passengerInfo, BusMaster busMaster) {
        this.seatNumber = seatNumber;
        this.passengerInfo = passengerInfo;
        this.busMaster = busMaster;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public PassengerInfo getPassengerInfo() {
        return passengerInfo;
    }

    public void setPassengerInfo(PassengerInfo passengerInfo) {
        this.passengerInfo = passengerInfo;
    }

    public BusMaster getBusMaster() {
        return busMaster;
    }

    public void setBusMaster(BusMaster busMaster) {
        this.busMaster = busMaster;
    }

    public String returnReservationObject(Reservation reservation){
        return reservation.getBusMaster().getBusNo() + "," + reservation.getBusMaster().getFrom() + "," + reservation.getBusMaster().getTo() + "," + reservation.getBusMaster().getStartingDate()
                + "," + reservation.getBusMaster().getStartingTime() + "," + reservation.getPassengerInfo().getName() + "," + reservation.getPassengerInfo().getAge()
                + "," + reservation.getPassengerInfo().getSex() + "," + reservation.getSeatNumber() + "," + reservation.getPassengerInfo().getSeatPreference() + "\n";
    }
    @Override
    public String toString() {
        return ("Bus No: "+getBusMaster().getBusNo()+" From: "+getBusMaster().getFrom() +" To: "+getBusMaster().getTo() +" Date: "+getBusMaster().getStartingDate() +" Time: "+getBusMaster().getStartingTime() +" Seat No: "+seatNumber + "\n"
                + "Passenger Name: "+getPassengerInfo().getName() +" Age: "+getPassengerInfo().getAge() +" Sex: "+getPassengerInfo().getSex()
                + " Seat Prefernces: "+getPassengerInfo().getSeatPreference());
    }

    public boolean equals(Object obj){
        if(obj == this)
            return true;
        if(!(obj instanceof Reservation))
            return false;
        Reservation reserve = (Reservation) obj;
        return seatNumber == reserve.getSeatNumber() && passengerInfo.equals(reserve.getPassengerInfo())
                && busMaster.equals(reserve.getBusMaster());
    }

    public static List<Reservation> populate(Reservation reservation){
        List<Reservation> reservationList = new ArrayList<>();
        reservationList.add(reservation);
        return reservationList;
    }
}
