import java.io.*;

public class GuitarHero {

	String [][] song = null;
	boolean [] thatB = null;
	static String [] noteNames;
	public GuitarHero () {
		
		try{
			BufferedReader reader = new BufferedReader(new FileReader("Guitar Song.txt"));
			int row = 0;
			String temp;
			noteNames = new String [] {"G#", "G", "F#", "F", "E", "D#", "D", "C#", "C", "B", "A#", "A", "G#", "G", "F#", "F", "E", "D#", "D", "C#", "C", "B", "A#", "A", "G#", "G", "F#", "F", "E"};

			int [][] fretPos = {{29, 24, 19, 14, 10, 5},
								{28, 23, 18, 13, 9, 4},
								{27, 22, 17, 12, 8, 3},
								{26, 21, 16, 11, 7, 2},
								{25, 20, 15, 10, 6, 1}};
			
			boolean first = true;
			while((temp=reader.readLine()) != null) {
				String [] measures = temp.split(",");
				if(first) {
					setUpAnswer(measures);
					first = false;
				}

				for(int x = 0; x < measures.length; x++) {
					for(int y = 0; y < 6; y++) {
						int fp = fretPos[row][y];
						if(measures[x].charAt(y) == '*' || measures[x].charAt(y) == 'o'){
							song[fp][x+1] = "o";
							if(row == 4 && y == 3 && measures[x].charAt(y) == '*'){
								thatB[x+1] = true;
							}
						}
					}
				}
				row++;
			}	
			for(int c = 1; c < song[0].length; c++) {
				boolean highNote = false;
				boolean hitTheB = false;
				
				for(int r = 1; r < song.length; r++) {
					if(!highNote) {
						if(song[r][c].equals("o"))
							highNote = true;

						if(r == 11 && song[r-1][c].equals("o") && song[r][c].equals("o"))
						song[r][c] = "";

					} else {
						if(song[r][c].equals("o"))
						song[r][c] = "";
					}
					
					if(r == 10) {
						if(thatB[c]) 
						song[r][c] = "o";
					}
					if((r <= 10 && r%5 == 0) || (r > 10 && r%5 == 4)){
						highNote = false;
					}
				}
			}
			for(int x = 0; x < song.length; x++) {
				for(int y = 0; y < song[x].length; y++) {
					System.out.print(song[x][y] + "\t");
				}
				System.out.println();
			}

			reader.close();
		} catch(IOException e) {
			e.printStackTrace();
		}

	}

	public void setUpAnswer(String [] measures) {
		song = new String[30][measures.length+1];
		for(int r = 0; r < song.length; r++) {
			for(int c = 0; c < song[0].length; c++) {
				song[r][c] = "";
			}
		}

		for(int r = 1; r <= noteNames.length; r++) {
			song[r][0] = noteNames[r-1];
		}

		for(int x = 1; x <= measures.length; x++) {
			song[0][x] = "" + x;
		}
		song[0][0] = "Measure";

		thatB = new boolean[measures.length + 1];
	}

	public static void main (String [] args){
		GuitarHero app = new GuitarHero();
	}

}