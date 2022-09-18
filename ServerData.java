package cop2805;
import java.net.*;
import java.io.*;
import java.nio.charset.*;

public class ServerData {
    public static int fibonacci (int n) {
    	//used recursive solution
    	if(n == 0) {
            return 0;
        }
        if(n == 1) {
            return 1;
        }
        return fibonacci(n-1) + fibonacci(n-2);
    }

    public static void main(String[] args) {
        
        ServerSocket server = null;
        boolean shutdown = false;
        try {
            server = new ServerSocket(1236);
            System.out.println("Port bound. Accepting connection");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        
        while (!shutdown) {
            Socket client = null;
            InputStream input = null;
            OutputStream output = null;
            try {
                client = server.accept();
                input = client.getInputStream();
                output = client.getOutputStream();
                              
                int n = input.read();
                byte[] data = new byte[n];
                input.read(data);
     
                String clientInput = new String(data, StandardCharsets.UTF_8);
                clientInput.replace("\n", "");

                System.out.println("Client said: " + clientInput);
                
                try {
                	int num = Integer.parseInt(clientInput); 
                	String response = fibonacci(num)+"";
                	output.write(response.length());
                	output.write(response.getBytes());
                
                } catch (NumberFormatException e)  {
                	}
                
                client.close();
                	if (clientInput.equalsIgnoreCase("shutdown")) {
                    System.out.println("shutting down...");
                    shutdown = true;
                
                }
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            } 
        }
    }
}
