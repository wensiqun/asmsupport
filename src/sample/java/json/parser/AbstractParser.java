package json.parser;

import json.JSONPool;
import json.utils.StringEncoder;

public abstract class AbstractParser {

    protected JSONPool jsonPool;

    public AbstractParser(JSONPool jsonPool) {
        this.jsonPool = jsonPool;
    }

    public final String parse(Object obj) {
        StringEncoder encoder = new StringEncoder();
        parse(encoder, obj);
        return encoder.toString();
    }

    public abstract void parse(StringEncoder encoder, Object obj);
}
