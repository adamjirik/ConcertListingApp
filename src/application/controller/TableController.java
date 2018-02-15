package application.controller;


import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.database.DBConnector;
import application.model.Concert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

	public class TableController {

		private DBConnector dbconnector;
		
		private ObservableList<Concert> data = FXCollections.observableArrayList();
		   @FXML
		    private TableView<Concert> table_concerts;

		    @FXML
		    private TableColumn<Concert, Integer> col_id;

		    @FXML
		    private TableColumn<Concert, String> col_artist;

		    @FXML
		    private TableColumn<Concert, String> col_supporting;

		    @FXML
		    private TableColumn<Concert, String> col_venue;

		    @FXML
		    private TableColumn<Concert, String> col_city;

		    @FXML
		    private TableColumn<Concert, Double> col_cost;

		    @FXML
		    private TableColumn<Concert, Date> col_date;

		    @FXML
		    private TableColumn<Concert, String> col_genre;
		
		
	    @FXML
	    private Menu menu_file;

	    @FXML
	    private MenuItem menu_save;

	    @FXML
	    private MenuItem menu_viewSaved;

	    @FXML
	    private MenuItem menu_logout;

	    @FXML
	    private MenuItem menu_close;

	    @FXML
	    private Menu menu_help;

	    @FXML
	    private MenuItem menu_instructions;

	    @FXML
	    private TextField tf_artistFilter;

	    @FXML
	    private TextField tf_venueFilter;

	    @FXML
	    private TextField tf_cityFilter;

	    @FXML
	    private Slider s_minCost;

	    @FXML
	    private Slider s_maxCost;

	    @FXML
	    private DatePicker dp_startDate;

	    @FXML
	    private DatePicker dp_endDate;

	    @FXML
	    private ComboBox<?> cb_genreFilter;

	    @FXML
	    private Button btn_clear;

	    @FXML
	    private Button btn_filter;

	    public void initialize() throws SQLException {
	    	dbconnector = new DBConnector();
	    	Connection connection = null;
	    	try {
	        	connection = dbconnector.connection();
	        	Statement stmt = dbconnector.connection().createStatement();
	        	ResultSet rs = stmt.executeQuery("SELECT * from concerts;");
	        	setTableValues(rs);
	        	
	    	} catch (SQLException e) {
	    		e.printStackTrace();
	    	}
	    	finally {
	    	if (connection != null) {
	    		connection.close();
	    	}
	    	
	    	
	    }
	    }
	    
		private void setTableValues(ResultSet rs) throws SQLException {
			data.clear();
			while (rs.next()) {
				data.add(new Concert(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), 
						rs.getString(5), rs.getDouble(6), rs.getDate(7), rs.getString(8)));
			}
			
			table_concerts.setItems(null);
			table_concerts.setItems(data);
		}
	    
	    @FXML
	    void closeApp(ActionEvent event) {

	    }

	    @FXML
	    void filterClear(MouseEvent event) {

	    }

	    @FXML
	    void filterResults(MouseEvent event) {

	    }

	    @FXML
	    void logout(ActionEvent event) {

	    }

	    @FXML
	    void menuSave(ActionEvent event) {

	    }

	    @FXML
	    void viewSaved(ActionEvent event) {

	    }

	}

	
