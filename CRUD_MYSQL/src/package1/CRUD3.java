package package1;

import java.sql.*;
import java.util.Scanner;

public class CRUD3 {
	static Scanner sc = new Scanner(System.in);
	private final static String url = "jdbc:mysql://localhost:3306/sports";
	private final static String user = "root";
	private final static String password = "sri08#fitness";
	static Connection con = null;

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
			System.out.println("1. Insert data");
			System.out.println("2. Update data");
			System.out.println("3. Delete data");
			System.out.println("4. View data");
			System.out.println("5. Insert multiple data");
			System.out.println("6.View selected data");
			System.out.println("7. Update multiple data");
			System.out.println("8. Delete multiple data");
			System.out.println("Choose any action:");
			int choice = sc.nextInt();

			switch (choice) {
			case 1:
				insertData(con);
				break;
			case 2:
				updateData(con);
				break;
			case 3:
				deleteData(con);
				break;
			case 4:
				viewData(con);
				break;
			case 5:
				insertMultipleData(con);
				break;
			case 6:
				viewSelectedData(con);
				break;
			case 7:
				updateMultipleData(con);
				break;
			case 8:
				deleteMultipleData(con);
				break;
			}
			con.close();
		}
		 catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void insertData(Connection con) throws SQLException {
		System.out.println("Enter product name: ");
		String productName = sc.next();
		PreparedStatement st = con.prepareStatement("insert into product (name) values (?)");
		st.setString(1, productName);
		int num = st.executeUpdate();
		System.out.println(num + " of row/s updated");
	}

	private static void updateData(Connection con) throws SQLException {
		System.out.println("Enter product id to be updated: ");
		int id = sc.nextInt();
		System.out.println("Enter new product name: ");
		String newProductName = sc.next();
		PreparedStatement st = con.prepareStatement("update product set name=? where id=?");
		st.setString(id, newProductName);
		int numOfRowsAffected = st.executeUpdate();
		if (numOfRowsAffected > 0) {
			System.out.println("Data updated successfully");
		} else {
			System.out.println("No such id found");
		}
	}
	private static void deleteData(Connection con) throws SQLException{
		System.out.println("Select the id of product to be deleted: ");
		int id = sc.nextInt();
		PreparedStatement st = con.prepareStatement("delete from product where id = "+id);
		st.executeUpdate();
		System.out.println("Data deleted successfully");
	}
	private static void viewData(Connection con) throws SQLException{
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select * from product");
		System.out.println("Product Table data:");
		while(rs.next()) {
			System.out.println(rs.getInt("id")+" "+rs.getString("name"));
		}
	}
	private static void insertMultipleData(Connection con) throws SQLException{
		System.out.println("How many records do you want to insert?");
		int records = sc.nextInt();
		sc.nextLine();
		for(int i=1;i<=records;++i) {
			System.out.println("Enter product name for record "+i+": ");
			String productName = sc.nextLine();
			PreparedStatement st = con.prepareStatement("insert into product (name) values (?)");
			st.setString(1, productName);
			int num = st.executeUpdate();
			System.out.println(num+" of row/s affected");
			
		}
	}
	private static void viewSelectedData(Connection con) throws SQLException{
		System.out.println("Enter id of the selected product: ");
		int id = sc.nextInt();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select * from product where id= "+id);
		if(rs.next()) {
			System.out.println(rs.getInt("id")+" "+rs.getString("name"));
		}
		else {
			System.out.println("No product found with the specified id");
		}
	}
	private static void updateMultipleData(Connection con) throws SQLException{
		System.out.println("How many records do you want to update: ");
		int record = sc.nextInt();
		sc.nextLine();
		for(int i=1;i<=record;i++) {
			System.out.println("Enter id to be updated: ");
			int id = sc.nextInt();
			sc.nextLine();
			System.out.println("Enter updated product name: ");
			String newProductName = sc.next();
			PreparedStatement st = con.prepareStatement("update product set name = ? where id = ?");
			st.setString(1,newProductName);
			st.setInt(2,id);
			st.executeUpdate();
		}
		System.out.println("Data inserted successfully");
	}
	private static void deleteMultipleData(Connection con) throws SQLException{
		int noOfRowsAffected;
		System.out.println("How many records do you want to delete");
		int record = sc.nextInt();
		sc.nextLine();
		for(int i=1;i<=record;i++) {
			System.out.println("Enter id of product you want to delete for record "+i+": ");
			int id = sc.nextInt();
			PreparedStatement st = con.prepareStatement("delete from product where id= "+id);
			noOfRowsAffected = st.executeUpdate();
			if(noOfRowsAffected>0) {
				System.out.println("Data deleted successfully");
			}
			else {
				System.out.println("No such id found");
			}
		}
	}
}
