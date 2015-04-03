package json.parser;

import json.JSONPool;
import json.utils.StringEncoder;

public class BaseParser extends AbstractParser {

    public BaseParser(JSONPool jsonPool) {
        super(jsonPool);
    }

    @Override
    public void parse(StringEncoder encoder, Object obj) {
        encoder.append(obj.toString());
    }

}
