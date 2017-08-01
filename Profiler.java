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
	// {2017-07-31 16:40=2017-07-31 16:40}
	static Map<String, String> table = new HashMap<>();
	static ConcurrentSkipListMap<String, String[]> result = new ConcurrentSkipListMap<>(new ComparatorKeys());
	static String[] keys;
	static DateTimeFormatter format = DateTimeFormatter.ofPattern("d.MM.yyyy");


	static {
		table.put("1.01.2017 09:00", "1.01.2017 09:45");
		table.put("1.01.2017 10:00", "1.01.2017 10:00");
		table.put("1.01.2017 10:30", "2.01.2017 12:45");
		table.put("1.01.2017 11:00", "2.01.2017 12:45");
		table.put("1.01.2017 11:30", "2.01.2017 12:45");
	}
	public static void main(String[] args) {
//		table.entrySet().parallelStream().forEach((e) -> {
//
//		});

		int durationHour = 1;

		initResultKeys(1, 2017, format);

		for (Map.Entry<String, String> m : table.entrySet()) {
//			if (m.getValue().equals("") || m.getValue().equals("-")) {
//				m.getKey()
//			}

			String[] arrayStart = m.getKey().split("\\s");
			LocalDate dateStart = LocalDate.parse(arrayStart[0], DateTimeFormatter.ofPattern("d.MM.yyyy"));
			LocalTime timeStart = LocalTime.parse(arrayStart[1], DateTimeFormatter.ofPattern("HH:mm"));

			String[] arrayEnd = m.getValue().split("\\s");
			LocalDate dateEnd = LocalDate.parse(arrayEnd[0], DateTimeFormatter.ofPattern("d.MM.yyyy"));
			LocalTime timeEnd = LocalTime.parse(arrayEnd[1], DateTimeFormatter.ofPattern("HH:mm"));


			int diff = getCalculateDiffHour(dateStart, timeStart, dateEnd, timeEnd);


		}

		show();
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

	static String getHour(int i) {
		if (i < 10)
			return "0" + i;
		else
			return "" + i;
	}
}
