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
	
	private int compare(String x, String y) {
		int len1 = x.length();
        int len2 = y.length();
        int lim = Math.min(len1, len2);

        char v1[] = x.toCharArray();
        char v2[] = y.toCharArray();

        int k = 0;
        while (k < lim) {
            char c1 = v1[k];
            char c2 = v2[k];
            if (c1 != c2) {
                return c1 - c2;
            }
            k++;
        }
        return len1 - len2;
	}
	
	private void swap(int i, int j) {
		String tmp = names[i];
		names[i] = names[j];
		names[j] = tmp;
	}
	
	private void BubbleSort() {
		for(int i=0;i<names.length;++i) {
			for(int j=i+1;j<names.length;++j) {
				if( compare(names[i], names[j]) > 0) {
					swap(i, j);
				}
			}
		}
	}
	
	private void sort() {
		BubbleSort();
	}
	
	private void loadfile() {
		List<Integer> list = new ArrayList<Integer>();
		File file = new File("p022_names.txt");
		BufferedReader reader = null;

		try {
		    reader = new BufferedReader(new FileReader(file));
		    String text = null;

		    text = reader.readLine().replaceAll("\"", "");
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
	
	private int getScore(String s) {
		int score = 0;
		char []n = s.toCharArray();
		for(int i=0;i<n.length;++i) {
			score += (n[i] - 'A'+1); 
		}
		return score;
	}
	public long nameScore() {
		System.out.println(getScore("COLIN"));
		loadfile();
		sort();
		int sum = 0;
		for(int i = 0;i<names.length; ++i) {
			sum += getScore(names[i])*(i+1);
		}
		return sum;
	}

}
