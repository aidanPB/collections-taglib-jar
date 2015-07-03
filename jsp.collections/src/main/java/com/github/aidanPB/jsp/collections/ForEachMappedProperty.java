package com.github.aidanPB.jsp.collections;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.reflect.TypeLiteral;
import org.apache.commons.lang3.reflect.TypeUtils;

public class ForEachMappedProperty extends ForEachMappedTagSupport<String> implements
		ForEachPropertyTag {

	/**
	 * UID for this version of this class.
	 */
	private static final long serialVersionUID = -8730776313323871635L;
	private Object bean;
	private String property;

	@Override
	public void setName(String name) {
		bean = pageContext.findAttribute(name);
	}

	@Override
	public void setProperty(String property) {
		this.property = property;
	}

	@Override
	protected void findKeySet() {
		try {
			Object trialMap = PropertyUtils.getSimpleProperty(bean, property);
			TypeLiteral<Map<String,?>> tlmsq = new TypeLiteral<Map<String,?>>() {};
			if(TypeUtils.isInstance(trialMap, tlmsq.getType())){
				Map<String,?> msq = uncheckedMapCast(trialMap);
				iterKeys = msq.keySet();
				return;
			}else{
				//FIXME log the fact that the keyset couldn't be retrieved.
				HashSet<String> hss = new HashSet<String>();
				Collections.addAll(hss, keys);
				iterKeys = hss;
				return;
			}
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			//FIXME log the exception
			HashSet<String> hss = new HashSet<String>();
			Collections.addAll(hss, keys);
			iterKeys = hss;
			return;
		}
	}

	@SuppressWarnings("unchecked")
	private static Map<String,?> uncheckedMapCast(Object obj){
		return (Map<String,?>) obj;
	}

	@Override
	protected void stepIteration() {
		String curKey = keyIter.next();
		pageContext.setAttribute(keyVar, curKey);
		try {
			pageContext.setAttribute(valueVar,
					PropertyUtils.getMappedProperty(bean, property, curKey));
			return;
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			//FIXME log the exception
			pageContext.setAttribute(valueVar, null);
		}
	}
}
