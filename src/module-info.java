module calc {
	requires javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.graphics;
	requires transitive javafx.base;
	requires jdk.jshell;
	
	opens org.openjfx to javafx.fxml;
	exports org.openjfx;
}