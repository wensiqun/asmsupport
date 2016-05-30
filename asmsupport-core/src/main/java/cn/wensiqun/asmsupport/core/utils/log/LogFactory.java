package cn.wensiqun.asmsupport.core.utils.log;

import java.util.logging.*;


/**
 * A simple log factory.
 * 
 * @author sqwen
 *
 */
public class LogFactory {
	
    public final static ThreadLocal<LogFactory> LOG_FACTORY_LOCAL = new ThreadLocal<>();

    private StreamHandler handler;
    
    private final static Log EMPTY_LOG = new Log(null);

    public LogFactory(String logFile) {
        try {
            handler = new FileHandler(logFile, true);
        } catch (Exception e) {
            System.out.println("Error to create FileHandler cause by " + e.getMessage() + ", create ConsoleHandler replace.");
            handler = new ConsoleHandler();
        }
        handler.setFormatter(new LogFormatter());
    }

    public LogFactory() {
        handler = new ConsoleHandler() {
            {
                setOutputStream(System.out);
            }
        };
        handler.setFormatter(new LogFormatter());
    }
    
    private Log getLogInternal(Class<?> type) {
        Logger log = Logger.getLogger(type.getName());
        log.setLevel(Level.INFO);
        log.addHandler(handler);
        return new Log(log);
    }
    
    public static Log getLog(Class<?> type) {
        LogFactory factory = LOG_FACTORY_LOCAL.get();
        if(factory == null) {
            return EMPTY_LOG;
        } 
        return factory.getLogInternal(type);
    }

}
