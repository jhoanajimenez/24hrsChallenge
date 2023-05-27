import java.io.File;
import java.net.URISyntaxException;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("HI");
		
		//Connect to DB and create new database
		dbConnection.databaseConnect();
		
		//create tables
		dbConnection.createTables();
		
		//check and load csv
		loadCSV();
	}
	
	public static void loadCSV() {
		String tbl[] = {"orders.csv","pizza_types.csv","pizzas.csv","order_details.csv"};
		
		try {
			String path = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
			for(String t : tbl) {
				File f = new File(path + "\\csv\\" + t);
				System.out.println(f);
				if(!f.exists() || f.isDirectory()) { 
				    System.out.println(t + " file does not exist!");
				} else {
					//load csv to database
					dbConnection.csvToDB(t, t.replace(".csv", ""));
				}
			}
			
		} catch (Exception ex) {
			System.out.println("failed loading csv");
		}
		
	}
	
	
}
