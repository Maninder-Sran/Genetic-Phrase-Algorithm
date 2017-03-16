import java.util.ArrayList;

public class Population {
	
	private String target;
	private double mutationRate;
	private DNA[] population;
	private ArrayList<DNA> matingPool;
	private int generation;
	public double bestScore;
	public double perfectScore;
	private boolean finished;
	
	public Population(String target, int populationSize, double mutationRate){
		this.target = target;
		this.mutationRate = mutationRate;
		population = new DNA[populationSize];
		matingPool = new ArrayList<DNA>();
		generation = 1;
		bestScore = 0;
		perfectScore = 1.0;
		finished = false;
		for(int i = 0; i < populationSize; i++){
			population[i] = new DNA(target.length());
		}
		calcFitness();
	}
	public void calcFitness(){
		for(int i = 0; i < population.length; i++){
			population[i].fitness(target);
		}
	}
	public void naturalSelection(){
		matingPool.clear();
		
		for(int i = 0; i < population.length; i++){
			int n = (int)(population[i].fitness * 100);
			for(int j = 0; j < n; j++){	
				matingPool.add(population[i]);
			}
		}
	}
	public void generate(){
		for(int i = 0; i < population.length; i++){
			int a = (int)(Math.random() * matingPool.size());
			int b = (int)(Math.random() * matingPool.size());
			a = (a == matingPool.size()) ? a - 1 : a;
			b = (b == matingPool.size()) ? b - 1 : b;
			DNA partnerA = matingPool.get(a);
			DNA partnerB = matingPool.get(b);
			DNA child = partnerA.crossOver(partnerB);
			child.mutate(mutationRate);
			population[i] = child;
		}
		generation++;
	}
	public String getBest(){ 
		int index = 0;
		for(int i = 0; i < population.length; i++){
			if(population[i].fitness > bestScore){
				bestScore = population[i].fitness;
				index = i;
			}
			if(target.equals(population[i].getPhrase())){
				finished = true;
			}
		}
		return "Phrase: "+population[index].getPhrase()+"\t\tScore: "+bestScore+"\n"; 
	}
	public boolean isEvolved(){	return finished; }
	public int getGeneration(){	return generation; }
	public String getAllPhrases(){
		StringBuilder sb = new StringBuilder(population.length);
		for(int i = 0; i < population.length; i++){
			sb.append(population[i].getPhrase()+"\n");
		}
		return sb.toString();
	}
}
