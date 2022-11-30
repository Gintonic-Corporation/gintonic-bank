package application;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Tester {
	static DatabaseConnection con;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		con = new DatabaseConnection();
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void insertionIntoCustomers() {
		try {
			con.insertIntoDatabase("insert into customers values('Nagy Kelemen','1995-05-30','Szombathely','Nagy Rozalia', '9700 Szombathely, Petõfi Sándor utca 15','heheh@gmail.com','06302384545')");
			con.deletRow("delete from customers where ID=(select max(ID) from customers)");
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	void insertionIntoUsersAdmin() {
		try {
			con.insertIntoDatabase("insert into users values('Test','TestPW','Test Admin',1)");
			con.deletRow("delete from users where username = 'Test'");
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
		
	}
	@Test
	void insertionIntoUsers() {
		try {
			con.insertIntoDatabase("insert into users values('Test','TestPW','Test Admin',0)");
			con.deletRow("delete from users where username = 'Test'");
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
		
	}
	@Test
	void insertionIntoAccounts() {
		try {
			con.insertIntoDatabase("insert into account(owner) values(10005)");
			con.deletRow("delete from account where accountID=(select max(accountID) from account)");
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
	}
	@Test
	void insertionIntoTransactions() {
		try {
			con.insertIntoDatabase("insert into transactions values(1000,1281,1235,'2022-11-25')");
			con.deletRow("delete from transactions where ID=(select max(ID) from transactions)");
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
		
	}
	@Test
	void updateExistingClient() {
		try {
			con.updateDatabase("update customers set realname='Kiss István' where ID=10003");
			con.updateDatabase("update customers set realname='Nagy István' where ID=10003");
			
			con.updateDatabase("update customers set birthplace='Debrecen' where ID=10001");
			con.updateDatabase("update customers set birthplace='Szombathely' where ID=10001");
			
			con.updateDatabase("update customers set birthdate='2005-06-12' where ID=10001");
			con.updateDatabase("update customers set birthdate='1992-12-06' where ID=10001");
			
			con.updateDatabase("update customers set mother='Farkas Rozália' where ID=10001");
			con.updateDatabase("update customers set mother='Németh Margit' where ID=10001");
			
			con.updateDatabase("update customers set homeaddress='Budapest IX. ker' where ID=10001");
			con.updateDatabase("update customers set homeaddress='Szhely valamelyik utca' where ID=10001");
			
			con.updateDatabase("update customers set email='alma@gmail.com' where ID=10001");
			con.updateDatabase("update customers set email='kkelemen@gmail.com' where ID=10001");
			
			con.updateDatabase("update customers set phone='06302587451' where ID=10001");
			con.updateDatabase("update customers set phone='06201593215' where ID=10001");
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
		
	}
	@Test
	void checkClientConstrainst() {
		try {
			con.updateDatabase("update customers set email='pista.nagy@gmail.com' where ID=10001");
			fail();
		} catch (SQLException e) {
			
		}
		try {
			con.updateDatabase("update customers set phone='06302584587' where ID=10001");
			fail();
		} catch (SQLException e) {
			
		}
	}
	@Test
	void checkUsersConstrainst() {
		try {
			con.updateDatabase("insert into users values('HPeter','TestPW','Test User',0)");
			fail();
		} catch (SQLException e) {
			
		}
		try {
			con.updateDatabase("update users set username='NMark' where username='KZsuzsa'");
			fail();
		} catch (SQLException e) {
			
		}
	}
	@Test
	void checkAccountConstrainst() {
		try {
			con.updateDatabase("insert into account values(-500,10005)");
			fail();
		} catch (SQLException e) {
			
		}
		try {
			con.updateDatabase("update account set balance=balance-5000 where accountid=1235");
			fail();
		} catch (SQLException e) {
			
		}
	}
	@Test
	void checkTransactionConstrainst() {
		try {
			con.updateDatabase("insert into transactions values(0,1281,1235,'2022-11-25')");
			fail();
		} catch (SQLException e) {
			
		}
		try {
			con.updateDatabase("insert into transactions values(1000,1281,2,'2022-11-25')");
			fail();
		} catch (SQLException e) {
			
		}
	}
	@Test
	void checkClientQuery() {
		ResultSet rs=con.doQuery("select * from customers");
		try {
			rs.next();
			if(!rs.getString(2).equals("Kiss Kelemen")) fail();
			if(!rs.getString(7).equals("kkelemen@gmail.com")) fail();
			rs.next();
			if(!rs.getString(4).equals("Sárvár")) fail();
		} catch (SQLException e) {
			fail();
		}
	}
	@Test
	void checkUsersQuery() {
		ResultSet rs=con.doQuery("select * from users");
		try {
			rs.next();
			if(!rs.getString(1).equals("HPeter")) fail();
			if(!rs.getString(2).equals("b23b1cd3872099dde6063537cca7b632")) fail();
			rs.next();
			if(!rs.getString(1).equals("KPal")) fail();
			if(!(rs.getByte(4)==1)) fail();
			rs.next();
			if(!(rs.getByte(4)==0)) fail();
		} catch (SQLException e) {
			fail();
		}
	}
	@Test
	void checkAccountQuery() {
		ResultSet rs=con.doQuery("select * from account");
		try {
			rs.next();
			if(!(rs.getInt(1)==1120)) fail();
			if(!(rs.getInt(2)==12000)) fail();
			if(!(rs.getByte(4)==0)) fail();
			rs.next();
			if(!(rs.getInt(2)==0)) fail();
			rs.next();
			if(!(rs.getInt(1)==1235)) fail();
			if(!(rs.getInt(3)==10002)) fail();
		} catch (SQLException e) {
			fail();
		}
	}
	@Test
	void checkTransactionsQuery() {
		ResultSet rs=con.doQuery("select * from transactions");
		try {
			rs.next();
			if(!(rs.getInt(2)==12000)) fail();
			if(!(rs.getInt(4)==1350)) fail();
			rs.next();
			if(!(rs.getInt(4)==1281)) fail();
			rs.next();
			if(!(rs.getInt(1)==12110)) fail();
			if(!(rs.getInt(4)==1281)) fail();
		} catch (SQLException e) {
			fail();
		}
	}
}
