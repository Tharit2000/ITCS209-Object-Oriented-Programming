//Name: Tharit Chantanalertvilai
//ID: 6188068
//Section: 2

import java.lang.Math;
import java.util.ArrayList;

public class Kalculator {
	//******INSERT YOUR CODE HERE***********
	//Class attributes go here
	//**************************************
	/**
	 * Constructor is the fist method to be call at instantiation of a Kalculator object.
	 * If you need to initialize your object, do so here. 
	 */

	public ArrayList<Double> Arr = new ArrayList<Double>();
	
	private double sum = 0;
	private double avg = 0;
	private double std = 0;
	private double max = 0;
	private double min;
	private double[] MaxK;
	private double[] MinK;

	Kalculator()
	{
	}
	
	/**
	 * Add number to the list of numbers. 
	 * @param number
	 */
	public void addNumber(double number)
	{
		Arr.add(number);
	}
	
	/**
	 * Remove the least recently added number from the list. If the list is empty, do nothing.
	 */
	public void deleteFirstNumber()
	{
		if(Arr.size() != 0)
		{
			Arr.remove(0);
		}
	}
	
	/**
	 * Remove the most recently added number from the list. If the list is empty, do nothing.
	 */
	public void deleteLastNumber()
	{
		if(Arr.size() != 0)
		{
			Arr.remove(Arr.size()-1);
		}
	}
	
	/**
	 * Calculate the summation of all the numbers in the list, then returns the sum. 
	 * If the list is empty, return 0.
	 * @return
	 */
	public double getSum()
	{
		for(int i=0; i<Arr.size(); i++)
		{
			sum += Arr.get(i);
		}
		return sum;
	}
	
	/**
	 * Calculate and return the average of all the numbers in the list.
	 * If the list is empty, return 0.
	 * @return
	 */
	public double getAvg()
	{
		if(Arr.size() == 0)
		{
			return 0;
		}
		else
		{
			avg = sum/Arr.size();
			return avg;
		}
	}
	
	/**
	 * Calculate and return the sample standard deviation of all the numbers in the list.
	 * If the list has fewer than 2 numbers, return 0.
	 * @return
	 */
	public double getStd()
	{
		if(Arr.size() < 2)
		{
			return 0;
		}
		else
		{
			for(int i=0; i<Arr.size(); i++)
			{
				std += Math.pow(Arr.get(i) - avg, 2);

			}
			std = Math.sqrt(std/(Arr.size() - 1));
			return std;
		}
	}
	
	/**
	 * Find and return the maximum of all the numbers in the list.
	 * If the list is empty, return 0.
	 * @return
	 */
	public double getMax()
	{
		if(Arr.size() == 0)
		{
			return 0;
		}
		else
		{
			for(int i=0; i<Arr.size(); i++)
			{
				if(max < Arr.get(i))
				{
					max = Arr.get(i);
				}
			}
			return max;
		}
	}
	
	/**
	 * Find and return the minimum of all the numbers in the list.
	 * If the list is empty, return 0.
	 */
	public double getMin()
	{
		if(Arr.size() == 0)
		{
			return 0;
		}
		else
		{
			min = Arr.get(0);
			for(int i=0; i<Arr.size(); i++)
			{
				if(min > Arr.get(i))
				{
					min = Arr.get(i);
				}
			}
			return min;
		}
	}
	
	/**
	 * Find and return the maximum k numbers of all the numbers in the list as an array of k double number.
	 * The order of the returned k numbers does not matter. (We only care if you can get the max k elements)
	 * If the list has fewer than k numbers, return null.
	 */
	public double[] getMaxK(int k)
	{
		if(Arr.size() < k)
		{
			return null;
		}
		else
		{
			MaxK = new double[k];

			for(int i=0; i<Arr.size()-1; i++)
			{
				for(int j=Arr.size()-1; j>i; j--)
				{
					if(Arr.get(i) < Arr.get(j))
					{
						Double tmp = Arr.get(i);
						Arr.set(i, Arr.get(j));
						Arr.set(j, tmp);
					}
				}
			}
			
			for(int i=0; i<k; i++)
			{
				MaxK[i] = Arr.get(i);
			}
			return MaxK;
		}
	}
	
	/**
	 * Find and return the minimum k numbers of all the numbers in the list as an array of k double number.
	 * The order of the returned k numbers does not matter. (We only care if you can get the min k elements)
	 * If the list has fewer than k numbers, return null.
	 */
	public double[] getMinK(int k)
	{
		if(Arr.size() < k)
		{
			return null;
		}
		else
		{
			MinK = new double[k];

			for(int i=0; i<Arr.size()-1; i++)
			{
				for(int j=Arr.size()-1; j>i; j--)
				{
					if(Arr.get(i) > Arr.get(j))
					{
						Double tmp = Arr.get(i);
						Arr.set(i, Arr.get(j));
						Arr.set(j, tmp);
					}
				}
			}
			
			for(int i=0; i<k; i++)
			{
				MinK[i] = Arr.get(i);
			}
			return MinK;
		}
	}
	
	/**
	 * Print (via System.out.println()) the numbers in the list in format of:
	 * DATA[<N>]:[<n1>, <n2>, <n3>, ...]
	 * Where N is the size of the list. <ni> is the ith number in the list.
	 * E.g., "DATA[4]:[1.0, 2.0, 3.0, 4.0]"
	 */
	public void printData()
	{
		System.out.print("DATA [" + Arr.size() + "] : ");
		System.out.println(Arr);
	}
}
