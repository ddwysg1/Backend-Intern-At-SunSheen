package chatApp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class receiveFile {
	public static void main(String[] args) {
        try {
            ServerSocket s = new ServerSocket(8896);
            Socket sc = s.accept();
            InputStream in = sc.getInputStream();
            int t = 1;
            while(true){
                byte[] lData = new byte[4];
                in.read(lData);
                int length = byteArrayToInt(lData);
                byte[] dt2 = new byte[length];

                readData(in,dt2);

                byte[] type = new byte[5];

                in.read(type);

                String typeStr = new String(type);
                File of = new File("D:\\SunSheen\\Backend\\chatApp\\" + t + "." + typeStr.trim());
                of.createNewFile();
                OutputStream o = new FileOutputStream(of);

                o.write(dt2);
                o.flush();
                o.close();
                t++;
            }
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

    /**
     * 
     * @param in
     * @param bData
     * @throws IOException
     */
    private static void readData(InputStream in,byte[] bData) throws IOException{
        int off = 0;
        int length = bData.length;
        int readLength = 0;
        do{
            off = readLength + off;
            length = length - readLength;
            readLength = in.read(bData, off, length);
        }while(readLength != length);
    }
}
