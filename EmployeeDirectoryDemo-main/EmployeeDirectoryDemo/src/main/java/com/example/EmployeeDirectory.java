package com.example;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDirectory {
    private DataSource dataSource;

    public EmployeeDirectory() {
        try {
            Context ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/EmployeeDB");
        } catch (NamingException e) {
            System.out.println("JNDI Error: " + e.getMessage());
        }
    }

    public void addEmployee(Employee employee) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO employees (name, email, phone_number) VALUES (?, ?, ?)")) {
            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getEmail());
            stmt.setString(3, employee.getPhoneNumber());
            stmt.executeUpdate();
            System.out.println("Employee added: " + employee.getName());
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    public Employee searchEmployee(String name) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM employees WHERE name = ?")) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Employee(rs.getString("name"), rs.getString("email"), rs.getString("phone_number"));
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return null;
    }

    public void updateEmployee(Employee employee) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE employees SET email = ?, phone_number = ? WHERE name = ?")) {
            stmt.setString(1, employee.getEmail());
            stmt.setString(2, employee.getPhoneNumber());
            stmt.setString(3, employee.getName());
            stmt.executeUpdate();
            System.out.println("Employee updated: " + employee.getName());
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    public void deleteEmployee(String name) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM employees WHERE name = ?")) {
            stmt.setString(1, name);
            stmt.executeUpdate();
            System.out.println("Employee deleted: " + name);
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
}
