package application;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import auctionClasses.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import storageUnits.CommonUnit;

/**
 * Utility class which manages all the database connections 
 */
public class DBUtils {
	public static User actualUser;
	
	/**
	 * @return connection returns the connection if is successfully connected to the database, else return null 
	 */
	public static Connection getConnection() {
		Connection connection = null;
		String dbName = "myauction";
		String username = "root";
		String password = "root";
		String  url = "jdbc:mysql://localhost:3306/"+dbName;
		
		try {
			connection = (Connection) DriverManager.getConnection(url, username, password);
			System.out.println("Connected successfully");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: "+e.getMessage());
		}
	
		return connection;
	}

	/**
	 * @param fxmlFile fxmlFile is the parameter which requires the path of the fxml file to be open
	 * @param title title is the text that will be displayed in the top left corner of the window
	 * @param username username is the nick that actual user is using
	 * @param moneyBalance moneyBalance is the parameter where users balance is stored
	 * @param unitsBought unitsBought is the parameter containing the number of users units which were bought
	 */
	public static void changeScene(ActionEvent event, String fxmlFile, String title, String username, int moneyBalance, int unitsBought) {
		Parent root = null;
	
		if(username != null) {
			try {
				FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
				root = loader.load();
				
				if(loader.getController().getClass().getName() == "application.AuctionController") {
					AuctionController auctionController = loader.getController();
					auctionController.setUserInformation(username, moneyBalance, unitsBought);
				}
				else if(loader.getController().getClass().getName() == "application.EndMenuController") {
					
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("ERROR in ChangeScene: " + e.getMessage());
			}
		}
		else {
			System.out.println("Loading a LoginMenu");
			try {
				if(unitsBought != -1)
					DBUtils.getUnits("sell");
				root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("ERROR in ChangeScene Else: " + e.getMessage());
			}
		}
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle(title);
		stage.setScene(new Scene(root));
		stage.show();
	}

	/**
	 *  differs if the user exists and if not it registers him
	 *  @param fxmlFile fxmlFile is the parameter which requires the path of the fxml file to be open
	 * 	@param username username is the nick that actual user is using
	 * 	@param password password is the login information need to sign up a new user
	 *  @param moneyBalance moneyBalance is the parameter where users balance is stored
	 *  @param unitsBought unitsBought is the parameter containing the number of users units which were bought
	 */
	public static void signUpUser(ActionEvent event, String username, String password, int moneyBalance, int unitsBought) throws ClassNotFoundException {
		Connection connection = null;
		PreparedStatement psInsert = null;
		PreparedStatement psCheckUserExists = null;
		ResultSet resultSet = null;

		try {
			connection = getConnection();
			psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
			psCheckUserExists.setString(1, username);
			resultSet = psCheckUserExists.executeQuery();

			if(resultSet.isBeforeFirst()) {
				System.out.println("User already exists!");
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Username exists");
				alert.show();
			}
			else {
				psInsert = connection.prepareStatement("INSERT INTO users (username, password, moneyBalance, unitsBought) VALUES (?, ?, ?, ?)");
				psInsert.setString(1, username);
				psInsert.setString(2, password);
				psInsert.setInt(3, moneyBalance);
				psInsert.setInt(4, unitsBought);
				psInsert.executeUpdate();
				
				// serialize data to send them to different PC
				serialiseNewUser(new User(username, password, moneyBalance, unitsBought, null), "saves/usersJoinedFile.ser");
				User newUser = deserialiseNewUser("saves/usersJoinedFile.ser");
				
				System.out.println("Deserialised user data: ");
				newUser.printUserData();
				
				changeScene(event, "Auction.fxml", "Welcome!", username, moneyBalance, unitsBought);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error v UTILS Sign Up: "+e.getMessage());
		} finally {
			if(resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(psCheckUserExists != null) {
				try {
					psCheckUserExists.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(psInsert != null) {
				try {
					psInsert.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 *  log in existing user
	 * 	@param username username is the nick that actual user is using
	 *  @param moneyBalance moneyBalance is the parameter where users balance is stored	 
	 */
	public static void logInUser(ActionEvent event, String username, String password) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement("SELECT password, moneyBalance, unitsBought FROM users WHERE username = ?");
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();
			
			if(!resultSet.isBeforeFirst()) {
				System.out.println("User not found in the database!");
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setContentText("User or password incorrect");
				alert.show();
			}
			else {
				while (resultSet.next()) {
					String retrievedPassword = resultSet.getString("password");
					int retrievedMoneyBalance = resultSet.getInt("moneyBalance");
					int unitsBought = resultSet.getInt("unitsBought");
					if(retrievedPassword.equals(password)) {
						changeScene(event, "Auction.fxml", "Storage Unit Auction", username, retrievedMoneyBalance, unitsBought);
					}
					else {
						System.out.println("Password did not match!");
						Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.setContentText("Incorrect password or username");
						alert.show();
					}
				}
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * soldUnit is the function that adds the sold unit to the database
	 * @param unitType unitType is the name of the current unit
	 * @param unitPrice ending price of the current unit
	 * @param unitValue starting value of the current unit
	 * @param soldTo sold to is the last user who bid some money
	 * @param items items contained inside the unit
	 */
	public static void soldUnit(String unitType, int unitPrice, int unitValue, String soldTo, String items) {
		Connection connection = null;
		PreparedStatement psInsert = null;

		try {
			connection = getConnection();

			psInsert = connection.prepareStatement("INSERT INTO sold_units (unitType, soldTo, unitPrice, unitValue, items) VALUES (?, ?, ?, ?, ?)");
			psInsert.setString(1, unitType);
			psInsert.setString(2, soldTo);
			psInsert.setInt(3, unitPrice);
			psInsert.setInt(4, unitValue);
			psInsert.setString(5, items);
			psInsert.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in UTILS soldUnit: "+e.getMessage());
		} finally {
			if(psInsert != null) {
				try {
					psInsert.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * it updates the database and sets the current unit as the unit which is currently used
	 * @param unitType unitType si the name of the actual unit
	 * @param unitPrice is the actual price of the actual unit
	 * @param lastBid it is the last user who bid some money
	 */
	public static void updateActualUnit(String unitType, int unitPrice, String lastBid) {
		Connection connection = null;
		PreparedStatement psInsert = null;

		try {
			connection = getConnection();
			
			resetTable("actual_unit");
			
			psInsert = connection.prepareStatement("INSERT INTO actual_unit (unitType, unitPrice, lastBid) VALUES (?, ?, ?)");
			psInsert.setString(1, unitType);
			psInsert.setInt(2, unitPrice);
			psInsert.setString(3, lastBid);
			psInsert.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in UTILS updatActualUnit: "+e.getMessage());
		} finally {
			if(psInsert != null) {
				try {
					psInsert.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * shows the statistics of the current user and displays them inside the statistics table 
	 * @param username it is the name of the current user
	 */
	public static void showStatistics(ActionEvent event, String username) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement("SELECT moneyBalance, unitsBought FROM users WHERE username = ?");
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();
			
			if(!resultSet.isBeforeFirst()) {
				System.out.println("User not found in the database!");
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setContentText("User or password incorrect");
				alert.show();
			}
			else {
				while (resultSet.next()) {
					int retrievedMoneyBalance = resultSet.getInt("moneyBalance");
					int unitsBought = resultSet.getInt("unitsBought");
					
					changeScene(event, "StatisticsMenu.fxml", "Statistics", username, retrievedMoneyBalance, unitsBought);
				}
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * returns the list of the sold units of the actual user
	 * @param owner it is the name of the current user
	 * @return returns a list of the sold units owned by current owner inside the database
	 */
	public static ArrayList<CommonUnit> getUnits(String owner){
		ArrayList<CommonUnit> units = new ArrayList<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;
		ResultSet resultSet = null;
		
		try {
			connection = getConnection();
			if(owner == null) {
				preparedStatement = connection.prepareStatement("SELECT * FROM sold_units");
				resultSet = preparedStatement.executeQuery();
			}
			else {
				if(owner == "sell") {
					preparedStatement2 = connection.prepareStatement("SELECT * FROM sold_units WHERE soldTo = ?");
					preparedStatement2.setString(1, actualUser.getName());
					resultSet = preparedStatement2.executeQuery();
					while (resultSet.next()){
						int unitValue = resultSet.getInt(5);
						actualUser.addMoney(unitValue);
						updateUserInformation(actualUser.getName(), actualUser.getBalance(), actualUser.getUnitsBought());
					}
					
					preparedStatement = connection.prepareStatement("DELETE FROM sold_units WHERE soldTo = ?");
					preparedStatement.setString(1, actualUser.getName());
					preparedStatement.execute();					
				}else {
					preparedStatement = connection.prepareStatement("SELECT * FROM sold_units WHERE soldTo = ?");
					preparedStatement.setString(1, actualUser.getName());	
					resultSet = preparedStatement.executeQuery();
				}
			}
			
			while(resultSet != null && resultSet.next())
            {
				String unitName = resultSet.getString(2);
				String soldTo = resultSet.getString(3);
				int unitPrice = resultSet.getInt(4);
				int unitValue = resultSet.getInt(5);
				String items = resultSet.getString(6);
				
				units.add(new CommonUnit(unitName, soldTo, unitPrice, unitValue, items));
            }

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(preparedStatement2 != null) {
				try {
					preparedStatement2.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return units;
	}
	
	/**
	 * updates the database such as money value and units bought of the actual user
	 * @param username name of the username
	 * @param moneyBalance money balance of the current user
	 * @param unitsBought number of units that user bought
	 */
	public static void updateUserInformation(String username, int moneyBalance, int unitsBought) {
		Connection connection = null;
		PreparedStatement psInsert = null;

		try {
			connection = getConnection();

			psInsert = connection.prepareStatement("UPDATE users SET moneyBalance = ?, unitsBought = ? WHERE username = ?");
			psInsert.setInt(1, moneyBalance);
			psInsert.setInt(2, unitsBought);
			psInsert.setString(3, username);
			psInsert.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in UTILS.UpdateUserInformation: "+e.getMessage());
		} finally {
			if(psInsert != null) {
				try {
					psInsert.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * deletes the content of the given table
	 * @param table it is the name of the table which is going to be truncated
	 */
	public static void resetTable(String table) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = getConnection();
			String sql = "TRUNCATE ";
			
			preparedStatement = connection.prepareStatement(sql + table);
		
			preparedStatement.executeUpdate();
			
			System.out.println("Table " + table + " TRUNCATED");
			
		} catch (Exception e) {
			System.out.println("ERROR: Reset table fail: " + e.getMessage());
		}		
	}
	
	/**
	 * serializes the user and saves all the information about the object to a .ser file  
	 * @param user is the user which is going to be serialized
	 * @param serialiseFilePath location where will be the serialization stored
	 */
	public static void serialiseNewUser(User user, String serialiseFilePath) {
        //String usersJoinedFile = "saves/usersJoinedFile.ser";

        try
        {   
            //Saving the object to a file
            FileOutputStream file = new FileOutputStream(serialiseFilePath);
            ObjectOutputStream out = new ObjectOutputStream(file);
              
            // Method for serialization of object
            out.writeObject(user);

            out.close();
            file.close();
            
            // if needed create sockets to send the file via Internet
              
            System.out.println("Object User has been serialized");
        } catch(IOException ex)
        {
            System.out.println("ERROR IS: " + ex.getMessage());
        }
	}
	
	/**
	 * deserializes the given file and creates a new user of it
	 * @param deserialiseFilePath is the location of the file that is going to be deserialized
	 * @return newUser that is a new user of the current auction. It can be received via Internet
	 * @throws ClassNotFoundException exception that handles error if User class is not found
	 */
	public static User deserialiseNewUser(String deserialiseFilePath) throws ClassNotFoundException {
        //String usersJoinedFile = "saves/usersJoinedFile.ser";
		User newUser = null;
			
	    try
	    {   
	        //Saving the object to a file
	        FileInputStream fileIn = new FileInputStream(deserialiseFilePath);
	        ObjectInputStream in = new ObjectInputStream(fileIn);
	              
	        // Method for deserialization of object
	        newUser = (User) in.readObject();
	            
	        in.close();
	        fileIn.close();
	            
	        // if needed create sockets to retrieve the file via Internet
	            
	        System.out.println("Object User has been deserialized");
	    } catch(IOException ex)
	    {
	        System.out.println("ERROR IS: " + ex.getMessage());
	    }
	        
	       return newUser;
	}
}
