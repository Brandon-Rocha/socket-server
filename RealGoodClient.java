import java.net.*;
import java.io.*;
 
public class RealGoodClient {
 
    public static void main(String[] args) {
    	int port = 1052;
    	String address = "139.62.210.155";
 
        try {
		//read data from keyboard
		BufferedReader keyboardInput = new BufferedReader(new InputStreamReader(System.in));
		String str = null;

		//repeat as long as 7 is not typed
		while(str != "7"){
			System.out.println("");
			System.out.printf("1) Date and Time\n"
					+ "2) Uptime\n"
					+ "3) Memory Use\n"
					+ "4) Netstat\n"
					+ "5) Current Users\n"
					+ "6) Running Processes\n"
					+ "7) Quit\n"
					+ "Enter a number from the above menu: ");
			str = keyboardInput.readLine();
			System.out.println("");
			if(str.equals("7") ){
				Socket tSocket = new Socket(address, port);
				SendRequest send = new SendRequest(tSocket);
				Thread sendT = new Thread(send, str);
				sendT.start();
				sendT.join();
				break;
			}
			System.out.print("Enter the number of times you wish to run this command (1-25): ");
			String intParse = keyboardInput.readLine();
			System.out.println("");
			int executeTimes = Integer.parseInt(intParse);
			long averageStart = System.nanoTime();
			for (int i = 0 ; i < executeTimes; i++){
				Socket threadSocket = new Socket(address, port);
				
				SendRequest doSend = new SendRequest(threadSocket);
				Thread sendingThread = new Thread(doSend, str);
				
				ReceiveRequest doReceive = new ReceiveRequest(threadSocket);
				Thread receivingThread = new Thread(doReceive, str);
				
				long start = System.nanoTime();
				sendingThread.start();
				sendingThread.join();
				
				receivingThread.start();
				receivingThread.join();
				long end = System.nanoTime();
				System.out.println("Thread " + i + " took " + ((end - start)/1000000) + " milliseconds to complete");
				
			}//end for loop
			System.out.println("");
			long averageEnd = System.nanoTime();
			
			System.out.println("Average time for " + executeTimes +" request(s) was/is " + ( ((averageEnd - averageStart)/ executeTimes) / 1000000) + " milliseconds" );
		}//end while str != 7
		System.out.println("Goodbye");
		System.exit(0);
 
        } catch (UnknownHostException ex) {
 
            System.out.println("Exception Thrown, here is the info: " + ex.getMessage());
            ex.printStackTrace();
 
        } catch (IOException ex) {
 
            System.out.println("I/O error: " + ex.getMessage());
        }
        catch(IllegalArgumentException ee) {
        	System.out.println("IllegalArgumentException");
        }
        catch (InterruptedException ie) {
        	System.out.println("Interrupted");
        }
    }
}