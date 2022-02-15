// CS 0445 Spring 2022
// This is a partial implementation of the ReallyLongInt class.  You need to
// complete the implementations of the remaining methods.  Also, for this class
// to work, you must complete the implementation of the LinkedListPlus class.
// See additional comments below.

public class ReallyLongInt 	extends LinkedListPlus<Integer> 
							implements Comparable<ReallyLongInt>
{
	private ReallyLongInt() {
		super();
		add(0);
	}

	// Data is stored with the LEAST significant digit first in the list.  This is
	// done by adding all digits at the front of the list, which reverses the order
	// of the original string.  Note that because the list is doubly-linked and 
	// circular, we could have just as easily put the most significant digit first.
	// You will find that for some operations you will want to access the number
	// from least significant to most significant, while in others you will want it
	// the other way around.  A doubly-linked list makes this access fairly
	// straightforward in either direction.
	public ReallyLongInt(String s){
		super();
		char c;
		int digit = -1;
		// Iterate through the String, getting each character and converting it into
		// an int.  Then make an Integer and add at the front of the list.  Note that
		// the add() method (from A2LList) does not need to traverse the list since
		// it is adding in position 1.  Note also the the author's linked list
		// uses index 1 for the front of the list.
		for (int i = 0; i < s.length(); i++)
		{
			c = s.charAt(i);
			if (('0' <= c) && (c <= '9'))
			{
				digit = c - '0';
				// Do not add leading 0s
				if (!(digit == 0 && this.getLength() == 0)) 
					this.add(1, Integer.valueOf(digit));
			}
			else throw new NumberFormatException("Illegal digit " + c);
		}
		// If number is all 0s, add a single 0 to represent it
		if (digit == 0 && this.getLength() == 0)
			this.add(1, Integer.valueOf(digit));
	}

	// Copy constructor can just call super()
	public ReallyLongInt(ReallyLongInt rightOp) {
		super(rightOp);
	}
	
	// Constructor with a long argument.  You MUST create the ReallyLongInt
	// digits by parsing the long argument directly -- you cannot convert to a String
	// and call the constructor above.  As a hint consider the / and % operators to
	// extract digits from the long value.
	public ReallyLongInt(long X) {
		if (X == 0) add(0);
		while (X > 0) {
			long temp = (X / 10) * 10;
			add(this.numberOfEntries+1, (int)(X - temp));
			X = temp / 10;
		}
	}
	
	// Method to put digits of number into a String.  Note that toString()
	// has already been written for LinkedListPlus, but you need to
	// override it to show the numbers in the way they should appear.
	public String toString() {
		Node curr = this.firstNode;
		String output = "";
		for (int i = 0; i < this.numberOfEntries; i++) {
			curr = curr.prev;
			output += curr.data.toString();
		}
		return output;
	}

	// See notes in the Assignment sheet for the methods below.  Be sure to
	// handle the (many) special cases.  Some of these are demonstrated in the
	// RLITest.java program.

	// Return new ReallyLongInt which is sum of current and argument
	public ReallyLongInt add(ReallyLongInt rightOp) {

		ReallyLongInt sum;
		Node curr1, curr2;
		int carry = 0;
		int smaller;

		if (this.numberOfEntries > rightOp.numberOfEntries) {
			sum = new ReallyLongInt(this);
			curr2 = rightOp.firstNode;
			smaller = rightOp.numberOfEntries;
		} 
		else {
			sum = new ReallyLongInt(rightOp);
			curr2 = this.firstNode;
			smaller = this.numberOfEntries;
		}
		curr1 = sum.firstNode;

		for (int i = 0; i < sum.numberOfEntries; i++) {
			if (i < smaller) {
				int val = curr1.data + curr2.data + carry;
				if (val < 10) {
					curr1.data = val;
					carry = 0;
				} else {
					curr1.data = val % 10;
					carry = 1;
				}
			} else if (carry == 1) {
				int val = curr1.data + carry;
				if (val < 10) {
					curr1.data = val;
					carry = 0;
					break;
				} else curr1.data = val % 10;
			} else break;
			curr1 = curr1.next;
			curr2 = curr2.next;
		}
		if (carry == 1) {
			sum.add(1);
		}
		return sum;
	}
	
	// Return new ReallyLongInt which is difference of current and argument
	public ReallyLongInt subtract(ReallyLongInt rightOp) {
		
		if (compareTo(rightOp) == -1) throw new ArithmeticException();

		ReallyLongInt diff = new ReallyLongInt(this);
		Node curr1 = diff.firstNode;
		Node curr2 = rightOp.firstNode;

		// Subtracting rightOp from this
		for (int i = 0; i < rightOp.numberOfEntries; i++) {
			if (curr1.data < curr2.data ) {
				int forward = 1;
				curr1 = curr1.next;
				while (curr1.data == 0) {
					curr1 = curr1.next;
					forward++;
				}
				curr1.data -= 1;
				curr1 = curr1.prev;
				forward--;
				while (curr1.data == 0 && forward > 0) {
					curr1.data += 9;
					curr1 = curr1.prev;
					forward--;
				}
				curr1.data += 10 - curr2.data;				
			} else curr1.data -= curr2.data;
			
			curr1 = curr1.next;
			curr2 = curr2.next;
		}
		
		// Removing insignificant zeroes
		curr1 = diff.firstNode.prev;
		while (curr1.data == 0 && diff.numberOfEntries > 1) {
			curr1 = curr1.prev;
			diff.numberOfEntries--;
		}
		diff.firstNode.prev = curr1;
		curr1.next = diff.firstNode;
		return diff;
	}

	// Return new ReallyLongInt which is product of current and argument
	public ReallyLongInt multiply(ReallyLongInt rightOp) {

		ReallyLongInt product = new ReallyLongInt();
		Node curr1 = this.firstNode;
		Node curr2 = rightOp.firstNode;
		int power = 0;

		for (int i = 0; i < this.numberOfEntries; i++) {
			int nextPow = power + 1;
			for (int k = 0; k < rightOp.numberOfEntries; k++) {
				ReallyLongInt temp = new ReallyLongInt(curr1.data * curr2.data);
				for (int j = 0; j < power; j++) {
					if (!temp.toString().equals("0")) temp.add(1,0);
				}
				product = product.add(temp);
				curr2 = curr2.next;
				power++;
			}
			power = nextPow;
			curr1 = curr1.next;
		}
		return product;
	}
	
	// Return -1 if current ReallyLongInt is less than rOp
	// Return 0 if current ReallyLongInt is equal to rOp
	// Return 1 if current ReallyLongInt is greater than rOp
	public int compareTo(ReallyLongInt rOp) {
		if (this.numberOfEntries > rOp.numberOfEntries) return 1;
		else if (this.numberOfEntries < rOp.numberOfEntries) return -1;
		Node curr1 = this.firstNode.prev;
		Node curr2 = rOp.firstNode.prev;
		for (int i = 0; i < this.numberOfEntries; i++) {
			if (curr1.data > curr2.data) return 1;
			else if (curr2.data > curr1.data) return -1;
			curr1 = curr1.prev;
			curr2 = curr2.prev;
		}
		return 0;
	}

	// Is current ReallyLongInt equal to rightOp?  Note that the argument
	// in this case is Object rather than ReallyLongInt.  It is written
	// this way to correctly override the equals() method defined in the
	// Object class.
	public boolean equals(Object rightOp) {
		System.out.println("checking equality");
		ReallyLongInt copy = (ReallyLongInt) rightOp;
		if (this.numberOfEntries != copy.numberOfEntries) return false;
		Node curr1 = this.firstNode;
		Node curr2 = copy.firstNode;;
		for (int i = 0; i < this.numberOfEntries; i++) {
			if (curr1.data != curr2.data) return false;
			curr1 = curr1.next;
			curr2 = curr2.next;
		}
		return true;
	}
}
