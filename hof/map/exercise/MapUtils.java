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
package hof.map.exercise;

import edu.wustl.cse231s.NotYetImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author Martin Hristov
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public class MapUtils {

	/**
	 * Creates and returns a list whose contents are the result applying the
	 * Function f to each item in the specified list. The order in the input must be
	 * preserved in the result.
	 *
	 * @param <T>  input list item type
	 * @param <R>  result list item type
	 * @param f    function to apply each item.
	 * @param list the specified list of items.
	 * @return a list of the mapped results.
	 */
	public static <T, R> List<R> map(Function<T, R> f, List<T> list) {

		List<R> result = new ArrayList<R>();

		for (T item : list) {
			result.add(f.apply(item));
		}

		return result;

	}
}
