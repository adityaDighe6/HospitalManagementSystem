package hospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.cj.exceptions.RSAException;

//Driver Class for the project.
public class HospitalManagementSystem {

	//Step 2 : Add url, user and password.
	private static final String url = "jdbc:mysql://localhost:3306/hospital_db";
	private static final String user = "root";
	private static final String password = "aditya";
	//End
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Step 1 : Load the drivers.
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Drivers loaded successfully...");
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		//End
		Scanner sc = new Scanner(System.in);
		//Step 3 : Establish a connection.
		try {
			Connection con = DriverManager.getConnection(url, user, password);
			
			Patients patients = new Patients(con, sc);
			Doctors doctors = new Doctors(con);
			
			System.out.println("HOSPITAL MANAGEMENT SYSTEM ");
            System.out.println("1. Add Patient");
            System.out.println("2. View Patients");
            System.out.println("3. View Doctors");
            System.out.println("4. Book Appointment");
            System.out.println("5. Exit");
            System.out.println("Enter your choice: ");
            int choice = sc.nextInt();
            
            switch(choice) {
            case 1 : patients.addPatients();
            		 System.out.println(); 
            		 break;
            
            case 2 : patients.viewPatients();
            		 System.out.println();
            		 break;
            		 
            case 3 : doctors.viewDoctors();
            		 System.out.println();
            		 break;
            		 
            case 4 : bookAppointment(patients, doctors, con, sc);
            		 break;
            
            case 5 : System.out.println("THANK YOU! FOR USING HOSPITAL MANAGEMENT SYSTEM!!");
            		 return;
            	     
            default : 
            	      System.out.println("Invalid choice ...");
            	      break;
            }
            con.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		//End
	}
	
	//Method for booking appointment
	public static void bookAppointment(Patients patients, Doctors doctors, Connection con, Scanner sc) {
		System.out.print("Enter Patient Id: ");
        int patientId = sc.nextInt();
        System.out.print("Enter Doctor Id: ");
        int doctorId = sc.nextInt();
        System.out.print("Enter appointment date (YYYY-MM-DD): ");
        String appointmentDate = sc.next();
        
        	if(patients.getPatientById(patientId) && doctors.getDoctorById(doctorId)) {
        		if(checkAvailabilityOfDoctor(doctorId, appointmentDate, con)) {
        			String query = "INSERT INTO appointments (patient_id,doctor_id,appointment_date) VALUES (?, ?, ?) ";
        			
        			try {
        				PreparedStatement preparedStatement = con.prepareStatement(query);
        				
        				preparedStatement.setInt(1, patientId);
        				preparedStatement.setInt(2,patientId);
        				preparedStatement.setString(3, appointmentDate);
        				
        				int noOfRowsAffected = preparedStatement.executeUpdate();
        				
        				if(noOfRowsAffected > 0) {
        					System.out.println("Appointment booked...");
        				}
        				else {
        					System.out.println("Appointment not booked...");
        				}
        			}
        			catch(SQLException e) {
        				e.printStackTrace();
        			}
        	}
        	else {
        		 System.out.println("Doctor not available on this date!!");
        	}
        }
        else {
        	System.out.println("doctor or patient doesn't exist!!!"); 
        }
	}
	//End
	//Method for checking availability of doctor.
	public static boolean checkAvailabilityOfDoctor(int doctor_id, String appointment_date, Connection con) {
		
		String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
		
		try {
			PreparedStatement preparedStatement = con.prepareStatement(query);
			
			preparedStatement.setInt(1, doctor_id);
			preparedStatement.setString(2, appointment_date);
			
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()) {
				int count = rs.getInt(1);
				if(count == 0) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;	
	} 
	//End
}
