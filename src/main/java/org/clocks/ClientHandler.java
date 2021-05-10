package org.clocks;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {

    public ClientHandler(Socket client) {
        ClientHandler.clientDialog = client;
        int index = 0;

        try {
            out = new DataOutputStream(clientDialog.getOutputStream());
            in  = new DataInputStream(clientDialog.getInputStream());

            index = in.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }

        clock = new Clock(new DefaultTime(0, 0, 0), index);
    }

    @Override
    public void run() {
        try {

            while (!clientDialog.isClosed()) {
                char operation = in.readChar();

                switch (operation) {
                    case Clock.set_time: {
                        long h = in.readLong();
                        long m = in.readLong();
                        long s = in.readLong();

                        clock.set_time(new DefaultTime(h, m, s));
                        break;
                    }
                    case Clock.set_alarm: {
                        long h = in.readLong();
                        long m = in.readLong();
                        long s = in.readLong();
                        String message = in.readUTF();

                        clock.set_alarm(new DefaultTime(h, m, s), message);
                        break;
                    }
                    case Clock.pause: {
                        clock.pause();
                        break;
                    }
                    case Clock.get_time: {
                        out.writeUTF(clock.get_time());
                        break;
                    }
                    case Clock.get_message: {
                        out.writeUTF(clock.get_alarm_messages("\n"));
                        break;
                    }
                }

                out.flush();
            }

            in.close();
            out.close();

            clientDialog.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DataOutputStream out;
    private DataInputStream in;
    private static Socket clientDialog;
    private Clock clock;
}