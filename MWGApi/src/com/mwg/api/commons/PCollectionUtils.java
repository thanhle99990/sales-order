package com.mwg.api.commons;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;


public class PCollectionUtils {

	public static <T> Collection<T> select(T[] collection, IPredicate<T> predicate){
	    return select(Arrays.asList(collection), predicate);
	}
	public static <T,V> Collection<V> transform(T[] collection, ITransformer<T,V> trans){
		return transform(Arrays.asList(collection), trans);
	}
	@SuppressWarnings("unchecked")
	public static <T> Collection<T> select(Collection<T> collection, IPredicate<T> predicate){
		return CollectionUtils.select(collection, new Predicate() {
			@Override
			public boolean evaluate(Object obj) {
				return predicate.evaluate((T)obj);
			}
		});
	}
	@SuppressWarnings("unchecked")
	public static <T,V> Collection<V> transform(Collection<T> collection, ITransformer<T,V> trans){
		return CollectionUtils.collect(collection, new Transformer() {
			@Override
			public Object transform(Object obj) {
				return trans.trans((T)obj);
			}
		});
	}
	public static <T,V> Collection<V> selectAndTransform(Collection<T> collection,IPredicate<T> predicate, ITransformer<T,V> trans){
		return transform(select(collection, predicate),trans);
	}
	public static <T,V> Collection<V> transAndSelect(Collection<T> collection,ITransformer<T,V> trans,IPredicate<V> predicate){
		return select(transform(collection, trans),predicate);
	}
	public static <T,V> Collection<V> selectAndTransform(T[] collection,IPredicate<T> predicate, ITransformer<T,V> trans){
		return selectAndTransform(Arrays.asList(collection), predicate, trans);
	}
	public static <T,V> Collection<V> transAndSelect(T[] collection,ITransformer<T,V> trans,IPredicate<V> predicate){
		return transAndSelect(Arrays.asList(collection), trans, predicate);
	}
	
}
