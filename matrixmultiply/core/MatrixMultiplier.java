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

import java.util.concurrent.ExecutionException;

/**
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public interface MatrixMultiplier {
	/**
	 * <a href=
	 * "https://en.wikipedia.org/wiki/Matrix_multiplication_algorithm">Matrix
	 * multiplication algorithm on Wikipedia</a>
	 *
	 * @param a an n by m matrix
	 * @param b an m by p matrix
	 * @return a new n by p matrix which is the result of multiplying a x b
	 * @throws InterruptedException     if the computation was cancelled
	 * @throws ExecutionException       if the computation threw an exception
	 * @throws IllegalArgumentException if a and b are not a valid combination
	 */
	double[][] multiply(double[][] a, double[][] b) throws InterruptedException, ExecutionException;
}
