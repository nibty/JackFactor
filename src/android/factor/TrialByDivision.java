package android.factor;



import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TrialByDivision
{
	private static final int startingPrimes[] =
	{
		      2,
		      3,
		      5,
		      7,
		      13,
		      11,
		      31,
		      61,
		      19,
		      37,
		      181,
		      29,
		      43,
		      71,
		      127,
		      211,
		      421,
		      631,
		      41,
		      73,
		      281,
		      2521,
		      17,
		      113,
		      241,
		      337,
		      1009,
		      109,
		      271,
		      379,
		      433,
		      541,
		      757,
		      2161,
		      7561,
		      15121,
		      23,
		      67,
		      89,
		      199,
		      331,
		      397,
		      463,
		      617,
		      661,
		      881,
		      991,
		      1321,
		      2311,
		      2377,
		      2971,
		      3697,
		      4159,
		      4621,
		      8317,
		      9241,
		      16633,
		      18481,
		      23761,
		      101,
		      151,
		      401,
		      601,
		      701,
		      1051,
		      1201,
		      1801,
		      2801,
		      3301,
		      3851,
		      4201,
		      4951,
		      6301,
		      9901,
		      11551,
		      12601,
		      14851,
		      15401,
		      19801,
		      97,
		      353,
		      673,
		      2017,
		      3169,
		      3361,
		      5281,
		      7393,
		      21601,
		      30241,
		      53,
		      79,
		      131,
		      157,
		      313,
		      521,
		      547,
		      859,
		      911,
		      937,
		      1093,
		      1171,
		      1249,
		      1301,
		      1873,
		      1951,
		      2003,
		      2081,
		      41,
		      2731,
		      2861,
		      3121,
		      3433,
		      3511,
		      5851,
		      6007,
		      6553,
		      7151,
		      7723,
		      8009,
		      8191,
		      8581,
		      8737,
		      9829,
		      11701,
		      13729,
		      14561,
		      15601,
		      16381,
		      17551,
		      20021,
		      20593,
		      21841,
		      24571,
		      25741,
		      26209,
		      28081 };

	public static List<BigInteger> primeFactors(long n)
	{
		List<BigInteger> factors = new ArrayList<BigInteger>();

		double sqrt = Math.sqrt(n);
		
		for (int prime : startingPrimes)
		{
			while (n % prime == 0)
	    	{
		    	factors.add(new BigInteger(Long.toString(prime)));
		    	n /= prime;
		    	sqrt = Math.sqrt(n);
		    }
		}
		
		for (int i = 30241; i <= sqrt; i=i+2) 
		{
			if (i % 5 != 0)
			{
			    while (n % i == 0)
		    	{
			    	//System.out.println(i);
			    	factors.add(new BigInteger(Long.toString(i)));
			    	n /= i;
			    	sqrt = Math.sqrt(n);
			    }
			}
		}
		
		if (n > 1)
		{
			factors.add(new BigInteger(Long.toString(n)));
		}
		
		return factors;
	}

}
