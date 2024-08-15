import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReceiveRequest implements Runnable {
	private BufferedReader reader = null;
	private Socket socket;
	private String fromServer;
	   
	ReceiveRequest(Socket socket){
	this.socket = socket;
	}
	
	public void run() {
		try {
			//to read data coming from the server
			this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			while( (fromServer = reader.readLine()) != null) {
				System.out.println(fromServer);
			}
		}
		catch (Exception e){
            System.out.println("Exception Thrown, here is the info: " + e.getMessage());
            e.printStackTrace();
		}
	}
}//
