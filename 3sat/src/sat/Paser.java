package sat;

import java.io.FileInputStream;
import java.io.IOException;

public class Paser {

	// an variable for all the data
	public static byte[] liste_data;

	Paser() {

	}

	static // new the liste
	void setSize(int size) {
		System.out.println("data size:" + size);
		liste_data = new byte[size];
	}

	static void readFile(String filename) throws IOException {
		FileInputStream fin = new FileInputStream(filename);

		// new the liste
		setSize(fin.available());
		// Read data into the array
		fin.read(liste_data);
		System.out.println((char)liste_data[6]);
		for (int i = 0; i < liste_data.length; i++) {
			System.out.print((char) liste_data[i]);
		}
		//System.out.println();
		//System.out.println("data size:" + liste_data.length);
		fin.close();

	}

	public static void main(String args[]) throws Exception {
		String fname = "003.cnf";
		readFile(fname);
	}

}
