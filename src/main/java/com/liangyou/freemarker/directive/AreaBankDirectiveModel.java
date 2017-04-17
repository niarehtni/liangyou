package com.liangyou.freemarker.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.liangyou.dao.AreaBankDao;
import com.liangyou.domain.AreaBank;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 *各种下拉框的html输出 
 * @author fuxingxing
 *usage: <@area name="use" id="use" class="test" value=1 pid=20></@area>
 *@param name 表单名字,String类型,不能为空
 *@param id   表单的id,String类型，可以为空
 *@param clazz  表单的class,String类型，可以为空
 *@param pid  选择地区的父编码,Number类型
 *@param value 表单的默认值，String类型。
 *@return  返回拼装出来的html字符串
 */
public class AreaBankDirectiveModel implements TemplateDirectiveModel {
	
	private static Logger logger = Logger.getLogger(AreaBankDirectiveModel.class);
	
    private static final String NAME = "name";
    private static final String ID = "id";
    private static final String CLASS="class";
    private static final String PID="pid";
    private static final String VALUE="value";
	private AreaBankDao areaBankDao;

	
	public AreaBankDirectiveModel(AreaBankDao areaBankDao) {
		super();
		this.areaBankDao = areaBankDao;
	}

	public AreaBankDirectiveModel() {
		super();
	}

	@Override
	public void execute(Environment env, Map map, TemplateModel[] loopVars, 
			TemplateDirectiveBody body) throws TemplateException, IOException {
		Iterator it = map.entrySet().iterator();
		String name="",id="",clazz="",value="",pid="";
        while (it.hasNext()) {
            Map.Entry entry = (Entry) it.next();
            String paramName = entry.getKey().toString();
            TemplateModel paramValue = (TemplateModel) entry.getValue();
            logger.debug("name:"+paramName);
            logger.debug("r:"+paramValue);
            if (paramName.equals(NAME)) {
            	name=paramValue.toString();
            }else if (paramName.equals(ID)) {
                id=paramValue.toString();
            }else if (paramName.equals(CLASS)) {
            	clazz=paramValue.toString();
            }else if (paramName.equals(PID)) {
            	pid = paramValue.toString();
            }else if (paramName.equals(VALUE)) {
            	value = paramValue.toString();
            }
        }
        String result=html(name,id,clazz,pid,value);
        Writer out = env.getOut();
        out.write(result);
	}
	
	/**
	 * 
	 * @param name 表单名字,不能为空
	 * @param id   表单的id，可以为空
	 * @param clazz  表单的class，可以为空
	 * @param typeid  下拉框的类型，比如借款用途为19，对应数据库中的type_id
	 * @return  返回拼装出来的html字符串
	 */
	private String html(String name,String id,String clazz,String pid,String value){
		List<AreaBank> list=new ArrayList<AreaBank>();
		if(pid.equals("")){
			pid="-1";
			AreaBank a=new AreaBank();
			a.setId(-1);
			a.setName("请选择");
			list.add(a);
		}else{
			list=areaBankDao.getListByPid(pid);
		}
		
		StringBuffer sb=new StringBuffer();
		sb.append("<select name=\"").append(name);
		if(!id.equals("")){
			sb.append("\" id=\"").append(id);
		}
		if(!clazz.equals("")){
			sb.append("\" class=\"").append(clazz);
		}
		sb.append("\">");
		//
		sb.append("<option value=\"0\">请选择</option>");
		for(AreaBank l:list){
			sb.append("<option value=\"").append(l.getId()).append("\"");
			if(value.equals(l.getId()+"")){
				sb.append(" selected=\"selected\" ");
			}
			sb.append(">")
			.append(l.getName())
			.append("</option>");
		}
		sb.append("</select>");
		return sb.toString();
	}
	
}
