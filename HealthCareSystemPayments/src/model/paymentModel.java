package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import connection.DBConnect;


public class paymentModel {

private int success;
	
	public void addPayments(String name,String Hname,String type,double price) {
		Connection connection;
		PreparedStatement preparedStatement;
		String cname=null;
		
		try {
			connection = DBConnect.getDBConnection();
			
			preparedStatement = connection.prepareStatement("select * from payment where name=?");
			preparedStatement.setString(1, name);
			ResultSet rs = preparedStatement.executeQuery();
			 
			while(rs.next())
			{
				cname = rs.getString(2);	
			}
			
			if(cname==null) {
				
				//insert value
				preparedStatement = connection.prepareStatement("insert into payment (name,hname,type,price) values (?,?,?,?)");
				preparedStatement.setString(1, name);
				preparedStatement.setString(2,Hname);
				preparedStatement.setString(3,type);
				preparedStatement.setDouble(4, price);
				preparedStatement.execute();
				preparedStatement.close();
				connection.close();
				setSuccess(1);
				
			}else {
				setSuccess(0);
			}
		
		}catch (ClassNotFoundException | SQLException  e) {
			System.out.println(e.getMessage());
		}
	}

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}
	
	public String getPayments() {
		
		Connection connection;
		PreparedStatement preparedStatement;
		String table="";
		
		try {
			
			connection = DBConnect.getDBConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM payment");
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			table = "<table class='table'><thead>"
		            +"<tr>"
		            +"<th class='tableTh'>ID</th>"
		                +"<th class='tableTh'>Package Name</th>"
		                +"<th class='tableTh'>Hospital</th>"
		                +"<th class='tableTh'>Room Type</th>"
		                +"<th class='tableTh'>Price</th>"
		                +"<th class='tableTh'>Action</th>"
		                +"</tr>"
		            +"</thead><tbody>";
			
			while (resultSet.next()) {
				
				String button = "<button type='button' onclick='edit("+resultSet.getString(1)+")' class='btn btn-success'>Edit</button><button type='button' onclick='deletes("+resultSet.getString(1)+")' class='btn btn-danger'>Delete</button>";
				
				table = table+"<tr><td class='tableTh'>"+resultSet.getString(1)+"</td>"
						+ "<td class='tableTh'>"+resultSet.getString(2)+"</td>"
						+ "<td class='tableTh'>"+resultSet.getString(3)+"</td>"
						+ "<td class='tableTh'>"+resultSet.getString(5)+"</td>"
						+ "<td class='tableTh'>"+resultSet.getString(4)+"</td>"
						+ "<td class='tableTh'>"+button+"</td>"
					  + "</tr>";
				
			}
			
			preparedStatement.close();
			connection.close();
			
		}catch (ClassNotFoundException | SQLException  e) {

			System.out.println(e.getMessage());
		}
		
		return table+"</table>";
	}

	public void editPayments(String name,String Hname,String type,double price,int cid) {
		Connection connection;
		PreparedStatement preparedStatement;
		int id=0;
		
		try {
			connection = DBConnect.getDBConnection();
			
			//check hospital name
			preparedStatement = connection.prepareStatement("select * from payment where name=?");
			preparedStatement.setString(1, name);
			ResultSet rs = preparedStatement.executeQuery();
			 
			while(rs.next())
			{
				id= rs.getInt(1);
			}
			
			if(id==0||id==cid) {
				
				//insert value
				preparedStatement = connection.prepareStatement("UPDATE payment SET name=?,hname=?,type=?,price=? where id=?");
				preparedStatement.setString(1, name);
				preparedStatement.setString(2,Hname);
				preparedStatement.setString(3,type);
				preparedStatement.setDouble(4, price);
				preparedStatement.setInt(5,cid);
				preparedStatement.execute();
				preparedStatement.close();
				connection.close();
				setSuccess(1);
				
			}else {
				setSuccess(0);
			}
		
		}catch (ClassNotFoundException | SQLException  e) {
			System.out.println(e.getMessage());
		}
	}

	public void deletePayment(int pay) {
		Connection connection;
		PreparedStatement preparedStatement;
		
		try {
			connection = DBConnect.getDBConnection();
			
			//delete payment
			preparedStatement = connection.prepareStatement("DELETE FROM payment WHERE id=?");
			preparedStatement.setInt(1, pay);
			preparedStatement.execute();
			
			setSuccess(1);
		
		}catch (ClassNotFoundException | SQLException  e) {
			setSuccess(0);
		}
	}
	
	public JSONObject getPaymentById(int id) {
		Connection connection;
		PreparedStatement preparedStatement;
		JSONObject json = new JSONObject();
		
		try {
			connection = DBConnect.getDBConnection();
			
			preparedStatement = connection.prepareStatement("select * from payment where id=?");
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();

			while(rs.next())
			{
				
				json.put("id", rs.getInt(1));
				json.put("name", rs.getString(2));
				json.put("hospital", rs.getString(3));
				json.put("type", rs.getString(5));
				json.put("price", rs.getDouble(4));
				
			}
			
		}catch (ClassNotFoundException | SQLException  e) {
			setSuccess(0);
		}
		return json;
	}
	
}
