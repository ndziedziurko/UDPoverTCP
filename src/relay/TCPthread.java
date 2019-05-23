package relay;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class TCPthread extends Thread {
    private BufferedReader in;
    private int destPort;
    private InetAddress address = InetAddress.getByName(MainRelay.destIP);

    public TCPthread() throws UnknownHostException {
    }


    @Override
    public void run(){
       while(true){
           try {
               //waits for message
               in = new BufferedReader(new InputStreamReader(MainRelay.socketTCP.getInputStream()));
               String message;

               while ((message = in.readLine()) != null) {
                   //disconnects
                   if (message.equals("disconnect")) {
                       MainRelay.destIP = null;
                       System.out.println("agent was disconnected");
                   } else {
                       System.out.println("relay got: " + message);


                       //sends message to received port number
                       destPort = Integer.parseInt(message.split(" ")[0]);
                       byte[] queryBuff = message.getBytes();
                       DatagramPacket query = new DatagramPacket(queryBuff, queryBuff.length, address, destPort);
                       MainRelay.socketUDP.send(query);
                   }
               }
           } catch(IOException e){
                   e.printStackTrace();
           }
       }
    }
}
