package com.hbks.burt.Client;

import java.awt.List;
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
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.hbks.burt.ResourceInterface.MessageResourceHead;

public class Client implements MessageResourceHead {
	private static JFrame fr;
	private static JButton bt, bt1;
	private static JLabel lb, lb1;
	private static List jl;
	private static JTextArea ta;
	private static JTextField tf, tf1;

	private Socket Client = null;
	private String LocalPort=null;

	/*************************** �շ������� ****************************/

	private void ReceiveCode(String s) { // �ж���Ϣ���͸���UI��Ϣ
		//���յ�һ������Ŀ���Ϣ�����Լ�����
		if (s.length()!=0&&s != null) {
			int code = Integer.parseInt(s.substring(0, 1));
			switch (code) {
			case GroupMessage:					
				ta.append(getTime() + " " + s.substring(1) + "\r\n");
				break;
			case PrivateMessage:
				ta.append(getTime() + " " + s.substring(1) + "\r\n");
				break;
			case FriendList:
				jl.removeAll();
				jl.add("Ⱥ��");
				
				//�������������͵ĺ����б����ݰ�
				String FList=s.substring(1);
				String[] ports=FList.split(":", 0);
				for(String i:ports){
					if((!i.equals(LocalPort))&&i.length()>0)
					jl.add(i);
				}
				jl.select(0);
				break;
			case ManagerMessage:
				ta.append(getTime()+" ����Ա��" + s.substring(1)+ "\r\n");
				break;
			}
		}
	}

	private boolean Connection() {
		try {
			Client = new Socket("127.0.0.1", Integer.parseInt(tf.getText()));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**************************������Ϣ�߳�*****************************/
	private void Receive() {
		ExecutorService pool = Executors.newSingleThreadExecutor();
		pool.execute(new Runnable() {
			public void run() {
				while (true) {
					BufferedReader bfr;
					try {
						bfr = new BufferedReader(new InputStreamReader(Client
								.getInputStream()));
						String s=null;
						while ((s = bfr.readLine()) != null) {
							System.out.println(s);
							ReceiveCode(s);
						}
						
					} catch (IOException e) {
					}
				}
			}
		});
	}
	/************************** ���غ����б����(�߳�) *****************************/
	/*private void CheckFriendList(){//ʹ������ȥ���㷨
		ExecutorService pool = Executors.newSingleThreadExecutor();
		pool.execute(new Runnable() {
			public void run() {
				while (true) {
					try {Thread.sleep(500);} catch (InterruptedException e) {}
					String[] items = jl.getItems();
					int start=0;
					int end=items.length-1;
					while(items.length>0){
						
					}
				
				}
			}
		});
	}*/
	/************************** ������Ϣ(�߳�) *****************************/
	private void SendToServ(String s) throws IOException {
		OutputStream out = Client.getOutputStream();
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(out), true);
		pw.println(s + "\r\n");
	}

	/************************** ��Ϣת���� *****************************/
	private String SendCode(String s) {
		if (s.contains("Ⱥ��")) {
			return GroupMessage + s;
		} else {
			return PrivateMessage + s;
		}
	}

	private String getMessage() {
		String code = SendCode(jl.getSelectedItem());
		String s = tf1.getText();
		tf1.setText(null);
		if (s.length() > 0) {
			//ta.append(getTime() + " �Լ���" + s + "\r\n");
			return code + ":" + s;
		} else {
			tf1.setToolTipText("�����������");
			return null;
		}
	}

	private String getTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// �������ڸ�ʽ
		return (df.format(new Date()));// new Date()Ϊ��ȡ��ǰϵͳʱ��
	}

	/************************** ��������� *****************************/
	private void addListener() {
		fr.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		bt.addMouseListener(new MouseAdapter() {// ����
			public void mouseClicked(MouseEvent e) {
				if (Connection()) {
					Receive();
					LocalPort=Client.getLocalPort()+"";
					fr.setTitle("���"+LocalPort);
					lb.setText("���ӳɹ�");
					bt.setEnabled(false);
				} else {
					lb.setText("����ʧ��");
				}
			}
		});

		bt1.addMouseListener(new MouseAdapter() {// ��������
			public void mouseClicked(MouseEvent e) {
				try {
					String s = getMessage();
					if (s != null) {
						SendToServ(s);
					}

				} catch (IOException e1) {
					System.out.println("�����쳣");
				}
			}
		});

		bt1.addKeyListener(new KeyAdapter() {// ���س�������
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						String s = getMessage();
						if (s.length() > 0)
							SendToServ(s);
					} catch (IOException e1) {
						System.out.println("�����쳣");
					}

				}
			}
		});

	}

	/******************* 88888888888**********���沿��**********88888888888 ****************************/

	public void init() {
		fr = new JFrame("���촰��");
		fr.setLayout(null);
		lb = new JLabel("������δ���ӣ� ������˿�");
		lb1 = new JLabel("���ߺ���");
		jl = new List();
		jl.add("Ⱥ��");
		jl.select(0);
		tf = new JTextField();
		tf.setText("5012");
		tf1 = new JTextField();
		ta = new JTextArea();
		bt = new JButton("����");
		bt1 = new JButton("����");
		fr.setBounds(100, 150, 481, 371);

		fr.getContentPane().add(lb);
		lb.setBounds(10, 10, 170, 24);

		fr.getContentPane().add(lb1);
		lb1.setBounds(370, 10, 120, 24);

		fr.getContentPane().add(tf);
		tf.setBounds(190, 10, 50, 24);

		fr.getContentPane().add(bt);
		bt.setBounds(251, 10, 112, 24);

		fr.getContentPane().add(ta);
		ta.setBounds(10, 50, 300, 230);

		fr.getContentPane().add(tf1);
		tf1.setBounds(10, 290, 300, 30);

		fr.getContentPane().add(jl);
		jl.setBounds(322, 50, 130, 230);

		fr.getContentPane().add(bt1);
		bt1.setBounds(322, 290, 130, 30);
		addListener();

		tf1.setText("�����������");
		fr.setVisible(true);
	}

}
