import java.io.File;
import java.net.URISyntaxException;

public class Main {

	public static void main(String[] args) {
	
		//Connect to DB
		dbConnection.databaseConnect();
		
		//create new database if not exist
		dbConnection.createDatabase();
		
		//create tables
		dbConnection.createTables();
		
		//check files and load csv
		loadCSV();
		
		System.out.println("IMPORT COMPLETE");
	}
	
	public static void loadCSV() {
		String tbl[] = {"orders.csv","pizza_types.csv","pizzas.csv","order_details.csv","users.csv"};
		
		try {
			String path = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
			for(String t : tbl) {
				File f = new File(path + "\\csv\\" + t);
				
				if(!f.exists() || f.isDirectory()) { 
				    System.out.println(f + " file does not exist!");
				} else {
					//load csv to database
					dbConnection.csvToDB(f.toString(), t.replace(".csv", ""));
				}
			}
			
		} catch (Exception ex) {
			System.out.println("failed loading csv");
		}
		
	}
	
	
}
