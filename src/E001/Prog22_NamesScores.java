package E001;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Prog22_NamesScores implements EulerProg{
	
	private String[] names;
 
	public static void main(String[] args) {
		System.out.println(new Prog22_NamesScores().run());
	}
	
	@Override
	public String run() {
		return String.valueOf(nameScore());
	}
	
	private void loadfile() {
		List<Integer> list = new ArrayList<Integer>();
		File file = new File("p022_names.txt");
		BufferedReader reader = null;

		try {
		    reader = new BufferedReader(new FileReader(file));
		    String text = null;

		    text = reader.readLine();
		    this.names = StringUtils.split(text, ","); 
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    try {
		        if (reader != null) {
		            reader.close();
		        }
		    } catch (IOException e) {
		    }
		}

	}
	public long nameScore() {
		loadfile();
		return 0;
	}

}
