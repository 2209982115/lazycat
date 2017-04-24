package com.how2java.lazycat.gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import com.how2java.lazycat.gui.dialog.WebAppAddDialog;
import com.how2java.lazycat.gui.frame.MainFrame;
import com.how2java.lazycat.gui.panel.TomcatPanel;
import com.how2java.lazycat.gui.panel.WebAppPanel;
import com.how2java.lazycat.gui.util.GUIUtil;
import com.how2java.lazycat.pojo.WebApp;
import com.how2java.lazycat.util.MapperUtil;

public class WebAppAddListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
	       WebAppAddDialog d = WebAppAddDialog.instance;
	    
	       
	       if(!GUIUtil.checkAlphaNumeric(d.tfName, "����"))
	    	   return;
	       if(!GUIUtil.checkEmpty(d.tfPath, "Ӳ��·��"))
	    	   return;
	    	   
	       String name = d.tfName.getText();
	       String path = d.tfPath.getText();
	       String testPage = d.tfTestPage.getText();
	       boolean startup = d.cbStartup.isSelected();
	       boolean deploy = d.cbDeploy.isSelected();
	       
	       
	       WebApp w= new WebApp();
	       w.setTomcat(TomcatPanel.instance.getSelectedTomcat());
	       w.setName(name);
	       w.setPath(path);
	       w.setTestPage(testPage);
	       w.setStartup(startup);
	       w.setDeploy(deploy);
	       
	       Map m = new HashMap();
	       m.put("name", name);
	       m.put("tomcat", TomcatPanel.instance.getSelectedTomcat());
	       WebApp wa = MapperUtil.webAppMapper.getByTomcatAndName(m);
	       if("ROOT".equalsIgnoreCase(w.getName().trim())){
	    	   JOptionPane.showMessageDialog(MainFrame.instance, "����ʹ��ROOT��Ϊ����");
	    	   return;
	       }
	       
	       if(null!=wa){
	    	   
	    	   JOptionPane.showMessageDialog(MainFrame.instance, "�����Ѿ���ʹ��");
	    	   WebAppAddDialog.instance.tfName.grabFocus();
	    	   return;
	       }
	       else{
	    	   List<WebApp> ws= MapperUtil.webAppMapper.listByTomcat(TomcatPanel.instance.getSelectedTomcat());
	    	   if(w.isStartup()){
	    		   for (WebApp bean : ws) {
						bean.setStartup(false);
						MapperUtil.webAppMapper.update(bean);
	    		   }
	    	   }
		       
		       MapperUtil.webAppMapper.add(w);
		       w.createXML();
		       
	       }
	       

	       
	       
	       d.setVisible(false);
	        
	}

}
