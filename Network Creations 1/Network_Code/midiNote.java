public class midiNote
{
    private double note;
    private double note2;

    public void midiNote()
    {
        note = 0.0;
        note2 = 0.0;
    }

    public void midiNote(double inputNote, double inputNote2)
    {
        note = inputNote;
        note2 = inputNote2;
    }
    
    public double getDelta()
    {
        return note - note2;
    }
}
