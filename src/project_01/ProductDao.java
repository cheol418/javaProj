package project_01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDao {
	String driver="oracle.jdbc.driver.OracleDriver";
	String url="jdbc:oracle:thin:@localhost:1521:orcl";
	String id="jspid";
	String pw="jsppw";
	Connection conn = null;
	int select_pw=0;
	String detail;
	
	ProductDao(){
		//0
		//1 드라이버 로드
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 로드 실패");
		}

	}//  ProductDao 생성자
	
	public void getConnect(){
		//2 접속
		try {
			conn = DriverManager.getConnection(url, id, pw);

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("접속 실패");
		}
	}//getConnect()
	
	public ArrayList<ProductBean> getAllProducts(){
		getConnect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ProductBean>  lists = new ArrayList<ProductBean>();

		//3.
		String sql = "select * from product order by p_no asc";
		try {
			ps = conn.prepareStatement(sql);

			// 4.
			rs = ps.executeQuery();
			while(rs.next()) {
				int p_no = rs.getInt("p_no");
				String name = rs.getString("name");
				int price = rs.getInt("price");
				String category = rs.getString("category");
				String regit_date =String.valueOf(rs.getDate("regit_date"));
				int phone_no = rs.getInt("phone_no");
				//int pw = rs.getInt("pw");
				ProductBean pb = new ProductBean(p_no,name,price,category,regit_date,phone_no);
				lists.add(pb);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null)
					rs.close();
				if(ps!=null)
					ps.close();
				if(conn!=null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return lists;
	}//getAllProducts

	public int insertData(ProductBean pb) {
		getConnect();
		PreparedStatement ps = null;
		int count = 0;
		String sql = "insert into product values(prdseq.nextval,?,?,?,?,?,?,?)";
		//p_no , name, price , category , phone_no , detail , regit_date, pw
		
		try {
			ps = conn.prepareStatement(sql); 
			ps.setString(1, pb.getName());
			ps.setInt(2, pb.getPrice());
			ps.setString(3, pb.getCategory());
			ps.setInt(4, pb.getPhone_no());
			ps.setString(5, pb.getDetail());
			ps.setString(6, pb.getRegit_date());
			ps.setInt(7, pb.getPw());

			count = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(ps!=null)
					ps.close();
				if(conn!=null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
		
	}//insertdata

	
	public int get_password(Object p_no) {
		getConnect();
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "select pw from product where p_no = ?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, (int) p_no);
			rs = ps.executeQuery();
			
			if(rs.next())
			select_pw = rs.getInt("pw");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(ps!=null)
					ps.close();
				if(conn!=null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} 

		return select_pw;
	}

	public int updateData(ProductBean pb) {
		getConnect();
		PreparedStatement ps = null;
		int count=0;
		String sql="update product set name=?,price=?,category=?,phone_no=?,detail=? where p_no=?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1,pb.getName());
			ps.setInt(2, pb.getPrice());
			ps.setString(3, pb.getCategory());
			ps.setInt(4, pb.getPhone_no());
			ps.setString(5, pb.getDetail());
			ps.setInt(6, pb.getP_no());
			
			count = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(ps!=null)
					ps.close();
				if(conn!=null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return count;

	}

	public int deleteData(int p_no) {
		getConnect();
		PreparedStatement ps = null;
		int count=0;
		
		String sql = "delete from product where p_no=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1,p_no);
			
			count = ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(ps!=null)
					ps.close();
				if(conn!=null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	public String searchDetail(int p_no) {
		getConnect();
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "select detail from product where p_no = ?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1,  p_no);
			rs = ps.executeQuery();
			
			if(rs.next())
				detail = rs.getString("detail");
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps!=null)
					ps.close();
				if(conn!=null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} 

		return detail;
	}
}
