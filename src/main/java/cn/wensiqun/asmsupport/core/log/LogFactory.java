package cn.wensiqun.asmsupport.core.log;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import cn.wensiqun.asmsupport.core.utils.ASConstant;


/**
 * A simple log factory.
 * 
 * @author sqwen
 *
 */
public class LogFactory {

    private Handler handler;
    
    private Level level;
    
    private String logfile;
    
    private final static Log EMPTY_LOG = new Log(null);

    public LogFactory(String logFile, boolean print) {
        if(print) {
            level = Level.CONFIG;
            try {
                handler = new FileHandler(logfile, true);
            } catch (Exception e) {
                System.out.println("Error to create FileHandler cause by " + e.getMessage() + ", create ConsoleHandler replace.");
                handler = new ConsoleHandler();
            }
        } else {
            handler = new ConsoleHandler();
            level = Level.OFF;
        }
    }

    public LogFactory(boolean print) {
        handler = new ConsoleHandler();
        level = print ? Level.CONFIG : Level.OFF;
    }
    
    private Log getLogInternal(Class<?> type) {
        Logger log = Logger.getLogger(type.getName());
        log.setLevel(level);
        log.addHandler(handler);
        return new Log(log);
    }
    
    public static Log getLog(Class<?> type) {
        LogFactory factory = ASConstant.LOG_FACTORY_LOCAL.get();
        if(factory == null) {
            return EMPTY_LOG;
        } 
        return factory.getLogInternal(type);
    }
    
    public static class Log {

        private Logger logger;
        
        public Log(Logger logger) {
            this.logger = logger;
        }

        public boolean isDebugEnabled() {
            if(logger == null) {
                return false;
            }
            return logger.isLoggable(Level.CONFIG);
        }
        
        public void debug(String msg) {
            if(logger != null) {
                logger.config(msg);
            }
        }
        
    }

}
