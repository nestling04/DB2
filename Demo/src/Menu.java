import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.Color;

import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.Date;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Menu extends JFrame {

	private JPanel contentPane;
	private JTextField outname;

	private File fin;
	private JTextField betegkora;
	private JTextField kocsitipusa;
	private JTextField nap;

	/**
	 * Create the frame.
	 */
	public Menu() {
		setTitle("Men\u00FC");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.CYAN);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnjAdat = new JButton("\u00DAj adat");
		btnjAdat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UjAdat ua = new UjAdat(Menu.this);
				ua.setVisible(true);
			}
		});
		btnjAdat.setBounds(10, 11, 91, 23);
		contentPane.add(btnjAdat);

		JButton btnMentstxtbe = new JButton("Ment\u00E9s .txt-be");
		btnMentstxtbe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FileDialog fd = new FileDialog(new Frame(), "", FileDialog.LOAD);
				fd.setFile("*.txt");
				fd.setVisible(true);
				if (fd.getFile() != null) {
					fin = new File(fd.getDirectory(), fd.getFile());
					String outfname = fin.getName();
					outname.setText(outfname);
					FileManager.TxTWriter(fin);
				}
			}
		});
		btnMentstxtbe.setBounds(10, 45, 137, 23);
		contentPane.add(btnMentstxtbe);

		JButton btnLekrdezs = new JButton("List\u00E1z\u00E1s");
		btnLekrdezs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String eredmeny = Abkezel.Lekerdez().toString();
				BC.showMD(eredmeny, 1);
			}
		});
		btnLekrdezs.setBounds(10, 79, 91, 23);
		contentPane.add(btnLekrdezs);

		outname = new JTextField();
		outname.setHorizontalAlignment(SwingConstants.RIGHT);
		outname.setEditable(false);
		outname.setBounds(157, 46, 275, 20);
		contentPane.add(outname);
		outname.setColumns(10);

		JPanel panel = new JPanel();
		panel.setBounds(10, 113, 422, 86);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblBetegKora = new JLabel("Beteg kora:");
		lblBetegKora.setBounds(53, 14, 68, 14);
		panel.add(lblBetegKora);

		JLabel lblKocsiTpusa = new JLabel("Kocsi t\u00EDpusa:");
		lblKocsiTpusa.setBounds(53, 58, 68, 14);
		panel.add(lblKocsiTpusa);

		betegkora = new JTextField();
		betegkora.setBounds(131, 11, 86, 20);
		panel.add(betegkora);
		betegkora.setColumns(10);

		kocsitipusa = new JTextField();
		kocsitipusa.setColumns(10);
		kocsitipusa.setBounds(131, 55, 86, 20);
		panel.add(kocsitipusa);

		JButton btnKeress = new JButton("Keres\u00E9s");
		btnKeress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String eredmeny = Abkezel.Lekerdez(betegkora.getText(),
						kocsitipusa.getText()).toString();
				BC.showMD(eredmeny, 1);
			}
		});
		btnKeress.setBounds(286, 31, 91, 23);
		panel.add(btnKeress);

		nap = new JTextField();
		nap.setBounds(72, 210, 86, 20);
		contentPane.add(nap);
		nap.setColumns(10);

		JLabel lblDtum = new JLabel("D\u00E1tum:");
		lblDtum.setBounds(10, 213, 46, 14);
		contentPane.add(lblDtum);

		JButton btnHvsokSzmaEzen = new JButton(
				"H\u00EDv\u00E1sok sz\u00E1ma ezen a napon");
		btnHvsokSzmaEzen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Abkezel.Hivasok_szama(nap.getText());
			}
		});
		btnHvsokSzmaEzen.setBounds(168, 210, 264, 23);
		contentPane.add(btnHvsokSzmaEzen);

		JButton btnMetaadatokLekrdezse = new JButton(
				"Metaadatok lek\u00E9rdez\u00E9se");
		btnMetaadatokLekrdezse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Abkezel.getMetaData();
			}
		});
		btnMetaadatokLekrdezse.setBounds(121, 241, 215, 23);
		contentPane.add(btnMetaadatokLekrdezse);
	}
}
