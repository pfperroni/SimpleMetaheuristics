/*
 * PSO.java
 *
 * Created on: May/01/2018
 * Author: Peter Frank Perroni (pfperroni@gmail.com)
 */

import java.util.Random;

/**
 * @brief Standard PSO metaheuristic algorithm.
 */
public class PSO{
	private double Gb[], gbFit, pb[][], pbFit[], x[][], v[][], fit[], S, w, c1, c2;
	private int p, n, nEvals, maxEvals;
	private long seed;
	private FitnessFunction fitFunc;
	private Random r;

	/**
	 * @brief PSO constructor.
	 * 
	 * @param S the search space boundaries.
	 * @param fitfunc the instance of the fitness function.
	 * @param p the population size.
	 * @param n the number of dimensions of the search space.
	 * @param w the acceleration coefficient.
	 * @param c1 the cognitive weight.
	 * @param c2 the social weight.
	 */
	public PSO(double S, FitnessFunction fitFunc, int p, int n, double w, double c1, double c2) {
		this(null, null, S, fitFunc, p, n, w, c1, c2);
	}

	/**
	 * @brief PSO constructor.
	 * 
	 * Receives an external set of positions and fitness values for PSO initialization.
	 * 
	 * @param x an external set of positions (will be copied internally).
	 * @param fit an external set of fitness (will be copied internally).
	 * @param S the search space boundaries.
	 * @param fitfunc the instance of the fitness function.
	 * @param p the population size.
	 * @param n the number of dimensions of the search space.
	 * @param w the acceleration coefficient.
	 * @param c1 the cognitive weight.
	 * @param c2 the social weight.
	 */
	public PSO(double x[][], double fit[], double S, FitnessFunction fitFunc, int p, int n, double w, double c1, double c2) {
		this.x = x;
		this.fit = fit;
		this.S = S;
		this.fitFunc = fitFunc;
		this.p = p;
		this.n = n;
		this.w = w;
		this.c1 = c1;
		this.c2 = c2;

		Gb = new double[n];
		pb = new double[p][n];
		pbFit = new double[p];
		v = new double[p][n];
		nEvals = 0;
		maxEvals = Integer.MAX_VALUE;

		startupPositions();
	}

	private void startupPositions(){
		seed = System.currentTimeMillis();
		r = new Random(seed);

		gbFit = Double.MAX_VALUE;
		if(x == null) x = new double[p][n];
		if(fit == null) fit = new double[p];
		for(int j, i=0; i < p; i++){
			for(j=0; j < n; j++) x[i][j] = r.nextDouble() * 2 - S;
			pbFit[i] = Double.MAX_VALUE;
		}
		setPositions(x);
	}

	/**
	 * @brief Set the current population to a different location.
	 * 
	 * Velocities are randomly assigned and fitness are recalculated.
	 * 
	 * @param pos the new set of positions.
	 */
	public void setPositions(double pos[][]){
		for(int j, i=0; i < p; i++){
			for(j=0; j < n; j++) x[i][j] = pos[i][j];
		}

		for(int j, i=0; i < p; i++){
			for(j=0; j < n; j++) v[i][j] = r.nextDouble() * S * 0.15;
			fit[i] = fitFunc.evaluate(x[i]);
			nEvals++;
			if(fit[i] < pbFit[i]){
				for(j=0; j < n; j++) pb[i][j] = x[i][j];
				pbFit[i] = fit[i];
				if(pbFit[i] < gbFit){
					for(j=0; j < n; j++) Gb[j] = pb[i][j];
					gbFit = pbFit[i];
				}
			}
		}
	}

	/**
	 * @brief Runs PSO.
	 * 
	 * This method can be called iteratively since internal state is maintained
	 * from previous run.
	 * 
	 * @param maxIt the maximum number of iterations to run the PSO.
	 */
	public void next(int maxIt) {
		for(int i, j, it=0; it < maxIt && nEvals < maxEvals; it++){
			for(i=0; i < p; i++){
				for(j=0; j < n; j++){
					v[i][j] = w * v[i][j] + c1 * r.nextDouble() * (pb[i][j] - x[i][j])
										  + c2 * r.nextDouble() * (Gb[j] - x[i][j]);
					x[i][j] += v[i][j];
				}
				fit[i] = fitFunc.evaluate(x[i]);
				nEvals++;

				if(fit[i] < pbFit[i]){
					for(j=0; j < n; j++) pb[i][j] = x[i][j];
					pbFit[i] = fit[i];
					if(pbFit[i] < gbFit){
						for(j=0; j < n; j++) Gb[j] = pb[i][j];
						gbFit = pbFit[i];
					}
				}
			}
		}
	}
	
	/**
	 * @brief Returns the current global best position found by PSO.
	 */
	public double[] getGBestPosition(){
		return Gb;
	}

	/**
	 * @brief Returns the current global best fitness found by PSO.
	 */
	public double getGBestFitness(){
		return gbFit;
	}

	/**
	 * @brief Returns the current personal best position found by every individual.
	 */
	public double[][] getPBestPositions(){
		return pb;
	}

	/**
	 * @brief Returns the current personal best fitness found by every individual.
	 */
	public double[] getPBestFitness(){
		return pbFit;
	}
	
	/**
	 * @brief Returns the current number of fitness function evaluations.
	 */
	public int getNEvals(){
		return nEvals;
	}
	
	/**
	 * @brief Sets the maximum number of fitness function evaluation allowed for this PSO.
	 * 
	 * Regardless the number of calls to the method next(int maxIt),
	 * the instance will not process more than maxEvals fitness function evaluations.
	 * 
	 * @param maxEvals the maximum number of fitness function evaluation allowed for the instance.
	 */
	public void setMaxEvals(int maxEvals){
		this.maxEvals = maxEvals;
	}
	
	/**
	 * @brief Returns the maximum number of fitness function evaluation allowed for this PSO.
	 */
	public int getMaxEvals(){
		return maxEvals;
	}
}
