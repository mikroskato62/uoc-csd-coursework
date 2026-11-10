// ---------------------------------------------------------------------------------------------------- //
/*                       [ Java - HY252 - A2 - Assignment 5 - RIP - csd5748 ]                           */
// ---------------------------------------------------------------------------------------------------- //
// * Ένα Droid – abstract class – έχει τα γνωρίσματα:
// • name – Το όνομα του ρομπότ
// • Humanoid master – Ο αφέντης του ρομπότ
// Το συμβόλαιο του ADT ενός Droid είναι:
// • Droid() * Δημιουργεί ένα νέο ρομπότ, με όνομα αποτελούμενο από 4 τυχαίους αλφαριθμητικούς χαρακτήρες [A-Z,0-9], χωρίς αφέντη.
// • Droid(Humanoid master) * Καλεί τον παραπάνω κονστράκτορα και επιπλέον αναθέτει έναν αφέντη στο ρομπότ.
// • void setMaster(Object master) * Αναθέτει στο droid έναν αφέντη (ο οποίος δεν μπορεί να είναι Droid).
// • Humanoid getMaster() * Επιστρέφει τον αφέντη του ρομπότ.
// • String getName() * Επιστρέφει το όνομα του ρομπότ.
// • boolean isFree() * Επιστρέφει true αν το ρομπότ δεν έχει αφέντη ή αν ο αφέντης του έχει ηττηθεί, διαφορετικά false.
// • String toString() * Επιστρέφει το όνομα του ρομπότ και το όνομα του αφέντη του.
// ---------------------------------------------------------------------------------------------------- //

package A25.creatures;

public abstract class Droid
{
    protected final String name;
    protected Humanoid master;

    public Droid()
    {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(4);
        for (int i = 0; i < 4; i++)
        {
            int index = (int)(Math.random() * chars.length());
            sb.append(chars.charAt(index));
        }
        this.name = sb.toString();
    }

    public Droid(Humanoid master)
    { this(); setMaster(master); }

    public void setMaster(Object master)
    { if (!(master instanceof Droid)) this.master = (Humanoid) master; }

    public Humanoid getMaster()
    { return master; }

    public String getName()
    { return name; }

    public boolean isFree()
    { return master == null || master.isDefeated(); }

    public String toString()
    { return ("Name: " + getName() + " | Master: " + (master != null ? master.getCallSign() : "None")); }
}

// The End ...