/**

Tests the methods of the ThreadedMap class

*/

public class ThreadedMapDriver{
	
	public static void main(String[] args){
		
		ThreadedMap map = new ThreadedMap<Integer, String>();
		
		//TEST 1: get() on a nonexisting value
		System.out.print("TEST 1: get() on a nonexisting value. Expecting: null. Actual:");
		System.out.println(map.get(314));
		map.put(5, "5");
		map.put(3, "3");
		map.put(7, "7");
		map.put(4, "4");
		map.put(6, "6");
		map.put(2, "2");
		map.put(8, "8");
		map.put(1, "1");
		map.put(9, "egg");
		
		System.out.print("TEST 2: put() on a pre-existing value. Expecting: egg. Actual:");
		System.out.println(map.put(9, "9"));
		
		System.out.print("TEST 3: get() on an existing value. Expecting: 2. Actual:");
		System.out.println(map.get(2));
		
		System.out.print("TEST 4: get() on the root. Expecting: 5. Actual:");
		System.out.println(map.get(5));
		
		map.inOrderTrav();
		System.out.println(map.prettyStr());
	}//main
	
	
	
}//class