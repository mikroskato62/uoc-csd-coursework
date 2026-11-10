// ---------------------------------------------------------------------------------------------------- //
/*                       [ Java - HY252 - A2 - Assignment 5 - RIP - csd5748 ]                           */
// ---------------------------------------------------------------------------------------------------- //
// * Ένα Weapon – abstract class – έχει τα γνωρίσματα:
// • power – Η δύναμη του όπλου ορίζεται στο [1,5]
// • WeaponCarrier holder – Ο κάτοχος του όπλου (η κλάση περιγράφεται παρακάτω)
// Το συμβόλαιο του ADT ενός Weapon είναι:
// • Weapon() * Δημιουργεί ένα νέο όπλο με τυχαία δύναμη, χωρίς κάτοχο.
// • int getPower() * Επιστρέφει τη δύναμη του όπλου.
// • void setHolder(Object holder), WeaponCarrier getHolder() * Αναθέτει τον κάτοχο του όπλου,
//   κάνοντας τους κατάλληλους ελέγχους και, αντίστοιχα, επιστρέφει τον κάτοχο του όπλου.
// • abstract String toString() * Επιστρέφει τις πληροφορίες για ένα όπλο.
// ---------------------------------------------------------------------------------------------------- //

package A25.weapons;

public abstract class Weapon
{
    protected final int power;
    protected A25.creatures.WeaponCarrier holder;

    public Weapon()
    { this.power = (1 + (int)(Math.random() * 5)); }

    public int getPower()
    { return power; }

    public void setHolder(Object holder)
    { if (holder instanceof A25.creatures.WeaponCarrier) this.holder = (A25.creatures.WeaponCarrier)holder; }

    public A25.creatures.WeaponCarrier getHolder()
    { return holder; }

    public abstract String toString();
}

// The End ...