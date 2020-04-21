package client;

import java.awt.Color;
import java.util.Properties;

public class JoinWhiteBoard {
	public static void main(String argv[]) {    
		try {      
			Properties props = new Properties();      
			Color col = Color.black; 
		    ThreadedWhiteboardUser tobj = new ThreadedWhiteboardUser(argv[2], col, argv[0],"TheMediator", argv[1]);
			
		}
		catch (Exception e) {      
			System.out.println("Caught exception:");      
			e.printStackTrace();    
		}  
	}
	

}
