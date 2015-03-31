package labs;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import javax.swing.JOptionPane;

public class sqliteConnection {

	Connection conn = null;
	static String sqlpath;

	public static Connection dbconnector() {
		try {
			Class.forName("org.sqlite.JDBC");

			try {
				Properties pro = new Properties();

				File config_file = new File("config.ini");

				if (config_file.exists()) {
					pro.load(new FileInputStream("config.ini"));
					sqlpath = pro.getProperty("sqlite");
					//JOptionPane.showMessageDialog(null, sqlpath);
				} else {
					JOptionPane.showMessageDialog(null,
							"config.ini Dosyası bulunamadı");
					System.exit(0);

				}

			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}

			Connection conn = DriverManager.getConnection("jdbc:sqlite:"+ sqlpath + "");
			
			//Connection conn = DriverManager.getConnection("jdbc:sqlite:D:\\cansu_labs\\cansulabs.db");

			return conn;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Hata Kapatılıyor  " + e);
			System.exit(0);
			return null;
		}
	}
}
