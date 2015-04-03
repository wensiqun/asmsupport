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

    protected final void appendValue(StringEncoder encoder, Object val) {
        Class<?> type = val.getClass();
        if (type == boolean.class || type == Boolean.class) {
            encoder.append((Boolean) val);
        } else if (type == byte.class || type == Byte.class) {
            encoder.append((Byte) val);
        } else if (type == short.class || type == Short.class) {
            encoder.append((Short) val);
        } else if (type == char.class || type == Character.class) {
            encoder.append((Character) val);
        } else if (type == int.class || type == Integer.class) {
            encoder.append((Integer) val);
        } else if (type == float.class || type == Float.class) {
            encoder.append((Float) val);
        } else if (type == long.class || type == Long.class) {
            encoder.append((Long) val);
        } else if (type == double.class || type == Double.class) {
            encoder.append((Double) val);
        } else {
            jsonPool.getOrRegister(type).parse(encoder, val);
        }
    }
}
