package com.mwg.api.commons;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ArraySorter<T, V> implements Comparator<T>  {

	private final IConverter<T, Comparable<V>> converter;

	  private final Comparator<T> comparator;

	  private boolean ascending = true;

	  private final List<T> data;

	  private Exception err = null;

	  private ArraySorter(List<T> data, IConverter<T, Comparable<V>> converter) {
	    this.converter = converter;
	    this.comparator = null;
	    this.data = data;
	  }

	  private ArraySorter(List<T> data, Comparator<T> comparator) {
	    this.comparator = comparator;
	    this.converter = null;
	    this.data = data;
	  }

	  public ArraySorter<T, V> asc() {
	    this.ascending = true;
	    return this;
	  }

	  public ArraySorter<T, V> desc() {
	    this.ascending = false;
	    return this;
	  }

	  @Override
	  @SuppressWarnings("unchecked")
	  public int compare(T objA, T objB) {
	    if (err == null) {
	      try {
	        if (comparator != null) {
	          if (objA != null && objB != null) {
	            if (this.ascending) {
	              return comparator.compare(objA, objB);
	            } else {
	              return comparator.compare(objB, objA);
	            }
	          } else {
	            return compareOnNullObject(objA, objB);
	          }
	        }
	        if (converter != null) {
	          Comparable<V> a = converter.convert(objA);
	          Comparable<V> b = converter.convert(objB);
	          if (a != null && b != null) {
	            if (this.ascending) {
	              return a.compareTo((V) b);
	            } else {
	              return b.compareTo((V) a);
	            }
	          } else {
	            return compareOnNullObject(a, b);
	          }
	        }
	      } catch (Exception ex) {
	        err = ex;
	      }
	    }
	    return 0;
	  }

	  private int compareOnNullObject(Object a, Object b) throws Exception {
	    if (a == null && b == null) {
	      return 0;
	    }
	    if (a == null) {
	      return this.ascending ? -1 : 1;
	    }
	    if (b == null) {
	      return this.ascending ? 1 : -1;
	    }
	    throw new RuntimeException("Never occur !");
	  }

	  public void sort() throws Exception {
	    if (this.data != null) {
	      Collections.sort(data, this);
	      if (err != null) {
	        throw err;
	      }
	    }
	  }

	  public static <T, V> ArraySorter<T, V> data(List<T> data, IConverter<T, Comparable<V>> converter) {
	    return new ArraySorter<>(data, converter);
	  }

	  public static <T, V> ArraySorter<T, V> data(List<T> data, Comparator<T> comparator) {
	    return new ArraySorter<>(data, comparator);
	  }

	  public static void sortRandom(List<?> objLst) {
	    Collections.sort(objLst, new Comparator<Object>() {
	      @Override
	      public int compare(Object a, Object b) {
	        return (int) (Math.round(Math.random()) - 1);
	      }
	    });
	  }

	  public static void sortRandom(Object[] objArr) {
	    Arrays.sort(objArr, new Comparator<Object>() {
	      @Override
	      public int compare(Object a, Object b) {
	        return (int) (Math.round(Math.random()) - 1);
	      }
	    });
	  }
	
}
