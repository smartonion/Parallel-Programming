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
package floodfill.viz;

import edu.wustl.cse231s.awt.BufferedImages;
import edu.wustl.cse231s.hsv.HsvColor;
import edu.wustl.cse231s.noise.NoiseUtils;
import edu.wustl.cse231s.viz.BorderPaneBuilder;
import edu.wustl.cse231s.viz.LineAxisBuilder;
import floodfill.io.FloodFillResource;
import floodfill.viz.util.MutableImageVC;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public class FloodFillApp extends JFrame {
	private static final long serialVersionUID = 1L;

	private static HsvColor interpolate(HsvColor a, HsvColor b, double portion) {
		double hue = a.hue() + (b.hue() - a.hue()) * portion;
		double saturation = a.saturation() + (b.saturation() - a.saturation()) * portion;
		double value = a.value() + (b.value() - a.value()) * portion;
		return new HsvColor(hue, saturation, value);
	}

	private static HsvColor A_COLOR = HsvColor.ofRgb(new Color(192, 192, 255).getRGB());
	private static HsvColor B_COLOR = HsvColor.ofRgb(Color.BLUE.darker().getRGB());

	private static Color toColor(double v) {
		if (v > 0.4) {
			return A_COLOR.toColor();
		} else if (v > 0.2) {
			return interpolate(A_COLOR, B_COLOR, 0.8).toColor();
		} else if (v > 0.0) {
			return interpolate(A_COLOR, B_COLOR, 0.6).toColor();
		} else if (v > -0.2) {
			return interpolate(A_COLOR, B_COLOR, 0.4).toColor();
		} else if (v > -0.4) {
			return interpolate(A_COLOR, B_COLOR, 0.2).toColor();
		} else {
			return B_COLOR.toColor();
		}
	}

	private final MutableImageVC imageVc = new MutableImageVC();
	private final BufferedImage washuLogoOriginal = BufferedImages
			.toBufferedImage(FloodFillResource.WASH_U_LOGO.getImageIcon());
	private final BufferedImage washuLogoBuffer = new BufferedImage(washuLogoOriginal.getWidth(),
			washuLogoOriginal.getHeight(), BufferedImage.TYPE_INT_BGR);

	public FloodFillApp() {
		setTitle("FloodFill");
		setToLogo();
		JButton logoButton = new JButton("WashU Logo");
		logoButton.addActionListener(e -> {
			setToLogo();
		});
		JButton noiseButton = new JButton("Perlin Noise");
		noiseButton.addActionListener(e -> {
			setToNoise();
		});
		JPanel panel = new BorderPaneBuilder().pageStart(
				new LineAxisBuilder().components(logoButton, new JLabel(), noiseButton).build()).center(imageVc)
				.build();
		getContentPane().add(panel, BorderLayout.CENTER);
	}

	private void setToLogo() {
		Graphics graphics = washuLogoBuffer.getGraphics();
		graphics.drawImage(washuLogoOriginal, 0, 0, null);
		graphics.dispose();
		imageVc.setImage(washuLogoBuffer);
	}

	private void setToNoise() {
		BufferedImage bufferedImage = NoiseUtils.generate(256, 256, System.currentTimeMillis(), FloodFillApp::toColor);
		imageVc.setImage(bufferedImage);
	}

	private static void createAndShowGUI() {
		JFrame frame = new FloodFillApp();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) throws IOException {
		SwingUtilities.invokeLater(() -> FloodFillApp.createAndShowGUI());
	}
}
