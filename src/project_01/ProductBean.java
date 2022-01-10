package project_01;

public class ProductBean {
	private int p_no; //등록번호, PK
	private String name; //품명
	private int price; //가격
	private String category; //카테고리
	private int phone_no; //연락처
	private String detail; //상세설명
	private String regit_date; //등록일
	private int pw; //pw
	
	
	public ProductBean(int p_no, String name, int price, String category, int phone_no, String detail,
			String regit_date, int pw) {
		super();
		this.p_no = p_no;
		this.name = name;
		this.price = price;
		this.category = category;
		this.phone_no = phone_no;
		this.detail = detail;
		this.regit_date = regit_date;
		this.pw = pw;
	}
	public ProductBean(int p_no2, String name2, int price2, String category2, String regit_date2, int phone_no2) {
		this.p_no = p_no2;
		this.name = name2;
		this.price = price2;
		this.category = category2;
		this.regit_date = regit_date2;
		this.phone_no = phone_no2;		
	}
	
	public ProductBean(int p_no2,String name2, int price2,String category2, int phone_no2, String detail2) {
		this.p_no = p_no2;
		this.name = name2;
		this.price = price2;
		this.category = category2;
		this.phone_no = phone_no2;		
		this.detail = detail2;

	}
	public int getP_no() {
		return p_no;
	}
	public void setP_no(int p_no) {
		this.p_no = p_no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	@Override
	public String toString() {
		return "ProductBean [p_no=" + p_no + ", name=" + name + ", price=" + price + ", category=" + category
				+ ", phone_no=" + phone_no + ", regit_date=" + regit_date + "]";
	}
	public int getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(int phone_no) {
		this.phone_no = phone_no;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getRegit_date() {
		return regit_date;
	}
	public void setRegit_date(String regit_date) {
		this.regit_date = regit_date;
	}
	public int getPw() {
		return pw;
	}
	public void setId(int pw) {
		this.pw = pw;
	}
	
}
