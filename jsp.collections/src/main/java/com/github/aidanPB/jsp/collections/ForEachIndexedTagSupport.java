package com.github.aidanPB.jsp.collections;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.IterationTag;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public abstract class ForEachIndexedTagSupport extends TagSupport
			implements ForEachIndexedTag {

	private static final long serialVersionUID = 2L;
	private Object originalIndexAtVal, originalValueAtVal;
	protected String indexVar, valueVar;
	protected Integer from, to, step, index;
	protected boolean toUnsetP, desc;

	public ForEachIndexedTagSupport() {
		super();
		toUnsetP = true;
		from = 0;
		step = 1;
	}

	@Override
	public void setIndexVar(String indexVar){
		this.indexVar = indexVar;
	}

	@Override
	public void setValueVar(String valueVar){
		this.valueVar = valueVar;
	}

	@Override
	public void setFrom(Integer from) {
		this.from = from;
	}

	@Override
	public void setTo(Integer to) {
		this.to = to;
		toUnsetP = false;
	}

	@Override
	public void setStep(Integer step) {
		this.step = Math.abs(step);
		if(this.step < 1) this.step = (Integer) 1;
	}

	@Override
	public int doAfterBody() throws JspException {
		if(desc){
			index -= step;
			if(index < to) return Tag.SKIP_BODY;
		}else{
			index += step;
			if(index > to) return Tag.SKIP_BODY;
		}
		stepIteration();
		return IterationTag.EVAL_BODY_AGAIN;
	}

	@Override
	public int doStartTag() throws JspException {
		super.doStartTag();
		prepIteration();
		return Tag.EVAL_BODY_INCLUDE;
	}
	
	/**
	 * Prepare for the first iteration. Important note: subclasses that override
	 * {@code prepIteration()} are expected to call {@code super.prepIteration()}
	 * as their <em>last</em> statement, not their first.
	 * @throws JspTagException if any exception is encountered.
	 */
	protected void prepIteration() throws JspTagException{
		desc = (from > to);
		index = from;
		if(indexVar == null){
			if(id == null){
				indexVar = "cIndex";
			}else{
				indexVar = id + "Index";
			}
		}
		if(valueVar == null){
			if(id == null){
				valueVar = "cValue";
			}else{
				valueVar = id + "Value";
			}
		}
		originalIndexAtVal = pageContext.getAttribute(indexVar);
		originalValueAtVal = pageContext.getAttribute(valueVar);
		stepIteration();
	}
	
	/**
	 * Set scripting variables for the current iteration.
	 */
	abstract protected void stepIteration();

	@Override
	public int doEndTag() throws JspException {
		pageContext.setAttribute(indexVar, originalIndexAtVal);
		pageContext.setAttribute(valueVar, originalValueAtVal);
		return super.doEndTag();
	}
}
