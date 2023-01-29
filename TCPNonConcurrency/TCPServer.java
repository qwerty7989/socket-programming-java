import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

class TCPServer {
    public static void main(String[] args) {
        String clientSentence;
        String capitalizedSentence;
       
        // ! Main server and connection socket
        ServerSocket welcomeSocket = null;
        Socket connectionSocket = null;

        // ! Input/Output Stream
        BufferedReader inFromClient = null; // Can use Scanner: Scanner inFromClient = null;
        DataOutputStream outToClient = null;

        // ? Create Main Server Socket & Instantiate the Server.
        try {
            welcomeSocket = new ServerSocket(56789);
        }
        catch (IOException e) {
            System.out.println("Cannot create a welcome socket.");
            System.exit(1);
        }

        while(true) {
            try {
                System.out.println("Waiting for connection");
                
                // ? Wait, for the Client to contact to the Main Socket.
                // ? Then create a dedicated socket for it.
                connectionSocket = welcomeSocket.accept();

                // ? Create Input Stream that receive from Client.
                inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                // inFromClient = new Scanner(connectionSocket.getInputStream());
                
                // ? Create Output Stream that'll be send to Client
                outToClient = new DataOutputStream(connectionSocket.getOutputStream());
        
                // ? Read the Input from Client Stream.
                clientSentence = inFromClient.readLine(); // inFromClient.nextLine();
                System.out.println("Incoming input: " + clientSentence);

                // ? Modify the Client's Input & Send it back through Output Stream.
                capitalizedSentence = clientSentence.toUpperCase() + '\n';
                outToClient.writeBytes(capitalizedSentence);
                System.out.println("Outgoing output: " + capitalizedSentence);
            }
            catch (IOException e) {
                System.out.println("Error cannot create connection socket with this connection.");
            }
            finally {
                try {
                    if (inFromClient != null)
                        inFromClient.close();
                    if (outToClient != null)
                        outToClient.close();
                    if (connectionSocket != null)
                        connectionSocket.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
