package Hospitalmanagementsystem;

import java.sql.*;
import java.util.Scanner;

public class Hospitalm {
    private static final String url="jdbc:mysql://localhost:3306/hospital";
    private static final String username="root";
    private static final String password="Niraj17!";

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        Scanner sc = new Scanner(System.in);

        try{
            Connection connection= DriverManager.getConnection(url,username,password);
            Patient p = new Patient(connection,sc);
            Doctor d = new Doctor(connection,sc);
            while (true)
            {
                System.out.println("1.ADD PATIENT");
                System.out.println("2.VIEW PATIENT");
                System.out.println("3.VIEW DOCTORS");
                System.out.println("4.BOOK APPOINTMENT");
                System.out.println("5.EXIT");
                System.out.println("Enter your choice");
                int ch = sc.nextInt();
                switch (ch)
                {
                    case 1:
                        p.addpatient();
                        break;
                    case 2:
                        p.viewpatient();
                        break;
                    case 3:
                        d.Viewdoctors();
                        break;
                    case 4:
                        appointment(p,d,connection,sc);
                        break;
                    case 5:
                        //exit
                        return;
                    default:
                        System.out.println("Enter valid choice");
                    }

            }


        }catch(SQLException e){
            e.printStackTrace();

        }
    }


    public static void appointment(Patient p, Doctor d,Connection connection,Scanner sc)
    {
        System.out.print("Enter patient id");
        int pid = sc.nextInt();
        System.out.print("Enter doctor id");
        int did=sc.nextInt();
        System.out.println("Enter appointment date(YYYY-MM-DD)");
        String ad = sc.next();
        if(p.checkpatient(pid)&&(d.getdoctor(did)))
        {
            if(isdavailaible(did,ad,connection))
            {
                String aquery="INSERT INTO appointment(patient_id,doctor_id,appointment_date)Values(?,?,?)";
                        try{
                            PreparedStatement preparedStatement=connection.prepareStatement(aquery);
                            preparedStatement.setInt(1,pid);
                            preparedStatement.setInt(2,did);
                            preparedStatement.setString(3,ad);
                            int rowsaffected=preparedStatement.executeUpdate();
                            if(rowsaffected>0)
                            {
                                System.out.println("Appointment booked");
                            }


                        }catch (SQLException e){
                            e.printStackTrace();

                        }

            }
            else {
                System.out.println("Doctor not availaible on this date");
            }
            

        }
        else{
            System.out.println("Either patient or doctor does not exist ");
        }


    }

    public static  boolean isdavailaible(int doctorid,String appointmentdate,Connection connection)
    {
        String query="SELECT COUNT(*) FROM APPOINTMENT WHERE doctor_id=? AND appointment_date=?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,doctorid);
            preparedStatement.setString(2,appointmentdate);
            ResultSet resultSet= preparedStatement.executeQuery();
            if(resultSet.next())
            {
                int count=resultSet.getInt(1);
                if(count==0) {
                    return true;
                }

            }

        }catch (SQLException e){
            e.printStackTrace();

        }
        return false;
    }
}
