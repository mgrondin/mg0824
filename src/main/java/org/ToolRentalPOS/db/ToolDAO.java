package org.ToolRentalPOS.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ToolDAO {

    //This method provides an example of a "try/finally" setup that allows
    //  for closing of resources even after an exception is first thrown.
    //  A more modern way of doing this is "try with resources" (Java7).  You can find
    //  an example of that in the RentalAgreementDAO.createRentalAgreementRecord() method
    public Tool getToolByToolCode(String toolCode) throws SQLException{
        Tool foundTool = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            String sql = "select t.ToolCode, t.ToolType, t.Brand, t.available " +
                    "from Tools t where ToolCode = ? ";

            conn = SQLliteConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, toolCode);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                foundTool = buildTool(resultSet);
            }
        } finally {
            if (conn != null) conn.close();
            if (stmt != null) stmt.close();
        }

        return foundTool;
    }

    private Tool buildTool(ResultSet resultSet)throws SQLException{
        Tool newTool = new Tool();
        newTool.setToolCode(resultSet.getString("ToolCode"));
        newTool.setToolType(resultSet.getString("ToolType"));
        newTool.setBrand(resultSet.getString("Brand"));
        newTool.setAvailable(resultSet.getInt("available"));
        return newTool;
    }


}
