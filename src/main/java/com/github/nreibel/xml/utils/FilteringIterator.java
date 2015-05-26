package com.github.nreibel.xml.utils;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class FilteringIterator<T> implements Iterator<T>, Iterable<T> {

	public static interface Filter<T> {
		public boolean matches(T obj);
	}

	private final Iterator<T> iterator;
	private final Filter<T> filter;

	private boolean hasNext = false;
	private T nextElement = null;

	public FilteringIterator(Iterator<T> iterator, Filter<T> filter) {
		this.iterator = iterator;
		this.filter = filter;
		
		nextMatch();
	}

	@Override
	public Iterator<T> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		return hasNext;
	}

	@Override
	public T next() {
		if (!hasNext) throw new NoSuchElementException();
		return nextMatch();
	}
	
	private T nextMatch() {
		T oldMatch = nextElement;

		while (iterator.hasNext()) {
			T o = iterator.next();
			if (filter.matches(o)) {
				hasNext = true;
				nextElement = o;
				return oldMatch;
			}
		}

		hasNext = false;
		return oldMatch;
	}

	@Override
	public void remove() {
		iterator.remove();
	}
}
