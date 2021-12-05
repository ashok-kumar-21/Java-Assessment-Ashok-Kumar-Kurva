package com.ford.assessment.utility;

import com.ford.assessment.dao.BusMgmtSystemDao;
import com.ford.assessment.model.BusDetails;
import com.ford.assessment.model.BusMaster;
import com.ford.assessment.model.PassengerInfo;
import com.ford.assessment.model.Reservation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationManagerTest {
    BusMgmtSystemDao busMgmtSystemDao;
    ReservationManager reservationManager;
    Reservation expectedCommonTicketReservation;
    Reservation expectedWomenOnlyTicketReservation;
    Reservation expectedSpecialSeatTicketReservation;
    PassengerInfo CommonTicketPassengerInfo;
    BusMaster CommonTicketBusMaster;
    BusDetails CommonTicketBusDetails;
    List<Reservation> expectedReservationList;
    List<BusMaster> expectedBusMasterList;

    @BeforeEach
    void setUp() throws ParseException {
        busMgmtSystemDao = new BusMgmtSystemDao();
        expectedReservationList = new ArrayList<>();
        expectedBusMasterList = new ArrayList<>();
        reservationManager = new ReservationManager();
        busMgmtSystemDao.createBusMasterTable();
        busMgmtSystemDao.createBusDetailsTable();
        reservationManager.loadBusMasterList();
        CommonTicketPassengerInfo = new PassengerInfo(23456,"Suresh Kumar",'M',25,"Chennai","Goa", Date.valueOf("2022-08-03"),false,false);
        CommonTicketBusDetails =new BusDetails("180A",42,34,4,4,33,4,4);
        CommonTicketBusMaster = new BusMaster("180A","Chennai","Goa",Date.valueOf("2022-08-03"),"6:00","920 minutes",26,"Normal",CommonTicketBusDetails);
        expectedCommonTicketReservation = new Reservation(1,CommonTicketPassengerInfo,CommonTicketBusMaster);
        expectedWomenOnlyTicketReservation = new Reservation(1,new PassengerInfo(23108,"Ritu Sharma",'F',35,"Madurai","Bangalore", Date.valueOf("2022-08-02"),false,true),new BusMaster("146R","Madurai","Bangalore",Date.valueOf("2022-08-02"),"14:00","600 minutes",19,"Normal",new BusDetails("146R",32,26,4,2,26,3,2)));
        expectedSpecialSeatTicketReservation = new Reservation(1,new PassengerInfo(23789,"Rishabh",'M',29,"Madurai","Bangalore", Date.valueOf("2022-08-02"),true,false),new BusMaster("146R","Madurai","Bangalore",Date.valueOf("2022-08-02"),"14:00","600 minutes",19,"Normal",new BusDetails("146R",32,26,4,2,26,3,1)));
        expectedReservationList.add(new Reservation(1,new PassengerInfo(23456,"Ravi Ashwin",'M',28,"Chennai","Bangalore", Date.valueOf("2022-08-01"),true,false),new BusMaster("122S","Chennai","Bangalore",Date.valueOf("2022-08-01"),"22:00","435 minutes",4,"SuperFast",new BusDetails("122S",32,26,4,2,26,4,1))));
        expectedBusMasterList.add(new BusMaster("146A","Chennai","Madurai",Date.valueOf("2022-08-02"),"20:30","525 minutes",13,"Normal",new BusDetails("146A",36,27,5,4,27,5,4)));
        expectedBusMasterList.add(new BusMaster("201A","Chennai","Trichy",Date.valueOf("2022-08-04"),"20:45","400 minutes",5,"Normal",new BusDetails("201A",40,35,5,0,35,5,0)));
        expectedBusMasterList.add(new BusMaster("201C","Trichy","Madurai",Date.valueOf("2022-08-05"),"4:00","160 minutes",3,"Normal",new BusDetails("201C",25,22,3,0,22,3,0)));
    }

    @AfterEach
    void tearDown() {
        reservationManager = null;
    }

    @Test
    public void shouldWriteBusMasterCsvDetailsToDb(){
        assertTrue(reservationManager.writeBusMasterCsvDetailsToDb());
    }

    @Test
    public void shouldWriteBusDetailsCsvDetailsToDb(){
        assertTrue(reservationManager.writeBusDetailsCsvDetailsToDb());
    }

//    Should call shouldWriteBusMasterCsvDetailsToDb() and shouldWriteBusDetailsCsvDetailsToDb in order to load the csv files as tables into database before running this testcase
    @Test
    public void shouldBookWomenOnlyTicket(){
        Reservation actualWomenOnlyTicketReservation = reservationManager.bookTicket(new PassengerInfo(23108,"Ritu Sharma",'F',35,"Madurai","Bangalore", Date.valueOf("2022-08-02"),false,true));
        assertEquals(expectedWomenOnlyTicketReservation,actualWomenOnlyTicketReservation);
    }

//     Should call shouldWriteBusMasterCsvDetailsToDb() and shouldWriteBusDetailsCsvDetailsToDb in order to load the csv files as tables into database before running this testcase
    @Test
    public void shouldBookSpecialTicket(){
        Reservation actualSpecialTicketReservation = reservationManager.bookTicket(new PassengerInfo(23789,"Rishabh",'M',29,"Madurai","Bangalore", Date.valueOf("2022-08-02"),true,false));
        assertEquals(expectedSpecialSeatTicketReservation,actualSpecialTicketReservation);
    }

//    Should call shouldWriteBusMasterCsvDetailsToDb() and shouldWriteBusDetailsCsvDetailsToDb in order to load the csv files as tables into database before running this testcase
    @Test
    public void shouldBookCommonSeatTicket(){
        Reservation actualCommonTicketReservation = reservationManager.bookTicket(new PassengerInfo(23456,"Suresh Kumar",'M',25,"Chennai","Goa", Date.valueOf("2022-08-03"),false,false));
        assertEquals(expectedCommonTicketReservation,actualCommonTicketReservation);
    }

//    Should call shouldWriteBusMasterCsvDetailsToDb() and shouldWriteBusDetailsCsvDetailsToDb in order to load the csv files as tables into database before running this testcase
    @Test
    public void shouldNotBookDueToLackOfSeat(){
        Reservation reservation = reservationManager.bookTicket(new PassengerInfo(23446,"Ramesh Kumar",'M',26,"Chennai","Trichy", Date.valueOf("2022-08-04"),true,false));
        assertEquals(null,reservation);
    }

//     Should call shouldWriteBusMasterCsvDetailsToDb() and shouldWriteBusDetailsCsvDetailsToDb in order to load the csv files as tables into database before running this testcase
    @Test
    public void shouldNotBookDueToStartingPointNotAvailable(){
        Reservation reservation = reservationManager.bookTicket(new PassengerInfo(23346,"Sukesh Hegde",'M',26,"Mumbai","Trichy", Date.valueOf("2022-08-04"),true,false));
        assertEquals(null,reservation);
    }

//    Should call shouldWriteBusMasterCsvDetailsToDb() and shouldWriteBusDetailsCsvDetailsToDb in order to load the csv files as tables into database before running this testcase
    @Test
    public void shouldCancelTicket(){
        Reservation actualCommonReservation = reservationManager.bookTicket(new PassengerInfo(23456,"Suresh Kumar",'M',25,"Chennai","Goa", Date.valueOf("2022-08-03"),false,false));
        boolean isTicketCancelled  = reservationManager.cancelTicket(actualCommonReservation);
        assertTrue(isTicketCancelled);
    }


//    Should call shouldWriteBusMasterCsvDetailsToDb() and shouldWriteBusDetailsCsvDetailsToDb in order to load the csv files as tables into database before running this testcase
    @Test
    public void shouldGetAllReservationListForParticularBus(){
        String busNo = "180A";
        List<Reservation> actualReservationList = new ArrayList<>();
        actualReservationList.add(reservationManager.bookTicket(new PassengerInfo(23456,"Ravi Ashwin",'M',28,"Chennai","Bangalore", Date.valueOf("2022-08-01"),true,false)));
        assertEquals(expectedReservationList,actualReservationList);
    }


//    Should call shouldWriteBusMasterCsvDetailsToDb() and shouldWriteBusDetailsCsvDetailsToDb in order to load the csv files as tables into database before running this testcase
    @Test
    public void shouldSuggestAlternatePlan(){
        List<BusMaster> actualBusMasterList = reservationManager.suggestAlternatePlan(new PassengerInfo(23678,"Kuldeep Kumar",'M',30,"Chennai","Madurai",Date.valueOf("2022-08-01"),false,false));;
        assertEquals(expectedBusMasterList,actualBusMasterList);

    }

//    Should call shouldWriteBusMasterCsvDetailsToDb() and shouldWriteBusDetailsCsvDetailsToDb in order to load the csv files as tables into database before running this testcase
    @Test
    public void shouldWriteAllReservationsToFile(){
        Reservation reservation = reservationManager.bookTicket(new PassengerInfo(23476,"Ramesh Singh",'M',26,"Chennai","Trichy", Date.valueOf("2022-08-04"),false,false));
        Reservation reservation1 = reservationManager.bookTicket(new PassengerInfo(23456,"Suresh Kumar",'M',25,"Chennai","Goa", Date.valueOf("2022-08-03"),false,false));
        assertTrue(reservationManager.writeAllReservationsToFile());
    }
}