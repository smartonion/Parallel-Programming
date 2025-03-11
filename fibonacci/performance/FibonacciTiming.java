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

package fibonacci.performance;

import edu.wustl.cse231s.timing.ImmutableTimer;
import fibonacci.challenge.LinearRecurrenceSequentialFibonacciCalculator;
import fibonacci.core.FibonacciCalculator;
import fibonacci.exercise.DynamicIterativeSequentialFibonacciCalculator;
import fibonacci.exercise.MemoizationSequentialFibonacciCalculator;
import fibonacci.exercise.RecurrenceRelationSequentialFibonacciCalculator;
import fibonacci.fun.RecurrenceRelationParallelWithThresholdFibonacciCalculator;

import java.math.BigInteger;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public class FibonacciTiming {
	private static String rightJustify(String s) {
		return String.format("%58s", s);
	}

	private static void time(FibonacciCalculator fibonacciCalculator, int n, BigInteger expected)
			throws InterruptedException, ExecutionException {
		ImmutableTimer timer = new ImmutableTimer(rightJustify(fibonacciCalculator.toString()));
		try {
			BigInteger actual = fibonacciCalculator.fibonacci(n);
			if (Objects.equals(expected, actual)) {
				timer.markAndPrintResults(n, actual);
			} else {
				timer.markAndPrintResults(n + " INCORRECT: " + expected + " != " + actual);
			}
		} catch (Exception e) {
			timer.markAndPrintResults(n + " " + e.getClass().getSimpleName());
		}
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		int n = 40;
		FibonacciCalculator seq = new RecurrenceRelationSequentialFibonacciCalculator();
		FibonacciCalculator par = new RecurrenceRelationParallelWithThresholdFibonacciCalculator((i) -> i > n - 3);
		FibonacciCalculator memo = new MemoizationSequentialFibonacciCalculator();
		FibonacciCalculator dyn = new DynamicIterativeSequentialFibonacciCalculator();
		FibonacciCalculator logn = new LinearRecurrenceSequentialFibonacciCalculator();

		BigInteger expected = dyn.fibonacci(n);
		final int ITERATION_COUNT = 10;
		for (int iteration = 0; iteration < ITERATION_COUNT; iteration++) {
			time(seq, n, expected);
			time(par, n, expected);
			time(dyn, n, expected);
			time(memo, n, expected);
			time(logn, n, expected);

			System.out.println();
		}
	}
}
