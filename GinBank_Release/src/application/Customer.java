package application;
/**
 * Ügyfelek adatait tartalmazó típus. Táblázatok miatt szükséges
 */
public class Customer {
	public Integer id;
	public String name,bdate, bplace, mother, haddress, email, phone;
	public Customer(int id, String name, String bdate, String bplace, String mother, String haddress, String email,
			String phone) {
		super();
		this.id = id;
		this.name = name;
		this.bdate = bdate;
		this.bplace = bplace;
		this.mother = mother;
		this.haddress = haddress;
		this.email = email;
		this.phone = phone;
	}
	public Integer getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBdate() {
		return bdate;
	}
	public void setBdate(String bdate) {
		this.bdate = bdate;
	}
	public String getBplace() {
		return bplace;
	}
	public void setBplace(String bplace) {
		this.bplace = bplace;
	}
	public String getMother() {
		return mother;
	}
	public void setMother(String mother) {
		this.mother = mother;
	}
	public String getHaddress() {
		return haddress;
	}
	public void setHaddress(String haddress) {
		this.haddress = haddress;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	
}
