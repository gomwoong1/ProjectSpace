package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private PreparedStatement pstmt = null;
	
	public static void main(String[] args) {
		DB db = new DB();
//		db.sel();
//		db.inst("gomwoong", 50);
//		db.inst("pinkippo", 40);
//		db.inst("choi", 60);
//		db.inst("gomwoong", 70);
//		db.inst("hello", 50);
//		db.inst("world", 55);
		db.rank();
	}
	
	public DB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/ticket";
			conn  = DriverManager.getConnection(url, "root", "201945018");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("에러: " + e);
		} 
	}

	
	public void sel() {
		try {
			String user, score;
			String sql = "select name, score from rankdb order by num desc limit 2";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
                user = rs.getString(1);
                score = rs.getString(2);
                
                System.out.println(user + "," + score);
            }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void inst(String username, int score) {
		try {
			String sql = "insert into rankdb (name, score) values ('" + username + "'," + score + ")";
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void rank() {
		try {
			String user, score;
			String[] rankArr = new String[3];
			String sql = "select name, score from rankdb order by score desc limit 3";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			int i = 0;
			while(rs.next()){
                user = rs.getString(1);
                score = rs.getString(2);
                
               rankArr[i] = user + "," + score;
               i++;
            }
			for(i = 0; i < 3; i++) {
				System.out.println((i+1) + "등: " + rankArr[i]);
			}
			System.out.println();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
