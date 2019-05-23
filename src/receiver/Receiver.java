package receiver;

import java.io.IOException;
import java.net.*;

public class Receiver {
        private DatagramSocket server;

    //creates tcp socket for listening to relay and starts
    public Receiver() throws IOException{
        for(int i = 0; i < 6; i++) {
            InetAddress adr = InetAddress.getByName("127.0.0.1");
            server = new DatagramSocket(1024+i, adr);
            System.out.println("Server listens on: " + server.getLocalPort() + ", server: " + server.getInetAddress().getLocalHost());
            new ListenThread(server).start();
        }
    }

        public static void main(String[] args) throws IOException {
        Receiver start = new Receiver();
        }
}
