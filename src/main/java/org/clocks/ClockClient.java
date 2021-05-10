package org.clocks;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClockClient {

    static Socket socket;

    public ClockClient(int index) {
        try {
            socket = new Socket("localhost", 3345);

            os = new DataOutputStream(socket.getOutputStream());
            is = new DataInputStream(socket.getInputStream());

            os.writeInt(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void set_time(IClockTime time) {
        try {
            os.writeChar(Clock.set_time);

            os.writeLong(time.get_hours());
            os.writeLong(time.get_minutes());
            os.writeLong(time.get_seconds());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void set_alarm(long hour, long minute, long second, String message) {
        try {
            os.writeChar(Clock.set_alarm);

            os.writeLong(hour);
            os.writeLong(minute);
            os.writeLong(second);

            os.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        try {
            os.writeChar(Clock.pause);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get_time() {
        String time = "";
        try {
            os.writeChar(Clock.get_time);
            time = is.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return time;
    }

    public String get_message() {
        String message = "";
        try {
            os.writeChar(Clock.get_message);
            message = is.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    private DataOutputStream os;
    private DataInputStream is;
}
