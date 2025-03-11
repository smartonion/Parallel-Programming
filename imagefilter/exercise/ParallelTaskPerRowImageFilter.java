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

package imagefilter.exercise;

import edu.wustl.cse231s.hsv.*;
import edu.wustl.cse231s.NotYetImplementedException;
import edu.wustl.cse231s.hsv.DefaultImmutableHsvImage;
import edu.wustl.cse231s.hsv.DefaultImmutableHsvRow;
import edu.wustl.cse231s.hsv.HsvColor;
import edu.wustl.cse231s.hsv.ImmutableHsvImage;
import edu.wustl.cse231s.hsv.operator.ImageFilter;
import edu.wustl.cse231s.hsv.operator.PixelFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static fj.FJ.fork_loop;
import static fj.FJ.join_fork_loop;

/**
 * @author Martin Hristov
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public class ParallelTaskPerRowImageFilter implements ImageFilter {
	@Override
	public ImmutableHsvImage apply(ImmutableHsvImage src, PixelFilter pixelFilter)
			throws InterruptedException, ExecutionException {




		List<ImmutableHsvRow> dstRows = join_fork_loop(0, src.rowCount(), (i) -> {
			//List<ImmutableHsvRow> dstRowsFuture = new ArrayList<>(src.rowCount());
			List<HsvColor> dstRowPixels = new ArrayList<>(src.rowAt(i).columnCount());
			for (HsvColor srcPixel : src.rowAt(i).columnColors()) {
				HsvColor dstPixel = pixelFilter.apply(srcPixel);
				dstRowPixels.add(dstPixel);
			}
			return new DefaultImmutableHsvRow(dstRowPixels);
		});


		return new DefaultImmutableHsvImage(dstRows);





		//throw new NotYetImplementedException();

	}
}
