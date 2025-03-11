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

package imagefilter.viz;

import edu.wustl.cse231s.viz.LookAndFeels;
import edu.wustl.cse231s.viz.MultipleSelectPane;
import edu.wustl.cse231s.viz.ResultsScrollPane;
import imagefilter.viz.resources.ImageResource;
import imagefilter.viz.util.AllResultsPane;
import imagefilter.viz.util.PixelFilterConfig;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public class ImageFilterApp extends JFrame {
	private static final long serialVersionUID = 1L;

	private final MultipleSelectPane<PixelFilterConfig> pixelFiltersPane;
	private final MultipleSelectPane<ImageResource> suppliersPane;
	private final AllResultsPane allResultsPane;

	public ImageFilterApp() {
		super("Image Filters");
		List<ImageResource> imageResources = Arrays.asList(ImageResource.WASHU_BEAR, ImageResource.MCKELVEY_HALL,
				ImageResource.JANUARY_110);

		allResultsPane = new AllResultsPane(imageResources);

		pixelFiltersPane = new MultipleSelectPane<PixelFilterConfig>("pixel filters: ",
				Arrays.asList(PixelFilterConfig.values()),
				(e) -> updateResultsViews());

		suppliersPane = new MultipleSelectPane<ImageResource>("images: ", imageResources, (e) -> updateResultsViews());

		JPanel controlPane = new JPanel();
		controlPane.setLayout(new BoxLayout(controlPane, BoxLayout.PAGE_AXIS));
		controlPane.add(pixelFiltersPane);
		controlPane.add(Box.createRigidArea(new Dimension(0, 8)));
		controlPane.add(suppliersPane);

		JScrollPane scrollPane = new ResultsScrollPane(allResultsPane);
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(controlPane, BorderLayout.LINE_START);
		this.getContentPane().add(scrollPane, BorderLayout.CENTER);
	}

	private void updateResultsViews() {
		Map<PixelFilterConfig, Boolean> mapFilter = pixelFiltersPane.getSelectedMap();
		Map<ImageResource, Boolean> mapSupplier = suppliersPane.getSelectedMap();
		allResultsPane.filterVisible(mapFilter, mapSupplier);
	}

	private static void createAndShowGUI() {
		LookAndFeels.attemptNimbusLookAndFeel();
		JFrame frame = new ImageFilterApp();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> ImageFilterApp.createAndShowGUI());
	}
}
