/*******************************************************************************
 * Copyright (C) 2016-2023 Dennis Cosgrove
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package hashtable.notthreadsafe.exercise;

import edu.wustl.cse231s.NotRequiredForTheAssignmentException;
import edu.wustl.cse231s.NotYetImplementedException;
import entry.exercise.DefaultEntry;
import hash.exercise.HashUtils;
import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.iterators.LazyIteratorChain;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.ToIntFunction;

/**
 * @author Martin Hristov
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
@NotThreadSafe
public class NotThreadSafeHashTable<K, V> implements Map<K, V> {
	private final ToIntFunction<K> hashFunction;
	Collection<Entry<K, V>>[] chains;


	@SuppressWarnings("unchecked")
	public NotThreadSafeHashTable(int chainCount, ToIntFunction<K> hashFunction) {
		this.hashFunction = hashFunction;
		chains = new Collection[chainCount];
		for (int i = 0; i < chainCount; i++) {
			chains[i] = new LinkedList<>();
		}



	}

	public ToIntFunction<K> hashFunction() {

		return this.hashFunction;

	}

	private Collection<Entry<K, V>>[] chains() {


		return this.chains;

	}

	private static <K, V> Entry<K, V> entryOf(Collection<Entry<K, V>> chain, K key) {

		for (Entry<K,V> entry : chain){
			if (entry.getKey().equals(key)){
				return entry;
			}
		}
		return null;

	}

	@Override
	public V put(K key, V value) {
		for(Entry<K,V> entry : chains[HashUtils.arrayIndexForKey(key, chains.length, hashFunction)]){
				if(entry.getKey().equals( key)){
					V prevValue = entry.getValue();
					entry.setValue(value);
					return prevValue;
			}
		}
		Entry<K, V> newEntry = new DefaultEntry<>(key, value);
		chains[HashUtils.arrayIndexForKey(key, chains.length, hashFunction)].add(newEntry);
		return null;


	}

	@Override
	public V get(Object keyObject) {
		@SuppressWarnings("unchecked")
		K key = (K) keyObject;

		for(Entry<K,V> entry : chains[HashUtils.arrayIndexForKey(key, chains.length, hashFunction)]){
			if(entry.getKey().equals(key)){
				return entry.getValue();
			}
		}
		return null;

	}

	@Override
	public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
		V val = null;
		for (Entry<K, V> entry : chains[HashUtils.arrayIndexForKey(key, chains.length, hashFunction)]) {
			if (entry.getKey().equals(key)) {
				val = entry.getValue();
			}
		}
		if (val == null) {
			val = mappingFunction.apply(key);
			Entry<K, V> newEntry = new DefaultEntry<>(key, val);
			chains[HashUtils.arrayIndexForKey(key, chains.length, hashFunction)].add(newEntry);
		}
		return val;

	}


	@Override
	public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		int index = HashUtils.arrayIndexForKey(key, chains.length, hashFunction);
		Collection<Entry<K, V>> chain = chains[index];
		Entry<K, V> target = null;
		for (Entry<K, V> entry : chain) {
			if (entry.getKey().equals(key)) {
				target = entry;
				break;
			}
		}
		V oldVal = (target == null) ? null : target.getValue();
		V newVal = remappingFunction.apply(key, oldVal);
		if (oldVal != null) {
			if (newVal != null) {
				target.setValue(newVal);
			}
		} else {
			if (newVal != null) {
				chain.add(new DefaultEntry<>(key, newVal));
			} else {
				return null;
			}
		}
		return newVal;
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return new AbstractSet<Entry<K, V>>() {
			@Override
			public Iterator<Entry<K, V>> iterator() {
				Collection<Entry<K, V>>[] chains = chains();
				return new LazyIteratorChain<Entry<K, V>>() {
					@Override
					protected Iterator<Entry<K, V>> nextIterator(int count) {
						int index = count - 1;
						return index < chains.length ? chains[index].iterator() : null;
					}
				};
			}

			@Override
			public int size() {
				int result = 0;
				for (var chain : chains()) {
					result += chain.size();
				}
				return result;
			}
		};
	}

	@Override
	public int size() {
		throw new NotRequiredForTheAssignmentException();
	}

	@Override
	public boolean isEmpty() {
		throw new NotRequiredForTheAssignmentException();
	}

	@Override
	public boolean containsKey(Object key) {
		throw new NotRequiredForTheAssignmentException();
	}

	@Override
	public boolean containsValue(Object value) {
		throw new NotRequiredForTheAssignmentException();
	}

	@Override
	public V remove(Object key) {
		throw new NotRequiredForTheAssignmentException();
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		throw new NotRequiredForTheAssignmentException();
	}

	@Override
	public void clear() {
		throw new NotRequiredForTheAssignmentException();
	}

	@Override
	public Set<K> keySet() {
		throw new NotRequiredForTheAssignmentException();
	}

	@Override
	public Collection<V> values() {
		throw new NotRequiredForTheAssignmentException();
	}
}
