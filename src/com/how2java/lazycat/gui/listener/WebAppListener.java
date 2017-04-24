package com.how2java.lazycat.gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import org.apache.catalina.startup.Bootstrap;

import com.how2java.lazycat.gui.dialog.WebAppAddDialog;
import com.how2java.lazycat.gui.dialog.WebAppEditDialog;
import com.how2java.lazycat.gui.panel.WebAppPanel;
import com.how2java.lazycat.pojo.WebApp;
import com.how2java.lazycat.util.BootstrapManager;
import com.how2java.lazycat.util.MapperUtil;

public class WebAppListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
	       WebAppPanel p = WebAppPanel.instance;
	       
	        JButton b = (JButton) e.getSource();
	        
	        if(b==p.bAdd){
	        	
	        	WebAppAddDialog.instance.setVisible(true);

	        }
	        if(b==p.bEdit){
	        	
	        	WebAppEditDialog.instance.setVisible(true,p.getSelectedWebApp());
	        	
	        }
	        
	        if(b==p.bDelete){
	        	WebApp t = p.getSelectedWebApp();
	            if (JOptionPane.OK_OPTION != JOptionPane.showConfirmDialog(p, "ȷ��Ҫɾ����"))
	                return;
	            t.getXMLFile().delete();
	            t.stopConext();
//	            BootstrapManager.getBoostrap(t.getTomcat().getPort()).undeployContexts();
				
	        	MapperUtil.webAppMapper.delete(t.getId());
	        }
	        
	        if(b==p.bStop){
	        	WebApp w = p.getSelectedWebApp();
//	            if (JOptionPane.OK_OPTION != JOptionPane.showConfirmDialog(p, "ȷ��Ҫж�أ�"))
//	                return;	        	
	            
	        	w.stopConext();
//	        	BootstrapManager.getBoostrap(w.getTomcat().getPort()).undeployContexts();
				w.getXMLFile().delete();
	        	
	        }
	        
	        
	        if(b==p.bStart){
	        
	        	
	        	WebApp w = p.getSelectedWebApp();
	        	if(w.getTomcat().getStatus().equals(Bootstrap.status_stopped)){
	        		
	        		if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(p, "��Ӧ��Tomcat��δ������Ҫ����Tomcat��")){
	        			
	        			SwingWorker worker = new SwingWorker() {
	        				protected Object doInBackground() throws Exception {
	        					//����ʱ����
	    	        			w.createXML();
	    	        			WebAppPanel.instance.updateDataWithoutDB();
	    	        			BootstrapManager.startTomcat(w.getTomcat());
	        					return null;
	        				}
	        			};
	        			worker.execute();
	        			

	        		}
	        		else{
	        			return;
	        		}
	        			
	        		
	        	}
	        	else{
	        		w.stopConext();
	        		w.getXMLFile().delete();
		        	w.createXML();
		        	w.stopConext();
		        	w.startContext();
	        	}
	        	WebAppPanel.instance.updateDataWithoutDB();
	        }
	        
	        if(b==p.bRestart){
	        	WebApp w = p.getSelectedWebApp();
	        	w.stopConext();
	    		int port = w.getTomcat().getPort();
	    		Bootstrap b2 = BootstrapManager.getBoostrap(port);
	    		System.out.println(b2.listContexts().size());
	    		
				w.getXMLFile().delete();
				WebAppPanel.instance.updateDataWithoutDB();
				w.createXML();
				w.startContext();
	        }
        	p.updateData();
	}

}
