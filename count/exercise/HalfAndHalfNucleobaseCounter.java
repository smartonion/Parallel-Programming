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

package count.exercise;

import count.core.NucleobaseCounter;
import edu.wustl.cse231s.NotYetImplementedException;
import edu.wustl.cse231s.bioinformatics.Nucleobase;
import midpoint.exercise.MidpointUtils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static count.exercise.NucleobaseUtils.countRange;
import static fj.FJ.fork;
import static fj.FJ.join;

/**
 * @author Martin Hristov
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public class HalfAndHalfNucleobaseCounter implements NucleobaseCounter {
	@Override
	public int count(byte[] chromosome, Nucleobase targetNucleobase) throws InterruptedException, ExecutionException {

		int mid = MidpointUtils.calculateMidpoint(0, chromosome.length);
		int test;
		Future<Integer> lowerHalf = fork(() -> {
			return countRange(chromosome, targetNucleobase, 0, mid);
		});
		int upperHalf = countRange(chromosome, targetNucleobase, mid, chromosome.length);

		return join(lowerHalf) + upperHalf;
	}
}
