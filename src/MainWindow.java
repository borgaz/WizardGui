import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.text.ParseException;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;
import javax.swing.UIManager;

public class MainWindow {

	private JFrame wizardForm;
	private JTextField firstNameText;
	private JTextField lastNameText;
	private JTextField streetText;
	private JTextField streetNumText;
	private JTextField flatNumText;
	private JFormattedTextField postcodeText;
	private JTextField cityText;
	private JFormattedTextField phoneNumText;
	private JTextPane summaryText;

	private boolean firstNameCheck;
	private boolean lastNameCheck;
	private Boolean[] addressCheck = { false, false, false, false };
	private boolean phoneNumCheck;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.wizardForm.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize variables and run initialize() method.
	 * 
	 * @throws ParseException
	 * @throws BadLocationException
	 */
	public MainWindow() throws ParseException, BadLocationException {
		firstNameCheck = false;
		lastNameCheck = false;
		phoneNumCheck = false;
		initialize();
	}
	
	/**
	 * Create new custom PlainDocument based on regex from parameter.
	 * 
	 * @param regex the string with regex
	 * 
	 * @return custom PlainDocument
	 */
	private PlainDocument getDocument(String regex) {
		PlainDocument document = new PlainDocument();
		document.setDocumentFilter(new DocumentFilter() {
			@Override
			public void insertString(FilterBypass fb, int off, String str, AttributeSet attr)
					throws BadLocationException {
				fb.insertString(off, str.replaceAll(regex, ""), attr);
			}

			@Override
			public void replace(FilterBypass fb, int off, int len, String str, AttributeSet attr)
					throws BadLocationException {
				fb.replace(off, len, str.replaceAll(regex, ""), attr);
			}
		});
		return document;
	}

	/**
	 * Capitalize first letter of text.
	 * 
	 * @param text - the string to be capitalized
	 * 
	 * @return the capitalized text
	 */
	private String capitalize(String text) {
		char[] stringArray = text.toCharArray();
		int temp = (int) stringArray[0];
		if (temp >= 260) {
			if (temp == 261 || temp == 263 || temp == 281 || temp == 322 || temp == 324 || temp == 347 || temp == 378
					|| temp == 380) {
				temp--;
				stringArray[0] = (char) temp;
			}
		} else if (temp >= 97 && temp <= 122) {
			stringArray[0] = Character.toUpperCase(stringArray[0]);
		} else if (temp == 243) {
			temp -= 32;
			stringArray[0] = (char) temp;
		}
		text = new String(stringArray);
		return text;
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws ParseException
	 * @throws BadLocationException
	 */
	private void initialize() throws ParseException, BadLocationException {
		wizardForm = new JFrame();
		wizardForm.setResizable(false);
		wizardForm.setTitle("Wizard");
		wizardForm.setBounds(100, 100, 326, 336);
		wizardForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		wizardForm.getContentPane().setLayout(new CardLayout());

		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(UIManager.getColor("Tree.dropCellBackground"));
		wizardForm.getContentPane().add(mainPanel, "mainPanel");
		mainPanel.setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(UIManager.getColor("TabbedPane.focus"));
		tabbedPane.setBounds(12, 12, 296, 241);
		mainPanel.add(tabbedPane);
		
		//************ Actions for TextFields and Buttons ***
		
		Action firstNameAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (firstNameText.getText().length() > 1) {
					firstNameText.setText(capitalize(firstNameText.getText()));
					tabbedPane.setSelectedIndex(1);
				} else
					JOptionPane.showMessageDialog(null, Constants.firstNameErrorText);
			}
		};

		Action lastNameAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (lastNameText.getText().length() > 1) {
					lastNameText.setText(capitalize(lastNameText.getText()));
					tabbedPane.setSelectedIndex(2);
				} else
					JOptionPane.showMessageDialog(null, Constants.lastNameErrorText);
			}
		};

		Action addressAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (addressCheck[0] && addressCheck[1] && addressCheck[2] && addressCheck[3]){
					streetText.setText(capitalize(streetText.getText().trim()));
					cityText.setText(capitalize(cityText.getText().trim()));					
					tabbedPane.setSelectedIndex(3);
				}
				else
					JOptionPane.showMessageDialog(null, Constants.mainErrorText);
			}
		};
		
		Action summaryAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent arg0) {
				if (firstNameCheck && lastNameCheck && phoneNumCheck
						&& addressCheck[0] && addressCheck[1] && addressCheck[2] && addressCheck[3]) {
					String summaryString = "Imię: " + firstNameText.getText()
						+ "\nNazwisko: " + lastNameText.getText()
						+ "\nUlica: " + streetText.getText() + " " + streetNumText.getText();
					if (!flatNumText.getText().isEmpty())
						summaryString += "/" + flatNumText.getText();
					summaryString += "\nKod Pocztowy: " + postcodeText.getText()
						+ "\nMiejscowość: "	+ cityText.getText()
						+ "\nNumer telefonu: " + phoneNumText.getText();
					summaryText.setText(summaryString);
					((CardLayout) (wizardForm.getContentPane().getLayout())).show(wizardForm.getContentPane(),
							"summaryPanel");
				} else
					JOptionPane.showMessageDialog(null, Constants.mainErrorText);
			}
		};
		
		//************ Summary Panel elements ***************

		JPanel summaryPanel = new JPanel();
		summaryPanel.setBackground(new Color(210, 233, 255));
		wizardForm.getContentPane().add(summaryPanel, "summaryPanel");
		summaryPanel.setLayout(null);
		
		JLabel lblZostayWprowadzoneNastpujce = new JLabel("Zostały wprowadzone następujące dane:");
		lblZostayWprowadzoneNastpujce.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblZostayWprowadzoneNastpujce.setBounds(12, 12, 252, 17);
		summaryPanel.add(lblZostayWprowadzoneNastpujce);
		
		summaryText = new JTextPane();
		summaryText.setFont(new Font("Tahoma", Font.BOLD, 14));
		summaryText.setEditable(false);
		summaryText.setEnabled(false);
		summaryText.setBounds(12, 41, 297, 253);
		summaryPanel.add(summaryText);

		//************ First name tab elements **************

		JPanel firstNamePanel = new JPanel();
		firstNamePanel.setBackground(UIManager.getColor("Tree.dropCellBackground"));
		tabbedPane.addTab("Imię", null, firstNamePanel, null);
		firstNamePanel.setLayout(null);

		JLabel lblPodajSwojeImi = new JLabel("Podaj swoje imi\u0119:");
		lblPodajSwojeImi.setBounds(10, 12, 106, 20);
		lblPodajSwojeImi.setFont(new Font("Tahoma", Font.PLAIN, 14));
		firstNamePanel.add(lblPodajSwojeImi);

		firstNameText = new JTextField();
		firstNameText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				if (firstNameText.getText().length() == 1)
					firstNameText.setText(capitalize(firstNameText.getText()));
				else if (firstNameText.getText().length() > 1)
					firstNameCheck = true;
				else
					firstNameCheck = false;
			}
		});
		firstNameText.addActionListener(firstNameAction);
		firstNameText.setDocument(getDocument(Constants.firstNameRegex));
		firstNameText.setBounds(10, 44, 269, 21);
		firstNameText.setFont(new Font("Dialog", Font.PLAIN, 12));
		firstNameText.setColumns(10);
		firstNamePanel.add(firstNameText);

		JButton firstNameNextButton = new JButton("Dalej");
		firstNameNextButton.addActionListener(firstNameAction);
		firstNameNextButton.setBounds(169, 174, 110, 27);
		firstNameNextButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		firstNamePanel.add(firstNameNextButton);

		//************ Last name tab elements ***************

		JPanel lastNamePanel = new JPanel();
		lastNamePanel.setBackground(UIManager.getColor("Tree.dropCellBackground"));
		tabbedPane.addTab("Nazwisko", null, lastNamePanel, null);
		lastNamePanel.setLayout(null);

		JLabel lblPodajSwojeNazwisko = new JLabel("Podaj swoje nazwisko:");
		lblPodajSwojeNazwisko.setBounds(10, 12, 137, 20);
		lblPodajSwojeNazwisko.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lastNamePanel.add(lblPodajSwojeNazwisko);

		lastNameText = new JFormattedTextField();
		lastNameText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				if (lastNameText.getText().length() == 1)
					lastNameText.setText(capitalize(lastNameText.getText()));
				else if (lastNameText.getText().length() > 1)
					lastNameCheck = true;
				else
					lastNameCheck = false;
			}
		});
		lastNameText.addActionListener(lastNameAction);
		lastNameText.setDocument(getDocument(Constants.lastNameRegex));
		lastNameText.setBounds(10, 44, 269, 21);
		lastNameText.setFont(new Font("Dialog", Font.PLAIN, 12));
		lastNameText.setColumns(10);
		lastNamePanel.add(lastNameText);

		JButton lastNameNextButton = new JButton("Dalej");
		lastNameNextButton.addActionListener(lastNameAction);
		lastNameNextButton.setBounds(169, 174, 110, 27);
		lastNameNextButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lastNamePanel.add(lastNameNextButton);

		//************ Address tab elements *****************

		JPanel addressPanel = new JPanel();
		addressPanel.setBackground(UIManager.getColor("Tree.dropCellBackground"));
		tabbedPane.addTab("Adres", null, addressPanel, null);
		addressPanel.setLayout(null);

		JLabel lblPodajSwjAdres = new JLabel("Podaj swój adres:");
		lblPodajSwjAdres.setBounds(10, 12, 109, 20);
		lblPodajSwjAdres.setFont(new Font("Tahoma", Font.PLAIN, 14));
		addressPanel.add(lblPodajSwjAdres);

		JLabel lblUlica = new JLabel("Ulica:");
		lblUlica.setBounds(10, 47, 28, 15);
		lblUlica.setFont(new Font("Tahoma", Font.PLAIN, 12));
		addressPanel.add(lblUlica);

		streetText = new JTextField();
		streetText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				if (streetText.getText().length() == 1 && e.getKeyChar() == ' ')
					streetText.setText("");
				else if (streetText.getText().length() == 1)
					streetText.setText(capitalize(streetText.getText()));
				else if (streetText.getText().length() > 2) {
					addressCheck[0] = true;
				} else
					addressCheck[0] = false;
			}
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				if (streetText.getText().length() == 1 && e.getKeyChar() == ' ')
					streetText.setText("");
			}
		});
		streetText.addActionListener(addressAction);
		streetText.setDocument(getDocument(Constants.cityStreetRegex));
		streetText.setBounds(44, 44, 235, 21);
		streetText.setFont(new Font("Dialog", Font.PLAIN, 12));
		streetText.setColumns(10);
		addressPanel.add(streetText);

		JLabel lblNumer = new JLabel("Numer:");
		lblNumer.setBounds(10, 80, 40, 15);
		lblNumer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		addressPanel.add(lblNumer);

		streetNumText = new JTextField();
		streetNumText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				if (streetNumText.getText().length() >= 1) {
					addressCheck[1] = true;
				} else
					addressCheck[1] = false;
			}
		});
		streetNumText.addActionListener(addressAction);
		streetNumText.setBounds(56, 77, 45, 21);
		streetNumText.setDocument(getDocument(Constants.numberRegex));
		streetNumText.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		streetNumText.setColumns(10);
		addressPanel.add(streetNumText);

		JLabel lblMieszkanie = new JLabel("Mieszkanie:");
		lblMieszkanie.setBounds(112, 80, 60, 15);
		lblMieszkanie.setFont(new Font("Tahoma", Font.PLAIN, 12));
		addressPanel.add(lblMieszkanie);

		flatNumText = new JTextField();
		flatNumText.addActionListener(addressAction);
		flatNumText.setDocument(getDocument(Constants.numberRegex));
		flatNumText.setBounds(178, 77, 47, 21);
		flatNumText.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		flatNumText.setColumns(10);
		addressPanel.add(flatNumText);

		JLabel lblKodPocztowy = new JLabel("Kod pocztowy:");
		lblKodPocztowy.setBounds(10, 113, 82, 15);
		lblKodPocztowy.setFont(new Font("Tahoma", Font.PLAIN, 12));
		addressPanel.add(lblKodPocztowy);

		postcodeText = new JFormattedTextField(new Constants().postcodeMask);
		postcodeText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				if (postcodeText.getText().trim().length() == 6) {
					addressCheck[2] = true;
				} else
					addressCheck[2] = false;
			}
		});
		postcodeText.addActionListener(addressAction);
		postcodeText.setBounds(98, 110, 50, 21);
		postcodeText.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		postcodeText.setColumns(2);
		addressPanel.add(postcodeText);

		JLabel lblMiejscowo = new JLabel("Miejscowość:");
		lblMiejscowo.setBounds(10, 146, 71, 15);
		lblMiejscowo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		addressPanel.add(lblMiejscowo);

		cityText = new JFormattedTextField();
		cityText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				if (cityText.getText().length() == 1 && e.getKeyChar() == ' ')
					cityText.setText("");
				else if (cityText.getText().length() == 1)
					cityText.setText(capitalize(cityText.getText()));
				else if (streetText.getText().length() > 2) {
					addressCheck[3] = true;
				} else
					addressCheck[3] = false;
			}
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				if (cityText.getText().length() == 1 && e.getKeyChar() == ' ')
					cityText.setText("");
			}
		});
		cityText.addActionListener(addressAction);
		cityText.setDocument(getDocument(Constants.cityStreetRegex));
		cityText.setBounds(86, 143, 193, 21);
		cityText.setFont(new Font("Dialog", Font.PLAIN, 12));
		cityText.setColumns(10);
		addressPanel.add(cityText);

		JButton addressNextButton = new JButton("Dalej");
		addressNextButton.addActionListener(addressAction);
		addressNextButton.setBounds(169, 174, 110, 27);
		addressNextButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		addressPanel.add(addressNextButton);

		//************ Phone number tab elements ************

		JPanel phoneNumPanel = new JPanel();
		phoneNumPanel.setBackground(UIManager.getColor("Tree.dropCellBackground"));
		tabbedPane.addTab("Numer telefonu", null, phoneNumPanel, null);
		phoneNumPanel.setLayout(null);

		JLabel lblPodajSwjNumer = new JLabel("Podaj swój numer telefonu:");
		lblPodajSwjNumer.setBounds(10, 12, 169, 20);
		lblPodajSwjNumer.setFont(new Font("Tahoma", Font.PLAIN, 14));
		phoneNumPanel.add(lblPodajSwjNumer);

		phoneNumText = new JFormattedTextField(new Constants().phoneMask);
		phoneNumText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				if (phoneNumText.getText().replace(" ", "").length() == 11)
					phoneNumCheck = true;
				else
					phoneNumCheck = false;
			}
		});
		phoneNumText.addActionListener(summaryAction);
		phoneNumText.setBounds(10, 44, 95, 21);
		phoneNumText.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		phoneNumText.setColumns(10);
		phoneNumPanel.add(phoneNumText);

		//************ Summary button ***********************

		JButton summaryButton = new JButton("Wyświetl podsumowanie");
		summaryButton.addActionListener(summaryAction);
		summaryButton.setBounds(58, 265, 206, 29);
		summaryButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		mainPanel.add(summaryButton);
	}
}
