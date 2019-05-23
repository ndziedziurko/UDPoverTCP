package sender;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import static UDP.UDP.MAX_DATAGRAM_SIZE;

public class Sender {

        public static void main(String[] args) throws IOException {
            InetAddress address;
            int port = 1025;
            String mess = "Hello!";

            Scanner scan = new Scanner(System.in);
            System.out.println("enter agent's ip address");
            String adr = scan.next();
            address = InetAddress.getByName(adr);

            //sends message to agent
            byte[] queryBuff = mess.getBytes();
            DatagramPacket query = new DatagramPacket(queryBuff, queryBuff.length, address, port);
            DatagramSocket socket = new DatagramSocket();
            socket.send(query);

            System.out.println("I've sent: "  + mess);

            //waits for response from the agent
            byte[] buff = new byte[MAX_DATAGRAM_SIZE];
            DatagramPacket packet = new DatagramPacket(buff, buff.length);
            socket.receive(packet);

            String resp = new String(packet.getData(), 0, packet.getLength()).trim();
            System.out.println("I've got:  "  + resp);

            socket.close();
            scan.close();
        }
}
