package notePad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class ItemDbUtil {
	private DataSource dataSource;
	
	public ItemDbUtil(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public List<Item> getItems() throws Exception {
		List<Item> items = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = dataSource.getConnection();
			myStmt = myConn.createStatement();
			
			String sql = "select * from item";
			
			myRs = myStmt.executeQuery(sql);
			
			while (myRs.next()) {
				int id = myRs.getInt("id");
				String todo = myRs.getString("todo");
				
				Item tempItem = new Item(id, todo);
				
				items.add(tempItem);
			}
			return items;
		} finally {
			close(myConn, myStmt, myRs);
		}
	}
	
	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		try {
			if (myRs != null) {
				myRs.close();
			}
			if (myStmt != null) {
				myStmt.close();
			}
			if (myConn != null) {
				myConn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addItem(Item theItem) throws Exception {
		// TODO Auto-generated method stub
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = dataSource.getConnection();
			String sql = "insert into item " + "(todo)" + "values (?)";
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setString(1, theItem.getTodo());
			
			myStmt.execute();
		} finally {
			close(myConn, myStmt, null);
		}
	}

	public void deleteItem(String theItemId) throws Exception{
		// TODO Auto-generated method stub
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = dataSource.getConnection();
			String sql = "delete from item " + 
					     "where id = ?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, Integer.parseInt(theItemId));
			
			myStmt.execute();
		} finally {
			close(myConn, myStmt, null);
		}
	}
	
}
