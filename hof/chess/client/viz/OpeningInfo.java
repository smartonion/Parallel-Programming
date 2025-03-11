/*******************************************************************************
 * Copyright (C) 2016-2024 Dennis Cosgrove
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
import edu.wustl.cse231s.chess.Opening;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public class OpeningInfo {
	private final Map<String, Short> mapSanToPackedMove = new HashMap<>();
	private final List<FilteredAndMappedInfo> filteredAndMappedInfos;

	public OpeningInfo(Opening opening, Map<RatingRange, List<Game>> mapRatingRangeToGames) {

		filteredAndMappedInfos = new ArrayList<>(mapRatingRangeToGames.size());
		long t0 = System.currentTimeMillis();

		for (Map.Entry<RatingRange, List<Game>> entry : mapRatingRangeToGames.entrySet()) {
			filteredAndMappedInfos
					.add(new FilteredAndMappedInfo(opening, mapSanToPackedMove, entry.getKey(), entry.getValue()));
		}
		long tDelta = System.currentTimeMillis() - t0;
		System.out.println("TIME TO FILTER AND MAP: " + tDelta);
	}

	public Short lookupPackedMove(String san) {
		return mapSanToPackedMove.get(san);
	}

	public void updateDataset(DefaultCategoryDataset dataset) {
		dataset.clear();
		for (FilteredAndMappedInfo filteredAndMappedInfo : filteredAndMappedInfos) {
			filteredAndMappedInfo.addToDataset(dataset);
		}
	}
}
