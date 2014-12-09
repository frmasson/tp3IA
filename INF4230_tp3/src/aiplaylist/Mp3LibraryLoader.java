package aiplaylist;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Mp3LibraryLoader implements LibraryLoader {

	@Override
	public Collection<Item> loadLibrary(String libraryPath) {
		File dir = new File(libraryPath);
		Collection<Item> musicLibrary = new ArrayList<Item>();

		extractMetadata(dir, musicLibrary);
		
		return musicLibrary;
	}

	private Collection<Item> extractMetadata(File song,
			Collection<Item> musicLibrary) {
		if (song.isDirectory()) {
			for (File f : song.listFiles()) {
				extractMetadata(f, musicLibrary);
			}
		} else {
			InputStream input;
			try {
				input = new FileInputStream(song);
				ContentHandler handler = new DefaultHandler();
				Metadata metadata = new Metadata();
				Parser parser = new Mp3Parser();
				ParseContext parseCtx = new ParseContext();
				parser.parse(input, handler, metadata, parseCtx);
				input.close();
				Collection<Feature> features = new TreeSet<Feature>();
				for (String key : metadata.names()) {
					features.add(new Feature(key, metadata.get(key)));
				}
				musicLibrary.add(new Song(metadata
						.get(TikaCoreProperties.TITLE), features));
			} catch (IOException | SAXException | TikaException e) {
				e.printStackTrace();
			}
		}
		return null;
	}		

}
