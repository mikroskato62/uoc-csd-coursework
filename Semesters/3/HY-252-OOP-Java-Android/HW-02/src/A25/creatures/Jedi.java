// ---------------------------------------------------------------------------------------------------- //
/*                       [ Java - HY252 - A2 - Assignment 5 - RIP - csd5748 ]                           */
// ---------------------------------------------------------------------------------------------------- //
// Ένας Jedi χαρακτηρίζεται (επιπλέον) από τα γνωρίσματα:
// • rank – παίρνει τις τιμές {YOUNG-LING, PADAWAN, KNIGHT, MASTER, GRAND_MASTER}
// • force_level – Παίρνει τιμές [1-100]
// Το συμβόλαιο του ADT ενός Jedi είναι:
// • Jedi(String firstName, String lastName) * Δημιουργεί ένα νέο Jedi με το δοθέν ονοματεπώνυμο και υγεία 10,
//   αναθέτει μια τυχαία τιμή στο force_level και καθορίζει το rank σύμφωνα με την αντιστοίχιση:
//   YOUNG-LING: [1-10), PADAWAN: [10-30), KNIGHT: [30-70), MASTER: [70-90), GRAND_MASTER: [90-100].
// • int getForceLevel(), RANK getRank * Επιστρέφουν τις τιμές του force_level και rank, αντίστοιχα.
// • void attack(Object adversary) * Επιτίθεται σε έναν αντίπαλο (ο οποίος δεν επιτρέπεται να είναι Jedi),
//   μειώνοντας την κατάσταση του αντιπάλου. Αν ο Jedi διαθέτει όπλο, τότε η μείωση των πόντων του αντιπάλου
//   είναι ίση με τη δύναμη του όπλου συν την ιεραρχία του Jedi (0 για YOUNG-LING, 1 για PADAWAN, 2 για KNIGHT, …).
//   Αν ο Jedi δεν έχει όπλο, τότε η μείωση των πόντων του αντιπάλου είναι ίση με την ιεραρχία του Jedi + 1.
// • String toString() * Επιστρέφει το ονοματεπώνυμο, τη θέση στην ιεραρχία και το όπλο του Jedi.
// ---------------------------------------------------------------------------------------------------- //

package A25.creatures;

public class Jedi extends Humanoid implements Combatant
{
    private enum RANK {YOUNGLING, PADAWAN, KNIGHT, MASTER, GRAND_MASTER}
    private final RANK rank;
    private final int force_level;

    public Jedi(String firstName, String lastName)
    {
        super(firstName, lastName, 10);
        this.force_level = (1 + (int)(Math.random() * 100));
        switch (getForceLevel() / 10)
        {
            case 0: rank = RANK.YOUNGLING; break;
            case 1: case 2: rank = RANK.PADAWAN; break;
            case 3: case 4: case 5: case 6: rank = RANK.KNIGHT; break;
            case 7: case 8: rank = RANK.MASTER; break;
            default: rank = RANK.GRAND_MASTER;
        }
    }

    public int getForceLevel()
    { return this.force_level; }

    RANK getRank()
    { return this.rank; }

    @Override public void attack(Object adversary)
    {
        Combatant opponent = (Combatant) adversary;
        if (this == adversary || getCondition() <= 0 || !(adversary instanceof Combatant) || opponent instanceof Jedi) return;

        int damage = hasWeapon() ? weapon.getPower() + rank.ordinal() : rank.ordinal() + 1;
        int newcond = Math.max((opponent.getCondition() - damage), 0);
        opponent.setCondition(newcond);

        System.out.println("[+] " + getCallSign() + " attacked " + opponent.getCallSign() + " for " + damage + " damage, new condition: " + newcond + ".");
    }

    @Override public String toString()
    { return ("Jedi Name: " + getCallSign() + " | Health: " + this.getHealth() + " | Force: " + getForceLevel() + " | Rank: " + getRank()) + " (" + rank.ordinal() + ") | Weapon: " + weapon.toString(); }
}

// The End ...