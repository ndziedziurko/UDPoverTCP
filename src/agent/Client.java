package agent;
import java.net.InetAddress;

public class Client {
    private int port;
    private InetAddress ip;

    public Client(int p, InetAddress i){
        this.port = p;
        this.ip = i;
    }

    public int getPort() {
        return port;
    }

    public InetAddress getIp() {
        return ip;
    }

    @Override
    public String toString(){
        return "port: " + port + ", ip: " + ip;
    }
}
