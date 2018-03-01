package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MailHelper {

	private static Logger logger = LoggerFactory.getLogger(MailHelper.class);

	private final static ExecutorService executors = new ThreadPoolExecutor(5, 50, 1, TimeUnit.HOURS,
			new ArrayBlockingQueue<Runnable>(3000),
			new ThreadPoolExecutor.CallerRunsPolicy());

	public static void submit(Runnable job) {
		executors.submit(job);
		// executors.shutdown();
	}
}
