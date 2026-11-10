// ---------------------------------------------------------------------------------------------------- //
/*                       [ Java - HY252 - A2 - Assignment 5 - RIP - csd5748 ]                           */
// ---------------------------------------------------------------------------------------------------- //
// * Ένα Lightsaber έχει το γνώρισμα:
// • color – μπορεί να πάρει τις τιμές {RED, BLUE}
// Το συμβόλαιο ενός Lightsaber είναι:
// • Lightsaber() * Δημιουργεί ένα νέο φωτόσπαθο με τυχαία δύναμη, χωρίς κάτοχο και χωρίς χρώμα.
// • int getColor() * Επιστρέφει το χρώμα του φωτόσπαθου.
// • void setHolder(Object holder) * Αναθέτει τον κάτοχο του όπλου, κάνοντας τους κατάλληλους ελέγχους
//                                   και αναθέτοντας το κατάλληλο χρώμα στο φωτόσπαθο.
// • String toString() * Επιστρέφει το χρώμα και τη δύναμη του φωτόσπαθου.
// ---------------------------------------------------------------------------------------------------- //

package A25.weapons;

public class Lightsaber extends Weapon
{
    private enum COLOR {RED, BLUE}
    private COLOR color;

    public Lightsaber()
    { super(); }

    COLOR getColor()
    { return this.color; }

    @Override public void setHolder(Object holder)
    {
        super.setHolder(holder);
        if (holder instanceof A25.creatures.Jedi) this.color = COLOR.BLUE;
        else if (holder instanceof A25.creatures.Sith) this.color = COLOR.RED;
    }

    public String toString()
    { return color.name() + "-Lightsaber | Power: " + getPower(); }
}

// The End ...