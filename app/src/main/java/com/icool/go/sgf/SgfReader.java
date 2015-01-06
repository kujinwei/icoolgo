package com.icool.go.sgf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.icool.go.ui.board.Coordinate;

public class SgfReader {

	private static final int ACode = (int) 'a';

	public static List<Coordinate> getCoordList(InputStream ins)
			throws IOException {
		
		List<Coordinate> cs = new ArrayList<Coordinate>();
		
		BufferedReader bufReader = new BufferedReader(new InputStreamReader(ins , "GBK"));
		String line = "";
		String str = "";
		while ((line = bufReader.readLine()) != null)
			str += line;
				
//		System.out.println("sgf=" + str);
		
		
		if (str.length() == 0)
			return cs;

		String data = str.substring(0, str.indexOf(")"));
//		System.out.println("data=" + data);
		String[] ds = data.split(";");
//		System.out.println("ds=" + ds.length);
		for (String s : ds) {
			if (s == "")
				continue;

			String ns = s.toLowerCase();
//			System.out.println("ns=" + ns);
			if (ns.length() == 5
					&& (ns.charAt(0) == 'b' || ns.charAt(0) == 'w')) {
				char xChar = ns.charAt(2);
				char yChar = ns.charAt(3);

				int x = (int) xChar - ACode;
				int y = (int) yChar - ACode;
//				System.out.println("x=" + x + ",y=" + y);
				Coordinate c = new Coordinate(x, y);
				cs.add(c);
			}
		}
		System.out.println("cs=" + cs.size());
		return cs;
	}
}
