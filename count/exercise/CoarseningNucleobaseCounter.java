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
import range.exercise.Range;
import range.exercise.Ranges;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static fj.FJ.join_fork_loop;
import static fj.FJ.void_fork_loop;

/**
 * @author Martin Hristov
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public class CoarseningNucleobaseCounter implements NucleobaseCounter {
	private final int numRanges;

	public CoarseningNucleobaseCounter(int numRanges) {

		this.numRanges = numRanges;

	}

	public int numRanges() {

		return this.numRanges;

	}

	@Override
	public int count(byte[] chromosome, Nucleobase targetNucleobase) throws InterruptedException, ExecutionException {

		Range[] chromRanges = Ranges.slice(0,chromosome.length, numRanges);
		List<Integer> sums = join_fork_loop(chromRanges, (range) -> {
			return NucleobaseUtils.countRange(chromosome, targetNucleobase, range.min(), range.maxExclusive());
		});

		int sum = 0;

        for (int i : sums) {
            sum += i;
        }
		return sum;

	}
}
