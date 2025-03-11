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

import edu.wustl.cse231s.chess.Game;
import edu.wustl.cse231s.chess.Move;
import edu.wustl.cse231s.chess.Opening;
import edu.wustl.cse231s.util.Maps;
import hof.filter.group.FilterUtils;
import hof.map.exercise.MapUtils;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.*;

/**
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
class FilteredAndMappedInfo {
	private final Opening opening;
	private final List<Game> games;
	private final RatingRange ratingRange;
	private final List<Game> filteredGames;
	private final List<Map.Entry<Move, Integer>> entries;

	public FilteredAndMappedInfo(Opening opening, Map<String, Short> mapSanToPackedMove, RatingRange ratingRange,
			List<Game> games) {
		this.opening = opening;
		this.ratingRange = ratingRange;
		this.games = games;
		this.filteredGames = FilterUtils.filter(game -> {
			if (game.matches(opening)) {
				return game.moveAtIndex(opening.moves().size()) != null;
			}
			return false;
		}, games);

		List<Move> responses = MapUtils.map(game -> {
			int moveIndex = opening.moves().size();
			Move move = game.moveAtIndex(moveIndex);
			if (move != null) {
				String san = move.shortAnnotation();
				if (san != null) {
					mapSanToPackedMove.computeIfAbsent(san, (key) -> {
						return move.chesspressoShortMove();
					});
				}
				return move;
			} else {
				throw new Error("this game should be filtered out");
			}
		}, filteredGames);
		Map<Move, Integer> moveToCountMap = new HashMap<>();
		for (Move move : responses) {
			Objects.requireNonNull(move);
			moveToCountMap.compute(move, (k, v) -> v != null ? v + 1 : 1);
		}

		entries = Maps.entriesSortedByValues(moveToCountMap, Comparator.reverseOrder());
	}

	public void addToDataset(DefaultCategoryDataset dataset) {
		for (var entry : entries) {
			double portion = entry.getValue() / (double) filteredGames.size();
			if (portion > 0.02) {
				dataset.addValue(portion, ratingRange.minimum() + "-" + ratingRange.maximumExclusive() + " \n "
						+ filteredGames.size() + " games", entry.getKey().shortAnnotation());
			}
		}
	}
}
