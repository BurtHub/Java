package MainActivity;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servicer {
	private ServerSocket Server;
	private ClientList cst=null;
	private Socket socket;
	public Servicer(int s) throws IOException{
		Server=new ServerSocket(s);
		cst=ClientList.cl;
		
		//new Thread(new Repeat(cst.SelClient(ip))).start();
		//��ӿͻ��˵�ClientList��
	/********************************�����߳�**************************************/	
		
		ExecutorService poolConnection=Executors.newSingleThreadExecutor();
		poolConnection.execute(new Runnable() {
			public void run() {
				try{
				while(true){
					socket=Server.accept();
					String  port=socket.getPort()+"";
					cst.AddClient(port,socket);//��ӵ��ͻ��б���
				}	
				}catch(Exception e){}
			}
			
		});
		System.out.println("��������������");
			//���ж˿�ѡ���ܣ��յ�С��1024��ʱ��ΪȺ��
	/*************************************���ղ�ת���߳�******************************************/
	/*	
	ExecutorService PoolRepeat=Executors.newSingleThreadExecutor();
	PoolRepeat.execute(new Runnable() {
		
		public void run() {
			
			InputStreamReader isr = null;
			BufferedReader br = null;
			try{
				isr=new InputStreamReader(socket.getInputStream());
				br =new  BufferedReader(isr);
				while((br.readLine())!=null){
					//���չ���
					isr=new InputStreamReader(socket.getInputStream());
					br =new  BufferedReader(isr);
					System.out.println(br.readLine());
					System.out.println("��������ʼ����");
					OutputStream out=socket.getOutputStream();
					PrintWriter pw=new PrintWriter(new OutputStreamWriter(out),true);
					int q=socket.getPort();
					pw.write(q+"����");
					System.out.println("�������");
					pw.flush();
				
				}
			}catch(Exception e){}finally{
				try{
					br.close();
					isr.close();
				}catch(Exception e){}
				
			}
			
			
		}
	});*/
	
	
	
	}
	
	
/*	class Connection implements Runnable{
		public void run() {
			PrintWriter pw = null;
			try{
			while(true){
				Socket socket=Server.accept();
				pw=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
				String  port=socket.getPort()+"";
				pw.println("�˿ڣ�"+port);
				pw.flush();
						}	
			}catch(Exception e){
				
			}finally{
				pw.close();
				System.out.println("���չر�");
			}
		}
	}*/

}
