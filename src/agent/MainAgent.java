package agent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class MainAgent {
    static protected Map<Integer, Client> clients = new HashMap<Integer, Client>();
    static Map<Integer, Port> ports = new HashMap<>();
    static List<UDPthread> udpThreads = new ArrayList<>();
    static String relayIP;
    static String destIP;
    static int portTCP = 55555;
    static Socket socketTCP;


    //creates tcp socket for listening to relay and starts UDP and TCP threads
    public MainAgent() throws IOException{

        try {
            socketTCP = new Socket(relayIP, portTCP);
        } catch (IOException e) {
            System.out.println("didnt open socketTCP");
        }

        for(Port p : ports.values()){
            UDPthread thread = new UDPthread(p);
            udpThreads.add(thread);
            (thread).start();
        }
        (new TCPthread(socketTCP)).start();
    }

    //send destIP to relayIP on portTCP
    public static void configure() throws IOException {
        PrintWriter out = new PrintWriter(socketTCP.getOutputStream(), true);
        out.println("destination " + destIP);
        System.out.println("configuration sent, destination: " + destIP);
    }

    public static void main(String[] args) throws IOException {

        Scanner scan = new Scanner(System.in);
        System.out.println("enter relay's ip address");
        relayIP = scan.next();

        System.out.println("enter destination ip address");
        destIP = scan.next();

        for(int i = 0; i < 6; i++){;
            int port = 1024+i;
            ports.put(port, new Port(port));
        }

        MainAgent start = new MainAgent();

        configure();

        new ExitThread().start();
    }
}
