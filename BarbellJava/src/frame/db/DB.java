package frame.db;
//DB연결 윤선호
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.beans.Statement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import frame.board.Board;
import frame.board.BoardEdit;
import frame.login.MyRoutine;
import frame.main.MemoFrame; 
import frame.login.Record;

public class DB {
	//윤선호 자유게시판 관련 DB 추가
	private ResultSet result = null;
	private Connection conn = null;
	private Statement stmt = null;
	
	private int id;
	private String cmt;
	private BoardEdit be;
	private MemoFrame memo;
	private String my_memo;
	
	public DB(BoardEdit boardedit, MemoFrame memoframe) {
		this.be = boardedit;
		this.memo = memoframe;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@127.0.0.1:1521:XE",
					"barbelljava",
					"inha1004");
			
			stmt = conn.createStatement();
			
		} catch (ClassNotFoundException e) {
			System.out.println("예외발생 : 해당 드라이버가 없습니다.");
			e.printStackTrace();
		} catch(SQLException e) {
			System.out.println("예외발생 : 접속 정보 확인 필요");
			e.printStackTrace();
		}
		
	}

	//2022-05-26 윤선호 자유게시판 글 작성 DB
	public void BDInsert(String title, String writeday, String writer, String category, String bd_contents, String id) {
	try {
		String sqlInsert = "insert into FREETALK (BD_ID, BD_TITLE, WRITE_DAY, BD_WRITER, category, bd_content, ID) values(FKID_SEQ.NEXTVAL, '" + title + "', '" + writeday + "', '" + writer + "', '" + category +"', '" + bd_contents +"', '" + id + "')";
		stmt.executeUpdate(sqlInsert);
		
		System.out.println("입력 성공");
		
	}catch(SQLException e){
		System.out.println("Insert fail");
		e.printStackTrace();
		
	}finally {
		try {
			stmt.close();
			//conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
		
}

	//윤선호 자유게시판 id DB에서 불러오기
	public int GetBDID() {
		try {
			result = stmt.executeQuery("select BD_ID from freetalk");
			if(result.next()) {
				String max = String.valueOf(result.getInt(1));
				id = Integer.parseInt(max);
			}
		}catch(SQLException e) {
			System.out.println("select Query Error!");
			e.printStackTrace();
		}
		System.out.println(id + "ddddddddddddddddd");
		return id ;
	}

	//2022-05-27 윤선호 자유게시판 글 수정
	public void BDUpdate(String id, String title, String writeday, String writer, String category, String bd_contents ) {
		try {
			String sqlUpdate = "update FREETALK SET BD_TITLE = '" + title +"', WRITE_DAY = '" + writeday + "', BD_WRITER = '" + writer + "', CATEGORY = '" + category + "', BD_CONTENT = '" + bd_contents + "' where BD_ID = '" + Integer.parseInt(id) +"'";
			stmt.executeUpdate(sqlUpdate);
			
			System.out.println("수정 성공!");
			
		}catch(SQLException e){
			System.out.println("수정 실패");
			e.printStackTrace();
			
		}finally {
			try {
				stmt.close();
				//conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	}
	//2022-05-27 윤선호 자유게시판 댓글 추가
	public void BDCMT(String id, String bd_contents, String name) {
		
		try {
			Random rnd = new Random();
			//글번호의 최대값보다 큰 수부터 랜덤값 가져오게 바꾸자
			String cmt = bd_contents;
			System.out.println(cmt);
		String sqlInsert = "insert into FR_COMMENT values('" + rnd.nextInt(99999 - 10000 + 1) + 10000 + "', '" + bd_contents + "', '" + id + "', '" + name + "')";
		stmt.executeUpdate(sqlInsert);
		
		System.out.println("댓글 추가 성공");
		
	}catch(SQLException e){
		System.out.println("댓글 추가 실패");
		e.printStackTrace();
		
	}finally {
		try {
			stmt.close();
			//conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
	//2022-05-26 윤선호 자유게시판 댓글 보여주기
	public void DisplayCMT(String id) {
		Statement stmt = null;
		ResultSet result = null;
		try {
			stmt = conn.createStatement();
			result = stmt.executeQuery("select \"CMT_WRITER\", \"COMMENT\" FROM FR_COMMENT where BD_ID = '" + Integer.parseInt(id) + "'");	
			
			while(result.next()) {
				String[] cmt = {result.getString("CMT_WRITER"), result.getString("COMMENT")};
				System.out.println(cmt[0]);
				System.out.println(cmt[1]);
				be.getta_comment().append(cmt[0] + " : " + cmt[1] + "\n");
				System.out.println("댓글 보여주기 성공");
				
			}
		} catch (SQLException e) {
			//System.out.println("댓글 못 불러옵니다.");
			//e.printStackTrace();
		}finally {
			try {
				//stmt.close();
				//result.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	//2022-05-28 21:57 윤선호 게시물 삭제
	public void DeleteBD(String id) {
		//int num_id = Integer.parseInt(id);
		try {
			result = stmt.executeQuery("delete FROM FR_COMMENT where BD_ID = '" + Integer.parseInt(id) + "'");
			result = stmt.executeQuery("delete FROM FREETALK where BD_ID = '" + Integer.parseInt(id) + "'");
			System.out.println("게시물 삭제 성공");
		} catch (NumberFormatException e) {
			System.out.println("게시물 삭제 실패");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
				result.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	//2022-06-15 윤선호 메모장 데베 저장 수정
	public void InsertMemo(String memo_date, String memo_data, String id) {
		try {
			stmt = conn.createStatement();
			result = stmt.executeQuery("select MEMO_CONTENT FROM MEMO WHERE MEMO_DATE = '" + memo_date + "' and ID = '" + id + "'");
			
			if(result.next()) {
				result = stmt.executeQuery("Update MEMO SET MEMO_CONTENT = '" + memo_data + "' ");
			}else {
			String sqlInsert = "insert into MEMO (MEMO_ID, MEMO_CONTENT, ID, MEMO_DATE) values(MEMO_SEQ.NEXTVAL, '" + memo_data + "', '" + id +"', '" + memo_date +"')";
			stmt.executeUpdate(sqlInsert);
			}
			System.out.println("메모 저장 성공");
			
		}catch(SQLException e){
			System.out.println("메모 저장 실패");
			e.printStackTrace();
			
		}finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	//2022-06-15 윤선호 메모 삭제
	public void DelMemo(String memo_date, String id) {
		try {
			stmt = conn.createStatement();
			result = stmt.executeQuery("delete from MEMO WHERE ID = '" + id +"' and MEMO_DATE = '" + memo_date + "' ");
		}catch (Exception e) {
			System.out.println("메모삭제 실패");
			e.printStackTrace();
		}finally {
			try {
				result.close();
				conn.close();
				stmt.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//2022-06-04 윤선호 테이블 업데이트
	public void TableRefresh(Board bd) {
		int rowCount = bd.getModel().getRowCount();
		for(int i = rowCount - 1; i >= 0; i--) {
			bd.getModel().removeRow(i);
		}
		try {
			stmt = conn.createStatement();
			result = stmt.executeQuery("select * from freetalk order by BD_ID desc");
			
			while(result.next()) {
				String[] imsi = {result.getString("BD_ID"), result.getString("BD_TITLE"), result.getString("BD_WRITER"), result.getString("WRITE_DAY"), result.getString("BD_CONTENT"),result.getString("CATEGORY")};
				bd.getModel().addRow(imsi);
			}
		} catch(SQLException e) {
			System.out.println("예외발생 : 접속 정보 확인 필요");
			e.printStackTrace();
			
		}finally {
			try {
				if(result != null)
					result.close();
				if(stmt != null)
					stmt.close();
				if(conn != null)
					conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	//윤선호 JTable에 모든 값 보여줌
		public void displayData(Board bd) {
			bd.getModel().setNumRows(0);
			try {
				stmt = conn.createStatement();
				result = stmt.executeQuery("select * from freetalk order by BD_ID desc");
				
				while(result.next()) {
					String[] imsi = {result.getString("BD_ID"), result.getString("BD_TITLE"), result.getString("BD_WRITER"), result.getString("WRITE_DAY"), result.getString("BD_CONTENT"),result.getString("CATEGORY")};
					bd.getModel().addRow(imsi);
				}
				
			} catch(SQLException e) {
				System.out.println("예외발생 : 접속 정보 확인 필요");
				e.printStackTrace();
				
			}finally {
				try {
					if(result != null)
						result.close();
					if(stmt != null)
						stmt.close();
					//if(conn != null)
						//conn.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	
	//2022-05-28 윤선호 내용으로 글 검색하면 보여준다
		public void scDisplay(Board bd, String src) {
			bd.getModel().setNumRows(0);
			try {
				stmt = conn.createStatement();
				result = stmt.executeQuery("select * from freetalk where BD_CONTENT LIKE '%' || '" +  src + "' || '%' order by BD_ID desc");
				
				while(result.next()) {
					String[] sch = {result.getString("BD_ID"), result.getString("BD_TITLE"), result.getString("BD_WRITER"), result.getString("WRITE_DAY"), result.getString("BD_CONTENT"), result.getString("CATEGORY")};
					bd.getModel().addRow(sch);
					}
					
				} catch(SQLException e) {
					System.out.println("예외발생 : 접속 정보 확인 필요");
					e.printStackTrace();
					
				} finally {
					try {
						if(result != null)
							result.close();
						if(stmt != null)
							stmt.close();
						//if(conn != null)
							//conn.close();
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
		}
		//201945012 로그인한 사람별로 저장한 메모를 날짜에 맞게 보여준다
		public void showMemo(String id, String date, MemoFrame memo) {
			String sql = "";
			
			try {
				stmt = conn.createStatement();
				sql = "select * FROM MEMO WHERE ID = '" + id +"' AND MEMO_DATE = '" + date +"'";
				result = stmt.executeQuery(sql);
				
				if(result.next()) {
					String my_memo = result.getString(2);
					System.out.println(result.getString(2));
					memo.getTaMemo().append(my_memo + "\n");
					System.out.println("메모장 보여주기 성공");
					
				}else {
					System.out.println("저장된 내용 없음");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			} 
			catch (Exception e) {
				System.out.println("메모장 보여주기 실패");
				e.printStackTrace();
				e.getMessage();
			}finally {
				try {
					stmt.close();
					result.close();
					//conn.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		//2022-06-05 윤선호 운동기록 디비저장
		public void EXInsert(String id, String name, String ex_date, String ex_name, String ex_weight, String ex_times, String ex_reps, String ex_set) {
			try {
				String sqlInsert = "insert into EX (ID, NAME, EX_DATE, EX_NAME, EX_WEIGHT, EX_TIMES, EX_REPS, EX_SET) "
						+ "values('" + id + "', '" + name + "', '" + ex_date + "', '" + ex_name + "', '" + ex_weight + "', '" + ex_times + "', '" + ex_reps + "', '" + ex_set + "') ";
				System.out.println(sqlInsert);
				stmt.execute(sqlInsert);
				System.out.println("기록 성공");
			}catch (Exception e) {
				System.out.println("운동기록실패");
				e.printStackTrace();
			}finally {
				try {
					stmt.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		//2022-06-05 운동기록한거 테이블에 보여주기
		public void EXdisplay(MyRoutine mr, String id) {
			mr.getModel().setNumRows(0);
			try {
				stmt = conn.createStatement();
				result = stmt.executeQuery("select EX_DATE, EX_NAME, EX_WEIGHT, EX_TIMES, EX_REPS, EX_SET from EX where ID = '"+ id + "'");
				while(result.next()) {
					String[] ex = {result.getString("EX_DATE"), result.getString("EX_NAME"),result.getString("EX_WEIGHT"), result.getString("EX_TIMES"),
							result.getString("EX_REPS"), result.getString("EX_SET")};
					mr.getModel().addRow(ex);
					}
				System.out.println("운동기록을 잘 보여줍니다");
				}catch (Exception e) {
					System.out.println("운동기록 보여주기 실패");
					e.printStackTrace();
				}finally {
					try {
						result.close();
						stmt.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		//2022-06-05 윤선호 운동기록 보는 테이블 리프레시 
		public void EXRefresh(MyRoutine mr, String id) {
			int rowCount = mr.getModel().getRowCount();
			for(int i = rowCount - 1; i >= 0; i--) {
				mr.getModel().removeRow(i);
			}
			try {
				stmt = conn.createStatement();
				result = stmt.executeQuery("select EX_DATE, EX_NAME, EX_WEIGHT, EX_TIMES, EX_REPS, EX_SET from EX where ID = '"+ id + "'");
				
				while(result.next()) {
					String[] ex = {result.getString("EX_DATE"), result.getString("EX_NAME"), result.getString("EX_WEIGHT"), result.getString("EX_TIMES"), result.getString("EX_REPS"),result.getString("EX_SET")};
					mr.getModel().addRow(ex);
				}
				
			} catch(SQLException e) {
				System.out.println("예외발생 : 접속 정보 확인 필요");
				e.printStackTrace();
				
			}finally {
				try {
					if(result != null)
						result.close();
					if(stmt != null)
						stmt.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		//0609 윤선호 내 글만 보이기
		public void MyBoard(Board bd, String id) {
			bd.getModel().setNumRows(0);
			try {
				stmt = conn.createStatement();
				result = stmt.executeQuery("select * from freetalk where ID = '" + id +"' order by BD_ID desc ");
				
				while(result.next()) {
					String[] imsi = {result.getString("BD_ID"), result.getString("BD_TITLE"), result.getString("BD_WRITER"), result.getString("WRITE_DAY"), result.getString("BD_CONTENT"),result.getString("CATEGORY")};
					bd.getModel().addRow(imsi);
				}
				
			} catch(SQLException e) {
				System.out.println("예외발생 : 접속 정보 확인 필요");
				e.printStackTrace();
				
			}finally {
				try {
					if(result != null)
						result.close();
					if(stmt != null)
						stmt.close();
					//if(conn != null)
						//conn.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		//0609 윤선호 운동기록열람 날짜 별로 보여주기
		public void ExDate(MyRoutine mr, String id, String date) {
			mr.getModel().setNumRows(0);
			try {
				stmt = conn.createStatement();
				result = stmt.executeQuery("select EX_DATE, EX_NAME, EX_WEIGHT, EX_TIMES, EX_REPS, EX_SET from EX where ID = '"+ id + "' AND EX_DATE = '" + date + "' ");
				while(result.next()) {
					String[] ex = {result.getString("EX_DATE"), result.getString("EX_NAME"),result.getString("EX_WEIGHT"), result.getString("EX_TIMES"),
							result.getString("EX_REPS"), result.getString("EX_SET")};
					mr.getModel().addRow(ex);
					}
				System.out.println("날짜별 운동기록을 잘 보여줍니다");
				}catch (Exception e) {
					System.out.println("날짜별 운동기록 보여주기 실패");
					e.printStackTrace();
				}finally {
					try {
						result.close();
						stmt.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		//0609 윤선호 운동 목록 저장, 0614 윤선호 같은 운동 저장 안되게 추가
		public void AddMyRt(Record record, String id, String myex) {
			try {
				stmt = conn.createStatement();
				result = stmt.executeQuery("select MY_EXNAME FROM MY_EX where MY_EXNAME = '" + myex +"'");
				if(result.next()) {
					JOptionPane.showMessageDialog(record, "이미 있습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				}else {
				result = stmt.executeQuery("insert into MY_EX (ID, MY_EXNAME) VALUES('" + id + "', '" + myex +"')");
				}
				System.out.println("운동명 디비 잘 추가");
			}catch (Exception e) {
				System.out.println("운동명 디비 안됨");
				e.printStackTrace();
			}finally {
				try {
					result.close();
					stmt.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		//0609 윤선호 운동목록 콤박에 추가
		public void MyCombo(Record record, String id) {
			try {
				stmt = conn.createStatement();
				result = stmt.executeQuery("select MY_EXNAME FROM MY_EX where ID = '" + id +"'");
				while(result.next()) {
					String mycombo = result.getString("MY_EXNAME");
					record.getVecCombo().add(mycombo);
				}
				System.out.println("콤박 잘됨");
			}catch (Exception e) {
				System.out.println("콤박 잘 안됨");
				e.printStackTrace();
			}finally {
				try {
					result.close();
					stmt.close();
				}catch (Exception e) {
				e.printStackTrace();
				}
			}
		}
}