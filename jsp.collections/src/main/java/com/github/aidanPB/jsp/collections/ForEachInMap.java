package com.github.aidanPB.jsp.collections;

import java.util.Map;

public class ForEachInMap extends ForEachMappedTagSupport<Object> {

	/**
	 * UID for this version of this class.
	 */
	private static final long serialVersionUID = -4536487545057006336L;
	private Map<Object,?> collection;
	
	public void setCollection(Map<Object,?> collection){
		this.collection = collection;
	}

	@Override
	protected void findKeySet() {
		iterKeys = collection.keySet();
	}

	@Override
	protected void stepIteration() {
		Object curKey = keyIter.next();
		pageContext.setAttribute(keyVar, curKey);
		pageContext.setAttribute(valueVar, collection.get(curKey));
	}
}
