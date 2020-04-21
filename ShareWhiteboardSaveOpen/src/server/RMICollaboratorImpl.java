package server;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import remote.Identity;
import remote.RMICollaborator;
import remote.RMIMediator;

import java.rmi.Naming; 
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject; 
import java.rmi.RMISecurityManager;

public class RMICollaboratorImpl extends UnicastRemoteObject implements RMICollaborator
{  
	protected Identity id = null;  
	protected RMIMediator mediator = null;
	public RMICollaboratorImpl(String name, String host, String mname)throws RemoteException {    
			id = new Identity(0);    
			id.setName(name);    
			Properties p = new Properties();    
			p.put("host", host);    
			p.put("mediatorName", mname);    
			connect(p);  
	}
	public RMICollaboratorImpl(String name) throws RemoteException {    
		id = new Identity(0);    
		id.setName(name);  
	}
	
	public Identity getIdentity() throws RemoteException { 
		return id; 
	}
	public boolean connect(Properties p) throws RemoteException {    
		boolean success = false;    
		//String host = p.getProperty("host");    
		//String mName = p.getProperty("mediatorName");    
		//if (host != null && mName != null)       
			try {        
				//String url = "rmi://" + host + "/" + mName;        
				//System.out.println("looking up " + url);
								
				//Registry registry = LocateRegistry.getRegistry("192.168.0.119", 1099);
				Registry registry = LocateRegistry.getRegistry();
				mediator = (RMIMediator)registry.lookup("mediator");        
				System.out.println("Got mediator " + mediator);        
				Identity newId = mediator.newMember(id.getName(),this);
				if (newId == null) {
					JFrame errorFrame = new JFrame();
					JOptionPane.showMessageDialog(errorFrame,
							"Access to whitebaord has been denied",
						    "Connection error",
						    JOptionPane.ERROR_MESSAGE);
					System.out.println();
					System.exit(0);
				}
				mediator.register(newId, this);        
				newId.setName(id.getName());        
				id = newId;        
				success = true;      
				}      
			catch (Exception e) {        
				e.printStackTrace();  
				System.out.println("Error connecting with mediatior");
				success = false;      
			}    
		return success;  
		
	}
	
	public boolean send(String tag, String msg, Identity dst)throws IOException, RemoteException 
	{    
		boolean success = false;    
		if (mediator != null) {      
			success = mediator.send(dst, getIdentity(), tag, msg);    
			}    
		return success;  
		}
	
	public boolean send(String tag, Object data, Identity dst)throws IOException, RemoteException {    
		boolean success = false;    
		if (mediator != null) {      
			success = mediator.send(dst, getIdentity(), tag, data);    
			}    
		return success;  
		}
	
	public boolean broadcast(String tag, String msg)throws IOException, RemoteException 
	{    
		boolean success = false;

	if (mediator != null) {
		success = mediator.broadcast(getIdentity(), tag, msg);
		}   
		return success;  
	}
	public boolean broadcastUsers()throws IOException, RemoteException {
		boolean success = false;
		if (mediator != null) {
			success = mediator.broadcastUsers();
			System.out.println("Sent to mediator broadcastUsers");
		}
		return success;
	}
	
	public boolean broadcastExit()throws IOException, RemoteException {
		boolean success = false;
		if (mediator != null) {
			success = mediator.exitMediator();
			System.out.println("Sent to mediator exit command");
		}
		return success;
	}
	
	public boolean kickingCommmand(String name)throws IOException, RemoteException {
		boolean success = false;
		if (mediator != null) {
			success = mediator.kickCommand(name);
			System.out.println("Sent to mediator exit command");
		}
		return success;
	}

	public boolean broadcast(String tag, Object data)throws IOException, RemoteException {    
		boolean success = false;    
		if (mediator != null) {      
			success = mediator.broadcast(getIdentity(), tag, data);    
			}    
		return success;  
	}
	
	public boolean broadcastPaint(String shape, Color col, MouseEvent e, int X, int Y, int brushSize) throws RemoteException, IOException {
		boolean success = false;    
		if (mediator != null) {      
			success = mediator.broadcastPaint(getIdentity(),shape, col, e, X, Y, brushSize);    
			System.out.println("Sent to mediator broadcastPaint");
			}
		
		return success;  
	}
	
	public boolean broadcastBI(BufferedImage image)throws RemoteException, IOException{
		boolean success = false;    
		if (mediator != null) {      
			success = mediator.broadcastBI(image, getIdentity()); 
			System.out.println("Sent to mediator");
			}
		
		return success;  
	}
	
	public boolean notifyPaint(String shape, Color col, MouseEvent e, int X, int Y, int brushSize) {
		System.out.println("Got message to Paint");    
		return true;  
	}

	public boolean notify(String tag, String msg, Identity src) throws IOException, RemoteException {    
		System.out.println("Got message: \"" + tag + " " + msg + "\""  + " from " + src.getName());    
		return true;  
	}
	
	public boolean notify(String tag, Object data, Identity src)throws IOException, RemoteException {    
		System.out.println("Got message: \"" + tag + " " + data + "\"" + " from " + src.getName());    
		return true;  	
	}
	public boolean notifyUsers(ArrayList<String> clients)throws IOException, RemoteException {    
		System.out.println("Got message of clients");    
		return true;  	
	}
	public boolean exitCollaborator() throws RemoteException, IOException {
		System.out.println("Exit the colaborator...");
		//System.exit(0);
		return false;
	}

	public static void main(String argv[]) {    // Install a security manager       
		try {      
			Properties props = new Properties();      
			Color col = Color.black; 
		    ThreadedWhiteboardUser tobj = new ThreadedWhiteboardUser("User 1", col, "host","TheMediator");
			
		}
		catch (Exception e) {      
			System.out.println("Caught exception:");      
			e.printStackTrace();    
		}  
	}
	@Override
	public boolean notifyBI(BufferedImage image) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public byte[] imageLoad() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean kickCollaborator() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean invalidUsername() throws RemoteException, IOException {
		System.out.println("Username already taken, please close the program and try again.");
		JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame,
				"Username already taken, please close the program and try again.",
			    "Invalid username!",
			    JOptionPane.WARNING_MESSAGE);
		return true;
	}
	@Override
	public boolean notifyOpen() throws RemoteException, IOException {
		// TODO Auto-generated method stub
		return false;
	}
}