/**    
 *  Asmsupport is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cn.wensiqun.asmsupport.core.utils.common;

import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

import java.util.*;

/**
 * The exception container. 
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public class ThrowExceptionContainer implements Set<IClass> {

	private List<IClass> exceptionEntryList;

	public ThrowExceptionContainer() {
		super();
		exceptionEntryList = new ArrayList<>();
	}

	@Override
	public int size() {
		return exceptionEntryList.size();
	}

	@Override
	public boolean isEmpty() {
		return exceptionEntryList.isEmpty();
	}

	/**
	 * if the parameter is sub type of any exception type in this class.
	 */
	@Override
	public boolean contains(Object o) {
		assert o != null;
		if (!(o instanceof IClass)) {
			return false;
		}

		for (IClass exception : exceptionEntryList) {
			if (((IClass) o).isChildOrEqual(exception)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Iterator<IClass> iterator() {
		return exceptionEntryList.iterator();
	}

	@Override
	public Object[] toArray() {
		return exceptionEntryList.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return exceptionEntryList.toArray(a);
	}

	/**
	 * Add an exception type, make sure the exception type is not sub type(or equal) of any element in this container 
	 * 
	 * @see java.util.Set#add(java.lang.Object)
	 */
	@Override
	public boolean add(IClass e) {
		assert e != null;

		if (e.isChildOrEqual(e.getClassHolder(	).getType(RuntimeException.class))) {
			return false;
		}

		boolean hasExist = false;
		for (IClass thrExc : this.exceptionEntryList) {
			if (e.isChildOrEqual(thrExc)) {
				hasExist = true;
				break;
			}
		}

		if (!hasExist) {
			for (int i = 0; i < exceptionEntryList.size();) {
				IClass thrExc = exceptionEntryList.get(i);
				if (thrExc.isChildOrEqual(e)) {
					exceptionEntryList.remove(i);
				} else {
					i++;
				}
			}
			this.exceptionEntryList.add(e);
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean remove(Object o) {
		assert o != null;
		if (!(o instanceof IClass)) {
			return false;
		}
		for (int i = 0; i < size(); i++) {
			IClass thrExc = exceptionEntryList.get(i);
			if (thrExc.equals(o)) {
				exceptionEntryList.remove(i);
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
	public boolean addAll(Collection<? extends IClass> c) {
		for (IClass aclass : c) {
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
		exceptionEntryList.clear();
	}

}
