package chatApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class sendFile {
	
	static Socket s;
	static OutputStream out;
	static boolean status = false;
	
	public static void main(String[] args) throws Exception {

	}
	
	public static void start() throws Exception {
		if (status == false) {
			s = new Socket("localhost", 8896);
			out = s.getOutputStream();
			status = true;
		}
	}
	
 	public static void sendData(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) {
                System.out.println("No Such File");
            } else {
                InputStream in = new FileInputStream(f);
                int length = (int) f.length();
                if(length > 1024 * 1024 * 5) {
                    System.out.println("File is too big");
                } else {
                    String[] tempFileType = path.split("\\.");
                    String fileType = tempFileType[tempFileType.length - 1];
                    byte[] typeData = fileType.getBytes();
                    byte[] sendTypeData = new byte[5];
                    if(typeData.length < 5){
                        System.arraycopy(typeData, 0, sendTypeData, 0, typeData.length);
                    }

                    byte[] tempData = new byte[length];
                    in.read(tempData);
                    in.close();

                    byte[] lengthData = intToByteArray(length);
                    byte[] sendData = new byte[tempData.length + lengthData.length + sendTypeData.length];

                    System.arraycopy(lengthData, 0, sendData, 0, lengthData.length);
                    System.arraycopy(tempData, 0, sendData, lengthData.length, tempData.length);
                    System.arraycopy(sendTypeData, 0, sendData, lengthData.length+tempData.length, sendTypeData.length);

                    out.write(sendData);
                    out.flush();
                }
            }
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
 	}
 	
    private static int byteArrayToInt(byte[] b) {
        return b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

    private static byte[] intToByteArray(int a) {
        return new byte[]{
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }
}
