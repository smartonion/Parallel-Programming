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
package hof.filter.viz;

/**
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public final class MedianRentInfo {
	private final String areaName;
	private final int bedroom0;
	private final int bedroom1;
	private final int bedroom2;
	private final int bedroom3;
	private final int bedroom4;
	private final int population2017;
	private final int population2010;

	public MedianRentInfo(String areaName, int bedroom0, int bedroom1, int bedroom2, int bedroom3, int bedroom4,
			int population2017, int population2010) {
		this.areaName = areaName;
		this.bedroom0 = bedroom0;
		this.bedroom1 = bedroom1;
		this.bedroom2 = bedroom2;
		this.bedroom3 = bedroom3;
		this.bedroom4 = bedroom4;
		this.population2017 = population2017;
		this.population2010 = population2010;
	}

	public String getAreaName() {
		return areaName;
	}

	public int getBedroom0() {
		return bedroom0;
	}

	public int getBedroom1() {
		return bedroom1;
	}

	public int getBedroom2() {
		return bedroom2;
	}

	public int getBedroom3() {
		return bedroom3;
	}

	public int getBedroom4() {
		return bedroom4;
	}

	public int getPopulation2010() {
		return population2010;
	}

	public int getPopulation2017() {
		return population2017;
	}

	@Override
	public String toString() {
		String sb = getClass().getSimpleName() +
				"[\"" + areaName + "\"" +
				"; br0=" + bedroom0 +
				"; br1=" + bedroom1 +
				"; br2=" + bedroom2 +
				"; br3=" + bedroom3 +
				"; br4=" + bedroom4 +
				"; pop2017=" + population2017 +
				"; pop2010=" + population2010 +
				"]";
		return sb;
	}
}
