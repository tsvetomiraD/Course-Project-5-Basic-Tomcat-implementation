import Servlet.ServletContext;
import WarInfo.ParseWebXml;
import org.apache.commons.cli.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    static int port;
    static int nThreads;

    private static boolean getOptions(String[] args) throws Exception {
        Option p = Option.builder("p").argName("port").hasArg().desc("Set port").build();
        Option t = Option.builder("t").argName("threads").hasArg().desc("set threads").build();
        Option h = Option.builder("h").argName("help").hasArg().desc("show help").build();
        Options options = new Options().addOption(t).addOption(p).addOption(h);

        CommandLineParser cp = new DefaultParser();
        CommandLine cl = cp.parse(options, args);

        if (cl.hasOption(h)) {
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("http-server", options);
            return true;
        }

        String pp = cl.getOptionValue("p", String.valueOf(80));
        port = Integer.parseInt(pp);

        String th = cl.getOptionValue("t", String.valueOf(1));
        nThreads = Integer.parseInt(th);

        return false;
    }

    private static void startServer(ServletContext ss) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(nThreads);
        try (ServerSocket s = new ServerSocket(port)) {
            while (true) {
                Socket socket = s.accept();
                HttpTask task = new HttpTask(socket, ss);
                pool.submit(task);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        boolean help = getOptions(args);
        if (help) {
            return;
        }

        ParseWebXml p = new ParseWebXml();
        ServletContext s = p.parse();

        startServer(s);
    }
}
