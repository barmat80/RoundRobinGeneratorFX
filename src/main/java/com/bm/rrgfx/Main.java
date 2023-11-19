package com.bm.rrgfx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.bm.rrgfx.constant.CSS;

import javafx.application.Application;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		Application.launch(RRG.class, getArgs());
	}

	private static String[] getArgs() throws FileNotFoundException, IOException {
		String[] myArgs = new String[1];
		String path = System.getProperties().getProperty("user.dir") + File.separator + "rrgfx.ini";
		if (new File(path).exists()) {
			Properties p = Utility.readPropertiesFile(path);
			myArgs[0] = (String) p.get("theme");
		} else {
			myArgs[0] = CSS.PRIMER_LIGHT;
		}
		return myArgs;
	}

}
