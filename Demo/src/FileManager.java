import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class FileManager {
	public static void TxTWriter(File fin) {
		BufferedWriter writer = null;
		try {

			PrintStream out = new PrintStream(new FileOutputStream(
					fin.getName()));
			List<String> listem = Abkezel.Lekerdez();
			String[] records = listem.toArray(new String[listem.size()]);
			for (int i = 0; i < records.length; i++) {
				out.println("" + records[i]);
			}

			// List<String> listem = Abkezel.Lekerdez();
			// writer = new BufferedWriter(new FileWriter(fin.getName()));
			// writer.write(listem.toString());
			out.close();
			BC.showMD("Data successfully written!", 1);
		} catch (IOException ioe) {
			BC.showMD("TxTWriter: " + ioe.getMessage(), 0);
		} finally {
			try {
				writer.close();
			} catch (IOException ioe) {
				BC.showMD("IOException: " + ioe.getMessage(), 0);
			}
		}
	}
}
