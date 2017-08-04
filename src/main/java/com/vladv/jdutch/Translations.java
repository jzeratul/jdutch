package com.vladv.jdutch;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Translations {

	private Properties prop = new Properties();

	public Translations() {

	}

	public Properties load(int lesson) {

		InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream("delftse_les" + lesson + ".properties");
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
}
