package com.how2java.lazycat.gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.PortUnreachableException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import com.how2java.lazycat.gui.panel.TomcatPanel;
import com.how2java.lazycat.mapper.TomcatMapper;
import com.how2java.lazycat.pojo.Tomcat;
import com.how2java.lazycat.util.BootstrapManager;
import com.how2java.lazycat.util.MapperUtil;
import com.how2java.lazycat.util.PortUtil;

public class TomcatListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
	       TomcatPanel p = TomcatPanel.instance;
	       
	        JButton b = (JButton) e.getSource();
	        if(!b.isEnabled())
	        	return;
	        
	        if(b==p.bAdd){
	        	 String strPort= JOptionPane.showInputDialog("��������Ҫ�½���Tomcat�Ķ˿ں�");
	        	 if(null==strPort)
	        		 return;
	        	 int port = 0;
	        	 try {
					port=Integer.parseInt(strPort.trim());
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(p, "�˿ں�ֻ�������֣��������7070,8080,9090");
					return;
				}
	        	 
	        	 if(port>60000){
	        		 JOptionPane.showMessageDialog(p, "�˿ںű���С��60000");
						return;
					
	        	 }
	        	 
	        	Tomcat t= MapperUtil.tomcatMapper.getByPort(port);
	        	if(t!=null){
					JOptionPane.showMessageDialog(p, "�ö˿ںŶ�Ӧ��Tomcat�Ѿ�����");
					return;
	        	}
	        	
	        	
	        	Tomcat tomcat = new Tomcat();
	        	tomcat.setPort(port);
	        	
	        	MapperUtil.tomcatMapper.add(tomcat);
	        	p.updateData();
	        }
	        
	        if(b==p.bDelete){
	        	Tomcat t = p.getSelectedTomcat();
	            if (JOptionPane.OK_OPTION != JOptionPane.showConfirmDialog(p, "ȷ��Ҫɾ����"))
	                return;	        	
	        	
	        	MapperUtil.tomcatMapper.delete(t.getId());
	        	p.updateData();
	        }
	        
	        
	        
	        if(b==p.bEdit){
	        	
	        	 String strPort= JOptionPane.showInputDialog("�������µĶ˿ں�");
	        	 if(null==strPort)
	        		 return;
	        	 int port = 0;
	        	
	        	 try {
					port=Integer.parseInt(strPort.trim());
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(p, "�˿ں�ֻ�������֣��������7070,8080,9090");
					return;
				}
	        	 if(port>60000){
	        		 JOptionPane.showMessageDialog(p, "�˿ںű���С��60000");
						return;
					
	        	 }
	        	 Tomcat tomcat = p.getSelectedTomcat();
	        	 
	        	
	        	if(tomcat.getPort()==port){
						JOptionPane.showMessageDialog(p, "�˿ں��ޱ仯");
						return;
		        	}
	        	
	        	Tomcat t= MapperUtil.tomcatMapper.getByPort(port);
	        	if(t!=null){
					JOptionPane.showMessageDialog(p, "�ö˿ںŶ�Ӧ��Tomcat�Ѿ�����");
					return;
	        	}
	        	
	        	
	        	tomcat.setPort(port);
	        	
	        	MapperUtil.tomcatMapper.update(tomcat);
	        	p.updateData();
	        	
	        }
	        
	        if(b==p.bStart){
	        	p.freeze();
	        	SwingWorker worker = new SwingWorker() {
	        		protected Object doInBackground() throws Exception {
	        			//����ʱ����
	        			BootstrapManager.startTomcat(p.getSelectedTomcat());
	        			  
	        			p.unfreeze();
	        			p.syncStatus();
	        			return null;
	        		}
	        	};
	        	worker.execute();

	        	
	        }
	        
	        if(b==p.bStop){
	        	p.freeze();
	        	SwingWorker worker = new SwingWorker() {
	        		protected Object doInBackground() throws Exception {
	        			//����ʱ����
	        			BootstrapManager.stopTomcat(p.getSelectedTomcat());
	        			  
	        			p.unfreeze();
	        			p.syncStatus();
	        			return null;
	        		}
	        	};
	        	worker.execute();
	        }
	        
	        
	        
        	
	        
	}

}
