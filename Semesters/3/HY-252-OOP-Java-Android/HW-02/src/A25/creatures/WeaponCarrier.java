// ---------------------------------------------------------------------------------------------------- //
/*                       [ Java - HY252 - A2 - Assignment 5 - RIP - csd5748 ]                           */
// ---------------------------------------------------------------------------------------------------- //
// * Ένας WeaponCarrier – abstract class – έχει το γνώρισμα:
// • Weapon weapon – ένα όπλο
// Το συμβόλαιο του ADT ενός WeaponCarrier είναι:
// • Weapon getWeapon() * Επιστρέφει το όπλο του WeaponCarrier.
// • Boolean hasWeapon() * Επιστρέφει true αν ο WeaponCarrier έχει όπλο.
// • void setWeapon(Weapon weapon) * Αναθέτει στον WeaponCarrier ένα όπλο
//   (προσοχή στην ανανέωση του πεδίου holder του Weapon).
// ---------------------------------------------------------------------------------------------------- //

package A25.creatures;

public abstract class WeaponCarrier
{
    protected A25.weapons.Weapon weapon;

    public A25.weapons.Weapon getWeapon()
    { return this.weapon; }

    public Boolean hasWeapon()
    { return (this.weapon !=  null); }

    public void setWeapon(A25.weapons.Weapon weapon)
    { this.weapon = weapon; if (weapon != null) weapon.setHolder(this); }
}

// The End ...