package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginDao {
	Connection conn = null;
	PreparedStatement pstmt;
	
	final String JDBC_DRIVER = "org.h2.Driver";
	final String JDBC_URL = "jdbc:h2:~/minidb;AUTO_SERVER=TRUE";
	
	public void open() {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(JDBC_URL,"mini","1234");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void close() {
	    try {
	        if (pstmt != null) pstmt.close();
	        if (conn != null) conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public void insert(Login s) {
		open();
		String sql = "INSERT INTO logins(STUDENT_ID, USERID, PASSWORD, NAME, MAJOR, GRADE) VALUES(?,?,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,s.getSTUDENT_ID());
			pstmt.setString(2, s.getUSERID());
			pstmt.setString(3,s.getPASSWORD());
			pstmt.setString(4,s.getNAME());
			pstmt.setString(5,s.getMAJOR());
			pstmt.setInt(6,s.getGRADE());
			
			int count = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("ERROR(insert): insert failed for userId=" + s.getUSERID());
			e.printStackTrace();
		} finally {
			close();
		}
	}
	public List<Login> getAll(){
		open();
		List<Login> Logins =  new ArrayList<>();
		
		try {
			pstmt = conn.prepareStatement("select * from logins");
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Login s = new Login();
				s.setSTUDENT_ID(rs.getInt("STUDENT_ID"));
				s.setUSERID(rs.getString("USERID"));
				s.setPASSWORD(rs.getString("PASSWORD"));
				s.setNAME(rs.getString("NAME"));
				s.setMAJOR(rs.getString("MAJOR"));
				s.setGRADE(rs.getInt("GRADE"));
				
				Logins.add(s);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return Logins;
	}

	public boolean loginCheck(String userId, String password) {
	    open();
	    boolean result = false;
	    try {
	        String sql = "SELECT * FROM logins WHERE USERID = ? AND PASSWORD = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, userId);
	        pstmt.setString(2, password);

	        ResultSet rs = pstmt.executeQuery();

	        // 디버그: SQL 실행 및 입력값 확인
	        System.out.println("DEBUG(loginCheck): loginCheck executed, userId=" + userId + ", password=" + password);

	        if (rs.next()) {
	            result = true;
	            System.out.println("DEBUG(loginCheck): login successful for userId=" + userId);
	        } else {
	            System.out.println("DEBUG(loginCheck): login failed for userId=" + userId);
	        }
	    } catch(Exception e) {
	        e.printStackTrace();
	    } finally {
	        close();
	    }
	    return result;
	}


	public Login getUserById(String userId) {
	    open();
	    Login user = null;
	    try {
	        String sql = "SELECT * FROM logins WHERE USERID = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, userId);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            user = new Login();
	            user.setSTUDENT_ID(rs.getInt("STUDENT_ID"));
	            user.setUSERID(rs.getString("USERID"));
	            user.setPASSWORD(rs.getString("PASSWORD"));
	            user.setNAME(rs.getString("NAME"));
	            user.setMAJOR(rs.getString("MAJOR"));
	            user.setGRADE(rs.getInt("GRADE"));
	        }
	    } catch(Exception e) {
	        e.printStackTrace();
	    } finally {
	        close();
	    }
	    return user;
	}
	
	public void deleteAll() {
	    open();
	    String sql = "DELETE FROM logins";

	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        close();
	    }
	}
}