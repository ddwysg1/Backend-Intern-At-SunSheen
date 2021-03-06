package chatApp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class chatClient {
	static JFrame chatWindow = new JFrame("Chat Application");
	static JLabel userLabel = new JLabel("User:");
	static JTextField userText = new JTextField(20); 
	static JTextArea chatArea = new JTextArea(22, 40);
	static JTextField textField = new JTextField(40);
	static JScrollPane chatField = new JScrollPane(chatArea);
	static JButton connectButton = new JButton("Connect");
	static JButton sendButton = new JButton("Send");
	static JButton uploadButton = new JButton("upload");
	static BufferedReader in;
	static PrintWriter out;
	static JLabel nameLabel = new JLabel("         ");
    
	static OutputStream o;
	static InputStream i;
	static Socket soc;
	static String name;
	static String address;
	static String str;
	
	chatClient() {
		chatWindow.setSize(600,400);
		
		JPanel panel = new JPanel();
		chatWindow.add(panel);
		placeComponent(panel);
		chatWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chatWindow.setVisible(true);
		textField.setEditable(false);
        chatArea.setEditable(false);
        connectButton.addActionListener(new connectListener());
        sendButton.addActionListener(new chatListener());
        uploadButton.addActionListener(new uploadListener());
	}
	
	private void placeComponent(JPanel panel) {
		panel.setLayout(null);
		
		nameLabel.setBounds(230, 10, 200, 30);
		panel.add(nameLabel);
		userLabel.setBounds(40, 50, 50, 25);
		panel.add(userLabel);
		userText.setBounds(80, 50, 165, 25);
		panel.add(userText);
		
		connectButton.setBounds(350, 50, 180, 25);
		panel.add(connectButton);
		
		chatField.setBounds(40, 80, 520, 180);
		panel.add(chatField);
		
		textField.setBounds(40, 270, 520, 30);
		panel.add(textField);
		
		uploadButton.setBounds(80, 310, 80, 30);
		panel.add(uploadButton);
		
		sendButton.setBounds(480, 310, 80, 30);
		panel.add(sendButton);
	}
	
	void startChat() throws Exception {
       String ipAddress = JOptionPane.showInputDialog(
                chatWindow,
                "Enter IP Address:",
                "IP Address Required!!",
                JOptionPane.PLAIN_MESSAGE);  
       soc = new Socket(ipAddress, 9806);
       in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
       o = soc.getOutputStream();
       out = new PrintWriter(o, true);
       while (true){
         str = in.readLine();
         if (str.equals("NAMEREQUIRED")){
	      	  JOptionPane.showMessageDialog(
	    			  chatWindow, 
	    			  "NAMEREQUIRED", 
	    			  "NAMEREQUIRED",
	    			  JOptionPane.INFORMATION_MESSAGE);
           } else if(str.equals("NAMEALREADYEXISTS")) {
        	   JOptionPane.showMessageDialog(
        			   chatWindow, 
        			   "NAMEALREADYEXISTS", 
        			   "NAMEALREADYEXISTS",
        			   JOptionPane.ERROR_MESSAGE);
           } else if (str.startsWith("NAMEACCEPTED")) {
        	   userText.setEditable(false);
               textField.setEditable(true);
               name = str.substring(12);
               nameLabel.setText("You are logged in as: " + name);
           } else {
        	   chatArea.append(str + "\n");
           }
       }
    }
	
	public static void main(String args[]) throws Exception {
		chatClient client = new chatClient();
		client.startChat();
	}
	
}

class connectListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
          chatClient.out.println(chatClient.userText.getText());
    }
}

class uploadListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
    	  String path = chatClient.textField.getText();
    	  chatClient.textField.setText("");
    	  try {
    		  chatClient.out.println("start to upload " + path);
    		  sendFile.start();
    		  sendFile.sendData(path);
    		  chatClient.out.println(path + " has been successfully uploaded");
    	  } catch (Exception ex){  
    	  } 
    }
}

class chatListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
          chatClient.out.println(chatClient.textField.getText());
          chatClient.textField.setText("");
    }
}
