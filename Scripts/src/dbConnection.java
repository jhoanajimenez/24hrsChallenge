import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class dbConnection {
	static String user = "root";
	static String pass = "";
	static String host = "jdbc:mysql://localhost:3306/";
	static String db = "pizza_orders";
	static Statement statement;
	static Connection connect;
	
	public static void databaseConnect() {
//		Connection connect = null;
		try {
			//Establish connection
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(host, user, pass);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void createDatabase() {
		try {
			statement = connect.createStatement();
			
			//Create new database
			String sql = "CREATE DATABASE IF NOT EXISTS PIZZA_ORDERS";
			if(statement.executeUpdate(sql) == 1) {
				System.out.println("PIZZA_ORDERS Database created successfully...");   
				
			} else {
				System.out.println("PIZZA_ORDERS Database exists...");   
			}
			
		} catch (Exception e) {
			System.out.println("Cannot connect to pizza_orders database");
		}
	}
	
	public static void createTables() {
		String orders_sql = "CREATE TABLE IF NOT EXISTS `pizza_orders`.`orders`( " +
				   "`order_id` INT(10) NOT NULL AUTO_INCREMENT , " +
				   "`date` DATE NOT NULL , " +
				   "`time` TIME NOT NULL , " +
				   "PRIMARY KEY (`order_id`))";
		
		String pizza_types_sql = "CREATE TABLE IF NOT EXISTS `pizza_orders`.`pizza_types`( " +
				   "`pizza_type` VARCHAR(50) NOT NULL , " +
				   "`name` VARCHAR(120) NOT NULL , " +
				   "`category` ENUM('Chicken','Classic','Supreme','Veggie') NOT NULL , " +
				   "`ingredients` LONGTEXT NOT NULL," +
				   "PRIMARY KEY (`pizza_type`))";
		
		String pizzas_sql =  "CREATE TABLE IF NOT EXISTS `pizza_orders`.`pizzas`( " +
				   "`pizza_id` VARCHAR(50) NOT NULL , " +
				   "`pizza_type` VARCHAR(50) NOT NULL , " +
				   "`size` ENUM('S','M','L','XL','XXL') NOT NULL , " + 
				   "`price` DECIMAL(20,2) NOT NULL , " +
				   "PRIMARY KEY (`pizza_id`), " +
				   "FOREIGN KEY (pizza_type) REFERENCES pizza_types(pizza_type))";
		
		String order_details_sql = "CREATE TABLE IF NOT EXISTS `pizza_orders`.`order_details`( " +
				   "`order_details_id` INT(10) NOT NULL AUTO_INCREMENT , " +
				   "`order_id` INT(10) NOT NULL , " +
				   "`pizza_id` VARCHAR(50) NOT NULL , " + 
				   "`quantity` INT(10) NOT NULL , " +
				   "PRIMARY KEY (`order_details_id`), " +
				   "FOREIGN KEY (order_id) REFERENCES orders(order_id), " +
				   "FOREIGN KEY (pizza_id) REFERENCES pizzas(pizza_id))";
		
		String users_sql = "CREATE TABLE IF NOT EXISTS `pizza_orders`.`users`( "
				+ "`id` int(10) NOT NULL , "
				+ "`username` varchar(20) NOT NULL , " 
				+ "`password` varchar(8) NOT NULL , "
				+ "PRIMARY KEY (`id`))";
				 
		
		String curr_tbl = "";
		try {
			System.out.println("Creating tables...");
			//creation of tables should be in this order
			curr_tbl = "orders";
			statement.executeUpdate(orders_sql);
			
			curr_tbl = "pizza_types";
			statement.executeUpdate(pizza_types_sql);
			
			curr_tbl = "pizzas";
			statement.executeUpdate(pizzas_sql);
			
			curr_tbl = "order_details";
			statement.executeUpdate(order_details_sql);
			
			curr_tbl = "users";
			statement.executeUpdate(users_sql);
			
			System.out.println("Creating tables sucess");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error in creating table: " + curr_tbl);
		}
	}
	
	public static void csvToDB(String csv, String tbl) {
		String load_sql = "LOAD DATA LOCAL INFILE ? IGNORE INTO TABLE `pizza_orders`." + tbl +
				" FIELDS TERMINATED BY ','" +
				" ENCLOSED BY '\"'" +
				" LINES TERMINATED BY '\\r\\n' " +
				" IGNORE 1 LINES " ;
		System.out.println(load_sql);
		
		
		try {
			PreparedStatement ps = connect.prepareStatement(load_sql);
			ps.setString(1, csv);
			ps.executeUpdate();
		} catch (Exception ex) {
			System.out.println("Failed imporing " + csv);
			ex.printStackTrace();
		}
	}
}
