package com.how2java.lazycat.gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import com.how2java.lazycat.gui.dialog.WebAppAddDialog;
import com.how2java.lazycat.gui.dialog.WebAppEditDialog;
import com.how2java.lazycat.gui.frame.MainFrame;
import com.how2java.lazycat.gui.panel.TomcatPanel;
import com.how2java.lazycat.gui.panel.WebAppPanel;
import com.how2java.lazycat.gui.util.GUIUtil;
import com.how2java.lazycat.pojo.WebApp;
import com.how2java.lazycat.util.MapperUtil;

public class WebAppEditListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
	       WebAppEditDialog d = WebAppEditDialog.instance;
	    
	       
	       if(!GUIUtil.checkAlphaNumeric(d.tfName, "����"))
	    	   return;
	       if(!GUIUtil.checkEmpty(d.tfPath, "Ӳ��·��"))
	    	   return;
	    	   
	       String name = d.tfName.getText();
	       String path = d.tfPath.getText();
	       String testPage = d.tfTestPage.getText();
	       boolean startup = d.cbStartup.isSelected();
	       boolean deploy = d.cbDeploy.isSelected();
	       
	       
	       WebApp w= WebAppPanel.instance.getSelectedWebApp();
	       String oldName =w.getName();
	       w.setName(name);
	       w.setPath(path);
	       w.setTestPage(testPage);
	       w.setStartup(startup);
	       w.setDeploy(deploy);
	       Map m = new HashMap();
	       
	       m.put("name", name);
	       m.put("tomcat", TomcatPanel.instance.getSelectedTomcat());
	       
	       if("ROOT".equalsIgnoreCase(w.getName().trim())){
	    	   JOptionPane.showMessageDialog(MainFrame.instance, "����ʹ��ROOT��Ϊ����");
	    	   return;
	       }

	       WebApp wa = MapperUtil.webAppMapper.getByTomcatAndName(m);
	       if(!name.equals(oldName) && null!=wa){
	    	   JOptionPane.showMessageDialog(MainFrame.instance, "�����Ѿ���ʹ��");
	    	   WebAppAddDialog.instance.tfName.grabFocus();
	    	   return;
	       }
	       else{
	    	   List<WebApp> ws= MapperUtil.webAppMapper.listByTomcat(TomcatPanel.instance.getSelectedTomcat());
	    	   if(w.isStartup()){
	    		   for (WebApp bean : ws) {
	    			   if(bean.getId()==w.getId())
	    				   continue;
						bean.setStartup(false);
						MapperUtil.webAppMapper.update(bean);
	    		   }
	    	   }
	    	   
	    	   
		       MapperUtil.webAppMapper.update(w);
		       WebAppPanel.instance.updateData();
	       }
	       

	       
	       
	       
	       d.setVisible(false);
	        
	}

}
