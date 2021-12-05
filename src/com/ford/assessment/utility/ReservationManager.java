package com.ford.assessment.utility;

import com.ford.assessment.dao.BusMgmtSystemDao;
import com.ford.assessment.model.BusDetails;
import com.ford.assessment.model.BusMaster;
import com.ford.assessment.model.PassengerInfo;
import com.ford.assessment.model.Reservation;
import com.ford.assessment.service.BusManagementService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationManager {

    BufferedWriter bWriter;
    List<BusDetails> busDetailObjectsList;

    List<BusMaster> busMasterList;
    List<PassengerInfo> currentPassengerList;
    List<Reservation> currentReservationList;

    BusMgmtSystemDao busMgmtSystemDao;
    PassengerInfo passengerInfo;


    public ReservationManager() {
        busMgmtSystemDao = new BusMgmtSystemDao();
        passengerInfo = new PassengerInfo();
    }

    public ReservationManager(List<BusMaster> busMasterList, List<PassengerInfo> currentPassengerList, List<Reservation> currentReservationList) {
        this.busMasterList = busMasterList;
        this.currentPassengerList = currentPassengerList;
        this.currentReservationList = currentReservationList;
    }

    public List<BusMaster> getBusMasterList() {
        return busMasterList;
    }

    public void setBusMasterList(List<BusMaster> busMasterList) {
        this.busMasterList = busMasterList;
    }

    public List<PassengerInfo> getCurrentPassengerList() {
        return currentPassengerList;
    }

    public void setCurrentPassengerList(List<PassengerInfo> currentPassengerList) {
        this.currentPassengerList = currentPassengerList;
    }

    public List<Reservation> getCurrentReservationList() {
        return currentReservationList;
    }

    public void setCurrentReservationList(List<Reservation> currentReservationList) {
        this.currentReservationList = currentReservationList;
    }

    //For writing the valid row details of the csv files into their relevant tables.
    public boolean writeBusMasterCsvDetailsToDb(){
        boolean flag = false;
        List <String> busMasterDetails = new ArrayList <String> ();


            String line = "";
            String splitBy = ",";
            try{
                BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\kashok23\\Downloads\\Java Assessment 2021\\Bus-Master.csv"));
                int n=1;
                while((line = br.readLine()) != null){

                    if(n==1) {
                        n+=1;
                        continue;
                    }
                    busMasterDetails.add(line);
                }
                flag = true;
            } catch (Exception e) {
                e.printStackTrace();
                flag=false;
            }

        ArrayList <BusMaster> busMastersDetailsList = new ArrayList<>();
        for(String eachRecord : busMasterDetails){
            String busMasterDetailFields[]= eachRecord.split(",");
            BusMaster obj = new BusMaster();
            obj.setBusNo(busMasterDetailFields[0]);
            obj.setFrom(busMasterDetailFields[1]);
            obj.setTo(busMasterDetailFields[2]);
            obj.setStartingDate(BusManagementService.stringToDateConverter(busMasterDetailFields[3]));
            obj.setStartingTime(busMasterDetailFields[4]);
            obj.setJourneyTime(busMasterDetailFields[5]);
            obj.setTotalStops(Integer.parseInt(busMasterDetailFields[6]));
            obj.setTypeOfBus(busMasterDetailFields[7]);
            busMastersDetailsList.add(obj);
            busMgmtSystemDao.insertBusMasterDetails(obj);
        }

        return flag;
    }
    public boolean writeBusDetailsCsvDetailsToDb(){
        boolean flag = false;
        List <String> busDetails = new ArrayList <String> ();
            String line = "";
            String splitBy = ",";
            try{
                BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\kashok23\\Downloads\\Java Assessment 2021\\Bus-Details.csv"));
                int n=1;
                while((line = br.readLine()) != null){
                    if(n==1) {
                        n+=1;
                        continue;
                    }
                    String[] details = line.split(splitBy);
                    busDetails.add(line);
                }
                flag = true;
            } catch (Exception e) {
                e.printStackTrace();
                flag = false;
            }

        ArrayList <BusDetails> busDetailsList = new ArrayList<>();
        for(String eachRecord : busDetails){
            String busDetailFields[]= eachRecord.split(",");
            BusDetails obj = new BusDetails();
            obj.setBusNo(busDetailFields[0]);
            obj.setTotalSeats(Integer.parseInt(busDetailFields[1]));
            obj.setTotalCommonSeats(Integer.parseInt(busDetailFields[2]));
            obj.setTotalWomenOnlySeats(Integer.parseInt(busDetailFields[3]));
            obj.setTotalSpecialSeats(Integer.parseInt(busDetailFields[4]));
            obj.setAvailableCommonSeats(Integer.parseInt(busDetailFields[5]));
            obj.setAvailableWomenSeats(Integer.parseInt(busDetailFields[6]));
            obj.setAvailableSpecialSeats(Integer.parseInt(busDetailFields[7]));
            busDetailsList.add(obj);
            busMgmtSystemDao.insertBusDetails(obj);
        }
        return flag;
    }

    //2. Read from bus master table and create a list of BusMaster objects and should also call loadBusDetailList()
    public void loadBusMasterList(){
            loadBusDetailsList();
            busMasterList =  busMgmtSystemDao.retrieveBusMasterList(busDetailObjectsList);
//            System.out.println(busMasterList);
    }

    //2. Read from BusDetails table and associate the correct BusDetails object to BusMaster object (use busNo)
    public void loadBusDetailsList(){
        busDetailObjectsList = busMgmtSystemDao.retrieveBusDetailList();
//        System.out.println(busDetailObjectsList);
//        System.out.println("---------------------------------------------------------------------------------");
        return ;
    }

    //3. method to reserve ticket
    public Reservation bookTicket(PassengerInfo passengerInfo){
        boolean present = false;
        if(currentPassengerList == null)
            passengerInfoPopulate(passengerInfo);
        for(PassengerInfo passenger : currentPassengerList){
            if(passenger.getPassengerId() == passengerInfo.getPassengerId()){
                present = true;
                break;
            }
        }
        if(!(present))
            passengerInfoPopulate(passengerInfo);
        for(BusMaster busMaster : busMasterList){
            if(passengerInfo.getStartingPoint().equalsIgnoreCase(busMaster.getFrom()) && passengerInfo.getEndingPoint().equalsIgnoreCase(busMaster.getTo()) && passengerInfo.getTravelDate().equals(busMaster.getStartingDate())){
                if(passengerInfo.isWomanOnlySeatNeeded()){
                    if(busMaster.getBusDetails().getAvailableWomenSeats() > 0){
                        int seatNo = (busMaster.getBusDetails().getTotalWomenOnlySeats() - busMaster.getBusDetails().getAvailableWomenSeats())+1;
                        busMaster.getBusDetails().setAvailableWomenSeats(busMaster.getBusDetails().getAvailableWomenSeats()-1);
                        busMgmtSystemDao.updateWomenOnlySeats(busMaster.getBusDetails(), busMaster.getBusDetails().getBusNo());
                        Reservation reservationObject = new Reservation(seatNo, passengerInfo, busMaster);

                        if(currentReservationList == null)
                            currentReservationList = Reservation.populate(reservationObject);
                        else
                            currentReservationList.add(reservationObject);
                        return reservationObject;
                    }
                }
                else if(passengerInfo.isSpecialSeatNeeded()){
                    if(busMaster.getBusDetails().getAvailableSpecialSeats() > 0){
                        int seatNo = (busMaster.getBusDetails().getTotalSpecialSeats() - busMaster.getBusDetails().getAvailableSpecialSeats())+1;
                        busMaster.getBusDetails().setAvailableSpecialSeats(busMaster.getBusDetails().getAvailableSpecialSeats()-1);
                        busMgmtSystemDao.updateSpecialSeats(busMaster.getBusDetails(), busMaster.getBusDetails().getBusNo());
                        Reservation reservationObject = new Reservation(seatNo, passengerInfo, busMaster);

                        if(currentReservationList == null)
                            currentReservationList = Reservation.populate(reservationObject);
                        else
                            currentReservationList.add(reservationObject);
                        return reservationObject;
                    }
                }
                else{
                    if(busMaster.getBusDetails().getAvailableCommonSeats() > 0){
                        int seatNo = (busMaster.getBusDetails().getTotalCommonSeats() - busMaster.getBusDetails().getAvailableCommonSeats())+1;
                        busMaster.getBusDetails().setAvailableCommonSeats(busMaster.getBusDetails().getAvailableCommonSeats()-1);
                        busMgmtSystemDao.updateCommonSeats(busMaster.getBusDetails(), busMaster.getBusDetails().getBusNo());
                        Reservation reservationObject = new Reservation(seatNo, passengerInfo, busMaster);

                        if(currentReservationList == null)
                            currentReservationList = Reservation.populate(reservationObject);
                        else
                            currentReservationList.add(reservationObject);
                        return reservationObject;
                    }
                }

            }
        }
        return null;
    }

    //cancel the ticket booked
    public boolean cancelTicket(Reservation reservation) {
        boolean present = false;
        if (currentReservationList != null){
            for (Reservation reservation1 : currentReservationList) {
                if (reservation1.equals(reservation)) {
                    present = true;
                    if (reservation.getPassengerInfo().isWomanOnlySeatNeeded()) {
                        reservation.getBusMaster().getBusDetails().setAvailableWomenSeats(reservation.getBusMaster().getBusDetails().getAvailableWomenSeats() + 1);
                        busMgmtSystemDao.updateWomenOnlySeats(reservation.getBusMaster().getBusDetails(), reservation.getBusMaster().getBusDetails().getBusNo());
                    } else if (reservation.getPassengerInfo().isSpecialSeatNeeded()) {
                        reservation.getBusMaster().getBusDetails().setAvailableSpecialSeats(reservation.getBusMaster().getBusDetails().getAvailableSpecialSeats() + 1);
                        busMgmtSystemDao.updateSpecialSeats(reservation.getBusMaster().getBusDetails(), reservation.getBusMaster().getBusDetails().getBusNo());
                    } else {
                        reservation.getBusMaster().getBusDetails().setAvailableCommonSeats(reservation.getBusMaster().getBusDetails().getAvailableCommonSeats() + 1);
                        busMgmtSystemDao.updateSpecialSeats(reservation.getBusMaster().getBusDetails(), reservation.getBusMaster().getBusDetails().getBusNo());
                    }
                    break;
                }
            }
    }
            if(present == false)
                System.out.println("Reservation doesn't exist");
            else{
                currentReservationList.remove(reservation);
                System.out.println("Reservation Cancelled");
                present = true;
            }
            return present;
    }

    //to get all reservations for bus with given bus number
    public List<Reservation> getAllReservationsForBus(String busNumber){
        boolean flag = false;
        List<Reservation>  reservationObjects = new ArrayList<>();
        if(currentReservationList != null) {
            for (Reservation reservation2 : currentReservationList) {
                if (busNumber.equals(reservation2.getBusMaster().getBusNo())) {
                    flag = true;
                    reservationObjects.add(reservation2);
                }
            }
        }
        if(reservationObjects.isEmpty())
            return null;
        else
           return reservationObjects;
    }

    //suggest alternative plan
    public List<BusMaster> suggestAlternatePlan(PassengerInfo passengerInfo){
        List<BusMaster>  busMasterArrayList = new ArrayList<>();
        boolean present =false;
        for(BusMaster busMaster: busMasterList) {
            if (busMaster.getFrom().equalsIgnoreCase(passengerInfo.getStartingPoint()) && busMaster.getTo().equalsIgnoreCase(passengerInfo.getEndingPoint()) ){
                busMasterArrayList.add(busMaster);
            }
        }
        for(BusMaster busMaster: busMasterList){
           if((busMaster.getFrom().equalsIgnoreCase(passengerInfo.getStartingPoint())) && (!(busMaster.getTo().equalsIgnoreCase(passengerInfo.getEndingPoint()))) ){
               for(BusMaster busMaster1 : busMasterList){
                    if((passengerInfo.getEndingPoint().equalsIgnoreCase(busMaster1.getTo())) && (!(busMaster1.getFrom().equalsIgnoreCase(passengerInfo.getStartingPoint()))) && (busMaster.getTo().equalsIgnoreCase(busMaster1.getFrom())) && ((busMaster.getStartingDate().before(busMaster1.getStartingDate())) || (busMaster.getStartingDate().equals(busMaster1.getStartingDate())))){
                        present = true;
                        busMasterArrayList.add(busMaster);
                        busMasterArrayList.add(busMaster1);
                            break;
                        }
                    }
                }
           if(present == true)
            break;
            }

        return busMasterArrayList;
    }

    public boolean writeAllReservationsToFile(){
        boolean flag=false;
        File file1=new File("C:\\Users\\kashok23\\Documents\\Reservation - BusManagement\\Reservation.csv");
        try {
            bWriter = new BufferedWriter(new FileWriter(file1,true));
            if(currentReservationList != null) {
                for (Reservation reservation : currentReservationList) {
                    bWriter.write(reservation.returnReservationObject(reservation));
                }
                flag = true;
                System.out.println("We have written into file successfully");
            }
            else{
                System.out.println("No reservations available to write into file");
            }
            bWriter.flush();
            bWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
            flag=false;
        }
        return flag;
    }

    public void passengerInfoPopulate(PassengerInfo passengerInfo){
        if(currentPassengerList == null)
            currentPassengerList = PassengerInfo.populatePassenger(passengerInfo);
        else
            currentPassengerList.add(passengerInfo);
    }

}
//Ashok Kumar Kurva