package ak.planets.logger;

import ak.planets.main.Reference;

import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Aleksander on 27/10/2015.
 */
public class Logger {
    private static PrintStream out =  System.out;
    private static String name = Reference.gameName;

    public enum LogLevel {
        ALL (1, "All"),
        DEBUG (2, "DEBUG"),
        ERROR (3, "ERROR");

        public int id;
        public String name;

        LogLevel(int id, String name){
            this.id = id;
            this.name = name;
        }
    }

    public static void log(LogLevel level, String message){
        Date now = new Date();
        out.printf("[%tD %tT][" + name + "][" + level.name + "] : " + message + "\n", now, now);
    }
}
