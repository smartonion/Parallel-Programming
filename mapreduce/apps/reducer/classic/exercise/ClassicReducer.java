/*
 * ******************************************************************************
 *  * Copyright (C) 2016-2023 Dennis Cosgrove, Ben Choi
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in
 *  * all copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  * SOFTWARE.
 *  *****************************************************************************
 */
package mapreduce.apps.reducer.classic.exercise;

import edu.wustl.cse231s.NotYetImplementedException;
import mapreduce.core.AccumulatorCombinerReducer;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;

/**
 * An interface used for {@code Collector}s that are similar to Hadoop's
 * reducer. In Hadoop, the collecting of the values into one place is done for
 * you, and you are given an {@link Iterable} of the values. Java's
 * {@code Collector} works a bit differently because it can have any type as its
 * mutable result container. In other words, Java's {@code Collector} is more
 * general, because you don't have to store the values in a list. In this
 * interface, we're making it easier to use Java's {@code Collector} like a
 * Hadoop reducer.
 * <p>
 * Java's {@code Collector} interface has four methods:
 * {@link Collector#supplier()}, {@link Collector#accumulator()},
 * {@link Collector#combiner()}, and {@link Collector#finisher()}. This
 * interface defines three of those methods.
 * <p>
 * This interface is built around the list as the mutable result container. The
 * {@code supplier}'s {@code get} method creates a new list, the
 * {@code accumulator}'s {@code accept} method adds an item to the list, and the
 * {@code combiner}'s {@code apply} method combines two lists. The last method,
 * {@code finisher}, must be defined by any class implementing this interface.
 * It finishes the reduction by converting everything in the list into some
 * result type.
 *
 * @param <V> the type of value being reduced
 * @param <R> the result of the reduction
 * @author Martin Hristov
 * @author Finn Voichick
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public abstract class ClassicReducer<V, R> implements AccumulatorCombinerReducer<V, List<V>, R> {

	List <V> container;
	@Override
	public List<V> createMutableContainer() {
		container = new LinkedList<>();
		return container;
	}

	@Override
	public void accumulate(List<V> container, V item) {

		container.add(item);

	}

	@Override
	public void combine(List<V> containerA, List<V> containerB) {

		containerA.addAll(containerB);

	}
}
