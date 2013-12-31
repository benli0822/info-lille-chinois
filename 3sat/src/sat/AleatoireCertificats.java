package sat;

import java.util.ArrayList;
import java.util.Random;

public class AleatoireCertificats {

	private int n;
	private ArrayList<Boolean> res;

	public AleatoireCertificats(int n) {
		this.n = n;
		this.res = new ArrayList<Boolean>();

	}

	public ArrayList<Boolean> getResultat() {
		
		Random r = new Random();
		
		while(this.res.size() < this.n){
			this.res.add(r.nextBoolean());
		}
		
		System.out.println(this.res);
		
		return this.res;
	}
	
	public static void main(String[] args){
		AleatoireCertificats ac = new AleatoireCertificats(5);
		ac.getResultat();
		
	}

}
