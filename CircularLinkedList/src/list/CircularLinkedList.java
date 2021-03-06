package list;

import java.util.*;

/**
 * This class is used to create circular linked list.
 * This list acts like usual linked list
 * However the last item points on the first element and vice versa
 * 
 * @author SanchoPansa
 * 
 * @param <T>
 * @version 2.1
 */

public class CircularLinkedList<T>
{
	private transient Node<T> firstNode;
	private transient Node<T> lastNode;
	
	private transient int size = 0;
	
	public CircularLinkedList()
	{
		//Do nothing
	}
	
	private void linkFirst(T t)
	{
		final Node<T> f = this.firstNode;
		final Node<T> l = this.lastNode;
		
		final Node<T> newNode = new Node<>(l, t, f);
		firstNode = newNode;
		if(f == null || l == null)
		{
			newNode.forward = newNode;
			newNode.backward = newNode;
			lastNode = newNode;
		}
		else
		{
			f.backward = newNode;
			lastNode.forward = newNode;
		}
		size++;
	}
	
	private void linkLast(T t)
	{
		final Node<T> f = this.firstNode;
		final Node<T> l = this.lastNode;
		
		final Node<T> newNode = new Node<>(l, t, f);
		lastNode = newNode;
		if(f == null || l == null)
		{
			newNode.backward = newNode;
			newNode.forward = newNode;
			firstNode = newNode;
			lastNode = newNode;
		}
		else
		{
			firstNode.backward = newNode;
			l.forward = newNode;
		}
		size++;
	}
	
	private void linkBefore(T t, Node<T> succeed)
	{
		final Node<T> previous = succeed.backward;
		final Node<T> newNode = new Node<>(previous, t, succeed);
		succeed.backward = newNode;
		if(previous == null)
		{
			linkFirst(t);
		}
		else
		{
			previous.forward = newNode;
		}
		size++;
	}
	
	private T unlinkFirst(Node<T> f)
	{
		final T element = f.content;
		final Node<T> next = f.forward;
		firstNode = next;
		if(next == f)
		{
			firstNode = null;
			lastNode = null;
		}
		else
		{
			f.content = null;
			f.backward = null;
			f.forward = null;
			firstNode.backward = lastNode;
			lastNode.forward = firstNode;
		}
		size--;
		return element;
	}
	
	private T unlinkLast(Node<T> l)
	{
		final T element = l.content;
		final Node<T> prev = l.backward;
		lastNode = prev;
		if(prev == l)
		{
			firstNode = null;
			lastNode = null;
		}
		else
		{
			l.content = null;
			l.backward = null;
			l.forward = null;
			firstNode.backward = lastNode;
			lastNode.forward = firstNode;
		}
		size--;
		return element;
	}
	
	private T unlink(Node<T> c)			// c stands for "chosenNode"
	{
		final T element = c.content;
		final Node<T> prev = c.backward;
		final Node<T> next = c.forward;
		
		if(prev == c)
		{
			firstNode = null;
			lastNode = null;
		}
		else 
		{
			prev.forward = next;
			next.backward = prev;
			c.forward = null;
			c.backward = null;
		}
		c.content = null;
		size--;
		return element;
	}
	
	private Node<T> getNodeAt(int index)
	{
		if(index < (size >> 1))
		{
			Node<T> searchNode = this.firstNode;
			for(int i = 0; i < index; i++)
				searchNode = searchNode.forward;
			return searchNode;
		}
		else
		{
			Node<T> newNode = this.lastNode;
			for(int i = this.size - 1; i > index; i--)
				newNode = newNode.backward;
			return newNode;
		}
		
	}
	
	private boolean isPositionIndex(int index)
	{
		return index >=0 && index <= this.size;
	}
	
	private String getOutOfBoundsMsg(int index)
	{
		return "Index " + index + " exceeds size of stack (" + this.size + ")"; 
	}
	
	/*
	 * Private methods end here
	 * 
	 * Public methods start here
	 */
	
	/*
	 * Adding methods start here
	 */
	/**
	 * Adds an element at the head of list (after the last added element)
	 * @param t
	 */
	public void addFirst(T t)
	{
		linkFirst(t);
	}
	
	/**
	 * Adds an element at the end of the list (before the first added element)
	 * @param t
	 */
	public void addLast(T t)
	{
		linkLast(t);
	}
	
	/**
	 * Adds element on specified index
	 * @throws IndexOutOfBoundsException
	 * @param index
	 * @param t
	 */
	public void addAt(int index, T t)
	{
		if(!this.isPositionIndex(index))
			throw new IndexOutOfBoundsException(this.getOutOfBoundsMsg(index));
		if(index == size)
			linkLast(t);
		else
			linkBefore(t, getNodeAt(index));
	}

	/**
	 * Pushes element into the beginning of the list.
	 * Don't forget, that stacks use LIFO system.
	 * @param t
	 */
	public void push(T t)
	{
		addFirst(t);
	}
	
	/**
	 * Returns, but no pops element on specified index
	 * @param index
	 * @return
	 */
	public Node<T> getAt(int index)
	{
		final Node<T> node = this.getNodeAt(index);
		return node;
	}
	
	/**
	 * Returns, but not pops the first Node (the last added)
	 * @return
	 */
	public Node<T> getFirst()
	{
		return firstNode;
	}
	
	/**
	 * Returns, but not removes the first added Node (the last element)
	 * @return
	 */
	public Node<T> getLast()
	{
		return lastNode;
	}
	
	/*
	 * Remove methods start here
	 */
	/**
	 * Removes the first element from the list. Equivalent to pop() method.
	 * @return
	 */
	public T removeFirst()
	{
		final Node<T> f = firstNode;
		if(f == null)
			throw new NoSuchElementException();
		return unlinkFirst(f);
	}
	
	/**
	 * Removes the first added element from the list (the last element of list)
	 * @return
	 */
	public T removeLast()
	{
		final Node<T> l = lastNode;
		if(l == null)
			throw new NoSuchElementException();
		return unlinkLast(l);
	}
	
	/**
	 * Removes element on specified index
	 * @throws IndexOutOfBoundsException
	 * @param index
	 * @return
	 */
	public T removeAt(int index)
	{
		if(!this.isPositionIndex(index))
			throw new IndexOutOfBoundsException(this.getOutOfBoundsMsg(index));
		return unlink(getNodeAt(index));
			
	}
	
	/**
	 * Pops the first element from the list
	 * @return
	 */
	public T pop()
	{
		return removeFirst();
	}
	
	/**
	 * Returns content of Node (type: {@link T}) 
	 * @param node
	 * @return
	 */
	public T getContent(Node<T> node)
	{
		return node.content;
	}
	
	/**
	 * Returns preceding Node
	 * @param node
	 * @return
	 */
	public Node<T> getPrevious(Node<T> node)
	{
		return node.backward;
	}
	
	/**
	 * Returns succeeding Node
	 * @param node
	 * @return
	 */
	public Node<T> getNext(Node<T> node)
	{
		return node.forward;
	}
	/**
	 * Returns the size of list.
	 * Initial size of list is 0
	 * @return
	 */
	public int size()
	{
		return this.size;
	}
	
	/**
	 * Returns whether this list contains any element.
	 * @return
	 */
	public boolean isEmpty()
	{
		return this.firstNode == null || this.lastNode == null;
	}
	
	public void clear()
	{
		for(Node<T> x = firstNode; x != null; )
		{
			Node<T> next = x.forward;
			x.backward = null;
			x.content = null;
			x.forward = null;
			x = next;
		}
		this.size = 0;
	}
	
	/**
	 * Scrolls circular list to one position clockwise
	 * i.e. first become second, second become third ... last become first
	 */
	public void scroll()
	{
		T temp = firstNode.content;
		for(int i = 0; i < this.size; i++)
		{
			this.getAt(i).content = this.getAt(i).forward.content;
		}
		lastNode.content = temp;
	}
	/**
	 * Reverts position of every element in list
	 */
	public CircularLinkedList<T> revert()
	{
		CircularLinkedList<T> revertedList = new CircularLinkedList<>();
		while(!this.isEmpty())
			revertedList.push(this.pop());
		return revertedList;
	}
	
	public static class Node<T>
	{
		private T content;
		private Node<T> backward;
		private Node<T> forward;
		
		Node(Node<T> prev, T elem, Node<T> next)
		{
			this.backward = prev;
			this.content = elem;
			this.forward = next;
		}
		
		public T getContents()
		{
			return this.content;
		}
		
	}
}
