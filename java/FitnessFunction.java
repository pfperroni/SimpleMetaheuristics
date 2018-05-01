/*
 * FitnessFunction.java
 *
 * Created on: May/01/2018
 * Author: Peter Frank Perroni (pfperroni@gmail.com)
 */

/**
 * @brief Abstracct class for a fitness function.
 * 
 * Requires a shift vector.
 */
public abstract class FitnessFunction {
	double shift[];
	
	/**
	 * @brief Generic constructor.
	 * 
	 * @param shift the shift vector.
	 */
	FitnessFunction(double shift[]){
		this.shift = shift;
	}

    /**
     * @brief Evalute the positions.
     * 
     * The method must shift the positions according to the shift vector.
     * 
     * @param pos the positions to be evaluated.
     * @return the fitness or cost.
     */
    abstract double evaluate(double pos[]);
}
