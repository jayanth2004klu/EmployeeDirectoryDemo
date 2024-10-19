package com.example;

public class Main {
    public static void main(String[] args) {
        EmployeeDirectory directory = new EmployeeDirectory();

        Employee employee = new Employee("John Doe", "johndoe@example.com", "123-456-7890");
        directory.addEmployee(employee);

        Employee foundEmployee = directory.searchEmployee("John Doe");
        System.out.println("Employee found: " + foundEmployee.getName());

        employee.setEmail("johndoe2@example.com");
        directory.updateEmployee(employee);

        directory.deleteEmployee("John Doe");
    }
}
