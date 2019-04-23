import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class midiNote {
    private ArrayList<Object> NotesList = new ArrayList<Object>();
    private double note;
    private double note2;

    // bank of all note values and their inharmonics
    // (inharmonics are because I am not sure exactly how our implemantation is
    // going to use these values)
    // 1st octave of note values

    Integer cflat1 = 23;
    Integer c1 = 24;
    Integer csharp1 = 25;
    Integer dflat1 = csharp1;
    Integer d1 = 26;
    Integer dsharp1 = 27;
    Integer eflat1 = dsharp1;
    Integer e1 = 28;
    Integer fflat1 = e1;
    Integer f1 = 29;
    Integer fsharp1 = 30;
    Integer gflat1 = fsharp1;
    Integer g1 = 31;
    Integer gsharp1 = 32;
    Integer aflat1 = gsharp1;
    Integer a1 = 33;
    Integer asharp1 = 34;
    Integer bflat1 = asharp1;
    Integer b1 = 35;
    // 2nd octave
    Integer cflat2 = b1;
    Integer c2 = 36;
    Integer csharp2 = 37;
    Integer dflat2 = csharp2;
    Integer d2 = 38;
    Integer dsharp2 = 39;
    Integer eflat2 = dsharp2;
    Integer e2 = 40;
    Integer fflat2 = e2;
    Integer f2 = 41;
    Integer fsharp2 = 42;
    Integer gflat2 = fsharp2;
    Integer g2 = 43;
    Integer gsharp2 = 44;
    Integer aflat2 = gsharp2;
    Integer a2 = 45;
    Integer asharp2 = 46;
    Integer bflat2 = asharp2;
    Integer b2 = 47;
    // 2rd octave
    Integer cflat3 = b2;
    Integer c3 = 48;
    Integer csharp3 = 49;
    Integer dflat3 = csharp3;
    Integer d3 = 50;
    Integer dsharp3 = 51;
    Integer eflat3 = dsharp3;
    Integer e3 = 52;
    Integer fflat3 = e3;
    Integer f3 = 53;
    Integer fsharp3 = 54;
    Integer gflat3 = fsharp3;
    Integer g3 = 55;
    Integer gsharp3 = 56;
    Integer aflat3 = gsharp3;
    Integer a3 = 57;
    Integer asharp3 = 58;
    Integer bflat3 = asharp3;
    Integer b3 = 59;
    // 4th octave
    Integer cflat4 = b3;
    Integer c4 = 60;
    NotesList.add(c4);
    Integer csharp4 = 61;
    NotesList.add(csharp4);
    Integer dflat4 = csharp4;
    NotesList.add(dflat4);
    Integer d4 = 62;
    NotesList.add(d4);
    Integer dsharp4 = 63;
    NotesList.add(dsharp4);
    Integer eflat4 = dsharp4;
    NotesList.add(eflat4);
    Integer e4 = 64;
    NotesList.add(e4);
    Integer fflat4 = e4;
    NotesList.add(fflat4);
    Integer f4 = 65;
    NotesList.add(f4);
    Integer fsharp4 = 66;
    NotesList.add(fsharp4);
    Integer gflat4 = fsharp4;
    NotesList.add(gflat4);
    Integer g4 = 67;
    NotesList.add(g4);
    Integer gsharp4 = 68;
    NotesList.add(gsharp4);
    Integer aflat4 = gsharp4;
    NotesList.add(aflat4);
    Integer a4 = 69;
    NotesList.add(a4);
    Integer asharp4 = 70;
    NotesList.add(asharp4);
    Integer bflat4 = asharp4;
    NotesList.add(blfat4);
    Integer b4 = 71;
    NotesList.add(b4);
    // 5th octave
    Integer cflat5 = b4;
    NotesList.add(cflat5);
    Integer c5 = 72;
    NotesList.add(c5);
    Integer csharp5 = 73;
    NotesList.add(csharp5);
    Integer dflat5 = csharp5;
    NotesList.add(dflat5);
    Integer d5 = 74;
    NotesList.add(d5);
    Integer dsharp5 = 75;
    NotesList.add(dsharp5);
    Integer eflat5 = dsharp5;
    NotesList.add(eflat5);
    Integer e5 = 76;
    NotesList.add(e5);
    Integer fflat5 = e5;
    NotesList.add(fflat5);
    Integer f5 = 77;
    NotesList.add(f5);
    Integer fsharp5 = 78;
    NotesList.add(fsharp5);
    Integer gflat5 = fsharp5;
    NotesList.add(glfat5);
    Integer g5 = 79;
    NotesList.add(g5);
    Integer gsharp5 = 80;
    NotesList.add(gsharp5);
    Integer aflat5 = gsharp5;
    NotesList.add(aflat5);
    Integer a5 = 81;
    NotesList.add(a5);
    Integer asharp5 = 82;
    NotesList.add(asharp5);
    Integer bflat5 = asharp5;
    NotesList.add(bflat5);
    Integer b5 = 83;
    NotesList.add(b5);
    // 6th octave
    Integer cflat6 = b5;
    Integer c6 = 84;
    Integer csharp6 = 85;
    Integer dflat6 = csharp6;
    Integer d6 = 86;
    Integer dsharp6 = 87;
    Integer eflat6 = dsharp6;
    Integer e6 = 88;
    Integer fflat6 = e6;
    Integer f6 = 89;
    Integer fsharp6 = 90;
    Integer gflat6 = fsharp6;
    Integer g6 = 91;
    Integer gsharp6 = 92;
    Integer aflat6 = gsharp6;
    Integer a6 = 93;
    Integer asharp6 = 94;
    Integer bflat6 = asharp6;
    Integer b6 = 95;
    // 7th octave
    Integer cflat7 = b6;
    Integer c7 = 96;
    Integer csharp7 = 97;
    Integer dflat7 = csharp7;
    Integer d7 = 98;
    Integer dsharp7 = 99;
    Integer eflat7 = dsharp7;
    Integer e7 = 100;
    Integer fflat7 = e7;
    Integer f7 = 101;
    Integer fsharp7 = 102;
    Integer gflat7 = fsharp7;
    Integer g7 = 103;
    Integer gsharp7 = 104;
    Integer aflat7 = gsharp7;
    Integer a7 = 106;
    Integer asharp7 = 107;
    Integer bflat7 = asharp7;
    Integer b7 = 108;

    // Methods used to input, manipulate, and analyze midiNote values

    public void MidiNote() throws FileNotFoundException {
        /*
         * File file = new File("midiNotes.dat"); Scanner scan = new Scanner(file);
         * LinkedList list = new LinkedList<>(); while(scan.hasNext()) {
         * 
         * }
         */
        note = 0.0;
        note2 = 0.0;
    }

    public void MidiNote(double inputNote, double inputNote2) {
        note = inputNote;
        note2 = inputNote2;
    }

    public double getDelta() {
        return note - note2;
    }

    public void midiNote() {
        File textNoteFile = new File("midiNotes.dat");
        ArrayList listOfNotes = new ArrayList();
        // Iterator iter = new Iterator<Double>();

        // while(iter.hasNext())
        // {
        // listOfNotes.add(iter.next());
        // }

    }

}
