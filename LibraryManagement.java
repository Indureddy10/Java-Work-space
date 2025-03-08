package library.com;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class LibraryManagement {
	static final String URL="jdbc:mysql://localhost:3306/librarymanagement";
	static final String USER="root";
	static final String PASSWORD="Indu@123#9s";
			
	public static void main(String[] args) throws SQLException {
		Scanner scanner=new Scanner(System.in);
		while(true) {
			System.out.println(" 1.Add book\n 2.Purchase book\n 3.Retrive book price\n 4.Exit");
			System.out.print("Select one option :");
			int choice=scanner.nextInt();
			switch(choice) {
			case 1:
				AddBook(scanner);
				break;
			case 2:
				PurchaseBook(scanner);
				break;
			case 3:
				RetriveBookPrice(scanner);
				break;
			case 4:
				Exit();
				scanner.close();
				return;
				default:
					System.out.println("Invalid choice,Try again...");
					
			}
			
		}
	}

	
	public static void AddBook(Scanner scanner) throws SQLException {
		String sql="insert into library_store value(?,?,?,?)";
		System.out.print("Enter Book ID :");
		int Book_id=scanner.nextInt();
		scanner.nextLine();
		System.out.print("Enter Book Name :");
		String Book_name=scanner.nextLine();
		System.out.print("Enter Author of the Book :");
		String Author=scanner.nextLine();
		System.out.print("Enter the price :");
		float Price=scanner.nextFloat();
		try(Connection con=DriverManager.getConnection(URL,USER,PASSWORD);
		PreparedStatement pstm=con.prepareStatement(sql)){
		pstm.setInt(1, Book_id);
		pstm.setString(2,Book_name);
		pstm.setString(3,Author);
		pstm.setFloat(4, Price);
		int Insertrow=pstm.executeUpdate();
		if(Insertrow>0) {
		System.out.println("Book added Successfully !");
		}else {
			System.out.println("Book is not Added into Store ");
		}
		} catch (SQLException e) {
            System.out.println("Error adding book!");
            e.printStackTrace();
		}
	}
	public static void PurchaseBook(Scanner scanner) throws SQLException {
		String sql="DELETE FROM library_store WHERE Book_name=?";
		System.out.print("Which booked you want :");
		scanner.nextLine();
		String Book_name=scanner.nextLine();
		
		try(Connection con=DriverManager.getConnection(URL,USER,PASSWORD);
		PreparedStatement pstm=con.prepareStatement(sql)){
		pstm.setString(1, Book_name);
		int Deleterow=pstm.executeUpdate();
		if(Deleterow>0) {
			System.out.println("Here u can take this book...");

		}else {
			System.out.println("Book is not avilable here.");
		}
		} catch (SQLException e) {
            System.out.println("Error purchase book!");
            e.printStackTrace();
		}
	}
	public static void RetriveBookPrice(Scanner scanner) throws SQLException {
		String sql="SELECT Price FROM library_store WHERE Book_name=?";
		System.out.print("Name of the book :");
		scanner.nextLine();
		String Book_name=scanner.nextLine();
		
		try(Connection con=DriverManager.getConnection(URL,USER,PASSWORD);
		PreparedStatement pstm=con.prepareStatement(sql)){
		pstm.setString(1,Book_name);
		ResultSet rs=pstm.executeQuery();
		if(rs.next()) {
			System.out.println("the price of "+Book_name+"book is :"+rs.getFloat("Price"));
		}else {
			System.out.println("Book not found");
		}
		} catch (SQLException e) {
            System.out.println("Error Retrive price book!");
            e.printStackTrace();
		}
		
    }
	public static void Exit() {
	String sql="SELECT * FROM library_store";
	try(Connection con=DriverManager.getConnection(URL,USER,PASSWORD);
			PreparedStatement pstm=con.prepareStatement(sql)){
			ResultSet rs=pstm.executeQuery();
			while(rs.next()) {
				System.out.println("Book sno "+rs.getInt("Book_id")+" Book Name ("+rs.getString("Book_name")+") Author of book ("
						 +rs.getString("Author")+") Price of book ₹"+rs.getFloat("Price"));
			}
	      } catch (SQLException e) {
        System.out.println("Error library book!");
        e.printStackTrace();
	}
	
	
		System.out.println("Exit...");
	}
	
}
