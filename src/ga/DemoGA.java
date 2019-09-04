/*
-this is an example implementation of a genetic algorithm
-given is a set of 5 genes, each with values 0 and 1
-fitness value is calculated as the number of 1s
-the algorithm try to maximise the fitness function to provide 
a population consisting of individuals with five 1s
*/

package ga;

import java.util.Random;

public class DemoGA {
	
	
	Population population = new Population();
	Individual fittest;
	Individual secondFittest;
	int generationCount = 0;

	
	
	public static void main(String[] args) {
		
		
		Random rn = new Random();
		DemoGA demo = new DemoGA();
		demo.population.initializePopulation(10);
		demo.population.calculateIndivFitness();
		
		System.out.println("Generation: " + demo.generationCount + " Fittest: " + demo.population.fittest);
		
		while (demo.population.fittest < 5) {
			++demo.generationCount;
			demo.selection();
			demo.crossover();
			if (rn.nextInt() % 7 < 5) {
				demo.mutation();
			}
			
			demo.addFittestOffSpring();
			
			demo.population.calculateIndivFitness();
			
			System.out.println("Generation: " + demo.generationCount + " Fittest: " + demo.population.fittest);

		}
		
		System.out.println("\nSolution found in generation: " + demo.generationCount);
		
		System.out.println("Fitness: " + demo.population.getFittest().fitness);
		
		System.out.println("Genes: ");
		
		for (int i = 0; i < 5; i++) {
			System.out.println(demo.population.getFittest().genes[i]);
		}
		
		
		System.out.println("");
	}
	

	//replace least fittest individual from most fittest offsppring
	
	private void addFittestOffSpring() {
		
		fittest.calculateFitness();
		
		secondFittest.calculateFitness();
		
		int leastFittestIndex = population.getlastFittestIndex();

		//replacement here
		population.individuals[leastFittestIndex] = getFittestOffSpring();

	}

	
	private Individual getFittestOffSpring() {
		
		if (fittest.fitness > secondFittest.fitness) {
			return fittest;
		}
		
		return secondFittest;
	}

	private void mutation() {
		
		Random rn = new Random();
		
		int mutationPoint = rn.nextInt(population.individuals[0].genes.length);
		
		if (secondFittest.genes[mutationPoint] == 0) {
			secondFittest.genes[mutationPoint] = 1;
		}
		
		else {
			secondFittest.genes[mutationPoint] = 0;
		}

	}

	private void crossover() {
		
		Random rn = new Random();
		
		int crossOverPoint = rn.nextInt(population.individuals[0].genes.length);
		
		for (int i = 0; i < crossOverPoint; i++) {
			
			int temp = fittest.genes[i];
			fittest.genes[i] = secondFittest.genes[i];
			secondFittest.genes[i] = temp;
		}
	}

	private void selection() {
		
		fittest = population.getFittest();
		secondFittest = population.getSecondFitetst();

	}
}

class Individual {
	
	int fitness = 0;
	int[] genes = new int[5];

	public Individual() {
		
		Random rn = new Random();
		
		for (int i = 0; i < genes.length; i++) {
			
			genes[i] = Math.abs(rn.nextInt() % 2);
		}
		fitness = 0;
	}

	// calculate fitness
	
	public void calculateFitness() {
		
		fitness = 0;
		for (int i = 0; i < genes.length; i++) {
			if (genes[i] == 1)
				++fitness;
		}
	}
}

class Population {
	
	int popSize = 10;
	
	Individual[] individuals = new Individual[10];
	
	int fittest = 0;

	// initialize population
	public void initializePopulation(int size) {

		for (int i = 0; i < individuals.length; i++) {
			individuals[i] = new Individual();
		}
	}

	// get fittest individual
	public Individual getFittest() {
		
		int maxFit = Integer.MIN_VALUE;
		int maxFitIndex = 0;
		
		for (int i = 0; i < individuals.length; i++) {
			
			if (maxFit <= individuals[i].fitness) {
				maxFit = individuals[i].fitness;
				maxFitIndex = i;
			}
		}
		
		fittest = individuals[maxFitIndex].fitness;
		
		return individuals[maxFitIndex];
	}

	// get second fittest individual
	public Individual getSecondFitetst() {
		
		int maxFit1 = 0;
		int maxFit2 = 0;
		
		for (int i = 0; i < individuals.length; i++) {
			
			if (individuals[i].fitness > individuals[maxFit1].fitness) {
				maxFit2 = maxFit1;
				maxFit1 = i;
			} else if (individuals[i].fitness > individuals[maxFit2].fitness) {
				maxFit2 = i;
			}
		}
		
		return individuals[maxFit2];
	}

	// get index of least fittest individual
	public int getlastFittestIndex() {
		
		int minFitVal = Integer.MAX_VALUE;
		int minFitIndex = 0;
		
		for (int i = 0; i < individuals.length; i++) {
			if (minFitVal < individuals[i].fitness) {
				minFitVal = individuals[i].fitness;
				minFitIndex = i;
			}
		}
		
		return minFitIndex;
	}

	// calculate fitness for individuals
	public void calculateIndivFitness() {
		
		for (int i = 0; i < individuals.length; i++) {
			individuals[i].calculateFitness();
		}
		getFittest();
	}
}
