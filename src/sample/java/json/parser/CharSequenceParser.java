package json.parser;

import json.JSONPool;
import json.utils.StringEncoder;

public class CharSequenceParser extends AbstractParser {

    public CharSequenceParser(JSONPool jsonPool) {
        super(jsonPool);
    }

    @Override
    public void parse(StringEncoder encoder, Object obj) {
        encoder.appendDirect('"').append(obj.toString()).appendDirect('"');
    }

}
