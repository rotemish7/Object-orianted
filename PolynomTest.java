package myMath;

/**
 *** test 1 ***
+1.0x
0.5000499999999602
0
p1=0, is zero?: true
p1=+1.0x, is zero?: false

*** test 2 ***
p1: +6.0-1.0x-4.7x^2
p2: +2.0+1.7x+1.7000000000000002x^2
p1+p2: +8.0+0.7x-3.0x^2
p1= +8.0+0.7x-3.0x^2
pp1(=-3.0x^2+0.7x+8.0) == p1(=-3.0x^2+0.7x+8.0)?: true
p2*p2: +4.0+6.8x+9.690000000000001x^2+5.78x^3+2.8900000000000006x^4
(p1+p2)*p2: +32.0+57.199999999999996x+70.28000000000002x^2+32.623000000000005x^3-1.904x^4-15.317x^5-8.670000000000002x^6

*** test 3 ***
p1*Monom(m): +6.0x-1.0x^2-4.7x^3
f(1) of p1: 0.2999999999999998
f(1) of p2: 5.4
derivative of p1: +6.0-2.0x-14.100000000000001x^2
derivative of p2: +1.7+3.4000000000000004x
p1: +6.0x-1.0x^2-4.7x^3, p2: +2.0+1.7x+1.7000000000000002x^2
p1-p2: -4.7x^3-2.7x^2+4.3x-2.0
p1-p1: 0000
p1 == p1? :true
p1 == p2? :false
p1 is zero?: true
root: -1.0 
 */

public class PolynomTest {
	public static void main(String[] args) {
		test1();
		test2();
		test3();
	}

	public static void test1() {
		System.out.println("*** test 1 ***");
		Polynom p1 = new Polynom();
		String[] monoms = {"1","x","x^2", "0.5x^2"};
		Monom m = new Monom(monoms[1]);
		p1.add(m);
		System.out.println(p1);
		// area
		double aa = p1.area(0, 1, 0.0001);
		System.out.println(aa);
		// sub p1-p1
		p1.substract(p1);
		System.out.println(p1);
		System.out.println("p1=" + p1 +", is zero?: " + p1.isZero());
		Monom m1 = new Monom (1,1);
		p1.add(m1);
		System.out.println("p1=" + p1 +", is zero?: " + p1.isZero());
		
	}
	public static void test2() {
		System.out.println();
		System.out.println("*** test 2 ***");
		Polynom p1 = new Polynom(), p2 =  new Polynom();
		String[] monoms1 = {"2", "-x","-3.2x^2","4","-1.5x^2"};
		String[] monoms2 = {"5", "1.7x","3.2x^2","-3","-1.5x^2"};
		for(int i=0;i<monoms1.length;i++) {
			Monom m = new Monom(monoms1[i]);
			p1.add(m);
		}
		for(int i=0;i<monoms2.length;i++) {
			Monom m = new Monom(monoms2[i]);
			p2.add(m);
		}
		// print p1
		System.out.println("p1: " + p1);

		// print p2
		System.out.println("p2: " + p2);

		// add p1+p2 and print
		p1.add(p2);
		System.out.println("p1+p2: " + p1);

		// Polynom(s)
		System.out.println("p1= " + p1);
		String s = p1.toString();
		Polynom pp1 = new Polynom(s);
		System.out.println("pp1(=" +pp1 +") == p1(= "+ p1 + ")?: " + pp1.equals(p1));
		
		// mult p1*p2 and print
		p2.multiply(p2);
		System.out.println("p2*p2: " + p2);
		p1.multiply(p2);
		System.out.println("(p1+p2)*p2: " + p1);

	}

	public static void test3() {
		System.out.println();
		System.out.println("*** test 3 ***");
		Polynom p1 = new Polynom(), p2 =  new Polynom();
		String[] monoms1 = {"2", "-x","-3.2x^2","4","-1.5x^2"};
		String[] monoms2 = {"5", "1.7x","3.2x^2","-3","-1.5x^2"};
		for(int i=0;i<monoms1.length;i++) {
			Monom m = new Monom(monoms1[i]);
			p1.add(m);
		}
		for(int i=0;i<monoms2.length;i++) {
			Monom m = new Monom(monoms2[i]);
			p2.add(m);
		}
		// mult p1*Monom(m)
		Monom m1 = new Monom (1,1);
		p1.multiply(m1);
		System.out.println("p1*Monom(m): " + p1);
		
		// f(x) + derivative
		System.out.println("f(1) of p1: " + p1.f(1));
		System.out.println("f(1) of p2: " + p2.f(1));
		System.out.println("derivative of p1: " + p1.derivative());
		System.out.println("derivative of p2: " + p2.derivative());

		// substract
		System.out.println("p1: " + p1 + ", p2: " + p2);
		p1.substract(p2);
		System.out.println("p1-p2: " + p1);
		p1.substract(p1);
		System.out.println("p1-p1: " + p1);

		// equals
		System.out.println("p1 == p1? :" + p1.equals(p1));
		System.out.println("p1 == p2? :" + p1.equals(p2));

		// isZero
		System.out.println("p1 is zero?: " + p1.isZero());

		// root
		System.out.println("root: " + p2.root(-50, 84, 0.000001));
	}
}

