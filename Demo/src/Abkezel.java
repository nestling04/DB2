import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.sound.midi.Receiver;

/**
 * 
 * @author Mészáros Gergõ, DML5Q5
 * 
 */

public class Abkezel {
	private static Connection conn = null;
	private static Statement s = null;
	private static String sqlp = "";
	private int vi = 0;

	// ========== Konstruktor
	public Abkezel() {
	}

	// ========== Konstruktor / Metódusok ==========
	public static int Exec(String sqlp) {
		int out = 0;
		try {
			s = conn.createStatement();
			out = s.executeUpdate(sqlp);
			BC.SMD("Végrehajtott parancs: " + sqlp);
		} catch (Exception e) {
			out = -1;
			BC.SMD("Probléma: " + e.getMessage());
		}
		return out;
	}

	// =================
	public static void Kapcs(String user, String pswd) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// String url = "jdbc:oracle:thin:@193.6.5.42:1521:info";
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			conn = DriverManager.getConnection(url, user, pswd);
			conn.setAutoCommit(false);
			BC.SMD("Sikeres kapcsolodas");
		} catch (Exception ex) {
			BC.SMD(ex.getMessage());
		}
	}

	// =================
	public static void LeKapcs() {
		if (conn != null) {
			try {
				conn.close();
				BC.SMD("Sikeres lekapcsolodas");
			} catch (Exception ex) {
				BC.SMD(ex.getMessage());
			}
		}
	}

	public static void StatikusTablaLetrehozas() {
		String sqlp_hivasok = "CREATE TABLE Hivasok(HID INT NOT NULL, Idopont DATE, Betegek_szama INT, Eset_sulyossaga INT, Eset_helyszine VARCHAR(150), PRIMARY KEY (HID))";
		String sqlp_betegek = "CREATE TABLE Betegek(BID INT NOT NULL, Kor INT, Nem INT, Nev VARCHAR(100), Allergia_Intolerancia VARCHAR(200), HID INT, PRIMARY KEY (BID), FOREIGN KEY (HID) REFERENCES Hivasok(HID))";
		String sqlp_kocsi = "CREATE TABLE Kocsi(KID INT NOT NULL, Személyzet_száma INT, Típus VARCHAR(80), Lóerõ INT, Felszereltségi_szint INT, Gyártási_év INT, PRIMARY KEY (KID))";
		String sqlp_kikuld = "CREATE TABLE Kiküld(HID INT NOT NULL, KID INT NOT NULL, FOREIGN KEY (HID) REFERENCES Hivasok(HID), FOREIGN KEY (KID) REFERENCES Kocsi(KID))";
		String sqlp_naplo = "CREATE TABLE Napló(Dátum DATE, Username VARCHAR(50), Adat CHAR(30))";
		String sqlp_seq = "CREATE SEQUENCE seq";
		if (conn != null) {
			try {
				s = conn.createStatement();
				/*
				 * s.executeUpdate(sqlp_hivasok);
				 * BC.SMD("Hívások tábla létrejött\n");
				 * s.executeUpdate(sqlp_betegek);
				 * BC.SMD("Betegek tábla létrejött\n");
				 * s.executeUpdate(sqlp_kocsi);
				 * BC.SMD("Kocsi tábla létrejött\n");
				 */
				s.executeUpdate(sqlp_kikuld);
				BC.SMD("Kiküld tábla létrejött\n");
				/*
				 * s.executeUpdate(sqlp_naplo);
				 * BC.SMD("Napló tábla létrejött\n"); s.executeUpdate(sqlp_seq);
				 * BC.SMD("Szekvencia létrehozva\n");
				 */
				s.close(); // erõforrás felszabadítása
			} catch (Exception ex) {
				BC.SMD(ex.getMessage());
			}
		}
	}

	public static void StatikusTablaModositas() {
		if (conn != null) {
			try {
				String sqlp = "alter table auto add(tulaj_id references tulaj)";
				s = conn.createStatement();
				s.executeUpdate(sqlp);
				BC.SMD("Auto tábla módosítva\n");
				s.close();
			} catch (Exception ex) {
				BC.SMD(ex.getMessage());
			}
		}
	}

	public static void StatikusAdatfelvitel() {
		if (conn != null) {
			String sqlp_tul = "insert into tulaj values(1, 'Tóth Máté', "
					+ " 'Miskolc', to_date('1980.05.12', 'yyyy.mm.dd'))";

			String[] sqlp = {
					"insert into auto values('aaa211','opel','piros',2014,1650000,1)",
					"insert into auto values ('bbb322','mazda','piros',2016,2800000,1)",
					"insert into auto (rsz, tipus, evjarat, ar) values ('ccc433','ford',2009, 15000000)" };
			try {
				s = conn.createStatement();
				s.executeUpdate(sqlp_tul);
				BC.SMD("Tulaj felvéve\n");
				s.close();
			} catch (Exception ex) {
				BC.SMD(ex.getMessage());
			}
			for (int i = 0; i < sqlp.length; i++) {
				try {
					s = conn.createStatement();
					s.executeUpdate(sqlp[i]);
					BC.SMD("Autó felvéve\n");
					s.close();
				} catch (Exception ex) {
					BC.SMD(ex.getMessage());
				}
			}
		}
	}

	public static void UjAdat(String query) {
		Kapcs("system", "jelszo");
		try {
			s = conn.createStatement();
			s.executeUpdate(query);
			conn.commit();
			BC.SMD("Adat felvéve\n");
		} catch (Exception ex) {
			try {
				conn.rollback();
			} catch (SQLException sqle) {
				BC.SMD(sqle.getMessage());
			}
			BC.SMD(ex.getMessage());
		} finally {
			try {
				s.close();
			} catch (SQLException sqle) {
				BC.SMD(sqle.getMessage());
			}
			LeKapcs();
		}
	}

	public static void DinamikusAdatfelvitel() {
		Scanner sc = new Scanner(System.in);
		PreparedStatement ps = null;

		if (conn != null) {
			// Az SQL parancsban a ? helyére kerülnek a paraméterek
			String sqlp = "insert into auto (rsz, tipus, szin, evjarat, ar, tulaj_id)"
					+ "values (?, ?, ?, ?, ?, ?)";
			BC.SMD("Kérem a rendszámot: ");
			String rsz = sc.next().trim();
			BC.SMD("Kérem a típust: ");
			String tipus = sc.next().trim();
			BC.SMD("Kérem a színt: ");
			String szin = sc.next().trim();
			BC.SMD("Kérem az évjáratot: ");
			int evjarat = sc.nextInt();
			BC.SMD("Kérem az árat: ");
			float ar = sc.nextFloat();
			BC.SMD("Kérem a tulajdonos azonosítóját: ");
			int tulaj_id = sc.nextInt();
			try {
				ps = conn.prepareStatement(sqlp);
				ps.setString(1, rsz);
				ps.setString(2, tipus);
				ps.setString(3, szin);
				ps.setInt(4, evjarat);
				ps.setFloat(5, ar);
				ps.setInt(6, tulaj_id);
				ps.executeUpdate();
				ps.close();
				BC.SMD("Autó felvéve\n");
			} catch (Exception ex) {
				BC.SMD(ex.getMessage());
			}
		}
	}

	public static void StatikusAdattorles() {
		Scanner sc = new Scanner(System.in);

		BC.SMD("Törlendõ autó rendszáma: ");
		String rsz = sc.next();
		String sqlp = "delete from auto where rsz like '" + rsz + "'";
		if (conn != null) {
			try {
				s = conn.createStatement();
				s.executeUpdate(sqlp);
				s.close();
				BC.SMD(rsz + " rendszámú autó törölve\n");
			} catch (Exception ex) {
				BC.SMD(ex.getMessage());
			}
		}
	}

	public static void StatikusLekerdezes() {
		ResultSet rs = null;

		if (conn != null) {
			String sqlp = "select * from auto";
			BC.SMD("Rendszám Típus Szín Évjárat Ár Tulaj");
			System.out
					.println("-------------------------------------------------");
			try {
				s = conn.createStatement();
				s.executeQuery(sqlp);
				rs = s.getResultSet();
				while (rs.next()) {
					// A get metódusoknak a megfelelõ típusú táblamezõket kell
					// megadni
					String rsz = rs.getString("rsz");
					String tipus = rs.getString("tipus");
					String szin = rs.getString("szin");
					int evjarat = rs.getInt("evjarat");
					int ar = rs.getInt("ar");
					int tulaj_id = rs.getInt("tulaj_id");
					BC.SMD(rsz + "\t\t" + tipus + "\t" + szin + "\t" + evjarat
							+ "\t" + ar + "\t" + tulaj_id);
				}
				rs.close();
			} catch (Exception ex) {
				BC.SMD(ex.getMessage());
			}
		}
	}

	public static List<String> Lekerdez() {
		Kapcs("system", "jelszo");
		ResultSet rs = null;
		List<String> records = new ArrayList<String>();
		if (conn != null) {
			String sqlp = "select * from hivasok";
			try {
				s = conn.createStatement();
				s.executeQuery(sqlp);
				BC.showMD(sqlp, 3);
				rs = s.getResultSet();
				while (rs.next()) {
					records.add(rs.getString("hid"));

					records.add(rs.getString("idopont"));

					records.add(rs.getString("betegek_szama"));

					records.add(rs.getString("eset_sulyossaga"));

					records.add(rs.getString("eset_helyszine"));

					records.add("\n");
				}
				rs.close();
			} catch (Exception ex) {
				BC.SMD(ex.getMessage());
			}
		}
		records.add("\n");
		if (conn != null) {
			String sqlp = "select * from betegek";
			try {
				s = conn.createStatement();
				s.executeQuery(sqlp);
				BC.showMD(sqlp, 3);
				rs = s.getResultSet();
				while (rs.next()) {
					records.add(rs.getString("bid"));

					records.add(rs.getString("kor"));

					records.add(rs.getString("nem"));

					records.add(rs.getString("nev"));

					records.add(rs.getString("allergia_intolerancia"));

					records.add(rs.getString("hid"));

					records.add("\n");
				}
				rs.close();
			} catch (Exception ex) {
				BC.SMD(ex.getMessage());
			}
		}
		records.add("\n");
		if (conn != null) {
			String sqlp = "select * from kocsi";
			try {
				s = conn.createStatement();
				s.executeQuery(sqlp);
				BC.showMD(sqlp, 3);
				rs = s.getResultSet();
				while (rs.next()) {
					records.add(rs.getString("kid"));

					records.add(rs.getString("személyzet_száma"));

					records.add(rs.getString("típus"));

					records.add(rs.getString("lóerõ"));

					records.add(rs.getString("felszereltségi_szint"));

					records.add(rs.getString("gyártási_év"));

					records.add("\n");
				}
				rs.close();
			} catch (Exception ex) {
				BC.SMD(ex.getMessage());
			}
		}
		LeKapcs();
		return records;
	}

	public static List<String> Lekerdez(String kor, String tipus) {
		Kapcs("system", "jelszo");
		ResultSet rs = null;
		List<String> records = new ArrayList<String>();
		if (conn != null) {
			String sqlp = "select * from hivasok";
			try {
				s = conn.createStatement();
				s.executeQuery(sqlp);
				rs = s.getResultSet();
				while (rs.next()) {
					records.add(rs.getString("hid"));

					records.add(rs.getString("idopont"));

					records.add(rs.getString("betegek_szama"));

					records.add(rs.getString("eset_sulyossaga"));

					records.add(rs.getString("eset_helyszine"));

					records.add("\n");
				}
				rs.close();
			} catch (Exception ex) {
				BC.SMD(ex.getMessage());
			}
		}
		records.add("\n");
		if (conn != null) {
			String sqlp = "select * from betegek where kor = " + kor;
			try {
				s = conn.createStatement();
				s.executeQuery(sqlp);
				rs = s.getResultSet();
				while (rs.next()) {
					records.add(rs.getString("bid"));

					records.add(rs.getString("kor"));

					records.add(rs.getString("nem"));

					records.add(rs.getString("név"));

					records.add(rs.getString("allergia_intolerancia"));

					records.add(rs.getString("hid"));

					records.add("\n");
				}
				rs.close();
			} catch (Exception ex) {
				BC.SMD(ex.getMessage());
			}
		}
		records.add("\n");
		if (conn != null) {
			String sqlp = "select * from kocsi where típus like '%" + tipus
					+ "%'";
			try {
				s = conn.createStatement();
				s.executeQuery(sqlp);
				rs = s.getResultSet();
				while (rs.next()) {
					records.add(rs.getString("kid"));

					records.add(rs.getString("személyzet_száma"));

					records.add(rs.getString("típus"));

					records.add(rs.getString("lóerõ"));

					records.add(rs.getString("felszereltségi_szint"));

					records.add(rs.getString("gyártási_év"));

					records.add("\n");
				}
				rs.close();
			} catch (Exception ex) {
				BC.SMD(ex.getMessage());
			}
		}
		LeKapcs();
		return records;
	}

	public static int getID() {
		Kapcs("system", "jelszo");
		ResultSet rs = null;
		String sqlp = "select seq.nextval from dual";
		int nextval = 0;
		try {
			s = conn.createStatement();
			s.executeQuery(sqlp);
			rs = s.getResultSet();
			while (rs.next()) {
				nextval = rs.getInt("nextval");
			}
			rs.close();
		} catch (Exception ex) {
			BC.SMD(ex.getMessage());
		}
		LeKapcs();
		return nextval;
	}

	public static void getMetaData() {
		Kapcs("system", "jelszo");
		ResultSet rs = null;
		String sqlp = "select dbms_metadata.get_ddl('TABLE', 'BETEGEK') as meta from dual";
		try {
			s = conn.createStatement();
			s.executeQuery(sqlp);
			rs = s.getResultSet();
			while (rs.next()) {
				BC.showMD(rs.getString("meta"), 3);
			}
			rs.close();
		} catch (Exception ex) {
			BC.SMD(ex.getMessage());
		}
		LeKapcs();
	}

	public static void ModosithatoKurzor() {
		Scanner sc = new Scanner(System.in);
		Statement s = null;
		ResultSet rs = null;

		BC.SMD("Szín: ");
		String szin = sc.next().trim();
		String sqlp = "select ar from auto where szin = '" + szin + "'";
		if (conn != null) {
			try {
				s = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
						ResultSet.CONCUR_UPDATABLE);
				rs = s.executeQuery(sqlp);
				while (rs.next()) {
					int regiar = rs.getInt("ar");
					rs.updateInt("ar", (regiar * 2));
					rs.updateRow();
				}
			} catch (Exception ex) {
				BC.SMD(ex.getMessage());
			}
		}
	}

	public static void DinamikusModositas() {
		ResultSet rs = null;

		Scanner sc = new Scanner(System.in);
		PreparedStatement ps = null;
		if (conn != null) {
			String sqlp = "update auto set ar=ar-?";
			BC.SMD("Mennyivel csökkentsük az árat? ");
			int arcsokk = sc.nextInt();
			try {
				conn.setAutoCommit(false);
				try {
					ps = conn.prepareStatement(sqlp);
					ps.setInt(1, arcsokk);
					ps.executeUpdate();
					conn.commit();
					BC.SMD("Módosítás megtörtént\n");
				} catch (Exception e) {
					BC.SMD(e.getMessage());
					BC.SMD("Hiba történt\n");
				}
				try {
					sqlp = "select * from auto";
					s = conn.createStatement();
					s.executeQuery(sqlp);
					rs = s.getResultSet();
					while (rs.next()) {
						int ar = rs.getInt("ar");
						BC.SMD("aktuális ár: " + ar);
						if (ar < 15) {
							conn.rollback();
							BC.SMD("Módosítás visszavonva\n");
							BC.SMD("aktuális ár: " + ar);
						}
					}
					rs.close();
				} catch (Exception ex) {
					BC.SMD(ex.getMessage());
				}

				conn.setAutoCommit(true);
			} catch (Exception ex) {
				BC.SMD(ex.getMessage());
			}
		}
	}

	public static void OutEljarasHivas() {
		Scanner sc = new Scanner(System.in);
		CallableStatement cs = null;

		if (conn != null) {
			try {
				String sqlp = "create or replace procedure atlagar "
						+ "(sz IN char, atl OUT number) is " + "begin "
						+ "select avg(ar) into atl from auto where szin=sz; "
						+ "end;";
				BC.SMD("Szín: ");
				String szin = sc.next();
				s = conn.createStatement();
				s.executeUpdate(sqlp);
				BC.SMD("Eljárás létrejött\n");
				cs = conn.prepareCall("{call atlagar(?, ?)}");
				cs.setString(1, szin);
				cs.registerOutParameter(2, java.sql.Types.FLOAT);
				cs.execute();
				float atlag = cs.getFloat(2);
				BC.SMD(szin + " autók átlagára: " + atlag + "\n");
			} catch (Exception ex) {
				BC.SMD(ex.getMessage());
			}
		}
	}

	public static void Hivasok_szama(String date) {
		Kapcs("system", "jelszo");

		// CallableStatement cs = null;
		// if (conn != null) {
		// try {
		// String sqlp =
		// "create or replace function hivasok_szama(nap date) return int as darab int; begin select count(*) into darab from hivasok where idopont like nap; return darab; end;";
		// s = conn.createStatement();
		// s.executeUpdate(sqlp);
		// BC.SMD("Eljárás létrejött\n");
		// cs = conn
		// .prepareCall("{? = call hivasok_szama(to_date('?','YYYY-MM-DD')}");
		// cs.registerOutParameter(1, Types.VARCHAR);
		// cs.setString(2, date);
		// cs.execute();
		// String darab = cs.getString(1);
		// BC.SMD("Hívások száma " + date + "-én/án: " + darab);
		// } catch (Exception ex) {
		// BC.SMD(ex.getMessage());
		// } finally {
		// try {
		// cs.close();
		// } catch (Exception ex) {
		// BC.SMD(ex.getMessage());
		// }
		// }
		// } else {
		// BC.showMD("Conn is null", 3);
		// }

		ResultSet rs = null;
		int db = 0;
		if (conn != null) {
			String sqlp = "select hivasok_szama(to_date('" + date
					+ "','YYYY-MM-DD')) as db from dual";
			try {
				s = conn.createStatement();
				s.executeQuery(sqlp);
				BC.showMD(sqlp, 3);
				rs = s.getResultSet();
				while (rs.next()) {
					db = rs.getInt("db");
				}
				rs.close();
			} catch (Exception ex) {
				BC.SMD(ex.getMessage());
			}
		}

		BC.SMD("Hívások száma " + date + "-én/án: " + db);

		LeKapcs();
	}

	// ========== Metódusok / Fõprogram ==========
	public static void main(String args[]) {
		Abkezel abk = new Abkezel();

		Kapcs("system", "jelszo");

		// sqlp = "drop table tulaj";
		// sqlp = "delete from kiküld";
		// int valami = Exec(sqlp);
		// sqlp = "drop table kocsi";
		// valami = Exec(sqlp);

		abk.StatikusTablaLetrehozas();
		// abk.StatikusTablaModositas();
		// abk.StatikusAdatfelvitel();

		// abk.DinamikusAdatfelvitel();
		// abk.StatikusAdattorles();
		// abk.StatikusLekerdezes();
		// abk.ModosithatoKurzor();
		// abk.StatikusLekerdezes();
		// abk.DinamikusModositas();
		// abk.StatikusLekerdezes();
		// abk.OutEljarasHivas();
		LeKapcs();
	}
}