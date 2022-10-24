/**
 * Poisson class is a generator of random numbers with a Poisson distribution
 * It is modeled on the Random class with a nextInt() method.
 * 
 * @author  COSC 311, Fall '22
 * @version (10-13-22)
 */

package Simulation;

import java.util.Random;

public class Poisson {
    private double lambda;
    private Random generator;

    /**
     * Creates a new generator of random numbers with Poisson distribution of
     * specified mean
     * @param  mean is the expected value of Poisson distribution
     */
    public Poisson(double mean)
    {
            lambda = mean;
            generator = new Random();
    }

    /**
     * Creates a new generator of random numbers with Poisson distribution of
     * specified mean and uses gen as the source of random numbers
     * @param  mean is the expected value of Poisson distribution
     * @param  gen is the source of random numbers
     */
    public Poisson(double mean, Random gen)
    {
        lambda = mean;
        generator = gen;
    }

    /**
     * Returns a random integer with Poisson distribution
     */
    public int nextInt()
    {
        int x = -1;

        for (double t = 0.0; t <= 1.0; t += -Math.log(generator.nextDouble()) / lambda)
            x++;

        return x;
    }

    /**
     *  Sets the lambda value of Poisson generator to specified mean
     *  @param  mean is the expected value of Poisson distribution
     */
    public void setMean(double mean)
    {
        lambda = mean;
    }

    /**
     * Sets the source of random numbers for the Poisson generator to specified
     * Random generator
     * @param  gen is the source of random numbers
     */
    public void setGenerator(Random gen)
    {
        generator = gen;
    }

    /**
     * Sets the seed of the source of random numbers to the specified value
     * @param  seed is the seed for the random number generator
     */
    public void setSeed(long seed)
    {
        generator.setSeed(seed);
    }
}
