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
package mapreduce.apps.kmer.client;

import mapreduce.apps.kmer.exercise.KMerMapper;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public class KMerMapperClient {
	public static void main(String[] args) {
		byte[] sequence = "ACTCATGAG".getBytes(StandardCharsets.UTF_8);
		KMerMapper mapper = new KMerMapper(3);
		List<Map.Entry<String, Integer>> keyValuePairs = mapper.map(sequence);
		keyValuePairs.forEach(kv -> {
			System.out.println(kv);
		});
	}
}
