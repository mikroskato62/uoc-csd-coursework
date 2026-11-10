// ---------------------------------------------------------------------------------------------------- //
/*                       [ Java - HY252 - A2 - Assignment 5 - RIP - csd5748 ]                           */
// ---------------------------------------------------------------------------------------------------- //
// * Ένα Humanoid – abstract class – χαρακτηρίζεται από τα γνωρίσματα:
// • firstName – Δεν μπορεί να είναι κενό
// • lastName – Δεν μπορεί να είναι κενό
// • health – Παίρνει τιμές [0-10]
// Το συμβόλαιο του ADT ενός Humanoid είναι: [ Όνομα μεθόδου * Περιγραφή μεθόδου ]:
// • Humanoid (String firstName, String lastName, int health) * Δημιουργεί ένα στιγμιότυπο ενός Humanoid με ονοματεπώνυμο και υγεία.
// • Humanoid (String firstName, String lastName) * Καλεί τον παραπάνω κονστράκτορα με default υγεία 10.
// • void setHealth(int health), int getHealth() * Θέτει (και επιστρέφει αντίστοιχα) την τιμή του health (προσοχή στους περιορισμούς).
// • void setCondition(int cond), int getCondition() * Θέτει (και επιστρέφει αντίστοιχα) την υγεία (κατάσταση) του ανθρωποειδούς...
// • boolean isDefeated() * Επιστρέφει false αν το ανθρωποειδές είναι ζωντανό, αλλιώς true.
// • String getCallSign() * Επιστρέφει το ονοματεπώνυμο του ανθρωποειδούς.
// • abstract String toString() * Επιστρέφει όλες τις πληροφορίες για ένα ανθρωποειδές.
// ---------------------------------------------------------------------------------------------------- //

package A25.creatures;

public abstract class Humanoid extends WeaponCarrier
{
    protected final String firstName;
    protected final String lastName;
    protected int health;

    public Humanoid (String firstName, String lastName, int health)
    {
        if (firstName == null || firstName.isEmpty() || lastName == null || lastName.isEmpty())
        { throw new IllegalArgumentException("[Error] Names cannot be null or empty ..."); }
        this.firstName = firstName; this.lastName = lastName; setHealth(health);
    }
    public Humanoid (String firstName, String lastName)
    {
        if (firstName == null || firstName.isEmpty() || lastName == null || lastName.isEmpty())
        { throw new IllegalArgumentException("[Error] Names cannot be null or empty ..."); }
        this.firstName = firstName; this.lastName = lastName; setHealth(10);
    }

    public void setHealth (int health)
    { if (health >= 0 && health <= 10) this.health = health; }
    public int getHealth()
    { return this.health; }

    public void setCondition (int cond)
    { setHealth(cond); }
    public int getCondition()
    { return getHealth(); }

    public boolean isDefeated()
    { return getCondition() <= 0; }

    public String getCallSign()
    { return (this.firstName + " " + this.lastName); }

    public abstract String toString();
}

// The End ...