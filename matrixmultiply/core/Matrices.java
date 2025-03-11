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

package matrixmultiply.core;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public class Matrices {
	public static int rowCount(double[][] matrix) {
		return matrix.length;
	}

	public static int columnCount(double[][] matrix) {
		return matrix.length > 0 ? matrix[0].length : 0;
	}

	public static void requireValidMultiplicationOperands(double[][] a, double[][] b) {
		Objects.requireNonNull(a);
		Objects.requireNonNull(b);
		int aColCount = columnCount(a);
		int bRowCount = rowCount(b);
		if (aColCount != bRowCount) {
			throw new IllegalArgumentException(
					"a's column count (" + aColCount + ") != b's row count (" + bRowCount + ")");
		}
	}

	public static double[][] createRandom(int rowCount, int colCount) {
		double[][] matrix = new double[rowCount][colCount];
		Random random = ThreadLocalRandom.current();
		for (double[] array : matrix) {
			for (int j = 0; j < array.length; j++) {
				array[j] += random.nextDouble();
			}
		}
		return matrix;
	}

	public static String toString(double[][] matrix) {
		StringBuilder sb = new StringBuilder();
		for (double[] row : matrix) {
			sb.append(Arrays.toString(row)).append("\n");
		}
		return sb.toString();
	}

	public static boolean equals(double[][] a, double[][] b) {
		if (a != null) {
			if (b != null) {
				if (a.length == b.length) {
					for (int r = 0; r < a.length; ++r) {
						if (Arrays.equals(a[r], b[r])) {
							// pass
						} else {
							return false;
						}
					}
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return b == null;
		}
	}
}
