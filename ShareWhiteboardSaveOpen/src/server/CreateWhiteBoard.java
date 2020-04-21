package server;

import java.awt.Color;
import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

import remote.RMIMediator;

public class CreateWhiteBoard {
	  public static boolean exitWhiteBoard = false;
	  public static void main(String argv[]) {    
	    try {      
	    	String serverIP = "";
	    	System.setProperty("java.rmi.server.hostname", argv[0]);
	    	//LocateRegistry.createRegistry(1099);
	    	
	    	String name = "TheMediator";      
	    	System.out.println("Registering RMIMediatorImpl as \""+ name + "\"");      
	    	RMIMediator mediator = new RMIMediatorImpl();      
	    	System.out.println("Created mediator, binding...");      
	    	Registry registry = LocateRegistry.createRegistry(Integer.parseInt(argv[1]));
	    	registry.rebind("mediator", mediator);      
	    	System.out.println("Remote mediator ready...");
	    	
	    	Properties props = new Properties();      
			Color col = Color.black; 
			String username = argv[2];
		    ThreadedWhiteboardUser tobj = new ThreadedWhiteboardUser(username, col, "host","TheMediator"); 
		    if(exitWhiteBoard == true) {
		    	System.exit(0);
		    }
	    	}
	    catch (Exception e) {      
	    	System.out.println("Caught exception while registering: " + e);
	    	}  
	    }
}