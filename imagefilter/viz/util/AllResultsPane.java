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

import imagefilter.viz.resources.ImageResource;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public class AllResultsPane extends JPanel {
	private static final long serialVersionUID = 1L;
	private final Map<PixelFilterConfig, FilterResultsView> mapFilterToPane = new HashMap<>();

	public AllResultsPane(List<ImageResource> imageResources) {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		boolean isFirst = true;
		for (PixelFilterConfig filter : PixelFilterConfig.values()) {
			FilterResultsView filterResultsView = new FilterResultsView(filter, imageResources, isFirst);
			isFirst = false;
			mapFilterToPane.put(filter, filterResultsView);
			add(filterResultsView);
			add(Box.createRigidArea(new Dimension(0, 48)));
		}
	}

	public void filterVisible(Map<PixelFilterConfig, Boolean> mapFilter, Map<ImageResource, Boolean> mapSupplier) {
		for (Entry<PixelFilterConfig, FilterResultsView> entry : mapFilterToPane.entrySet()) {
			entry.getValue().setVisible(mapFilter.get(entry.getKey()));
			entry.getValue().filterVisible(mapSupplier);
		}
	}

}
