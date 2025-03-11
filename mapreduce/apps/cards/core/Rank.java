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
package mapreduce.apps.cards.core;

/**
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public enum Rank {
	ACE, KING, QUEEN, JACK, TEN(10), NINE(9), EIGHT(8), SEVEN(7), SIX(6), FIVE(5), FOUR(4), THREE(3), TWO(2);

	public static final int NUMERIC_EMPTY = -1;

	private final int numericValue;

	Rank() {
		numericValue = NUMERIC_EMPTY;
	}

	Rank(int numericValue) {
		this.numericValue = numericValue;
	}

	public int numericValue() {
		return this.numericValue;
	}

	public boolean isNumericValuePresent() {
		return this.numericValue != NUMERIC_EMPTY;
	}

	public boolean isNumericValueEmpty() {
		return !isNumericValuePresent();
	}

	public static Rank parse(String text) {
		if (text.length() == 1) {
			for (Rank rank : values()) {
				if (rank.isNumericValuePresent()) {
					// pass
				} else {
					if (text.charAt(0) == rank.name().charAt(0)) {
						return rank;
					}
				}
			}
		}
		try {
			int v = Integer.parseInt(text);
			for (Rank rank : values()) {
				if (rank.isNumericValuePresent()) {
					if (v == rank.numericValue()) {
						return rank;
					}
				}
			}
			throw new IllegalArgumentException(text);
		} catch (NumberFormatException nfe) {
			return valueOf(text);
		}
	}
}
