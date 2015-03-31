package labs;

/*
 * ini okumak 
 * http://www.rgagnon.com/javadetails/java-0024.html
 * http://yourjavacode.blogspot.com.tr/2013/05/ini4j-how-to-read-and-write-from.html
 * sade https://www.daniweb.com/software-development/java/threads/123311/read-and-update-a-ini-file
 * 
 //http://stackoverflow.com/questions/26589976/how-to-format-date-and-time-from-jdatechooser-to-mysql-datetime-column
 // cok onemli http://naveenrajput10.blogspot.com.tr/2014/06/set-date-on-jdatechooser-and-retrieve.html
 //https://www.youtube.com/watch?v=EkAaFNV-GB0
 //http://stackoverflow.com/questions/11752989/netbeanshow-to-set-date-to-jdatechooser-which-is-retrieve-from-database
 http://stackoverflow.com/questions/3504986/java-date-time-format
 http://naveenrajput10.blogspot.com.tr/2014/06/set-date-on-jdatechooser-and-retrieve.html
 http://stackoverflow.com/questions/11719917/how-to-clear-jdatechooser-field
 eklenecek id yeri kaldı ve add dediğinde aslınd boşaltıp eklemeli o kaldı 
 */

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
 //ekle http://stackoverflow.com/questions/447481/how-do-i-use-jprogressbar-to-display-file-copy-progress



 +++gradelere sort koncak 
 çıkışa evet hayır sorusu konulacak
 File name de dosya kopyalama kaydet denilince yapılacak.
 alt veritabanları yapsana banaaaaa :)
 ID kalkacak kategori gelecek yeeyyyy :D


 */

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTable;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JTextArea;

import com.toedter.calendar.JDateChooser;

import javax.swing.JProgressBar;

import java.awt.Font;

import javax.swing.JInternalFrame;

import java.beans.PropertyVetoException;

import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.ImageIcon;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.Component;

import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class HomePage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	Connection connection = null;
	private JButton btnLoad;
	private JTextField textDatename;
	private JTextArea textExplanation;
	private JTextField textfilename;
	private JTable table;
	private JComboBox<Object> comboBox_categoryFilter;
	private JComboBox<Object> comboBox_1;

	private JComboBox<Object> comboBox_filter;
	private JLabel lblDurum;
	private JLabel lblIdst;
	private JDateChooser textDate;

	private JPanel contentPane;
	private JTextField textSearch;
	private JPanel panel_data;
	private JPanel panel_action;
	java.util.Date dt1;
	private JInternalFrame internalFrame;
	private JInternalFrame internalFrame_1;
	private JProgressBar progress;

	private JTable table_category;
	private JTextField textField_categoryName;

	private int fontSize_;
	private String filename;
	private String path;
	private JLabel lblCatid;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomePage frame = new HomePage();

					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */

	// seçilen combonun isminde id sini blur
	public String combox_id(String x) {

		String nm = null;
		try {
			PreparedStatement pst = connection
					.prepareStatement("select * from category where cat_name='"
							+ x + "'");
			ResultSet rs = pst.executeQuery();

			nm = rs.getString("cat_id");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nm;

	}

	public void comboBox_categoryFilter_selected_change() {
		int count = comboBox_categoryFilter.getItemCount();
		if (count > 0) {

			String query = null;

			String selecting = comboBox_categoryFilter.getSelectedItem()
					.toString();
			// textfilename.setText(selecting);
			if ((selecting.equals("All"))) {

				query = "select (select count(*) from labs b  where a.id >= b.id) as No , id,"
						+ "dataname as Dataname,strftime('%d-%m-%Y',date) as Date, filename as FileName,explanation as Explanation,"
						+ "category.cat_name as Category from labs as a join category on cat_id=category ";

			} else {

				selecting = combox_id(selecting);

				query = "select (select count(*) from labs b  where a.id >= b.id) as No , id,"
						+ "dataname as Dataname,strftime('%d-%m-%Y',date) as Date, filename as FileName,explanation as Explanation,"
						+ "category.cat_name as Category from labs as a join category on cat_id=category  where category = '"
						+ selecting + "'";
			}

			// String selecting = "'Proposal'";
			try {

				/*
				 * //textfilename.setText(selecting); String query2 =
				 * "select (select count(*) from labs b  where a.id >= b.id) as No , id,"
				 * +
				 * "dataname as Dataname,strftime('%d-%m-%Y',date) as Date, filename as FileName,explanation as Explanation,"
				 * +
				 * "category.cat_name as Category from labs as a join category on cat_id=category "
				 * ;
				 */

				PreparedStatement ps = connection.prepareStatement(query);
				// textExplanation.setText(query);
				ResultSet rs = ps.executeQuery();

				table.setModel(DbUtils.resultSetToTableModel(rs));

				DefaultTableModel model = (DefaultTableModel) table.getModel();

				TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(
						model);

				table.setRowSorter(sorter);
				TableColumnModel columnModel = table.getColumnModel();
				// columnModel.getColumn(0).setWidth(20);
				columnModel.getColumn(0).setPreferredWidth(10);// genişlik
																// ayarladım
				columnModel.getColumn(1).setPreferredWidth(0);// genişlik
																// ayarladım
																// id kolonu
				columnModel.getColumn(3).setPreferredWidth(35);// genişlik
																// ayarladım
				// id kolonu gili oldu
				columnModel.getColumn(1).setWidth(0);
				columnModel.getColumn(1).setMinWidth(0);
				columnModel.getColumn(1).setMaxWidth(0);

				ps.close();

			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void FileCopyAndDelete(String copyfile, String filename) {
		panel_action.hide();
		panel_data.hide();
		internalFrame.setVisible(true);
		InputStream inStream = null;
		OutputStream outStream = null;

		try {

			File afile = new File(copyfile);
			File bfile = new File("D:\\thesis\\" + filename);

			if (afile.exists()) {

				int length = (int) afile.length();
				// double kilobytes = (bytes/ 1024);

				inStream = new FileInputStream(afile);
				outStream = new FileOutputStream(bfile);

				byte[] buffer = new byte[1024];

				int length_m;
				// http://stackoverflow.com/questions/447481/how-do-i-use-jprogressbar-to-display-file-copy-progress
				int current = 0;

				progress.setMaximum(length); // we're going to get this many
												// bytes
				progress.setValue(0); // we've gotten 0 bytes so far

				// copy the file content in bytes
				while ((length_m = inStream.read(buffer)) > 0) {

					outStream.write(buffer, 0, length_m);
					current += length_m; // we've progressed a little so update
											// current
					progress.setValue(current); // tell progress how far we are
				}

				inStream.close();
				outStream.close();

				// delete the original file
				afile.delete();

				lblDurum.setText("File Copied");

				panel_action.show();
				panel_data.show();
				internalFrame.setVisible(false);
			} else {
				System.out.println("File does not exists!");

				JOptionPane.showMessageDialog(null, "File does not exists!");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * public void datable_yukle() { try { PreparedStatement pst = connection
	 * .prepareStatement("select * from labs"); ResultSet rs =
	 * pst.executeQuery(); table.setModel(new DefaultTableModel( new Object[][]
	 * { {new Integer(9), "2015-02-21", "545", "545456", "HaxLogs.txt"}, {new
	 * Integer(10), "2015-02-07", "545", "545456", "HaxLogs.txt"}, }, new
	 * String[] { "id", "date", "dataname", "explanation", "filename" } )); }
	 * catch (SQLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } }
	 */
	public void datable_category_yukle() {
		try {

			String sql = "select cat_id, cat_name as Category  from category";

			PreparedStatement pst = connection.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			table_category.setModel(DbUtils.resultSetToTableModel(rs));
			DefaultTableModel model = (DefaultTableModel) table_category
					.getModel();
			TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(
					model);

			table_category.setRowSorter(sorter);
			TableColumnModel columnModel = table_category.getColumnModel();

			columnModel.getColumn(0).setPreferredWidth(0);// genişlik ayarladım
															// id kolonu

			columnModel.getColumn(0).setWidth(0);
			columnModel.getColumn(0).setMinWidth(0);
			columnModel.getColumn(0).setMaxWidth(0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void datable_yukle() {
		try {

			// http://www.w3resource.com/sqlite/sqlite-strftime.php
			// String
			// sql="select (select count(*) from labs b  where a.id >= b.id) as No , id,dataname,strftime('%d-%m-%Y',date) as date, filename,explanation from labs as a";
			String sql = "select (select count(*) from labs b  where a.id >= b.id) as No , id,"
					+ "dataname as Dataname,strftime('%d-%m-%Y',date) as Date, filename as FileName,explanation as Explanation,"
					+ "category.cat_name as Category from labs as a join category on cat_id=category";

			PreparedStatement pst = connection.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			// http://www.thaicreate.com/java/java-gui-example-jtable-header-sort.html
			// https://tips4java.wordpress.com/2008/10/11/table-format-renderers/
			// https://www.youtube.com/watch?v=Hm14C30b1hg
			// http://esus.com/creating-a-jtable-with-a-different-background-color-per-column/
			// https://www.youtube.com/watch?v=kXI3VZvYWFE
			// kullan bunu
			// https://tips4java.wordpress.com/2010/01/24/table-row-rendering/

			DefaultTableModel model = (DefaultTableModel) table.getModel();

			/*
			 * model.addColumn("CustomerID"); model.addColumn("Name");
			 * model.addColumn("Email"); model.addColumn("CountryCode");
			 * model.addColumn("Budget"); model.addColumn("Used");
			 */

			// Header Sort
			TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(
					model);

			table.setRowSorter(sorter);
			TableColumnModel columnModel = table.getColumnModel();
			// columnModel.getColumn(0).setWidth(20);
			columnModel.getColumn(0).setPreferredWidth(10);// genişlik ayarladım
			columnModel.getColumn(1).setPreferredWidth(0);// genişlik ayarladım
															// id kolonu
			columnModel.getColumn(3).setPreferredWidth(35);// genişlik ayarladım
			// id kolonu gili oldu
			columnModel.getColumn(1).setWidth(0);
			columnModel.getColumn(1).setMinWidth(0);
			columnModel.getColumn(1).setMaxWidth(0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void fillcombobox() {

		comboBox_1_combobox();

		comboBox_categoryFilter_combobox();

		/*
		 * comboBox_categoryFilter.removeAllItems();
		 * comboBox_categoryFilter.addItem("All"); comboBox_1.removeAllItems();
		 */

	}

	public void fillcombobox_for_category() {

		comboBox_categoryFilter.removeAllItems();
		comboBox_categoryFilter.addItem("All");
		comboBox_1.removeAllItems();

		comboBox_1_combobox();

		comboBox_categoryFilter_combobox();

	}

	public void comboBox_categoryFilter_combobox() {

		/*
		 * DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>)
		 * comboBox_categoryFilter.getModel(); model.removeAllElements();
		 */

		// http://stackoverflow.com/questions/10064834/remove-items-from-a-combobox

		// http://stackoverflow.com/questions/27737659/java-populating-jcombobox-from-mysql-databse-error
		/*
		 * String sql = "SELECT combo FROM `leave`"; try (Connection con =
		 * Connect.ConnectDB(); PreparedStatement pst =
		 * con.prepareStatement(sql); ResultSet rs = pst.executeQuery()) { if
		 * (rs.next()) { String nm = rs.getString("combo");
		 * jComboBox1.addItem(nm); } } catch (Exception e) {
		 * JOptionPane.showMessageDialog(null, e); }
		 */

		// key value mantıgı
		// http://stackoverflow.com/questions/4822928/jcombobox-string-item-visible-and-integer-key-inherent
		// http://stackoverflow.com/questions/27737659/java-populating-jcombobox-from-mysql-databse-error

		try {
			PreparedStatement pst = connection
					.prepareStatement("select * from category");
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				String nm = rs.getString("cat_name"); // Retrieves the value of
														// the
				// designated column in the
				// current row of this ResultSet
				// object as a String
				if (nm != null) {
					nm = nm.trim();
					comboBox_categoryFilter.addItem(nm);
				}

			}
			// rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void comboBox_1_combobox() {

		try {
			PreparedStatement pst2 = connection
					.prepareStatement("select * from category");
			ResultSet rs2 = pst2.executeQuery();

			while (rs2.next()) {

				String nm2 = rs2.getString("cat_name"); // Retrieves the value
														// of
														// the
				// designated column in the
				// current row of this ResultSet
				// object as a String
				if (nm2 != null) {
					nm2 = nm2.trim();
				}

				comboBox_1.addItem(nm2);

			}
			rs2.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void file_open() {

		JFrame parentFrame = new JFrame();
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to save");

		int userSelection = fileChooser.showSaveDialog(parentFrame);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			lblDurum.setText("Save as file: " + fileToSave.getAbsolutePath());
			path = fileToSave.getAbsolutePath();
			filename = fileToSave.getName();
			// File dir = new
			// File(path.substring(0,path.lastIndexOf(File.separator)));
			// TODO : bak
			// label_sorgusu.setText("fileee: " +dir);klasor ismi verir

			lblDurum.setText("fileee: " + filename);
			textfilename.setText(filename);

		}
		// http://stackoverflow.com/questions/17034282/jfilechooser-regarding-the-open-and-cancel-buttons-java
		else if (userSelection == JFileChooser.CANCEL_OPTION) {
			panel_action.show();
			panel_data.show();
			internalFrame.setVisible(false);
		}
	}

	public HomePage() throws PropertyVetoException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(HomePage.class.getResource("/cansu/img/labs.png")));
		setTitle("Cansu LABS Data V0.1");
		// setTitle("Cansu ");
		connection = sqliteConnection.dbconnector();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 603, 637);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mnıtmCategoryManage = new JMenuItem("Category Manage");
		mnıtmCategoryManage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel_action.hide();
				panel_data.hide();
				internalFrame_1.setVisible(true);
			}
		});
		mnıtmCategoryManage.setIcon(new ImageIcon(HomePage.class
				.getResource("/cansu/img/folder_star.png")));
		mnFile.add(mnıtmCategoryManage);

		JMenuItem mnıtmOptions = new JMenuItem("Options");
		mnıtmOptions.setIcon(new ImageIcon(HomePage.class
				.getResource("/cansu/img/folder_find.png")));
		mnFile.add(mnıtmOptions);

		JMenuItem mnıtmExit = new JMenuItem("Exit");
		mnıtmExit.setIcon(new ImageIcon(HomePage.class
				.getResource("/cansu/img/door_out.png")));
		mnıtmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(JFrame.EXIT_ON_CLOSE);
			}
		});
		mnFile.add(mnıtmExit);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mnitmAbout = new JMenuItem("About");

		mnitmAbout.setIcon(new ImageIcon(HomePage.class
				.getResource("/cansu/img/folder_star.png")));
		mnitmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// http://www.java2s.com/Code/JavaAPI/javax.swing/JComboBoxgetItemCount.htm

				JOptionPane.showMessageDialog(null,
						" Biricik Aşkıma sevgilerle 18/02/2015 ", "sd",
						JOptionPane.WARNING_MESSAGE);
			}
		});

		mnHelp.add(mnitmAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		try {
			Properties pro = new Properties();

			File config_file = new File("config.ini");

			if (config_file.exists()) {
				pro.load(new FileInputStream("config.ini"));
				String num = pro.getProperty("FontSize");
				fontSize_ = Integer.parseInt(num);

			} else {
				String num = "10";
				fontSize_ = Integer.parseInt(num);

			}

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		internalFrame_1 = new JInternalFrame("Category Manage");
		internalFrame_1.addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosed(InternalFrameEvent arg0) {
				internalFrame_1.setVisible(false);
				panel_action.show();
				panel_data.show();
			}
		});
		internalFrame_1.setBounds(105, 11, 390, 469);
		contentPane.add(internalFrame_1);
		internalFrame_1.setClosable(true);
		internalFrame_1.setVisible(false);
		internalFrame_1.setRequestFocusEnabled(false);
		internalFrame_1.getContentPane().setLayout(null);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(36, 231, 296, 190);
		internalFrame_1.getContentPane().add(scrollPane_2);

		table_category = new JTable();
		table_category.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				try {
					int row = table_category.getSelectedRow();
					String id = (table_category.getModel().getValueAt(row, 0))
							.toString();
					PreparedStatement pst = connection
							.prepareStatement("select * from  category where cat_id= '"
									+ id + "'");
					ResultSet rs = pst.executeQuery();
					while (rs.next()) {
						lblCatid.setText(rs.getString("cat_id"));

						textField_categoryName.setText(rs.getString("cat_name"));
					}
					pst.close();

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		scrollPane_2.setViewportView(table_category);

		JLabel lblCategoryName = new JLabel("Category Name");
		lblCategoryName.setBounds(55, 29, 75, 14);
		internalFrame_1.getContentPane().add(lblCategoryName);

		textField_categoryName = new JTextField();
		textField_categoryName.setBounds(140, 26, 153, 20);
		internalFrame_1.getContentPane().add(textField_categoryName);
		textField_categoryName.setColumns(10);

		JButton btnAddCategory = new JButton("Add ");
		btnAddCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					PreparedStatement pst = connection
							.prepareStatement("insert into category (cat_name) values (?) ");

					pst.setString(1, textField_categoryName.getText());

					pst.execute();
					pst.close();

					datable_category_yukle();
					fillcombobox_for_category();
					JOptionPane.showMessageDialog(null, "Data record");
				} catch (SQLException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}

			}
		});
		btnAddCategory.setIcon(new ImageIcon(HomePage.class
				.getResource("/cansu/img/table_add.png")));
		btnAddCategory.setBounds(167, 85, 119, 25);
		internalFrame_1.getContentPane().add(btnAddCategory);

		JButton btnEditCategory = new JButton("Edit");
		btnEditCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String query = "update  category set" + " cat_name='"
						+ textField_categoryName.getText() + "' where cat_id='"
						+ lblCatid.getText() + "' ";
				// textField_categoryName.setText(query);

				try {

					PreparedStatement pst = connection.prepareStatement(query);

					pst.execute();
					pst.close();
					datable_category_yukle();
					fillcombobox_for_category();
					JOptionPane.showMessageDialog(null, "Data updated");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		btnEditCategory.setIcon(new ImageIcon(HomePage.class
				.getResource("/cansu/img/table_edit.png")));
		btnEditCategory.setBounds(36, 121, 121, 25);
		internalFrame_1.getContentPane().add(btnEditCategory);

		JButton btnNewCategory = new JButton("New ");
		btnNewCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField_categoryName.setText("");

			}
		});
		btnNewCategory.setIcon(new ImageIcon(HomePage.class
				.getResource("/cansu/img/table.png")));
		btnNewCategory.setBounds(38, 86, 119, 23);
		internalFrame_1.getContentPane().add(btnNewCategory);

		JButton btnCategoryDelete = new JButton("Delete");
		btnCategoryDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!(lblCatid.getText().equals(""))) {
					int option = JOptionPane.showConfirmDialog(
							null,
							lblCatid.getText()
									+ " id number , Are you sure you want to delete?",
							"Warning", JOptionPane.YES_NO_OPTION);

					if (option == 0) {

						// TODO : confirm emin misin sorusu koy
						try {
							PreparedStatement pst = connection
									.prepareStatement(" delete from category  where cat_id='"
											+ lblCatid.getText() + "' ");

							pst.execute();
							pst.close();
							datable_category_yukle();
							fillcombobox_for_category();
							JOptionPane.showMessageDialog(null,
									lblCatid.getText() + " id deleted ");

							textField_categoryName.setText("");

						} catch (SQLException ex) {
							// TODO Auto-generated catch block
							ex.printStackTrace();
						}
					}

				} else {
					JOptionPane.showMessageDialog(null, " no selected id ",
							"warning", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnCategoryDelete.setIcon(new ImageIcon(HomePage.class
				.getResource("/cansu/img/table_delete.png")));
		btnCategoryDelete.setBounds(167, 122, 119, 23);
		internalFrame_1.getContentPane().add(btnCategoryDelete);

		JButton btnExitCat = new JButton("Exit");
		btnExitCat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				internalFrame_1.setVisible(false);
				panel_action.show();
				panel_data.show();
			}
		});
		btnExitCat.setIcon(new ImageIcon(HomePage.class
				.getResource("/cansu/img/door_out.png")));
		btnExitCat.setBounds(167, 156, 119, 23);
		internalFrame_1.getContentPane().add(btnExitCat);

		JButton btnRefrresh_Cat = new JButton("Refresh Table");
		btnRefrresh_Cat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				datable_category_yukle();
			}
		});
		btnRefrresh_Cat.setIcon(new ImageIcon(HomePage.class
				.getResource("/cansu/img/table_refresh.png")));
		btnRefrresh_Cat.setBounds(36, 157, 121, 23);
		internalFrame_1.getContentPane().add(btnRefrresh_Cat);

		JLabel lblId = new JLabel("ID");
		lblId.setBounds(53, 61, 46, 14);
		internalFrame_1.getContentPane().add(lblId);

		lblCatid = new JLabel("");
		lblCatid.setBounds(139, 60, 46, 14);
		internalFrame_1.getContentPane().add(lblCatid);

		lblDurum = new JLabel("Durum");
		lblDurum.setBounds(12, 551, 513, 16);
		contentPane.add(lblDurum);
		lblDurum.setAutoscrolls(true);

		panel_action = new JPanel();
		panel_action.setBounds(12, 25, 575, 194);
		contentPane.add(panel_action);
		panel_action.setLayout(null);

		JLabel lbldate = new JLabel("Date");
		lbldate.setBounds(0, 47, 81, 14);
		panel_action.add(lbldate);

		textDatename = new JTextField();
		textDatename.setBounds(86, 73, 171, 26);
		panel_action.add(textDatename);
		textDatename.setColumns(10);

		JLabel lblNewLabel = new JLabel("Dataname");
		lblNewLabel.setBounds(0, 79, 74, 14);
		panel_action.add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(304, 22, 261, 161);
		panel_action.add(scrollPane);

		textExplanation = new JTextArea();
		scrollPane.setViewportView(textExplanation);
		textExplanation.setTabSize(0);
		textExplanation.setWrapStyleWord(true);
		// textExplanation.setRows(10);
		textExplanation.setLineWrap(true);

		textExplanation.setFont(new Font("SansSerif", Font.PLAIN, fontSize_));
		// DefaultCaret caret = (DefaultCaret) textExplanation.getCaret();
		// caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		textExplanation.setCaretPosition(textExplanation.getText().length());

		JLabel lblFilename = new JLabel("Filename");
		lblFilename.setBounds(0, 113, 89, 14);
		panel_action.add(lblFilename);

		textfilename = new JTextField();
		textfilename.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				file_open();
			}
		});
		textfilename.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				file_open();
			}
		});
		textfilename.setEditable(false);
		textfilename.setBounds(86, 107, 171, 26);
		panel_action.add(textfilename);
		textfilename.setColumns(10);

		JLabel lblid = new JLabel("ID");
		lblid.setBounds(0, 169, 46, 14);
		panel_action.add(lblid);

		JButton btnOpen = new JButton("");
		btnOpen.setIcon(new ImageIcon(HomePage.class
				.getResource("/cansu/img/folder_find.png")));
		btnOpen.setToolTipText("Open Directly");
		btnOpen.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				File folder = new File("D:\\thesis"); // path to the directory
														// to be
				// opened

				// File folder = new File(textfilename.getText()); // path to
				// the
				// directory to
				// be opened
				Desktop desktop = null;
				if (Desktop.isDesktopSupported()) {
					desktop = Desktop.getDesktop();
				}

				try {
					desktop.open(folder);
				} catch (IOException e) {
				}
			}
		});
		btnOpen.setBounds(155, 145, 46, 45);
		panel_action.add(btnOpen);

		JButton btnOpenFile = new JButton("");
		btnOpenFile.setIcon(new ImageIcon(HomePage.class
				.getResource("/cansu/img/folder_star.png")));
		btnOpenFile.setToolTipText("Show File");
		btnOpenFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// http://stackoverflow.com/questions/9134096/java-open-folder-on-button-click
				// https://www.youtube.com/watch?v=dRrYHN12FrI

				if (!(textfilename.getText().equals(""))) {
					File myfile = new File("d://thesis//"
							+ textfilename.getText());
					// String path = myfile.getAbsolutePath();//burası sadece
					// klasoru acar
					// File dir = new
					// File(path.substring(0,path.lastIndexOf(File.separator)));
					if (Desktop.isDesktopSupported()) {
						try {
							Desktop.getDesktop().open(myfile);// buna dir ver
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				} else {
					JOptionPane.showMessageDialog(null, "filename is blank  ");
				}

			}
		});
		btnOpenFile.setBounds(211, 145, 46, 45);
		panel_action.add(btnOpenFile);
		JButton btnSaveFile = new JButton("");
		btnSaveFile.setIcon(new ImageIcon(HomePage.class
				.getResource("/cansu/img/folder_go.png")));
		btnSaveFile.setToolTipText("Save File");
		btnSaveFile.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				file_open();
			}
		});
		btnSaveFile.setBounds(262, 107, 30, 28);
		panel_action.add(btnSaveFile);

		lblIdst = new JLabel("");
		lblIdst.setBounds(86, 105, 55, 16);
		panel_action.add(lblIdst);

		textDate = new JDateChooser();
		textDate.setDateFormatString("dd-MM-yyyy");
		textDate.setBounds(86, 39, 171, 28);
		// http://stackoverflow.com/questions/14930225/empty-jdatechooser
		// http://stackoverflow.com/questions/11323718/mysql-date-format-in-java
		// http://stackoverflow.com/questions/21012751/get-jdatechooser-date-to-jlabel
		// http://stackoverflow.com/questions/23564363/getting-value-from-jdatechooser-and-saving-to-ms-sql-db
		panel_action.add(textDate);
		textDate.setFocusTraversalPolicy(new FocusTraversalOnArray(
				new Component[] { textDate.getCalendarButton() }));

		JLabel lblCategory = new JLabel("Category");
		lblCategory.setBounds(0, 10, 46, 14);
		panel_action.add(lblCategory);

		comboBox_1 = new JComboBox<Object>();

		comboBox_1.setBounds(86, 6, 171, 22);

		panel_action.add(comboBox_1);

		JLabel lblNewLabel_1 = new JLabel("Explanation");
		lblNewLabel_1.setBounds(309, 0, 89, 14);
		panel_action.add(lblNewLabel_1);

		panel_data = new JPanel();
		panel_data.setBounds(25, 231, 556, 310);
		contentPane.add(panel_data);
		panel_data.setLayout(null);

		textSearch = new JTextField();
		textSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				String full = "%" + textSearch.getText() + "%";
				String selecting = (String) comboBox_filter.getSelectedItem();
				try {

					/*
					 * // http://alvinalexander.com/blog/post/jdbc/jdbc-
					 * preparedstatement-select-like örmek 1 PreparedStatement
					 * pst = connection .prepareStatement
					 * ("select * from  labs where filename = ?");
					 * pst.setString(1,textSearch.getText());
					 */
					/*
					 * örnek2 PreparedStatement pst = connection
					 * .prepareStatement
					 * ("select * from  labs where filename like  ?"); String
					 * full="%"+textSearch.getText()+"%"; pst.setString(1,full);
					 * 
					 * String query2=
					 * "select (select count(*) from labs b  where a.id >= b.id) as No , id,dataname as"
					 * +
					 * " Dataname,strftime('%d-%m-%Y',date) as Date, filename as FileName,explanation as Explanation,"
					 * +
					 * "category.cat_name as Category from labs as a join category on cat_id=category where id LIKE  '%8%'"
					 * ;
					 * 
					 * textExplanation.setText(query2+full);
					 */

					String query = "select (select count(*) from labs b  where a.id >= b.id) as No , id,"
							+ "dataname as Dataname,strftime('%d-%m-%Y',date) as Date, filename as FileName,explanation as Explanation,"
							+ "category.cat_name as Category from labs as a join category on cat_id=category where "
							+ selecting + " LIKE  ?";

					PreparedStatement ps = connection.prepareStatement(query);

					ps.setString(1, full);

					ResultSet rs = ps.executeQuery();

					table.setModel(DbUtils.resultSetToTableModel(rs));

					DefaultTableModel model = (DefaultTableModel) table
							.getModel();

					TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(
							model);

					table.setRowSorter(sorter);
					TableColumnModel columnModel = table.getColumnModel();
					// columnModel.getColumn(0).setWidth(20);
					columnModel.getColumn(0).setPreferredWidth(10);// genişlik
																	// ayarladım
					columnModel.getColumn(1).setPreferredWidth(0);// genişlik
																	// ayarladım
																	// id kolonu
					columnModel.getColumn(3).setPreferredWidth(35);// genişlik
																	// ayarladım
					// id kolonu gili oldu
					columnModel.getColumn(1).setWidth(0);
					columnModel.getColumn(1).setMinWidth(0);
					columnModel.getColumn(1).setMaxWidth(0);

					ps.close();

				} catch (SQLException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		});

		textSearch.setBounds(179, 58, 122, 24);
		panel_data.add(textSearch);
		textSearch.setColumns(10);

		JLabel lblSearch = new JLabel("Search");
		lblSearch.setBounds(142, 62, 55, 16);
		panel_data.add(lblSearch);

		comboBox_filter = new JComboBox<Object>();
		comboBox_filter.setModel(new DefaultComboBoxModel(new String[] { "id",
				"date", "dataname", "explanation", "filename" }));
		comboBox_filter.setBounds(28, 57, 112, 26);
		panel_data.add(comboBox_filter);

		JLabel lblFiltre = new JLabel("Filter");
		lblFiltre.setBounds(0, 62, 55, 16);
		panel_data.add(lblFiltre);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 94, 556, 216);
		panel_data.add(scrollPane_1);

		table = new JTable();
		table.setBackground(Color.WHITE);

		table.setSurrendersFocusOnKeystroke(true);
		table.setFillsViewportHeight(true);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrollPane_1.setViewportView(table);

		btnLoad = new JButton("");
		btnLoad.setIcon(new ImageIcon(HomePage.class
				.getResource("/cansu/img/table_refresh.png")));
		btnLoad.setToolTipText("Refresh Table");
		btnLoad.setBounds(508, 58, 38, 24);
		panel_data.add(btnLoad);

		JButton btnAdd = new JButton("Save");
		btnAdd.setIcon(new ImageIcon(HomePage.class
				.getResource("/cansu/img/table_add.png")));
		btnAdd.setBounds(96, 11, 89, 35);
		panel_data.add(btnAdd);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.setIcon(new ImageIcon(HomePage.class
				.getResource("/cansu/img/table_edit.png")));
		btnUpdate.setBounds(195, 11, 89, 35);
		panel_data.add(btnUpdate);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setIcon(new ImageIcon(HomePage.class
				.getResource("/cansu/img/table_delete.png")));
		btnDelete.setBounds(298, 11, 89, 35);
		panel_data.add(btnDelete);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(JFrame.EXIT_ON_CLOSE);
			}
		});
		btnExit.setIcon(new ImageIcon(HomePage.class
				.getResource("/cansu/img/door_out.png")));
		btnExit.setBounds(397, 11, 90, 35);
		panel_data.add(btnExit);

		JButton btnNew = new JButton("New");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				((JTextField) textDate.getDateEditor().getUiComponent())
						.setText("");
				lblIdst.setText("");
				// textDate.setDate("");
				textDatename.setText("");
				textExplanation.setText("");
				textfilename.setText("");
			}
		});
		btnNew.setIcon(new ImageIcon(HomePage.class
				.getResource("/cansu/img/table.png")));
		btnNew.setBounds(0, 11, 89, 35);
		panel_data.add(btnNew);

		JLabel lblCategory_1 = new JLabel("Category");
		lblCategory_1.setBounds(308, 63, 46, 14);
		panel_data.add(lblCategory_1);

		// comboBox = new JComboBox();
		comboBox_categoryFilter = new JComboBox();
		comboBox_categoryFilter.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				comboBox_categoryFilter_selected_change();
			}
		});
		comboBox_categoryFilter.setModel(new DefaultComboBoxModel(
				new String[] { "All" }));

		comboBox_categoryFilter.setBounds(364, 59, 134, 22);
		panel_data.add(comboBox_categoryFilter);

		internalFrame = new JInternalFrame("Progress");
		internalFrame.setBounds(0, 212, 605, 121);
		contentPane.add(internalFrame);
		internalFrame.setBorder(new LineBorder(new Color(0, 0, 0)));
		internalFrame.setRequestFocusEnabled(false);
		internalFrame.getContentPane().setLayout(null);

		progress = new JProgressBar();
		progress.setBounds(32, 31, 508, 48);
		internalFrame.getContentPane().add(progress);
		progress.setStringPainted(true);
		progress.setMinimum(0);
		progress.setMaximum(1000);

		JLabel lblCopy = new JLabel("Copy Please Wait");
		lblCopy.setBounds(235, 6, 143, 16);
		internalFrame.getContentPane().add(lblCopy);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!(lblIdst.getText().equals(""))) {
					int option = JOptionPane.showConfirmDialog(
							null,
							lblIdst.getText()
									+ " id number , Are you sure you want to delete?",
							"Warning", JOptionPane.YES_NO_OPTION);

					if (option == 0) {

						// TODO : confirm emin misin sorusu koy
						try {
							PreparedStatement pst = connection
									.prepareStatement(" delete from labs  where id='"
											+ lblIdst.getText() + "' ");

							pst.execute();
							pst.close();
							datable_yukle();
							JOptionPane.showMessageDialog(null,
									lblIdst.getText() + " id deleted ");
							// String strDate =
							// DateFormat.getDateInstance().format(textDate.getDate());
							lblIdst.setText("");
							// textDate.setDate("");
							textDatename.setText("");
							textExplanation.setText("");
							textfilename.setText("");
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				} else {
					JOptionPane.showMessageDialog(null, " no selected id ",
							"warning", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String x = comboBox_1.getSelectedItem().toString();
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

					dt1 = textDate.getDate();
					String strdtver1 = (String) sdf.format(textDate.getDate());
					PreparedStatement pst = connection
							.prepareStatement("update  labs set date = '"
									+ strdtver1 + "',dataname='"
									+ textDatename.getText()
									+ "' ,explanation='"
									+ textExplanation.getText()
									+ "' ,category='" + combox_id(x)
									+ "',filename='" + textfilename.getText()
									+ "' where id='" + lblIdst.getText() + "' ");

					pst.execute();
					pst.close();
					datable_yukle();
					JOptionPane.showMessageDialog(null, "Data updated");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/*
				 * progress dialog ornek ProgressDialog progress =new
				 * ProgressDialog(); HomePage.this.setEnabled(false);//kendini
				 * disable et progress.setVisible(true);//pencereyi goster
				 */
				String x = comboBox_1.getSelectedItem().toString();
				try {
					PreparedStatement pst = connection
							.prepareStatement("insert into labs (date,dataname,explanation,filename,category) values (?,?,?,?,?) ");

					// String strDate =
					// DateFormat.getDateInstance().format(textDate.getDate());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

					dt1 = textDate.getDate();
					String strdtver1 = (String) sdf.format(textDate.getDate());

					pst.setString(1, strdtver1);
					pst.setString(2, textDatename.getText());
					pst.setString(3, textExplanation.getText());
					pst.setString(4, textfilename.getText());
					pst.setString(5, combox_id(x));

					// lblCategory.setText(combox_id());

					pst.execute();
					pst.close();
					if (!(textfilename.getText().equals(""))) {
						FileCopyAndDelete(path, filename);
					}
					datable_yukle();

					JOptionPane.showMessageDialog(null, "Data record");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				datable_yukle();

			}
		});
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// JOptionPane.showMessageDialog(null, "gelir");
				try {
					int row = table.getSelectedRow();
					String id = (table.getModel().getValueAt(row, 1))
							.toString();
					PreparedStatement pst = connection
							.prepareStatement("select * from  labs where id= '"
									+ id + "'");
					ResultSet rs = pst.executeQuery();
					while (rs.next()) {
						lblIdst.setText(rs.getString("id"));
						// textDate.setDate( new
						// SimpleDateFormat("MM-dd-yyyy").parse(
						// rs.getString("date")));
						((JTextField) textDate.getDateEditor().getUiComponent())
								.setText(rs.getString("date"));
						String d5 = ((JTextField) textDate.getDateEditor()
								.getUiComponent()).getText();
						// JOptionPane.showMessageDialog(null, d5);
						java.util.Date date = new SimpleDateFormat("yyyy-MM-dd")
								.parse(String.valueOf(d5));
						textDate.setDate(date);
						textDatename.setText(rs.getString("dataname"));
						textExplanation.setText(rs.getString("explanation"));
						textfilename.setText(rs.getString("filename"));
					}
					pst.close();

				} catch (SQLException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		fillcombobox();
		datable_yukle();
		datable_category_yukle();

	}
}
