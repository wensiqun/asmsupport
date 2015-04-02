package json.parser;

import json.StringEncoder;
import json.generator.JSONPool;

public class CharSequenceParser extends AbstractParser {

    public CharSequenceParser(JSONPool jsonPool) {
        super(jsonPool);
    }

    @Override
    public String parse(Object obj) {
        if(obj == null) {
            return "";
        }
        StringEncoder se = new StringEncoder(obj.toString().length() + 2);
        se.append(obj.toString());
        return se.toString();
    }

    @Override
    protected void parse(StringEncoder encoder, Object obj) {
        encoder.append(obj.toString());
    }

}
