package com.liangyou.freemarker.method;

import java.util.List;

import com.liangyou.util.NumberUtils;
import com.liangyou.util.StringUtils;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 
 * @author lk
 *
 */
public class ParserLongMethodModel implements TemplateMethodModel {

	@SuppressWarnings("rawtypes")
	public Object exec(List args) throws TemplateModelException {
		if(args==null||args.size()<1) return 0;
		Object str=args.get(0);
		String tmp =StringUtils.isNull(str).replaceAll(",", "");
		
		long d=0;
		d=NumberUtils.getLong(tmp);
		return d;
		}
	}
