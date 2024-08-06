package org.ToolRentalPOS;

import org.ToolRentalPOS.db.*;
import org.junit.Test;


import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.Date;

import static org.junit.Assert.*;

public class CreateRentalAgreementTests {

    //NOTE: Make sure you run the dbClean.sql script before running the endToEndTest as it need the dummy data.
    //The first test is an End to End happy test.
    //It reads existing data from the database to find an existing customer, existing tool by tool code, and it's price
    //Then it creates a CheckOutDTO (Data transfer object).  Using this to hold all the parameters opens up the possibility
    //of pairing this service with a front end UI or a REST endpoint.

    @Test
    public void endToEndTest(){
        ToolDAO toolDAO = new ToolDAO();
        PriceDAO priceDAO = new PriceDAO(); //It would also be possible to create a ToolPrice class that holds the joined tables
        CustomerDAO customerDAO = new CustomerDAO();

        try{
            CheckOutDTO dto = new CheckOutDTO();

            Tool tool = toolDAO.getToolByToolCode("JAKR"); //Fetch a tool by code from the DB
            dto.setTool(tool);

            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
            dto.setCheckOutDate( new Date( format.parse("09/03/15").getTime() ) );
            dto.setRentalDayCount(5);
            dto.setDiscountPercent(12);

            Price price = priceDAO.getPriceByToolCode(tool.getToolCode());
            dto.setPrice(price);

            Customer customer = customerDAO.findCustomerByEmail("jon.doe@likesTools.com");
            dto.setCustomer(customer);

            RentalAgreement rentalAgreement = CheckOut.generateRentalAgreement(dto);
            //When this line executes, expect to see the Rental Agreement printed to std.out

            //Now validate the existence of the rental agreement in the DB
            //I ran short on time before I could create the method in the RentalAgreementDAO for fetching this record,
            //But this can be checked manually
        } catch (Exception e){
            fail("An exception was thrown by CheckOut.generateRentalAgreement(dto) " + e.getMessage() );
        }
    }

    //The following tests are tests specified in the design document
    @Test(expected = CheckOutException.class)
    public void Test1() throws CheckOutException {
        ToolDAO toolDAO = new ToolDAO();
        PriceDAO priceDAO = new PriceDAO();
        CustomerDAO customerDAO = new CustomerDAO();

        try {
            CheckOutDTO dto = new CheckOutDTO();

            Tool tool = toolDAO.getToolByToolCode("JAKR");
            dto.setTool(tool);

            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
            dto.setCheckOutDate(new Date(format.parse("09/03/15").getTime()));
            dto.setRentalDayCount(5);
            dto.setDiscountPercent(101);

            Price price = priceDAO.getPriceByToolCode(tool.getToolCode());
            dto.setPrice(price);

            Customer customer = customerDAO.findCustomerByEmail("jon.doe@likesTools.com");
            dto.setCustomer(customer);

            RentalAgreement rentalAgreement = CheckOut.generateRentalAgreement(dto);
        } catch (ParseException | SQLException e){ //Catch and fail unexpected exceptions
            fail("An exception was thrown by CheckOut.generateRentalAgreement(dto) " + e.getMessage() );
        }
    }

    @Test
    public void Test2() {
        ToolDAO toolDAO = new ToolDAO();
        PriceDAO priceDAO = new PriceDAO(); //It would also be possible to create a ToolPrice class that holds the joined tables
        CustomerDAO customerDAO = new CustomerDAO();

        try {
            CheckOutDTO dto = new CheckOutDTO();

            Tool tool = toolDAO.getToolByToolCode("LADW"); //Fetch a tool by code from the DB
            dto.setTool(tool);

            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
            dto.setCheckOutDate(new Date(format.parse("07/02/20").getTime()));
            dto.setRentalDayCount(3);
            dto.setDiscountPercent(10);

            Price price = priceDAO.getPriceByToolCode(tool.getToolCode());
            dto.setPrice(price);

            Customer customer = customerDAO.findCustomerByEmail("jon.doe@likesTools.com");
            dto.setCustomer(customer);

            RentalAgreement rentalAgreement = CheckOut.generateRentalAgreement(dto);
        } catch (Exception e){ //Catch an fail unexpected exceptions
            fail("An exception was thrown by CheckOut.generateRentalAgreement(dto) " + e.getMessage() );
        }
    }

    @Test
    public void Test3() {
        ToolDAO toolDAO = new ToolDAO();
        PriceDAO priceDAO = new PriceDAO(); //It would also be possible to create a ToolPrice class that holds the joined tables
        CustomerDAO customerDAO = new CustomerDAO();

        try {
            CheckOutDTO dto = new CheckOutDTO();

            Tool tool = toolDAO.getToolByToolCode("CHNS"); //Fetch a tool by code from the DB
            dto.setTool(tool);

            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
            dto.setCheckOutDate(new Date(format.parse("07/02/15").getTime()));
            dto.setRentalDayCount(5);
            dto.setDiscountPercent(25);

            Price price = priceDAO.getPriceByToolCode(tool.getToolCode());
            dto.setPrice(price);

            Customer customer = customerDAO.findCustomerByEmail("jon.doe@likesTools.com");
            dto.setCustomer(customer);

            RentalAgreement rentalAgreement = CheckOut.generateRentalAgreement(dto);
        } catch (Exception e){ //Catch an fail unexpected exceptions
            fail("An exception was thrown by CheckOut.generateRentalAgreement(dto) " + e.getMessage() );
        }
    }

    @Test
    public void Test4() {
        ToolDAO toolDAO = new ToolDAO();
        PriceDAO priceDAO = new PriceDAO(); //It would also be possible to create a ToolPrice class that holds the joined tables
        CustomerDAO customerDAO = new CustomerDAO();

        try {
            CheckOutDTO dto = new CheckOutDTO();

            Tool tool = toolDAO.getToolByToolCode("JAKD"); //Fetch a tool by code from the DB
            dto.setTool(tool);

            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
            dto.setCheckOutDate(new Date(format.parse("09/03/15").getTime()));
            dto.setRentalDayCount(6);
            dto.setDiscountPercent(0);

            Price price = priceDAO.getPriceByToolCode(tool.getToolCode());
            dto.setPrice(price);

            Customer customer = customerDAO.findCustomerByEmail("jon.doe@likesTools.com");
            dto.setCustomer(customer);

            RentalAgreement rentalAgreement = CheckOut.generateRentalAgreement(dto);
        } catch (Exception e){ //Catch an fail unexpected exceptions
            fail("An exception was thrown by CheckOut.generateRentalAgreement(dto) " + e.getMessage() );
        }
    }

    @Test
    public void Test5() {
        ToolDAO toolDAO = new ToolDAO();
        PriceDAO priceDAO = new PriceDAO(); //It would also be possible to create a ToolPrice class that holds the joined tables
        CustomerDAO customerDAO = new CustomerDAO();

        try {
            CheckOutDTO dto = new CheckOutDTO();

            Tool tool = toolDAO.getToolByToolCode("JAKR"); //Fetch a tool by code from the DB
            dto.setTool(tool);

            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
            dto.setCheckOutDate(new Date(format.parse("07/02/15").getTime()));
            dto.setRentalDayCount(9);
            dto.setDiscountPercent(0);

            Price price = priceDAO.getPriceByToolCode(tool.getToolCode());
            dto.setPrice(price);

            Customer customer = customerDAO.findCustomerByEmail("jon.doe@likesTools.com");
            dto.setCustomer(customer);

            RentalAgreement rentalAgreement = CheckOut.generateRentalAgreement(dto);
        } catch (Exception e){ //Catch an fail unexpected exceptions
            fail("An exception was thrown by CheckOut.generateRentalAgreement(dto) " + e.getMessage() );
        }
    }

    @Test
    public void Test6() {
        ToolDAO toolDAO = new ToolDAO();
        PriceDAO priceDAO = new PriceDAO(); //It would also be possible to create a ToolPrice class that holds the joined tables
        CustomerDAO customerDAO = new CustomerDAO();

        try {
            CheckOutDTO dto = new CheckOutDTO();

            Tool tool = toolDAO.getToolByToolCode("JAKR"); //Fetch a tool by code from the DB
            dto.setTool(tool);

            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
            dto.setCheckOutDate(new Date(format.parse("07/02/20").getTime()));
            dto.setRentalDayCount(4);
            dto.setDiscountPercent(50);

            Price price = priceDAO.getPriceByToolCode(tool.getToolCode());
            dto.setPrice(price);

            Customer customer = customerDAO.findCustomerByEmail("jon.doe@likesTools.com");
            dto.setCustomer(customer);

            RentalAgreement rentalAgreement = CheckOut.generateRentalAgreement(dto);
        } catch (Exception e){ //Catch an fail unexpected exceptions
            fail("An exception was thrown by CheckOut.generateRentalAgreement(dto) " + e.getMessage() );
        }
    }



    //Below are additional tests

    @Test
    public void getToolByToolCodeTest(){
        ToolDAO toolDAO = new ToolDAO();
        try{
            Tool foundTool = toolDAO.getToolByToolCode("JAKR");
            assertNotNull("JAKR not found by tool code!",foundTool);
            assertEquals(foundTool.getBrand(),"Ridgid");
        } catch (Exception e){
            fail("An exception was thrown by getToolByToolCode() " + e.getMessage() );
        }
    }

    @Test
    public void getPriceByToolCodeTest(){
        PriceDAO priceDAO = new PriceDAO();
        try{
            Price foundPrice = priceDAO.getPriceByToolCode("JAKR");
            assertNotNull("JAKR price not found by tool code!",foundPrice);
            assertEquals(foundPrice.getDailyChargeCents(),299);
        } catch (Exception e){
            fail("An exception was thrown by getToolByToolCode() " + e.getMessage() );
        }
    }

    @Test
    public void countChargeableDaysTest1(){
        Price price = new Price();
        price.setDailyChargeCents(299);
        price.setWeekdayCharge(true);
        price.setWeekendCharge(false);
        price.setHolidayCharge(false);

        CheckOutDTO dto = new CheckOutDTO();


        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2024);
        calendar.set(Calendar.MONTH, Calendar.JULY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        // Get a Date object
        Date date = new Date(calendar.getTimeInMillis());
        dto.setCheckOutDate(date);
        dto.setRentalDayCount(7);

        int chargeDays = CheckOut.countChargeableDays(price,dto);
        assertEquals(chargeDays, 4);
    }

    @Test
    public void countChargeableDaysTest2(){
        Price price = new Price();
        price.setDailyChargeCents(299);
        price.setWeekdayCharge(true);
        price.setWeekendCharge(false);
        price.setHolidayCharge(false);

        CheckOutDTO dto = new CheckOutDTO();
        Date coDate = new Date(System.currentTimeMillis());

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2024);
        calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        // Get a Date object
        Date date = new Date(calendar.getTimeInMillis());
        dto.setCheckOutDate(date);
        dto.setRentalDayCount(7);

        int chargeDays = CheckOut.countChargeableDays(price,dto);
        assertEquals(chargeDays, 4);
    }

}


