import java.io.DataOutputStream;
import java.net.Socket;

	public class SendRequest implements Runnable {
		private Socket socket;
		private DataOutputStream sendOut = null;
		
		SendRequest(Socket socket){
		this.socket = socket;
		}
		
		public void run() {
			try {
				//use to send request to server
				 sendOut = new DataOutputStream(socket.getOutputStream());

				//send to server
				sendOut.writeBytes(Thread.currentThread().getName() + "\n");
				System.out.println("");
				sendOut.flush();
			}//end try
			catch (Exception e){
	            System.out.println("Exception Thrown, here is the info: " + e.getMessage());
	            e.printStackTrace();
			}//end catch
		}//end run()
	}//end class
