package json.parser;

import json.JSONPool;
import json.utils.StringEncoder;

public class ArrayParser extends AbstractParser {

    public ArrayParser(JSONPool jsonPool) {
        super(jsonPool);
    }

    @Override
    public void parse(StringEncoder encoder, Object obj) {
        encoder.appendDirect('[');
        Object[] array = (Object[]) obj;
        for(Object ele : array){
            jsonPool.getJson(encoder, ele);
            encoder.appendDirect(',');
        }
        encoder.trimLastComma();
        encoder.appendDirect(']');
    }

}
