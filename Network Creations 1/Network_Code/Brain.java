import DryWetMidi;


//currently not quite sure how to work with midi files in this so this is essentially just the psuedo code

public class Brain
{
	public void main(String[] args)
	{
		
	}
	public static void ConvertMidiToText(string midiFilePath, string textFilePath)
	{
 		   var midiFile = MidiFile.Read(midiFilePath);

  		  File.WriteAllLines(textFilePath, midiFile.GetNotes()
            .Select(n => $"{n.NoteNumber} {n.Time} {n.Length}"));
	}

	public String readMidiText(string textFilePath)
	{

	}

}
