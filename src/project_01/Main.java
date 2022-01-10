package project_01;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class Main extends JFrame implements ActionListener{
	private ProductDao dao = null;

	private String[] columnName = {"등록번호","품명","가격","카테고리","등록일","연락처"};
	private Object[][] rowData = null;
	private JTable table = null;
	private JScrollPane scrollPane = null;
	ArrayList<ProductBean> lists = null;
	Container contentPane = null;
	LocalDateTime today = LocalDateTime.now();
	int p_no,select_pw=0,com_pw=0,input_pw=0,index;

	JTextField txtName;
	JTextField txtPrice;
	JTextField txtCategory;
	JTextField txtPhone_no;
	JTextField txtDetail;
	JTextField txtPW;
	JTextField txtP_no;

	String[] btnTitle = { "등록", "수정", "삭제", "조회", "종료" };
	private JButton[] btn = new JButton[btnTitle.length];

	JFrame pwFrame;
	JLabel lblinput_Pw;
	JButton pw_btn;
	JTextField txtInput_Pw;
	boolean flag=false;


	Main(String str){
		super(str);
		dao = new ProductDao();
		lists = dao.getAllProducts();

		rowData = new  Object[lists.size()][8];
		fillRowData(); //lists로 받은 데이터를 2차원배열로 바꿔주는 메서드.

		table = new JTable(rowData,columnName);
		table.addMouseListener(new MouseHandler());

		scrollPane = new JScrollPane(table);
		contentPane = getContentPane();

		contentPane.add(scrollPane,BorderLayout.NORTH); // North
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		compose();

		setSize(900,700);
		setVisible(true);	


	}//생성자

	class MouseHandler extends MouseAdapter{ //내부클래스 이용
		public void mouseClicked(MouseEvent e) {
			flag=false;
			int row = table.getSelectedRow();

			Object name = table.getValueAt(row, 1);
			Object price = table.getValueAt(row, 2);
			Object category = table.getValueAt(row, 3);
			Object phone_no = table.getValueAt(row, 5);

			Object p_no = table.getValueAt(row, 0);
			Object detail = table.getValueAt(row, 4);

			txtP_no.setText(p_no.toString());
			txtName.setText(name.toString());
			txtPrice.setText(price.toString());
			txtCategory.setText(category.toString());
			txtPhone_no.setText(phone_no.toString());

			com_pw= dao.get_password(p_no);
		}

	}//class


	private void compose() { //swing 화면 구성
		JLabel lblName = new JLabel("품명");
		JLabel lblPrice = new JLabel("가격");
		JLabel lblCategory = new JLabel("카테고리");
		JLabel lblPhone_no = new JLabel("연락처");
		JLabel lblDetail = new JLabel("세부사항");
		JLabel lblPW = new JLabel("비밀번호");
		JLabel lblP_no = new JLabel("등록번호");


		JPanel pCenter = new JPanel(); //패널
		pCenter.setLayout(null);

		pCenter.add(lblName);
		pCenter.add(lblPrice);
		pCenter.add(lblCategory);
		pCenter.add(lblPhone_no);
		pCenter.add(lblDetail);
		pCenter.add(lblPW);
		pCenter.add(lblP_no);

		lblName.setBounds(50, 20,100,20);
		lblPrice.setBounds(250, 20,100,20);
		lblCategory.setBounds(450, 20,100,20);
		lblPhone_no.setBounds(675, 20,100,20);
		lblDetail.setBounds(50, 50,100,20);
		lblPW.setBounds(450, 50,100,20);
		lblP_no.setBounds(450, 80,100,20);


		txtName = new JTextField();
		txtPrice = new JTextField();
		txtCategory = new JTextField();
		txtPhone_no = new JTextField();
		txtDetail = new JTextField();
		txtPW = new JTextField();
		txtP_no = new JTextField();

		txtName.setBounds(100,20,100,20);
		txtPrice.setBounds(300,20,100,20);
		txtCategory.setBounds(525,20,100,20);
		txtPhone_no.setBounds(725,20,100,20);
		txtDetail.setBounds(110,50,300,100);
		txtPW.setBounds(525,50,100,20);
		txtP_no.setBounds(525,80,100,20);

		pCenter.add(txtName);
		pCenter.add(txtPrice);
		pCenter.add(txtCategory);
		pCenter.add(txtPhone_no);
		pCenter.add(txtDetail);
		pCenter.add(txtPW);
		pCenter.add(txtP_no);

		contentPane.add("Center",pCenter);
		//label, txt

		JPanel pSouth = new JPanel(); //버튼 올릴 패널

		for(int i=0;i<btnTitle.length;i++) {
			btn[i] = new JButton(btnTitle[i]);
			btn[i].addActionListener(this); // new ActionEvent()
			pSouth.add(btn[i]);
		}

		contentPane.add("South",pSouth);
		pSouth.setLayout(new FlowLayout(50,100,10));

	}


	private void fillRowData() { // ArrayList => [][] 
		Object[] arr = lists.toArray();

		int cnt = 0;
		for(int i=0;i<arr.length;i++) {			
			ProductBean product = (ProductBean)arr[i]; //다운캐스팅

			rowData[i][cnt++] = product.getP_no(); 
			rowData[i][cnt++] = product.getName();
			rowData[i][cnt++] = product.getPrice();
			rowData[i][cnt++] = product.getCategory();
			rowData[i][cnt++] = product.getRegit_date();
			rowData[i][cnt++] = product.getPhone_no();
			cnt = 0;
		}
	}
	public static void main(String[] args) {
		new Main("중고 게시판");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		flag=false;
		Object obj = e.getSource();
		input_pw=1;

		if(obj == btn[0]) { //등록
			insertData();
			clearData();

		}
		else if(obj == btn[1]) { //수정
			password();
			pw_btn.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {

					Object obj = e.getSource();

					if(obj == pw_btn){ // 입력받은 비밀번호와 선택한 비밀번호 일치 확인.
						input_pw = Integer.parseInt(txtInput_Pw.getText());	
						System.out.println(com_pw);
						System.out.println(input_pw);
						if(com_pw == input_pw) {
							pwFrame.dispose();
							updateData();

						}else {
							JOptionPane.showMessageDialog( txtInput_Pw, "비밀번호를 확인하세요","비밀번호 오휴",JOptionPane.ERROR_MESSAGE);
							txtInput_Pw.requestFocus();
						}

					}				
				}
			});

		}
		else if(obj == btn[2]) { //삭제
			password();
			pw_btn.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {

					Object obj = e.getSource();

					if(obj == pw_btn){ // 입력받은 비밀번호와 선택한 비밀번호 일치 확인.
						input_pw = Integer.parseInt(txtInput_Pw.getText());	
						System.out.println(com_pw);
						System.out.println(input_pw);
						if(com_pw == input_pw) {
							deleteData();
							pwFrame.dispose();

						}else {
							JOptionPane.showMessageDialog( txtInput_Pw, "비밀번호를 확인하세요","비밀번호 오휴",JOptionPane.WARNING_MESSAGE);
							txtInput_Pw.requestFocus();
						}

					}				
				}
			});

		}
		else if(obj == btn[3]) { //조회

			detailFrame();
		}

		else { //종료
			System.exit(0);
		}//

	}//

	private void password() { // 비밀번호 입력창

		pwFrame = new JFrame("비밀번호 확인");
		Container pwFramecon = pwFrame.getContentPane();
		pwFrame.setLayout(null);

		lblinput_Pw = new JLabel("비밀번호");
		lblinput_Pw.setBounds(20,20,100,20);
		pwFramecon.add(lblinput_Pw);

		txtInput_Pw = new JTextField();
		txtInput_Pw.setBounds(150,20,100,20);
		pwFramecon.add(txtInput_Pw);

		pw_btn= new JButton("확인");
		pw_btn.setBounds(100, 50, 100, 20);
		pwFramecon.add(pw_btn);

		pwFrame.setSize(300,120);
		pwFrame.setVisible(true);		

	}

	private void detailFrame() { //조회버튼 누를시 detail 창
		JFrame detailFrame = new JFrame("상세조회");
		Container detailJFramecontentPane = detailFrame.getContentPane();
		detailJFramecontentPane.setLayout(null);

		p_no=Integer.parseInt(txtP_no.getText());

		String detail = dao.searchDetail(p_no);

		JLabel lblDetail = new JLabel();
		lblDetail.setText(detail.toString());

		lblDetail.setBounds(20,20, 250,20);

		detailJFramecontentPane.add(lblDetail);
		detailFrame.setSize(300,500);
		detailFrame.setVisible(true);	
	}

	private void deleteData() { //삭제
		int row = table.getSelectedRow();
		int p_no = Integer.parseInt(table.getValueAt(row, 0).toString());

		int cnt = dao.deleteData(p_no);

		clearData();
		getAllProducts();
	}

	private void updateData() { //수정

		int p_no =Integer.parseInt(txtP_no.getText());
		String name = txtName.getText();
		int price=  Integer.parseInt(txtPrice.getText());
		String category = txtCategory.getText();
		int phone_no=  Integer.parseInt(txtPhone_no.getText());
		String detail = txtDetail.getText();

		ProductBean pb = new ProductBean(p_no,name,price,category,phone_no,detail);

		int count = dao.updateData(pb);

		clearData();
		getAllProducts();
	}



	private void insertData() { //삽입

		p_no=0;
		String name = txtName.getText();
		int price=  Integer.parseInt(txtPrice.getText());
		String category = txtCategory.getText();
		int phone_no=  Integer.parseInt(txtPhone_no.getText());
		String detail = txtDetail.getText();
		//id
		String regit_date=String.valueOf(LocalDate.now());
		int pw = Integer.parseInt(txtPW.getText());

		ProductBean pb = new ProductBean(0,name,price,category,phone_no,detail,regit_date,pw);
		int cnt = dao.insertData(pb);

		clearData();
		getAllProducts(); 

	}

	private void getAllProducts() {
		lists=dao.getAllProducts();
		rowData = new  Object[lists.size()][6];

		fillRowData();

		table = new JTable(rowData,columnName);
		table.addMouseListener(new MouseHandler());

		scrollPane.remove(table);
		table.revalidate();
		table.repaint();
		scrollPane.setViewportView(table);

	}

	private void clearData() {
		txtName.setText("");
		txtPrice.setText("");
		txtCategory.setText("");
		txtPhone_no.setText("");
		txtDetail.setText("");
		txtPW.setText("");
	}

}
