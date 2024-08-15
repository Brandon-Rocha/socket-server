# socket-server
We use a PrintStream to send the results of the
given command back to the client and a BufferedReader to receive requests from the client. A
switch case is used to determine what data to collect and send back. It also echoes the results of
the given request to make sure that the results and what is sent to the server are the same.
Because we thought this was the best method for the server side we designed the client side to
use a BufferedReader. We did this to get input from the user(command from the menu and how
many times to execute this command). The execute times are then used to run a for loop that
creates and joins two different types of threads by implementing two runnable classes
(SendRequest, Receive Request). 
