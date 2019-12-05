package myMath;

import java.util.Comparator;

/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Boaz
 *
 */
public class Monom implements function
{
	public static final Monom ZERO = new Monom(0,0);
	public static final Monom MINUS1 = new Monom(-1,0);
	public static final double EPSILON = 0.0000001;
	public static final Comparator<Monom> _Comp = new Monom_Comperator();

	public static Comparator<Monom> getComp() {return _Comp;}

	/**
	 * init a Monom with the values of a and b as coefficient and power respectively
	 * @param a: is a double represents the coefficient of the Monom
	 * @param b: is a int represents the power of the Monom
	 */
	public Monom(double a, int b)
	{
		this.set_coefficient(a);
		this.set_power(b);
	}

	/**
	 * creating a new Monom that is a replica of the Monom 
	 * @param ot: is a Monom 
	 */
	public Monom(Monom ot) 
	{
		this(ot.get_coefficient(), ot.get_power());
	}

	/**
	 * 
	 * @return return a double represents the coefficient of the Monom
	 */
	public double get_coefficient() 
	{
		return this._coefficient;
	}

	/**
	 * 
	 * @return a int represents the power of the Monom
	 */
	public int get_power() 
	{
		return this._power;
	}

	/** 
	 * applying the derivative action on the Monom and returns a new Monom with the new values
	 * @return a new Monom that is the derivative of the Monom
	 */
	public Monom derivative() 
	{
		if(this.get_power()==0) {return getNewZeroMonom();}
		return new Monom(this.get_coefficient()*this.get_power(), this.get_power()-1);
	}

	/**
	 * @return a double represents the value of the Monom in the given x 
	 * @param x: is a double represents a real number
	 */
	public double f(double x) 
	{
		double ans=0;
		double p = this.get_power();
		ans = this.get_coefficient()*Math.pow(x, p);
		return ans;
	}

	/**
	 * 
	 * @return a true or false if the Monom is the zero Monom
	 */
	public boolean isZero() {return this.get_coefficient() == 0;}
	// ***************** add your code below **********************

	/**
	 * init a Monom from a String such as:
	 * {"x", "3", "2x^2", "3.4x+1","-3.1"};
	 * @param s: is a string represents a Monom
	 */
	public Monom(String s) 
	{
		boolean hasX = false;
		int ch = 0;
		for (int i = 0; i < s.length(); i++)
		{
			if(s.charAt(i) == 'x')
			{
				ch = i;
				hasX = true;
				break;
			}
		}
		//if the String contain "x"
		if(hasX)
		{
			String str_a = s.substring(0, ch);

			if(str_a.equals("-"))
			{
				str_a = "-1";
			}
			if (s.charAt(0) == 'x' || str_a.equals("+"))//new
			{
				str_a = "1";
			}
			if(str_a.contains("+"))
			{
				str_a = str_a.substring(1,str_a.length());
			}
			if(str_a.matches("[-]?[0-9]*\\.?[0-9]*")) 
			{
				if(ch == s.length()-1)
				{
					Monom m = new Monom(Double.parseDouble(str_a),1);
					this._coefficient = m.get_coefficient();
					this._power = m.get_power();		
				}
				else
				{
					Monom m = new Monom(0,0);
					m.set_coefficient(Double.parseDouble(str_a));
					String str_b = s.substring((ch+2) , s.length());
					if(str_b.matches("-?\\d+"))
					{
						m.set_power(Integer.parseInt(str_b));
						this._coefficient = m.get_coefficient();
						this._power = m.get_power();
					}
					else
					{
						T_exception(); 
					}
				}
			}
			else
			{
				T_exception();
			}
		}
		//the string doesn't contain "x"
		else
		{
			if(s.contains("+"))
			{
				s = s.substring(1,s.length());
			}
			if(s.matches("[-]?[0-9]*\\.?[0-9]*"))
			{
				double d = Double.parseDouble(s);
				//				m1.set_coefficient(d);
				//				m1.set_power(0);
				Monom m = new Monom(d,0);
				this._coefficient = m.get_coefficient();
				this._power = m.get_power();
			}
			else
			{
				T_exception();
			}
		}
	}

	/**
	 * adding Monom m to a Monom
	 * @param m: is a Monom
	 */
	public void add(Monom m) 
	{
		int b = m.get_power();
		double a = m.get_coefficient();
		if(b == this.get_power())
		{
			this.set_coefficient(a + this.get_coefficient()) ;
		}
		else
		{
			T_exception();
		}
	}

	/**
	 *  multiply the Monom with Monom d  such as:
	 *  {"3*x","2x^2*3,"1*9"}
	 * @param d: is a Monom
	 */
	public void multipy(Monom d) 
	{
		int b = d.get_power();
		double a = d.get_coefficient();
		this.set_coefficient(a*this.get_coefficient());
		this.set_power(b+this.get_power());
	}

	/**
	 * 
	 * init a string that represents the Monom
	 * 
	 */
	public String toString() 
	{
		String ans = "";

		if(this.get_coefficient() == 0)
		{
			return "0";
		}
		if(this.get_coefficient() > 0)
		{
			ans = ans + "+" + this.get_coefficient();
		}
		else
		{
			ans = ans +  this.get_coefficient();
		}
		if(this.get_power() >1)
		{
			ans += "x^"+Integer.toString(this.get_power());
		}
		if(this.get_power() == 1)
		{
			ans += "x";
		}
		return ans;
	}

	/**
	 * 
	 * throw an Exception if there is one
	 */
	public static void T_exception()
	{
		try
		{ 
			throw new NullPointerException("Exception"); 
		} 
		catch(NullPointerException e) 
		{ 
			System.out.println("Not a valid Monom"); 
			throw e; // rethrowing the exception 
		} 
	}

	/**
	 * @return return a true or false answer if Monom m1 is equal to another Monom such as:
	 * {"3==3","3x!=4x"}
	 * @param m1: is a Monom
	 * 
	 */
	public boolean equals(Monom m1)
	{
		double a = m1.get_coefficient();
		int b = m1.get_power();
		if(this.get_coefficient() == a && this.get_power() == b)
		{
			return true;
		}
		return false;
	}
	// you may (always) add other methods.

	//****************** Private Methods and Data *****************


	private void set_coefficient(double a)
	{
		this._coefficient = a;
	}
	private void set_power(int p) 
	{
		if(p<0) {throw new RuntimeException("ERR the power of Monom should not be negative, got: "+p);}
		this._power = p;
	}
	private static Monom getNewZeroMonom() {return new Monom(ZERO);}
	private double _coefficient; 
	private int _power;

	public function initFromString(String s)//new
	{
		Monom m = new Monom(s);
		this._coefficient = m._coefficient;
		this._power = m._power;
		return this;
	}

	public function copy() // new
	{
		double a = this.get_coefficient();
		int b = this.get_power();
		Monom m = new Monom(a,b);
		function f = m;
		return f;
	}

	public boolean equals(Object obj)//new
	{
		if(obj.getClass() != Monom.class) 
		{
			return false;
		}
		Monom m1 = new Monom(0,0);
		Monom m2 = new Monom(0,0);
		m1 = (Monom)obj;
		m2 = (Monom)this;
		return m1.equals(m2);

	}


}
