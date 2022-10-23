package edu.usfca.cs272;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

/**
 * Recursively generates a directory listing using single-threading.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
public class SerialDirectoryListing {
	/*
	 * The term "serial" in this context refers to code run sequentially (one at a
	 * time), rather than concurrently (multiple at a time).
	 */

	/**
	 * Returns a directory listing for the given path. The original path and all
	 * directories are included in the returned set of paths.
	 *
	 * @param path directory to create listing
	 * @return paths found within directory and its subdirectories
	 * @throws IOException from {@link Files#newDirectoryStream(Path)}
	 */
	public static Set<Path> list(Path path) throws IOException {
		Set<Path> paths = new HashSet<>();

		if (Files.exists(path)) {
			paths.add(path);

			if (Files.isDirectory(path)) {
				list(path, paths);
			}
		}

		return paths;
	}

	/**
	 * Recursively lists the directory or adds the path without throwing any
	 * exceptions.
	 *
	 * @param path the path to add or list
	 * @param paths the set of all paths found thus far
	 * @throws IOException from {@link Files#newDirectoryStream(Path)}
	 */
	private static void list(Path path, Set<Path> paths) throws IOException {
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
			for (Path current : stream) {
				paths.add(current);

				if (Files.isDirectory(current)) {
					list(current, paths);
				}
			}
		}
	}

	/**
	 * Tests the directory listing for the current directory.
	 *
	 * @param args unused
	 * @throws IOException from {@link Files#newDirectoryStream(Path)}
	 */
	public static void main(String[] args) throws IOException {
		list(Path.of(".")).stream().forEach(System.out::println);
	}
}
