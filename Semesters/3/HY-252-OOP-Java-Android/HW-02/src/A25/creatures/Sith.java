// ---------------------------------------------------------------------------------------------------- //
/*                       [ Java - HY252 - A2 - Assignment 5 - RIP - csd5748 ]                           */
// ---------------------------------------------------------------------------------------------------- //
// * Ένας Sith δεν έχει επιπλέον γνωρίσματα.
// Το συμβόλαιο του ADT ενός Sith είναι:
// • Sith(String lastName) * Δημιουργεί ένα νέο Sith με μικρό όνομα "Darth", το δοθέν επώνυμο και υγεία 10.
// • void attack(Object adversary) * Επιτίθεται σε έναν αντίπαλο μειώνοντας την κατάσταση του αντιπάλου.
//   Αν ο Sith διαθέτει όπλο, η μείωση των πόντων του αντιπάλου είναι ίση με τη δύναμη του όπλου.
//   Αν ο Sith δεν έχει όπλο, τότε η μείωση των πόντων του αντιπάλου είναι 1.
// • String toString() * Επιστρέφει το ονοματεπώνυμο και το όπλο του Sith.
// ---------------------------------------------------------------------------------------------------- //

package A25.creatures;

public class Sith extends Humanoid implements Combatant
{
    public Sith(String lastName)
    { super("Darth", lastName, 10); }

    @Override public void attack(Object adversary)
    {
        Combatant opponent = (Combatant) adversary;
        if (this == adversary || getCondition() <= 0 || adversary == null) return;

        int damage = hasWeapon() ? weapon.getPower() : 1;
        int newcond = Math.max(0, opponent.getCondition() - damage);
        opponent.setCondition(newcond);

        System.out.println("[+] " + getCallSign() + " attacked " + opponent.getCallSign() + " for " + damage + " damage, new condition: " + newcond + ".");
    }

    @Override public String toString()
    { return ("Sith Name: " + getCallSign() + " | Health: " + this.getHealth() + " | Weapon: " + weapon.toString()); }
}

// The End ...