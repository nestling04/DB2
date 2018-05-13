import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class BC {
	private static String mes = "ABKezel program üzenet";
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");

	public static String RF(JTextField a) {
		return a.getText().toString();
	}

	public static boolean filled(JTextField a) {
		String s = RF(a);
		if (s.length() > 0)
			return true;
		else
			return false;
	}

	public static boolean goodDate(JTextField a) {
		String s = RF(a);
		Date testDate = null;
		try {
			testDate = sdf.parse(s);
		} catch (ParseException e) {
			return false;
		}
		if (sdf.format(testDate).equals(s))
			return true;
		else
			return false;
	}

	public static boolean goodInt(JTextField a) {
		String s = RF(a);
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean goodSize(JTextField a) {
		String s = RF(a);
		String[] sa = s.split(";");
		if (sa.length != 3) {
			return false;
		}
		try {
			for (int i = 0; i < sa.length; i++) {
				if (sa[i].trim().equals("")) {
					continue;
				}
				Integer.parseInt(sa[i]);
			}
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static void showMD(String s, int i) {
		JOptionPane.showMessageDialog(null, s, mes, i);
	}

	public static void SMD(String s) {
		JOptionPane.showMessageDialog(null, s, mes, 0);
	}

	public static Date StoD(String s) {
		Date testDate = null;
		try {
			testDate = sdf.parse(s);
			;
		} catch (ParseException e) {
			return testDate;
		}
		if (sdf.format(testDate).equals(s))
			return testDate;
		else
			return testDate;
	}

	public static int StoI(String s) {
		int x = -55;
		x = Integer.parseInt(s);
		return x;
	}

	public static void DF(JTextField a) {
		a.setText("");
	}

	public static boolean goodStoInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
