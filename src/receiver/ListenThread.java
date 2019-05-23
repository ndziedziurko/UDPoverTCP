package receiver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import static UDP.UDP.MAX_DATAGRAM_SIZE;

public class ListenThread extends Thread{
    DatagramSocket server;
    byte[] buff = new byte[MAX_DATAGRAM_SIZE];
    final DatagramPacket datagram = new DatagramPacket(buff, buff.length);

    public ListenThread(DatagramSocket datagram){
        this.server = datagram;
    }

    @Override
    public void run() {
        //waits for message from relay
        try {
            server.receive(datagram);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String message = new String(datagram.getData(), 0, datagram.getLength()).trim();
        System.out.println("I've got: " + message);
        String info[] = message.split(" ");
        String resp = info[0] + " Hello back!";

        //sends response to relay
        byte[] respBuff = resp.getBytes();
        int clientPort = datagram.getPort();
        InetAddress clientAddress = datagram.getAddress();
        DatagramPacket response = new DatagramPacket(respBuff, respBuff.length, clientAddress, clientPort);

        try {
            server.send(response);
            System.out.println("I've sent: " + resp);
        } catch (IOException e) {
            System.out.println("no I/O");
        }
        server.close();
    }
}
