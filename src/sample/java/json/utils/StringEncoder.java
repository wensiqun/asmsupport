package json.utils;


public class StringEncoder {
    
    private StringBuilder stringBuilder; 
    
    public StringEncoder() {
        stringBuilder = new StringBuilder();
    }
    
    public StringEncoder(int capacity) {
        stringBuilder = new StringBuilder(capacity);
    }
    
    public int length() {
        return stringBuilder.length();
    }
    
    public StringEncoder append(CharSequence csq) {
        if(csq == null) {
            stringBuilder.ensureCapacity(stringBuilder.length());
            stringBuilder.append("null");
        } else {
            stringBuilder.ensureCapacity(stringBuilder.length() + csq.length());
            for(int i=0, len=csq.length(); i<len; i++) {
                append(csq.charAt(i));
            }
        }
        return this;
    }

    public StringEncoder append(char c) {
        if(c == '\\') {
            stringBuilder.append('\\').append('\\');
        } else if (c == '"') {
            stringBuilder.append('\\').append('"');
        } else {
            stringBuilder.append(c);
        }
        return this;
    }

    public StringEncoder appendDirect(char c) {
        stringBuilder.append(c);
        return this;
    }
    
    public StringEncoder append(boolean c) {
        stringBuilder.append(c);
        return this;
    }

    public StringEncoder append(byte c) {
        stringBuilder.append(c);
        return this;
    }

    public StringEncoder append(short c) {
        stringBuilder.append(c);
        return this;
    }

    public StringEncoder append(int c) {
        stringBuilder.append(c);
        return this;
    }

    public StringEncoder append(float c) {
        stringBuilder.append(c);
        return this;
    }

    public StringEncoder append(long c) {
        stringBuilder.append(c);
        return this;
    }

    public StringEncoder append(double c) {
        stringBuilder.append(c);
        return this;
    }

    public StringEncoder append(Object c) {
        return this.append(c.toString());
    }
    
    public void trimLastComma() {
        int len = stringBuilder.length() - 1;
        if(',' == stringBuilder.charAt(len)) {
            stringBuilder.deleteCharAt(len);
        }
    }

    public StringEncoder deleteCharAt(int index) {
        stringBuilder.deleteCharAt(index);
        return this;
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }
    
    
}
