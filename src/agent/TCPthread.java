package agent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class TCPthread extends Thread{
    private Socket socketTCP;
    private BufferedReader in;
    private int clientPort;
    private InetAddress clientIP;

    public TCPthread(Socket socket){
        this.socketTCP = socket;
    }


    @Override
    public void run(){

        //starts listening on socketTCP
        while(true){
            try {
                in = new BufferedReader(new InputStreamReader(socketTCP.getInputStream()));
                String line;
                
                //waits for response from relay
                while((line = in.readLine()) != null){
                    System.out.println("agent got response: " + line);

                    String mess[] = line.split(" ");
                    System.out.println("agent sends response to: " + MainAgent.clients.get(Integer.parseInt(mess[0])));
                    clientPort = MainAgent.clients.get(Integer.parseInt(mess[0])).getPort();
                    clientIP = MainAgent.clients.get(Integer.parseInt(mess[0])).getIp();

                    //sends message to client
                    byte[] queryBuff = line.getBytes();
                    DatagramPacket query = new DatagramPacket(queryBuff, queryBuff.length, clientIP, clientPort);
                    DatagramSocket socket = new DatagramSocket();
                    socket.send(query);

                    //"frees" the port
                    MainAgent.ports.get(Integer.parseInt(mess[0])).setUsed(false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                socketTCP.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
