import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ShiftOr {

	private String text;
	private String pattern;

	private int[] b;
	private int lim;

	private int patternLen;

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

	public int[] ShiftOr_algo() {
		int maxnum = this.text.length() / this.pattern.length();
		int[] result = new int[maxnum];
		result[0] = 0;

		ByteBuffer buffer = ByteBuffer.wrap(this.text.getBytes());

		int bufferLimit = buffer.limit();
		int state = ~0;
		for (int pos = buffer.position(); pos < bufferLimit; pos++) {
			state <<= 1;
			state |= this.b[(buffer.get(pos)) & 0xFF];
			if (state < this.lim) {
				result[0]++;
				result[result[0]] = pos - this.patternLen + 1;
			}
		}

		return result;
	}

	public final List<Integer> matchAll(final ByteBuffer buffer) {
		final List<Integer> matches = new ArrayList<Integer>();

		int maxnum = this.text.length() / this.pattern.length();
		int[] result = new int[maxnum];
		result[0] = 0;

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
		int[] result = this.ShiftOr_algo();
		System.out.println("-------------------------------------");
		System.out.println("ShiftOr result:");
		if (result[0] > 0) {
			System.out.println("Total match times " + result[0]);
			for (int i = 1; i < result[0] + 1; i++) {
				System.out.println("position of the " + i + "eme match : "
						+ result[i]);
			}
		} else {

			System.out.println("There is no matcher!");

		}

	}
}
