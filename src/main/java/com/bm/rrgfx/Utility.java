package com.bm.rrgfx;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;
import java.util.stream.Stream;

import com.bm.rrg.model.Competitor;

public class Utility {
	public static ArrayList<Competitor> fileToCompetitors(String file) throws IOException {
		Path p = Paths.get(file);
		ArrayList<Competitor> ll = new ArrayList<>();
		ArrayList<String> l = (ArrayList<String>) Files.readAllLines(p, java.nio.charset.StandardCharsets.UTF_8);
		for (String s : l) {
			Competitor player = new Competitor(s);
			ll.add(player);
		}
		return ll;
	}

	public static ArrayList<Competitor> linesToCompetitors(String lines) {
		ArrayList<Competitor> competitors = new ArrayList<>();
		Stream<String> streamCompetitors = lines.lines();
		streamCompetitors.forEach(p -> {
			Competitor player = new Competitor(p);
			competitors.add(player);
		});
		return competitors;
	}

	public static Properties readPropertiesFile(String path) throws FileNotFoundException, IOException {
		Properties p = new Properties();
		try (FileInputStream fis = new FileInputStream(path)) {
			p.load(fis);
		}
		return p;
	}

	public static void writePropertiesFile(Properties p, String path) throws IOException {
		try (FileWriter fw = new FileWriter(path); BufferedWriter bw = new BufferedWriter(fw);) {
			p.store(bw, null);
		}
	}
}
