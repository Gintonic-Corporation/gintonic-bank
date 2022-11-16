package application;

public class Data {
	public Integer D_accID;
	public Integer D_balance;
	Data(int a,int b){
		D_accID=a;
		D_balance=b;
	}
	public Integer getD_accID() {
		return D_accID;
	}
	public void setD_accID(Integer d_accID) {
		D_accID = d_accID;
	}
	public Integer getD_balance() {
		return D_balance;
	}
	public void setD_balance(Integer d_balance) {
		D_balance = d_balance;
	}
}
