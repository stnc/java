package labs;
/*
//explanation büyük olacak  ekle
//upload file ekle
//tarih olarak seçilecek 
//girişe enter ile olsun 
//08/02/2015  ilk işe yarar java uygulamam lara ver 
//db ayarı ini den oku 
//dabata se ini ye bağlı 
//tema seçimi ayarı iniye ver 

tarih hatalı ---
explanation buyuk ---
open ve file calışmıyor ---
attığı yerde aynı isimde dosya var mı yok mu kontrol etsin ***
load data yı refresh yapallım ---
kayıt yapıldı uyarısı ingilizce olsun ---
dosya yuklendi dediği sadece uyarı label ına gelsin ve orada wait olabilir 
filtreleme de date secince tarih gelsin
//https://blogs.oracle.com/CoreJavaTechTips/entry/making_progress_with_swing_s
//ekle http://stackoverflow.com/questions/447481/how-do-i-use-jprogressbar-to-display-file-copy-progress*/


import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
//import javax.swing.UIManager;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JPasswordField;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Dialog.ModalExclusionType;

import javax.swing.SwingConstants;

import java.awt.Window.Type;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import labs.screen_utils;


public class Login {

	private JFrame frmLoginscreen;
	private JTextField textUsername;
	private JPasswordField textPassword;
	private JButton btnlogin;
	Connection connection = null;

	/**
	 * Launch the application.
	 */
	// TODO : bakılıacak
	// çalıştıgı yeri verecekdi
	/*
	 * private String getJarFolder() { String name =
	 * this.getClass().getName().replace('.', '/'); String s =
	 * this.getClass().getResource("/" + name + ".class").toString(); s =
	 * s.replace('/', File.separatorChar); s = s.substring(0,
	 * s.indexOf(".jar")+4); s = s.substring(s.lastIndexOf(':')-1); return
	 * s.substring(0, s.lastIndexOf(File.separatorChar)+1); }
	 */

	public static void main(String[] args) {

		// http://stackoverflow.com/questions/9541045/how-to-set-jframe-look-and-feel
		try {

			// UIMaanager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");

		} catch (Exception e) {
			e.printStackTrace();
		}
		// /gorunumler burada
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();

					window.frmLoginscreen.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
		// diğer sınıflar cagrıldı
		screen_utils scr = new screen_utils();// bu pencerenin ortada acılmasını
												// sağlar
		scr.centerWindowOnScreen(frmLoginscreen);

		connection = sqliteConnection.dbconnector();
		// connection=MysqlConnection.dbconnector();
	}

	@SuppressWarnings("unused")
	private Container getContentPane() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("deprecation")
	private void login_islemleri (){
		
		/*
		 * deneme= textPassword.getText();
		 * JOptionPane.showMessageDialog(null, deneme);
		 */

		
		try {
			String query = "select * from users where username=? and password =?";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, textUsername.getText());
			pst.setString(2, textPassword.getText());
			ResultSet rs = pst.executeQuery();
			int count = 0;
			while (rs.next()) {
				count = count + 1;
			}
			if (count == 1) {
				// JOptionPane.showMessageDialog(null,
				// "username a password is correct");
				frmLoginscreen.dispose();//kapat kendini 
				HomePage homepage = new HomePage();
				homepage.setTitle("Cansu LABS Data Ver 0.2.1 ");// bağlıgı değiştir
				screen_utils scr = new screen_utils();// onun tam
														// oratada
														// açılmasını
														// sağlar
				scr.centerWindowFrame(homepage);//pancreyi  ortada acar 

				homepage.setVisible(true);//homaopafge penceresini açar 

			} else if (count > 1) {
				JOptionPane.showMessageDialog(null,
						"Duplicate username and password");

			} else {
				JOptionPane
						.showMessageDialog(null,
								"Username and password not correctly , please try again ");
			}
			rs.close();
			pst.close();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	private Toolkit getToolkit() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLoginscreen = new JFrame();
		frmLoginscreen.setType(Type.POPUP);
		frmLoginscreen.setResizable(false);// maximize yi disabled eder
		// frmLoginscreen.setUndecorated(true) ; kenarı olmayan form oluşturur

		frmLoginscreen
				.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		frmLoginscreen.setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/cansu/img/application.png")));
		frmLoginscreen.setTitle("LoginScreen");
		frmLoginscreen.setBounds(100, 100, 416, 229);
		frmLoginscreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLoginscreen.getContentPane().setLayout(null);

		JLabel lblUsername = new JLabel("UserName");
		lblUsername.setBounds(162, 67, 80, 14);
		frmLoginscreen.getContentPane().add(lblUsername);

		textUsername = new JTextField();
		textUsername.setBounds(252, 64, 111, 20);
		frmLoginscreen.getContentPane().add(textUsername);
		textUsername.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(162, 98, 86, 14);
		frmLoginscreen.getContentPane().add(lblPassword);

		textPassword = new JPasswordField();
		textPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
			//entr ile basıldıgını anlamak içindir 
				if (e.getKeyCode()==KeyEvent.VK_ENTER){
					login_islemleri();
		        }
				
			}
			
		});
		textPassword.setBounds(252, 95, 111, 20);
		frmLoginscreen.getContentPane().add(textPassword);
		textPassword.setColumns(10);

		 btnlogin = new JButton("Login");
		btnlogin.setHorizontalAlignment(SwingConstants.LEFT);
		btnlogin.setIcon(new ImageIcon(Login.class.getResource("/cansu/img/tick.png")));
		btnlogin.addActionListener(new ActionListener() {
			// private String deneme;

			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				login_islemleri();

			}
		});
		btnlogin.setBounds(251, 138, 89, 23);
		frmLoginscreen.getContentPane().add(btnlogin);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Login.class.getResource("/cansu/img/login.png")));
	
	
		

		// Image img =new
		// ImageIcon(this.getClass().getResource("/login.png")).getImage();
		// lblNewLabel.setIcon(new ImageIcon (img));
		lblNewLabel.setBounds(27, 11, 126, 166);
		frmLoginscreen.getContentPane().add(lblNewLabel);

	}
}
