package org.ToolRentalPOS.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RentalAgreementDAO {


    public void createRentalAgreementRecord(RentalAgreement agreement) throws SQLException{
        String sql = "INSERT into RentalAgreements(" +
                "CustomerID," +
                "ToolCode," +
                "RentalDays," +
                "CheckOutDate," +
                "DueDate," +
                "DailyChargeCents," +
                "ChargeDays," +
                "PreDiscountChargeCents," +
                "DiscountPercent," +
                "FinalChargeCents)" +
            "values(?,?,?,?,?,?,?,?,?,?)";

        try (Connection conn = SQLliteConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            //Skip RentalAgreements as it is AUTOINCREMENT
            stmt.setInt(1,agreement.getCustomer().getCustomerID());
            stmt.setString(2,agreement.getTool().getToolCode());//ToolCode
            stmt.setInt(3,agreement.getRentalDays()); //RentalDays
            stmt.setDate(4,agreement.getCheckOutDate()); //CheckOutDate
            stmt.setDate(5,agreement.getDueDate()); //DueDate
            stmt.setInt(6,agreement.getDailyChargeCents()); //DailyChargeCents
            stmt.setInt(7,agreement.getChargeDays()); //ChargeDays
            stmt.setInt(8,agreement.getPreDiscountChargeCents());//PreDiscountChargeCents
            stmt.setInt(9,agreement.getDiscountPercent());//DiscountPercent
            stmt.setInt(10,agreement.getFinalChargeCents());//FinalChargeCents

            stmt.executeUpdate();
        }
    }
}
