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

import edu.wustl.cse231s.hsv.operator.PixelFilter;
import pixelfilter.demo.PixelFilters;

import javax.swing.*;

/**
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public enum PixelFilterConfig {
	SET_HUE(0, 100, 50) {
		@Override
		public PixelFilter pixelFilter() {
			return PixelFilters.hueSettingPixelFilterOf(getSliderValue());
		}

		@Override
		public String toString() {
			return "hueSettingPixelFilterOf(_)";
		}
	},
	ADJUST_HUE(0, 100, 50) {
		@Override
		public PixelFilter pixelFilter() {
			return PixelFilters.hueAdjustingPixelFilterOf(getSliderValue());
		}

		@Override
		public String toString() {
			return "hueAdjustingPixelFilterOf(_)";
		}
	},
	ADJUST_SATURATION(-100, 100, -50) {
		@Override
		public PixelFilter pixelFilter() {
			return PixelFilters.saturationAdjustingPixelFilterOf(getSliderValue());
		}

		@Override
		public String toString() {
			return "saturationAdjustingPixelFilterOf(_)";
		}
	},
	ADJUST_VALUE(-100, 100, -50) {
		@Override
		public PixelFilter pixelFilter() {
			return PixelFilters.valueAdjustingPixelFilterOf(getSliderValue());
		}

		@Override
		public String toString() {
			return "valueAdjustingPixelFilterOf(_)";
		}
	};

	private final JSlider slider;

	PixelFilterConfig(int min, int max, int value) {
		this.slider = new JSlider(min, max, value);
	}

	public JSlider getSlider() {
		return slider;
	}

	public double getSliderValue() {
		if (slider != null) {
			return slider.getValue() / 100.0;
		} else {
			return Double.NaN;
		}
	}

	public abstract PixelFilter pixelFilter();

	public final String toStringWithSliderValue() {
		return toString().replace("_", String.format("%.2f", getSliderValue()));
	}
}
