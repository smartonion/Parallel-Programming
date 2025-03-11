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
package floodfill.viz.util;

import edu.wustl.cse231s.awt.BufferedImages;
import edu.wustl.cse231s.awt.accessibility.ColorPalettes;
import edu.wustl.cse231s.hsv.AwtBufferedImageMutableHsvImage;
import edu.wustl.cse231s.hsv.HsvColor;
import edu.wustl.cse231s.hsv.MutableHsvImage;
import fj.impl.executor.ExecutorServiceBuilder;
import floodfill.exercise.FloodFiller;
import org.apache.commons.lang3.mutable.MutableBoolean;
import x10.X10;
import x10.impl.fj.ForkJoinX10;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;

/**
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public class MutableImageVC extends JComponent {
	private static final long serialVersionUID = 1L;

	private BufferedImage bufferedImage;
	private final Color[] colors = ColorPalettes.colorPalette7();
	private int colorIndex = 0;
	private boolean isSpacePressed = false;

	public MutableImageVC() {
		this.addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				floodFill(e.getX(), e.getY());
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});

		this.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					isSpacePressed = true;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					isSpacePressed = false;
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});

		setFocusable(true);
		requestFocus();
	}

	private void waitForRepaintIfDesired() {
		if (false && SwingUtilities.isEventDispatchThread()) {
			//pass
		} else {
			if (isSpacePressed) {
				// pass
			} else {
				try {
					SwingUtilities.invokeAndWait(() -> {
						// pass
					});
				} catch (InvocationTargetException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private class RepaintTriggeringMutableHsvImage extends AwtBufferedImageMutableHsvImage {
		public RepaintTriggeringMutableHsvImage(BufferedImage bufferedImage) {
			super(bufferedImage);
		}

		@Override
		public void setColorAtPixel(int x, int y, HsvColor color) {
			super.setColorAtPixel(x, y, color);
			repaint();
			waitForRepaintIfDesired();
		}
	}

	public void setImage(Image image) {
		bufferedImage = BufferedImages.toBufferedImage(image);
		repaint();
	}

	private void floodFill(int x, int y) {
		MutableHsvImage pixels = new RepaintTriggeringMutableHsvImage(bufferedImage);

		MutableBoolean isRunning = new MutableBoolean(false);
		if (pixels.isInBounds(x, y)) {
			if (isRunning.booleanValue()) {
				System.out.println("previous floodfill not yet complete.");
			} else {
				HsvColor targetColor = pixels.colorAtPixel(x, y);
				Thread thread = new Thread() {
					@Override
					public void run() {
						HsvColor fillColor = HsvColor.ofRgb(colors[colorIndex % colors.length].getRGB());
						colorIndex++;
						int desiredWorkerThreads = Runtime.getRuntime().availableProcessors() - 1;
						try {
							isRunning.setTrue();
							X10 x10 = new ForkJoinX10(new ExecutorServiceBuilder().isWorkStealingThreadPoolDesired(true)
									.numWorkerThreads(desiredWorkerThreads).build());
							FloodFiller.floodFill(x10, pixels, fillColor, x, y);
							// TODO: investigate Mac
							sleep(100);
							SwingUtilities.invokeLater(() -> repaint());
						} catch (InterruptedException ie) {
							System.out.println("canceled");
						} catch (ExecutionException ee) {
							Throwable cause = ee.getCause();
							if (cause instanceof RuntimeException runtimeExceptionCause) {
								throw runtimeExceptionCause;
							} else {
								throw new RuntimeException(ee);
							}
						} finally {
							isRunning.setFalse();
						}
					}
				};
				thread.start();
			}
		} else {
			System.out.printf("out of bounds.\n\tmouse x, y: (%3d, %3d)\n\timage w, h: (%d, %d)\n%n", x, y,
					pixels.columnCount(), pixels.rowCount());
		}
	}

	@Override
	public Dimension getPreferredSize() {
		if (bufferedImage != null) {
			return new Dimension(bufferedImage.getWidth(), bufferedImage.getHeight());
		} else {
			return super.getPreferredSize();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (bufferedImage != null) {
			// TODO: scale image and input
			//		g.drawImage(bufferedImage, 0, 0, getWidth(), getHeight(), 0, 0, bufferedImage.getWidth(),
			//				bufferedImage.getHeight(), this);
			g.drawImage(bufferedImage, 0, 0, this);
		}
	}
}
