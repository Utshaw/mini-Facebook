/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerFileOperation;

import Util.NetworkConnection;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class RecieveFileServerPart {

    private DataInputStream din;
    private DataOutputStream dout;

    public RecieveFileServerPart(NetworkConnection nc, String clientname, String whichfile) {
        System.out.println("I am in RecieveFileServerPart constructor");
        try {
            din = new DataInputStream(nc.socket.getInputStream());
            dout = new DataOutputStream(nc.socket.getOutputStream());
            System.out.println("I am after din,dout in RecieveFileServerPart");
            
            RandomAccessFile rw = null;
            long current_file_pointer = 0;
            boolean loop_break = false;
            while (true) {
                byte[] initialize = new byte[1];
                try {
                    din.read(initialize, 0, initialize.length);
                    if (initialize[0] == 2) {
                        byte[] cmd_buf = new byte[3];
                        din.read(cmd_buf, 0, cmd_buf.length);
                        byte[] rec_data = ReadStream();
                        switch (Integer.parseInt(new String(cmd_buf))) {
                            case 124:
                                //     File Myfile = new File("Lecture 1- An Overview of C++.pdf");
                                if(whichfile.equals("profilepicture")) {
                                    rw = new RandomAccessFile(System.getProperty("user.dir") + "\\facebook" + "\\accounts" + "\\" + clientname + "\\" + "info" + "\\" + "propic" + "\\" + clientname + ".jpg", "rw");
                                    System.out.println(new String(rec_data));
                                }
                                else if(whichfile.equals("cover_photo_change")){
                                    rw = new RandomAccessFile(System.getProperty("user.dir") + "\\facebook" + "\\accounts" + "\\" + clientname + "\\" + "info" + "\\" + "coverphoto" + "\\" + clientname + ".jpg", "rw");
                                    System.out.println(new String(rec_data));
                                }
                                dout.write(CreateDataPacket("125".getBytes("UTF8"), String.valueOf(current_file_pointer).getBytes("UTF8")));
                                dout.flush();
                                break;
                            case 126:
                                rw.seek(current_file_pointer);
                                rw.write(rec_data);
                                System.out.println("download percentage :" + (float) current_file_pointer / rw.length() * 100 + "%");
                                current_file_pointer = rw.getFilePointer();
                                dout.write(CreateDataPacket("125".getBytes("UTF8"), String.valueOf(current_file_pointer).getBytes("UTF8")));
                                dout.flush();
                                break;
                            case 127:
                                if (new String(rec_data).equals("Close")) {
                                    loop_break = true;
                                }
                                break;
                        }
                    }
                    if (loop_break == true) {
                    //  iDONT KNOW THIS WILL WORK OR NOT
                      //  dout.close();
                      rw.close();
                        break;
                    }
                } catch (IOException ex) {
                    System.out.println(ex);
                }

            }

        } catch (IOException ex) {
            Logger.getLogger(RecieveFileServerPart.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private byte[] ReadStream() {
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

}
