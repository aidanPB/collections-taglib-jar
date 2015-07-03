package com.github.aidanPB.jsp.collections;

import java.util.List;

import javax.servlet.jsp.JspTagException;

public class ForEachInList extends ForEachIndexedTagSupport {

	/**
	 * UID for this version of this class.
	 */
	private static final long serialVersionUID = 199981386474548300L;
	private List<?> list;

	public void setCollection(List<?> list){
		this.list = list;
	}

	@Override
	protected void prepIteration() throws JspTagException {
		int ls = list.size();
		if(toUnsetP) to = ls - 1;
		if(from < 0) from += ls;
		if(to < 0) to += ls;
		super.prepIteration();
	}

	@Override
	protected void stepIteration() {
		pageContext.setAttribute(indexVar, new Integer(index));
		pageContext.setAttribute(valueVar, list.get(index));
	}
}
