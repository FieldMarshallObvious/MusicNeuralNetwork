import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class midiNote {

    // Init class members
    private ArrayList<Integer> NotesList = new ArrayList<Integer>();
    private ArrayList<String> NotesName = new ArrayList<String>();

    String finalOutput;
    String fileLocation;

    private double note;
    private double note2;

    public midiNote() {
        // bank of all note values and their inharmonics
        // (inharmonics are because I am not sure exactly how our implemantation is
        // going to use these values)
        // 1st octave of note values

        Integer cflat1 = new Integer(23);
        Integer c1 = new Integer(24);
        Integer c#1 = new Integer(25);
        Integer dflat1 = new Integer(c#1);
        Integer d1 = new Integer(26);
        Integer d#1 = new Integer(27);
        Integer eflat1 = new Integer(d#1);
        Integer e1 = new Integer(28);
        Integer fflat1 = new Integer(e1);
        Integer f1 = new Integer(29);
        Integer f#1 = new Integer(30);
        Integer gflat1 = new Integer(f#1);
        Integer g1 = new Integer(31);
        Integer g#1 = new Integer(32);
        Integer aflat1 = new Integer(g#1);
        Integer a1 = new Integer(33);
        Integer a#1 = new Integer(34);
        Integer bflat1 = new Integer(a#1);
        Integer b1 = new Integer(35);
        // 2nd octave
        Integer cflat2 = new Integer(b1);
        Integer c2 = new Integer(36);
        Integer c#2 = new Integer(37);
        Integer dflat2 = new Integer(c#2);
        Integer d2 = new Integer(38);
        Integer d#2 = new Integer(39);
        Integer eflat2 = new Integer(d#2);
        Integer e2 = new Integer(40);
        Integer fflat2 = new Integer(e2);
        Integer f2 = new Integer(41);
        Integer f#2 = new Integer(42);
        Integer gflat2 = new Integer(f#2);
        Integer g2 = new Integer(43);
        Integer g#2 = new Integer(44);
        Integer aflat2 = new Integer(g#2);
        Integer a2 = new Integer(45);
        Integer a#2 = new Integer(46);
        Integer bflat2 = new Integer(a#2);
        Integer b2 = new Integer(47);
        // 2rd octave
        Integer cflat3 = new Integer(b2);
        Integer c3 = new Integer(48);
        Integer c#3 = new Integer(49);
        Integer dflat3 = new Integer(c#3);
        Integer d3 = new Integer(50);
        Integer d#3 = new Integer(51);
        Integer eflat3 = new Integer(d#3);
        Integer e3 = new Integer(52);
        Integer fflat3 = new Integer(e3);
        Integer f3 = new Integer(53);
        Integer f#3 = new Integer(54);
        Integer gflat3 = new Integer(f#3);
        Integer g3 = new Integer(55);
        Integer g#3 = new Integer(56);
        Integer aflat3 = new Integer(g#3);
        Integer a3 = new Integer(57);
        Integer a#3 = new Integer(58);
        Integer bflat3 = new Integer(a#3);
        Integer b3 = new Integer(59);
        // 4th octave
        Integer cflat4 = new Integer(b3);
        Integer c4 = new Integer(60);
        NotesList.add(c4);
        NotesName.add("c4");
        Integer c#4 = new Integer(61);
        NotesList.add(c#4);
        NotesName.add("c#4");
        Integer dflat4 = new Integer(c#4);
        NotesList.add(dflat4);
        NotesName.add("dflat4");
        Integer d4 = new Integer(62);
        NotesList.add(d4);
        NotesName.add("d4");
        Integer d#4 = new Integer(63);
        NotesList.add(d#4);
        NotesName.add("d#4");
        Integer eflat4 = new Integer(d#4);
        NotesList.add(eflat4);
        NotesName.add("eflat4");
        Integer e4 = new Integer(64);
        NotesList.add(e4);
        NotesName.add("e4");
        Integer fflat4 = new Integer(e4);
        NotesList.add(fflat4);
        NotesName.add("fflat4");
        Integer f4 = new Integer(65);
        NotesList.add(f4);
        NotesName.add("f4");
        Integer f#4 = new Integer(66);
        NotesList.add(f#4);
        NotesName.add("f#4");
        Integer gflat4 = new Integer(f#4);
        NotesList.add(gflat4);
        NotesName.add("gflat4");
        Integer g4 = new Integer(67);
        NotesList.add(g4);
        NotesName.add("g4");
        Integer g#4 = new Integer(68);
        NotesList.add(g#4);
        NotesName.add("g#4");
        Integer aflat4 = new Integer(g#4);
        NotesList.add(aflat4);
        NotesName.add("aflat4");
        Integer a4 = new Integer(69);
        NotesList.add(a4);
        NotesName.add("a4");
        Integer a#4 = new Integer(70);
        NotesList.add(a#4);
        NotesName.add("a#4");
        Integer bflat4 = new Integer(a#4);
        NotesList.add(bflat4);
        NotesName.add("blfat4");
        Integer b4 = new Integer(71);
        NotesList.add(b4);
        NotesName.add("b4");
        // 5th octave
        Integer cflat5 = new Integer(b4);
        NotesList.add(cflat5);
        NotesName.add("cl");
        Integer c5 = new Integer(72);
        NotesList.add(c5);
        NotesName.add("c5");
        Integer c#5 = new Integer(73);
        NotesList.add(c#5);
        NotesName.add("c#5");
        Integer dflat5 = new Integer(c#5);
        NotesList.add(dflat5);
        NotesName.add("dflat5");
        Integer d5 = new Integer(74);
        NotesList.add(d5);
        NotesName.add("d5");
        Integer d#5 = new Integer(75);
        NotesList.add(d#5);
        NotesName.add("d#5");
        Integer eflat5 = new Integer(d#5);
        NotesList.add(eflat5);
        NotesName.add("eflat5");
        Integer e5 = new Integer(76);
        NotesList.add(e5);
        NotesName.add("e5");
        Integer fflat5 = new Integer(e5);
        NotesList.add(fflat5);
        NotesName.add("fflat5");
        Integer f5 = new Integer(77);
        NotesList.add(f5);
        NotesName.add("f5");
        Integer f#5 = new Integer(78);
        NotesList.add(f#5);
        NotesName.add("f#5");
        Integer gflat5 = new Integer(f#5);
        NotesList.add(gflat5);
        NotesName.add("gflat5");
        Integer g5 = new Integer(79);
        NotesList.add(g5);
        NotesName.add("g5");
        Integer g#5 = new Integer(80);
        NotesList.add(g#5);
        NotesName.add("g#5");
        Integer aflat5 = new Integer(g#5);
        NotesList.add(aflat5);
        NotesName.add("aflat5");
        Integer a5 = new Integer(81);
        NotesList.add(a5);
        NotesName.add("a5");
        Integer a#5 = new Integer(82);
        NotesList.add(a#5);
        NotesName.add("a#5");
        Integer bflat5 = new Integer(a#5);
        NotesList.add(bflat5);
        NotesName.add("bflat5");
        Integer b5 = new Integer(83);
        NotesList.add(b5);
        NotesName.add("b5");
        // 6th octave
        Integer cflat6 = new Integer(b5);
        Integer c6 = new Integer(84);
        Integer c#6 = new Integer(85);
        Integer dflat6 = new Integer(c#6);
        Integer d6 = new Integer(86);
        Integer d#6 = new Integer(87);
        Integer eflat6 = new Integer(d#6);
        Integer e6 = new Integer(88);
        Integer fflat6 = new Integer(e6);
        Integer f6 = new Integer(89);
        Integer f#6 = new Integer(90);
        Integer gflat6 = new Integer(f#6);
        Integer g6 = new Integer(91);
        Integer g#6 = new Integer(92);
        Integer aflat6 = new Integer(g#6);
        Integer a6 = new Integer(93);
        Integer a#6 = new Integer(94);
        Integer bflat6 = new Integer(a#6);
        Integer b6 = new Integer(95);
        // 7th octave
        Integer cflat7 = new Integer(b6);
        Integer c7 = new Integer(96);
        Integer c#7 = new Integer(97);
        Integer dflat7 = new Integer(c#7);
        Integer d7 = new Integer(98);
        Integer d#7 = new Integer(99);
        Integer eflat7 = new Integer(d#7);
        Integer e7 = new Integer(100);
        Integer fflat7 = new Integer(e7);
        Integer f7 = new Integer(101);
        Integer f#7 = new Integer(102);
        Integer gflat7 = new Integer(f#7);
        Integer g7 = new Integer(103);
        Integer g#7 = new Integer(104);
        Integer aflat7 = new Integer(g#7);
        Integer a7 = new Integer(106);
        Integer a#7 = new Integer(107);
        Integer bflat7 = new Integer(a#7);
        Integer b7 = new Integer(108);

        // Methods used to input, manipulate, and analyze midiNote values
    }

    public void noteToNetConverter(String input, int size, String file) throws IOException {

        String[] inputString = input.split(" ");
        fileLocation = file;

        int line = 0;

        // Determine the line location
        for (String cur : inputString) {
            int x = 0;
            for (String curObject : NotesName) {
                x++;
                if (cur.equals(curObject)) {
                    line = x;
                    break;
                }
            }
        }
        setFinalOutputString(line, size);
    }

    public void netToNoteConvereter(String inputFile, int size, String outputFile) throws IOException
    {
        String noteSelection = selectedNotes(inputFile);

        String[] arrayofSelectedNodes = noteSelection.split(" ");

        for(String cur : arrayofSelectedNodes)
        {
            int x = 0;
            int curInt = Integer.valueOf(cur);
            for(int noteInt : NotesList)
            {
                x++;
                if(noteInt == curInt)
                {
                    finalOutput += NotesName.get(x) + " ";
                }
            }
        }

       usingBufferedWritter();
    }

    private void setFinalOutputString(int line, int size) throws IOException {

        ArrayList<Double> newDat = new ArrayList<Double>();

        // Set all lines that are not the specified line to 0.0
        for (int x = 0; x < size; x++) {
            if (x != line)
                newDat.add(0.0);
            else
                newDat.add(1.0);
        }

        finalOutput = newDat.stream().map(Object::toString).collect(Collectors.joining("\n"));
        // write file
        usingBufferedWritter();
    }

    private String selectedNotes(String inputFile) throws FileNotFoundException
    {
        String outputFile = "";
        Scanner inputReader = new Scanner(new File(inputFile));
        while(inputReader.hasNext())
        {
           outputFile += inputReader.nextLine() + " "; 
        }
        return outputFile;
    }

    private void usingBufferedWritter() throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(fileLocation));
        writer.write(finalOutput);
        writer.close();
    }
}

    © 2019 GitHub, Inc.
    Terms
    Privacy
    Security
    Status
    Help

    Contact GitHub
    Pricing
    API
    Training
    Blog
    About

