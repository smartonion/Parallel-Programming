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
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
class FilterResultsView extends JPanel {
	private static final long serialVersionUID = 1L;

	private final PixelFilterConfig filter;
	private final Map<ImageResource, ResultView> mapSupplierToPane = new HashMap<>();
	private final JLabel header;

	public FilterResultsView(PixelFilterConfig filter, List<ImageResource> imageResources, boolean isFirst) {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.filter = filter;

		header = new JLabel(filter.toStringWithSliderValue());
		header.setFont(header.getFont().deriveFont(18.0f));
		JSlider slider = filter.getSlider();
		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout());
		pane.add(header, BorderLayout.LINE_START);
		if (slider != null) {
			slider.addChangeListener((e) -> handleSliderChange(e));
			pane.add(slider, BorderLayout.CENTER);
		}
		pane.setAlignmentX(Component.LEFT_ALIGNMENT);
		add(pane);

		for (ImageResource imageResource : imageResources) {
			ResultView resultView = new ResultView(filter, imageResource);
			mapSupplierToPane.put(imageResource, resultView);
			resultView.setAlignmentX(Component.LEFT_ALIGNMENT);
			add(resultView);
		}
		setAlignmentX(Component.LEFT_ALIGNMENT);

		int pad = 4;
		setBorder(BorderFactory.createEmptyBorder(isFirst ? pad : pad + 48, pad, pad, pad));
	}

	private void handleSliderChange(ChangeEvent e) {
		header.setText(filter.toStringWithSliderValue());
		for (ResultView resultView : mapSupplierToPane.values()) {
			resultView.handleSliderChange(e);
		}
	}

	public void filterVisible(Map<ImageResource, Boolean> mapSupplier) {
		for (Entry<ImageResource, ResultView> entry : mapSupplierToPane.entrySet()) {
			entry.getValue().setVisible(mapSupplier.get(entry.getKey()));
		}
	}
}
