package JDBCtest;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {
	public static void main (String args[]) {
		Test.getAll();
		Test.insert(new Student("Achilles", "Male", "14", "13699456645"));
		Test.getAll();
		Test.update(new Student("Curry", "", "7", ""));
		Test.delete("Achilles");
		Test.getAll();
	}
	
	private static Connection getConn() {
		String driver = "com.mysql.jdbc.Driver";
	    String url = "jdbc:mysql://localhost:3306/test";
	    String username = "ODBC";
	    String password = "";
	    Connection conn = null;
	    try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        conn = (Connection) DriverManager.getConnection(url, username, password);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return conn;
	}
	
	private static int insert(Student student) {
		Connection conn = getConn();
		int i = 0;
		String sql = "insert into student (Name, Sex, Age, Tel) values(?, ?, ?, ?)";
		PreparedStatement ps;
		try {
			ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setString(1, student.getName());
			ps.setString(2, student.getSex());
			ps.setString(3, student.getAge());
			ps.setString(4, student.getTel());
			i = ps.executeUpdate();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}
	
	private static int update(Student student) {
		Connection conn = getConn();
		int i = 0;
		String sql = "update student set Age = '" + student.getAge() + "' where Name = '" + student.getName() + "'";
		PreparedStatement ps;
		try {
			ps = (PreparedStatement) conn.prepareStatement(sql);
			i = ps.executeUpdate();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}
	
	private static Integer getAll() {
		Connection conn = getConn();
	    String sql = "select * from student";
	    PreparedStatement ps;
	    try {
	        ps = (PreparedStatement) conn.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	        int col = rs.getMetaData().getColumnCount();
	        System.out.println("============================");
	        while (rs.next()) {
	            for (int i = 1; i <= col; i++) {
	                System.out.print(rs.getString(i) + "\t");
	                if ((i == 2) && (rs.getString(i).length() < 8)) {
	                    System.out.print("\t");
	                }
	             }
	            System.out.println("");
	        }
	            System.out.println("============================");
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	private static int delete(String name) {
	    Connection conn = getConn();
	    int i = 0;
	    String sql = "delete from student where Name='" + name + "'";
	    PreparedStatement ps;
	    try {
	        ps = (PreparedStatement) conn.prepareStatement(sql);
	        i = ps.executeUpdate();
	        System.out.println("resutl: " + i);
	        ps.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return i;
	}
}
