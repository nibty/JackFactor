package android.factor;



/*************************************************************************
 *  Compilation:  javac PollardRho.java
 *  Execution:    java PollardRho N
 *  
 *  Factor N using the Pollard-Rho method.
 *
 *************************************************************************/

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
    
public class PollardRho
{
    private final static BigInteger ZERO = new BigInteger("0");
    private final static BigInteger ONE  = new BigInteger("1");
    private final static BigInteger TWO  = new BigInteger("2");
    private final static SecureRandom random = new SecureRandom();

    /**
     *  The Pollard Rho Algorithm
     *  @param the integer
     *  @return the divisor
     */
    public static BigInteger rho(BigInteger N)
    {
        BigInteger divisor;
        BigInteger c  = new BigInteger(N.bitLength(), random);
        BigInteger x  = new BigInteger(N.bitLength(), random);
        BigInteger xx = x;

        // check divisibility by 2
        if (N.mod(TWO).compareTo(ZERO) == 0) return TWO;

        do 
        {
            x  =  x.multiply(x).mod(N).add(c).mod(N);
            xx = xx.multiply(xx).mod(N).add(c).mod(N);
            xx = xx.multiply(xx).mod(N).add(c).mod(N);
            divisor = x.subtract(xx).gcd(N);
        } while((divisor.compareTo(ONE)) == 0);

        return divisor;
    }

    /**
     *  Factor the integer recursively and place results in factor list
     *  @param <E>
     *  @parm the integer
     */
    public static <E> void factor(BigInteger N, List<BigInteger> factorList) 
    {
        if (N.compareTo(ONE) == 0) return;
        
        if (N.isProbablePrime(1)) 
        {
        	//  Place value in factor list
        	factorList.add(N);
        	return;
        }
        
        //  Recursion 
        BigInteger divisor = rho(N);
        factor(divisor, factorList);
        factor(N.divide(divisor), factorList);
    }
}
