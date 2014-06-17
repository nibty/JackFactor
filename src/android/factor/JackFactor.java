package android.factor;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 *  Calculates all prime factors of an integer and the closest multiples 
 *  @author Nicholas Pettas
 *
 */
public class JackFactor
{
	private final static String MAX_RANDOM = "99999999999999999";
    private final static String MAX_TRIAL_BY_DIVISION = "0";
	private List<BigInteger> multipiers = new LinkedList<BigInteger>();
    private List<BigInteger> factors = new LinkedList<BigInteger>();
	private List<BigInteger> primaryMultiplier = new LinkedList<BigInteger>();
	private List<BigInteger> SecondaryMultiplier = new LinkedList<BigInteger>();
	private List<BigInteger> allFactors = new LinkedList<BigInteger>();
	private List<BigInteger> diffs = new LinkedList<BigInteger>();
	private BigInteger number;
	private BigInteger lowestMultiplier;
	private BigInteger lowestOtherMultiplier;
	private BigInteger lowestDiff;
	private String factorAlgorithm;
	private boolean factored = false;
	private long time;
	
	/**
	 *  Construct a jackFactor with a random number
	 */
    public JackFactor()
    {
    	number = new BigInteger(MAX_RANDOM);
    	number = nextRandomBigInteger(number);
    }
    
	/**
	 *   Construct a jackFactor with a specified number from a string
	 *   @param setNumber the number
	 */
    public JackFactor(String setNumber)
    {
    	number = new BigInteger(setNumber);
    }
    
	/**
	 *   Construct a jackFactor with a specified number from a BigInteger
	 *   @param setNumber the number
	 */
    public JackFactor(BigInteger setNumber)
    {
    	number = setNumber;
    }
    
	/**
	 *   Construct a jackFactor with a specified number from a int
	 *   @param setNumber the number
	 */
    public JackFactor(int setNumber)
    {
    	String stringVar = Integer.toString(setNumber);
    	number = new BigInteger(stringVar);
    }
    
    /**
     *  Get run time 
     *  @return the run time in ms
     */
    public long getTime()
    {
        return time;
    }
    
    /**
     *  Get the lowest difference value
     *  @return lowest difference value
     */
    public BigInteger getLowestDiff()
    {
    	return lowestDiff;
    }
    
    /**
     *  Gets the first Multiplier
     *  @return the first Multiplier
     */
    public BigInteger getMultiplier()
    {
    	return lowestMultiplier;
    }
    
    /**
     *  Gets the second multiplier
     *  @return the second multiplier
     */
    public BigInteger getOtherMultiplier()
    {
    	return lowestOtherMultiplier;
    }
    
    /**
     *  Gets number to be factored
     *  @return the number
     */
    public BigInteger getNumber()
    {
    	return number;
    }
    
    /**
     *  Get list of prime factors 
     *  @return list of factors
     */
    public List<BigInteger> getFactors()
    {
    	return factors;
    }
    
    /**
     *  Get the prime factor algorithm name
     *  @return name of the prime factor algorithm
     */
    public String getFactorAlgorithm()
    {
    	return factorAlgorithm;
    }
    
    /**
     *  Calculates the factors and the closest multiples. Sets run time.
     */
    public void calculate()
    {       
    	//  Clear previous factor
    	clear();
    	
    	//  Get start time
    	long startTime = System.currentTimeMillis();

    	//  Check if prime with MillerRabin & LucasLehmer
    	if (number.isProbablePrime(3))
    	{
			factored = true;
			factors.add(number);
			factorAlgorithm = "MillerRabin & LucasLehmer";
    	}

	    //  Factor by TrialByDivision for small numbers
    	if (number.compareTo(new BigInteger(MAX_TRIAL_BY_DIVISION)) <= 0)
    	{
    		factored = true;
    		Long longNumber = number.longValue();
    		factors = TrialByDivision.primeFactors(longNumber);
    		factorAlgorithm = "TrialByDivision";    
        }
    	
    	//  Use PollardRho for bigger numbers and failed trialByFactor
    	if (factored == false)
    	{
			Pollard p = new Pollard(System.nanoTime());
			p.factor(number,factors);
		    factorAlgorithm = "PollardRho";	
    	}
    	
        //  Get combinations
    	if (factors.size() > 1)
    	{
            allFactors = AllFactors.getAllFactors(factors);
    	}
    	else 
    	{
    		allFactors.addAll(factors);
    	}
        
        //  Get two closest factors
        getClosestMultiples();

        //  Set run time
        long endTime = System.currentTimeMillis();
        time = endTime - startTime;
    }
    
    /**
     *  Clears all lists
     */
    private void clear()
    {
        multipiers.clear();
    	factors.clear();
    	primaryMultiplier.clear();
    	SecondaryMultiplier.clear();
    	diffs.clear();
    }
    
    /**
     *  Find closest multiples from factors
     *  @param combos the list of all possible combos of the factors
     */
    private void getClosestMultiples()
    {
    	int count = allFactors.size();
    	int lowestDiffIndex = (count / 2);
    	
    	if (count > 2)
    	{  
        	lowestMultiplier = allFactors.get(lowestDiffIndex);
    	}
    	else 
    	{
        	lowestMultiplier = allFactors.get(0);
    	}
    	
	    lowestOtherMultiplier =  number.divide(lowestMultiplier);
	    lowestDiff = lowestMultiplier.subtract(lowestOtherMultiplier).abs();
    }
    
    /**
     *  Create random biginteger between 0 and n
     *  @param n the maximum biginteger
     *  @return the random biginteger
     */
    private BigInteger nextRandomBigInteger(BigInteger n) 
    {
        Random rand = new Random();
        BigInteger result = new BigInteger(n.bitLength(), rand);
        while( result.compareTo(n) >= 0 ) 
        {
            result = new BigInteger(n.bitLength(), rand);
        }
        
        return result;
    }
}
