package chatApp;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.swing.*;

public class chatServer {
    static ArrayList<String> userNames = new ArrayList<String>();
    static ArrayList<PrintWriter> printWriters = new ArrayList<PrintWriter>();
    static JFrame serverWindow = new JFrame("Chat Server");
    static JLabel activeUserLabel = new JLabel("Active User:");
    static JTextArea showArea = new JTextArea(22, 40);
    static Socket soc;
    static int t = 1;
    static InputStream in;
    
    chatServer(){
    	serverWindow.setSize(600,400);
    	JPanel panel = new JPanel();
    	serverWindow.add(panel);
		placeComponent(panel);
		serverWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		serverWindow.setVisible(true);
		showArea.setEditable(false);
    }
    
    private void placeComponent(JPanel panel) {
    	panel.setLayout(null);
    	
    	activeUserLabel.setBounds(40, 30, 100, 20);
    	panel.add(activeUserLabel);
    	JScrollPane showField = new JScrollPane(showArea);
		showField.setBounds(40, 60, 520, 180);
		panel.add(showField);
    }
    
    public static void main(String args[]) throws Exception {
    	chatServer server = new chatServer();
    	System.out.println("Waiting for clients...");
    	ServerSocket ss = new ServerSocket(9806);
    	
    	while(true) {
    		soc = ss.accept();
    		System.out.println("Connection Established");
    		ConversationHandler handler = new ConversationHandler(soc);
            handler.start();
    	}	
    }
}
    
class ConversationHandler extends Thread {
	Socket socket;
    BufferedReader in;
    PrintWriter out;
    String name;
    PrintWriter pw;
    static FileWriter fw;
    static BufferedWriter bw;

    public ConversationHandler(Socket socket) throws IOException{
        this.socket = socket;
        fw = new FileWriter("D:\\SunSheen\\Backend\\chatApp\\ChatServer-Logs.txt",true);
        bw = new BufferedWriter(fw);
        pw = new PrintWriter(bw,true);
    }
    
    public void run(){
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            int count = 0;
            while (true){
               if(count > 0) {
                    out.println("NAMEALREADYEXISTS");
               } else {
                    out.println("NAMEREQUIRED");
               }
               name = in.readLine();
               if (name == null) {
                   return;
               }
               if (!chatServer.userNames.contains(name)) {
                  chatServer.userNames.add(name);
                  chatServer.showArea.append("User " + name + " is connected!!!" + "\n");
                  break;
               }
             count++;
           }

            out.println("NAMEACCEPTED" + name);
            chatServer.printWriters.add(out);

            while (true) {
                String message = in.readLine();
                if (message == null) {
                    return;
                }
                
                pw.println(name + ": " + message);
                for (PrintWriter writer : chatServer.printWriters) {
                    writer.println(name + ": " + message);
                }
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
   }
    
}