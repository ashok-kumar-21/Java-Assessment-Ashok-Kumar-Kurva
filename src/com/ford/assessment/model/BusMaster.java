package com.ford.assessment.model;




import javax.validation.constraints.Pattern;
import java.util.Date;


public class BusMaster {

    @Pattern(regexp = "^[0-9]{3}+[a-zA-Z]{1}$",message = "Bus No is Invalid")
    String busNo;
    String from;
    String to;
    Date startingDate;
    String startingTime;
    String journeyTime;
    int totalStops;
    String typeOfBus;
    BusDetails busDetails;

    public BusMaster() {
    }

    public BusMaster(String busNo, String from, String to, Date startingDate, String startingTime, String journeyTime, int totalStops, String typeOfBus, BusDetails busDetails) {
        this.busNo = busNo;
        this.from = from;
        this.to = to;
        this.startingDate = startingDate;
        this.startingTime = startingTime;
        this.journeyTime = journeyTime;
        this.totalStops = totalStops;
        this.typeOfBus = typeOfBus;
        this.busDetails = busDetails;
    }

    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public String getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(String startingTime) {
        this.startingTime = startingTime;
    }

    public String getJourneyTime() {
        return journeyTime;
    }

    public void setJourneyTime(String journeyTime) {
        this.journeyTime = journeyTime;
    }

    public int getTotalStops() {
        return totalStops;
    }

    public void setTotalStops(int totalStops) {
        this.totalStops = totalStops;
    }

    public String getTypeOfBus() {
        return typeOfBus;
    }

    public void setTypeOfBus(String typeOfBus) {
        this.typeOfBus = typeOfBus;
    }

    public BusDetails getBusDetails() {
        return busDetails;
    }

    public void setBusDetails(BusDetails busDetails) {
        this.busDetails = busDetails;
    }

    public boolean verifyBusDetails(BusDetails b){
         return this.busNo.equalsIgnoreCase(b.busNo);

    }

    @Override
    public String toString() {
        return "BusMaster{" +
                "busNo='" + busNo + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", startingDate='" + startingDate + '\'' +
                ", startingTime='" + startingTime + '\'' +
                ", journeyTime='" + journeyTime + '\'' +
                ", totalStops=" + totalStops +
                ", typeOfBus='" + typeOfBus + '\'' +
                ", busDetails=" + busDetails +
                '}';
    }

    public boolean equals(Object obj){
        if(obj == this)
            return true;
        if(!(obj instanceof BusMaster))
            return false;
        BusMaster bM = (BusMaster)obj;
        return busNo.equalsIgnoreCase(bM.busNo) && from.equalsIgnoreCase(bM.from)
                && to.equalsIgnoreCase(bM.to) && startingDate.equals(bM.startingDate)
                && startingTime.equalsIgnoreCase(bM.startingTime) && journeyTime.equals(bM.journeyTime)
                && totalStops == bM.totalStops && typeOfBus.equalsIgnoreCase(bM.typeOfBus) && busDetails.equals(bM.busDetails) ;
    }

    public String returnBusMasterObject(BusMaster busMaster){
        return "Bus No:"+busMaster.getBusNo()+" From:"+busMaster.getFrom()+" To:"+busMaster.getTo()
                +" Date:"+busMaster.getStartingDate()+" Time:"+busMaster.getStartingTime();
    }

}
