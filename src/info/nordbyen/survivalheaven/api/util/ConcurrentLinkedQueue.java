/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.api.util;

import java.io.IOException;
import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * The Class ConcurrentLinkedQueue.
 *
 * @param <E>
 *            the element type
 */
public class ConcurrentLinkedQueue<E> extends AbstractQueue<E> implements
		Queue<E>, java.io.Serializable {

	/**
	 * The Class Itr.
	 */
	private class Itr implements Iterator<E> {

		/** The next node. */
		private Node<E> nextNode;
		
		/** The next item. */
		private E nextItem;
		
		/** The last ret. */
		private Node<E> lastRet;

		/**
		 * Instantiates a new itr.
		 */
		Itr() {
			advance();
		}

		/**
		 * Advance.
		 *
		 * @return the e
		 */
		private E advance() {
			lastRet = nextNode;
			final E x = nextItem;
			Node<E> p = (nextNode == null) ? first() : nextNode.getNext();
			for (;;) {
				if (p == null) {
					nextNode = null;
					nextItem = null;
					return x;
				}
				final E item = p.getItem();
				if (item != null) {
					nextNode = p;
					nextItem = item;
					return x;
				} else {
					p = p.getNext();
				}
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			return nextNode != null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#next()
		 */
		@Override
		public E next() {
			if (nextNode == null)
				throw new NoSuchElementException();
			return advance();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#remove()
		 */
		@Override
		public void remove() {
			final Node<E> l = lastRet;
			if (l == null)
				throw new IllegalStateException();
			// rely on a future traversal to relink.
			l.setItem(null);
			lastRet = null;
		}
	}

	/*
	 * This is a straight adaptation of Michael & Scott algorithm. For
	 * explanation, read the paper. The only (minor) algorithmic difference is
	 * that this version supports lazy deletion of internal nodes (method
	 * remove(Object)) -- remove CAS'es item fields to null. The normal queue
	 * operations unlink but then pass over nodes with null item fields.
	 * Similarly, iteration methods ignore those with nulls.
	 * 
	 * Also note that like most non-blocking algorithms in this package, this
	 * implementation relies on the fact that in garbage collected systems,
	 * there is no possibility of ABA problems due to recycled nodes, so there
	 * is no need to use "counted pointers" or related techniques seen in
	 * versions used in non-GC'ed settings.
	 */
	/**
	 * The Class Node.
	 *
	 * @param <E>
	 *            the element type
	 */
	private static class Node<E> {

		/** The item. */
		private volatile E item;
		
		/** The next. */
		private volatile Node<E> next;
		
		/** The Constant nextUpdater. */
		@SuppressWarnings("rawtypes")
		private static final AtomicReferenceFieldUpdater<Node, Node> nextUpdater = AtomicReferenceFieldUpdater
				.newUpdater(Node.class, Node.class, "next");
		
		/** The Constant itemUpdater. */
		@SuppressWarnings("rawtypes")
		private static final AtomicReferenceFieldUpdater<Node, Object> itemUpdater = AtomicReferenceFieldUpdater
				.newUpdater(Node.class, Object.class, "item");

		/**
		 * Instantiates a new node.
		 *
		 * @param x
		 *            the x
		 */
		@SuppressWarnings("unused")
		Node(final E x) {
			item = x;
		}

		/**
		 * Instantiates a new node.
		 *
		 * @param x
		 *            the x
		 * @param n
		 *            the n
		 */
		Node(final E x, final Node<E> n) {
			item = x;
			next = n;
		}

		/**
		 * Cas item.
		 *
		 * @param cmp
		 *            the cmp
		 * @param val
		 *            the val
		 * @return true, if successful
		 */
		boolean casItem(final E cmp, final E val) {
			return itemUpdater.compareAndSet(this, cmp, val);
		}

		/**
		 * Cas next.
		 *
		 * @param cmp
		 *            the cmp
		 * @param val
		 *            the val
		 * @return true, if successful
		 */
		boolean casNext(final Node<E> cmp, final Node<E> val) {
			return nextUpdater.compareAndSet(this, cmp, val);
		}

		/**
		 * Gets the item.
		 *
		 * @return the item
		 */
		E getItem() {
			return item;
		}

		/**
		 * Gets the next.
		 *
		 * @return the next
		 */
		Node<E> getNext() {
			return next;
		}

		/**
		 * Sets the item.
		 *
		 * @param val
		 *            the new item
		 */
		void setItem(final E val) {
			itemUpdater.set(this, val);
		}

		/**
		 * Sets the next.
		 *
		 * @param val
		 *            the new next
		 */
		@SuppressWarnings("unused")
		void setNext(final Node<E> val) {
			nextUpdater.set(this, val);
		}
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 196745693267521676L;
	
	/** The Constant tailUpdater. */
	@SuppressWarnings("rawtypes")
	private static final AtomicReferenceFieldUpdater<ConcurrentLinkedQueue, Node> tailUpdater = AtomicReferenceFieldUpdater
			.newUpdater(ConcurrentLinkedQueue.class, Node.class, "tail");

	/** The Constant headUpdater. */
	@SuppressWarnings("rawtypes")
	private static final AtomicReferenceFieldUpdater<ConcurrentLinkedQueue, Node> headUpdater = AtomicReferenceFieldUpdater
			.newUpdater(ConcurrentLinkedQueue.class, Node.class, "head");

	/** The head. */
	private transient volatile Node<E> head = new Node<E>(null, null);

	/** The tail. */
	private transient volatile Node<E> tail = head;

	/**
	 * Instantiates a new concurrent linked queue.
	 */
	public ConcurrentLinkedQueue() {
	}

	/**
	 * Instantiates a new concurrent linked queue.
	 *
	 * @param c
	 *            the c
	 */
	public ConcurrentLinkedQueue(final Collection<? extends E> c) {
		for (final Iterator<? extends E> it = c.iterator(); it.hasNext();) {
			add(it.next());
		}
	}

	// Have to override just to update the javadoc
	/* (non-Javadoc)
	 * @see java.util.AbstractQueue#add(java.lang.Object)
	 */
	@Override
	public boolean add(final E e) {
		return offer(e);
	}

	/**
	 * Cas head.
	 *
	 * @param cmp
	 *            the cmp
	 * @param val
	 *            the val
	 * @return true, if successful
	 */
	private boolean casHead(final Node<E> cmp, final Node<E> val) {
		return headUpdater.compareAndSet(this, cmp, val);
	}

	/**
	 * Cas tail.
	 *
	 * @param cmp
	 *            the cmp
	 * @param val
	 *            the val
	 * @return true, if successful
	 */
	private boolean casTail(final Node<E> cmp, final Node<E> val) {
		return tailUpdater.compareAndSet(this, cmp, val);
	}

	/* (non-Javadoc)
	 * @see java.util.AbstractCollection#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(final Object o) {
		if (o == null)
			return false;
		for (Node<E> p = first(); p != null; p = p.getNext()) {
			final E item = p.getItem();
			if ((item != null) && o.equals(item))
				return true;
		}
		return false;
	}

	/**
	 * First.
	 *
	 * @return the node
	 */
	Node<E> first() {
		for (;;) {
			final Node<E> h = head;
			final Node<E> t = tail;
			final Node<E> first = h.getNext();
			if (h == head) {
				if (h == t) {
					if (first == null)
						return null;
					else {
						casTail(t, first);
					}
				} else {
					if (first.getItem() != null)
						return first;
					else {
						casHead(h, first);
					}
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see java.util.AbstractCollection#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return first() == null;
	}

	/* (non-Javadoc)
	 * @see java.util.AbstractCollection#iterator()
	 */
	@Override
	public Iterator<E> iterator() {
		return new Itr();
	}

	/* (non-Javadoc)
	 * @see java.util.Queue#offer(java.lang.Object)
	 */
	@Override
	public boolean offer(final E e) {
		if (e == null)
			throw new NullPointerException();
		final Node<E> n = new Node<E>(e, null);
		for (;;) {
			final Node<E> t = tail;
			final Node<E> s = t.getNext();
			if (t == tail) {
				if (s == null) {
					if (t.casNext(s, n)) {
						casTail(t, n);
						return true;
					}
				} else {
					casTail(t, s);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Queue#peek()
	 */
	@Override
	public E peek() { // same as poll except don't remove item
		for (;;) {
			final Node<E> h = head;
			final Node<E> t = tail;
			final Node<E> first = h.getNext();
			if (h == head) {
				if (h == t) {
					if (first == null)
						return null;
					else {
						casTail(t, first);
					}
				} else {
					final E item = first.getItem();
					if (item != null)
						return item;
					else {
						casHead(h, first);
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Queue#poll()
	 */
	@Override
	public E poll() {
		for (;;) {
			final Node<E> h = head;
			final Node<E> t = tail;
			final Node<E> first = h.getNext();
			if (h == head) {
				if (h == t) {
					if (first == null)
						return null;
					else {
						casTail(t, first);
					}
				} else if (casHead(h, first)) {
					final E item = first.getItem();
					if (item != null) {
						first.setItem(null);
						return item;
					}
					// else skip over deleted item, continue loop,
				}
			}
		}
	}

	/**
	 * Read object.
	 *
	 * @param s
	 *            the s
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	private void readObject(final java.io.ObjectInputStream s)
			throws java.io.IOException, ClassNotFoundException {
		// Read in capacity, and any hidden stuff
		s.defaultReadObject();
		head = new Node<E>(null, null);
		tail = head;
		// Read in all elements and place in queue
		for (;;) {
			@SuppressWarnings("unchecked")
			final E item = (E) s.readObject();
			if (item == null) {
				break;
			} else {
				offer(item);
			}
		}
	}

	/* (non-Javadoc)
	 * @see java.util.AbstractCollection#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(final Object o) {
		if (o == null)
			return false;
		for (Node<E> p = first(); p != null; p = p.getNext()) {
			final E item = p.getItem();
			if ((item != null) && o.equals(item) && p.casItem(item, null))
				return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see java.util.AbstractCollection#size()
	 */
	@Override
	public int size() {
		int count = 0;
		for (Node<E> p = first(); p != null; p = p.getNext()) {
			if (p.getItem() != null) {
				// Collections.size() spec says to max out
				if (++count == Integer.MAX_VALUE) {
					break;
				}
			}
		}
		return count;
	}

	/* (non-Javadoc)
	 * @see java.util.AbstractCollection#toArray()
	 */
	@Override
	public Object[] toArray() {
		// Use ArrayList to deal with resizing.
		final ArrayList<E> al = new ArrayList<E>();
		for (Node<E> p = first(); p != null; p = p.getNext()) {
			final E item = p.getItem();
			if (item != null) {
				al.add(item);
			}
		}
		return al.toArray();
	}

	/* (non-Javadoc)
	 * @see java.util.AbstractCollection#toArray(java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(final T[] a) {
		// try to use sent-in array
		int k = 0;
		Node<E> p;
		for (p = first(); (p != null) && (k < a.length); p = p.getNext()) {
			final E item = p.getItem();
			if (item != null) {
				a[k++] = (T) item;
			}
		}
		if (p == null) {
			if (k < a.length) {
				a[k] = null;
			}
			return a;
		}
		// If won't fit, use ArrayList version
		final ArrayList<E> al = new ArrayList<E>();
		for (Node<E> q = first(); q != null; q = q.getNext()) {
			final E item = q.getItem();
			if (item != null) {
				al.add(item);
			}
		}
		return al.toArray(a);
	}

	/**
	 * Write object.
	 *
	 * @param s
	 *            the s
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void writeObject(final java.io.ObjectOutputStream s)
			throws java.io.IOException {
		// Write out any hidden stuff
		s.defaultWriteObject();
		// Write out all elements in the proper order.
		for (Node<E> p = first(); p != null; p = p.getNext()) {
			final Object item = p.getItem();
			if (item != null) {
				s.writeObject(item);
			}
		}
		// Use trailing null as sentinel
		s.writeObject(null);
	}
}
