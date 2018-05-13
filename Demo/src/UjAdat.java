import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UjAdat extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField idopont;
	private JTextField betegekszama;
	private JTextField esetsulyossaga;
	private JTextField esethelyszine;
	private JTextField kor;
	private JTextField nem;
	private JTextField nev;
	private JTextField allergiaintolerancia;
	private JTextField szemelyzetszama;
	private JTextField tipus;
	private JTextField loero;
	private JTextField felszereltsegiszint;
	private JTextField gyartasiev;

	/**
	 * Create the dialog.
	 */
	public UjAdat(JFrame f) {
		super(f, true);
		setTitle("Új adat felvitele");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		idopont = new JTextField();
		idopont.setBounds(66, 11, 125, 20);
		contentPanel.add(idopont);
		idopont.setColumns(10);

		JLabel lblIdpont = new JLabel("Id\u0151pont:");
		lblIdpont.setBounds(10, 14, 46, 14);
		contentPanel.add(lblIdpont);

		JLabel lblBetegekSzma = new JLabel("Betegek sz\u00E1ma:");
		lblBetegekSzma.setBounds(10, 45, 79, 14);
		contentPanel.add(lblBetegekSzma);

		betegekszama = new JTextField();
		betegekszama.setBounds(87, 42, 104, 20);
		betegekszama.setColumns(10);
		contentPanel.add(betegekszama);

		JLabel lblEsetSlyossga = new JLabel("Eset s\u00FAlyoss\u00E1ga:");
		lblEsetSlyossga.setBounds(10, 73, 92, 14);
		contentPanel.add(lblEsetSlyossga);

		esetsulyossaga = new JTextField();
		esetsulyossaga.setBounds(99, 70, 92, 20);
		esetsulyossaga.setColumns(10);
		contentPanel.add(esetsulyossaga);

		JLabel lblHelyszn = new JLabel("Helysz\u00EDn:");
		lblHelyszn.setBounds(10, 101, 46, 14);
		contentPanel.add(lblHelyszn);

		esethelyszine = new JTextField();
		esethelyszine.setBounds(77, 98, 114, 20);
		esethelyszine.setColumns(10);
		contentPanel.add(esethelyszine);

		JLabel lblKor = new JLabel("Kor:");
		lblKor.setBounds(10, 129, 46, 14);
		contentPanel.add(lblKor);

		kor = new JTextField();
		kor.setBounds(77, 126, 114, 20);
		kor.setColumns(10);
		contentPanel.add(kor);

		JLabel lblNem = new JLabel("Nem:");
		lblNem.setBounds(10, 157, 46, 14);
		contentPanel.add(lblNem);

		nem = new JTextField();
		nem.setBounds(77, 154, 114, 20);
		nem.setColumns(10);
		contentPanel.add(nem);

		JLabel lblNv = new JLabel("N\u00E9v:");
		lblNv.setBounds(10, 185, 46, 14);
		contentPanel.add(lblNv);

		nev = new JTextField();
		nev.setBounds(77, 182, 114, 20);
		nev.setColumns(10);
		contentPanel.add(nev);

		JLabel lblAllergiaintolerancia = new JLabel("Allergia-Intolerancia:");
		lblAllergiaintolerancia.setBounds(10, 213, 104, 14);
		contentPanel.add(lblAllergiaintolerancia);

		allergiaintolerancia = new JTextField();
		allergiaintolerancia.setBounds(112, 210, 320, 20);
		allergiaintolerancia.setColumns(10);
		contentPanel.add(allergiaintolerancia);

		szemelyzetszama = new JTextField();
		szemelyzetszama.setBounds(340, 67, 92, 20);
		szemelyzetszama.setColumns(10);
		contentPanel.add(szemelyzetszama);

		JLabel lblSzemlyzetSzma = new JLabel("Szem\u00E9lyzet sz\u00E1ma:");
		lblSzemlyzetSzma.setBounds(218, 70, 97, 14);
		contentPanel.add(lblSzemlyzetSzma);

		tipus = new JTextField();
		tipus.setBounds(307, 95, 125, 20);
		tipus.setColumns(10);
		contentPanel.add(tipus);

		JLabel lblTpus = new JLabel("T\u00EDpus:");
		lblTpus.setBounds(251, 98, 46, 14);
		contentPanel.add(lblTpus);

		loero = new JTextField();
		loero.setBounds(307, 123, 125, 20);
		loero.setColumns(10);
		contentPanel.add(loero);

		JLabel lblLer = new JLabel("L\u00F3er\u0151:");
		lblLer.setBounds(251, 126, 46, 14);
		contentPanel.add(lblLer);

		felszereltsegiszint = new JTextField();
		felszereltsegiszint.setBounds(328, 151, 104, 20);
		felszereltsegiszint.setColumns(10);
		contentPanel.add(felszereltsegiszint);

		JLabel lblFelszereltsgiSzint = new JLabel("Felszerelts\u00E9gi szint:");
		lblFelszereltsgiSzint.setBounds(218, 154, 97, 14);
		contentPanel.add(lblFelszereltsgiSzint);

		gyartasiev = new JTextField();
		gyartasiev.setBounds(307, 179, 125, 20);
		gyartasiev.setColumns(10);
		contentPanel.add(gyartasiev);

		JLabel lblGyrtsiv = new JLabel("Gy\u00E1rt\u00E1si \u00E9v:");
		lblGyrtsiv.setBounds(228, 182, 69, 14);
		contentPanel.add(lblGyrtsiv);

		JLabel lblKikldttKocsiAdatai = new JLabel(
				"Kik\u00FCld\u00F6tt kocsi adatai:");
		lblKikldttKocsiAdatai.setHorizontalAlignment(SwingConstants.CENTER);
		lblKikldttKocsiAdatai.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblKikldttKocsiAdatai.setBounds(228, 11, 180, 36);
		contentPanel.add(lblKikldttKocsiAdatai);

		JButton btnFelvisz = new JButton("Felvisz");
		btnFelvisz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int hid = Abkezel.getID();
				int bid = Abkezel.getID();
				int kid = Abkezel.getID();
				String hivasok_query = "insert into hivasok values(" + hid
						+ ", to_date('" + idopont.getText()
						+ "','YYYY-MM-DD'), " + betegekszama.getText() + ", "
						+ esetsulyossaga.getText() + ", '"
						+ esethelyszine.getText() + "')";
				String betegek_query = "insert into betegek values(" + bid
						+ ", " + kor.getText() + ", " + nem.getText() + ", '"
						+ nev.getText() + "', '"
						+ allergiaintolerancia.getText() + "', " + hid + ")";
				String kocsi_query = "insert into kocsi values(" + kid + ", "
						+ szemelyzetszama.getText() + ", '" + tipus.getText()
						+ "', " + loero.getText() + ", "
						+ felszereltsegiszint.getText() + ", "
						+ gyartasiev.getText() + ")";
				String kikuld_query = "insert into kiküld values(" + hid + ", "
						+ kid + ")";

				Abkezel.UjAdat(hivasok_query);
				BC.showMD("Hívások felvíve", 0);
				Abkezel.UjAdat(betegek_query);
				BC.showMD("Betegek felvíve", 0);
				Abkezel.UjAdat(kocsi_query);
				BC.showMD("Kocsi felvíve", 0);
				Abkezel.UjAdat(kikuld_query);
				BC.showMD("Kiküld felvíve", 0);
			}
		});
		btnFelvisz.setBounds(167, 239, 91, 23);
		contentPanel.add(btnFelvisz);
	}
}
