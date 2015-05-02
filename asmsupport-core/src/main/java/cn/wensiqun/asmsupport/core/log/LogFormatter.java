package cn.wensiqun.asmsupport.core.log;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter {

    public synchronized String format(LogRecord record) {
        return "[ASMSupport-Info] " + formatMessage(record) + '\n';
    }
    
}