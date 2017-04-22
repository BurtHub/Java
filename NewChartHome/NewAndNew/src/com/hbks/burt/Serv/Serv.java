package com.hbks.burt.Serv;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.hbks.burt.ResourceInterface.MessageResourceHead;

public class Serv implements MessageResourceHead {
	private static JFrame fr;
	private static JButton bt, bt1;
	private static JLabel lb;
	private static JTextArea ta;
	private static JTextField tf, tf1;

	private ServerSocket Server;
	private ClientList cst = null;
	private ClientList cls = ClientList.cl;

	/*********************** �շ�һ���߳� ***************************************/
	/*
	 * ��������� ���յ�����Ϣ���з����ת�� ������� ˽�� Ⱥ��
	 */

	/**
	 * ����ʵ�� ����ClientList ���Ҿ�����ϵ�ˣ� �������0ֱ�ӽ���Ⱥ��
	 **/
	/* ͷ*************************������Ϣ�߳�**************************** */
	private void Receive(final String port, final Socket socket) {
		ExecutorService pool = Executors.newSingleThreadExecutor();
		pool.execute(new Runnable() {
			public void run() {
				while (true) {
					BufferedReader bfr;
					try {
						bfr = new BufferedReader(new InputStreamReader(socket
								.getInputStream()));
						String s;
						while ((s = bfr.readLine()) != null) {
							if (s.length() > 0)
								ta.append(getTime() + " " + port + " " + s
										+ "\r\n");
							ReceiveCode(s);
						}
					} catch (IOException e) {}
					
				}

			}
		});
	}

	/************************** ��Ϣ����ģ��**************************** */
	private void ReceiveCode(String s) {// ���յ�����Ϣ���н���,��ת��
		//���յ�һ������Ŀ���Ϣ�����Լ�����
		if (s.length()!=0&&s != null) {
			int code = Integer.parseInt(s.substring(0, 1));
			int port=ManagerMessage;
			if(!s.contains("Ⱥ��")){
				port =Integer.parseInt(s.substring(1,6));
			}
			
			String Message = s.substring(1);
			switch (code) {
			case GroupMessage:// Ⱥ��
				ReceivedAndSend(0, GroupMessage + Message);
				break;
			case PrivateMessage:// ˽��
				ReceivedAndSend(port, PrivateMessage + Message);
				break;
			}
		}
	}

	public void ReceivedAndSend(int code, String msg) {// ��Ϣת�룬���ն˿ڲ�����ϵ�˲�����
		Set<Entry<String, Socket>> set = cls.GetMap();
		Iterator<Entry<String, Socket>> it = set.iterator();
		while (it.hasNext()) {
			String sq = it.next() + "";
			String a[] = sq.split("=");
			int port = Integer.parseInt(a[0]);
			if (code == GroupMessage) {
				//System.out.println(port+"Ⱥ��");
				SendToClient(cls.SelClient(port + ""), msg);
			} else if (code == port) {
				SendToClient(cls.SelClient(port + ""), msg);
			}
		}
	}

	private void SendToClient(Socket client, String s) {// ������Ϣ��ָ��Socket
		//System.out.println(s);
		OutputStream out;
		try {
			out = client.getOutputStream();
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out), true);
			pw.println(s + "\r\n");
		} catch (IOException e) {
			System.out.println("ת���쳣");
		}

	}
	
	private String TraverseFriendList(){//ָ����ϵ�˱��������б�Ⱥ��
		Set<Entry<String, Socket>> set = cls.GetMap();
		Iterator<Entry<String, Socket>> it = set.iterator();
		StringBuilder Message = new StringBuilder();
		while (it.hasNext()) {
			String sq = it.next() + "";
			String a[] = sq.split("=");
			int port = Integer.parseInt(a[0]);
			Message.append(port+":");
		}	
		return	new String(Message);
	}		
		
	
	
	private void SendToClientFriendList(){//���б�����ȡÿ���ͻ��ˣ��ɷ����óɵݹ飩
		Set<Entry<String, Socket>> set = cls.GetMap();
		Iterator<Entry<String, Socket>> it = set.iterator();
		while (it.hasNext()) {
			String sq = it.next() + "";
			//System.out.println(sq+"�����б�");
			String a[] = sq.split("=");
			int port = Integer.parseInt(a[0]);
			String Flist=TraverseFriendList();
			if(Flist==null){
				SendToClient(cls.SelClient(port + ""), FriendList+" û�к�������");
			}else{
				SendToClient(cls.SelClient(port + ""), FriendList+Flist);
			}
			
		}		
			
		
	}
	/*********************************** ��ʼ��ģ�� ******************************************/
	public void Create(int s) {
		try {
			Server = new ServerSocket(s);
		} catch (IOException e1) {
			ta.append(getTime() + " ����������ʧ��" + "\r\n");
		}
		cst = ClientList.cl;
		ExecutorService poolConnection = Executors.newSingleThreadExecutor();
		poolConnection.execute(new Runnable() {
			public void run() {
				try {
					while (true) {
						Socket socket = Server.accept();
						String port = socket.getPort() + "";
						cst.AddClient(port, socket);// ��ӵ��ͻ��б���
						SendToClient(socket, ManagerMessage+"��ӭ����������");
						Receive(port, socket);
						ta.append(getTime() + " " + port + " ������" + "\r\n");
						// ���Ӻ��ÿ���ͻ��˷��ͺ����б�
						SendToClientFriendList();
					}
				} catch (Exception e) {
					e.printStackTrace();
					ta.append(getTime() + " �ͻ��������쳣" + "\r\n");
				}
			}

		});
		ta.append(getTime() + " �����������ɹ�" + "\r\n");
	}

	private void addListener() {

		bt.addMouseListener(new MouseAdapter() {// ����
			public void mouseClicked(MouseEvent e) {
				Create(Integer.parseInt(tf.getText()));
			}
		});

		bt1.addMouseListener(new MouseAdapter() {// ��������
			public void mouseClicked(MouseEvent e) {

			}
		});

		bt1.addKeyListener(new KeyAdapter() {// ���س�������
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

				}
			}
		});
		fr.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	private String getTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// �������ڸ�ʽ
		return (df.format(new Date()));// new Date()Ϊ��ȡ��ǰϵͳʱ��
	}

	public void init() {
		fr = new JFrame("������");
		fr.setLayout(null);
		lb = new JLabel(" ������˿�:");

		tf = new JTextField();
		tf.setText("5012");
		tf1 = new JTextField();
		ta = new JTextArea();
		bt = new JButton("����������");
		bt1 = new JButton("����");
		fr.setBounds(700, 110, 427, 381);

		fr.getContentPane().add(lb);
		lb.setBounds(10, 10, 90, 24);

		fr.getContentPane().add(tf);
		tf.setBounds(120, 10, 50, 24);

		fr.getContentPane().add(bt);
		bt.setBounds(281, 10, 112, 24);

		fr.getContentPane().add(ta);
		ta.setBounds(10, 50, 390, 230);

		fr.getContentPane().add(tf1);
		tf1.setBounds(10, 290, 240, 30);

		fr.getContentPane().add(bt1);
		bt1.setBounds(280, 290, 130, 30);
		addListener();

		fr.setVisible(true);
	}
}
