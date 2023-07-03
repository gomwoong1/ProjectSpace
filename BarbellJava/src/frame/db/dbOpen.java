package frame.db;
//DB연결 김지웅 + 전우진
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import frame.login.IdCheckFrame;
import frame.login.JoinFrame;
import frame.login.Login;
import frame.login.PsCheckFrame;
import frame.main.MainFrame;

public class dbOpen {

	private java.sql.Statement statement;
	private ResultSet result;
	private Connection conn;
	private String pw, hint, db_pw, name;
	private MainFrame mainFrame;
	private int period;
	
	//전우진
	private String date;
	private String startDate; // 현재 날짜
	private long diffDay;  // 종료 - 현재 = 만료일
	private SimpleDateFormat format;  // 날짜 형식
	private String enddate;  // 이용권 종료날짜
	private int add;
	
	public dbOpen() {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				
				conn = DriverManager.getConnection(
						"jdbc:oracle:thin:@127.0.0.1:1521:XE", 
						"barbelljava", 
						"inha1004");
				
				statement = conn.createStatement();
				
			} catch (ClassNotFoundException e) {
				System.out.println("예외발생 : 해당 드라이버가 없습니다.");
				e.printStackTrace();
			} catch (SQLException e) {
				System.out.println("예외발생 : 접속 정보 확인이 필요합니다.");
				e.printStackTrace();
			}
			
			
	}
		
	// 회원가입 할 때 텍스트필드에서 값 긁어와서 DB에 던지기
	public void infoInsert(String id, String name, String ph, String add, String pw, String pwhint) {
		String sqlInsert = "insert into memberinfo (id, name, phonenumber, address, password, passwordhint)"
				+ " values('" + id + "', '" + name + "', '" + ph + "', '" + add + "', '" + pw + "', '" + pwhint + "')";
	
		try {
			statement.executeUpdate(sqlInsert);
		} catch (SQLException e) {
			System.out.println("Insert Error!");
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//로그인 검증, id값 돌려쓰기 위해서 String으로 id 리턴
		public void loginSelect(Login frame, String inputId, String inputPw) {
			String id = inputId;
			
			try {
				result = statement.executeQuery("select password from memberinfo where id = '" + inputId + "'" );
				if(result.next()) {
					pw = result.getString("password");
					
					result = statement.executeQuery("select name from memberinfo where id = '" + inputId + "'" );
					if(result.next()) {
						name = result.getString("name");
					}
					
					result = statement.executeQuery("select enddate from memberinfo where id = '" + inputId + "'" );
					if(result.next()) {
						enddate = result.getString("enddate");
					}
					
					//입력한 비밀번호와 DB에 저장된 비밀번호가 일치하는지 검증
					if(pw.equals(inputPw)) {
						mainFrame = new MainFrame(id, name, enddate);
						mainFrame.setLocationRelativeTo(frame);
						frame.dispose();
					} else {
						JOptionPane.showMessageDialog(frame, "입력 정보를 다시 확인해주세요.", "정보 오류", JOptionPane.ERROR_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(frame, "입력 정보를 다시 확인해주세요.", "정보 오류", JOptionPane.ERROR_MESSAGE);
				}
				
			} catch (SQLException e) {
				System.out.println("select Query Error!");
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(frame, "입력 정보를 다시 확인해주세요.", "정보 오류", JOptionPane.ERROR_MESSAGE);
			}
			
			finally {
				try {
					result.close();
					statement.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	
	//패스워드 초기화하는 힌트값 DB에서 긁어와서 검증하기
	public void resetPW(PsCheckFrame pf, String id, String inputHint) {
		try {
			result = statement.executeQuery("select passwordhint from memberinfo where id = '" + id + "'" );
		
			if(result.next()) {
				hint = result.getString("passwordhint");
			}
			
			if(inputHint.equals(hint)) {
				String sqlUpdate = "update memberinfo set password = '0000' where id = '" + id + "'";
				statement.executeUpdate(sqlUpdate);
				JOptionPane.showMessageDialog(pf, "힌트가 일치합니다. 비밀번호는 0000으로 초기화 됩니다.", "비밀번호 변경", JOptionPane.INFORMATION_MESSAGE);
				Login lg = new Login();
				lg.setLocationRelativeTo(pf);
				pf.dispose();
			}
			else {
				JOptionPane.showMessageDialog(pf, "아이디 또는 힌트가 일치하지 않습니다. ", "오류", JOptionPane.ERROR_MESSAGE);
				if(JOptionPane.showConfirmDialog(pf, 
						"고객센터로 전화하시겠습니까?",
						"힌트 오류",
						JOptionPane.YES_NO_OPTION
						) == JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(pf, "고객센터 전화번호는 032-777-7777 입니다.", "고객센테 안내", JOptionPane.INFORMATION_MESSAGE);
					} 
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				result.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 전우진 메인프레임 회원 이름
		public void pullInfoMain(String id, JLabel name) {
			try {
				result = statement.executeQuery("select name from memberinfo where id = '" + id + "'");
				if(result.next()) {
					name.setText(result.getString("name") + "회원님!");
				}
				
			} catch (SQLException e) {
				System.out.println("pull Main Error!");
				e.printStackTrace();
			} finally {
				try {
					statement.close();
					result.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		
		// 전우진 메인프레임 만료일 수정(하루 지날 때마다 차감)
		public void plusPeriodDate(String id, JLabel period, String endDate) {
			
			try {
				result = statement.executeQuery("select period from memberinfo where id = '" + id + "'" );
				
				if(result.next()) {
					period.setText(result.getString("period"));
				}
				
				result = statement.executeQuery("select enddate from memberinfo where id = '" + id + "'" );
				
				if(result.next()) {
					date = result.getString("enddate");
				}
				Date startDate = new Date();
				format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
				int dd = Integer.parseInt(period.getText());
				if(dd < 0 || period.equals(null) || date.equals("0") || date == null ) { // 만료일이 0일 이하이거나 종료 날짜가 널 값 혹은 0인 경우
					
					endDate = format.format(startDate);
					diffDay = 0;
					String lbl = Long.toString(diffDay);
					period.setText(lbl);
					String sqlUpdate = "update memberinfo set enddate = '" + endDate + "', period = '" +  period.getText() + "' where id = '" + id + "'";
					statement.executeUpdate(sqlUpdate);
					
				} else {

					Date finish = format.parse(endDate);
					
					diffDay =  ((finish.getTime() - startDate.getTime()) / (24*60*60*1000)); 
					
					String lblday = Long.toString(diffDay);
					period.setText(lblday);
					endDate = format.format(finish);
					String sqlUpdate = "update memberinfo set enddate = '" + endDate + "', period = '" +  period.getText() + "' where id = '" + id + "'";
					statement.executeUpdate(sqlUpdate);
					
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				
			} finally {
				try {
					result.close();
					statement.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	//전달받은 ID에 해당하는 정보를 가져옴
	public void pullInfo(String id, JTextField name, JTextField phonenumber, JTextField address, JPasswordField pw, JPasswordField pwCh) {
		try {
				result = statement.executeQuery("select name from memberinfo where id = '" + id + "'");
				if(result.next()) {
					name.setText(result.getString("name"));
				}
				result = statement.executeQuery("select phonenumber from memberinfo where id = '" + id + "'");
				if(result.next()) {
					phonenumber.setText(result.getString("phonenumber"));
				}
				result = statement.executeQuery("select address from memberinfo where id = '" + id + "'");
				if(result.next()) {
					address.setText(result.getString("address"));
				}
				result = statement.executeQuery("select password from memberinfo where id = '" + id + "'");
				if(result.next()) {
					pw.setText(result.getString("password"));
					pwCh.setText(result.getString("password"));
				}
					
		} catch (SQLException e) {
			System.out.println("pull Informaiton Error!");
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				result.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	//회원정보 업데이트
	public void chMemberInfo(String id, JPasswordField pw, JTextField phone, JTextField address) {
		char[] temp = pw.getPassword();
		String result = "";
		
		for(char ch	: temp) {
			Character.toString(ch);
			result += ""+ch+"";
		}
		
		String sqlUpdate = "update memberinfo set password = '" + result + "', phonenumber = '" + phone.getText() + "', address = '" + address.getText() +  "' where id = '" + id + "'";
		try {
			statement.executeUpdate(sqlUpdate);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
		
	//김지웅 이용권 구매시 이용권 기간 늘림 + 전우진 이용 기간 늘어날 때마다 만료일 늘림
	public void plusPeriod(String id, int addPeriod, String endDate) {
		try {
			result = statement.executeQuery("select period from memberinfo where id = '" + id + "'" );
			
			if(result.next()) {
				period = result.getInt("period");
			}
			addPeriod += period;
			add = addPeriod-period;
			result = statement.executeQuery("select enddate from memberinfo where id = '" + id + "'" );

			if(result.next()) {
				date = result.getString("enddate");
			}
			Date startDate = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
			try {
				
				Date day = format.parse(date);
				Calendar cal = Calendar.getInstance();
				cal.setTime(day);
				cal.add(Calendar.DATE, add);
				endDate = format.format(cal.getTime());
				Date finish = format.parse(endDate);
				diffDay =  ((finish.getTime() - startDate.getTime()) / (24*60*60*1000)); 
				String sqlUpdate = "update memberinfo set enddate = '" + endDate + "', period = '" +  diffDay + "' where id = '" + id + "'";
				statement.executeUpdate(sqlUpdate);
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				result.close();
				statement.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	// 비밀번호 확인 프레임에 사용되는 메소드
	public String checkPW(String id) {
		try {
			result = statement.executeQuery("select password from memberinfo where id = '" + id + "'");
			if(result.next()) {
				db_pw = result.getString("password");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				result.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return db_pw;
	}
	
	//아이디 중복 체크
	public void checkID(IdCheckFrame idCheckFrame, String idInput, JTextField tf) {
		try {
			result = statement.executeQuery("select id from memberinfo where id = '" + idInput + "'");
			
			if(result.next()) {
				JOptionPane.showMessageDialog(idCheckFrame, "이미 사용중인 아이디 입니다.");
				tf.setText("");
			}else {
				JOptionPane.showMessageDialog(idCheckFrame, "사용 가능한 아이디 입니다.");
				JoinFrame jf = new JoinFrame("회원가입");
				jf.setLocationRelativeTo(idCheckFrame);
				JCheckBox ch = jf.getAgreeCheck();
				ch.setSelected(true);
				JTextField jfid = jf.getTfId();
				idCheckFrame.dispose();
				
				jfid.setText(idInput);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//PT게시판 글 비밀번호 끌어오는 메서드, 작성자 김지웅, 0602
	public String chBoardPW(String bdID) {
		String boardPW = "";
		
		try {
			result = statement.executeQuery("select password from pttalk where pt_id = '" + bdID + "'");
			if(result.next()) {
				boardPW = result.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return boardPW;
	}

}
