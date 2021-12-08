import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.ArrayList;  
import java.io.PrintWriter;
 
public class ServerThread extends Thread {
 
    private ServerSocket ss;
   // private static ArrayList<String> sendPorts = new ArrayList<String>(); //holds the ports that we are sending too.
    private ArrayList<Socket> sockets = new ArrayList<Socket>(); //list holding all the client sockets connected to the p2p app.
   
    public ServerThread(int port) throws IOException {
        ss = new ServerSocket((port));
//	System.out.println("Success on creation of server socket on port: "+port);
    }
    
    public void run() {
	
        try{
            while(true) {
 //               System.out.println("serverThread run() prior to accepting a new conn.");
                //in order to obtain information about who is connected in our server socket.
                Socket connected_socket;
                connected_socket = ss.accept();
       //         ServerThreadThread serverThreadThread = new ServerThreadThread(connected_socket,this);
                //ServerThreadThread serverThreadThread = new ServerThreadThread(ss.accept(),this);
                
    //            System.out.println();
   //             System.out.println("ServerSocket accept()");
                InetSocketAddress socketAddress = (InetSocketAddress) connected_socket.getRemoteSocketAddress();
                String clientIpAddress = socketAddress.getAddress().getHostAddress();
                int  clientPort = socketAddress.getPort();
		//sendPorts.add(String.valueOf(clientPort));
		sockets.add(connected_socket);
//		System.out.println("added client socket into sockets arraylist.");
 //               System.out.println("clients IP addr: "+clientIpAddress+" / Client's port: "+clientPort);
                
  //              serverThreadThreads.add(serverThreadThread);
   //             serverThreadThread.start();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void sendMessage(String message) throws IOException {
  //      System.out.println("[dbg]sendMessage() message: "+message);
         
	for(int i = 0; i < sockets.size(); i++)
	{
//		System.out.println("sending via the PrintWriter.");
		Socket tmp = sockets.get(i);
		PrintWriter out = new PrintWriter(tmp.getOutputStream(),true);
		out.println(message);
//		System.out.println("sent: "+message);
	}
    }
}
