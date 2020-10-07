package safar.machine_learning.text_classification_api.conf;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * Done !!.
 */
public class LoggerConf {
    public static Logger logger = Logger.getLogger(LoggerConf.class.getName());

    public LoggerConf() {
        //BasicConfigurator.configure();
        PatternLayout layout = new PatternLayout();
        /*%-7p %d [%t] %c %x -*/
        String conversionPattern = "%m%n";
        layout.setConversionPattern(conversionPattern);
        ConsoleAppender consoleAppender = new ConsoleAppender();
        consoleAppender.setLayout(layout);
        consoleAppender.activateOptions();
        logger.setLevel(Level.INFO);
        logger.addAppender(consoleAppender);
    }

    public LoggerConf setLoggerLevel(Level level) {
        logger.setLevel(level);
        return this;
    }
}
