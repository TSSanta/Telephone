package fi.utu.tech.telephonegame.network;
import java.net.UnknownHostException;
import java.util.ArrayList;


/**
 * DO NOT EDIT THIS FILE. - ÄLÄ MUOKKAA TÄTÄ TIEDOSTOA.
 */

import java.util.concurrent.TransferQueue;

public interface Network {

	public ArrayList<String> getIPs();

	public void initialize(int serverport);

	public void startResolver(boolean serverMode, int netType, int rootServerPort, String rootIPAddress);

	public void connect(String clientIP, int clientPort);

	public String[] resolve() throws UnknownHostException;

	public void postMessage(Object out);

	public TransferQueue<Object> getOutputQueue();
}
