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

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
class ImageOrThrowablePane extends JPanel {
	private static final long serialVersionUID = 1L;

	private final CardLayout cardLayout = new CardLayout();
	private final JLabel imageLabel = new JLabel();
	private final JTextArea throwableTextArea = new JTextArea();

	private enum ContrainstKey {
		IMAGE, THROWABLE
	}

	public ImageOrThrowablePane(int width, int height) {
		JScrollPane scrollPane = new JScrollPane(throwableTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(width, height));

		throwableTextArea.setForeground(new Color(144, 0, 0));
		throwableTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		setLayout(cardLayout);
		add(imageLabel, ContrainstKey.IMAGE.toString());
		add(scrollPane, ContrainstKey.THROWABLE.toString());
	}

	public void setThrowable(Throwable throwable) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		throwable.printStackTrace(printWriter);
		throwableTextArea.setText(stringWriter.toString());
		throwableTextArea.setCaretPosition(0);
		cardLayout.show(this, ContrainstKey.THROWABLE.toString());
	}

	public void setImage(Image image) {
		imageLabel.setIcon(new ImageIcon(image));
		cardLayout.show(this, ContrainstKey.IMAGE.toString());
	}
}
