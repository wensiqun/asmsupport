package cn.wensiqun.asmsupport.utils.collections;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.MapUtils;


public abstract class MapLooper<K, V> {

	private Map<K, V> map;

	public MapLooper(Map<K, V> map) {
		super();
		this.map = map;
	}
	
	public final void loop(){
		if(MapUtils.isNotEmpty(map)){
			Set<Entry<K, V>> set = map.entrySet();
			for(Entry<K, V> entry : set){
				process(entry.getKey(), entry.getValue());
			}
		}
	}
	
	protected abstract void process(K key, V value);
	
}
