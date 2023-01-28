package fi.utu.tech.telephonegame.network;

import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;

/** 
 * Tämä luokka yrittää yhdistää kyseistä solmua toiseen solmuun 
 */

public class TelephoneClient extends Thread {

	private Socket socket;
	private final ObjectInputStream oIn;
	private final ObjectOutputStream oOut;
	private TransferQueue<Object> outQueue;
	
	public TelephoneClient(Socket socket, TransferQueue<Object> outQueue) throws IOException {
		this.socket = socket;
		this.outQueue = outQueue;
		InputStream iS = socket.getInputStream();
		OutputStream oS = socket.getOutputStream();
		
		this.oOut = new ObjectOutputStream(oS);
		this.oIn = new ObjectInputStream(iS);
		
		
	}
	
	public void run() {
		try {
			try {
				while (true) {
					// Luetaan sisään tuleva Envelope ja lisäätään 
    				// sen payload outQueue:en, josta Messagebroker
    				// luokka voi sen napata käsiteltääksi
					Envelope env = (Envelope)oIn.readObject();
					Object o = env.getPayload();
					try {
						outQueue.offer(o, 1, TimeUnit.SECONDS);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				
				}	
			} catch (IOException e) { socket.close(); }
		
		}
		catch (IOException e) {
            e.printStackTrace();	
		}
		catch (Exception e) {
            e.printStackTrace();	
		}
		
	}
	
	/**
	 *  Lähetetään viesti eteenpäin yhdistetylle solmulle
	 */
	public void send(Envelope env) {
		try {
			oOut.writeObject(env);
			oOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
