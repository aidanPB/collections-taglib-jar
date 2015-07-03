package com.github.aidanPB.jsp.collections;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.IterationTag;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.beanutils.PropertyUtils;

public class ForEachIndexedProperty extends ForEachIndexedTagSupport implements
		ForEachPropertyTag {

	/**
	 * UID for this version of this class.
	 */
	private static final long serialVersionUID = 4520081152707409653L;

	private enum IndexOn{
		ARRAY, COLLECTION, UNKNOWN
	}

	private String property;
	private Object bean;
	private Object[] propArray;
	private ArrayList<?> propList;
	private IndexOn type;
	private Object curVal;
	
	public ForEachIndexedProperty() {
		super();
		type = IndexOn.UNKNOWN;
	}

	@Override
	public void setName(String name) {
		bean = pageContext.findAttribute(name);
	}

	@Override
	public void setProperty(String property) {
		this.property = property;
	}

	@Override
	protected void stepIteration() {
		pageContext.setAttribute(indexVar, new Integer(index));
		switch(type){
		case ARRAY:
			curVal = propArray[index];
			break;
		case COLLECTION:
			curVal = propList.get(index);
			break;
		case UNKNOWN:
			try {
				curVal = PropertyUtils.getIndexedProperty(bean, property, index);
			} catch (Exception e) {
				curVal = null;
			}
			break;
		}
		pageContext.setAttribute(valueVar, curVal);
	}

	@Override
	protected void prepIteration() throws JspTagException {
		try {
			Class<?> ipropCls = PropertyUtils.getPropertyType(bean, property);
			if(ipropCls.isArray()){
				propArray = (Object[]) PropertyUtils.getProperty(bean, property);
				type = IndexOn.ARRAY;
				prepSizedIteration(propArray.length);
			}else if(Collection.class.isAssignableFrom(ipropCls)){
				Collection<?> propColl = (Collection<?>) PropertyUtils.getProperty(bean, property);
				propList = new ArrayList<Object>(propColl);
				type = IndexOn.COLLECTION;
				prepSizedIteration(propList.size());
			}else{
				if(toUnsetP){
					to = Integer.MAX_VALUE;
				}else{
					to = Math.abs(to);
				}
				from = Math.abs(from);
			}
		} catch (Exception e) {
			throw new JspTagException(e);
		}
		super.prepIteration();
	}
	
	private void prepSizedIteration(int ln) throws JspTagException {
		if(toUnsetP) to = ln - 1;
		if(from < 0) from += ln;
		if(to < 0) to += ln;
	}

	@Override
	public int doAfterBody() throws JspException {
		switch(type){
		case ARRAY:
		case COLLECTION:
			return super.doAfterBody();
		default:
			if(desc){
				index -= step;
				if(index < to) return Tag.SKIP_BODY;
			}else{
				index += step;
				if(index > to) return Tag.SKIP_BODY;
			}
			stepIteration();
			if(curVal == null) return Tag.SKIP_BODY;
			return IterationTag.EVAL_BODY_AGAIN;
		}
	}

	@Override
	public int doStartTag() throws JspException {
		super.doStartTag();
		if(curVal == null) return Tag.SKIP_BODY;
		return Tag.EVAL_BODY_INCLUDE;
	}
}
