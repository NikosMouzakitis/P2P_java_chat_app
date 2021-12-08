import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.Socket;

public class P2p {

	public static void main(String args[]) throws IOException, Exception {
		System.out.println("Enter username and port to use(space separated)\n");
		BufferedReader bufferedReader;
		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		String[] entryValues = bufferedReader.readLine().split(" ");

		int myport;
		String myuser="";

		myport = Integer.valueOf(entryValues[1]);
		myuser=entryValues[0];

		ServerThread st = new ServerThread(myport);
		st.start();
//		System.out.println("Success on creating ServerThread's class object");
		new P2p().updateListenToPeers(bufferedReader, entryValues[0],st);
	}

	//updates the "from which peers" we get messages from.
	public void updateListenToPeers(BufferedReader bufferedReader, String username, ServerThread serverThread) throws Exception {

		System.out.println("enter (space separated) hostname:port to connect with");
		System.out.println("press (s) to skip");
		String input = bufferedReader.readLine();
		String[] inputVal = input.split(" ");

		if(!input.equals("s")) {

			for(int i = 0; i < inputVal.length; i++) {
				String[] address = inputVal[i].split(":");
				Socket socket = null;
				try {
					socket = new Socket(address[0],Integer.valueOf(address[1]));
	///				System.out.println("Starting a socket for "+address[0] + " "+address[1]);
					new PeerThread(socket).start();
	////				System.out.println("Socket success for "+address[0] + " "+address[1]);

				} catch(Exception e) {
					if(socket != null)
						socket.close();
					else
						System.out.println("Invalid input,skips next step");
				}
			}
	//		System.out.println("Call to communicate() from updateListenToPeers()");
			communicate(bufferedReader, username, serverThread);
		}
	}

	public void communicate(BufferedReader br, String username, ServerThread serverThread) throws IOException, Exception {
	
	//	System.out.println("[communicate] ");

		boolean flag=true;

		while(flag) {
			System.out.println("e: exit, c:updatePeers, <enter your message>");

			String message = br.readLine();
			if(message.equals("e")) {
				flag=false;
				break;
			} else if(message.equals("c")) {
				updateListenToPeers(br,username, serverThread);
			} else {
				//here is the actual send of message.
			//	StringWriter stringWriter= new StringWriter();
				//create message..
			//	System.out.println("Message to send: "+message);
				String mts;
				//x*x is the discriminant in between the username and the actual message.
				mts=username+"~"+message;

			//	System.out.println("[dbg]communicate() call to sendMessage: "+mts);
				//serverThread.sendMessage(stringWriter.toString());
				serverThread.sendMessage(mts);
			}
		}

		System.exit(0);
	}

}

