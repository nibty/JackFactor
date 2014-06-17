package android.factor;



import java.math.BigInteger;
import java.util.*;

/**
 * Generates all factors from prime factors
 * 
 * @author Nicholas Pettas
 * 
 */

public class AllFactors
{
	public static List<BigInteger> getAllFactors(List<BigInteger> primeFactors) 
	{
	    Set<BigInteger> distinctFactors = new HashSet<BigInteger>();
	    for (int maxDepth = 0; maxDepth < primeFactors.size(); maxDepth++) 
	    {
	        permutatPrimeFactors(0, maxDepth, 0, new BigInteger("1"), primeFactors, distinctFactors);
	    }
	    
	    List<BigInteger> result = new ArrayList<BigInteger>(distinctFactors);
	    Collections.sort(result);
	    
	    return result;
	}

	private static void permutatPrimeFactors(int depth, int maxDepth, int minIndex, BigInteger valueSoFar,
			List<BigInteger> primeFactors, Set<BigInteger> distinctFactors) 
	{
	    if (depth == maxDepth && valueSoFar.compareTo(new BigInteger("1")) > 0) 
	    { 
	        distinctFactors.add(valueSoFar);
	        return;
	    }

	    for (int index = minIndex; index < primeFactors.size(); index++) 
	    {
	        permutatPrimeFactors(depth + 1, maxDepth, index + 1, valueSoFar.multiply(primeFactors.get(index)),
	        		primeFactors, distinctFactors);
	    }
	}
}