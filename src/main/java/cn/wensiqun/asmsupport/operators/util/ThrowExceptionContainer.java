package cn.wensiqun.asmsupport.operators.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.AClassFactory;

/**
 * 异常集合
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class ThrowExceptionContainer implements Set<AClass> {

	/**异常List */
	private List<AClass> allThrowExceptions;

	public ThrowExceptionContainer() {
		super();
		allThrowExceptions = new ArrayList<AClass>();
	}

	@Override
	public int size() {
		return allThrowExceptions.size();
	}

	@Override
	public boolean isEmpty() {
		return allThrowExceptions.isEmpty();
	}

	/**
	 * 如果当前集合包含当前给定的异常或者包含当前给定异常的父类异常则
	 * 返回true，否则返回false
	 */
	@Override
	public boolean contains(Object o) {
		assert o != null;
		//当前集合只能包含AClass
		if (!(o instanceof AClass)) {
			return false;
		}

		for (AClass exception : allThrowExceptions) {
			if (((AClass) o).isChildOrEqual(exception)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Iterator<AClass> iterator() {
		return allThrowExceptions.iterator();
	}

	@Override
	public Object[] toArray() {
		return allThrowExceptions.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return allThrowExceptions.toArray(a);
	}

	/*
	 * 添加异常。 如果新增的异常已经存在，或者其父类已经存在则不添加
	 * @see java.util.Set#add(java.lang.Object)
	 */
	@Override
	public boolean add(AClass e) {
		assert e != null;

		if (e.isChildOrEqual(AClassFactory
				.getProductClass(RuntimeException.class))) {
			return false;
		}

		boolean hasExist = false;
		for (AClass thrExc : this.allThrowExceptions) {
			if (e.isChildOrEqual(thrExc)) {
				hasExist = true;
				break;
			}
		}

		if (!hasExist) {
			for (int i = 0; i < allThrowExceptions.size();) {
				AClass thrExc = allThrowExceptions.get(i);
				if (thrExc.isChildOrEqual(e)) {
					allThrowExceptions.remove(i);
				} else {
					i++;
				}
			}
			this.allThrowExceptions.add(e);
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean remove(Object o) {
		assert o != null;
		if (!(o instanceof AClass)) {
			return false;
		}
		for (int i = 0; i < size(); i++) {
			AClass thrExc = allThrowExceptions.get(i);
			if (thrExc.equals(o)) {
				allThrowExceptions.remove(i);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object aclass : c) {
			if (!contains(aclass)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends AClass> c) {
		for (AClass aclass : c) {
			boolean result = this.add(aclass);
			if (!result) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		for (Object aclass : c) {
			boolean result = this.remove(aclass);
			if (!result) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean result = false;
		for(int i=0; i<size();){
		    for(Object o : c){
		    	if(!contains(o)){
		    		remove(o);
		    		result = true;
		    	}else{
		    		i++;
		    	}
		    }	
		}
		return result;
	}

	@Override
	public void clear() {
		allThrowExceptions.clear();
	}

}
