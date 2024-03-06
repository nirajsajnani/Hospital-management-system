package Hospitalmanagementsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
    private Connection connection;
    private Scanner sc;


    public Doctor(Connection connection,Scanner sc)
    {
        this.connection=connection;
        this.sc=sc;
    }

    public void Viewdoctors()
    {
        String query = "SELECT * FROM doctors";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet=preparedStatement.executeQuery();
            System.out.println("+--------+--------------+-----+");
            System.out.println("|doctor id|specialization|name|");
            System.out.println("+--------+--------------+-----+");
            while(resultSet.next())
            {
                int id = resultSet.getInt("id");
                String specialization= resultSet.getString("specialization");
                String name = resultSet.getString("name");
                System.out.printf("|%-8s|%-13s|%-6s|%n",id,specialization,name);
                System.out.println("+--------+--------------+-----+");
            }

        }catch (SQLException e){
            e.printStackTrace();

        }
    }

    public boolean getdoctor(int id)
    {
        String query ="SELECT * FROM doctors WHERE id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet= preparedStatement.executeQuery();
            while(resultSet.next())
            {
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();

        }
        return false;
    }

}
