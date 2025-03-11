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
package hof.filter.viz.io;

import edu.wustl.cse231s.lazy.Lazy;
import hof.filter.viz.MedianRentInfo;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public enum MedianRentInfoResource {
	// https://www.huduser.gov/portal/datasets/50per.html
	AREA_2019("FY2019_50_FMRArea_rev");

	private final Lazy<List<MedianRentInfo>> lazyInfos;

	MedianRentInfoResource(String subpath) {
		lazyInfos = new Lazy<List<MedianRentInfo>>(() -> {
			try {
				InputStream is = MedianRentInfoResource.class.getResourceAsStream(subpath + ".median_rent_info");
				if (is != null) {
					return MedianRentInfoCodec.decode(is);
				} else {
					throw new NullPointerException(subpath);
				}
			} catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
		});
	}

	public List<MedianRentInfo> getMedianRentInfos() {
		return lazyInfos.force();
	}
}
