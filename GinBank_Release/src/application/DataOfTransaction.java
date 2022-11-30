package application;
/**
 * Tranzakciók adatait tárolja. Táblafeltöltéshez szükséges
 * @author Hujber Ádám
 *
 */
public class DataOfTransaction {
	int sender,receiver,amount;
	String date;
	public DataOfTransaction(int sender, int receiver, int amount, String date) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.amount = amount;
		this.date = date;
	}
	public int getSender() {
		return sender;
	}
	public void setSender(int sender) {
		this.sender = sender;
	}
	public int getReceiver() {
		return receiver;
	}
	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}
