package MainActivity;

import java.awt.List;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;



/*****************88888************�ͻ��˴���*****************8888888*********************/

public class CliIntent {
	private JFrame fr;
	private JButton bt,bt1;
	private JLabel lb,lb1;
	private List jl;
	private JTextArea ta;
	private JTextField tf,tf1;
	ClientList cls=ClientList.cl;
	
	CliIntent(){
		Init();
	}
/****************8888888*************�ͻ������Ӳ���**************8888888************************/
	 class InnerClient{
		//��ȡ�ͻ��б��ڵ������Ӱ�ťʱ���������б��еĿͻ���ӵ�List��
		
		private Socket Client=null;
		InnerClient() { }
		
		boolean Connection(){
			try {
				Client=new Socket("127.0.0.1",Integer.parseInt(tf.getText()));
				ReceivePool();
				
			} catch (Exception e) {
				return false;
			}
			return true;
		}
		//�����߳�(���ղ���,ʹ���̳߳�)
		void ReceivePool(){
			ExecutorService pool=Executors.newSingleThreadExecutor();
			pool.execute(new Runnable() {
				public void run() {
					BufferedReader bfr;
					try {
						bfr=new BufferedReader(new InputStreamReader(Client.getInputStream()));
						String s;
						while((s=bfr.readLine())!=null){
							ta.append(s+"\r\n");
						}
					} catch (IOException e) {}
					
				}
			});
		}
		
		//Ⱥ�ĺ�˽�Ĺ��ܣ���List�еĶ˿ں�תΪint���ͺ��͸�������
		
	}
/************8888888*****************����������****************88888888888**********************/
	void FlushFriendList(){  //ˢ�º����б�
		Set<Entry<String, Socket>> set=cls.GetMap();
		Iterator<Entry<String, Socket>> it=set.iterator();
		while(it.hasNext()){
			String s=it.next()+"";
			String a[]=s.split("=");
			String[] lists=jl.getItems();
			boolean flag=true;
			for(String a1:lists){
				if(a[0].equals(a1))flag=false;
			}
			if(flag)jl.add(a[0]);
		}
	}
	void SendToallFriend(String s) throws NumberFormatException, IOException{
		Set<Entry<String, Socket>> set=cls.GetMap();
		Iterator<Entry<String, Socket>> it=set.iterator();
		while(it.hasNext()){
			String sq=it.next()+"";
			String a[]=sq.split("=");
			SendToFriend(Integer.parseInt(a[0]),s);
			
		}
	}
	void SendToFriend(int code,String s) throws IOException{
		Socket sendSocket=cls.SelClient(code+"");
		OutputStream out=sendSocket.getOutputStream();
		PrintWriter pw=new PrintWriter(new OutputStreamWriter(out),true);
		pw.println(code+":"+s+"\r\n");
	}
	
	void addListener(){
		bt.addMouseListener(new MouseAdapter() {//����
			public void mouseClicked(MouseEvent e) {
				if(bt.getText()=="ˢ�º����б�"||bt.getText()=="���ӳɹ�"){
					FlushFriendList();
					return;
					}
				if(new InnerClient().Connection()){
					lb.setText("���ӳɹ�");
					bt.setText("ˢ�º����б�");
					FlushFriendList();
				
				}else
					lb.setText("�����б�ɹ�ˢ��");
					FlushFriendList();
				
			}
		});
		
		bt1.addMouseListener(new MouseAdapter() {//��������
			public void mouseClicked(MouseEvent e) {
				String s=tf1.getText();
				tf1.setText(null);
				
				try{
					if(jl.getSelectedItem().equals("Ⱥ��")){
						SendToallFriend(s);
						ta.append("Ⱥ��"+":"+s+"\r\n");
					}else{
						int code=Integer.parseInt(jl.getSelectedItem());
						SendToFriend(code,s);
						ta.append(code+":"+s+"\r\n");
					
					}
				}catch(Exception e1){}
				System.out.println("����ʧ��");
			}
		});
		
		bt1.addKeyListener(new KeyAdapter() {//���س�������
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					String s=tf1.getText();
					tf1.setText(null);
					
					try{
						if(jl.getSelectedItem().equals("Ⱥ��")){
							SendToallFriend(s);
							ta.append("Ⱥ��"+":"+s+"\r\n");
						}else{
							int code=Integer.parseInt(jl.getSelectedItem());
							SendToFriend(code,s);
							ta.append(code+":"+s+"\r\n");
						
						}
					}catch(Exception e1){}
					System.out.println("����ʧ��");
					
				}
			}
			
		});
		
	}	
/*******************88888888888**********���沿��**********88888888888****************************/
	void Init(){
		
		fr=new JFrame("���촰��");
		fr.setLayout(null);
		lb=new JLabel("������δ���ӣ� ������˿�");
		lb1=new JLabel("���ߺ���");
		jl=new List();
		jl.add("Ⱥ��");
		jl.select(0);
		tf=new JTextField();
		tf1=new JTextField();
		ta=new JTextArea();
		bt=new JButton("����");
		bt1=new JButton("����");
		fr.setBounds(100, 150,481, 371);
		
		fr.getContentPane().add(lb);
		lb.setBounds(10,10, 170, 24);
		
		fr.getContentPane().add(lb1);
		lb1.setBounds(370,10, 120, 24);
		
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
		
		fr.setVisible(true);
	}
	

}
