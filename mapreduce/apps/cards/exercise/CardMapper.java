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
package mapreduce.apps.cards.exercise;

import edu.wustl.cse231s.NotYetImplementedException;
import entry.exercise.DefaultEntry;
import mapreduce.apps.cards.core.Card;
import mapreduce.apps.cards.core.Deck;
import mapreduce.apps.cards.core.Suit;
import mapreduce.core.Mapper;

import javax.annotation.concurrent.Immutable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Martin Hristov
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
@Immutable
public class CardMapper implements Mapper<Deck, Suit, Integer> {
	@Override
	public List<Map.Entry<Suit, Integer>> map(Deck deck) {

		List<Map.Entry<Suit, Integer>> result = new ArrayList<>();

		for (Card card : deck) {
			int rankValue = card.rank().numericValue();

			if (rankValue > 0) {
				result.add(new DefaultEntry<>(card.suit(), rankValue));
			}
		}
		return result;

	}
}
