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

import imagefilter.core.SequentialImageFilter;
import imagefilter.exercise.ParallelTaskPerRowImageFilter;
import imagefilter.viz.resources.ImageResource;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
class ResultView extends JPanel {
	private static final long serialVersionUID = 1L;
	private final PixelFilterConfig pixelFilter;
	private final ImageIcon src;
	private final JLabel filterTextLabel;
	private final ImageOrThrowablePane sequentialImageOrThrowablePane;
	private final ImageOrThrowablePane parallelImageOrThrowablePane;

	public ResultView(PixelFilterConfig pixelFilter, ImageResource imageResource) {
		this.pixelFilter = pixelFilter;
		this.src = new ImageIcon(imageResource.bufferedImage());
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();

		JLabel aTextLabel = createTextLabel(layout, gbc, "src");
		JLabel aIconLabel = createIconLabel(layout, gbc, src);
		filterTextLabel = createTextLabel(layout, gbc, "== " + pixelFilter.toStringWithSliderValue() + " ==>");
		filterTextLabel.setFont(new Font(Font.MONOSPACED, 0, 12));
		filterTextLabel.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 16));
		JLabel filterEmptyLabel = createTextLabel(layout, gbc, "");
		JLabel sequentialTextLabel = createTextLabel(layout, gbc, SequentialImageFilter.class.getSimpleName());
		JLabel parallelTextLabel = createTextLabel(layout, gbc, ParallelTaskPerRowImageFilter.class.getSimpleName());

		sequentialImageOrThrowablePane = new ImageOrThrowablePane(src.getIconWidth(), src.getIconHeight());
		parallelImageOrThrowablePane = new ImageOrThrowablePane(src.getIconWidth(), src.getIconHeight());

		setLayout(layout);
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.0;
		gbc.gridwidth = 1;
		add(aTextLabel, gbc);
		add(filterEmptyLabel, gbc);
		add(sequentialTextLabel, gbc);
		gbc.insets.left = 8;
		add(parallelTextLabel, gbc);
		gbc.insets.left = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.weightx = 1.0;
		add(new JPanel(), gbc);

		gbc.weightx = 0.0;
		gbc.gridwidth = 1;
		add(aIconLabel, gbc);
		add(filterTextLabel, gbc);
		add(sequentialImageOrThrowablePane, gbc);
		gbc.insets.left = 8;
		add(parallelImageOrThrowablePane, gbc);
		gbc.insets.left = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.weightx = 1.0;
		add(new JPanel(), gbc);

		setBorder(BorderFactory.createEmptyBorder(0, 32, 8, 0));
		updateImages();
	}

	private void updateImages() {
		updateImage(BufferedImageFilter.SEQUENTIAL, sequentialImageOrThrowablePane);
		updateImage(BufferedImageFilter.PARALLEL, parallelImageOrThrowablePane);
	}

	private void updateImage(BufferedImageFilter bufferedImageFilter, ImageOrThrowablePane imageOrThrowablePane) {
		BufferedImage bufferedImage = (BufferedImage) src.getImage();
		try {
			BufferedImage bufferedImagePrime = bufferedImageFilter.apply(bufferedImage, pixelFilter.pixelFilter());
			imageOrThrowablePane.setImage(bufferedImagePrime);
		} catch (Throwable throwable) {
			imageOrThrowablePane.setThrowable(throwable);
		}
	}

	public void handleSliderChange(ChangeEvent event) {
		filterTextLabel.setText("== " + pixelFilter.toStringWithSliderValue() + " ==>");
		updateImages();
	}

	private JLabel createTextLabel(GridBagLayout layout, GridBagConstraints gbc, String text) {
		JLabel label = new JLabel(text);
		label.setHorizontalAlignment(JLabel.CENTER);
		layout.setConstraints(label, gbc);
		return label;
	}

	private JLabel createIconLabel(GridBagLayout layout, GridBagConstraints gbc, Icon icon) {
		JLabel label = new JLabel(icon);
		layout.setConstraints(label, gbc);
		return label;
	}
}
