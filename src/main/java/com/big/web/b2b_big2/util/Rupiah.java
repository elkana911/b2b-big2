package com.big.web.b2b_big2.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class Rupiah {
	private static final String[] majorNames = { "", " RIBU", " JUTA",
			" MILYAR", " TRILIUN", " QUADRILIUN", " QUINTILIUN" };

	private static final String[] tensNames = { "", " SEPULUH", " DUA PULUH",
			" TIGA PULUH", " EMPAT PULUH", " LIMA PULUH", " ENAM PULUH",
			" TUJUH PULUH", " DELAPAN PULUH", " SEMBILAN PULUH" };

	private static final String[] numNames = { "", " SATU", " DUA", " TIGA",
			" EMPAT", " LIMA", " ENAM", " TUJUH", " DELAPAN", " SEMBILAN",
			" SEPULUH", " SEBELAS", " DUA BELAS", " TIGA BELAS",
			" EMPAT BELAS", " LIMA BELAS", " ENAM BELAS", " TUJUH BELAS",
			" DELAPAN BELAS", " SEMBILAN BELAS" };

	private static String convertLessThanOneThousand(long number) {
		String soFar;

		if (number % 100 < 20) {
			soFar = numNames[(int) (number % 100)];
			number /= 100;
		} else {
			soFar = numNames[(int) (number % 10)];
			number /= 10;

			soFar = tensNames[(int) (number % 10)] + soFar;
			number /= 10;
		}
		if (number == 0)
			return soFar;
		return numNames[(int) (number)] + " RATUS" + soFar;
	}

	public static String say(long number) {
		if (number == 0) {
			return "NOL";
		}

		String prefix = "";

		if (number < 0) {
			number = -number;
			prefix = "negative";
		}

		String soFar = "";
		int place = 0;

		do {
			long n = number % 1000;
			if (n != 0) {
				String s = convertLessThanOneThousand(n);

				String pre = s + majorNames[place];

				if (pre.trim().equals("SATU RIBU"))
					soFar = " SERIBU" + soFar;
//				else if (pre.trim().equals("SATU RATUS"))
//					soFar = " SERATUS" + soFar;
				else
					soFar = pre + soFar;

			}
			place++;
			number /= 1000;
		} while (number > 0);

		return (prefix + soFar).trim().replaceAll("SATU RATUS", "SERATUS");
	}

	public static String say(double d) {
		long dollar = (long) Math.floor(d);
		long cent = (long) Math.floor((d - dollar) * 100.0f);
		String soFar = say(dollar);

		if (cent > 0) {
			soFar += " DAN " + say(cent) + " SEN";
		}

		return soFar;
	}

	public static String say(String s) {
		try {
			return say(Double.parseDouble(s));
		} catch (Exception e) {
			e.printStackTrace();
			
			return "0";
		}
	}

	public static String say(String money, String CurrencyName) {
		return say(money) + " " + CurrencyName;
	}
	
	public static String format(String s, boolean showLabel) {
		double harga = s == null ? 0 : Double.parseDouble(s.trim());

		return format(harga, showLabel);
	}

	public static String format(double harga, boolean showLabel) {
		
		DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
		DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
		
		formatRp.setCurrencySymbol(showLabel ? "Rp. " : "");
		formatRp.setMonetaryDecimalSeparator(',');
		formatRp.setGroupingSeparator('.');
		
		kursIndonesia.setDecimalFormatSymbols(formatRp);
		kursIndonesia.setMaximumFractionDigits(0);//buat ngilangin cent
		
		return kursIndonesia.format(harga);
//        System.out.printf("Harga Rupiah: %s %n", kursIndonesia.format(harga));	
	}

	public static void main(String[] args) {
		System.err.println(Rupiah.say("120000"));
		System.err.println(Rupiah.format("120000", false));
		
		String s = "ambon, (intl) xys (CGK)";
		System.out.println(Utils.getLastWord(s));
		System.out.println(Utils.getLastWord("CGK"));
		System.out.println(Utils.getLastWord(" (CGK)"));
		System.out.println(Utils.getLastWord("a (CGK)"));
	}
}
