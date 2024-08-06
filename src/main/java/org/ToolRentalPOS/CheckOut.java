package org.ToolRentalPOS;

import org.ToolRentalPOS.db.*;

import java.sql.SQLException;
import java.util.Calendar;
import java.sql.Date;

public class CheckOut {

    private static PriceDAO priceDAO = new PriceDAO();
    private static ToolDAO toolDAO = new ToolDAO();
    private static RentalAgreementDAO rentalAgreementDAO = new RentalAgreementDAO();

    public static RentalAgreement generateRentalAgreement(CheckOutDTO dto) throws CheckOutException{
        StringBuilder errorMessageSB = new StringBuilder();
        RentalAgreement agreement = null;
        if(validateInput(dto,errorMessageSB)){
            if(!checkAvailability(dto.getTool().getToolCode())){
                throw new CheckOutException("There are no tools with code ("+dto.getTool().getToolCode()+") available.");
            }
            agreement = compileAgreement(dto);
            //At this point we would probably like to confirm with the customer that the agreement is correct/acceptable
            // before saving it to the DB, but for the sake of simplicity, we will do it all at once.
            commitAgreement(agreement);
            printAgreement(agreement);
        } else {
            throw new CheckOutException(errorMessageSB.toString());
        }
        return agreement;
    }

    private static RentalAgreement compileAgreement(CheckOutDTO dto) throws CheckOutException{

        Price dailyPrice = getPrice(dto.getTool().getToolCode());
        Tool tool = getTool(dto.getTool().getToolCode());
        int chargeDays = countChargeableDays(dailyPrice, dto);

        RentalAgreement ra = new RentalAgreement();

        ra.setCustomer(dto.getCustomer());
        ra.setTool(tool);
        ra.setRentalDays(dto.getRentalDayCount());
        ra.setCheckOutDate(dto.getCheckOutDate());
        ra.setDueDate(calculateDueDate(dto));// Calculate from checkout date and rental days
        ra.setChargeDays(chargeDays);
        ra.setDailyChargeCents(dto.getPrice().getDailyChargeCents());
        ra.setPreDiscountChargeCents(chargeDays * ra.getDailyChargeCents());// calc as chargeDays * dailyCharge
        ra.setDiscountPercent(dto.getDiscountPercent());
        int finalCharge = (int)(ra.getPreDiscountChargeCents() - (ra.getPreDiscountChargeCents() * (dto.getDiscountPercent()/100.0)));
        ra.setFinalChargeCents(finalCharge);//Calc as pre-discount charge - discount amount

        return ra;
    }

    private static Date calculateDueDate(CheckOutDTO dto){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dto.getCheckOutDate());
        calendar.add(Calendar.DATE,dto.getRentalDayCount());
        return new Date(calendar.getTimeInMillis());
    }

    private static void commitAgreement(RentalAgreement agreement) throws CheckOutException {
        try{
            rentalAgreementDAO.createRentalAgreementRecord(agreement);
        } catch (SQLException sqlE){
            throw new CheckOutException("Checkout was unable to commit the agreement in the database.",sqlE);
        }
    }

    protected static int countChargeableDays(Price price, CheckOutDTO dto){
        //Based on the description of the problem, and hinted by the test data, I have created an algorithm
        //  to count the number of chargeable days in the rental period by simply traversing the days.
        //Starting at the start date, advance one day to get to "day after checkout".  Then
        //For each day:
        //  Check if chargeable on weekdays,
        //      then determine if the current day is a weekday or not.
        //          If the day is chargeable, count++ and break
        //  same with weekends,
        //  same with holidays
        //Counting this way is simple, but may not be the most efficient way to count the days.
        //However, the number of days is typically small
        //Also, we take care to order our conditionals such that the most common are triggered first, and the most complex checks are last
        //We will assume that if a holiday lands on a day that would not normally be charged, we do not add a charge for the holiday.
        //  The exercise only contains two holidays, both of which are always observed on weekdays.  This has been written to work
        //  even if we introduce a new holiday that can fall on weekends.
        //  Example: If a customer rents a tool that is free on weekends, but gets charged on holidays (chainsaw),
        //      and the new holiday falls on a weekend, I assume we would not add a charge for the holiday weekend.
        //

        //See truth table
        //|----Tool Pricing Config-------| Charge if holiday falls on..
        //|Week Day | Week End | Holiday |   Week Day | Week End
        //|    1    |    0     |    0    |     0      |     0
        //|    1    |    0     |    1    |     1      |     0      <--(Chainsaw example, no charge if holiday lands on weekend)
        //|    1    |    1     |    0    |     0      |     0
        //|    1    |    1     |    1    |     1      |     1
        //|    0    |    1     |    1    |     0      |     1

        Calendar rentalDay = Calendar.getInstance();
        rentalDay.setTime(dto.getCheckOutDate());
        int chargeDays = 0;
        for(int i = 0; i<dto.getRentalDayCount(); i++){
            rentalDay.add(Calendar.DATE,1);  //Advance one day (we dont charge the day of checkout)

            if(price.isWeekdayCharge() && isWeekday(rentalDay)){
                if(price.isHolidayCharge() && isHoliday(rentalDay)){
                    chargeDays++;
                } else if(!price.isHolidayCharge() && !isHoliday(rentalDay)){
                    chargeDays++;
                }
            } else if (price.isWeekendCharge() && !isWeekday(rentalDay)) {
                if(price.isHolidayCharge() && isHoliday(rentalDay)){
                    chargeDays++;
                } else if(!price.isHolidayCharge() && !isHoliday(rentalDay)){
                    chargeDays++;
                }
            }
        }
        return chargeDays;
    }

    private static boolean isWeekday(Calendar cal){
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        return (dayOfWeek >= Calendar.MONDAY && dayOfWeek <= Calendar.FRIDAY);
    }

    private static boolean isHoliday(Calendar cal){
        //We only consider two holidays: Independence Day - July 4th, and Labor Day - First Monday in September.
        //If the 4th is on a weekend, it is observed on the nearest weekday.
        //We will write a method for each holiday.  This scheme would require a software update to add a holiday.
        if(checkLaborDay(cal))
            return true;
        if(checkIndependenceDay(cal))
            return true;

        return false;
    }

    private static boolean checkLaborDay(Calendar cal){
        if (cal.get(Calendar.MONTH) != Calendar.SEPTEMBER) return false;
        if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) return false;
        //Now we know it's a monday in December.  If it's the first monday, the date will be less than 8
        if( cal.get(Calendar.DATE) > 7) return false;
        return true;
    }

    private static boolean checkIndependenceDay(Calendar cal){
        //For independence day, we need to check if it is July 4th, or Jul3 and Friday, or is it july 5 and Monday

        if(cal.get(Calendar.MONTH) != Calendar.JULY) return false;
        if(cal.get(Calendar.DATE) == 4) return true;
        if((cal.get(Calendar.DATE) == 3) && (cal.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY)) return true;
        if((cal.get(Calendar.DATE) == 5) && (cal.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY)) return true;
        return false;
    }

    private static Tool getTool(String toolCode) throws CheckOutException {
        try{
            return toolDAO.getToolByToolCode(toolCode);
        } catch (SQLException sqlE){
            throw new CheckOutException("Checkout was unable to find the ToolCode ("+toolCode+") in the database.",sqlE);
        }
    }

    private static boolean checkAvailability(String toolCode) throws CheckOutException{
        return (getTool(toolCode).getAvailable()>0);
    }

    private static Price getPrice(String toolCode)throws CheckOutException{
        try{
            return priceDAO.getPriceByToolCode(toolCode);
        } catch (SQLException sqlE){
            throw new CheckOutException("Checkout was unable to find the ToolCode ("+toolCode+") in the database.",sqlE);
        }
    }

    private static boolean validateInput(CheckOutDTO dto, StringBuilder errorMessageSB) {
        boolean pass = true;
        errorMessageSB.append("There was one or more validation errors when trying to create the rental agreement:" + System.lineSeparator());
        if(dto.getRentalDayCount() <= 0) {
            errorMessageSB.append("ERROR: Rental Day count must be 1 or more." + System.lineSeparator());
            pass = false;
        }
        if(dto.getDiscountPercent()<0 || dto.getDiscountPercent()>100){
            errorMessageSB.append("ERROR: Discount must be entered as a percentage between 0 and 100." + System.lineSeparator());
            pass = false;
        }
        return pass;
    }

    private static void printAgreement(RentalAgreement agreement ){
        System.out.println(agreement.toStringAsReport());
    }

}
