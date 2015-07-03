package com.github.aidanPB.jsp.collections;

import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.IterationTag;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.functor.UnaryPredicate;
import org.apache.commons.functor.core.collection.FilteredIterable;
import org.apache.commons.functor.core.collection.IsElementOf;

public abstract class ForEachMappedTagSupport<K> extends TagSupport implements
		ForEachMappedTag<K> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private Object originalValueVarVal, originalKeyVarVal;
	protected String valueVar, keyVar;
	protected K[] keys;
	protected Iterable<K> iterKeys;
	protected Iterator<K> keyIter;
	protected UnaryPredicate<K> keyFilter;
	

	public void setKeys(K[] keys){
		this.keys = keys;
	}

	public void setKeyFilter(UnaryPredicate<K> keyFilter){
		this.keyFilter = keyFilter;
	}

	@Override
	public void setValueVar(String valueVar) {
		this.valueVar = valueVar;
	}

	@Override
	public void setKeyVar(String keyVar) {
		this.keyVar = keyVar;
	}

	@Override
	public int doEndTag() throws JspException {
		pageContext.setAttribute(keyVar, originalKeyVarVal);
		pageContext.setAttribute(valueVar, originalValueVarVal);
		return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {
		if(valueVar == null){
			if(id == null){
				valueVar = "cValue";
			}else{
				valueVar = id + "Value";
			}
		}
		if(keyVar == null){
			if(id == null){
				keyVar = "cKey";
			}else{
				keyVar = id + "Key";
			}
		}
		originalKeyVarVal = pageContext.getAttribute(keyVar);
		originalValueVarVal = pageContext.getAttribute(valueVar);
		findKeySet();
		FilteredIterable<K> fik = FilteredIterable.of(iterKeys);
		if(keys != null && keys.length > 0) fik.retain(IsElementOf.<K>instance(keys));
		if(keyFilter != null) fik.retain(keyFilter);
		keyIter = fik.iterator();
		if(keyIter.hasNext()){
			stepIteration();
			return Tag.EVAL_BODY_INCLUDE;
		}
		return Tag.SKIP_BODY;
	}

	@Override
	public int doAfterBody() throws JspException {
		if(keyIter.hasNext()){
			stepIteration();
			return IterationTag.EVAL_BODY_AGAIN;
		}
		return Tag.SKIP_BODY;
	}

	abstract protected void findKeySet();

	abstract protected void stepIteration();
}
