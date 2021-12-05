package com.ford.assessment.dao;

import com.ford.assessment.connections.ConnectionClass;
import com.ford.assessment.model.BusDetails;
import com.ford.assessment.model.BusMaster;
import com.ford.assessment.service.BusManagementService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BusMgmtSystemDao {
    Connection connection;
    Statement statement;
    PreparedStatement preparedStatement;
    ResultSet rs;
    ConnectionClass connectionClass;
    BusManagementService busManagementService;

    public BusMgmtSystemDao() {
        connectionClass = new ConnectionClass();
        connection = connectionClass.getMyConnection();
        busManagementService = new BusManagementService();
    }

    public boolean createBusMasterTable(){
        boolean flag = false;
        try{
            preparedStatement = connection.prepareStatement("Create Table  IF Not EXISTS BusMaster(" +
                    "BusNo varchar(20)," +
                    "SourceFrom varchar(20)," +
                    "DestinationTO varchar(20)," +
                    "StartingDate date," +
                    "StartingTime varchar(20)," +
                    "JourneyTime varchar(20)," +
                    "TotalStops int," +
                    "BusType varchar(20))");
            preparedStatement.execute();
            flag=true;
        } catch (SQLException throwables) {
            flag=false;
            throwables.printStackTrace();
        }
        return flag;
    }

    public boolean createBusDetailsTable(){
        boolean flag = false;
        try{
            preparedStatement = connection.prepareStatement("Create Table  IF Not EXISTS BusDetails(" +
                            "BusNo varchar(20)," +
                            "TotalSeats int," +
                            "TotalCommonSeats int," +
                            "TotalWomenOnlySeats int," +
                            "TotalSpecialSeats int," +
                            "AvailableCommonSeats int," +
                            "AvailableWomenSeats int," +
                            "AvailableSpecialSeats int)");
            preparedStatement.execute();
            flag=true;
        } catch (SQLException throwables) {
            flag=false;
            throwables.printStackTrace();
        }
        return flag;
    }

    public boolean insertBusMasterDetails(BusMaster busMaster) {
        boolean flag = false;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<BusMaster>> constraintViolations = validator.validate(busMaster);
        if (constraintViolations.size() > 0) {
            System.out.println(constraintViolations.iterator().next().getMessage());
            BusManagementService.writeBusMasterErrorToTextFile(constraintViolations.iterator().next().getMessage(), busMaster);
        }
        else {
            try {
                preparedStatement = connection.prepareStatement(" Insert into BusMaster values (?,?,?,?,?,?,?,?)");
                preparedStatement.setString(1, busMaster.getBusNo());
                preparedStatement.setString(2, busMaster.getFrom());
                preparedStatement.setString(3, busMaster.getTo());
                preparedStatement.setDate(4, BusManagementService.utilToSqlDateConverter(busMaster.getStartingDate()));
                preparedStatement.setString(5, busMaster.getStartingTime());
                preparedStatement.setString(6, busMaster.getJourneyTime());
                preparedStatement.setInt(7, busMaster.getTotalStops());
                preparedStatement.setString(8, busMaster.getTypeOfBus());
                preparedStatement.executeUpdate();
                flag = true;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return flag;
    }

    public boolean insertBusDetails(BusDetails busDetails) {
        boolean flag = false;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<BusDetails>> constraintViolations = validator.validate(busDetails);
        if (constraintViolations.size() > 0) {
            System.out.println(constraintViolations.iterator().next().getMessage());
            BusManagementService.writeBusDetailsErrorToTextFile(constraintViolations.iterator().next().getMessage(), busDetails);
        } else {
            try {
                preparedStatement = connection.prepareStatement(" Insert into BusDetails values (?,?,?,?,?,?,?,?)");
                preparedStatement.setString(1, busDetails.getBusNo());
                preparedStatement.setInt(2, busDetails.getTotalSeats());
                preparedStatement.setInt(3, busDetails.getTotalCommonSeats());
                preparedStatement.setInt(4, busDetails.getTotalWomenOnlySeats());
                preparedStatement.setInt(5, busDetails.getTotalSpecialSeats());
                preparedStatement.setInt(6, busDetails.getAvailableCommonSeats());
                preparedStatement.setInt(7, busDetails.getAvailableWomenSeats());
                preparedStatement.setInt(8, busDetails.getAvailableSpecialSeats());
                preparedStatement.executeUpdate();
                flag = true;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return flag;
    }

    public void updateWomenOnlySeats(BusDetails busDetails,String busNo){
        try {
            preparedStatement = connection.prepareStatement("update BusDetails set availablewomenseats = ? where busNo = ?");
            preparedStatement.setInt(1,busDetails.getAvailableWomenSeats());
            preparedStatement.setString(2,busNo);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateSpecialSeats(BusDetails busDetails, String busNo){
        try {
            preparedStatement = connection.prepareStatement("update BusDetails set  availablespecialseats = ? where busNo = ?");
            preparedStatement.setInt(1,busDetails.getAvailableSpecialSeats());
            preparedStatement.setString(2,busNo);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateCommonSeats(BusDetails busDetails, String busNo){
        try {
            preparedStatement = connection.prepareStatement("update BusDetails set availablecommonseats = ? where busNo = ?");
            preparedStatement.setInt(1,busDetails.getAvailableCommonSeats());
            preparedStatement.setString(2,busNo);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<BusMaster> retrieveBusMasterList(List<BusDetails> busDetailObjectsList){
        List<BusMaster> busMasterList = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("Select * from BusMaster");
            rs = preparedStatement.executeQuery();
            while(rs.next()){
                for(BusDetails b : busDetailObjectsList){
                    if(rs.getString(1).equalsIgnoreCase(b.getBusNo())){
                        BusMaster obj = new BusMaster(rs.getString(1),rs.getString(2),rs.getString(3),rs.getDate(4),rs.getString(5),rs.getString(6),rs.getInt(7),rs.getString(8),b);
                        busMasterList.add(obj);
                    }
                }
//                BusMaster obj = new BusMaster(rs.getString(1),rs.getString(2),rs.getString(3),rs.getDate(4),rs.getString(5),rs.getString(6),rs.getInt(7),rs.getString(8),null);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return busMasterList;
    }

    public List<BusDetails> retrieveBusDetailList(){
        List<BusDetails> busDetailList = new ArrayList<>();
        try{
            preparedStatement = connection.prepareStatement("Select * from BusDetails");
            rs = preparedStatement.executeQuery();
            while(rs.next()){
                BusDetails obj = new BusDetails(rs.getString(1),rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),rs.getInt(6),rs.getInt(7),rs.getInt(8));
                busDetailList.add(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return busDetailList;
    }
}
