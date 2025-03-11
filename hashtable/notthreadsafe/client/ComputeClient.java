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
package hashtable.notthreadsafe.client;

import edu.wustl.cse231s.text.core.TextSection;
import edu.wustl.cse231s.text.resources.TextSectionResource;
import hashtable.notthreadsafe.exercise.NotThreadSafeHashTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public class ComputeClient {
	public static void main(String[] args) {
		int chainCount = 8;
		Map<String, Integer> mapWordToCount = new NotThreadSafeHashTable<>(chainCount, Objects::hashCode);

		for (TextSection textSection : TextSectionResource.GETTYSBURG_ADDRESS.textSections()) {
			for (String word : textSection.words()) {
				if (word.length() > 0) {
					String lowerCaseWord = word.toLowerCase();
					mapWordToCount.compute(lowerCaseWord, (unusedKey, prevValue) -> {
						if (prevValue != null) {
							return prevValue + 1;
						} else {
							return 1;
						}
					});
				}
			}
		}

		System.out.println(toString(mapWordToCount));
	}

	private static String toString(Map<String, Integer> map) {
		List<Entry<String, Integer>> sortedByValueEntries = new ArrayList<>(map.entrySet());
		sortedByValueEntries.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));
		StringBuilder sb = new StringBuilder();
		sortedByValueEntries.forEach(e -> sb.append(e.getKey()).append(" => ").append(e.getValue()).append("\n"));
		return sb.toString();
	}
}
