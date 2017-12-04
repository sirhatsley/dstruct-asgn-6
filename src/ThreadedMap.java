
/**
A Map implemented with a Threaded Binary Search Tree.

@author Tim Callies, Jacob Wiggins


*/

public class ThreadedMap<K extends Comparable<K>,V> {
	
	/**
	Initializes the Binary Search Tree
	*/
	private ThreadedMapNode<K,V> root;
	
	public ThreadedMap()
	{
		root=null;
	}//constructor
	
	/**
	Returns the value associated with the given key
	
	@return the value in the map associated with the given key, null otherwise.
	
	*/
	public V get(K key)
	{
		if (root==null)
		{
			return null;
		}
		
		ThreadedMapNode<K,V> thisNode;
		thisNode=root;
		while(thisNode!=null)
		{
			if(key.compareTo(thisNode.key)<0)
			{
				thisNode=thisNode.leftChild;
			}
			
			else if(key.compareTo(thisNode.key)>0)
			{
				if(thisNode.threadFlag){thisNode=null;}
				else{thisNode=thisNode.rightChild;}
			}
			
			else if(key.compareTo(thisNode.key)==0)
			{
				return thisNode.value;
			}
		}
		return null;
	}//get
	
	/**
	Associates the given value with the specified key in the map. 
	(i.e. adds a new node or updates an existing node)
	
	@param key the key to be associated with the given value
	
	@param value the value to be inserted into the map
	
	@return the value previously associated with the key, or null if there was no previous value.
	
	*/
	public V put(K key, V value)
	{
		V output = null;
		if (root==null)
		{
			root = new ThreadedMapNode<K,V>(key, value);
			root.threadFlag=true;
		}
		
		else
		{
			ThreadedMapNode<K,V> thisNode;
			thisNode=root;
			
			boolean keepLooping=true;
			while (keepLooping)
			{
				//Loops through the tree to find the correct placement.
				if(key.compareTo(thisNode.key)<0)
				{
					System.out.println("left at "+thisNode.value);
					//Checks the left subtree
					if (thisNode.leftChild==null)
					{
						//Stops looping, adds a new node and sets the thread to its parent
						ThreadedMapNode<K,V> newNode = new ThreadedMapNode<K,V>(key, value);
						
						thisNode.leftChild=newNode;
						newNode.threadFlag=true;
						newNode.rightChild=thisNode;
						keepLooping=false;
					}
					else
					{
						//Sets thisNode to the left child
						thisNode=thisNode.leftChild;
					}
				}
				
				else if(key.compareTo(thisNode.key)>0)
				{
					//Checks the right subtree
					if (thisNode.threadFlag)
					{
						//Stops looping, adds a new node and sets the thread to its parent's thread
						ThreadedMapNode<K,V> newNode = new ThreadedMapNode<K,V>(key, value);
						
						thisNode.threadFlag=false;
						newNode.threadFlag=true;
						newNode.rightChild=thisNode.rightChild;
						thisNode.rightChild=newNode;
						keepLooping=false;
					}
					else
					{
						//Sets thisNode to the right child
						thisNode=thisNode.rightChild;
					}
				}
				
				else if(key.compareTo(thisNode.key)==0)
				{
					//If keys are identical, stops looping and updates the key
					//at that location
					output=thisNode.value;
					thisNode.value=value;
					keepLooping=false;
				}
			}
		}
		return output;
	}//put
	
	/**
	prints the key of each node as it is processed in an in order traversal 
	
	(i.e. this should print the keys in the tree in sorted order from low to high)
	
	This method must NOT use recursion.  You must use the threads you added to the tree!
	*/
	public void inOrderTrav()
	{
		ThreadedMapNode<K,V> thisNode;
		thisNode=root;
		
		while(thisNode.leftChild!=null)
		{
			//Goes as far left as possible
			thisNode=thisNode.leftChild;
		}
		
		while(thisNode!=null)
		{
			System.out.println(thisNode.value);
			if (thisNode.threadFlag)
			{
				//If flag is true, follow thread
				thisNode = thisNode.rightChild;
			}
			else
			{
				//If flag is false, goes right and continues to find the furthest
				//left subchild
				thisNode=thisNode.rightChild;
				if (thisNode!=null)
				{
					while(thisNode.leftChild!=null)
					{
						System.out.println(thisNode.value);
						thisNode=thisNode.leftChild;
					}
				}
			}
		}
	}
	
	/**
	returns a string representation of all the nodes in the tree and all the information in their data fields.
	
	@return a string representation of all the nodes and their data in the tree.
	*/
	public String prettyStr()
	{
		ThreadedMapNode<K,V> thisNode;
		thisNode=root;
		StringBuilder output = new StringBuilder();
		
		while(thisNode.leftChild!=null)
		{
			//Goes as far left as possible
			thisNode=thisNode.leftChild;
		}
		
		while(thisNode!=null)
		{
			output.append(thisNode.toString()+"\n");
			if (thisNode.threadFlag)
			{
				//If flag is true, follow thread
				thisNode = thisNode.rightChild;
			}
			else
			{
				//If flag is false, goes right and continues to find the furthest
				//left subchild
				thisNode=thisNode.rightChild;
				if (thisNode!=null)
				{
					while(thisNode.leftChild!=null)
					{
						output.append(thisNode.toString()+"\n");
						thisNode=thisNode.leftChild;
					}
				}
			}
		}
		return output.toString();
	}//prettyStr
	
	private class ThreadedMapNode<K extends Comparable<K>,V>
	{
		/*
		Subclass which defines a single node in a Binary Search Tree.
		*/
		ThreadedMapNode leftChild;
		ThreadedMapNode rightChild;
		boolean threadFlag;
		K key;
		V value;
		
		public ThreadedMapNode(K key, V value)
		{
			this.key=key;
			this.value=value;
		}
		
		public String toString()
		{
			return ("Key: "+key+" | Value: "+value+" | Flag: "+threadFlag);
		}
	}
	
}//class