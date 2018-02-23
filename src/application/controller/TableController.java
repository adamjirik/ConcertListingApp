package application.controller;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
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
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class TableController {


	public void setUserLabel(String user) {
		lbl_user.setText(user);
	}

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
	private Label lbl_minVal;

	@FXML
	private Label lbl_maxVal;

	@FXML
	private Label lbl_user;

	@FXML
	private ComboBox<String> cb_genreFilter;

	private ObservableList<String> genreOptions = FXCollections.observableArrayList();;

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
			ResultSet rsGenres = stmt.executeQuery("SELECT distinct genre from concerts;");
			while(rsGenres.next()) {
				genreOptions.add(rsGenres.getString(1));
			}
			cb_genreFilter.setItems(genreOptions);
			cb_genreFilter.setValue("genre");


		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if (connection != null) {
				connection.close();
			}
			setCellValues();

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

	private void setCellValues() {
		col_id.setCellValueFactory(new PropertyValueFactory<Concert, Integer>("id"));
		col_artist.setCellValueFactory(new PropertyValueFactory<Concert, String>("artist"));
		col_supporting.setCellValueFactory(new PropertyValueFactory<Concert, String>("supporting"));
		col_venue.setCellValueFactory(new PropertyValueFactory<Concert, String>("venue"));
		col_city.setCellValueFactory(new PropertyValueFactory<Concert, String>("city"));
		col_cost.setCellValueFactory(new PropertyValueFactory<Concert, Double>("cost"));
		col_date.setCellValueFactory(new PropertyValueFactory<Concert, Date>("date"));
		col_genre.setCellValueFactory(new PropertyValueFactory<Concert, String>("genre"));
	}

	@FXML
	void closeApp(ActionEvent event) {
		System.exit(0);


	}

	@FXML
	void filterClear(MouseEvent event) throws SQLException {
		lbl_maxVal.setText("");
		lbl_minVal.setText("");
		tf_artistFilter.clear();
		tf_cityFilter.clear();
		tf_venueFilter.clear();
		s_maxCost.setValue(0.0);
		s_minCost.setValue(0.0);
		dp_startDate.setValue(null);
		dp_endDate.setValue(null);
		cb_genreFilter.setValue("genre");

		dbconnector = new DBConnector();
		Connection connection = null;
		try {
			connection = dbconnector.connection();
			Statement stmt = dbconnector.connection().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * from concerts;");
			setTableValues(rs);
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(connection != null) {
				connection.close();
			}
		}
	}

	@FXML
	void filterResults(MouseEvent event) {
		Connection connection = null;
		boolean firstFilter = false;
		try {
			connection = dbconnector.connection();
			StringBuilder sql = new StringBuilder("SELECT * FROM concerts.concerts");
			if(isAnyfilter()) {
				sql.append(" WHERE");
				if(isArtist()) {
					sql.append(" artist like ? or supporting like ?");
					firstFilter = true;
				}
				if(isVenue()) {
					if(firstFilter) {
						sql.append(" AND");
					}
					sql.append(" venue like ?");
					firstFilter = true;
				}
				if(isCity()) {
					if(firstFilter) {
						sql.append(" AND");
					}
					sql.append(" city like ?");
					firstFilter = true;

				}


				if(isMinCost() || isMaxCost()) {
					if(firstFilter) {
						sql.append(" AND");
					}
					if(minAndMax()) {
						sql.append(" cost between ? and ?");
					} else if(onlyMin()) {
						sql.append(" cost > ?");
					} else if (onlyMax()) {
						sql.append(" cost < ?");
					}
					firstFilter = true;
				}

				if(isStartDate() || isEndDate()) {
					if(firstFilter) {
						sql.append(" AND");
					}
					if(startAndEnd()) {
						sql.append(" date between ? and ?");
					} else if (onlyStartDate()) {
						sql.append(" date > ?");
					} else if (onlyEndDate()) {
						sql.append(" date < ?");
					}
					firstFilter = true;

				}

				if(isGenre()) {
					if(firstFilter) {
						sql.append(" AND");
					}
					sql.append(" genre like ?");
				}
			}
			System.out.println(sql.toString());
			PreparedStatement ps = connection.prepareStatement(sql.toString());

			System.out.println(ps.toString());
			if(isAnyfilter()) {
				int i = 1;
				if(isArtist()) {
					ps.setString(i, "%" + tf_artistFilter.getText() + "%");
					ps.setString((i+1), "%" + tf_artistFilter.getText() + "%");
					i+=2;
				}
				if(isVenue()) {
					ps.setString(i, "%" + tf_venueFilter.getText() + "%");
					i++;
				}
				if(isCity()) {
					ps.setString(i, "%" + tf_cityFilter.getText() + "%");
					i++;
				}


				if(isMinCost() || isMaxCost()) {
					if(minAndMax()) {
						ps.setDouble(i, s_minCost.getValue());
						ps.setDouble((i+1), s_maxCost.getValue());
						i+=2;
					} else if(onlyMin()) {
						ps.setDouble(i, s_minCost.getValue());
						i++;
					} else if (onlyMax()) {
						ps.setDouble(i, s_maxCost.getValue());
						i++;
					}
					firstFilter = true;
				}

				if(isStartDate() || isEndDate()) {

					if(startAndEnd()) {
						ps.setDate(i, Date.valueOf(dp_startDate.getValue()));
						ps.setDate((i+1), Date.valueOf(dp_endDate.getValue()));
						i+=2;
					} else if (onlyStartDate()) {
						ps.setDate(i, Date.valueOf(dp_startDate.getValue()));
						i++;
					} else if (onlyEndDate()) {
						ps.setDate((i), Date.valueOf(dp_endDate.getValue()));
						i++;
					}
					firstFilter = true;

				}

				if(isGenre()) {
					ps.setString(i, cb_genreFilter.getValue());
					i++;
				}


			}
			System.out.println(ps.toString());
			ResultSet rs = ps.executeQuery();
			setTableValues(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private boolean onlyEndDate() {
		return !isStartDate() && isEndDate();
	}

	private boolean onlyStartDate() {
		return isStartDate() && !isEndDate();
	}

	private boolean startAndEnd() {
		return isStartDate() && isEndDate();
	}

	private boolean onlyMax() {
		return !isMinCost() && isMaxCost();
	}

	private boolean onlyMin() {
		return isMinCost() && !isMaxCost();
	}

	private boolean minAndMax() {
		return isMinCost() && isMaxCost();
	}

	private boolean isAnyfilter() {

		return isArtist() || isCity() || isVenue() || isStartDate()
				|| isEndDate() || isMinCost() || isMaxCost() ||isGenre()
				;

	}

	private boolean isGenre() {
		return !"genre".equalsIgnoreCase(cb_genreFilter.getValue());
	}

	private boolean isMaxCost() {
		return !(s_maxCost.getValue() == 0.0);
	}

	private boolean isMinCost() {
		return !(s_minCost.getValue() == 0.0);
	}

	private boolean isEndDate() {
		return !(dp_endDate.getValue() == null);
	}

	private boolean isStartDate() {
		return !(dp_startDate.getValue() == null);
	}

	private boolean isVenue() {
		return !tf_venueFilter.getText().isEmpty();
	}

	private boolean isCity() {
		return !tf_cityFilter.getText().isEmpty();
	}

	private boolean isArtist() {
		return !tf_artistFilter.getText().isEmpty();
	}


	@FXML
	void logout(ActionEvent event) {

	}

	@FXML
	void menuSave(ActionEvent event) throws SQLException {
		
		Connection connection = null;
		try {
			connection = dbconnector.connection();
			
			TableViewSelectionModel<Concert> selectionModel = table_concerts.getSelectionModel();
			Concert selectedItem = selectionModel.getSelectedItem();
			int idToSave = selectedItem.getId();
			
			int userId = 0;
			
			PreparedStatement userSelect = connection.prepareStatement("SELECT iduser FROM users WHERE username = ?");
			userSelect.setString(1, lbl_user.getText());
			ResultSet set = userSelect.executeQuery();
			while(set.next()) {
				userId = set.getInt(1);
			}
			
			PreparedStatement insert = connection.prepareStatement("INSERT INTO saved_concerts (iduser, idconcert) VALUES (?, ?)");
			insert.setInt(1, userId);
			insert.setInt(2, idToSave);

			insert.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(connection != null) {
				connection.close();
			}
		}
	}

	@FXML
	void viewSaved(ActionEvent event) throws SQLException {
		Connection connection = null;
		try {
			int userId = 0;
			connection = dbconnector.connection();
			PreparedStatement userSelect = connection.prepareStatement("SELECT iduser FROM users WHERE username = ?");
			userSelect.setString(1, lbl_user.getText());
			ResultSet userSet = userSelect.executeQuery();
			while(userSet.next()) {
				userId = userSet.getInt(1);
			}
			PreparedStatement concertsSelect = connection.prepareStatement("SELECT iduser, idconcert FROM saved_concerts WHERE iduser = ?");
			concertsSelect.setInt(1, userId);
			ResultSet concertSet = concertsSelect.executeQuery();
			setTableValues(concertSet);

		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(connection != null) {
				connection.close();
			}
		}
		
	}
	
    @FXML
    void setMaxText(MouseEvent event) {
    	lbl_maxVal.setText("" + s_maxCost.getValue());
    }

    @FXML
    void setMinText(MouseEvent event) {
    	lbl_minVal.setText("" + s_minCost.getValue());
    }

}


