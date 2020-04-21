package remote;

import java.rmi.RemoteException;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException; 
import java.rmi.Remote; 
import java.util.Vector;
public interface RMIMediator extends Remote {
	public boolean register(Identity i, RMICollaborator c)throws RemoteException;  
	public Identity newMember(String userRequest, RMICollaborator c) throws RemoteException;  
	public boolean  remove(Identity i) throws RemoteException;  
	public Vector   getMembers() throws RemoteException;
	public boolean send(Identity to, Identity from, String mtag, String msg)throws IOException, RemoteException;  
	public boolean broadcast(Identity from, String mtag, String msg)throws IOException, RemoteException;  
	public boolean send(Identity to, Identity from, String mtag, Object data)throws IOException, RemoteException;  
	public boolean broadcast(Identity from, String mtag, Object data)throws IOException, RemoteException; 
	public boolean broadcastPaint(Identity from, String shape, Color col, MouseEvent e, int X, int Y, int brushSize)throws IOException, RemoteException;
	public boolean broadcastBI(BufferedImage image, Identity from) throws RemoteException, IOException;
	public boolean exitMediator() throws RemoteException, IOException;
	public byte[] presentImage() throws RemoteException;
	public boolean broadcastUsers()throws RemoteException, IOException;
	public boolean kickCommand(String kicked) throws RemoteException, IOException;
	public boolean notifyOpen(Identity from) throws RemoteException, IOException;
	}