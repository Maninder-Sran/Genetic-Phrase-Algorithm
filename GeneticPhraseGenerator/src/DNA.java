import java.text.DecimalFormat;

public class DNA {
	
	public double fitness;
	public char[] genes;
	private int[] asciiCodes = new int[128];
	public DNA(int geneLength){
		for(int i  = 0; i < asciiCodes.length; i++){
			asciiCodes[i] = i;
		}
		genes = new char[geneLength];
		for(int i = 0; i < geneLength; i++){
			genes[i] = getRandomChar();
		}
	}
	
	private char getRandomChar(){
		int asciiCode = (int)(Math.random()*asciiCodes.length);
		asciiCode = (asciiCode == 128) ? asciiCode - 1 : asciiCode;
		return (char)asciiCodes[asciiCode];
	}
	public String getPhrase(){
		return new String(genes);
	}
	public DNA crossOver(DNA partnerB){
		
		DNA child = new DNA(genes.length);
		
		int midPoint = (int)(Math.random()*genes.length);
		midPoint = (midPoint == genes.length) ?  midPoint-1 : midPoint;
		
		for (int i = 0; i < genes.length; i++) {
			if (i < midPoint) {
				child.genes[i] = genes[i];
			}
			else{child.genes[i] = partnerB.genes[i];}
		}
		return child;
	}
	public void mutate(double mutationRate){
		for (int i = 0; i < genes.length; i++) {
			double probability = Math.random();
			if (probability < mutationRate) {
				genes[i] = getRandomChar();
			}
		}
	}
	public void fitness(String target){
		DecimalFormat df = new DecimalFormat("#.##");
		int numCorrect = 0;
		for(int i = 0; i < target.length();i++){
			if(genes[i] == target.charAt(i)){
				numCorrect++;
			}
		}
		fitness = Double.valueOf(df.format(numCorrect/(float)target.length()));
	}
}
