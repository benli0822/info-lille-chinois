import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ShiftOr {
	/**
	 * @author CHENG Xiaojun, JIN Benli et ZHAO Xuening 
	 * methode Shift-Or
	 */
	private String text;
	private String pattern;

	private int[] b;
	private int lim;

	private int patternLen;
	
	private ArrayList<Integer> result_liste;

	public ShiftOr(ArrayList<String> motif, ArrayList<String> text) {
		this.text = "";
		for (String line : text) {
			this.text += line;
		}
		this.pattern = "";
		for (String pa : motif) {
			this.pattern += pa;

		}
		ByteBuffer pat = ByteBuffer.wrap(this.pattern.getBytes());
		this.patternLen = pat.remaining();
		this.preprocess(pat);
		
		this.result_liste = new ArrayList<Integer>();

	}

	private void preprocess(final ByteBuffer pat) {
		this.b = new int[256];
		this.lim = 0;
		for (int i = 0; i < 256; i++) {
			this.b[i] = ~0;

		}
		for (int i = 0, j = 1; i < this.patternLen; i++, j <<= 1) {
			this.b[(pat.get(i)) & 0xFF] &= ~j;
			this.lim |= j;
		}
		this.lim = ~(this.lim >> 1);

	}

	public void ShiftOr_algo() {
		int maxnum = this.text.length() / this.pattern.length();
		System.out.println("Max result: "+maxnum);
		
		ByteBuffer buffer = ByteBuffer.wrap(this.text.getBytes());

		int bufferLimit = buffer.limit();
		int state = ~0;
		for (int pos = buffer.position(); pos < bufferLimit; pos++) {
			state <<= 1;
			state |= this.b[(buffer.get(pos)) & 0xFF];
			if (state < this.lim) {
				
				this.result_liste.add(pos - this.patternLen + 1);
			}
		}

		
	}

	public final List<Integer> matchAll(final ByteBuffer buffer) {
		final List<Integer> matches = new ArrayList<Integer>();

		

		final int bufferLimit = buffer.limit();
		int state = ~0;
		for (int pos = buffer.position(); pos < bufferLimit; pos++) {
			state <<= 1;
			state |= this.b[(buffer.get(pos)) & 0xFF];
			if (state < this.lim) {
				matches.add(pos - this.patternLen + 1);
			}
		}
		return matches;
	}
	
	public void ShiftOrMatcher() {
		this.ShiftOr_algo();
		System.out.println("-------------------------------------");
		System.out.println("ShiftOr result:");
		if (this.result_liste.size() > 0) {
			System.out.println("Total match times " + this.result_liste.size());
			for (int i = 1; i <100; i++) {
				System.out.println("position of the " + i + "eme match : "
						+ this.result_liste.get(i));
			}
		} else {

			System.out.println("There is no matcher!");

		}

	}
}
