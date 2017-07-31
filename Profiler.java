package ru.teleofis;

import java.text.DateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by fruitjazzy on 31.07.17.
 */
public class Profiler {
	// {2017-07-31 16:40=2017-07-31 16:40}
	static Map<String, String> table = new HashMap<>();
	static ConcurrentHashMap<String, String[]> result = new ConcurrentHashMap<>();
	static String[] keys;

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
		DateTimeFormatter format = DateTimeFormatter.ofPattern("d.MM.yyyy");

		getKeysFromMonth(1, 2017, format);

	}

	static String[] getKeysFromMonth(int month, int year, DateTimeFormatter format) {
		LocalDate startDate = LocalDate.of(year, month, 1);
		keys = new String[startDate.lengthOfMonth()];
		keys[0] = startDate.format(format);
		for (int i = 1; i < startDate.lengthOfMonth(); i++) {
			keys[i] = startDate.plusDays(i).format(format);
		}
		return keys;
	}

	static int betweenHours(String start, String stop) {
		LocalTime first = LocalTime.parse(start);
		LocalTime second = LocalTime.parse(stop);
		return Math.abs((int) ChronoUnit.HOURS.between(first, second));
	}

	static int betweenDays(String start, String stop, DateTimeFormatter format) {
		LocalDate from = LocalDate.parse(start, format);
		LocalDate to = LocalDate.parse(stop, format);
		return Math.abs((int) ChronoUnit.DAYS.between(from, to));
	}

	static String getHour(int i) {
		if (i < 10)
			return "0" + i;
		else
			return "" + i;
	}
}
