import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

class EchoThread extends Thread {
    private Socket connectionSocket;

    public EchoThread(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
    }

    public void run() {
        // ! Input/Output Stream
        BufferedReader inFromClient = null; // Can use Scanner: Scanner inFromClient = null;
        DataOutputStream outToClient = null;

        try {
            // ? Create Input Stream that receive from Client.
            inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            // inFromClient = new Scanner(connectionSocket.getInputStream());

            // ? Create Output Stream that'll be send to Client
            outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            // ? Read the Input from Client Stream.
            String clientSentence = inFromClient.readLine(); // inFromClient.nextLine();
            System.out.println("Incoming input: " + clientSentence);

            // ? Modify the Client's Input & Send it back through Output Stream.
            String capitalizedSentence = clientSentence.toUpperCase() + '\n';
            outToClient.writeBytes(capitalizedSentence);
            System.out.println("Outgoing output: " + capitalizedSentence);
        } catch (IOException e) {
            System.err.println("Closing Socket connection");
        } finally {
            try {
                if (inFromClient != null)
                    inFromClient.close();
                if (outToClient != null)
                    outToClient.close();
                if (connectionSocket != null)
                    connectionSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

class TCPConcurrentServer {
    public static void main(String[] args) {
       
        // ! Main server and connection socket
        ServerSocket welcomeSocket = null;
        
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
                Socket connectionSocket = welcomeSocket.accept();

                // ? Create new thread and start running it.
                EchoThread echoThread = new EchoThread(connectionSocket);
                echoThread.start();
            }
            catch (IOException e) {
                System.out.println("Cannot create this connection");
            }
        }
    }
}
