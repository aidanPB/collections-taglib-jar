package com.github.aidanPB.jsp.collections;

import org.apache.commons.functor.UnaryPredicate;

public interface ForEachMappedTag<K> extends ForEachJCFTag {

	public void setKeyVar(String keyVar);
	public void setKeys(K[] keys);
	public void setKeyFilter(UnaryPredicate<K> keyFilter);
}
