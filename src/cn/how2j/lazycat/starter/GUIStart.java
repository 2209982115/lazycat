package cn.how2j.lazycat.starter;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import cn.how2j.lazycat.exception.PortException;
import cn.how2j.lazycat.gui.dialog.InitDialog;
import cn.how2j.lazycat.gui.dialog.ProgressDialog;
import cn.how2j.lazycat.gui.frame.MainFrame;
import cn.how2j.lazycat.gui.panel.MainPanel;
import cn.how2j.lazycat.gui.panel.TomcatPanel;
import cn.how2j.lazycat.gui.panel.WebAppPanel;
import cn.how2j.lazycat.gui.util.GUIUtil;
import cn.how2j.lazycat.gui.util.LogPrintStream4err;
import cn.how2j.lazycat.gui.util.LogPrintStream4out;
import cn.how2j.lazycat.pojo.Tomcat;
import cn.how2j.lazycat.pojo.WebApp;
import cn.how2j.lazycat.util.CatalinaUtil;
import cn.how2j.lazycat.util.HotKey;
import cn.how2j.lazycat.util.MapperUtil;
import cn.how2j.lazycat.util.PortUtil;

public class GUIStart {
	static {

		GUIUtil.useLNF();
	}

	static int initTimeGap = 80;
	public static void showProgress(String msg) throws InterruptedException{
		InitDialog.instance.lInitText.setText(msg);
		Thread.sleep(initTimeGap);
	}
	public static void main(String[] args) throws Exception {
		
		//������ʼ������
		try {
			PortUtil.usePort(PortUtil.application_port);
		} catch (PortException e1) {
			JOptionPane.showMessageDialog(null, "�Ѿ�����");
			return;
		}

		InitDialog.instance.setVisible(true);
		showProgress("��ʼ��ʼ��...");
		
		showProgress("�ض�����־���");
		
		System.setOut(new LogPrintStream4out());
		System.setErr(new LogPrintStream4err());
		
		showProgress("ע���ȼ�");
		
		registerHotkey();


		
		showProgress("��ʼ�����ݿ�");
		List<Tomcat> ts =MapperUtil.tomcatMapper.list();
		
		showProgress("��ʼ����������");
		CatalinaUtil.init();
		
		for (Tomcat t : ts) {
			
			//��Ҫ����������tomcat,����Щ��ֵ�����
//			showProgress("�Զ�����tomcat���˿ں�:" +t.getPort());
//			BootstrapManager.startTomcat(t);
//			Thread.sleep(initTimeGap);
		}
		
		showProgress("��ʼ���ɹ�");
		
		Thread.sleep(500);
		
		MainFrame.instance.repaint();
		
		InitDialog.instance.setVisible(false);
		

		try {

			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					MainFrame.instance.setVisible(true);
					MainPanel.instance.workingPanel.show(TomcatPanel.instance);
					// MainPanel.instance.workingPanel.show(WebAppPanel.instance);
				}
			});
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void registerHotkey() {
		// TODO Auto-generated method stub
		HotKey k = new HotKey() {

			@Override
			public void onHotKey(int key) {
				// TODO Auto-generated method stub
				
				
//				ContextConfig.java
				WebApp w= WebAppPanel.instance.getSelectedWebApp();
				if(null==w){
					JOptionPane.showMessageDialog(null, "��ǰû��ѡ���κ�WebӦ��");
					return;					
				}
					
				
				
				
				if(!w.getStatus().equals(WebApp.loaded)){
					JOptionPane.showMessageDialog(null, "ֻ�������ɹ���WebӦ�ã�����ʹ�ø��ȼ�������������");
					return;
				}
					
				WebAppPanel.instance.bRestart.doClick();
				Tomcat t = w.getTomcat();
				String titleFormat = "�������ȣ� �˿�(%d)�µĵ�WebӦ�ã� %s";
				String title =String.format(titleFormat, t.getPort(),w.getName());
				ProgressDialog.instance.setTitle(title);
				ProgressDialog.instance.start();
			}
		};
	}
}