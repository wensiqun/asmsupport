package cn.wensiqun.asmsupport.core.utils.collections;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public abstract class MapLooper<K, V> {

	private Map<K, V> map;

	public MapLooper(Map<K, V> map) {
		super();
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
