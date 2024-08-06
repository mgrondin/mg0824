package org.ToolRentalPOS.db;

import java.sql.*;

public class PriceDAO {

    public Price getPriceByToolCode(String code)throws SQLException{
        Price foundPrice = null;

        String sql = "select p.ToolType, p.DailyChargeCents, p.WeekdayCharge, p.WeekendCharge, p.HolidayCharge " +
                "from Prices p " +
                "left join Tools t on t.ToolType = p.ToolType " +
                "where t.ToolCode = ? ";
        try (Connection conn = SQLliteConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, code);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                foundPrice = buildPrice(resultSet);
            }
        }
        return foundPrice;
    }

    private Price buildPrice(ResultSet resultSet)throws SQLException{
        Price newPrice = new Price();
        newPrice.setToolType(resultSet.getString("ToolType"));
        newPrice.setDailyChargeCents(resultSet.getInt("DailyChargeCents"));
        newPrice.setWeekdayCharge(resultSet.getBoolean("WeekdayCharge"));
        newPrice.setWeekendCharge(resultSet.getBoolean("WeekendCharge"));
        newPrice.setHolidayCharge(resultSet.getBoolean("HolidayCharge"));
        return newPrice;
    }
}
