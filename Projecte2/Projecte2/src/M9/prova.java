package M9;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;



public class prova {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

		final ScheduledExecutorService schExService = Executors.newScheduledThreadPool(2);

		final Runnable ob = new TascaProgramada().new ExecutaFil();

		schExService.scheduleWithFixedDelay(ob, 2, 5, TimeUnit.SECONDS);
		
		schExService.awaitTermination(10, TimeUnit.SECONDS);
		schExService.shutdown();
		
	}

	class ExecutaFil implements Runnable {
		@Override
		public void run() {
			Calendar calendario = new GregorianCalendar();
			System.out.println("Hora execució tasca: " + calendario.get(Calendar.HOUR_OF_DAY) + ":"
					+ calendario.get(Calendar.MINUTE) + ":" + calendario.get(Calendar.SECOND));
			System.out.println("Tasca en execució");

			System.out.println("Execució acabada");
		}
	}
}
