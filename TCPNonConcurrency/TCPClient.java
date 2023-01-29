import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.Buffer;
import java.util.Scanner;

class TCPClient {
    public static void main(String[] args) {
        String sentence;
        String modifiedSentence;
        
        // ! Client Socket
        Socket clientSocket = null;
        
        // ! Input/Output Stream
        BufferedReader inFromUser = null; // Can use Scanner: Scanner inFromUser = null;
        BufferedReader inFromServer = null; // Can use Scanner: Scanner inFromServer = null;
        DataOutputStream outToServer = null;

        try {
            // ? Create Input Stream to get input from user.
            inFromUser = new BufferedReader(new InputStreamReader(System.in));
            // inFromUser = new Scanner(System.in);

            // ? Create Client Socket & Try connect to server.
            clientSocket = new Socket("localhost", 56789);

            // ? Create Output Stream & Attached to Socket
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            // ? Create Input Stream & Attached to Socket (From Server, not User)
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            // inFromServer = new Scanner(clientSocket.getInputStream());

            // ? Get the Input from User into sentence
            System.out.print("Please enter your message: ");
            sentence = inFromUser.readLine(); // inFromUser.nextLine();

            // ? Write the Input from User to the Server through Output Stream.
            outToServer.writeBytes(sentence + "\n");

            // ? Read the Input from Server.
            modifiedSentence = inFromServer.readLine(); // inFromServer.nextLine();
            System.out.println("FROM SERVER: " + modifiedSentence);
        }
        catch (IOException e) {
            System.out.println("Error occurred: Closing the connection.");
        }
        finally {
            try {
                if (inFromServer != null)
                    inFromServer.close();
                if (outToServer != null)
                    outToServer.close();
                if (clientSocket != null)
                    clientSocket.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
