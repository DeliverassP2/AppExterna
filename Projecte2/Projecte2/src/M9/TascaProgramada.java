package M9;



import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TascaProgramada {

	public static void main(final String... args) throws
	InterruptedException, ExecutionException {
	 //mostrem hora actual abans d’execució
	 Calendar calendario = new GregorianCalendar();
	 System.out.println("Inici: "+ calendario.get(Calendar.
	HOUR_OF_DAY) + ":" + calendario.get(Calendar.MINUTE) +
	":" + calendario.get(Calendar.SECOND));
	
	 // Crea un pool de 2 fils
	 final ScheduledExecutorService schExService = Executors.
	newScheduledThreadPool(2);
	 // Crea objecte Runnable.
	 final Runnable ob = new TascaProgramada().new ExecutaFil
	();
	 // Programa Fil, s’inicia als 2 segons i després es va
	 // executant cada 3 segons
	 schExService.scheduleWithFixedDelay(ob, 2, 3, TimeUnit.
	SECONDS);

	 // Espera per acabar 10 segons
	 schExService.awaitTermination(10, TimeUnit.SECONDS);
	 // shutdown .
	 schExService.shutdownNow();
	 System.out.println("Completat");
	 }

	// Fil Runnable
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
