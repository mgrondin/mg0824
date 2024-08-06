package org.ToolRentalPOS.db;

public class Price {
    private String ToolType;
    private int DailyChargeCents; //Note: We should avoid floats and doubles due to the possibility of rounding errors
    private boolean WeekdayCharge;
    private boolean WeekendCharge;
    private boolean HolidayCharge;

    public String getToolType() {
        return ToolType;
    }

    public void setToolType(String toolType) {
        ToolType = toolType;
    }

    public int getDailyChargeCents() {
        return DailyChargeCents;
    }

    public void setDailyChargeCents(int dailyChargeCents) {
        DailyChargeCents = dailyChargeCents;
    }

    public boolean isWeekdayCharge() {
        return WeekdayCharge;
    }

    public void setWeekdayCharge(boolean weekdayCharge) {
        WeekdayCharge = weekdayCharge;
    }

    public boolean isWeekendCharge() {
        return WeekendCharge;
    }

    public void setWeekendCharge(boolean weekendCharge) {
        WeekendCharge = weekendCharge;
    }

    public boolean isHolidayCharge() {
        return HolidayCharge;
    }

    public void setHolidayCharge(boolean holidayCharge) {
        HolidayCharge = holidayCharge;
    }

    @Override
    public String toString() {
        return "Price{" +
                "ToolType='" + ToolType + '\'' +
                ", DailyChargeCents=" + DailyChargeCents +
                ", WeekdayCharge=" + WeekdayCharge +
                ", WeekendCharge=" + WeekendCharge +
                ", HolidayCharge=" + HolidayCharge +
                '}';
    }
}
