package cn.wensiqun.asmsupport.sample.client.json.parser;

import cn.wensiqun.asmsupport.sample.client.json.JSONPool;
import cn.wensiqun.asmsupport.sample.client.json.utils.StringEncoder;

public class BaseParser extends AbstractParser {

    public BaseParser(JSONPool jsonPool) {
        super(jsonPool);
    }

    @Override
    public void parse(StringEncoder encoder, Object obj) {
        encoder.append(obj.toString());
    }

}
