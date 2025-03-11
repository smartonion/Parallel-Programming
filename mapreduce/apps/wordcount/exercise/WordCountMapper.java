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
package mapreduce.apps.wordcount.exercise;

import edu.wustl.cse231s.NotYetImplementedException;
import edu.wustl.cse231s.text.core.TextSection;
import entry.exercise.DefaultEntry;
import mapreduce.core.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Martin Hristov
 * @author Finn Voichick
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public class WordCountMapper implements Mapper<TextSection, String, Integer> {
	@Override
	public List<Map.Entry<String, Integer>> map(TextSection textSection) {

		List<Map.Entry<String, Integer>> result = new ArrayList<>();

		for (String word : textSection.words()) {
			String cleanedWord = word.toLowerCase().trim();
			if (!cleanedWord.isEmpty()) {
				result.add(new DefaultEntry<>(cleanedWord, 1));
			}
		}

		return result;

	}

}
