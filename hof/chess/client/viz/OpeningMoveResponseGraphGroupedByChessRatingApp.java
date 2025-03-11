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

package hof.chess.client.viz;

import chesscargot.viz.Arrow;
import chesscargot.viz.MoveLineTableModel;
import chesscargot.viz.PositionView;
import chesspresso.game.GameModel;
import edu.wustl.cse231s.chess.Game;
import edu.wustl.cse231s.chess.Opening;
import edu.wustl.cse231s.chess.Side;
import edu.wustl.cse231s.chess.commandline.ChessCommandLine;
import edu.wustl.cse231s.chess.commandline.ChessCommandLines;
import edu.wustl.cse231s.chess.io.GameDatabase;
import edu.wustl.cse231s.chess.util.ChesspressoUtils;
import edu.wustl.cse231s.viz.LookAndFeels;
import edu.wustl.cse231s.viz.PageAxisBuilder;
import edu.wustl.cse231s.viz.SplitPaneBuilder;
import org.jfree.chart.*;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleEdge;

import javax.swing.*;
import java.awt.*;
import java.time.Month;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public class OpeningMoveResponseGraphGroupedByChessRatingApp extends JFrame {
	private static final long serialVersionUID = 1L;

	private final Map<Opening, OpeningInfo> mapOpeningToOpeningInfo = new HashMap<>();

	public OpeningMoveResponseGraphGroupedByChessRatingApp(Map<RatingRange, List<Game>> mapRatingRangeToGames,
			Opening initialOpening) {
		setTitle(OpeningMoveResponseGraphGroupedByChessRatingApp.class.getSimpleName());
		MoveLineTableModel moveLineTableModel = new MoveLineTableModel();
		JTable moveTable = new JTable(moveLineTableModel);
		moveTable.setFocusable(false);
		moveTable.setCellSelectionEnabled(false);
		moveTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		JFreeChart chart = ChartFactory.createBarChart("", "", "", dataset, PlotOrientation.VERTICAL, true, true,
				false);
		chart.getLegend().setPosition(RectangleEdge.RIGHT);
		CategoryPlot plot = chart.getCategoryPlot();
		plot.getDomainAxis().setCategoryMargin(0.4);
		BarRenderer barRenderer = (BarRenderer) chart.getCategoryPlot().getRenderer();
		barRenderer.setItemMargin(0.0);

		ChartPanel chartPanel = new ChartPanel(chart, false);
		chartPanel.setMouseWheelEnabled(false);
		chartPanel.setPreferredSize(new Dimension(600, 400));

		PositionView positionView = new PositionView();

		JComboBox<Opening> openingComboBox = new JComboBox<>(Opening.values()) {
			@Override
			public Dimension getMinimumSize() {
				return new Dimension(0, 0);
			}

			@Override
			public Dimension getPreferredSize() {
				Dimension size = super.getPreferredSize();
				size.width = Math.min(size.width, 320);
				return size;
			}

			@Override
			public Dimension getMaximumSize() {
				Dimension size = super.getMaximumSize();
				size.height = getPreferredSize().height;
				return size;
			}
		};
		openingComboBox.setSelectedIndex(-1);
		openingComboBox.addItemListener(e -> {
			Opening selectedOpening = (Opening) e.getItem();
			if (selectedOpening != null) {
				OpeningInfo openingInfo = mapOpeningToOpeningInfo.computeIfAbsent(selectedOpening,
						key -> new OpeningInfo(key, mapRatingRangeToGames));
				openingInfo.updateDataset(dataset);
				chesspresso.position.Position position = new chesspresso.position.Position(selectedOpening.toFen());
				positionView.setPosition(position);
				moveLineTableModel
						.clearAndSetMoves(Arrays.asList(ChesspressoUtils.toChesspressoMovesAsArray(selectedOpening)));
			}
		});

		openingComboBox.setFont(openingComboBox.getFont().deriveFont(18.0f));
		openingComboBox.setSelectedItem(initialOpening);

		chartPanel.addChartMouseListener(new ChartMouseListener() {
			@Override
			public void chartMouseClicked(ChartMouseEvent chartMouseEvent) {

			}

			@Override
			public void chartMouseMoved(ChartMouseEvent chartMouseEvent) {
				ChartEntity entity = chartMouseEvent.getEntity();
				String toolTipText = entity.getToolTipText();
				Short packedMove = null;
				if (toolTipText != null) {
					if (toolTipText.contains(", ") && toolTipText.contains(" = ")) {
						String[] xs = toolTipText.split(", ");
						String san = xs[1].split("\\)")[0];
						Opening currentOpening = (Opening) openingComboBox.getSelectedItem();
						if (currentOpening != null) {
							OpeningInfo openingInfo = mapOpeningToOpeningInfo.get(currentOpening);
							packedMove = openingInfo.lookupPackedMove(san);
						}
					}
				}
				positionView.clearArrows();
				if (packedMove != null) {
					int fromIndex = chesspresso.move.Move.getFromSqi(packedMove);
					int toIndex = chesspresso.move.Move.getToSqi(packedMove);
					Paint paint = new Color(255, 255, 0, 127);
					positionView.addArrow(new Arrow(fromIndex, toIndex, paint));
				}
			}
		});

		setContentPane(
				new SplitPaneBuilder()
						.start(positionView)
						.end(
								new PageAxisBuilder()
										.component(openingComboBox)
										.component(moveTable)
										.component(chartPanel)
										.build())
						.resizeWeight(0.5)
						.buildLineAxisSplit());
	}

	private static void createAndShowGUI(Map<RatingRange, List<Game>> map, Opening opening) {
		LookAndFeels.attemptNimbusLookAndFeel();
		JFrame frame = new OpeningMoveResponseGraphGroupedByChessRatingApp(map, opening);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	private static Map<RatingRange, List<Game>> parseOpeningMonthInWhiteRatingRanges(int year, Month month,
			int minRating, int... ratings) {
		List<RatingRange> ratingRanges = new ArrayList<>(ratings.length);
		int currentMinRating = minRating;
		for (int maxRatingExclusive : ratings) {
			ratingRanges.add(new RatingRange(currentMinRating, maxRatingExclusive));
			currentMinRating = maxRatingExclusive;
		}
		RatingRange minRatingRange = new RatingRange(0, minRating);
		RatingRange maxRatingRange = new RatingRange(currentMinRating, 3200);
		ratingRanges.add(minRatingRange);
		ratingRanges.add(maxRatingRange);
		Map<RatingRange, List<Game>> map = parseOpeningMonthInWhiteRatingRanges(year, month, ratingRanges);
		map.remove(minRatingRange);
		map.remove(maxRatingRange);
		return new TreeMap<>(map);
	}

	private static Map<RatingRange, List<Game>> parseOpeningMonthInWhiteRatingRanges(int year, Month month,
			List<RatingRange> ratingRanges) {
		List<GameModel> gameModels = GameDatabase.parseLichessMonth(year, month);
		return gameModels.stream().map(Game::new).collect(Collectors.groupingBy(game -> {
			int whiteElo = game.rating(Side.WHITE);
			for (RatingRange ratingRange : ratingRanges) {
				if (ratingRange.contains(whiteElo)) {
					return ratingRange;
				}
			}
			throw new RuntimeException(String.format("%d not found in %s", whiteElo, ratingRanges));
		}));
	}

	public static void main(String[] args) {
		ChessCommandLine commandLine = ChessCommandLines.parse(args);
		Map<RatingRange, List<Game>> map = parseOpeningMonthInWhiteRatingRanges(2013, Month.JANUARY, 800, 1200, 1600,
				2000, 2400);
		Opening opening = commandLine.openingOrDefault(Opening.FRENCH_DEFENSE_NORMAL);
		SwingUtilities.invokeLater(() -> createAndShowGUI(map, opening));
	}
}
