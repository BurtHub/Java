package MainActivity;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Main {
	JFrame jf;
	JLabel jl;
	JTextField jt;
	JButton jb,jb1;
	Main(){
		jf=new JFrame("������");
		jb=new JButton("����������");
		jl=new JLabel("������λ����");
		jb1=new JButton("�����û�");
		jt=new JTextField("5012");
		jf.add(jb);
		jf.add(jb1);
		jf.add(jl);
		
		jf.add(jt);
		jf.setLayout(new GridLayout(2,2));
		jf.setBounds(600, 300, 350, 120);
		jf.setVisible(true);
		addListener();
	}
	
	public void addListener(){
		jf.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		jb.addMouseListener(new MouseAdapter() {
			//�ͻ��ˣ�ʵ���������������߳���
			public void mouseClicked(MouseEvent e) {
				try {
					new Servicer(Integer.parseInt(jt.getText()));
				} catch (IOException e1) {
					System.out.println("����������ʧ��");
				}
				}
		});
		
		
		jb1.addMouseListener(new MouseAdapter() {
			//�ͻ��ˣ�ʵ���������������߳���
			public void mouseClicked(MouseEvent e) {
				new CliIntent();
				}
		});
	}
	public static void main(String[] args) {
		new Main();

	}

}
