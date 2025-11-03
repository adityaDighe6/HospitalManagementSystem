package hospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patients {
	
	private Connection con;
	private Scanner sc;
	
	public Patients(Connection con, Scanner sc){
		this.con = con;
		this.sc = sc;
	}
	//Method to add patient.
	public void addPatients() {
		System.out.println("Enter Patient's Name : ");
		String name = sc.next();
		
		System.out.println("Enter Patient's Age :");
		int age = sc.nextInt();
		
		System.out.println("Enter Patient's Gender : ");
		String gender = sc.next();
		
		try {
			String query = "INSERT INTO patients (name,age,gender) VALUES (?, ?, ?)";
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, age);
			preparedStatement.setString(3, gender);
			
			int noOfRowsAffected = preparedStatement.executeUpdate();
			
			if(noOfRowsAffected > 0) {
				System.out.println("Patient added successfully");
			}
			else {
				System.out.println("Patient not added...");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	//End
	
	//Method to view patients.
	public void viewPatients() {
		String query = "SELECT * FROM patients";
		
		try {
			PreparedStatement preparedStatement = con.prepareStatement(query);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				String gender = rs.getString("gender");
				
				System.out.println("Patient's Id is : "+id);
				System.out.println("Patient's Name is "+name);
				System.out.println("Patient's Age is "+age);
				System.out.println("Patient's Gender is "+gender);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	//End
	
	//Method to find patients by ID.
	public boolean getPatientById(int id) {
		String query = "SELECT * FROM patients WHERE id = ?";
		
		try {
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, id);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			if(rs.next()) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	//End
}
