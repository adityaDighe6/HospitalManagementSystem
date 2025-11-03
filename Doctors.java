package hospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctors {
	private Connection con;
	
	public Doctors(Connection con) {
		this.con = con;
	}
	
	//Method to view doctors.
	public void viewDoctors() {
		String query = "SELECT * FROM doctors";
		
		try {
			PreparedStatement preparedStatement = con.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String specialization = rs.getString("specialization");
				
				System.out.println("Doctor's Id is : "+id);
				System.out.println("Doctor's Name is : "+name);
				System.out.println("Doctor's Specialization is : "+specialization);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	//End
	//Method to get Doctor by Id.
	public boolean getDoctorById(int id) {
		String query = "SELECT * FROM doctors WHERE id = ?";
		
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
