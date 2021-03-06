import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by fruitjazzy on 31.07.17.
 */
public class Profiler {

	private static Map<String, String> table = new HashMap<>();
	private static ConcurrentSkipListMap<String, String[]> result = new ConcurrentSkipListMap<>(new ComparatorKeys());
	private static String[] keys;
	private static DateTimeFormatter format = DateTimeFormatter.ofPattern("d.MM.yyyy");

	static {
		table.put("1.01.2017 09:00", "1.01.2017 09:45");
		table.put("1.01.2017 10:00", "1.01.2017 10:00");
		table.put("1.01.2017 10:30", "2.01.2017 12:45");
		table.put("1.01.2017 11:00", "2.01.2017 12:45");
		table.put("2.01.2017 11:30", "2.01.2017 12:45");
	}

	public static void main(String[] args) {
		// допустимый период между началом и концом события
		int period = 1;

		// подготавливаем список ключей (дни месяца)
		initResultKeys(1, 2017, format);

		// подготавливаем карту с ключами дни месяца и массивом значений 0-23
		prepareResultMap();

		table.entrySet().parallelStream().forEach((e) -> {
			// если пустое значение в таблице
			if (e.getValue().equals("") || e.getValue().equals("-")) {
				return;
			}

			// парсим данные из таблицы дату и часы
			String[] arrayStart = e.getKey().split("\\s");
			LocalDate dateStart = getData(arrayStart[0]);
			LocalTime timeStart = getHours(arrayStart[1]);

			String[] arrayEnd = e.getValue().split("\\s");
			LocalDate dateEnd = getData(arrayEnd[0]);
			LocalTime timeEnd = getHours(arrayEnd[1]);


			// подсчитываем разницу между началом события и концом
			int diff = getCalculateDiffHour(dateStart, timeStart, dateEnd, timeEnd);
			int res = diff <= period ? 0 : diff;


			// получаем текущее значение за определенную дату и час
			String val = result.get(dateStart.format(format))[getIndex(timeStart)];

			Integer i;
			try {
				 i = Integer.valueOf(val);
			}
			catch (IllegalArgumentException x) {
				i = 0;
			}
			
			// прибавляем к тому что есть
			val = String.valueOf(res + i);
			result.get(dateStart.format(format))[getIndex(timeStart)] = val;
		});

		show();
	}

	private static int getIndex(LocalTime hh) {
		String[] time = hh.toString().split(":");
		return Integer.valueOf(time[0]);
	}

	private static void prepareResultMap() {
		for (String key : keys) {
			String[] val = new String[24];
			prepareValues(val);
			result.put(key, val);
		}
	}

	private static LocalTime getHours(String text) {
		return LocalTime.parse(text, DateTimeFormatter.ofPattern("HH:mm"));
	}

	private static LocalDate getData(String text) {
		return LocalDate.parse(text, DateTimeFormatter.ofPattern("d.MM.yyyy"));
	}

	private static int getCalculateDiffHour(LocalDate dateStart, LocalTime timeStart, LocalDate dateEnd, LocalTime timeEnd) {
		int res = 0;
		res += betweenHours(timeStart, timeEnd);
		res += betweenDays(dateStart, dateEnd) * 24;
		return res;
	}

	private static void show() {
		for (String key : result.keySet()) {
			System.out.println(key + ": " + Arrays.toString(result.get(key)));
		}
	}

	static class ComparatorKeys implements Comparator<String> {
		@Override
		public int compare(String o1, String o2) {
			LocalDate first = LocalDate.parse(o1,format);
			LocalDate second = LocalDate.parse(o2,format);

			long between = ChronoUnit.DAYS.between(first, second);
			if (between < 0) {
				return 1;
			}
			else if (between > 0) {
				return -1;
			}
			return 0;
		}
	}

	static void prepareValues(String[] val) {
		for (int i = 0; i < val.length; i++) {
			val[i] = "-";
		}
	}

	static String[] initResultKeys(int month, int year, DateTimeFormatter format) {
		LocalDate startDate = LocalDate.of(year, month, 1);
		keys = new String[startDate.lengthOfMonth()];
		keys[0] = startDate.format(format);
		for (int i = 1; i < startDate.lengthOfMonth(); i++) {
			keys[i] = startDate.plusDays(i).format(format);
		}
		return keys;
	}

	static int betweenHours(LocalTime start, LocalTime stop) {
		return Math.abs((int) ChronoUnit.HOURS.between(start, stop));
	}

	static int betweenDays(LocalDate start, LocalDate stop) {
		return Math.abs((int) ChronoUnit.DAYS.between(start, stop));
	}
}
