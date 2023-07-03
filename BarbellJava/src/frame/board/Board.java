package frame.board;
// 자유게시판 디자인 : 허유진, 기능 : 윤선호 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.Member;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.CellEditorListener;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import frame.db.DB;
import frame.main.MainFrame;

public class Board extends JFrame implements ActionListener, MouseListener, WindowListener, ItemListener{
	private String[] title = {"글번호", "제목", "작성자", "작성날짜", "내용", "분류"};
	private String[][]datas = new String[0][5];
	private DefaultTableModel model = new DefaultTableModel(datas, title);
	private JTable table = new JTable(model);
	private JPanel panelUP,panelUPUP , panelUPDown,bdpanel;
	private JLabel lblTilte;
	private JTextField tfsearch;
	private JButton btnsearch, btnWrite;
	private Color skyblue;
	private ArrayList<String> al;
	private TableModel data;
	private String pre_i, id , name;
	private DB db = new DB(null, null);
	private JScrollPane ScrollPane;
	private JCheckBox my_board;
	private Color Blue;
	private MainFrame mf;
	private BoardEdit be;
	private Boardwrite be2;

	public DefaultTableModel getModel() {
		return model;
	}

	public JTable getTable() {
		return table;
	}

	public Board(String id, String name) {
		this.id = id;
		this.name = name;
		skyblue = new Color(189, 215, 238);
		
		setTitle("자유게시판");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(300, 300);
		setSize(600, 560);
		setResizable(false); // 사이즈 조절 못하게함
		setLayout(new BorderLayout());
		
		addWindowListener(this);
		PanelUP();
		PanelDowm();
		
		db.displayData(this);
		setVisible(true);
	}

	private void PanelUP() {
		panelUP = new JPanel();
		panelUP.setLayout(new GridLayout(2,1));
		
		panelUPUP = new JPanel();
		panelUPUP.setBackground(skyblue);
		panelUPUP.setLayout(null);
		panelUPUP.setPreferredSize(new DimensionUIResource(100,50));
		lblTilte = new JLabel("자유게시판");
		lblTilte.setFont(new Font("210 맨발의청춘 L", Font.BOLD, 25));
		lblTilte.setLocation(20, 20);
		lblTilte.setSize(150, 30);
		panelUPUP.add(lblTilte);
		
		
		//글쓰기 버튼 출력
		btnWrite = new JButton(new ImageIcon("imges/pencil1.png"));
		btnWrite.setBorderPainted(false);
		btnWrite.setContentAreaFilled(false);
		btnWrite.setFocusPainted(false);
		btnWrite.setLocation(490,20);
		btnWrite.setSize(130, 30);
		
		btnWrite.addActionListener(this);
		panelUPUP.add(btnWrite);
		
		panelUP.add(panelUPUP);
		
		panelUPDown = new JPanel();
		panelUPDown.setLayout(null);
		panelUPDown.setPreferredSize(new DimensionUIResource(100,50));
		panelUPDown.setBackground(skyblue);
		
		my_board = new JCheckBox("내 게시물", false);
		my_board.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 12));
		my_board.setBounds(15, 9, 80, 30);
		my_board.setBackground(skyblue);
		my_board.addItemListener(this);
		panelUPDown.add(my_board);
		
		//검색 텍스트 필드 출력
		tfsearch = new JTextField("내용을 검색하세요.",20);
		tfsearch.setBounds(105, 13, 400, 20);
		tfsearch.setBorder(BorderFactory.createEmptyBorder());
		tfsearch.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 12));
		tfsearch.addMouseListener(this);
		tfsearch.addActionListener(this);
		
		ImageIcon imgtfsearch = new ImageIcon("imges/tfsearch.png");
		JLabel lbltfsearch = new JLabel(imgtfsearch);
		lbltfsearch.setBounds(100,8, 410, 30);
		
		panelUPDown.add(tfsearch);
		panelUPDown.add(lbltfsearch);
		
		
		//검색 버튼 출력
		btnsearch = new JButton(new ImageIcon("imges/btnsearch2.png"));
		btnsearch.setBounds(510, 10, 70, 30);
		btnsearch.setBorderPainted(false);
		btnsearch.setContentAreaFilled(false);
		btnsearch.setFocusPainted(false);
		btnsearch.addActionListener(this);
		
		panelUPDown.add(btnsearch);
		panelUP.add(panelUPDown);
		
		add(panelUP,BorderLayout.NORTH);
	}

	private void PanelDowm() {
		bdpanel = new JPanel();
		
		//테이블 크기 조절
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(1).setPreferredWidth(80);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setPreferredWidth(80);
		table.getColumnModel().getColumn(4).setPreferredWidth(120);
		table.getColumnModel().getColumn(5).setPreferredWidth(50);
		
		//table.setEnabled(true);
		Blue = new Color(70, 96, 147);
		ScrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
												JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 단일 선택
		table.addMouseListener(this);
		
		//2022-06-12 윤선호 테이블 색상변경
		table.getTableHeader().setOpaque(false);
		table.getTableHeader().setBackground(Blue);
		table.getTableHeader().setForeground(Color.WHITE);
		table.getTableHeader().setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 13));
		
		table.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 12));
		//테이블 행간 조절
		table.setRowHeight(25);
		ScrollPane.setBackground(skyblue);
		bdpanel.add(ScrollPane);
		
		//테이블 가운데 정렬
		DefaultTableCellRenderer dtc = new DefaultTableCellRenderer();
		dtc.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		//테이블의 columnmodel을 가져옴
		TableColumnModel tcmSchedule = table.getColumnModel();
		
		//테이블을 가운데 정렬로 지정
		for(int i = 0; i < tcmSchedule.getColumnCount(); i++) {
			tcmSchedule.getColumn(i).setCellRenderer(dtc);
		}
		
		add(bdpanel, BorderLayout.CENTER);
		add(ScrollPane, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		//글쓰기 버튼 누를 시 자유게시판 글쓰기 창이 뜬다.
		if(obj == btnWrite) {
		be2 = new Boardwrite("글쓰기", id, name, this);
		be2.setLocationRelativeTo(this);
		
		//게시물 검색 기능
		}else if(obj == tfsearch || obj == btnsearch) {
		String src = tfsearch.getText();
		System.out.println(src);
		
		db.scDisplay(this, src);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	//2022-05-26 201945012 윤선호 게시물 클릭시 게시물 내용 보여줌
	@Override
	public void mousePressed(MouseEvent e) {
		Object obj =e.getSource();
		if (obj == tfsearch) {
			tfsearch.setText("");
		}else {
		RunBoard();
		}
	}

	private void RunBoard() {
		//게시물 누르면 이거 다 긁어옴
		//BoardEdit를 실행
		int row = table.getSelectedRow();
		int col = table.getSelectedColumn();
		
		data = table.getModel();
		
		pre_i = (String)data.getValueAt(row, 0);
		
		String pre_title = (String)data.getValueAt(row, 1);
		String pre_writer = (String)data.getValueAt(row, 2);
		String pre_writeday = (String)data.getValueAt(row, 3);
		String pre_category = (String)data.getValueAt(row, 5);
		String pre_content = (String)data.getValueAt(row, 4);
		
		al = new ArrayList<>();
		al.add(pre_i);
		al.add(pre_title);
		al.add(pre_writer);
		al.add(pre_writeday);
		al.add(pre_category);
		al.add(pre_content);
		
		be = new BoardEdit(al, id, name, this);
		be.setLocationRelativeTo(this);
		
		DB db = new DB(be, null);
		db.DisplayCMT(pre_i);
		//this.dispose();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public String getPre_i() {
		return pre_i;
	}

	public ArrayList<String> getAl() {
		return al;
	}

	//2022-05-28 윤선호 자유게시판 창 뜨면 검색필드 활성화
	@Override
	public void windowOpened(WindowEvent e) {
		tfsearch.requestFocus();
	}

	@Override
	public void windowClosing(WindowEvent e) {
		mf = new MainFrame(id,name, null);
		mf.setLocationRelativeTo(this);
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		//0609 윤선호 체크박스 선택하면 내글만 보임
		if(e.getStateChange() == ItemEvent.SELECTED) {
			//체크되면 내 글만 보이기
			db.MyBoard(this, id);
		}else if(e.getStateChange() == ItemEvent.DESELECTED) {
			db.displayData(this);
		}
	}
}
