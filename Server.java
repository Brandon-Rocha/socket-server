import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Server {

	public static void main(String[] args) {
		if (args.length < 1) {
			return;
		}
		int port = Integer.parseInt(args[0]);
		
		String incoming = null, outgoing = null;
		int command = 0;
		
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			
			System.out.println("Server is listening on port: " + port);
			
			while(command != 7) {
				//get connection from client
	            Socket socket = serverSocket.accept();
	            System.out.println("New client connected");
	            
	            //send data to the client
		        PrintStream printStream = new PrintStream(socket.getOutputStream());
		        //read date from client
		        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	            
		        //get input from client
	            incoming = reader.readLine();
	            //print command that was received from client
	            System.out.println("Command:" + incoming);
	            command = Integer.parseInt(incoming);
	            if (command == 7) {
	            	break;
	            }
	            //switch statement to determine the command to perform
	            switch(command) {
	            case 1: //get the date and time
	            	outgoing = Server.getDT();
	            	System.out.println("Command result: " + outgoing);
            		printStream.println("Server:" + outgoing);
	            	break;
	            case 2: //get the uptime
	            	long upTime = Server.getUptime();
	            	System.out.println("Command result: " + upTime);
            		printStream.println("Server: " + upTime + " milliseconds");
	            	break;
	            case 3: //get memory use
	            	long memoryUse = Server.getMemoryUse();
	            	System.out.println("Command result: " + memoryUse);
            		printStream.println("Server: " + memoryUse + " bytes");
	            	break;
	            case 4: //run the netstat command
	            	try {
	            		//run the netstat command
	            		Process process = Runtime.getRuntime().exec("netstat -at");
	    		        //used to read the data received from running the command
	                    BufferedReader standardOutput = new BufferedReader(new 
	                         InputStreamReader(process.getInputStream()));
	            		
	            		// read the output from the command
	            		System.out.println("Here is the standard output of the command: ");
	            		while ((outgoing = standardOutput.readLine()) != null) {
	            			printStream.println(outgoing);
	            			System.out.println(outgoing);
	            			}//end while
	            		//printStream.println(outgoing);// this should be a flag for the print loop in client (outgoing == null)
	            		
	            		process.destroy();
	            		standardOutput.close();
	            	}//end try
	            		catch (IOException e) {
                            System.out.println("exception happened - here's what I know: ");
                            e.printStackTrace();
                            System.exit(-1);
                        }
	            	break;
	            case 5: //get the current users
	            	try {
	            		//run the current users command
	            		Process process = Runtime.getRuntime().exec("who");
	            		//used to read the data received from running the command
	                    BufferedReader standardOutput = new BufferedReader(new 
	                         InputStreamReader(process.getInputStream()));
	            		
	            		// read the output from the command
	            		System.out.println("Here is the standard output of the command: ");
	            		while ((outgoing = standardOutput.readLine()) != null) {
	            			printStream.println(outgoing);
	            			System.out.println(outgoing);
	            			}//end while
	            		//printStream.println(outgoing); // this should be a flag for the print loop in client (outgoing == null)
	            		
	            		process.destroy();
	            		standardOutput.close();
	            	}//end try
	            		catch (IOException e) {
                            System.out.println("exception happened - here's what I know: ");
                            e.printStackTrace();
                            System.exit(-1);
                        }
	            	break;
	            case 6: //get the current processes
	            	try {
	            		//run the current processes command
	            		Process process = Runtime.getRuntime().exec("ps -ef");
	            		//used to read the data received from running the command
	                    BufferedReader standardOutput = new BufferedReader(new 
	                         InputStreamReader(process.getInputStream()));
	            		
	            		// read the output from the command
	            		System.out.println("Here is the standard output of the command: ");
	            		while ((outgoing = standardOutput.readLine()) != null) {
	            			printStream.println(outgoing);
	            			System.out.println(outgoing);
	            			}//end while
	            		//printStream.println(outgoing); // this should be a flag for the print loop in client (outgoing == null)
	            		
	            		process.destroy();
	            		standardOutput.close();
	            	}//end try
	            	catch (IOException e) {
                            System.out.println("That was not a valid menu option, please enter a number between 1 and 6. Or you may enter 7 to quit.");
                        }
	            	break;
	            case 7:
	   	         System.out.println("Goodbye!");
	   	         socket.close();
		         serverSocket.close();
		         System.exit(0);
	            	break;
	            default:
	            	printStream.println("That was not a valid menu option, please enter a number between 1 and 6. Or you may enter 7 to quit.");
	            	break;
	            }//end switch
	            
	         reader.close();
	         printStream.close();
	         
			}//end while command != 7
		}//end try
		catch (IOException e) {
			
		}//end catch

	}//end main

	public static String getDT() {
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formatDT = dateTime.format(formatObj);
		return formatDT;
	}//end getDT

	public static long getUptime() {
		RuntimeMXBean rBean = ManagementFactory.getRuntimeMXBean();
		long upTime = rBean.getUptime();
	
		return upTime;
	}//end getUptime

	public static long getMemoryUse() {
		Runtime.getRuntime().gc();
		long memory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		return memory;
	}//end getMemoryUse
}//end class
