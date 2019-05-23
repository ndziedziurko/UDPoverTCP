package agent;

public class Port {
    private int port;
    private Boolean isUsed;

    public Port(int p){
        this.port = p;
        this.isUsed = false;
    }

    public int getPort() {
        return port;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public synchronized void setUsed(boolean used) {
        isUsed = used;
    }
}
