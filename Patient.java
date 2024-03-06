package Hospitalmanagementsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner sc;
public Patient(Connection connection, Scanner sc)
{
    this.connection=connection;
    this.sc=sc;
}
public void addpatient(){
    System.out.println("Enter your name :");
    String name = sc.next();
    System.out.println("Enter your age");
    int age = sc.nextInt();
    System.out.println("Enter your gender");
    String gender = sc.next();

    try{
        String query = "INSERT INTO patients(name,age,gender)VALUES(?,?,?)";
        PreparedStatement preparedStatement=connection.prepareStatement(query);
        preparedStatement.setString(1,name);
        preparedStatement.setInt(2,age);
        preparedStatement.setString(3,gender);
        int affectedrows=preparedStatement.executeUpdate();
        if(affectedrows>0) {
            System.out.println(("Done"));
        }
        else {
            System.out.println("Failed");
        }



    }catch (SQLException e) {
        e.printStackTrace();
    }
    }

    public void viewpatient()
    {
        String query="SELECT * FROM patients";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            ResultSet resultSet=preparedStatement.executeQuery();
            System.out.println("Patients: ");
            System.out.println("+-----------+-----------+------+--------+");
            System.out.println("|Patient ID | Name      | AGE  | GENDER |");
            System.out.println("+-----------+-----------+------+--------+");
            while(resultSet.next())
            {
                int id = resultSet.getInt("id");
                String name=resultSet.getString("name");
                int age = resultSet.getInt("age");
                String  gender = resultSet.getString("gender");
                System.out.printf("|%-10s|%-11s|%-4s|%-6s|%n", id,name,age,gender);
                System.out.println("+-----------+-----------+------+--------+");


            }





        }catch (SQLException e){
            e.printStackTrace();

        }






    }

    public boolean checkpatient(int id)
    {
        String query = "Select * FROM patients WHERE id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()) {
                return true;
            }
            else {
                return false;
            }
        }catch (SQLException e){
                e.printStackTrace();
            }
        return false;
        }

    }
