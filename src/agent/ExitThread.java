package agent;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class ExitThread extends Thread{

    @Override
    public void run(){
        while(true){
            Scanner scan = new Scanner(System.in);
            String str = scan.next();
            if(str.equals("exit")){
                System.out.println("ive got exit");
                PrintWriter out = null;
                for(UDPthread thread : MainAgent.udpThreads) thread.exit();
                try {
                    out = new PrintWriter(MainAgent.socketTCP.getOutputStream(), true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                out.println("disconnect");
                System.exit(0);
            }
        }
    }
}
