import java.io.File;

public class Client {
    private File messeng;

    public Client(String messeng){
        this.messeng = new File(messeng);
    }

    public Client(File messeng){
        this.messeng = messeng;
    }

    public File getMesseng() {
        return messeng;
    }
}
