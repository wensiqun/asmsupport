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
package cn.wensiqun.asmsupport.core.utils.collections;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public abstract class MapLooper<K, V> {

	private Map<K, V> map;

	public MapLooper(Map<K, V> map) {
		this.map = map;
	}
	
	public final void loop(){
		if(map != null && map.size() > 0){
			Set<Entry<K, V>> set = map.entrySet();
			for(Entry<K, V> entry : set){
				process(entry.getKey(), entry.getValue());
			}
		}
	}
	
	protected abstract void process(K key, V value);
	
}
