// ---------------------------------------------------------------------------------------------------- //
/*                       [ Java - HY252 - A2 - Assignment 5 - RIP - csd5748 ]                           */
// ---------------------------------------------------------------------------------------------------- //
// * Ένα BattleDroid έχει το (επιπλέον) γνώρισμα:
// • condition – Παίρνει τιμές στο [0,10]
// Το συμβόλαιο του ADT ενός BattleDroid είναι:
// • BattleDroid() * Δημιουργεί ένα νέο BattleDroid με condition 10.
// • BattleDroid(Humanoid master) * Δημιουργεί ένα νέο BattleDroid με condition 10 και αναθέτει έναν αφέντη.
// • int getCondition(), void setCondition(int condition) * Επιστρέφει και αναθέτει αντίστοιχα την τιμή του condition.
// • String getCallSign() * Επιστρέφει το όνομα του BattleDroid.
// • void attack(Object adversary) * Επιτίθεται σε έναν αντίπαλο μειώνοντας την κατάσταση του αντιπάλου κατά 1.
// • boolean isDefeated() * Επιστρέφει true αν η τιμή του condition είναι 0, διαφορετικά false.
// • void protectMasterFrom(Object adversary) * Επιτίθεται στον adversary. Προϋποθέτει ότι:
//   - Ο αφέντης δεν έχει ηττηθεί.
//   - Ο adversary μπορεί να επιτεθεί στον αφέντη.
// ---------------------------------------------------------------------------------------------------- //

package A25.creatures;

public class BattleDroid extends Droid implements Combatant
{
    private int condition;

    public BattleDroid()
    { setCondition(10); }

    public BattleDroid(Humanoid master)
    { setCondition(10); setMaster(master); }

    @Override public int getCondition()
    { return condition; }
    @Override public void setCondition(int condition)
    { if (condition >= 0 && condition <= 10) this.condition = condition; }

    @Override public String getCallSign()
    { return getName(); }

    @Override public void attack(Object adversary)
    {
        if (adversary instanceof Humanoid)
        {
            Humanoid opponent = (Humanoid) adversary;
            opponent.setCondition(opponent.getCondition() - 1);
            System.out.println("[+] " + this.getCallSign() + " protects " + this.master.getCallSign() + ", attacked " + opponent.getCallSign() + " for 1 damage, new condition: " + opponent.getCondition() + ".");
        }
    }

    @Override public boolean isDefeated()
    { return (getCondition() <= 0); }

    public void protectMasterFrom(Object adversary)
    {
        if (master == null || master.isDefeated() || !(adversary instanceof Combatant) || this.getCondition() <= 0) return;
        attack(adversary);
    }

    @Override public String toString()
    { return ("Battle Droid " + super.toString() + " | Condition: " + this.getCondition()); }
}

// The End ...