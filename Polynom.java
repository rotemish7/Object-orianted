package myMath;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.function.Predicate;

import myMath.Monom;
/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * 
 * @author Boaz
 *
 */
public class Polynom implements Polynom_able
{

	ArrayList<Monom> poly = new ArrayList<Monom>();

	/**
	 * Zero (empty Polynom)
	 */
	public Polynom() 
	{

	}

	/**
	 * init a Polynom from a String such as:
	 *  {"x", "3+1.4X^3-34x", "(2x^2-4)*(-1.2x-7.1)", "(3-3.4x+1)*((3.1x-1.2)-(3X^2-3.1))"};
	 * @param s: is a string represents a Polynom
	 */
	public Polynom(String s) 
	{
		int j=0;

		for (int i=1; i<s.length(); i++) 
		{
			if (s.charAt(i) == '+' || s.charAt(i) == '-') 
			{
				String str = s.substring(j,i);
				j=i;
				Monom m1 = new Monom(str);
				this.add(m1);
			}
			if (i == s.length()-1) 
			{
				String str = s.substring(j,s.length());
				Monom m1 = new Monom(str);
				this.add(m1);
			}
		}
		Collections.sort(this.poly, new Monom_Comperator());
	}
	/**
	 * @return return a double value of the Polynom in the given point x
	 * @param x: is a double represents a real number
	 */
	@Override
	public double f(double x) 
	{
		double ans = 0.0;

		for (int i=0; i<poly.size(); i++) 
		{
			ans += poly.get(i).f(x);
		}
		return ans;
	}

	/** 
	 * @return adding Polynom_able p1 to another Polynom such as:
	 * {"x"+"3+1.4X^3-34x", "2x^2"+"7.1", "3-3.4x+1"+"3.1x-1.2"};
	 * @param p1: is a Polynom_able  represent a Polynom
	 */
	@Override
	public void add(Polynom_able p1) 
	{
		Iterator<Monom> ip1 = p1.iteretor();
		while (ip1.hasNext()) 
		{
			this.add(ip1.next());
		}
		Collections.sort(this.poly, new Monom_Comperator());
	}

	/**
	 * update the Polynom to be the Polynom plus Monom m1
	 * @param m1: is a Monom
	 */
	@Override
	public void add(Monom m1)
	{
		boolean isHere = false;

		for (int i = 0; i < poly.size(); i++)
		{
			if (m1.get_power() == poly.get(i).get_power()) 
			{
				poly.get(i).add(m1);
				isHere = true;
			}
		}
		if (isHere == false)
		{
			poly.add(m1);
		}
		Collections.sort(this.poly, new Monom_Comperator());
	}

	/**
	 * 
	 * subtract Polynom_able p1 from a Polynom and update the Polynom
	 *  
	 * @param p1: is a Polynom_able represent a Polynom
	 */
	@Override
	public void substract(Polynom_able p1) 
	{
		Iterator<Monom> ip1 = p1.iteretor();

		if(p1.equals(this))
		{
			ArrayList<Monom> empty = new ArrayList<Monom>();
			this.poly = empty;
		}
		else 
		{
			while(ip1.hasNext()) 
			{
				Monom temp = ip1.next();
				Monom m1 = new Monom ((temp.get_coefficient())*(-1),temp.get_power());
				this.add(m1);
			}
		}
		Collections.sort(this.poly, new Monom_Comperator());
	}
	/**
	 * multiply Polynom_able p1 with a Polynom and update the Polynom
	 * @param p1: is a Polynom_able represents a Polynom
	 */
	@Override
	public void multiply(Polynom_able p1) 
	{
		Polynom pp2 = new Polynom();

		for (int i=0; i<poly.size(); i++) 
		{
			Polynom_able pp1 = (Polynom)p1.copy();//new
			pp1.multiply(poly.get(i));
			pp2.add(pp1);
		}
		this.poly = pp2.poly;

		Collections.sort(this.poly, new Monom_Comperator());
	}

	/**
	 * @return return true or false if the given Polynom_able p1 is equal to another Polynom 
	 * @param p1: is a Polynom_able represents a Polynom
	 */
	@Override
	public boolean equals(Polynom_able p1) 
	{
		Iterator<Monom> ip1 = p1.iteretor();

		if(p1.isZero() || this.poly.isEmpty())
		{
			if(p1.isZero() && this.poly.isEmpty())
			{
				return true;
			}

			if(this.poly.isEmpty() && !p1.isZero())
			{
				return false;
			}

			if(p1.isZero() && !this.poly.isEmpty())
			{
				if(this.poly.get(0) == Monom.ZERO)
				{
					return true;
				}
				else
				{
					return false;					
				}
			}
		}

		while(ip1.hasNext()) 
		{
			for (int i=0; i<poly.size(); i++)
			{
				if (!ip1.next().equals(poly.get(i)))
				{
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * @return return a true or false if the given Polynom is 0 or empty(equal zero)
	 * 
	 */
	@Override
	public boolean isZero() 
	{
		if (this.poly.isEmpty())
		{
			return true;
		}
		else 
		{
			for (int i = 0; i < poly.size(); i++) 
			{
				if (!poly.get(i).isZero())
				{
					return false;
				}
			}
		}
		return true;
	}
	/**
	 *@return the value in which the Polynom cross the X axis with the given x0.x1
	 *
	 *@param x0: a double represents a real number
	 *@param x1: a double represents a real number
	 *@param eps:a double represents a small positive number
	 */
	@Override
	public double root(double x0, double x1, double eps) 
	{
		// we found it in geeksforgeeks: https://www.geeksforgeeks.org/program-for-bisection-method/
		if (this.f(x0) * this.f(x1) >= 0)
		{
			//throw();
			return -1;
		} 
		double c = x0; 
		while ((x1-x0) >= eps)
		{  
			c = (x0+x1)/2; 

			if (this.f(c) == 0.0) 
			{
				break; 
			}
			else if (this.f(c)*this.f(x0) < 0) 
			{
				x1 = c; 
			}
			else
			{
				x0 = c; 
			}
		} 
		return c;
	}
	/**
	 * creating a New Polynom that is the copy of the Polynom_able being used
	 */
	//	@Override
	//	public Polynom_able copy() 
	//	{
	//		Polynom_able copy = new Polynom();
	//
	//		for (int i = 0; i < this.poly.size(); i++) 
	//		{
	//			Monom m = new Monom(this.poly.get(i));
	//			copy.add(m);
	//		}
	//
	//		return copy;
	//	}
	/**
	 * applying the derivative action on the Polynom_able and update him 
	 */
	@Override
	public Polynom_able derivative() 
	{
		Polynom p1 = new Polynom();
		Iterator<Monom> ip1 = this.iteretor();

		while (ip1.hasNext()) 
		{
			Monom m1 =(Monom)ip1.next();
			Monom m2 = new Monom(m1.derivative());
			p1.add(m2);
		}

		return p1;
	}
	/**
	 * @return: the area of the given Polynom integral between two points x0,x1 with the accuracy of eps
	 * @param x0: is a double represents a real number
	 * @param x1: is a double represents a real number
	 * @param eps: is a double represents a small positive real number 
	 */
	@Override
	public double area(double x0, double x1, double eps) 
	{
		double area = 0;

		if (x0>=x1)
		{
			return 0;
		}
		while (x0<x1) 
		{
			if(f(x0) > 0)
			{
				area += eps*f(x0);
			}

			x0 += eps;
		}
		return area;
	}
	/**
	 * @return: an Iterator of the given Arraylist represents a Monom in the Polynom 
	 * 
	 */
	@Override
	public Iterator<Monom> iteretor() 
	{
		Iterator<Monom> itr = poly.iterator();
		return itr;
	}

	/**
	 * update the Polynom to be the Polynom multiply by Monom m1
	 * @param m1: is a Monom
	 * 
	 */
	@Override
	public void multiply(Monom m1) 
	{
		for(int i=0; i<poly.size();i++) 
		{
			poly.get(i).multipy(m1);
		}
	}

	/**
	 * @return a string that represents the Polynom
	 * 
	 */
	public String toString() 
	{
		if(this.poly.isEmpty())
		{
			return "0";
		}

		String ans = "";

		for (int i = 0; i < this.poly.size(); i++) 
		{
			ans += this.poly.get(i).toString();
		}
		return ans;
	}

	public function initFromString(String s)
	{
		Polynom p = new Polynom(s);
		
		for (int i = 0; i < p.poly.size(); i++) 
		{
			Monom m = new Monom(p.poly.get(i));
			this.poly.add(m);
		}
		return this;
	}

	public function copy()
	{
		function copy = new Polynom();

		for (int i = 0; i < this.poly.size(); i++) 
		{
			Monom m = new Monom(this.poly.get(i));
			((Monom) copy).add(m);
		}

		return copy;
	}

	public boolean equals(Object obj)
	{
		if(obj.getClass() != Polynom.class) 
		{
			return false;
		}
		
		Polynom p = new Polynom();
		p = (Polynom) obj;
		
		for (int i = 0; i < this.poly.size(); i++) 
		{
			if(this.poly.get(i) != p.poly.get(i))
			{
				return false;
			}
		}
		return true;
	}
}
