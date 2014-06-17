package android.factor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;

public class Pollard
{
	public static BigInteger constants[]= new BigInteger[]{BigInteger.ONE, new BigInteger("2"), new BigInteger("3"), new BigInteger("5"), new BigInteger("7"), new BigInteger("11"), new BigInteger("13")};
	long start_time;
	
	public Pollard(long start_time)
	{
		this.start_time = start_time;
	}
	
	public void factor(BigInteger n, List<BigInteger> factorList)
	{		
			if(n.isProbablePrime(5))
			{
				factorList.add(n);
			}
			else
			{
				BigInteger f1;
				BigInteger f2;
				
				f1 = pollard(n);
				//f1 = brent(n);
				
				if(f1.equals(BigInteger.ONE))
				{ 
					BigInteger ans[] = specialCase(n);
					
					if(ans == null)
					{
						System.out.println("fail");
					}
					else
					{
						BigInteger i = BigInteger.ZERO;
						while(i.compareTo(ans[1]) < 0)
						{
							factor(ans[0], factorList);
							i = i.add(BigInteger.ONE);
						}
					}
				}
				else
				{
					f2 = n.divide(f1);
					factor(f1, factorList);
					factor(f2, factorList);
				}
			}
	}
	
	public BigInteger brent(BigInteger n)
	{
		for(int test=0;test< constants.length;test++)
		{
			Random rnd = new Random();
			BigInteger two = new BigInteger("2");
			BigInteger y = new BigInteger(n.bitLength(), rnd);
			BigInteger c = constants[test];
			BigInteger m = new BigInteger(n.bitLength(), rnd);
			
			BigInteger g = BigInteger.ONE;
			BigInteger r = BigInteger.ONE;
			BigInteger q = BigInteger.ONE;
			BigInteger x = y;
			BigInteger ys = y;
			
			if(n.mod(two).equals(BigInteger.ZERO)) return two;
			else
			{
				while(g.equals(BigInteger.ONE))
				{
					x = y;
					for(BigInteger i = BigInteger.ZERO; i.compareTo(r) < 0; i = i.add(BigInteger.ONE))
					{
						//y = f(y, c, n);
						y = y.multiply(y).add(c).mod(n);
					}
					BigInteger k = BigInteger.ZERO;
					while(k.compareTo(r) < 0 && g.equals(BigInteger.ONE))
					{
						ys = y;
						for(BigInteger i = BigInteger.ZERO; i.compareTo(m.min(r.subtract(k))) < 0; i = i.add(BigInteger.ONE))
						{
							//y = f(y, c, n);
							y = y.multiply(y).add(c).mod(n);
							q = q.multiply(x.subtract(y).abs()).mod(n);
						}
						g = q.gcd(n);
						k = k.add(m);
					}
					r = r.multiply(two);
				}	
			}
			if(g.compareTo(n) != 0)
				return g;
		}
		return BigInteger.ONE;
	}
	
	public BigInteger pollard(BigInteger n)
	{
		for(int i=0;i< constants.length;i++)
		{
			BigInteger x = new BigInteger("2");
			BigInteger y = new BigInteger("2");

			BigInteger d = BigInteger.ONE;
			
			while(d.equals(BigInteger.ONE))
			{
				//x = f(x, n, c);
				//y = f(f(y, n, c), n, c);
				x = f(x, n, constants[i]);
				y = f(f(y, n, constants[i]), n, constants[i]);
				d = x.subtract(y).gcd(n);
			}
			//if(d != n) return d;
			if(d.compareTo(n) != 0) return d;
		}
		return BigInteger.ONE;
	}
	
	private BigInteger f(BigInteger x, BigInteger n, BigInteger c)
	{
		return x.multiply(x).add(c).mod(n);
	}
	
	
	/*
	 * Testar om n = a^k, ger isf ut {a,k} , annars null
	 * 
	 */
	private BigInteger[] specialCase(BigInteger n)
	{
		for(int k= 2; k< 1000; k++)
		{
			if(new BigInteger("2").pow(k).compareTo(n) > 0)
			{
				break;
			}
			BigInteger temp = newtonRaphson(n, k, new BigInteger("100"));
			if(temp != null)
			{
				return new BigInteger[]{temp, new BigInteger(Integer.toString(k))};
			}
		}
		return null;
	}
	
	private BigInteger newtonRaphson(BigInteger n, int k, BigInteger start_x)
	{
		BigDecimal n_dec = new BigDecimal(n);
		BigDecimal old_x = new BigDecimal(start_x);
		BigDecimal k_dec = new BigDecimal(Integer.toString(k));
		
		int counter = 0;
		while(true)
		{
			BigDecimal new_x = old_x.subtract(old_x.divide(k_dec, RoundingMode.HALF_EVEN )).add(n_dec.divide(k_dec.multiply(old_x.pow(k-1)), RoundingMode.HALF_EVEN));
		
			if(new_x.toBigInteger().pow(k).compareTo(n) == 0)
			{
				return new_x.toBigInteger();
			}
			else if(counter > 1000){
				return null;
			}
			old_x = new_x.setScale(5, RoundingMode.DOWN);
			counter++;
		}
		
	}
	
}