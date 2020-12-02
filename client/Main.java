package client;


import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {

    @Parameter(names = "-t", description = "type of request")
    String commandType = "";

    @Parameter(names = "-k", description = "key of data")
    String key = "";

    @Parameter(names = "-v", description = "value to be stored")
    String data = "";

    @Parameter(names = "-in", description = "read request from file")
    String fileName = "";

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;
    //private static final Scanner scanner = new Scanner(System.in);
    public static void main(String ...args) {
        //while (true) {}
        Main main = new Main();


        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(args);
        main.run();

    }




    public void run() {
        String msg;
        try (Socket socket = new Socket(InetAddress.getByName(ADDRESS), PORT);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ){
            String compositeJson = "";

            switch (commandType) {
                case "get":
                case "exit":
                case "delete":
                    compositeJson = "{" + "\"type\":" + "\"" + commandType + "\","
                            + "\"key\":" + "\"" + key + "\"" + "}";
                    break;
                case "set":
                    compositeJson = "{" + "\"type\":" + "\"" + commandType + "\","
                            + "\"key\":" + "\"" + key + "\"," + "\"value\":"
                            + "\"" + data + "\"" + "}";
                    break;

                default:
            }

            if (!fileName.isEmpty()) {
                File f = new File("");
                String abs = f.getCanonicalPath();
                fileName = abs + "/JSON Database/task/src/client/data/" + fileName;
                ReadWriteLock lock = new ReentrantReadWriteLock();
                Lock readLock = lock.readLock();
                readLock.lock();
                try {
                    compositeJson = FileReader.readFile(fileName);
                    System.out.println(compositeJson);
                } finally {
                    readLock.unlock();
                }

            }
            System.out.println("Client started!");
            output.writeUTF(compositeJson);
            if (commandType.equals("exit")) {
                System.out.println("Sent: " + "{" + "\"type\":" + "\"" + commandType + "\"" + "}");
            } else {
                System.out.println("Sent: " + compositeJson);
            }
            msg = input.readUTF();

            System.out.println("Received: " + msg);

        } catch (Exception c) {
            c.printStackTrace();
        }
    }
}



