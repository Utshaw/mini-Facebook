/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientFileOperation;

import Util.NetworkConnection;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class SendFileClientpart {

    
    public SendFileClientpart(NetworkConnection nc, File toSend) {
        System.out.println("I am in SendFileClientpart constructor");
        try {
            DataInputStream din = new DataInputStream(nc.socket.getInputStream());
            DataOutputStream dout = new DataOutputStream(nc.socket.getOutputStream());
            System.out.println("I am after din,dout in SendFileClientpart");
            
            dout.write(CreateDataPacket("124".getBytes("UTF8"), toSend.getName().getBytes("UTF8")));
                dout.flush();
                RandomAccessFile rw = new RandomAccessFile(toSend, "r");
                long current_file_pointer = 0;
                boolean loopbreak = false;
                while (true) {
                    if (din.read() == 2) {
                        byte[] cmd_buf = new byte[3];
                        din.read(cmd_buf, 0, cmd_buf.length);
                        byte[] rec_buf = ReadStream(din);
                        switch (Integer.parseInt(new String(cmd_buf))) {
                            case 125:
                                current_file_pointer = Long.valueOf(new String(rec_buf));
                                // Don't understand
                                int buff_len = (int) (rw.length() - current_file_pointer < 20000 ? rw.length() - current_file_pointer : 20000);
                              //  int buff_len = (int)(rw.length()-current_file_pointer);
                                byte[] temp_buff = new byte[buff_len];
                                if (current_file_pointer != rw.length()) {
                                    rw.seek(current_file_pointer);
                                    rw.read(temp_buff, 0, temp_buff.length);
                                    dout.write(CreateDataPacket("126".getBytes("UTF8"), temp_buff));
                                    dout.flush();
                                    System.out.println("Upload percentage :" + ((float) current_file_pointer / rw.length()) * 100 + "%");
                                } else {
                                    loopbreak = true;
                                }
                                break;

                        }
                    }
                    if (loopbreak == true) {
                        System.out.println("Stop Server informed");
                        dout.write(CreateDataPacket("127".getBytes("UTF8"), "Close".getBytes("UTF8")));
                        dout.flush();
//                        sock.close();
                        System.out.println("client socket closed");
                        break;
                    }
                }
            
//            byte[] buffer = obj.CreateDataPacket("Codevlog".getBytes("UTF8"));
//            dout.write(buffer);
        
        
        
        
        } catch (IOException ex) {
            Logger.getLogger(SendFileClientpart.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }
    private byte[] CreateDataPacket(byte[] cmd, byte[] data) {
        byte[] packet = null;
        try {
            byte[] initialize = new byte[1];
            initialize[0] = 2;
            byte[] separetor = new byte[1];
            separetor[0] = 4;
            byte[] data_length = String.valueOf(data.length).getBytes("UTF8");
            packet = new byte[initialize.length + cmd.length + separetor.length + data_length.length + data.length];

            System.arraycopy(initialize, 0, packet, 0, initialize.length);
            System.arraycopy(cmd, 0, packet, initialize.length, cmd.length);
            System.arraycopy(data_length, 0, packet, initialize.length + cmd.length, data_length.length);
            System.arraycopy(separetor, 0, packet, initialize.length + cmd.length + data_length.length, separetor.length);
            System.arraycopy(data, 0, packet, initialize.length + cmd.length + data_length.length + separetor.length, data.length);

        } catch (UnsupportedEncodingException ex) {
            System.out.println(ex);
        }

        return packet;
    }
    private byte[] ReadStream(DataInputStream din) {
        byte[] data_buf = null;
        int b = 0;
        String buf_length = "";
        try {
            while ((b = din.read()) != 4) {
                buf_length += (char) b;     //DIDN't UNDERSTAND
            }
            int data_length = Integer.parseInt(buf_length);
            data_buf = new byte[Integer.parseInt(buf_length)];
            int byte_read = 0;
            int byte_offset = 0;
            while (byte_offset < data_length) {
                byte_read = din.read(data_buf, byte_offset, data_length - byte_offset);
                byte_offset += byte_read;
            }

        } catch (IOException ex) {
            System.out.println(ex);
        }
        return data_buf;
    }
}
