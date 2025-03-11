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
package hash.core;

import java.util.Objects;
import java.util.function.ToIntFunction;

/**
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public final class SmearHashFunction<K> implements ToIntFunction<K> {
	private final ToIntFunction<K> hashFunction;

	public SmearHashFunction(ToIntFunction<K> hashFunction) {
		this.hashFunction = hashFunction;
	}

	public SmearHashFunction() {
		this(Objects::hashCode);
	}

	@Override
	public int applyAsInt(K key) {
		int hashCode = hashFunction.applyAsInt(key);
		/*
		 * This method was written by Doug Lea with assistance from members of JCP
		 * JSR-166 Expert Group and released to the public domain, as explained at
		 * http://creativecommons.org/licenses/publicdomain
		 *
		 * As of 2010/06/11, this method is identical to the (package private) hash
		 * method in OpenJDK 7's java.util.HashMap class.
		 */
		hashCode ^= (hashCode >>> 20) ^ (hashCode >>> 12);
		return hashCode ^ (hashCode >>> 7) ^ (hashCode >>> 4);
	}
}
