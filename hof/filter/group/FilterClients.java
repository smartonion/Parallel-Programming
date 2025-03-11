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
package hof.filter.group;

import edu.wustl.cse231s.NotYetImplementedException;

import java.util.List;

/**
 * @author Martin Hristov
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public class FilterClients {
	private static final char[] LOWER_CASE_VOWELS = "aeiou".toCharArray();

	/**
	 * Creates and returns a list whose contents are the filtered result of the
	 * specified input words which contain each of the vowels 'a', 'e', 'i', 'o',
	 * and 'u'. The order in the input words must be preserved in the result.
	 * <p>
	 * For example, if the input is ["parallel", "equation", "concurrent",
	 * "tenacious"] then the returned list should be ["equation", "tenacious"].
	 *
	 * @param words the specified list of words
	 * @return the filtered list of specified words which contain each of the vowels
	 */
	public static List<String> filterWordsWhichContainAll5Vowels(List<String> words) {

		throw new NotYetImplementedException();

	}

	/**
	 * Creates and returns a list whose contents are the filtered result of the
	 * specified input integers which are even. The order in the input integers must
	 * be preserved in the result.
	 * <p>
	 * For example, if the input is [12, 71, 100, 231, 404] then the returned list
	 * should be [12, 100, 404].
	 *
	 * @param xs the specified list of integers
	 * @return the filtered list of specified integers which are even
	 */
	public static List<Integer> filterEvens(List<Integer> xs) {

		throw new NotYetImplementedException();

	}
}
