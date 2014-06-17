package android.factor;


import java.math.BigInteger;

public class MathUtils {

  public static boolean isPrimeInt(long j) {
    long k = sqrtLong(j);
    if (j % 2 == 0)
      return false;
    for (long p = 3; p <= k; p += 2)
      if (j % p == 0)
        return false;
    return true;
  }

  public static long legendreSymbol(long n, long p) {
    long count, temp;
    long legendre = 1;
    if (n == 0)
      return 0;
    if (n < 0) {
      n = -n;
      if (p % 4 == 3)
        legendre = -1;
    }
    do {
      count = 0;
      while (n % 2 == 0) {
        n = n / 2;
        count = 1 - count;
      }
      if ((count * (p * p - 1)) % 16 == 8)
        legendre = -legendre;
      if (((n - 1) * (p - 1)) % 8 == 4)
        legendre = -legendre;
      temp = n;
      n = p % n;
      p = temp;
    } while (n > 1);
    return legendre;
  }

  public static long modPowLong(long n, long p, long m) {
    if (p == 0)
      return 1;
    if (p % 2 == 1)
      return (n * modPowLong(n, p - 1, m)) % m;
    else {
      long result = modPowLong(n, p / 2, m);
      return (result * result) % m;
    }
  }

  public static long sqrtLong(long n) {
    long low = 1;
    long high = n;
    long medium, square;
    do {
      medium = (high + low) / 2;
      square = medium * medium;
      if (square < n) low = medium;
      if (square > n) high = medium;
      if (square == n) return medium;
    } while (high > low + 1);
    if (high * high == n)
      return high;
    else
      return low;
  }

  public static BigInteger sqrtBigInt(BigInteger i) {
    long c;
    BigInteger medium;
    BigInteger high = new BigInteger(i.toString());
    BigInteger low = BigInteger.ONE;
    while (high.subtract(low).compareTo(BigInteger.ONE) > 0) {
      medium = high.add(low).divide(BigInteger.ONE.add(BigInteger.ONE));
      c = medium.multiply(medium).compareTo(i);
      if (c > 0) high = medium;
      if (c < 0) low = medium;
      if (c == 0)
        return medium;
    }
    if (high.multiply(high).compareTo(i) == 0)
      return high;
    else
      return low;
  }


  public static long v(long i, long h, long n, long p) {
    if (i == 1)
      return h;
    if (i == 2)
      return (h * h - 2 * n) % p;
    long vi = v(i/2, h, n, p);
    long vi_1 = v(i/2 + 1, h, n, p);
    if (i % 2 == 1) {
      vi = (vi * vi_1 - h * modPowLong(n, i/2, p)) % p;
      if (vi < 0)
        vi += p;
      return vi;
    }
    else
      return (vi * vi - 2 * modPowLong(n, i/2, p)) % p;
  }

  public static long v_(long j, long h, long n, long p) {
    long b[] = new long[64];
    long m = n;
    long v = h;
    long w = (h * h - 2 * m) % p;
    long x;
    int k, t;
    t = 0;
    while (j > 0) {
      b[++t] = j % 2;
      j /= 2;
    }
    for (k = t - 1; k >= 1; k--) {
      x = (v * w - h * m) % p;
      v = (v * v - 2 * m) % p;
      w = (w * w - 2 * n * m) % p;
      m = m * m % p;
      if (b[k] == 0)
        w = x;
      else {
        v = x;
        m = n * m % p;
      }
    }
    return v;
  }




}