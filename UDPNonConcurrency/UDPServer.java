import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

class UDPServer {
    public static void main(String[] args) throws Exception {
        String sentence;
        String capitalizedSentence; 

        // ? Create Main Server Socket & Instantiate the Server.
        DatagramSocket serverSocket = new DatagramSocket(9876);
        
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        while(true) {
            System.out.println("Waiting for connection");

            // ? Allocated Space for received Datagram & 
            // ? Received the Input Datagram from user.
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);

            // ? Break down the Input Datagram; Data, IP address, Port.
            sentence = new String(receivePacket.getData());
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            
            // ? Modify the Client's Input & 
            // ? Turn into Bytes Stream for sending.
            capitalizedSentence = sentence.toUpperCase();
            sendData = capitalizedSentence.getBytes();

            // ? Create Datagram to send the modified data &
            // ? Send it to the Client.
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            serverSocket.send(sendPacket);
        }
    }    
}
