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
package floodfill.io;

import edu.wustl.cse231s.awt.BufferedImages;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.net.URL;

/**
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public enum FloodFillResource {
	// "https://sites.wustl.edu/publicaffairs/files/2015/07/Washington_University_Monogram_Open1c200-01-17zuofc.png";
	WASH_U_LOGO("WashULogo.png");

	private final ImageIcon imageIcon;

	FloodFillResource(String name) {
		URL url = FloodFillResource.class.getResource(name);
		ImageIcon originalImageIcon = new ImageIcon(url);
		BufferedImage bufferedImage = BufferedImages.toBufferedImage(originalImageIcon);
		clampToBlackAndWhite(bufferedImage);
		this.imageIcon = new ImageIcon(bufferedImage);
	}

	public ImageIcon getImageIcon() {
		return imageIcon;
	}

	private static void clampToBlackAndWhite(BufferedImage image) {
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int rgb = image.getRGB(x, y);
				if (rgb != 0XFF000000) {
					image.setRGB(x, y, 0xFFFFFFFF);
				}
			}
		}
	}
}
