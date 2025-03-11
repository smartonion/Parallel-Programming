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
package hof.filter.viz.io;

import hof.filter.viz.MedianRentInfo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public class MedianRentInfoCodec {
	private static List<MedianRentInfo> decodeOIS(ObjectInputStream ois) throws IOException {
		int version = ois.readInt();
		if (version == 1) {
			try {
				int count = ois.readInt();
				List<MedianRentInfo> infos = new ArrayList<MedianRentInfo>(count);
				for (int i = 0; i < count; ++i) {
					String areaName = (String) ois.readObject();
					//					System.out.println(i + " " + areaName);
					int bedroom0 = ois.readShort();
					int bedroom1 = ois.readShort();
					int bedroom2 = ois.readShort();
					int bedroom3 = ois.readShort();
					int bedroom4 = ois.readShort();
					int population2017 = ois.readInt();
					int population2010 = ois.readInt();
					infos.add(new MedianRentInfo(areaName, bedroom0, bedroom1, bedroom2, bedroom3, bedroom4,
							population2017, population2010));
				}
				return infos;
			} catch (ClassNotFoundException cnfe) {
				throw new RuntimeException(cnfe);
			}
		} else {
			throw new RuntimeException("version: " + version);
		}
	}

	public static List<MedianRentInfo> decode(InputStream is) throws IOException {
		GZIPInputStream zis = new GZIPInputStream(is);
		ObjectInputStream ois = new ObjectInputStream(zis);
		return decodeOIS(ois);
	}

	private static void encodeOOS(ObjectOutputStream oos, List<MedianRentInfo> infos) throws IOException {
		int version = 1;
		oos.writeInt(version);
		oos.writeInt(infos.size());
		for (MedianRentInfo info : infos) {
			oos.writeObject(info.getAreaName());
			oos.writeShort(info.getBedroom0());
			oos.writeShort(info.getBedroom1());
			oos.writeShort(info.getBedroom2());
			oos.writeShort(info.getBedroom3());
			oos.writeShort(info.getBedroom4());
			oos.writeInt(info.getPopulation2017());
			oos.writeInt(info.getPopulation2010());
		}
		oos.flush();
	}

	public static void encodeAndClose(OutputStream os, List<MedianRentInfo> infos) throws IOException {
		GZIPOutputStream zos = new GZIPOutputStream(os);
		ObjectOutputStream oos = new ObjectOutputStream(zos);
		encodeOOS(oos, infos);
		zos.flush();
		zos.close();
	}
}
