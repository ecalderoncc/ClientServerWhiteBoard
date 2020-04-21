package client;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.border.EmptyBorder;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.JInternalFrame;

import View.WhiteBoardInterface;
import remote.Identity;
import remote.RMICollaborator;


public class ThreadedWhiteboardUser extends RMICollaboratorImpl implements java.awt.event.MouseListener,java.awt.event.MouseMotionListener{
	protected Hashtable lastPts = new Hashtable();
	protected Component whiteboard;
	protected Image buffer;
	protected BufferedImage buffer1;
	protected CommHelper helper;
	public BufferedImage currentImage;
	
	protected JFrame frmSharedWhitboard;
	protected JMenuBar menuBar;
	protected JMenu mnFile;
	protected JMenuItem mntmNew;
	protected JMenuItem mntmOpen;
	protected JMenuItem mntmSave;
	protected JMenuItem mntmSaveAs;
	protected JMenuItem mntmClose;
	protected JSeparator separator;
	protected JSeparator separator_1;
	protected JSeparator separator_2;
	protected JSeparator separator_3;
	protected JSeparator separator_4;
	protected JSeparator separator_5;
	protected JPanel canvas_panel;
	protected JPanel secondary_panel;
	protected JPanel status_panel;
	protected JPanel tools_panel;
	protected JPanel users_panel;
	protected JLabel lblUsersConected;
	protected JLabel lblUsers;
	protected JList list_client;

	protected JTextField textField;
	protected JButton btnSend;
	protected JTextArea chatArea;
	
	protected JButton btnFreeDraw;
	protected JButton btnLine;
	protected JComboBox <String>comboBoxSize;
	protected JButton btnSelSize;
	protected JButton btnCircle;
	protected JButton btnRectangle;
	protected JButton btnOval;
	protected JButton btnEraser;
	protected JButton btnMoreColor;
	protected JButton btnClear;
	protected JButton btnText;
	
	protected JTextField textField_inputCanvas;
	protected JButton btnColor1;
	protected JButton btnColor2;
	protected JButton btnColor3;
	protected JButton btnColor4;
	protected JButton btnColor5;
	protected JButton btnColor6;
	protected JButton btnColor7;
	protected JButton btnColor8;
	protected JButton btnColor9;
	protected JButton btnColor10;
	protected JLabel lblCurrentColor;
	protected JLabel lbl_status;
	protected JLabel lbl_chat;
	
	//***********************
	
	private int _shape = 2;
    private int _currentStartX = 0;  // where mouse first pressed
    private int _currentStartY = 0;
    private int _currentEndX   = 0;  // where dragged to or released
    private int _currentEndY   = 0;
	
	public static final int NONE      = 0;
    public static final int LINE      = 1;
    public static final int RECTANGLE = 2;
    public static final int CIRCLE    = 3;
    public static final int OVAL      = 4;
    public Graphics2D grafarea;
    
    //***********************
    Color col = Color.BLACK;
  	DrawArea1 drawArea;
	private String[] brushSizeList = {"1","2","3","4","5","6","8","10","12","14","16","20","24","32","48"};
	public int brushSize = 1;
	Boolean freeHandState = true, lineState = false, rectState = false, circleState = false, ovalState = false, textState = false;
	
	String hostName = "agmosql.database.windows.net";
    String dbName = "agmoSQL"; 
    String user = "agmo@agmosql";
    String password = "ds2019@@";
    String url = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;"
        + "hostNameInCertificate=*.database.windows.net;loginTimeout=30;", hostName, dbName, user, password);
    Connection connection = null;
	
	BufferedImage biOpen;
	
	JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
	BufferedImage bi;
	Container content;
	File selectedFile = null;
	
	
	protected static boolean isNew = true;
	protected static boolean isSaved = false;
	
	protected DefaultListModel<String> currentUsers;
	   
    //***********************
    
	ActionListener actionListener = new ActionListener() {
		 
		public void actionPerformed(ActionEvent e) {
	      	if (e.getSource() == btnClear) {
	      		drawArea.clear();
	      	} else if (e.getSource() == comboBoxSize) {
	      		drawArea.setBrushSize();
	      	} else if (e.getSource() == btnMoreColor) {
	      		drawArea.colorChooser();
	      	} else if (e.getSource() == btnFreeDraw) {
	      		drawArea.brush();
	      	} else if (e.getSource() == btnLine) {
	      		drawArea.line();
	      	} else if (e.getSource() == btnRectangle) {
	      		drawArea.rectangle();
	      	} else if (e.getSource() == btnCircle) {
	      		drawArea.circle();
	      	} else if (e.getSource() == btnOval) {
	      		drawArea.oval();
	      	} else if (e.getSource() == btnEraser) {
	      		drawArea.erase();
	      	} else if (e.getSource() == btnText) {
	      		drawArea.text();
	      	} else if (e.getSource() == mntmNew) {
	      		drawArea.newCanvas();
	      	} else if (e.getSource() == mntmSave) {
	      		drawArea.saveCanvas();
	      	} else if (e.getSource() == mntmSaveAs) {
	      		drawArea.saveAsCanvas();
	      	} else if (e.getSource() == mntmOpen) {
	      		drawArea.openCanvas();
	      	} else if (e.getSource() == mntmClose) {
	      		drawArea.closeCanvas();
	      	} else if (e.getSource() == btnColor1) {
	      		drawArea.colorChooser(btnColor1.getBackground());
	      	} else if (e.getSource() == btnColor2) {
	      		drawArea.colorChooser(btnColor2.getBackground());
	      	} else if (e.getSource() == btnColor3) {
	      		drawArea.colorChooser(btnColor3.getBackground());
	      	} else if (e.getSource() == btnColor4) {
	      		drawArea.colorChooser(btnColor4.getBackground());
	      	} else if (e.getSource() == btnColor5) {
	      		drawArea.colorChooser(btnColor5.getBackground());
	      	} else if (e.getSource() == btnColor6) {
	      		drawArea.colorChooser(btnColor6.getBackground());
	      	} else if (e.getSource() == btnColor7) {
	      		drawArea.colorChooser(btnColor7.getBackground());
	      	} else if (e.getSource() == btnColor8) {
	      		drawArea.colorChooser(btnColor8.getBackground());
	      	} else if (e.getSource() == btnColor9) {
	      		drawArea.colorChooser(btnColor9.getBackground());
	      	} else if (e.getSource() == btnColor10) {
	      		drawArea.colorChooser(btnColor10.getBackground());
	      	} 
	      	
	    }
	  };
	  
	  WindowListener windowListener = new WindowListener() {
		@Override
		public void windowOpened(WindowEvent e) {}
		@Override
		public void windowIconified(WindowEvent e) {}
		@Override
		public void windowDeiconified(WindowEvent e) {}
		@Override
		public void windowDeactivated(WindowEvent e) {}
		@Override
		public void windowClosing(WindowEvent e) {

		}
		@Override
		public void windowClosed(WindowEvent e) {
			
		}
		@Override
		public void windowActivated(WindowEvent e) {}
	};
	
	public ThreadedWhiteboardUser(String name, Color color, String host, String mname, String port) throws RemoteException {
		super(name, host, mname, port);
		getIdentity().setProperty("color", color);
		startUI();
		helper = new CommHelper(this);
		helper.start();
	}
	
	public boolean notifyOpen() throws RemoteException, IOException {
		drawArea.firstImage = true;
		return false;
	}
	
	public byte[] imageLoad() throws IOException {
		bi = new BufferedImage(drawArea.getSize().width, drawArea.getSize().height, BufferedImage.TYPE_INT_ARGB);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    ImageIO.write(bi, "png", bos );
	    bos.flush();
	    byte [] data = bos.toByteArray();
	    bos.close();
	    return data;
	}
//	
	protected void startUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					buildUI();
//						frmSharedWhitboard.setVisible(true);
//					}
////				    drawArea.g2.drawImage(currentImage,0,0,null);
////				    drawArea.repaint();
					broadcastUsers();
					frmSharedWhitboard.setVisible(true);
					
					
//					else {
//						System.out.println("Current Image is Null!");
//					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	//***********************
	
	protected void buildUI() {
		
		frmSharedWhitboard = new JFrame();
		frmSharedWhitboard.setTitle("Shared Whiteboard");
		frmSharedWhitboard.setIconImage(Toolkit.getDefaultToolkit().getImage(WhiteBoardInterface.class.getResource("/View/icons8-paint-palette-32.png")));
		frmSharedWhitboard.setBounds(100, 100, 1526, 998);
		frmSharedWhitboard.getContentPane().setBackground(new Color(12,92,117));
		frmSharedWhitboard.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmSharedWhitboard.setResizable(false);
		//Comment
		
		//File menu creation
		menuBar = new JMenuBar();
		frmSharedWhitboard.setJMenuBar(menuBar);
		
		mnFile = new JMenu("File");
		mnFile.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		menuBar.add(mnFile);
		
		mntmNew = new JMenuItem("New");
		mntmNew.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		mnFile.add(mntmNew);
		mntmNew.addActionListener(actionListener);
		mntmNew.setEnabled(false);
		
		mntmOpen = new JMenuItem("Open");
		mntmOpen.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		mnFile.add(mntmOpen);
		mntmOpen.addActionListener(actionListener);
		mntmOpen.setEnabled(false);
		
		mntmSave = new JMenuItem("Save");
		mntmSave.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		mnFile.add(mntmSave);
		mntmSave.addActionListener(actionListener);
		mntmSave.setEnabled(false);
		
		mntmSaveAs = new JMenuItem("Save As...");
		mntmSaveAs.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		mnFile.add(mntmSaveAs);
		mntmSaveAs.addActionListener(actionListener);
		mntmSaveAs.setEnabled(false);
		
		separator = new JSeparator();
		mnFile.add(separator);
		
		mntmClose = new JMenuItem("Close");
		mntmClose.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		mnFile.add(mntmClose);
		mntmClose.addActionListener(actionListener);
		
		//Get user name
		String name = null;
		try {
			name = getIdentity().getName();
		}catch (Exception e) {
			name = "Unknown";
		}
		//Status panel creation
		status_panel = new JPanel();
		status_panel.setBorder(new CompoundBorder(new LineBorder(Color.DARK_GRAY), new EmptyBorder(4, 4, 4, 4)));
		
		lbl_status = new JLabel("Welcome " + name);
		lbl_status.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_status.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		status_panel.add(lbl_status);
		
		//Other panels
		canvas_panel = new JPanel();
		tools_panel = new JPanel();
		tools_panel.setBackground(Color.WHITE);
		users_panel = new JPanel();
		users_panel.setBackground(new Color(24,154,167));
		secondary_panel = new JPanel();
		secondary_panel.setBackground(new Color(24,154,167));
		drawArea = new DrawArea1();
		drawArea.setBackground(Color.WHITE);
		//drawArea.setLayout(new BorderLayout(0, 0));
		
		separator_1 = new JSeparator();
		separator_2 = new JSeparator();
		separator_3 = new JSeparator();
		separator_4 = new JSeparator();
		separator_5 = new JSeparator();
		
		GroupLayout groupLayout = new GroupLayout(frmSharedWhitboard.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(tools_panel, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(drawArea, GroupLayout.DEFAULT_SIZE, 976, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(users_panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(secondary_panel, GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)))
						.addComponent(status_panel, GroupLayout.DEFAULT_SIZE, 1484, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(drawArea, GroupLayout.DEFAULT_SIZE, 867, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(users_panel, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(secondary_panel, GroupLayout.PREFERRED_SIZE, 625, GroupLayout.PREFERRED_SIZE))
						.addComponent(tools_panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(status_panel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		lblUsersConected = new JLabel("Users conected:");
		lblUsersConected.setForeground(Color.WHITE);
		lblUsersConected.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));

		lblUsers = new JLabel("X");
		lblUsers.setForeground(Color.WHITE);
		lblUsers.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 15));
		
		currentUsers = new DefaultListModel<String>();
		list_client = new JList<String>(currentUsers);
		list_client.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		
		GroupLayout gl_users_panel = new GroupLayout(users_panel);
		gl_users_panel.setHorizontalGroup(
			gl_users_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_users_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblUsersConected)
					.addGap(13)
					.addComponent(lblUsers)
					.addContainerGap(221, Short.MAX_VALUE))
				.addComponent(list_client, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
		);
		gl_users_panel.setVerticalGroup(
			gl_users_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_users_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_users_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUsers)
						.addComponent(lblUsersConected, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(list_client, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
		);
		users_panel.setLayout(gl_users_panel);
		drawArea.setLayout(new BorderLayout(0, 0));
				
		chatArea = new JTextArea();
		chatArea.setFont(new Font("Arial Unicode MS", Font.PLAIN, 13));
		chatArea.setEditable(false);
				
		textField = new JTextField();
		textField.setColumns(10);
		btnSend = new JButton("Send");
		btnSend.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		
		lbl_chat = new JLabel("Chat");
		lbl_chat.setForeground(Color.WHITE);
		lbl_chat.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		
		GroupLayout gl_secondary_panel = new GroupLayout(secondary_panel);
		gl_secondary_panel.setHorizontalGroup(
			gl_secondary_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_secondary_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(textField, GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSend, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addComponent(chatArea, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
				.addGroup(gl_secondary_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lbl_chat, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(261, Short.MAX_VALUE))
		);
		gl_secondary_panel.setVerticalGroup(
			gl_secondary_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_secondary_panel.createSequentialGroup()
					.addGap(12)
					.addComponent(lbl_chat, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(chatArea, GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_secondary_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSend, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		secondary_panel.setLayout(gl_secondary_panel);
		
		btnFreeDraw = new JButton("");
		btnFreeDraw.setIcon(new ImageIcon(WhiteBoardInterface.class.getResource("/View/icons8-pencil-drawing-32.png")));
		btnFreeDraw.addActionListener(actionListener);
		
		btnLine = new JButton("");
		btnLine.setIcon(new ImageIcon(WhiteBoardInterface.class.getResource("/View/icons8-line-32.png")));
		btnLine.addActionListener(actionListener);
		
		comboBoxSize = new JComboBox<String>(brushSizeList);
		comboBoxSize.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		comboBoxSize.addActionListener (actionListener);
		
		btnCircle = new JButton("");
		btnCircle.setIcon(new ImageIcon(WhiteBoardInterface.class.getResource("/View/icons8-circle-32.png")));
		btnCircle.addActionListener(actionListener);
		
		btnRectangle = new JButton("");
		btnRectangle.setIcon(new ImageIcon(WhiteBoardInterface.class.getResource("/View/icons8-rectangular-32.png")));
		btnRectangle.addActionListener(actionListener);
		
		btnOval = new JButton("");
		btnOval.setIcon(new ImageIcon(WhiteBoardInterface.class.getResource("/View/icons8-oval-32.png")));
		btnOval.addActionListener(actionListener);
		
		btnEraser = new JButton("");
		btnEraser.setIcon(new ImageIcon(WhiteBoardInterface.class.getResource("/View/icons8-eraser-32.png")));
		btnEraser.addActionListener(actionListener);
		
		btnMoreColor = new JButton("");
		btnMoreColor.setIcon(new ImageIcon(WhiteBoardInterface.class.getResource("/View/icons8-more-32.png")));
		btnMoreColor.addActionListener(actionListener);
		
		btnClear = new JButton("Clear");
		btnClear.setFont(new Font("Arial Unicode MS", Font.PLAIN, 16));
		btnClear.addActionListener(actionListener);
		btnClear.setEnabled(false);
		
		btnText = new JButton("");
		btnText.setIcon(new ImageIcon(WhiteBoardInterface.class.getResource("/View/icons8-type-32.png")));
		btnText.addActionListener(actionListener);
		textField_inputCanvas = new JTextField();
		textField_inputCanvas.setColumns(10);
		
		btnColor1 = new JButton("");
		btnColor1.setBackground(Color.BLACK);
		btnColor1.addActionListener(actionListener);
		
		btnColor2 = new JButton("");
		btnColor2.setOpaque(false);
		btnColor2.setBackground(Color.GRAY);
		btnColor2.addActionListener(actionListener);
		
		btnColor3 = new JButton("");
		btnColor3.setBackground(Color.RED);
		btnColor3.addActionListener(actionListener);
		
		btnColor4 = new JButton("");
		btnColor4.setBackground(Color.PINK);
		btnColor4.addActionListener(actionListener);
		
		btnColor5 = new JButton("");
		btnColor5.setBackground(new Color(128, 0, 0));
		btnColor5.addActionListener(actionListener);
		
		btnColor6 = new JButton("");
		btnColor6.setBackground(Color.ORANGE);
		btnColor6.addActionListener(actionListener);
		
		btnColor7 = new JButton("");
		btnColor7.setBackground(Color.GREEN);
		btnColor7.addActionListener(actionListener);
		
		btnColor8 = new JButton("");
		btnColor8.setBackground(Color.YELLOW);
		btnColor8.addActionListener(actionListener);
		
		btnColor9 = new JButton("");
		btnColor9.setBackground(Color.BLUE);
		btnColor9.addActionListener(actionListener);
		
		btnColor10 = new JButton("");
		btnColor10.setBackground(Color.CYAN);
		btnColor10.addActionListener(actionListener);
		
		lblCurrentColor = new JLabel("");
		lblCurrentColor.setOpaque(true);
		lblCurrentColor.setBackground(col);
		
		GroupLayout gl_tools_panel = new GroupLayout(tools_panel);
		gl_tools_panel.setHorizontalGroup(
			gl_tools_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tools_panel.createSequentialGroup()
					.addGroup(gl_tools_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_tools_panel.createSequentialGroup()
							.addGap(12)
							.addGroup(gl_tools_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(textField_inputCanvas, GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
								.addComponent(btnOval, GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
								.addComponent(btnRectangle, GroupLayout.PREFERRED_SIZE, 84, Short.MAX_VALUE)
								.addComponent(btnCircle, GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
								.addComponent(btnLine, GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
								.addComponent(comboBoxSize, 0, 84, Short.MAX_VALUE)
								.addComponent(btnEraser, GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
								.addComponent(btnFreeDraw, GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
								.addComponent(separator_3, GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
								.addComponent(btnText, GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)))
						.addGroup(gl_tools_panel.createSequentialGroup()
							.addGap(25)
							.addGroup(gl_tools_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_tools_panel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnColor9, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnColor10, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_tools_panel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnColor7, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnColor8, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_tools_panel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnColor5, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnColor6, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_tools_panel.createSequentialGroup()
									.addComponent(btnColor3, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnColor4, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_tools_panel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnColor1, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnColor2, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_tools_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(separator_4, GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE))
						.addGroup(gl_tools_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(separator_5, GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE))
						.addGroup(gl_tools_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblCurrentColor, GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE))
						.addGroup(gl_tools_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnMoreColor, GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_tools_panel.createSequentialGroup()
							.addGap(11)
							.addComponent(btnClear, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_tools_panel.setVerticalGroup(
			gl_tools_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_tools_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnFreeDraw)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnEraser)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comboBoxSize, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(separator_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnLine, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnCircle, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnRectangle, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnOval)
					.addGap(8)
					.addComponent(btnText, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textField_inputCanvas, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(separator_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_tools_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnColor2, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnColor1, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_tools_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnColor3, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnColor4, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_tools_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnColor6, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnColor5, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_tools_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnColor8, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnColor7, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_tools_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnColor10, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnColor9, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnMoreColor, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblCurrentColor, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(separator_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addGap(49))
		);
		tools_panel.setLayout(gl_tools_panel);
		frmSharedWhitboard.getContentPane().setLayout(groupLayout);
			
		//***********************
		
		
		
		whiteboard = canvas_panel;
		whiteboard.addMouseListener(this);
		whiteboard.addMouseMotionListener(this);
		buffer = whiteboard.createImage(canvas_panel.getSize().width,canvas_panel.getSize().height);
		
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// See if there's something to say...
				String msg = textField.getText();
				if (msg.length() > 0) {
					try {
						boolean success = broadcast("chat", msg);
						if (success) {
							lbl_status.setText("Sent message OK.");
						}
						else {
							lbl_status.setText("Failed to send message.");
						}
						// Clear the chat input field
						textField.setText("");
					}
					catch (Exception exc) {
					}
				}
			}
		});
	}
	
	//***********************
	
	protected void nextLine(String agent, Point pt, Color c) {
		Graphics g = buffer.getGraphics();
		g.setColor(c);
		Point lastPt = (Point)lastPts.get(agent);
		g.drawLine(lastPt.x, lastPt.y, pt.x, pt.y);
		canvas_panel.getGraphics().drawImage(buffer, 0, 0, canvas_panel);
	}
	public void mousePressed(MouseEvent ev) {
		System.out.println("mousePressed start");
		Point evPt = ev.getPoint();
		try {
			lastPts.put(getIdentity().getName(), evPt);
			CommHelper.addMsg("start", evPt);
			System.out.println("mousePressed end");
		}catch (Exception e) {
			System.out.println("MousePressed catch");
		}
	}	
	public void mouseReleased(MouseEvent ev) {
		System.out.println("MouseReleased start");
		Point evPt = ev.getPoint();
		try {
			nextLine(getIdentity().getName(), evPt,
					(Color)getIdentity().getProperty("color"));
			lastPts.remove(getIdentity().getName());
			helper.addMsg("end", evPt);
			System.out.println("MouseReleased end");
			helper.run();
		}catch (Exception e) {
			System.out.println(e);
		}
	}
	public void mouseDragged(MouseEvent ev) {
		Point evPt = ev.getPoint();
		try {
			nextLine(getIdentity().getName(), evPt,
					(Color)getIdentity().getProperty("color"));
			lastPts.put(getIdentity().getName(), evPt);
			helper.addMsg("drag", evPt);
		}catch (Exception e) {}
		
	}
	public void mouseExited(MouseEvent ev) {}
	public void mouseMoved(MouseEvent ev) {}
	public void mouseClicked(MouseEvent ev) {}
	public void mouseEntered(MouseEvent ev) {}
	
	public boolean notifyPaint(String shape, Color col, MouseEvent e, int X, int Y, int remoteBrushSize) {
		boolean success = false;
		success = drawArea.remotePaint(shape, col, e, X, Y, remoteBrushSize);
		return success;
	}
	
	public boolean exitCollaborator() throws RemoteException, IOException {
		System.out.println("Exit by admin...");
		frmSharedWhitboard.dispose();
		JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame,
			    "The admin has exited the program.",
			    "Admin exit",
			    JOptionPane.WARNING_MESSAGE);
		//System.exit(0);
		return false;
	}
	
	public boolean kickCollaborator() throws RemoteException, IOException{
		JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame,
			    "You have been kicked by the collaborator.",
			    "Kicked!",
			    JOptionPane.WARNING_MESSAGE);
		frmSharedWhitboard.dispose();
		//System.exit(0);
		return false;
	}
	
	public boolean notifyBI(BufferedImage image) {
		boolean success = false;
		success = drawArea.remotePaintBI(image);
		return success;
	}
	
	
	public boolean notifyUsers(ArrayList<String> clients)throws IOException, RemoteException { 
		currentUsers.removeAllElements();
		int currentUsersSize = 0;
		for (String temp : clients) {
			currentUsers.addElement(temp);
			System.out.println("model"+currentUsers);
			currentUsersSize ++;
		}
//		list_client = new JList<String>(currentUsers);
		lblUsers.setText(""+currentUsersSize);
		return true;
	}
	
//	public boolean notifyUsers(ArrayList<String> clients)throws IOException, RemoteException {    
//		System.out.println("HERE");
//		DefaultListModel<String> currentUsers = new DefaultListModel<String>();
////		currentUsers.removeAllElements();
//		
//		for (String temp : clients) {
//			currentUsers.addElement(temp);
//			//System.out.println(currentUsers);
//		}
////		list_client.removeAll();
//		list_client = new JList<String>(currentUsers);
//		lblUsers.setText(""+list_client.getComponentCount());
//		return true;
//	}
	
	public boolean notify(String tag, String msg, Identity src) throws IOException, RemoteException {
		// Print the message in the chat area.
		chatArea.append("\n" + src.getName() + ": " + msg);
		return true;
	}
	public boolean notify(String tag, Object data, Identity src) throws IOException, RemoteException {
		// If this is our own event, ignore it since it's already been handled.
		if (src.getName().compareTo(getIdentity().getName()) == 0) {
			return true;
		}
		Color agentColor = null;
		try {
			agentColor = (Color)src.getProperty("color");
		}
		catch (Exception exc) {
			System.out.println("Exception while getting color.");
			exc.printStackTrace();
		}
		if (tag.compareTo("start") == 0) {
			// First point along a path, save it and continue
		lastPts.put(src.getName(), data);
		}
		else if (tag.compareTo("drag") == 0) {
			// Next point in a path, draw a line from the last
			// point to here, and save this point as the last point.
			nextLine(src.getName(), (Point)data, agentColor);
			lastPts.put(src.getName(), data);
		}
		else if (tag.compareTo("end") == 0) {
			// Last point in a path, so draw the line and remove
			// the last point.
			nextLine(src.getName(), (Point)data, agentColor);
			lastPts.remove(src.getName());
		}
		return true;
	}
	
	
	//***********************
	
	class DrawArea1 extends JPanel {
		  
		  // Image in which we're going to draw
		  private Image image;
		  // Graphics2D object ==> used to draw on
		  private Graphics2D g2;
		  // Mouse coordinates
		  private int currentX, currentY, oldX, oldY;
		  public int currentBrushsize;
		  public boolean firstImage = true;
		 
		  public DrawArea1() {
		    setDoubleBuffered(false);
		    
//			try {
//				byte[] data = mediator.presentImage();
//			    ByteArrayInputStream bis = new ByteArrayInputStream(data);
//				BufferedImage bImage2;
//				bImage2 = ImageIO.read(bis);
//				currentImage = bImage2;
//			    g2.drawImage(currentImage,0,0,null);
//			    repaint();
//			} catch (IOException e2) {
//				// TODO Auto-generated catch block
//				e2.printStackTrace();
//			}
			
		    addMouseListener(new MouseAdapter() {
		      public void mousePressed(MouseEvent e) {
		        // save coord x,y when mouse is pressed
		        oldX = e.getX();
		        oldY = e.getY();		
		      }
		    });
		    
		    addMouseMotionListener(new MouseMotionAdapter() {
		      public void mouseDragged(MouseEvent e) {
		        // coord x,y when drag mouse
		        currentX = e.getX();
		        currentY = e.getY();
		        if ((g2 != null) & freeHandState == true){
		          // draw line if g2 context not null
		        	g2.setPaint(col);
		          g2.drawLine(oldX, oldY, currentX, currentY);
		          // refresh draw area to repaint
		          repaint();
		          isNew = false;
		          isSaved = false;
		          try {
						broadcastPaint("freeHand",col,e,oldX,oldY, brushSize);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		          oldX = currentX;
		          oldY = currentY;
		          
		        } 
		      }
		      
		      
		    });
		    
		    addMouseListener(new MouseAdapter() {
		    	public void mouseReleased(MouseEvent e) {
		    		int shapeWidth = Math.abs(currentX - oldX);
		    		int shapeHeight = Math.abs(currentY - oldY);
			    	  if((freeHandState == false) & (lineState == true)){
				        	g2.setPaint(col);
					        g2.drawLine(oldX, oldY, currentX, currentY);
					        repaint();
					        isSaved = false;
					        isNew = false;
					        try {
					        	//bi = new BufferedImage(drawArea.getSize().width, drawArea.getSize().height, BufferedImage.TYPE_INT_ARGB);
								//broadcastBI(bi);
					        	broadcastPaint("line",col,e,oldX,oldY, brushSize);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
				        	
				        } 
			    	  else if((freeHandState == false) & (rectState == true)){
				        	g2.setPaint(col);
					        g2.drawRect(oldX, oldY, shapeWidth, shapeHeight);
					        repaint();
					        isSaved = false;
					        isNew = false;
					        try {
								broadcastPaint("rectangle",col, e, oldX, oldY, brushSize);
								System.out.println("Painting broadcasted");
							} catch (IOException e1) {
								System.out.println("Error");
								e1.printStackTrace();
							}
				        	
				        } 
			    	  else if((freeHandState == false) & (circleState == true)){
				        	g2.setPaint(col);
					        g2.drawOval(oldX, oldY, shapeWidth, shapeWidth);
					        repaint();
					        isSaved = false;
					        isNew = false;
					        try {
								broadcastPaint("circle",col,e, oldX,oldY, brushSize);
								System.out.println("Painting broadcasted");
							} catch (IOException e1) {
								System.out.println("Error");
								e1.printStackTrace();
							}
				        	
				        } 
			    	  else if((freeHandState == false) & (ovalState == true)){
				        	g2.setPaint(col);
					        g2.drawOval(oldX, oldY, shapeWidth, shapeHeight);
					        repaint();
					        isSaved = false;
					        isNew = false;
					        try {
								broadcastPaint("oval",col,e, oldX,oldY, brushSize);
								System.out.println("Painting broadcasted");
							} catch (IOException e1) {
								System.out.println("Error");
								e1.printStackTrace();
							}
				        	
				        }
			    	  else if((freeHandState == false) & (textState == true)){
				        	g2.setPaint(col);
				        	Font font = new Font("Serif", Font.PLAIN, shapeWidth);	 
				        	g2.setFont(font);
					        g2.drawString(textField_inputCanvas.getText(), oldX, oldY);
					        repaint();
					        isSaved = false;
					        isNew = false;
					        try {
								broadcastPaint(textField_inputCanvas.getText(),col,e, oldX,oldY, brushSize);
								System.out.println("Painting broadcasted");
							} catch (IOException e1) {
								System.out.println("Error");
								e1.printStackTrace();
							}
				        	
				        }
			      }
			});
		  }
		 
		  public boolean remotePaintBI(BufferedImage image2) {
			g2.drawImage(image2,0,0,null);
			repaint();
			isNew = false;
			return true;
		}
		  
		public void loadCurentImageDB() {
				try {
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				} 
				catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				Connection connection = null;
				
				// Initialize connection object
				try
				{
					// get connection
					connection = DriverManager.getConnection(url, user, password);
					Statement stmt = connection.createStatement();
					ResultSet rs = stmt.executeQuery("Select Logo from broadcastToNewUsers;");
					if (rs.next()) {
					    Blob remoteImage = rs.getBlob("Logo");
					    InputStream in = remoteImage.getBinaryStream();  
					    Image image = ImageIO.read(in);
						g2.drawImage(image,0,0,null);
						repaint();
						System.out.println("Retrieved from database!");
					}
				    
				}
				catch (SQLException e)
				{
					System.out.println(e.getMessage());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		  
		public void loadCurrentImage() {
				File fileLoc = new File("G:\\My Drive\\DSAssignment2\\current.png");
				Image imageInput;
				try {
					imageInput = ImageIO.read(fileLoc);
					//drawArea.image = imageInput;
					BufferedImage bi = (BufferedImage) imageInput;
					Graphics g = bi.createGraphics();
					clear();
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
				    ImageIO.write(bi, "png", bos );
				    bos.flush();
				    byte [] data = bos.toByteArray();
				    ByteArrayInputStream bis = new ByteArrayInputStream(data);
				    bos.close();
				    imageInput = ImageIO.read(bis);
				    bis.close();
				    System.out.println(data);
					g2.drawImage(imageInput,0,0,null);
					repaint();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}

		protected void paintComponent(Graphics g) {
		    if (image == null) {
		      // image to draw null ==> we create
		      image = createImage(getSize().width, getSize().height);
		      g2 = (Graphics2D) image.getGraphics();
		      // enable antialiasing
		      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		      // clear draw area
		      clear();
		      
		    }
		    if(firstImage) {
		    	
		    	loadCurentImageDB();
		    	setBrushSize();
		    	firstImage = false;
		    }
		    g.drawImage(image, 0, 0, null);
		  }
		 
		  // now we create exposed methods
		  public void clear() {
			  col = Color.BLACK;
			  freeHandState = true;
			  lineState = false;
			  rectState = false;
			  circleState = false;
			  ovalState = false;
			  g2.setPaint(Color.white);
			  g2.fillRect(0, 0, getSize().width, getSize().height);
			  //g2.setPaint(Color.black);
			  g2.setStroke(new BasicStroke(1));
			  repaint();
			  isSaved = false;
			  isNew = true;
			  lbl_status.setText("Whiteboard Cleared");
		  }
		 
		  public void setBrushSize() {
			  brushSize = Integer.parseInt((String) comboBoxSize.getSelectedItem());
			  g2.setStroke(new BasicStroke(brushSize));
			  currentBrushsize = brushSize;
			  if(brushSize > 36) {
				  lbl_status.setText("Wow!");
			  }
		  }
		 
		  public void colorChooser() {
			  col = JColorChooser.showDialog(null, "Choose a color", col);
			  lblCurrentColor.setBackground(col);  
		  }
		  public void colorChooser(Color c) {
			  col = c;
			  lblCurrentColor.setBackground(col);  
		  }
		  
		  public void brush() {
			  if (col == Color.WHITE){
				  col = Color.BLACK;
			  }
			    freeHandState = true;
			    lineState = false;
			    rectState = false;
			    circleState = false;
			    ovalState = false;
		  }
		 
		  public void line() {
			  if (col == Color.WHITE){
				  col = Color.BLACK;
			  }
			  	freeHandState = false;
			    lineState = true;
			    rectState = false;
			    circleState = false;
			    ovalState = false;
		  }
		 
		  public void rectangle() {
			  if (col == Color.WHITE){
				  col = Color.BLACK;
			  }
			  	freeHandState = false;
			    lineState = false;
			    rectState = true;
			    circleState = false;
			    ovalState = false;
		  }
		  
		  public void circle() {
			  if (col == Color.WHITE){
				  col = Color.BLACK;
			  }
			  	freeHandState = false;
			    lineState = false;
			    rectState = false;
			    circleState = true;
			    ovalState = false;
		  }
		  
		  public void oval() {
			  if (col == Color.WHITE){
				  col = Color.BLACK;
			  }
			  	freeHandState = false;
			    lineState = false;
			    rectState = false;
			    circleState = false;
			    ovalState = true;
		  }
		  public void text() {
			  if (col == Color.WHITE){
				  col = Color.BLACK;
			  }
			  	freeHandState = false;
			    lineState = false;
			    rectState = false;
			    circleState = false;
			    ovalState = false;
			    textState = true;
		  }
		  
		  public void erase() {
			  if (col == Color.WHITE){
				  col = Color.BLACK;
			  }
			  freeHandState = true;
			    lineState = false;
			    rectState = false;
			    circleState = false;
			    ovalState = false;
			  col = Color.WHITE;
			    g2.setPaint(col);
		  }
		  public void newCanvas() {
			  if(!isSaved && !isNew) {
				  Object[] options = {"Save", "Don't Save", "Cancel"};
				  int n = JOptionPane.showOptionDialog(null,
				      "Would you like to save the canvas before creating a new one?",
				      "Save Canvas",
				      JOptionPane.YES_NO_CANCEL_OPTION,
				      JOptionPane.QUESTION_MESSAGE,
				      null,
				      options,
				      options[2]);				  
				  if (n == 0) {
					  saveAsCanvas();
					  isSaved = true;
					  clear();
				  } else if(n == 1) {
					  clear();
					  lbl_status.setText("...New Painting created");
				  } else {}
				  
			  }else {
				  clear();
				  lbl_status.setText("...New Painting created");
			  }
		  }
		  
		  public void saveCanvas() {
			  
			  if (selectedFile != null) {
				  try {
					  	bi = new BufferedImage(drawArea.getSize().width, drawArea.getSize().height, BufferedImage.TYPE_INT_ARGB); 
						Graphics g = bi.createGraphics();
						drawArea.paint(g); 
						g.dispose(); 
						ImageIO.write(bi,"png",selectedFile);
						lbl_status.setText("WhiteBoard Saved...");
						isSaved = true;
					}
					catch (Exception e1) {
						lbl_status.setText("Problems with saving!!!");
					}
			  } else {
				  saveAsCanvas();
			  }    
		  }
		  
		  public void saveAsCanvas() {
			  
			  bi = new BufferedImage(drawArea.getSize().width, drawArea.getSize().height, BufferedImage.TYPE_INT_ARGB); 
				Graphics g = bi.createGraphics();
				drawArea.paint(g); 
				g.dispose();
				int returnValue = jfc.showSaveDialog(null);
				ImageIcon imageIcon = new ImageIcon(bi); 

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					selectedFile = jfc.getSelectedFile();
					try {
						ImageIO.write(bi,"png",selectedFile);
						lbl_status.setText("WhiteBoard Saved...");
						isSaved = true;
					}
					catch (Exception e1) {
						lbl_status.setText("Problems with saving!!!");
					}
				}
		  }
		  
		  public void openCanvas() {
			  if(isNew) {
				  int returnValue = jfc.showOpenDialog(null);
				  clear();
					if (returnValue == JFileChooser.APPROVE_OPTION) {
						selectedFile = jfc.getSelectedFile();
						try {
							Image imageInput = ImageIO.read(selectedFile);
							//drawArea.image = imageInput;
							BufferedImage bi = (BufferedImage) imageInput;
							Graphics g = bi.createGraphics();
							clear();
							g2.drawImage(imageInput,0,0,null);
							repaint();
							isNew = false;
							lbl_status.setText("File opened sucessfully.");
						}
						catch (Exception e1) {
							lbl_status.setText("Error openning file!!!");
						}
					}
			  } else if(!isSaved) {
				  Object[] options = {"Save", "Don't Save", "Cancel"};
				  int n = JOptionPane.showOptionDialog(null,
				      "Would you like to save the canvas before creating a new one?",
				      "Save Canvas",
				      JOptionPane.YES_NO_CANCEL_OPTION,
				      JOptionPane.QUESTION_MESSAGE,
				      null,
				      options,
				      options[2]);				  
				  if (n == 0) {
					  saveAsCanvas();
					  isSaved = true;
					  int returnValue = jfc.showOpenDialog(null);
					  clear();
						if (returnValue == JFileChooser.APPROVE_OPTION) {
							selectedFile = jfc.getSelectedFile();
							try {
								Image imageInput = ImageIO.read(selectedFile);
								BufferedImage bi = (BufferedImage) imageInput;
								Graphics g = bi.createGraphics();
								clear();
								g2.drawImage(imageInput,0,0,null);
								repaint();
								isNew = false;
							}
							catch (Exception e1) {
								lbl_status.setText("Error opening file!!!");
							}
						}
					  
				  } else if(n == 1) {
					  int returnValue = jfc.showOpenDialog(null);
					  clear();
						if (returnValue == JFileChooser.APPROVE_OPTION) {
							selectedFile = jfc.getSelectedFile();
							try {
								Image imageInput = ImageIO.read(selectedFile);
								BufferedImage bi = (BufferedImage) imageInput;
								Graphics g = bi.createGraphics();
								clear();
								g2.drawImage(imageInput,0,0,null);
								repaint();
								isNew = false;
								lbl_status.setText("File opened sucessfully");
							}
							catch (Exception e1) {
								lbl_status.setText("Error opening file!!!");
							}
						}
						lbl_status.setText("File opened sucessfully");
				  } else {}
			  }	else {
				  int returnValue = jfc.showOpenDialog(null);
				  clear();
					if (returnValue == JFileChooser.APPROVE_OPTION) {
						selectedFile = jfc.getSelectedFile();
						try {
							Image imageInput = ImageIO.read(selectedFile);
							BufferedImage bi = (BufferedImage) imageInput;
							Graphics g = bi.createGraphics();
							clear();
							g2.drawImage(imageInput,0,0,null);
							repaint();
							isNew = false;
							lbl_status.setText("File opened sucessfully");
						}
						catch (Exception e1) {}
						lbl_status.setText("Error opening file!!!");
					}
					lbl_status.setText("File opened sucessfully");
			  }
		  }
		  
		  public boolean remotePaint(String shape, Color col, MouseEvent e, int X, int Y, int remoteBrushSize) {
			  int oldX = X;
			  int oldY = Y;
			  int xPos = e.getX();
			  int yPos = e.getY();
			  int temp = this.currentBrushsize;
			  System.out.println("Painting from remote");
			  if (shape.equals("line")){
				  g2.setStroke(new BasicStroke(remoteBrushSize));
				  line();
				  g2.setPaint(col);
			      g2.drawLine(oldX, oldY, xPos, yPos);
			      repaint();
			      isSaved = false;
			      isNew = false;
			  }else if(shape.equals("freeHand")) {				  
				  g2.setStroke(new BasicStroke(remoteBrushSize));
				  brush();
				  g2.setPaint(col);
			      g2.drawLine(oldX, oldY, xPos, yPos);
			      repaint();
			      isSaved = false;
			      isNew = false;
			  }
			  else if (shape.equals("oval")){
				  g2.setStroke(new BasicStroke(remoteBrushSize));
				  oval();
				  g2.setPaint(col);
			      g2.drawOval(oldX, oldY, xPos, yPos);
			      repaint();
			      isSaved = false;
			      isNew = false;
			  }
			  else if (shape.equals("circle")){
				  g2.setStroke(new BasicStroke(remoteBrushSize));
				  circle();
				  g2.setPaint(col);
			      g2.drawOval(oldX, oldY, xPos, xPos);
			      repaint();
			      isSaved = false;
			      isNew = false;
			  }
			  else if (shape.equals("rectangle")){
				  g2.setStroke(new BasicStroke(remoteBrushSize));
				  rectangle();
				  g2.setPaint(col);
			      g2.drawRect(oldX, oldY, xPos, yPos);
			      repaint();
			      isSaved = false;
			      isNew = false;
			  }
			  else if (shape.contentEquals("clear")){
				  clear();
			  }
			  else{
				  int shapeWidth = Math.abs(xPos - oldX);
				  g2.setPaint(col);
				  Font font = new Font("Serif", Font.PLAIN, shapeWidth);	 
				  g2.setFont(font);
			      g2.drawString(shape, oldX, oldY);
			      repaint();
			      isSaved = false;
			      isNew = false;
			  }
			  g2.setStroke(new BasicStroke(currentBrushsize));
			  return true;
			  
		  }
		  
		  

		  
		  public void closeCanvas() {
				Object[] options = {"Yes", "No"};
				int n = JOptionPane.showOptionDialog(null,
				      "Are you sure you would like to exit?",
				      "Close Canvas",
				      JOptionPane.YES_NO_OPTION,
				      JOptionPane.QUESTION_MESSAGE,
				      null,
				      options,
				      options[1]);
				if (n==0) {
					try {
						System.out.println(mediator.remove(getIdentity()));
						broadcastUsers();
						frmSharedWhitboard.dispose();
					} catch (RemoteException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				  }
		  }
		}
}

//***********************

class MyPanel extends JPanel{
	
}

class Msg {
	public Object data;
	public String tag;
	public Msg(String t, Object o) {
		data = o;
		tag = t;
	}
}

class CommHelper extends Thread {
	RMICollaborator collaborator;
	static Vector msgs = new Vector();
	public CommHelper(RMICollaborator c) {
		collaborator = c;
	}

	public static void addMsg(String t, Object o) {
		synchronized (msgs) {
			msgs.addElement(new Msg(t, o));
		}
	}
	public void run() {
		while (true) {
			try {
				Msg m = null;
				synchronized (msgs) {
					m = (Msg)msgs.elementAt(0);
					msgs.removeElementAt(0);
				}
				collaborator.broadcast(m.tag, m.data);
			}catch (Exception e) {}
		}
	}
}



