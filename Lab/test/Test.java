package Lab.test;
// Java program to demonstrate
// Upcasting Vs Downcasting

// Parent class
class Parent {
	String name;

	// A method which prints the
	// signature of the parent class
	void method()
	{
		System.out.println("Method from Parent");
	}
}

// Child class
class Child extends Parent {
	int id;

	// Overriding the parent method
	// to print the signature of the
	// child class
	@Override void method()
	{
		System.out.println("Method from Child");
	}
}

// Demo class to see the difference
// between upcasting and downcasting
public class Test {

	// Driver code
	public static void main(String[] args)
	{
		String myStr = "Hello";
byte[] result = myStr.getBytes();
System.out.println(result[5]);
	}
}
