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
package floodfill.exercise;

import edu.wustl.cse231s.NotYetImplementedException;
import edu.wustl.cse231s.hsv.HsvColor;
import edu.wustl.cse231s.hsv.MutableHsvImage;
import x10.X10;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

import static fj.FJ.void_fork;

/**
 * @author Martin Hristov
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public class FloodFiller {
	/**
	 * @param mutableImage The image of pixels that to manipulate.
	 * @param targetColor  The color targeted for replacement. Remember that the
	 *                     flood fill algorithm changes one color to another. It
	 *                     should only change pixels of this target color.
	 * @param fillColor    The replacement color the appropriate pixels should
	 *                     be set to.
	 * @param x            The x-coordinate of the pixel to examine.
	 * @param y            The y-coordinate of the pixel to examine.
	 */
	private static void floodFillKernel(MutableHsvImage mutableImage, HsvColor targetColor, HsvColor fillColor, int x,
			int y) {

		throw new NotYetImplementedException();

	}

	/**
	 * Starts your recursive algorithm by finding the original color of the
	 * specified pixel and invoking floodFillKernel on that pixel to initiate
	 * replacing all of the connected pixels of the original prevColor to nextColor.
	 *
	 * @param x10       X10 support for finishing all enveloped tasks
	 * @param pixels    The original pixels to manipulate for the flood fill.
	 * @param fillColor The new color to fill the area with.
	 * @param x         The x-coordinate of the flood fill starting point.
	 * @param y         The y-coordinate of the flood fill starting point.
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public static void floodFill(X10 x10, MutableHsvImage pixels, HsvColor fillColor, int x, int y)
			throws InterruptedException, ExecutionException {
		HsvColor targetColor = pixels.colorAtPixel(x, y);
		if (targetColor != null) {
			if (Objects.equals(targetColor, fillColor)) {
				// pass
			} else {
				x10.void_finish(() -> {
					floodFillKernel(pixels, targetColor, fillColor, x, y);
				});
			}
		}
	}
}
