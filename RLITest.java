// CS 0445 Spring 2022
// Test program for your ReallyLongInt class -- for full credit you CANNOT MODIFY
// this code in ANY WAY.

// This program should execute without error and produce output identical to
// the output shown on the Web site.  If your output does not match mine, think
// carefully about what your operations are doing and trace them to find the
// problem.

// If your output does not match mine, or if you must change this file to get
// your code to work, you will lose credit, but you can still get PARTIAL
// credit for your work, so be sure to turn something in no matter what!

import java.util.*;

public class RLITest
{
	public static void main (String [] args)
	{
		// Once you have LLPlus working, this first section should work with
		// the ReallyLongInt methods that I have given you (the toString will
		// work from LLPlus but the output will be backwards and will not be
		// formatted correctly until you override toString() for ReallyLongInt
		ReallyLongInt R1 = new ReallyLongInt("123456789");
		ReallyLongInt R2 = new ReallyLongInt("987654321");
		ReallyLongInt leadZero1 = new ReallyLongInt("0000000000");
		ReallyLongInt leadZero2 = new ReallyLongInt("0000012300");
		System.out.println("R1 = " + R1.toString());
		System.out.println("R2 = " + R2.toString());
		System.out.println("leadZero1 = " + leadZero1.toString());
		System.out.println("leadZero2 = " + leadZero2.toString());
		System.out.println();
		
		// Testing long constructor
		ReallyLongInt L = new ReallyLongInt(0);
		System.out.println("L = " + L.toString());
		L = new ReallyLongInt(123456);
		System.out.println("L = " + L.toString());
		L = new ReallyLongInt(10000000);
		System.out.println("L = " + L.toString());
		System.out.println();
		
		// Testing add method.
		ReallyLongInt R3 = R1.add(R2);
		System.out.println(R1 + " + " + R2 + " = " + R3);
		R1 = new ReallyLongInt("1");
		R2 = new ReallyLongInt("9999999999999999999999999999");
		R3 = R1.add(R2);
		ReallyLongInt R4 = R2.add(R1);
		System.out.println(R1 + " + " + R2 + " = " + R3);
		System.out.println(R2 + " + " + R1 + " = " + R4);
		System.out.println();
		
		// Testing subtract method
		R1 = new ReallyLongInt("23456");
		R2 = new ReallyLongInt("4567");
		R3 = R1.subtract(R2);
		System.out.println(R1 + " - " + R2 + " = " + R3);
		R1 = new ReallyLongInt("1000000000000000000000000000000");
		R2 = new ReallyLongInt("1");
		R3 = R1.subtract(R2);
		System.out.println(R1 + " - " + R2 + " = " + R3);
		R1 = new ReallyLongInt("2111112");
		R2 = new ReallyLongInt("1111111");
		R3 = R1.subtract(R2);
		System.out.println(R1 + " - " + R2 + " = " + R3);
		R1 = new ReallyLongInt("12345678987654321");
		R2 = new ReallyLongInt("12345678987654320");
		R3 = R1.subtract(R2);
		System.out.println(R1 + " - " + R2 + " = " + R3);
		R1 = new ReallyLongInt("123456");
		R2 = new ReallyLongInt("123456");
		R3 = R1.subtract(R2);
		System.out.println(R1 + " - " + R2 + " = " + R3);
		R1 = new ReallyLongInt("123456");
		R2 = new ReallyLongInt("123457");
		try
		{
			R3 = R1.subtract(R2);
		}
		catch (ArithmeticException e)
		{
			System.out.println(R2 + " > " + R1 + ": Cannot subtract");
			//System.out.println(e);
		}
		System.out.println();

		// Testing copy constructor
		ReallyLongInt R5 = new ReallyLongInt(R4);
		System.out.println("Copy of " + R4.toString() + " = " + R5.toString());
		System.out.println();
		
		// Testing compareTo
		ReallyLongInt [] C = new ReallyLongInt[5];
		C[0] = new ReallyLongInt("844444444444444");
		C[1] = new ReallyLongInt("744444444444444");
		C[2] = new ReallyLongInt("844444445444444");
		C[3] = new ReallyLongInt("9444");
		C[4] = new ReallyLongInt("744444444444445");
		for (int i = 0; i < C.length; i++)
		{
			for (int j = 0; j < C.length; j++)
			{
				int ans = C[i].compareTo(C[j]);
				if (ans < 0)
					System.out.println(C[i] + " < " + C[j]);
				else if (ans > 0)
					System.out.println(C[i] + " > " + C[j]);
				else
					System.out.println(C[i] + " == " + C[j]);
			}
		}
		System.out.println();
		Arrays.sort(C);
		System.out.println("Here is the sorted array: ");
		for (ReallyLongInt R: C)
			System.out.println(R);
		System.out.println();

		// Testing equals
		R1 = new ReallyLongInt("12345678987654321");
		R2 = new ReallyLongInt("12345678987654321");
		R3 = new ReallyLongInt("12345678907654321");
		if (R1.equals(R2))
			System.out.println(R1 + " equals " + R2);
		if (!R1.equals(R3))
			System.out.println(R1 + " does not equal " + R3);
		System.out.println();

		// Testing multiply
		R1 = new ReallyLongInt("12345");
		R2 = new ReallyLongInt("100");
		R3 = R1.multiply(R2);
		System.out.println(R1 + " * " + R2 + " = " + R3);
		
		R1 = new ReallyLongInt("123456");
		R2 = new ReallyLongInt("987");
		R3 = R1.multiply(R2);
		System.out.println(R1 + " * " + R2 + " = " + R3);
		
		R3 = R2.multiply(R1);
		System.out.println(R2 + " * " + R1 + " = " + R3);
		
		R1 = new ReallyLongInt("999999999");
		R2 = new ReallyLongInt("9");
		R3 = R1.multiply(R2);
		System.out.println(R1 + " * " + R2 + " = " + R3);
		
		R1 = new ReallyLongInt("999999999");
		R2 = new ReallyLongInt("0");
		R3 = R1.multiply(R2);
		System.out.println(R1 + " * " + R2 + " = " + R3);
		
		R3 = R2.multiply(R1);
		System.out.println(R2 + " * " + R1 + " = " + R3);	
		
		R1 = new ReallyLongInt("123456787654321");
		R2 = new ReallyLongInt("987654323456789");
		R3 = R1.multiply(R2);
		System.out.println(R1 + " * " + R2 + " = " + R3);
		
		R1 = new ReallyLongInt("55555555555555555555555555555555555");
		R2 = new ReallyLongInt("2000");
		R3 = R1.multiply(R2);
		System.out.println(R1 + " * " + R2 + " = " + R3);	
		
		R3 = R2.multiply(R1);
		System.out.println(R2 + " * " + R1 + " = " + R3);	
	}
}
