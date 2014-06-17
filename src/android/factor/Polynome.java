package android.factor;

import java.math.BigInteger;


public class Polynome {

	BigInteger[] pol_;
	BigInteger mod_;


	public Polynome(BigInteger[] pol, BigInteger mod){pol_ = pol; mod_ = mod;}

	public BigInteger evaluate(BigInteger x)
	{
		BigInteger sum = new BigInteger("0");
		for(int i = 0; i < pol_.length; i++)
		{
			sum = (sum.add(x.modPow(new BigInteger(""+i), mod_)).multiply(pol_[i]).mod(mod_));
		}

		return sum;
	}
	
	public String toString()
	{
		String ret = "";
		for(int i = 0; i < pol_.length; i++)
		{
			
			if(pol_[i].compareTo(BigInteger.ZERO) != 0)
			{
				if(i != 0)
				{
					ret = " + " + ret;
				ret = "x" + "^" + i + ret;
				if(pol_[i].intValue() != 1)
					ret = pol_[i] + "*" + ret;
				} else
					ret = pol_[i] + "";
				
			}
		}
		return ret;		
	}
}
