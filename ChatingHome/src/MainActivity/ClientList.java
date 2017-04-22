package MainActivity;

import java.net.Socket;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

//���ݿ��,Ҫ��Ψһ������ʹ�õ���ģʽ
public class ClientList {//Ŀǰ��Ŷ˿ںţ���������� ���IP��ַ
	public static ClientList cl =new ClientList();
	private ClientList(){}
	private TreeMap<String,Socket> Tree=new TreeMap<String,Socket>();
	
	public void AddClient(String ip,Socket client){
		Tree.put(ip, client);
	}
	public void DelClient(String ip){//�Ƴ���Ӧ�Ŀͻ���
		Tree.remove(ip);
	}
	public Socket SelClient(String ip){//��ȡ��Ӧ��Socket
		return Tree.get(ip);
	}
	
	public Set<Entry<String, Socket>> GetMap(){//�������пͻ��˶˿ں�
		return Tree.entrySet();
	}
}
