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

	private String[] columnName = {"��Ϲ�ȣ","ǰ��","����","ī�װ�","�����","����ó"};
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

	String[] btnTitle = { "���", "����", "����", "��ȸ", "����" };
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
		fillRowData(); //lists�� ���� �����͸� 2�����迭�� �ٲ��ִ� �޼���.

		table = new JTable(rowData,columnName);
		table.addMouseListener(new MouseHandler());

		scrollPane = new JScrollPane(table);
		contentPane = getContentPane();

		contentPane.add(scrollPane,BorderLayout.NORTH); // North
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		compose();

		setSize(900,700);
		setVisible(true);	


	}//������

	class MouseHandler extends MouseAdapter{ //����Ŭ���� �̿�
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


	private void compose() { //swing ȭ�� ����
		JLabel lblName = new JLabel("ǰ��");
		JLabel lblPrice = new JLabel("����");
		JLabel lblCategory = new JLabel("ī�װ�");
		JLabel lblPhone_no = new JLabel("����ó");
		JLabel lblDetail = new JLabel("���λ���");
		JLabel lblPW = new JLabel("��й�ȣ");
		JLabel lblP_no = new JLabel("��Ϲ�ȣ");


		JPanel pCenter = new JPanel(); //�г�
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

		JPanel pSouth = new JPanel(); //��ư �ø� �г�

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
			ProductBean product = (ProductBean)arr[i]; //�ٿ�ĳ����

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
		new Main("�߰� �Խ���");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		flag=false;
		Object obj = e.getSource();
		input_pw=1;

		if(obj == btn[0]) { //���
			insertData();
			clearData();

		}
		else if(obj == btn[1]) { //����
			password();
			pw_btn.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {

					Object obj = e.getSource();

					if(obj == pw_btn){ // �Է¹��� ��й�ȣ�� ������ ��й�ȣ ��ġ Ȯ��.
						input_pw = Integer.parseInt(txtInput_Pw.getText());	
						System.out.println(com_pw);
						System.out.println(input_pw);
						if(com_pw == input_pw) {
							pwFrame.dispose();
							updateData();

						}else {
							JOptionPane.showMessageDialog( txtInput_Pw, "��й�ȣ�� Ȯ���ϼ���","��й�ȣ ����",JOptionPane.ERROR_MESSAGE);
							txtInput_Pw.requestFocus();
						}

					}				
				}
			});

		}
		else if(obj == btn[2]) { //����
			password();
			pw_btn.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {

					Object obj = e.getSource();

					if(obj == pw_btn){ // �Է¹��� ��й�ȣ�� ������ ��й�ȣ ��ġ Ȯ��.
						input_pw = Integer.parseInt(txtInput_Pw.getText());	
						System.out.println(com_pw);
						System.out.println(input_pw);
						if(com_pw == input_pw) {
							deleteData();
							pwFrame.dispose();

						}else {
							JOptionPane.showMessageDialog( txtInput_Pw, "��й�ȣ�� Ȯ���ϼ���","��й�ȣ ����",JOptionPane.WARNING_MESSAGE);
							txtInput_Pw.requestFocus();
						}

					}				
				}
			});

		}
		else if(obj == btn[3]) { //��ȸ

			detailFrame();
		}

		else { //����
			System.exit(0);
		}//

	}//

	private void password() { // ��й�ȣ �Է�â

		pwFrame = new JFrame("��й�ȣ Ȯ��");
		Container pwFramecon = pwFrame.getContentPane();
		pwFrame.setLayout(null);

		lblinput_Pw = new JLabel("��й�ȣ");
		lblinput_Pw.setBounds(20,20,100,20);
		pwFramecon.add(lblinput_Pw);

		txtInput_Pw = new JTextField();
		txtInput_Pw.setBounds(150,20,100,20);
		pwFramecon.add(txtInput_Pw);

		pw_btn= new JButton("Ȯ��");
		pw_btn.setBounds(100, 50, 100, 20);
		pwFramecon.add(pw_btn);

		pwFrame.setSize(300,120);
		pwFrame.setVisible(true);		

	}

	private void detailFrame() { //��ȸ��ư ������ detail â
		JFrame detailFrame = new JFrame("����ȸ");
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

	private void deleteData() { //����
		int row = table.getSelectedRow();
		int p_no = Integer.parseInt(table.getValueAt(row, 0).toString());

		int cnt = dao.deleteData(p_no);

		clearData();
		getAllProducts();
	}

	private void updateData() { //����

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



	private void insertData() { //����

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
