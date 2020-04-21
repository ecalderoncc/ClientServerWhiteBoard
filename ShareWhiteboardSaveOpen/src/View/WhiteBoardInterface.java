package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import javax.swing.JMenuBar;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Component;
import javax.swing.Box;
import java.awt.TextArea;
import javax.swing.BoxLayout;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.EmptyBorder;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JInternalFrame;
import java.awt.Canvas;
import java.awt.GridBagConstraints;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.JList;

public class WhiteBoardInterface {

	private JFrame frmSharedWhitboard;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmNew;
	private JMenuItem mntmOpen;
	private JMenuItem mntmSave;
	private JMenuItem mntmSaveAs;
	private JMenuItem mntmClose;
	private JSeparator separator;
	private JPanel canvas_panel;
	private JPanel secondary_panel;
	private JTextField textField;
	private JSeparator separator_4;
	private JSeparator separator_5;
	private JButton btnRectangle;
	private JButton btnOval;
	private JButton btnEraser;
	private JButton btnMoreColor;
	private JButton btnClear;
	private JPanel users_panel;
	private JLabel lblUsers;
	private JButton btnText;
	private JButton btnColor1;
	private JTextField textField_inputCanvas;
	private JLabel lblCurrentColor;
	private JButton btnColor2;
	private JButton btnColor3;
	private JButton btnColor4;
	private JButton btnColor5;
	private JButton btnColor6;
	private JButton btnColor7;
	private JButton btnColor8;
	private JButton btnColor9;
	private JButton btnColor10;
	private JList list_client;
	private JLabel lbl_chat;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WhiteBoardInterface window = new WhiteBoardInterface();
					window.frmSharedWhitboard.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WhiteBoardInterface() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSharedWhitboard = new JFrame();
		frmSharedWhitboard.setIconImage(Toolkit.getDefaultToolkit().getImage(WhiteBoardInterface.class.getResource("/View/icons8-paint-palette-32.png")));
		frmSharedWhitboard.setTitle("Shared Whiteboard");
		//frmSharedWhitboard.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//frmSharedWhitboard.setUndecorated(true);
		frmSharedWhitboard.setBounds(100, 100, 1526, 998);
		frmSharedWhitboard.getContentPane().setBackground(new Color(12,92,117));
		frmSharedWhitboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menuBar = new JMenuBar();
		frmSharedWhitboard.setJMenuBar(menuBar);
		
		mnFile = new JMenu("File");
		mnFile.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		menuBar.add(mnFile);
		
		mntmNew = new JMenuItem("New");
		mntmNew.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		mnFile.add(mntmNew);
		
		mntmOpen = new JMenuItem("Open");
		mntmOpen.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		mnFile.add(mntmOpen);
		
		mntmSave = new JMenuItem("Save");
		mntmSave.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		mnFile.add(mntmSave);
		
		mntmSaveAs = new JMenuItem("Save As...");
		mntmSaveAs.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		mnFile.add(mntmSaveAs);
		
		separator = new JSeparator();
		mnFile.add(separator);
		
		mntmClose = new JMenuItem("Close");
		mntmClose.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		mnFile.add(mntmClose);
		
		JPanel status_panel = new JPanel();
		status_panel.setBorder(new CompoundBorder(new LineBorder(Color.DARK_GRAY), new EmptyBorder(4, 4, 4, 4)));
		final JLabel status = new JLabel();
		status.setHorizontalAlignment(SwingConstants.CENTER);
		status_panel.add(status);
		
		canvas_panel = new JPanel();
		canvas_panel.setBackground(Color.WHITE);
		
		JPanel tools_panel = new JPanel();
		tools_panel.setBackground(Color.WHITE);
		
		secondary_panel = new JPanel();
		secondary_panel.setBackground(new Color(24,154,167));
		
		users_panel = new JPanel();
		users_panel.setBackground(new Color(24,154,167));

		GroupLayout groupLayout = new GroupLayout(frmSharedWhitboard.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(tools_panel, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(canvas_panel, GroupLayout.DEFAULT_SIZE, 976, Short.MAX_VALUE)
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
						.addComponent(canvas_panel, GroupLayout.DEFAULT_SIZE, 867, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(users_panel, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(secondary_panel, GroupLayout.PREFERRED_SIZE, 625, GroupLayout.PREFERRED_SIZE))
						.addComponent(tools_panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(status_panel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		JLabel lblUsersConected = new JLabel("Users conected:");
		lblUsersConected.setForeground(Color.WHITE);
		lblUsersConected.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		
		lblUsers = new JLabel("4");
		lblUsers.setForeground(Color.WHITE);
		lblUsers.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 15));
		
		list_client = new JList();
		
		JButton btn_kick = new JButton("Kick");
		btn_kick.setIcon(new ImageIcon(WhiteBoardInterface.class.getResource("/View/icons8-combat-32.png")));
		btn_kick.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		GroupLayout gl_users_panel = new GroupLayout(users_panel);
		gl_users_panel.setHorizontalGroup(
			gl_users_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_users_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblUsersConected)
					.addGap(13)
					.addComponent(lblUsers)
					.addPreferredGap(ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
					.addComponent(btn_kick)
					.addGap(10))
				.addComponent(list_client, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
		);
		gl_users_panel.setVerticalGroup(
			gl_users_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_users_panel.createSequentialGroup()
					.addGroup(gl_users_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_users_panel.createSequentialGroup()
							.addGap(9)
							.addComponent(btn_kick, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_users_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_users_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblUsersConected, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblUsers))))
					.addGap(10)
					.addComponent(list_client, GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE))
		);
		users_panel.setLayout(gl_users_panel);
		canvas_panel.setLayout(new BorderLayout(0, 0));
		
		JTextArea chatArea = new JTextArea();
		chatArea.setFont(new Font("Arial Unicode MS", Font.PLAIN, 13));
		chatArea.setEditable(false);
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JButton btnSend = new JButton("Send");
		btnSend.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
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
		
		JSeparator separator_3 = new JSeparator();
		
		separator_4 = new JSeparator();
		
		separator_5 = new JSeparator();
		
		JButton btnFreeDraw = new JButton("");
		btnFreeDraw.setIcon(new ImageIcon(WhiteBoardInterface.class.getResource("/View/icons8-pencil-drawing-32.png")));
		
		JButton btnLine = new JButton("");
		btnLine.setIcon(new ImageIcon(WhiteBoardInterface.class.getResource("/View/icons8-line-32.png")));
		
		JComboBox comboBoxSize = new JComboBox();
		comboBoxSize.setFont(new Font("Segoe UI Light", Font.BOLD, 16));
		
		JButton btnCircle = new JButton("");
		btnCircle.setIcon(new ImageIcon(WhiteBoardInterface.class.getResource("/View/icons8-circle-32.png")));
		btnCircle.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		btnRectangle = new JButton("");
		btnRectangle.setIcon(new ImageIcon(WhiteBoardInterface.class.getResource("/View/icons8-rectangular-32.png")));
		btnRectangle.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		btnOval = new JButton("");
		btnOval.setIcon(new ImageIcon(WhiteBoardInterface.class.getResource("/View/icons8-oval-32.png")));
		btnOval.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		btnEraser = new JButton("");
		btnEraser.setIcon(new ImageIcon(WhiteBoardInterface.class.getResource("/View/icons8-eraser-32.png")));
		btnEraser.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		btnMoreColor = new JButton("");
		btnMoreColor.setIcon(new ImageIcon(WhiteBoardInterface.class.getResource("/View/icons8-more-32.png")));
		btnMoreColor.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnMoreColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		btnClear = new JButton("Clear");
		btnClear.setFont(new Font("Arial Unicode MS", Font.PLAIN, 16));
		
		btnText = new JButton("");
		btnText.setIcon(new ImageIcon(WhiteBoardInterface.class.getResource("/View/icons8-type-32.png")));
		btnText.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		btnColor1 = new JButton("");
		btnColor1.setOpaque(false);
		btnColor1.setBackground(Color.BLACK);

			
		textField_inputCanvas = new JTextField();
		textField_inputCanvas.setColumns(10);
		
		lblCurrentColor = new JLabel("");
		lblCurrentColor.setOpaque(true);
		lblCurrentColor.setBackground(Color.ORANGE);
		
		btnColor2 = new JButton("");
		btnColor2.setOpaque(false);
		btnColor2.setBackground(Color.GRAY);
		
		btnColor3 = new JButton("");
		btnColor3.setOpaque(false);
		btnColor3.setBackground(Color.RED);
		
		btnColor4 = new JButton("");
		btnColor4.setOpaque(false);
		btnColor4.setBackground(Color.PINK);
		
		btnColor5 = new JButton("");
		btnColor5.setOpaque(false);
		btnColor5.setBackground(new Color(128, 0, 0));
		
		btnColor6 = new JButton("");
		btnColor6.setOpaque(false);
		btnColor6.setBackground(Color.ORANGE);
		
		btnColor7 = new JButton("");
		btnColor7.setOpaque(false);
		btnColor7.setBackground(Color.GREEN);
		
		btnColor8 = new JButton("");
		btnColor8.setOpaque(false);
		btnColor8.setBackground(Color.YELLOW);
		
		btnColor9 = new JButton("");
		btnColor9.setOpaque(false);
		btnColor9.setBackground(Color.BLUE);
		
		btnColor10 = new JButton("");
		btnColor10.setOpaque(false);
		btnColor10.setBackground(Color.CYAN);
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
	}
}

//*************




