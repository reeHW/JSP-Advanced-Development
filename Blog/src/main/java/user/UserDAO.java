package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public UserDAO() {
		try {
			String dbURL = "jdbc:oracle:thin:@localhost:1521/xe";
			String driver = "oracle.jdbc.driver.OracleDriver";
			String User = "Web";
			String Password = "1234";
			Class.forName(driver);
			con = DriverManager.getConnection(dbURL, User, Password);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int login(String userID, String userPassword) {
		//사용자의 userID를 인자로 받아서 실제로 그 사용자가 존재하는지 조회하는 구문
		String SQL = "SELECT USERPASSWORD FROM USER_T WHERE USERID=?";
		try {
			pstmt = con.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			
			//사용자가 존재하는지 확인
			if(rs.next()) {
				// 사용자의 비밀번호가 일치하는지 확인
				if(rs.getString("USERPASSWORD").equals(userPassword)) {
					return 1; //로그인 성공
				}else {
					return 0; // 비밀번호 불일치
				}
			}
			return -1; // 아이디가 없음.
		}

		catch (Exception e) {
			// TODO: handle exception
		}
		return -2; // 데이터베이스 오류

	}
	
	public int join(User user) {
		String SQL = "INSERT INTO USER_T VALUES(?, ?, ?, ?, ?)";
		try {
			pstmt = con.prepareStatement(SQL);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserGender());
			pstmt.setString(5, user.getUserEmail());
			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return -1;
	}
}
