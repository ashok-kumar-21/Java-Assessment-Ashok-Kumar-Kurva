package com.ford.assessment.dbmanager;

import com.ford.assessment.dao.BusMgmtSystemDao;
import com.ford.assessment.model.BusMaster;
import com.ford.assessment.model.PassengerInfo;
import com.ford.assessment.model.Reservation;
import com.ford.assessment.utility.ReservationManager;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class BusMgmtDbManager {
    int choice;
    String reply = "yes";
    Scanner sc=new Scanner(System.in);
    ReservationManager reservationManager;
    BusMgmtSystemDao busMgmtSystemDao;

    public BusMgmtDbManager() {
        reservationManager = new ReservationManager();
        busMgmtSystemDao = new BusMgmtSystemDao();
    }

    public void displayMainMenu(){
//        busMgmtSystemDao.createBusMasterTable();
//        busMgmtSystemDao.createBusDetailsTable();
        reservationManager.writeBusMasterCsvDetailsToDb();
        reservationManager.writeBusDetailsCsvDetailsToDb();
        reservationManager.loadBusMasterList();
        while (reply.equalsIgnoreCase("yes")){
            System.out.println("-----------------------Main Menu-----------------------------------");
            System.out.println("1. Book Ticket");
            System.out.println("2. Cancel Ticket");
            System.out.println("3.Get all Reservations Of a Particular Bus");
            System.out.println("4.Available Buses");
            System.out.println("5.Writing all reservations to file");
            System.out.println("6.Exit");
            System.out.println("--------------------------------------------------------------------");

            System.out.println("Enter your choice");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice){
                case 1:{
                    System.out.println("Enter Passenger Details");
                    System.out.println("Enter passenger id");
                    long passengerId = sc.nextLong();
                    sc.nextLine();
                    System.out.println("Enter passenger name");
                    String name = sc.nextLine();
                    System.out.println("Sex (Male/Female)");
                    char sex = sc.nextLine().charAt(0);
                    System.out.println("Enter age of the passenger");
                    int age = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter starting point of the journey");
                    String startingPoint = sc.nextLine();
                    System.out.println("Enter ending point of the journey");
                    String endingPoint = sc.nextLine();
                    System.out.println("Enter travel date in the format (yyyy-MM-dd)");
                    Date travelDate = Date.valueOf(sc.nextLine());
                    System.out.println("Do you want to book a special seat? (true/false)");
                    boolean isSpecialSeatNeeded = sc.nextBoolean();
                    System.out.println("Do you want to book a women-only seat? (true/false)");
                    boolean isWomenOnlySeatNeeded = sc.nextBoolean();
                    sc.nextLine();
                    PassengerInfo passengerInfo = new PassengerInfo(passengerId,name,sex,age,startingPoint,endingPoint,travelDate,isSpecialSeatNeeded,isWomenOnlySeatNeeded);
                    Reservation reservationObject = reservationManager.bookTicket(passengerInfo);
                    if(reservationObject != null){
                        System.out.println("Your ticket is booked successfully");
                        System.out.println(" Your reservation ticket details are "+ reservationObject);
                    }
                    else{
                        System.out.println("Tickets Unavailable");
                    }
                    break;
                }
                case 2:
                {
                    BusMaster busMasterObject = null;
                    PassengerInfo passengerInfoObject = null;
                    Reservation reservation = null;
                    System.out.println("Please provide your reservation details for cancellation");
                    System.out.println("Enter your passenger id");
                    long passengerId = sc.nextLong();
                    sc.nextLine();
                    System.out.println("Enter your travel date in the format (yyyy-MM-dd)");
                    Date travelDate = Date.valueOf(sc.nextLine());
                    System.out.println("Enter your bus number");
                    String busNo = sc.nextLine();
                    System.out.println("Enter your seat number");
                    int seatNo = sc.nextInt();
                    sc.nextLine();
                    for(BusMaster busMaster:reservationManager.getBusMasterList()){
                        if(busMaster.getBusNo().equalsIgnoreCase(busNo)){
                             busMasterObject = busMaster;
                            break;
                        }
                    }
                    if(reservationManager.getCurrentPassengerList()!=null) {
                        for (PassengerInfo passengerInfo : reservationManager.getCurrentPassengerList()){
                            if(passengerInfo.getPassengerId() == passengerId && passengerInfo.getTravelDate().equals(travelDate)){
                                 passengerInfoObject = passengerInfo;
                                break;
                            }
                        }
                    }
                    System.out.println("Processing your request...");
                    if(busMasterObject == null && passengerInfoObject ==null) {
                        System.out.println("Reservation doesn't Exist");
                    }
                    else{
                        reservation = new Reservation(seatNo,passengerInfoObject,busMasterObject);
                        reservationManager.cancelTicket(reservation);
                    }
                    break;
                }
                case 3:
                {
                    System.out.println("Enter bus no");
                    String busNo = sc.nextLine();
                    List<Reservation> reservationObjects = reservationManager.getAllReservationsForBus(busNo);
                    if(reservationObjects == null){
                        System.out.println("There are no reservations for this bus");
                    }
                    else {
                        for (Reservation reservation : reservationObjects)
                            System.out.println(reservation);
                    }
                        break;
                }
                case 4:
                {
                    System.out.println("Enter Passenger Details");
                    System.out.println("Enter passenger id");
                    long passengerId = sc.nextLong();
                    sc.nextLine();
                    System.out.println("Enter passenger name");
                    String name = sc.nextLine();
                    System.out.println("Sex (Male/Female)");
                    char sex = sc.nextLine().charAt(0);
                    System.out.println("Enter age of the passenger");
                    int age = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter starting point of the journey");
                    String startingPoint = sc.nextLine();
                    System.out.println("Enter ending point of the journey");
                    String endingPoint = sc.nextLine();
                    System.out.println("Enter travel date in the format (yyyy-MM-dd)");
                    Date travelDate = Date.valueOf(sc.nextLine());
                    System.out.println("Do you want  to book a special seat? (true/false)");
                    boolean isSpecialSeatNeeded = sc.nextBoolean();
                    System.out.println("Do you want to book a women-only seat? (true/false)");
                    boolean isWomenOnlySeatNeeded = sc.nextBoolean();
                    sc.nextLine();
                    PassengerInfo passengerInfo = new PassengerInfo(passengerId,name,sex,age,startingPoint,endingPoint,travelDate,isSpecialSeatNeeded,isWomenOnlySeatNeeded);
                    List<BusMaster> busMasterList = reservationManager.suggestAlternatePlan(passengerInfo);
                    for(BusMaster busMaster:busMasterList) {
                        System.out.println(busMaster.returnBusMasterObject(busMaster));
                    }
                    break;
                }
                case 5:{
                    reservationManager.writeAllReservationsToFile();
                    break;
                }
                case 6:
                {
                    System.out.println("Exit");
                    break;
                }
                default: {
                    System.out.println("Valid Range is 1-6");
                    break;
                }
            }
            System.out.println("Do you wish to continue (yes/no)");
            reply = sc.nextLine();




        }
    }

}
