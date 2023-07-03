package frame.db;
//DB연결 허유진
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import frame.board.Board2_PT;
import frame.board.BoardEdit_PT;

public class DBPT {
	//허유진 
	//댓글테이블 수정
	
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet result = null;
	private int id;
	private BoardEdit_PT be2;
	private String cmt;
	
	public  DBPT(BoardEdit_PT be2) {
		this.be2 = be2;
		
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
		} catch (SQLException e) {
			System.out.println("예외발생 : 접속 정보 확인이 필요합니다.");
			e.printStackTrace();
		}
	}
	
	//허유진 DB insert 
	public void PtInsert(String pttitle, String ptwriteday, String pw, String ptcontent, String ptwriter, String id) {
		System.out.println("입력할 데이터 : ");
		//System.out.println(i);
		System.out.println(pttitle);
		System.out.println(ptwriteday);
		System.out.println(pw);
		System.out.println(ptcontent);
		System.out.println(ptwriter);
		
		
		try {
			String sqlInsert = "insert into PTTALK (PT_ID, PT_TITLE, PT_WRITEDAY, PT_WRITER, PASSWORD, PT_CONTENT, ID) "
					+ "values(PKID_SEQ.NEXTVAL, '" + pttitle + "', '" + ptwriteday + "', '" + ptwriter + "', '" + pw +"', '" + ptcontent+"', '" + id+"')";
			stmt.executeUpdate(sqlInsert);
			
			System.out.println("입력 성공");
			
		}catch(SQLException e){
			System.out.println("Insert fail");
			e.printStackTrace();
			
		}finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
			
	}
		
		//pt게시판 id DB에서 불러오기
		public int GetBDID() {
			try {
				result = stmt.executeQuery("select PT_ID from PTTALK");
				if(result.next()) {
					String max = String.valueOf(result.getInt(1));
					id = Integer.parseInt(max);
				}
			}catch(SQLException e) {
				System.out.println("select Query Error!");
				e.printStackTrace();
			}
			return id ;
		}
		
		//pt게시판 글 수정-BoardWirtept2
		public void BDUpdate(String id, String pttitle, String ptwriter, String ptwriteday, String pw, String ptcontent ) {
			try {
				String sqlUpdate = "update pttalk SET PT_TITLE = '" + pttitle +"', PT_WRITER = '" + ptwriter + "', PT_WRITEDAY = '" + ptwriteday + "', PASSWORD = '" + pw + "', PT_CONTENT = '" + ptcontent + "' where PT_ID = '" + Integer.parseInt(id) +"'";
				stmt.executeUpdate(sqlUpdate);
				
				System.out.println("수정 성공!");
				
			}catch(SQLException e){
				System.out.println("수정 실패");
				e.printStackTrace();
				
			}finally {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		}
		//pt게시판 댓글 추가-Boardedit_pt
		public void BDCMT(String id, String ptcontent, String namept) {
			
			try {
				Random rnd = new Random();
				//글번호의 최대값보다 큰 수부터 랜덤값 가져오게 바꾸자
				System.out.println(rnd.nextInt(99999 -10000 + 1) + 10000);
				System.out.println(id);
				String cmt = ptcontent;
				System.out.println(cmt);
			String sqlInsert = "insert into PT_COMMENT values('" + rnd.nextInt(99999 - 10000 + 1) + 10000 + "', '" + ptcontent + "', '" + id +  "', '"+namept+"')";
			stmt.executeUpdate(sqlInsert);
			
			System.out.println("댓글 추가 성공");
			
		}catch(SQLException e){
			System.out.println("댓글 추가 실패");
			e.printStackTrace();
			
		}finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
		//pt게시판 댓글 보여주기-Boardedit_pt
		public void DisplayCMT(String id) {
			Statement stmt = null;
			ResultSet result = null;
			try {
				stmt = conn.createStatement();
				
				result = stmt.executeQuery("select  \"CMPT_WRITER\", \"COMMENTPT\" FROM PT_COMMENT where PT_ID= '"+ Integer.parseInt(id)+"'");
				
				while(result.next()) {
					
					String[] cmt = {result.getString("CMPT_WRITER"), result.getString("COMMENTPT")};
					System.out.println(cmt[0]);
					System.out.println(cmt[1]);
					be2.getTacomment().append(cmt[0] + " : " + cmt[1] + "\n");
					System.out.println("댓글 보여주기 성공");
					
				}
			} catch (SQLException e) {
				System.out.println("댓글 못 불러옵니다.");
				//e.printStackTrace();
			}
			finally {
				try {
					result.close();
					conn.close();
					stmt.close();
				}catch (Exception e) {
					//e.printStackTrace();
				}
			}
			
		}
		
		// 게시물 삭제-Boardedit_pt
		public void PT_DELETE(String id ) {
			try {
				String sqldeletechild = " delete from PT_comment where PT_ID = '"+ Integer.parseInt(id) +"'";
				String sqldelete = " delete from PTTALK where PT_ID = '"+ Integer.parseInt(id) +"'";
				stmt.executeQuery(sqldeletechild);
				stmt.executeQuery(sqldelete);
				
				
				System.out.println("삭제 성공");
			} catch (SQLException e) {
				System.out.println("삭제 실패");
				e.printStackTrace();
			}finally {
				try {
					stmt.close();
					//result.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		public void boardPwCheck() {
			try {
				
			} catch (Exception e) {
			}
		}
		
		
		//Jtable에 모든 값을 보여줌-Board2_pt
		public void displayData(Board2_PT bdpt, String ID) {
			bdpt.getModel().setNumRows(0);//테이블 초기화 
			try {
				
				stmt = conn.createStatement();
				result = stmt.executeQuery("select * from PTTALK where ID = '" + ID +"' order by PT_ID desc");
				int count = 0;
				
				while(result.next()) {//"글번호", "제목", "작성자", "작성날짜", "내용"
					String[] imsi = {result.getString("PT_ID"), result.getString("PT_TITLE"), result.getString("PT_WRITER"), result.getString("PT_WRITEDAY"), result.getString("PT_CONTENT")};
				bdpt.getModel().addRow(imsi);
				count++;
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
//					if(conn != null)
//						conn.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		
		//검색하면 보여줌-Board2_pt
		public void scDisplay(Board2_PT dbpt, String src, String ID) {
		      dbpt.getModel().setNumRows(0);
		      try {
		        
		         stmt = conn.createStatement();
		         result = stmt.executeQuery("select * from pttalk where PT_TITLE LIKE '%' || '" +  src + "' || '%'  and ID = '" + ID +"' order by PT_ID desc");
		         
		         while(result.next()) {
		            String[] sch = {result.getString("PT_ID"), result.getString("PT_TITLE"), result.getString("PT_WRITER"), result.getString("PT_WRITEDAY"), result.getString("PT_CONTENT")};
		            dbpt.getModel().addRow(sch);
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
//		               if(conn != null)
//		                  conn.close();
		            }catch(Exception e) {
		               e.printStackTrace();
		            }
		         }
		   }
		
		//리프레시하기
		public void PtRefresh(Board2_PT bdpt, String ID) {
			int rowCount = bdpt.getModel().getRowCount();
			for (int i = rowCount -1; i >= 0; i--) {
				bdpt.getModel().removeRow(i);
			}
			try {
				stmt = conn.createStatement();
				result = stmt.executeQuery("select * from pttalk  where ID = '" + ID + "'  order by PT_ID desc");
				
				while (result.next()) {
					String[] imsi = {result.getString("PT_ID"), result.getString("PT_TITLE"), result.getString("PT_WRITER"), result.getString("PT_WRITEDAY"), result.getString("PT_CONTENT")};
					bdpt.getModel().addRow(imsi);
				}
			} catch (SQLException e) {
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
		
		//내글보기 - Board2_PT
		public void MyCommentPt(Board2_PT bdpt, String ID) {
			bdpt.getModel().setNumRows(0);
			try {
				stmt = conn.createStatement();
				result = stmt.executeQuery("select * from pttalk where ID = '"+ ID +"' order by PT_ID desc ");
				System.out.println("내글보기 출력");
				
				while(result.next()) {
					String[] imsi = {result.getString("PT_ID"), result.getString("PT_TITLE"), result.getString("PT_WRITER"), result.getString("PT_WRITEDAY"), result.getString("PT_CONTENT")};
					bdpt.getModel().addRow(imsi);
				}
				
			} catch (SQLException e) {
				System.out.println("내글보기 오류");
				e.printStackTrace();
			}
//			finally {
//				try {
//					if(result != null)
//						result.close();
//					if(stmt != null)
//						stmt.close();
//					//if(conn != null)
//						//conn.close();
//				}catch(Exception e) {
//					e.printStackTrace();
//				}
//			}
		}
		
}


