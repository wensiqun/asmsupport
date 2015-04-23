package cn.wensiqun.asmsupport.sample.client.json.parser;

import java.util.Iterator;

import cn.wensiqun.asmsupport.sample.client.json.JSONPool;
import cn.wensiqun.asmsupport.sample.client.json.utils.StringEncoder;

public class IterableParser extends AbstractParser {

    public IterableParser(JSONPool jsonPool) {
        super(jsonPool);
    }

    @Override
    public void parse(StringEncoder encoder, Object obj) {
        encoder.appendDirect('[');
        Iterator<?> it = ((Iterable<?>)obj).iterator();
        while(it.hasNext()) {
            jsonPool.getJson(encoder, it.next());
            encoder.appendDirect(',');
        }
        encoder.trimLastComma();
        encoder.appendDirect(']');
    }

}
