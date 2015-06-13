package cn.wensiqun.asmsupport.core.utils.log;

import java.util.logging.Level;
import java.util.logging.Logger;


public class Log {

    private Logger logger;
    
    public Log(Logger logger) {
        this.logger = logger;
        if(logger != null) {
            this.logger.setUseParentHandlers(false);
        }
    }

    public boolean isPrintEnabled() {
        if(logger == null) {
            return false;
        }
        return logger.isLoggable(Level.INFO);
    }
    
    public void print(String msg) {
        if(logger != null) {
            logger.info(msg);
        }
    }
    
    public void print(Object msg) {
        if(logger != null) {
            logger.info(msg.toString());
        }
    }
    
}