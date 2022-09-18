package cop2805;
import java.net.*;
import java.io.*;
import java.nio.charset.*;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ClientData {

    public static void main(String[] args) {
        JPanel panel = new JPanel(); //create JPanel container
        panel.setLayout(new GridLayout(2,2)); //setting panel layout, 2 rows, 2 columns
        
        JLabel topLeft = new JLabel("Enter Fibonacci Number: "); //creating label for UI
        JTextField tf = new JTextField(); //creating new text object for user input
        
        JButton btn = new JButton("Calculate"); //creating button for calculation
        JLabel bottomRight = new JLabel("Server Answer: "); //
        
        //adding objects to the created panels
        panel.add(topLeft);
        panel.add(tf);
        panel.add(btn);
        panel.add(bottomRight);
        
        btn.addActionListener(new ActionListener() { //creating ActionListener for user interaction
           
            public void actionPerformed(ActionEvent e) { //code that reacts to the user input
                try {
                    String userString = tf.getText(); //receives String/user input
                    Socket connection = new Socket("127.0.0.1", 1236); //connection socket
                    
                    InputStream input = connection.getInputStream(); //Returning input stream from open connection
                    OutputStream output = connection.getOutputStream(); //Returning output stream from open connection
                    output.write(userString.length()); //writing user string to output file descriptor
                    output.write(userString.getBytes()); // writing user string to output file descriptor 
                    
                   try {
                    int n = input.read(); //reading in user input as int n
                    byte[] data = new byte[n]; //array
                   
                    input.read(data); //reading in data
                    String serverResponse = new String(data, StandardCharsets.UTF_8); //setting string format as UTF_8
                    
                    bottomRight.setText("Server Answer: " + serverResponse); //setting response to Jpanel
            
                   } catch(NegativeArraySizeException nase) {
                    }
                    
                    if (!connection.isClosed());
                    connection.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    System.exit(0);
                }
            }
        }); //close ActionListener
        
        JFrame frame = new JFrame("Final Project Client"); //naming the program on top of window
        //frame.setSize(400, 170); //took out and used frame.pack(); for better user experience
        frame.add(panel); //adding frames to panel
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closing program upon frame exit
        frame.pack(); //sizing frame based on user screen resolution
        frame.setLocationRelativeTo(null); //set to open on middle of screen
        frame.setVisible(true); 
        
    }
}

