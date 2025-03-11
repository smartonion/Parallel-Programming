/*
 * ******************************************************************************
 *  * Copyright (C) 2016-2023 Dennis Cosgrove
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

package matrixmultiply.client;

import matrixmultiply.core.Matrices;
import matrixmultiply.core.MatrixMultiplier;
import matrixmultiply.sequential.SequentialMatrixMultiplier;

import java.util.concurrent.ExecutionException;

/**
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public class SequentialMatrixMultiplierClient {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// Khan Academy example: https://www.youtube.com/watch?v=OMA2Mwo0aZg
		double[][] E = {
				{ 0, 3, 5 },
				{ 5, 5, 2 }
		};
		double[][] D = {
				{ 3, 4 },
				{ 3, -2 },
				{ 4, -2 }
		};
		double[][] expected = {
				{ 29, -16 },
				{ 38.0, 6.0 }
		};

		MatrixMultiplier matrixMultiplier = new SequentialMatrixMultiplier();
		double[][] actual = matrixMultiplier.multiply(E, D);

		if (Matrices.equals(expected, actual)) {
			System.out.println("correct:");
			System.out.println(Matrices.toString(actual));
		} else {
			String message = String.format("\nexpected:\n%s\n\nactual:\n%s",
					Matrices.toString(expected),
					Matrices.toString(actual));
			throw new RuntimeException(message);
		}
	}
}
