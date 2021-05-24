package tr.edu.sehir.oop;

import ball.BallDTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class client1 {

    ObjectOutputStream os = null;
    ObjectInputStream is = null;
    Socket socket = null;

    public void start() {

        try {
            socket = new Socket("localhost", 4444);
            os = new ObjectOutputStream(socket.getOutputStream());
            is = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.exit(-1);
        }
    }

    public void sendObject(BallDTO ball) {
        try {
            os.writeObject(ball);
        } catch (IOException e) {
            System.exit(-1);
        }
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            System.exit(-1);
        }
    }

}
