

import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

//���ݿ��,Ҫ��Ψһ������ʹ�õ���ģʽ
public class ClientList {    //Ŀǰ��Ŷ˿ںţ� ���� ���IP��ַ
	public static ClientList cl =new ClientList();
	private ClientList(){}
	private TreeMap<String,String> Tree=new TreeMap<String,String>();
	
	public void AddClient(String ip,String client){
		Tree.put(ip, client);
	}
	public void DelClient(String ip){//�Ƴ���Ӧ�Ŀͻ���
		Tree.remove(ip);
	}
	public String SelClient(String ip){//��ȡ��Ӧ��Socket
		return Tree.get(ip);
	}
	
	public Set<Entry<String, String>> GetMap(){//�������пͻ��˶˿ں�
		return Tree.entrySet();
	}
}
