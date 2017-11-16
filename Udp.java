import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Udp {
	public static void main(String[] args) {
		int packetSize = Integer.parseInt(args[2]);
		int delay_ns = 600000;
    int delay_ms = Integer.parseInt(args[3]);
    InetAddress host = null;
    try{
      host = InetAddress.getByName(args[0]);
    } catch(UnknownHostException ue){
      ue.printStackTrace();
    }
    
    int userPort = Integer.parseInt(args[1]);
		// int bufferSize = 130;
		int port = 43455;
		// byte[] buf = new byte[256];
		DatagramSocket socket;
		File f = new File("quartet.mp3");
    // File f = new File("quartet.au");
		// File f = new File("quartet.wav");
    // File f = new File("harp.mp3");
    // File f = new File("harp.au");
    // File f = new File("harp.wav");
    
		try {
			socket = new DatagramSocket(port);
			try {
				System.out.println("Server started at port: " + socket.getLocalPort());
				InputStream fis = new FileInputStream(f);
				int length = fis.available();
				byte[] file = new byte[packetSize];
				int packets = (int) Math.ceil(length / packetSize);
				for (int i = 0; i < packets; i++) {
					fis.read(file, 0, packetSize);
					DatagramPacket pktFile = new DatagramPacket(file, file.length, host, userPort);
					socket.send(pktFile);
					Thread.sleep(delay_ms, delay_ns);
				}
				fis.close();
			} catch(IOException ie){
				ie.printStackTrace();
			} catch (InterruptedException ie){
				ie.printStackTrace();
			}
			finally {
				socket.close();
			}
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}