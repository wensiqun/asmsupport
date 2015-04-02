package json.parser;

import json.StringEncoder;
import json.generator.JSONPool;

public class BaseParser extends AbstractParser {

    public BaseParser(JSONPool jsonPool) {
        super(jsonPool);
    }

    @Override
    public String parse(Object obj) {
        return obj == null ? "null" : obj.toString();
    }

    @Override
    protected void parse(StringEncoder encoder, Object obj) {
        encoder.append(parse(obj));
    }

}
