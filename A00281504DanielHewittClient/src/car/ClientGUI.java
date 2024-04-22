package car;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class ClientGUI extends JFrame implements ActionListener{
	
	private Container content;
	
	private JPanel detailsPanel;
	private JScrollPane dbContentsPanel;
	
	private Border lineBorder;
	
	private JLabel IDLabel = new JLabel("ID");
	private JLabel BrandLabel = new JLabel("Brand");
	private JLabel ModelLabel = new JLabel("Model");
	private JLabel DoorsLabel = new JLabel("Doors");
	
	private JTextField IDText = new JTextField(10);
	private JTextField BrandText = new JTextField(10);
	private JTextField ModelText = new JTextField(10);
	private JTextField DoorsText = new JTextField(10);
	
    protected JTable TableofDBContents=new JTable();
	
	private JButton insertButton = new JButton("Insert");
	private JButton updateButton = new JButton("Update");
	private JButton deleteButton = new JButton("Delete");
	private JButton getCarsButton = new JButton("Get Cars");
	private JButton clearButton = new JButton("Clear");
	
	public ClientGUI() {
		this.setTitle("Car Client");
		this.setResizable(true);
		this.setSize(1500, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setupActionListeners();
		GUISetup();
		
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object target = e.getSource();
		
		if (target.equals(insertButton)) {
			insert();
		}
		if (target.equals(updateButton)) {
			update();
		}
		if (target.equals(deleteButton)) {
			delete();
		}
		if (target.equals(getCarsButton)) {
			export();
		}
		if (target.equals(clearButton)) {
			clear();
		}
	}
	
	private void GUISetup() {
		
		content=getContentPane();
		content.setLayout(null);
		content.setBackground(Color.LIGHT_GRAY);
		lineBorder = BorderFactory.createEtchedBorder(15, Color.red, Color.black);
		
		detailsPanel = new JPanel();
		detailsPanel.setLayout(new GridLayout(4,2));
		detailsPanel.setBackground(Color.LIGHT_GRAY);
		detailsPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "CRUD Actions"));
		detailsPanel.add(IDLabel);
		detailsPanel.add(IDText);
		detailsPanel.add(BrandLabel);
		detailsPanel.add(BrandText);
		detailsPanel.add(ModelLabel);
		detailsPanel.add(ModelText);
		detailsPanel.add(DoorsLabel);
		detailsPanel.add(DoorsText);
        detailsPanel.setSize(360, 300);
        detailsPanel.setLocation(3,0);
		content.add(detailsPanel);
		
		insertButton.setSize(100, 30);
		insertButton.setLocation(370, 10);
		content.add(insertButton);
		
		updateButton.setSize(100, 30);
		updateButton.setLocation(370, 110);
		content.add(updateButton);
		
		getCarsButton.setSize(100, 30);
		getCarsButton.setLocation(370, 160);
		content.add(getCarsButton);
		
		deleteButton.setSize(100, 30);
		deleteButton.setLocation(370, 60);
		content.add(deleteButton);
		
		clearButton.setSize(100, 30);
		clearButton.setLocation(370, 210);
		content.add(clearButton);
		
        TableofDBContents.setPreferredScrollableViewportSize(new Dimension(900, 300));

		dbContentsPanel = new JScrollPane(TableofDBContents, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		dbContentsPanel.setBackground(Color.lightGray);
		dbContentsPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "Database Content"));
		dbContentsPanel.setSize(700, 300);
		dbContentsPanel.setLocation(477, 0);
		content.add(dbContentsPanel);
		
		setSize(982, 645);
		setVisible(true);
	}
	
	private void setupActionListeners() {
		insertButton.addActionListener(this);
		updateButton.addActionListener(this);
		deleteButton.addActionListener(this);
		getCarsButton.addActionListener(this);
		clearButton.addActionListener(this);
	}
	
	private void insert() {
		try {
			String apiUrl = "REPLACE";
			
	        String xmlData = "<car>" +
                    "<id>" + IDText.getText() + "</id>" +
                    "<brand>" + BrandText.getText() + "</brand>" +
                    "<model>" + ModelText.getText() + "</model>" +
                    "<doors>" + DoorsText.getText() + "</doors>" +
                    "</car>";
	        
	        sendHttpRequest(apiUrl, "POST", xmlData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void update() {
		try {
			String apiUrl = "REPLACE" + IDText.getText();
			
	        String xmlData = "<car>" +
                    "<id>" + IDText.getText() + "</id>" +
                    "<brand>" + BrandText.getText() + "</brand>" +
                    "<model>" + ModelText.getText() + "</model>" +
                    "<doors>" + DoorsText.getText() + "</doors>" +
                    "</car>";
	        
	        sendHttpRequest(apiUrl, "PUT", xmlData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void delete() {
		try {
			String apiUrl = "REPLACE" + IDText.getText();
			
			sendHttpRequest(apiUrl, "DELETE", null);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void export() {
		try {
			String apiUrl = "REPLACE";
			
			String response = sendHttpRequest(apiUrl, "GET", null);
			
			//Process data
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void clear() {
		try {
			IDText.setText("");
			BrandText.setText("");
			ModelText.setText("");
			DoorsText.setText("");
		} catch (Exception e) {
			System.out.println("Error clearing text fields");
		}
	}
	
	private String sendHttpRequest(String apiUrl, String method, String requestData) throws Exception {
		
		URL url = new URL(apiUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(method);
		
		conn.setRequestProperty("Content-Type", "application/xml");
		conn.setDoOutput(true);
		
		if (requestData != null) {
			try (OutputStream os = conn.getOutputStream()) {
				os.write(requestData.getBytes());
				os.flush();
			}
		}
		
		StringBuilder response = new StringBuilder();
		try(BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
			String line;
			while((line = in.readLine()) != null) {
				response.append(line);
			}
		}
		
		conn.disconnect();
		return response.toString();
	}
}
