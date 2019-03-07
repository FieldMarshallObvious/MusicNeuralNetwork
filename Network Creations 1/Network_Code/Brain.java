//import DryWetMidi;


//currently not quite sure how to work with midi files in this so this is essentially just the psuedo code

public class Brain
{
	public void main(String[] args)
	{
		IntialNetwork initNet = new IntialNetwork(88, 88, 0.3);
		ForgettingNetwork forgettingNet = new ForgettingNetwork(44, 44, 0.3);
		SelectionNetwork selectNet = new SelectionNetwork(22, 22, 0.3);

		initNet.

	}
	/*
	public static void ConvertMidiToText(string midiFilePath, string textFilePath)
	{
 		   var midiFile = MidiFile.Read(midiFilePath);

  		  File.WriteAllLines(textFilePath, midiFile.GetNotes()
            .Select(n => $"{n.NoteNumber} {n.Time} {n.Length}"));
	}

	public String readMidiText(string textFilePath)
	{

	}*/

}
