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
package mapreduce.apps.cards.client;

import mapreduce.apps.cards.core.Deck;
import mapreduce.apps.cards.core.Suit;
import mapreduce.apps.cards.exercise.CardMapper;
import mapreduce.core.AccumulatorCombinerReducer;
import mapreduce.core.MapReduceFramework;
import mapreduce.framework.stream.StreamMapReduceFramework;
import mapreduce.util.StreamUtils;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public class CardMapReduceClient {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		Deck[] decks = {
				Deck.createFull(),
				Deck.createFull(),
				Deck.createFull(),
				Deck.createFull(),
		};
		CardMapper mapper = new CardMapper();
		AccumulatorCombinerReducer<Integer, ?, Integer> accumulatorCombinerReducer = StreamUtils
				.summingIntAccumulatorCombinerReducer();
		MapReduceFramework<Deck, Suit, Integer, ?, Integer> framework = new StreamMapReduceFramework<>(mapper,
				accumulatorCombinerReducer);
		Map<Suit, Integer> map = framework.mapReduceAll(decks);
		map.entrySet().forEach(entry -> {
			System.out.println(entry);
		});
	}
}
