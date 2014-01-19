import java.util.ArrayList;


public class KMP {
	  	private String text;  
	    private String pattern;  
	      
	    KMP() {  
	          
	    }  
	      
	    KMP(String text, String pattern) {  
	        this.text = text;  
	        this.pattern = pattern;  
	    }  
	    
	    KMP(ArrayList<String> motif, ArrayList<String> text){
	    	this.text = "";
	    	for(String line: text){
	    		this.text += line;
	    	}
	    	this.pattern = "";
	    	for(String pa : motif){
	    		this.pattern += pa;
	    		
	    	}
	    	
	    }
	      
	    public void setText(String text) {  
	        this.text = text;  
	    }  
	      
	    public void setPattern(String pattern) {  
	        this.pattern = pattern;  
	    }  
	      
	    public void KMPMatcher() {  
	        int n = text.length();  
	        int m = pattern.length();  
	        
	        int prefix[] = computePrefix();  
	        int q = 0;  
	          
	        int count = 0;  
	        for(int i = 0; i < n; i++) {  
	              
	            while(q > 0 && pattern.charAt(q)!= text.charAt(i)) {  
	                q = prefix[q -1];  
	            }  
	              
	            if(pattern.charAt(q) == text.charAt(i))  
	                q++;  
	              
	            if(q == m) {  
	                System.out.println("occurs at position "+ (i-m+1) + " and Pattern occurs with shift  " + ++count + " times");  
	                q = prefix[q - 1];  
	            }  
	        }  
	          
	        if(count == 0) {  
	            System.out.println("There is no matcher!");  
	        }  
	    }  
	      
	    private int[] computePrefix() {  
	        int length = pattern.length();  
	        int[] prefix = new int[length];  
	          
	        prefix[0] = 0;  
	          
	        int k = 0;  
	        for(int i = 1; i < length; i++) {  
	            while(k > 0 && pattern.charAt(k) != pattern.charAt(i)) {  
	                k = prefix[k -1];   
	            }  
	            if(pattern.charAt(k) == pattern.charAt(i))  
	                k++;  
	            prefix[i] = k;  
	        }  
	          
	        return prefix;  
	    }  
	      
	  /*  public static void main(String[] args) {  
	          
	        KMP kmp;  
	          
	        if(args.length == 2) {  
	            kmp = new KMP(args[0] , args[1]);  
	        }  
	        else {  
	            kmp = new KMP();  
	            kmp.setText("ababacabacbababababacabcbabababaca");  
	            kmp.setPattern("ababaca");  
	        }  
	          
	        kmp.KMPMatcher();  
	          
	    }  */
}
