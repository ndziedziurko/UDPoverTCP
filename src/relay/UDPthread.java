package relay;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import static UDP.UDP.MAX_DATAGRAM_SIZE;

public class UDPthread extends Thread {
    private PrintWriter out;

    @Override
    public void run(){
        try {
            //receives response from receiver
            byte[] buff = new byte[MAX_DATAGRAM_SIZE];
            DatagramPacket packet = new DatagramPacket(buff, buff.length);
            MainRelay.socketUDP.receive(packet);

            String message = new String(packet.getData(), 0, packet.getLength()).trim();
            System.out.println("relay got response: " + message);

            //sends response to agent
            out = new PrintWriter(MainRelay.socketTCP.getOutputStream(), true);
            out.println(message);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

