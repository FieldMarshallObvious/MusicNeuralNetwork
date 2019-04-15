import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class midiNote
{
    private double note;
    private double note2;



    // bank of all note values and their inharmonics 
    // (inharmonics are because I am not sure exactly how our implemantation is going to use these values)
    
                               //1st octave of note values
   
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
                                //2nd octave
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
                                //3rd octave
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
                                //4th octave
    Integer cflat4 = b3;
    Integer c4 = 60;
    Integer csharp4 = 61;
    Integer dflat4 = csharp4;
    Integer d4 = 62;
    Integer dsharp4 = 63;
    Integer eflat4 = dsharp4;
    Integer e4 = 64;
    Integer fflat4 = e4;
    Integer f4 = 65;
    Integer fsharp4 = 66;
    Integer gflat4 = fsharp4;
    Integer g4 = 67;
    Integer gsharp4 = 68;
    Integer aflat4 = gsharp4;
    Integer a4 = 69;
    Integer asharp4 = 70;
    Integer bflat4 = asharp4;
    Integer b4 = 71;
                                //5th octave
    Integer cflat5 = b4;
    Integer c5 = 72;
    Integer csharp5 = 73;
    Integer dflat5 = csharp5;
    Integer d5 = 74;
    Integer dsharp5 = 75;
    Integer eflat5 = dsharp5;
    Integer e5 = 76;
    Integer fflat5 = e5;
    Integer f5 = 77;
    Integer fsharp5 = 78;
    Integer gflat5 = fsharp5;
    Integer g5 = 79;
    Integer gsharp5 = 80;
    Integer aflat5 = gsharp5;
    Integer a5 = 81;
    Integer asharp5 = 82;
    Integer bflat5 = asharp5;
    Integer b5 = 83;
                                //6th octave
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
                                //7th octave
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
    Integer gsharp7 =104;
    Integer aflat7 = gsharp7;
    Integer a7 = 106;
    Integer asharp7 = 107;
    Integer bflat7 = asharp7;
    Integer b7 = 108;

    note = 0.0;
    note2 = 0.0;
    
    
 //Methods used to input, manipulate, and analyze midiNote values

    public void MidiNote() throws FileNotFoundException
    {
        File textNoteFile = new File("midiNotes.dat");
        ArrayList listOfNotes = new ArrayList();
        Iterator iter = new Iterator<Double>();
        
        while(iter.hasNext())
        {
            listOfNotes.add(iter.next());
        }
      
    }

    public void MidiNote(double inputNote, double inputNote2)
    {
        note = inputNote;
        note2 = inputNote2;
    }
    //method no longer in use for current network*****
    public double getDelta()
    {
        return note - note2;
    }

}
