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

package imagefilter.core;

import edu.wustl.cse231s.hsv.*;
import edu.wustl.cse231s.hsv.operator.ImageFilter;
import edu.wustl.cse231s.hsv.operator.PixelFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public class SequentialImageFilter implements ImageFilter {
	@Override
	public ImmutableHsvImage apply(ImmutableHsvImage src, PixelFilter pixelFilter) {
		List<ImmutableHsvRow> dstRows = new ArrayList<>(src.rowCount());
		for (ImmutableHsvRow srcRow : src.rows()) {
			List<HsvColor> dstRowPixels = new ArrayList<>(srcRow.columnCount());
			for (HsvColor srcPixel : srcRow.columnColors()) {
				HsvColor dstPixel = pixelFilter.apply(srcPixel);
				dstRowPixels.add(dstPixel);
			}
			dstRows.add(new DefaultImmutableHsvRow(dstRowPixels));
		}
		return new DefaultImmutableHsvImage(dstRows);
	}
}
