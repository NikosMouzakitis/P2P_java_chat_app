import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class PeerThread extends Thread {

	private BufferedReader br;
	private Socket socket;

	public PeerThread(Socket socket) throws IOException {
		this.socket = socket;
		br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	public void run() {

		boolean flag=true;
		while(flag) {
			try {
				//here we read and PRINT the message received.
				String recv = br.readLine();
				//tilda is used to discriminate in between username and actual message.
				String[] disc = recv.split("~");
				String actual_msg=disc[1];
				String uname = disc[0];
				System.out.println("[ "+uname+" ]: "+actual_msg);	

			} catch(Exception e) {
				flag=false;
				//      System.out.println("FLAG IS FALSEEEEEEEE!");
			}
		}
	}
}
