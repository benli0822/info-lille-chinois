package simulateur;

import java.util.Random;

/**
 * @author CHENG Xiaojun, JIN Benli et ZHAO Xuening main test class of
 *         simulateur
 */
public class SimulateurMain {

	/**
	 * @param args
	 */
	public int getRandomNumber(int max, int min) {
		
		return (int) Math.round(Math.random()*(max-min)+min);  
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		SimulateurMain test = new SimulateurMain();
		
	}

}
