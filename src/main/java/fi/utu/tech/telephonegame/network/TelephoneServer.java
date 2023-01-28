package fi.utu.tech.telephonegame.network;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;

/** 
 * Tämä luokka luo palvelin instannin solmulle, 
 * antaen muiden solmujen yhdistää kyseiseen solmuun
 */

public class TelephoneServer extends Thread {

	private ServerSocket ss;
	private ArrayList<ConnectionToClient> clientList;
	private TransferQueue<Object> outQueue;
	
	public TelephoneServer(ServerSocket ss,  TransferQueue<Object> outQueue) {
		this.ss = ss;
		this.outQueue = outQueue;
		}
    
	public void run() {
		clientList = new ArrayList<ConnectionToClient>();
		
        try {
            while (true) {
  
                Socket client = ss.accept();
                ConnectionToClient clientCon = new ConnectionToClient(client, outQueue);
                
                // Listaan clientList kirjataan yhdistetyt asiakkaat
                clientList.add(clientCon);
                
                clientCon.start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        finally {
            if (ss != null) {
                try {
                    ss.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
	/**
	 *  Annetaan viesti kaikkille asiakasyhteyksille lähetettäväksi
	 */
	public void send(Envelope env) {
		for(ConnectionToClient client : clientList) {
			client.send(env);
		}
	}
	
	/**
	 *  Tämä luokka hoitaa yksittäisen asiakkaan yhteyden
	 */
	private static class ConnectionToClient extends Thread{
		private final ObjectInputStream oIn;
		private final ObjectOutputStream oOut;
		private final Socket socket;
		private TransferQueue<Object> outQueue;

        ConnectionToClient(Socket socket,  TransferQueue<Object> outQueue) throws IOException {
            this.socket = socket;
            this.outQueue = outQueue;
            this.oIn = new ObjectInputStream(socket.getInputStream());
            this.oOut = new ObjectOutputStream(socket.getOutputStream());
        }
        
        /**
         *  Lähettää viestin yhdistetylle asiakkaalle
         */
        public void send(Envelope env) {
			try {
				oOut.writeObject(env);
				oOut.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        
        public void run() {
        	try {
        		try {
        			while (true) {
        				// Luetaan sisään tuleva Envelope ja lisäätään 
        				// sen payload outQueue:en, josta Messagebroker  
        				// luokka voi sen napata käsiteltäväksi
        				Envelope env = (Envelope)oIn.readObject();
        				Object o = env.getPayload();
        				try {
        					outQueue.offer(o, 1, TimeUnit.SECONDS);
        				} catch (InterruptedException e) {
        					e.printStackTrace();
        				}
        			}
        		} catch (IOException e) { socket.close();}
        		} catch (Exception e)
        		{ throw new Error(e.toString()); }
        }
    }
}
	

