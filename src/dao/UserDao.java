package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Users;
import utility.ConnectionManager;

public class UserDao implements UserDaoInterface {

	@Override
	public int signupUser(Users user) {
		String insert_user = "INSERT INTO USERS(name, email, mobile, password) VALUES(?,?,?,?)";
		int result = 0;
		Connection con = null;
		try {
			con = ConnectionManager.getconnection();
			PreparedStatement ps = con.prepareStatement(insert_user);
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getContactNumber());
			ps.setString(4, user.getPassword());
			result = ps.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}


	@Override
	public boolean loginUser(String email, String password) {
		boolean status = false;
		try {
			Connection con = ConnectionManager.getconnection();
			PreparedStatement ps = con.prepareStatement("SELECT email, password FROM USERS WHERE email=? AND password=?");
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			status = rs.next();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return status;
	}

}
