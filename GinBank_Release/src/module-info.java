module GinBank {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires java.sql;
	requires org.junit.jupiter.api;
	
	opens application; //to javafx.graphics, javafx.fxml;
}
