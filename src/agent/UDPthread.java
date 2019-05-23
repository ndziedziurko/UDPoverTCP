package agent;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import static UDP.UDP.MAX_DATAGRAM_SIZE;

public class UDPthread extends Thread {
    private DatagramSocket serverUDP;
    private Port port;
    private int clientPort;
    private InetAddress clientIp;
    private PrintWriter out;
    boolean exit = false;

    public UDPthread(Port port) throws SocketException {
        this.port = port;
        serverUDP = new DatagramSocket(port.getPort());
    }


    public void exit(){
        exit = true;
    }

    @Override
    public void run(){
        while(!exit){
            if(port.isUsed() == false){
                byte[] buff = new byte[MAX_DATAGRAM_SIZE];
                final DatagramPacket datagram = new DatagramPacket(buff, buff.length);

                try {
                    //receives message from sender
                    serverUDP.receive(datagram);
                    clientPort = datagram.getPort();
                    clientIp = datagram.getAddress();

                    MainAgent.clients.put(port.getPort(), new Client(clientPort, clientIp));
                    port.setUsed(true);

                    String received = new String(datagram.getData(), 0, datagram.getLength()).trim();
                    System.out.println("agent got message: "+ received + ", from: " + MainAgent.clients.get(port.getPort()));

                    //forwards message to relay
                    String message = port.getPort() + " " + received;
                    System.out.println("agent sent: " + message + " to relay");

                    out = new PrintWriter(MainAgent.socketTCP.getOutputStream(), true);
                    out.println(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        serverUDP.close();
    }
}
