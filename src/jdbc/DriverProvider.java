package jdbc;

import java.sql.Connection;

public class DriverProvider {

	public void getDriver() {
	try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
		System.out.println("����̹� �ε� ����");
		}
	}
}