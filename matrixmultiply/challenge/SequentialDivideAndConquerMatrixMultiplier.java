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

import matrixmultiply.core.Matrices;
import matrixmultiply.core.MatrixMultiplier;

/**
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public class SequentialDivideAndConquerMatrixMultiplier implements MatrixMultiplier {
	@Override
	public double[][] multiply(double[][] a, double[][] b) {
		Matrices.requireValidMultiplicationOperands(a, b);
		int rows = Matrices.rowCount(a);
		int cols = Matrices.columnCount(b);
		double[][] result = new double[rows][cols];
		new OffsetSubMatrix(result).sequentialMultiply(new OffsetSubMatrix(a), new OffsetSubMatrix(b));
		return result;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
