package com.github.aidanPB.jsp.collections;

public interface ForEachIndexedTag extends ForEachJCFTag {

	public void setFrom(Integer from);
	public void setTo(Integer to);
	public void setStep(Integer step);
	
	public void setIndexVar(String indexVar);
}
