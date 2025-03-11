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
package matrixmultiply.challenge;

import edu.wustl.cse231s.NotYetImplementedException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.IntPredicate;

import static fj.FJ.join;
import static fj.FJ.void_fork;

/**
 * @author Martin Hristov
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
/* package-private */ class OffsetSubMatrix {
	private final double[][] values;
	private final int row;
	private final int col;
	private final int size;

	/**
	 * Creates a OffsetSubMatrix based on a matrix of values, starts at row zero,
	 * column zero, with a size equal to the matrix's size
	 *
	 * @param values the complete matrix
	 * @throws IllegalArgumentException if the specified matrix is not square and a
	 *                                  power of 2 in size
	 */
	public OffsetSubMatrix(double[][] values) {
		this(values, 0, 0, values.length);

		int n = values.length;
		boolean isPowerOfTwo = (n & (n - 1)) == 0;
		if (isPowerOfTwo) {
			// pass
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Creates a square SubMatrix based on a matrix of values, a given start row, a
	 * given start column, and a given size
	 *
	 * @param values the matrix of values
	 * @param row    the row to start
	 * @param col    the column to start
	 * @param size   the size of the matrix
	 */
	private OffsetSubMatrix(double[][] values, int row, int col, int size) {
		this.values = values;
		this.row = row;
		this.col = col;
		this.size = size;
	}

	/**
	 * @return a new OffsetSubMatrix which represents the top left quadrant of this
	 * OffsetSubMatrix
	 */
	private OffsetSubMatrix sub11() {
		int halfSize = size / 2;
		return new OffsetSubMatrix(this.values, row, col, halfSize);
	}

	/**
	 * @return a new OffsetSubMatrix which represents the top right quadrant of this
	 * OffsetSubMatrix
	 */
	private OffsetSubMatrix sub12() {
		int halfSize = size / 2;
		return new OffsetSubMatrix(this.values, row, col + halfSize, halfSize);
	}

	/**
	 * @return a new OffsetSubMatrix which represents the bottom left quadrant of
	 * this OffsetSubMatrix
	 */
	private OffsetSubMatrix sub21() {
		int halfSize = size / 2;
		return new OffsetSubMatrix(this.values, row + halfSize, col, halfSize);
	}

	/**
	 * @return a new OffsetSubMatrix which represents the bottom right quadrant of
	 * this OffsetSubMatrix
	 */
	private OffsetSubMatrix sub22() {
		int halfSize = size / 2;
		return new OffsetSubMatrix(this.values, row + halfSize, col + halfSize, halfSize);
	}

	/* package-private */ void sequentialMultiply(OffsetSubMatrix a, OffsetSubMatrix b) {
		if (size == 1) {
			values[row][col] += (a.values[a.row][a.col] * b.values[b.row][b.col]);
		} else {
			OffsetSubMatrix result11 = sub11();
			OffsetSubMatrix result12 = sub12();
			OffsetSubMatrix result21 = sub21();
			OffsetSubMatrix result22 = sub22();

			OffsetSubMatrix a11 = a.sub11();
			OffsetSubMatrix a12 = a.sub12();
			OffsetSubMatrix a21 = a.sub21();
			OffsetSubMatrix a22 = a.sub22();

			OffsetSubMatrix b11 = b.sub11();
			OffsetSubMatrix b12 = b.sub12();
			OffsetSubMatrix b21 = b.sub21();
			OffsetSubMatrix b22 = b.sub22();

			// https://en.wikipedia.org/wiki/Matrix_multiplication_algorithm#Divide_and_conquer_algorithm
			throw new NotYetImplementedException();

		}
	}

	/* package-private */ void parallelMultiply(OffsetSubMatrix a, OffsetSubMatrix b, IntPredicate isParallelPredicate)
			throws InterruptedException, ExecutionException {
		if (size > 1 && isParallelPredicate.test(size)) {
			OffsetSubMatrix result11 = sub11();
			OffsetSubMatrix result12 = sub12();
			OffsetSubMatrix result21 = sub21();
			OffsetSubMatrix result22 = sub22();

			OffsetSubMatrix a11 = a.sub11();
			OffsetSubMatrix a12 = a.sub12();
			OffsetSubMatrix a21 = a.sub21();
			OffsetSubMatrix a22 = a.sub22();

			OffsetSubMatrix b11 = b.sub11();
			OffsetSubMatrix b12 = b.sub12();
			OffsetSubMatrix b21 = b.sub21();
			OffsetSubMatrix b22 = b.sub22();

			throw new NotYetImplementedException();

		} else {
			sequentialMultiply(a, b);
		}
	}

}
