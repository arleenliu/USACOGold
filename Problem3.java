import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Problem3 {
	public static int findGreatestIndexLessEqualTo(ArrayList<Integer> list, int v) {
		int s = 0, e = list.size() - 1;

		while (s <= e) {
			int m = (s + e) / 2;
			if (list.get(m) <= v)
				e = m - 1;
			else
				s = m + 1;
		}
		return s;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader r = new BufferedReader(new FileReader("hayfeast.in"));
		String line = r.readLine();

		String[] words = line.split(" ");
		int N = Integer.parseInt(words[0]);
		long M = Long.parseLong(words[1]);
		ArrayList<Integer> listOfSpicenessInRange = new ArrayList<Integer>();

		int[] F = new int[N];
		int[] S = new int[N];

		// read in barn connections
		for (int i = 0; i < N; i++) {
			line = r.readLine();

			words = line.split(" ");
			F[i] = Integer.parseInt(words[0]);
			S[i] = Integer.parseInt(words[1]);
		}

		long totalF = 0;
		int maxS = 0; // maximum spiceness in the range
		int minOverallSpicyness = Integer.MAX_VALUE;
		int s = 0; // beginning of range
		int e = -1; // end of range
		while ((e != N - 1 || totalF >= M)) { // only terminate if already at the end and value is small, since
												// decreasing the range more does not help
			if (totalF < M) { // not a valid range, must increase
				e++;
				totalF += F[e];

				int posToInsert = findGreatestIndexLessEqualTo(listOfSpicenessInRange, S[e]);
				listOfSpicenessInRange.add(posToInsert, S[e]);
			} else {
				// increase the range at the end does not make sense any more, it only increases
				// the max spiceness
				// so decrease
				// should be exact match, since we know for sure this value was added to the
				// list
				int posToDelete = findGreatestIndexLessEqualTo(listOfSpicenessInRange, S[s]);
				listOfSpicenessInRange.remove(posToDelete);
				totalF -= F[s];
				s++;
			}

			if (totalF >= M &&
			// decreasing list, first element is the max value in the range
					listOfSpicenessInRange.get(0) < minOverallSpicyness)
				minOverallSpicyness = listOfSpicenessInRange.get(0); // found a smaller value
		}

		try {
			PrintWriter writer = new PrintWriter("hayfeast.out", "UTF-8");
			writer.println(minOverallSpicyness);
			System.out.println(minOverallSpicyness);
			writer.close();
		} catch (IOException eee) {
			// do something
		}

		return;

	}
}
