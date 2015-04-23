package cn.wensiqun.asmsupport.sample.client.json.parser;

import cn.wensiqun.asmsupport.sample.client.json.JSONPool;
import cn.wensiqun.asmsupport.sample.client.json.utils.StringEncoder;

public class CharSequenceParser extends AbstractParser {

    public CharSequenceParser(JSONPool jsonPool) {
        super(jsonPool);
    }

    @Override
    public void parse(StringEncoder encoder, Object obj) {
        encoder.appendDirect('"').append(obj.toString()).appendDirect('"');
    }

}
