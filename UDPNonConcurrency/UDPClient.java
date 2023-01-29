import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class UDPClient {
    public static void main(String[] args) throws Exception {
        String sentence;
        String modifiedSentence;

        // ? Create Input Stream to get input from user.
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        // ? Create Client Socket.
        DatagramSocket clientSocket = new DatagramSocket();

        // ? Translate hostname to IP address, using DNS.
        InetAddress IPAddress = InetAddress.getByName("localhost");

        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];
        
        // ? Get the Input from User into sentence
        System.out.print("Please enter your message: ");
        sentence = inFromUser.readLine();
        
        sendData = sentence.getBytes();

        // ? Create Datagram with Data-to-Send; length, IP address, port.
        // ? Then Send this Packet with Client Socket.
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
        clientSocket.send(sendPacket);
        
        // ? Read the Datagram from Server.
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);
        modifiedSentence = new String(receivePacket.getData());
        System.out.println("FROM SERVER:" + modifiedSentence);

        clientSocket.close();
    }    
}
