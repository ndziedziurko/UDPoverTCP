package relay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class MainRelay {
    protected static String destIP;
    protected static Socket socketTCP;
    protected static DatagramSocket socketUDP;

    static {
        try {
            socketUDP = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public MainRelay() throws UnknownHostException{
        (new TCPthread()).start();
        (new UDPthread()).start();
    }


    //waits for synchronization
    public static void configure() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socketTCP.getInputStream()));
        String line = in.readLine();

        if(line.contains("destination")) {
            System.out.println("relay got synchronization: " + line);
            String ip[] = line.split(" ");
            destIP = ip[1];
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(55555);
        System.out.println("Relay listens on TCP port: " + server.getLocalPort()+ ", server IP: " + server.getInetAddress().getLocalHost());
        while(true) {
            socketTCP = server.accept();
            configure();
            MainRelay start = new MainRelay();
        }
    }
}
