package server;

import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import remote.Identity;
import remote.RMICollaborator;
import remote.RMIMediator;

import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException; 
import java.rmi.Remote; 
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Naming;

public class RMIMediatorImpl extends UnicastRemoteObject implements RMIMediator {  
	private static Enumeration idlistnow;
	Hashtable clients = new Hashtable();  
	Vector idList = new Vector();
	ArrayList<String> usersArrayList = new ArrayList<String>();
	byte[] currentImage;
	public boolean exitWhiteBoard = false;
	Identity admin;
	int userOne = 0;
	public RMIMediatorImpl() throws RemoteException {    
		super();  }
	
  public boolean register(Identity i, RMICollaborator c) throws RemoteException {    
	  System.out.println("Registering member " + i.getId()+ " as " + c.getIdentity().getName());
		  userOne++;
		  clients.put(i, c);
		  usersArrayList.add(c.getIdentity().getName());
		  if (userOne == 1) {
			  admin = i;
		  }
		  return true;  
  }
	  
  private boolean userNameDenied(RMICollaborator c) {
	boolean success = false;  
	try {
		success = c.invalidUsername();
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return success;
  }

public Identity newMember(String userRequest, RMICollaborator c)  {    
	  int max = -1;    
	  boolean found = true;    
	  Enumeration x;
	  if (idList.size() > 0) {
		  if(usersArrayList.contains(userRequest)) {
			  userNameDenied(c);
			  return null;
		  }
		  Object[] options = {"Yes", "No"};
		  int n = JOptionPane.showOptionDialog(null,
		      "Would you like to grant whiteboad access to "+userRequest +" ?",
		      "Save Canvas",
		      JOptionPane.YES_NO_OPTION,
		      JOptionPane.QUESTION_MESSAGE,
		      null,
		      options,
		      options[1]);				  
		  if (n == 1) {
			  return null;
		  }
		  else {
			System.out.println("Hello");
		   
		  }
	  }
	  
	  synchronized (idList) {       
		  x = idList.elements();    
		  }    
	  while (x.hasMoreElements()) {   
		  Identity i = (Identity) x.nextElement();
		        
		  if (i.getId() > max) { 
			  max = i.getId();  
			  }    
		  }
  	
	  Identity newId = new Identity(max + 1);    
	  synchronized (idList) {      
		  idList.addElement(newId);    
    		}   
	  
	  System.out.println("New Member registered" + newId.getId());
	  return newId;
	  
    }
  
  
  public boolean remove(Identity i) throws RemoteException {    
	  boolean success = true;    
	  usersArrayList.remove(i.getName());
	  System.out.println(clients.get(i));
	  return success;  
	  }
  
  
  public Vector getMembers() throws RemoteException {    
	  synchronized (idList) {      
		  return (Vector)idList.clone();    
		  }  
	  }
  
  public boolean send(Identity to, Identity from, String mtag, String msg)throws IOException, RemoteException {    
	  boolean success = false;    
	  RMICollaborator c = getMember(to);    
	  synchronized (c) {      
		  if (c != null) {        
			  success = c.notify(mtag, msg, from);      
			  }    
		  }
  return success;  
  }
  
  public boolean send(Identity to, Identity from, String mtag, Object data) throws IOException, RemoteException {    
	  boolean success = false;    
	  RMICollaborator c = getMember(to);    
	  synchronized (c) {      
		  if (c != null) {        
			  success = c.notify(mtag, data, from);      
			  }    
		  }    
	  return success;  
	  }
  
  public boolean broadcast(Identity from, String mtag, String msg)throws IOException, RemoteException {    
	  System.out.println("Broadcasting...");    
	  boolean success = true;    
	  Enumeration ids;    
	  synchronized (clients) {      
		  ids = clients.keys();    
		  }    
	  RMICollaborator target = null;    
	  while (ids.hasMoreElements()) {      
		  Identity i = (Identity)ids.nextElement();      
		  synchronized (clients) {        
			  target = (RMICollaborator)clients.get(i);      
			  }      
		  synchronized (target) {        
			  if (target == null ||!target.notify(mtag, msg, from)) 
			  {          
				  success = false;        
				  }      
			  }
		  
		  }
	  return success;
	  }
  
  public boolean broadcast(Identity from, String mtag, Object data)throws IOException, RemoteException {    
	  boolean success = true;    
	  Enumeration ids;    
	  synchronized (clients) {      
		  ids = clients.keys();    
		  }    
	  RMICollaborator target = null;    
	  while (ids.hasMoreElements()) {      
		  Identity i = (Identity)ids.nextElement();      
		  synchronized (clients) {        
			  target = (RMICollaborator)clients.get(i);      
			  }      
		  synchronized (target) {        
			  if (target == null ||!target.notify(mtag, data, from)) {
				  success = false;        
				  System.out.print(success);
				  }      
			  }    
		  }    
	  return success;  
	  }
  public boolean broadcastUsers() throws RemoteException, IOException {
	  boolean success = true;    
	  Enumeration ids;
//	  ArrayList<String> usersArrayList = new ArrayList<String>();
	  synchronized (clients) {      
		  ids = clients.keys();    
		  }    
	  RMICollaborator target = null;
//	  while (ids.hasMoreElements()) {
//		  Identity i = (Identity)ids.nextElement();
//		  target = (RMICollaborator)clients.get(i); 
//		  usersArrayList.add(target.getIdentity().getName());
////	  }
//	  while (ids.hasMoreElements()) {      
//		  Identity i = (Identity)ids.nextElement();
//		  synchronized (clients) {        
//			  target = (RMICollaborator)clients.get(i);
//			  usersArrayList.add(target.getIdentity().getName());
//			  }
//	  }
	  
	  synchronized (clients) {      
		  ids = clients.keys();    
		  }    
	  while (ids.hasMoreElements()) {      
		  Identity i = (Identity)ids.nextElement();
		  synchronized (clients) {        
			  target = (RMICollaborator)clients.get(i);
			  synchronized (target) {        
				  if (target == null ||!target.notifyUsers(usersArrayList)) {
					  success = false;        
					  System.out.print(success);
					  }      
				  }    
			  }
	  }
	  return success;  
	}
  
  public boolean broadcastPaint(Identity from, String shape, Color col, MouseEvent e, int X, int Y, int brushSize) throws RemoteException, IOException {
	  boolean success = true;    
	  Enumeration ids;    
	  synchronized (clients) {      
		  ids = clients.keys();    
		  }    
	  RMICollaborator target = null;    
	  while (ids.hasMoreElements()) {      
		  Identity i = (Identity)ids.nextElement();      
		  synchronized (clients) {        
			  target = (RMICollaborator)clients.get(i);      
			  }      
		  synchronized (target) {
			  if (!(target.getIdentity().equals(from))) {
				  if (target == null ||!target.notifyPaint(shape, col, e, X, Y, brushSize)) {
					  success = false;        
					  System.out.print(success);
					  }      
			  }    
		  }
	  }
	  System.out.println("Painting broadcasted successfully");
	  return success;  
	  }
  
  protected RMICollaborator getMember(Identity i) {    
	  Enumeration ids;    
	  synchronized (clients) {      
		  ids = clients.keys();    
		  }    
	  RMICollaborator c = null;    
	  Identity tmp;    
	  while (c == null && ids.hasMoreElements()) {       
		  tmp = (Identity)ids.nextElement();       
		  if (tmp.equals(i)) {         
			  synchronized (clients) {           
				  c = (RMICollaborator)clients.get(tmp);
				  }       
			  }    
		  }    
	  return c;  
	  
  }
  public static void main(String argv[]) {    
	  // Install a security manager    System.setSecurityManager(new RMISecurityManager());
    try {      
    	RMIMediator foo = new RMIMediatorImpl();          	
    	String name = "TheMediator";      
    	System.out.println("Registering RMIMediatorImpl as \""+ name + "\"");      
    	RMIMediator mediator = new RMIMediatorImpl();      
    	System.out.println("Created mediator, binding...");      
    	Registry registry = LocateRegistry.getRegistry();
    	registry.bind("mediator", mediator);      
    	System.out.println("Remote mediator ready...");    
    	}    
    catch (Exception e) {      
    	System.out.println("Caught exception while registering: " + e);
    	}  
    }



public boolean kickCommand(String kicked)throws RemoteException, IOException {
	System.out.println("Kicking a collaborator");
	Identity toKick = null;
	boolean success = true;    
	Enumeration ids;       
	ids = clients.keys();    
	RMICollaborator target = null;    
	while (ids.hasMoreElements()) {
		Identity i = (Identity)ids.nextElement();
		target = (RMICollaborator)clients.get(i);
		String targetCandidate = target.getIdentity().getName();
		if(targetCandidate.equals(kicked)) {
			usersArrayList.remove(kicked);
			i.removed = true;
			try {
				if(!target.kickCollaborator()) {
					success = false;        
					System.out.print(success);
						  }
				} 
			catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
			catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			break;
		}
	}
	while (ids.hasMoreElements()) {      
		  Identity id2 = (Identity)ids.nextElement();
		  synchronized (clients) {        
			  target = (RMICollaborator)clients.get(id2);
			  synchronized (target) {
				  try {
				  if (target == null ||!target.notifyUsers(usersArrayList)) {
					  success = false;        
					  System.out.print(success);
					  }
				  }
				  catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				  catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				  }    
			  }
	  }
	return success;
}

public void close() {
	System.exit(0);
}
		  
	  

public boolean exitMediator() throws RemoteException, IOException {
	System.out.println("Shutting down each collaborator");
	boolean success = true;    
	  Enumeration ids;    
	  synchronized (clients) {      
		  ids = clients.keys();    
		  }    
	  RMICollaborator target = null;    
	  while (ids.hasMoreElements()) {      
		  Identity i = (Identity)ids.nextElement();      
		  synchronized (clients) {        
			  target = (RMICollaborator)clients.get(i);      
			  }      
		  synchronized (target) {
			  if ((!i.equals(admin)) && (!i.removed)) {
					  if (target == null ||!target.exitCollaborator()) {
						  success = false;        
						  System.out.print(success);
						  }         
			  }
		  }
	  }
	  System.out.println("Shutting down each collaborator");
	  //exitWhiteBoard = true;
	  //System.exit(0);
	  return success;  
}

@Override
public boolean broadcastBI(BufferedImage image, Identity from) throws RemoteException, IOException {
	// TODO Auto-generated method stub
	return false;
}

@Override
public byte[] presentImage() throws RemoteException {
	// TODO Auto-generated method stub
	return null;
}

public boolean notifyOpen(Identity from) throws RemoteException, IOException {
	  boolean success = true;    
	  Enumeration ids;    
	  synchronized (clients) {      
		  ids = clients.keys();    
		  }    
	  RMICollaborator target = null;    
	  while (ids.hasMoreElements()) {      
		  Identity i = (Identity)ids.nextElement();      
		  synchronized (clients) {        
			  target = (RMICollaborator)clients.get(i);      
			  }      
		  synchronized (target) {
			  if (!(target.getIdentity().equals(from))) {
				  if (target == null ||!target.notifyOpen()) {
					  success = false;        
					  System.out.print(success);
					  }      
			  }    
		  }
	  }
	  System.out.println("Painting broadcasted successfully");
	  return success;  
	  }
	
}


 