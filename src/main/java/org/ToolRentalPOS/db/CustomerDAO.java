package org.ToolRentalPOS.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//I decided to add a Customer entity, table, and DAO so that RentalAgreements would have
//a record of who the tool is checked out to.
public class CustomerDAO {

    public Customer findCustomerByEmail(String email) throws SQLException {
        String sql = "select c.CustomerID, c.FirstName, c.LastName ,c.Email " +
                "from Customers c where email = ?";

        Customer foundCustomer = null;
        try (Connection conn = SQLliteConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                foundCustomer = buildCustomer(resultSet);
            }
        }
        return foundCustomer;
    }

    private Customer buildCustomer(ResultSet resultSet) throws SQLException{
        Customer customer = new Customer();
        customer.setCustomerID(resultSet.getInt("CustomerID"));
        customer.setFirstName(resultSet.getString("FirstName"));
        customer.setLastName(resultSet.getString("LastName"));
        customer.setEmail(resultSet.getString("Email"));
        return customer;
    }
}
