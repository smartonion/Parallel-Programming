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

package hof.chess.client;

import chesspresso.pgn.PGNSyntaxError;
import edu.wustl.cse231s.chess.Game;
import edu.wustl.cse231s.chess.Move;
import edu.wustl.cse231s.chess.Opening;
import edu.wustl.cse231s.chess.Side;
import edu.wustl.cse231s.chess.io.GameDatabase;
import edu.wustl.cse231s.util.Maps;
import edu.wustl.cse231s.viz.PageAxisBuilder;
import hof.filter.group.FilterUtils;
import hof.map.exercise.MapUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleEdge;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.Month;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public class OpeningResponseClient {
	private static List<Game> toFilteredGames(Opening opening, int minRating, int maxRating, List<Game> chessGames) {
		return FilterUtils.filter(game -> {
			int rating = game.rating(Side.WHITE);
			if (minRating < rating && rating < maxRating) {
				return game.matches(opening);
			} else {
				return false;
			}
		}, chessGames);
	}

	private static List<Map.Entry<Move, Integer>> toSortedEntries(Opening opening, List<Game> relevantChessGames) {
		List<Move> responses = MapUtils.map(game -> {
			return game.moveAtIndex(opening.moves().size());
		}, relevantChessGames);
		Map<Move, Integer> moveToCountMap = new HashMap<>();
		for (Move move : responses) {
			if (move != null) {
				moveToCountMap.compute(move, (k, v) -> v != null ? v + 1 : 1);
			}
		}
		return Maps.entriesSortedByValues(moveToCountMap, Comparator.reverseOrder());
	}

	public static void main(String[] args) throws IOException, PGNSyntaxError {
		List<Game> games = GameDatabase.parseLichessMonth(2013, Month.JANUARY).stream().map(Game::new).toList();
		System.out.println("total games: " + games.size());

		int aRating = 1200;
		int bRating = aRating + 400;
		int cRating = bRating + 400;
		int dRating = cRating + 400;

		Opening opening = Opening.FRENCH_DEFENSE_NORMAL;
		List<Game> abGames = toFilteredGames(opening, aRating, bRating, games);
		List<Game> bcGames = toFilteredGames(opening, bRating, cRating, games);
		List<Game> cdGames = toFilteredGames(opening, cRating, dRating, games);
		List<Map.Entry<Move, Integer>> abEntries = toSortedEntries(opening, abGames);
		List<Map.Entry<Move, Integer>> bcEntries = toSortedEntries(opening, bcGames);
		List<Map.Entry<Move, Integer>> cdEntries = toSortedEntries(opening, cdGames);
		SwingUtilities.invokeLater(() -> showGraph(opening.name(), aRating, bRating, cRating, dRating, abGames.size(),
				abEntries, bcGames.size(), bcEntries, cdGames.size(), cdEntries));
	}

	private static void x(DefaultCategoryDataset dataset, int minRating, int maxRating, int gameCount,
			List<Map.Entry<Move, Integer>> entries) {
		for (var entry : entries) {
			dataset.addValue(entry.getValue() / (double) gameCount, minRating + "-" + maxRating,
					entry.getKey().toString());
		}
	}

	private static void showGraph(String title, int aRating, int bRating, int cRating, int dRating, int abGameCount,
			List<Map.Entry<Move, Integer>> abEntries, int bcGameCount, List<Map.Entry<Move, Integer>> bcEntries,
			int cdGameCount, List<Map.Entry<Move, Integer>> cdEntries) {
		ApplicationFrame applicationFrame = new ApplicationFrame(title);
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		x(dataset, aRating, bRating, abGameCount, abEntries);
		x(dataset, bRating, cRating, bcGameCount, bcEntries);
		x(dataset, cRating, dRating, cdGameCount, cdEntries);
		JFreeChart chart = ChartFactory.createBarChart(
				title,
				"Response",
				"",
				dataset,
				PlotOrientation.VERTICAL,
				true, true, false);

		chart.getLegend().setPosition(RectangleEdge.RIGHT);

		ChartPanel chartPanel = new ChartPanel(chart, false);
		chartPanel.setMouseWheelEnabled(true);
		chartPanel.setPreferredSize(new Dimension(1000, 800));
		applicationFrame.getContentPane().add(chartPanel, BorderLayout.CENTER);
		JPanel controlPane = new PageAxisBuilder().build();
		applicationFrame.getContentPane().add(controlPane, BorderLayout.LINE_END);
		applicationFrame.pack();
		applicationFrame.setVisible(true);
	}
}
