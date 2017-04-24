package com.how2java.lazycat.gui.model;

  
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.how2java.lazycat.mapper.WebAppMapper;
import com.how2java.lazycat.pojo.WebApp;
import com.how2java.lazycat.util.MapperUtil;
  
public class WebAppTableModel extends AbstractTableModel {
	
	WebAppMapper webAppMapper = MapperUtil.webAppMapper;
	
    String[] columnNames = new String[] { "id","����", "Ӳ��λ��", "������ַ","�Ƿ�������","�Ƿ��Զ�����","״̬"};
  
    // ʹ�ô�Service���ص�List��ΪTableModel������
  
    public List<WebApp> cs = webAppMapper.list();
  
     public int getRowCount() {
         
        return cs.size();
    }
  
    public int getColumnCount() {
         
        return columnNames.length;
    }
  
    public String getColumnName(int columnIndex) {
         
        return columnNames[columnIndex];
    }
  
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
  
    // ��ͨ��cs.get(rowIndex)��ȡ�ж�Ӧ��Category����
    // Ȼ�����columnIndex���ض�Ӧ������
    public Object getValueAt(int rowIndex, int columnIndex) {
        WebApp h = cs.get(rowIndex);
        if (0 == columnIndex)
            return h.getId();
        if (1 == columnIndex)
            return h.getName();
        if (2 == columnIndex)
        	return h.getPath();
        if (3 == columnIndex)
        	return h.getTestPage();
        if (4 == columnIndex)
        	return h.isStartup();
        if (5 == columnIndex)
        	return h.isDeploy();
        if (6 == columnIndex)
        	return h.getStatus();
        return null;
    }
  
}