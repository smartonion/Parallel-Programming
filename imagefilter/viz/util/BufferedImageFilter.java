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

package imagefilter.viz.util;

import edu.wustl.cse231s.hsv.HsvColor;
import edu.wustl.cse231s.hsv.ImmutableHsvImage;
import edu.wustl.cse231s.hsv.operator.ImageFilter;
import edu.wustl.cse231s.hsv.operator.PixelFilter;
import edu.wustl.cse231s.hsv.util.HsvImageUtils;
import fj.api.TaskBiFunction;
import imagefilter.core.SequentialImageFilter;
import imagefilter.exercise.ParallelTaskPerRowImageFilter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutionException;

/**
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public enum BufferedImageFilter implements TaskBiFunction<BufferedImage, PixelFilter, BufferedImage> {
	FOR_COMPARISON {
		@Override
		public BufferedImage apply(BufferedImage src, PixelFilter pixelFilter)
				throws InterruptedException, ExecutionException {
			int w = src.getWidth();
			int h = src.getHeight();
			BufferedImage dst = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

			float[] atHSB = new float[3];
			for (int y = 0; y < h; ++y) {
				for (int x = 0; x < w; ++x) {
					int rgb = src.getRGB(x, y);
					int r = (rgb >> 16) & 0xFF;
					int g = (rgb >> 8) & 0xFF;
					int b = rgb & 0xFF;
					Color.RGBtoHSB(r, g, b, atHSB);
					HsvColor hsv = new HsvColor(atHSB[0], atHSB[1], atHSB[2]);
					HsvColor hsvPrime = pixelFilter.apply(hsv);
					Color pixelPrime = hsvPrime.toColor();
					dst.setRGB(x, y, pixelPrime.getRGB());
				}
			}

			return dst;
		}
	},
	SEQUENTIAL {
		@Override
		public BufferedImage apply(BufferedImage src, PixelFilter pixelFilter)
				throws InterruptedException, ExecutionException {
			return BufferedImageFilter.helper(new SequentialImageFilter(), src, pixelFilter);
		}

		@Override
		public String toString() {
			return "SequentialImageFilter apply(_)";
		}
	},
	PARALLEL {
		@Override
		public BufferedImage apply(BufferedImage src, PixelFilter pixelFilter)
				throws InterruptedException, ExecutionException {
			return BufferedImageFilter.helper(new ParallelTaskPerRowImageFilter(), src, pixelFilter);
		}

		@Override
		public String toString() {
			return "ParallelImageFilter apply(_)";
		}
	};

	private static BufferedImage helper(ImageFilter imageFilter,
			BufferedImage src, PixelFilter pixelFilter) throws InterruptedException, ExecutionException {
		ImmutableHsvImage srcHsvImage = HsvImageUtils.toImmutableHsvImage(src);
		ImmutableHsvImage dstHsvImage = imageFilter.apply(srcHsvImage, pixelFilter);
		return HsvImageUtils.toBufferedImage(dstHsvImage);
	}

}
