package com.ford.assessment.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;


public class BusDetails {

    @Pattern(regexp = "^[0-9]{3}+[a-zA-Z]{1}$",message = "Bus No is Invalid")
    String busNo;

    @PositiveOrZero(message = "Number should be greater than zero")
    int totalSeats;
    @PositiveOrZero(message = "Number should be greater than zero")
    int totalCommonSeats;
    @PositiveOrZero(message = "Number should be greater than zero")
    int totalWomenOnlySeats;
    @PositiveOrZero(message = "Number should be greater than zero")
    int totalSpecialSeats;
    @PositiveOrZero(message = "Number should be greater than zero")
    int availableCommonSeats;
    @PositiveOrZero(message = "Number should be greater than zero")
    int availableWomenSeats;
    @PositiveOrZero(message = "Number should be greater than zero")
    int availableSpecialSeats;

    public BusDetails() {
    }

    public BusDetails(String busNo, int totalSeats, int totalCommonSeats, int totalWomenOnlySeats, int totalSpecialSeats, int availableCommonSeats, int availableWomenSeats, int availableSpecialSeats) {
        this.busNo = busNo;
        this.totalSeats = totalSeats;
        this.totalCommonSeats = totalCommonSeats;
        this.totalWomenOnlySeats = totalWomenOnlySeats;
        this.totalSpecialSeats = totalSpecialSeats;
        this.availableCommonSeats = availableCommonSeats;
        this.availableWomenSeats = availableWomenSeats;
        this.availableSpecialSeats = availableSpecialSeats;
    }

    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getTotalCommonSeats() {
        return totalCommonSeats;
    }

    public void setTotalCommonSeats(int totalCommonSeats) {
        this.totalCommonSeats = totalCommonSeats;
    }

    public int getTotalWomenOnlySeats() {
        return totalWomenOnlySeats;
    }

    public void setTotalWomenOnlySeats(int totalWomenOnlySeats) {
        this.totalWomenOnlySeats = totalWomenOnlySeats;
    }

    public int getTotalSpecialSeats() {
        return totalSpecialSeats;
    }

    public void setTotalSpecialSeats(int totalSpecialSeats) {
        this.totalSpecialSeats = totalSpecialSeats;
    }

    public int getAvailableCommonSeats() {
        return availableCommonSeats;
    }

    public void setAvailableCommonSeats(int availableCommonSeats) {
        this.availableCommonSeats = availableCommonSeats;
    }

    public int getAvailableWomenSeats() {
        return availableWomenSeats;
    }

    public void setAvailableWomenSeats(int availableWomenSeats) {
        this.availableWomenSeats = availableWomenSeats;
    }

    public int getAvailableSpecialSeats() {
        return availableSpecialSeats;
    }

    public void setAvailableSpecialSeats(int availableSpecialSeats) {
        this.availableSpecialSeats = availableSpecialSeats;
    }

    @Override
    public String toString() {
        return "BusDetails{" +
                "busNo='" + busNo + '\'' +
                ", totalSeats=" + totalSeats +
                ", totalCommonSeats=" + totalCommonSeats +
                ", totalWomenOnlySeats=" + totalWomenOnlySeats +
                ", totalSpecialSeats=" + totalSpecialSeats +
                ", availableCommonSeats=" + availableCommonSeats +
                ", availableWomenSeats=" + availableWomenSeats +
                ", availableSpecialSeats=" + availableSpecialSeats +
                '}';
    }

    public boolean equals(Object obj){
        if(obj == this)
            return true;
        if(!(obj instanceof BusDetails))
            return false;
        BusDetails bD = (BusDetails)obj ;
        return busNo.equalsIgnoreCase(bD.busNo) && totalSeats == bD.totalSeats
                && totalCommonSeats == bD.totalCommonSeats && totalWomenOnlySeats == bD.totalWomenOnlySeats
                && totalSpecialSeats == bD.totalSpecialSeats && availableCommonSeats == bD.availableCommonSeats
                && availableWomenSeats == bD.availableWomenSeats && availableSpecialSeats == bD.availableSpecialSeats ;
    }
}
