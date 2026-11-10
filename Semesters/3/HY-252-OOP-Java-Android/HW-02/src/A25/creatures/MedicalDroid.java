// ---------------------------------------------------------------------------------------------------- //
/*                       [ Java - HY252 - A2 - Assignment 5 - RIP - csd5748 ]                           */
// ---------------------------------------------------------------------------------------------------- //
// * Ένα MedicalDroid έχει τα (επιπλέον) γνωρίσματα:
// • medicalSkills – Παίρνει τιμές στο [1,3]
// Το συμβόλαιο του ADT ενός MedicalDroid είναι:
// • MedicalDroid() * Δημιουργεί ένα νέο MedicalDroid με τυχαίο medicalSkills.
// • MedicalDroid(Humanoid master) * Δημιουργεί ένα νέο MedicalDroid με τυχαίο medicalSkills και αναθέτει σε αυτό έναν αφέντη.
// • Humanoid getMedicalSkills() * Επιστρέφει την τιμή του medicalSkills ---> Λάθος εκφώνησης !!!!!
// • String getName() * Επιστρέφει το όνομα του ρομπότ.
// • void healMaster() * Βελτιώνει την κατάσταση του αφέντη κατά medicalSkills μονάδες. Προσοχή στους περιορισμούς!
// ---------------------------------------------------------------------------------------------------- //

package A25.creatures;

public class MedicalDroid extends Droid
{
    private final int medicalSkills;

    public MedicalDroid()
    { medicalSkills = (1 + (int)(Math.random() * 3)); }

    public MedicalDroid(Humanoid master)
    { this(); setMaster(master); }

    public int getMedicalSkills()
    { return medicalSkills; }

    @Override public String getName()
    { return super.getName(); }

    public void healMaster()
    {
        int newcond = Math.min((master.getCondition() + medicalSkills), 10);
        if (this.master.getHealth() > 0) this.master.setCondition(newcond);
        System.out.println("[+] " + this.getName() + " healed " + this.master.getCallSign() + " for " + this.getMedicalSkills() + ", new condition: " +  this.master.getCondition() + ".");
    }

    @Override public String toString()
    { return ("Medical Droid " + super.toString() + " | MedicalSkills: " + this.getMedicalSkills()); }
}

// The End ...