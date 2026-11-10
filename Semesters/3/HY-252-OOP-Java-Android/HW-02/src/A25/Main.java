// ---------------------------------------------------------------------------------------------------- //
/*                        [ Java - HY252 - A2 - Assignment 5 - RIP - csd5748 ]                          */
// [!] Please note this is a new class (file) created to implement the exact given instructions method. //
// ---------------------------------------------------------------------------------------------------- //
// * Σε αυτή την άσκηση καλείστε να μοντελοποιήσετε ένα τμήμα του περιβάλλοντος ενός παιχνιδιού ρόλων.
// Σε αυτό το παιχνίδι εμφανίζονται διάφορες οντότητες από τη σειρά Star Wars, όπως Jedi, Sith και
// Droids. Οι Jedi και οι Sith είναι ανθρωποειδή (Humanoid) και έχουν κάποια κοινά χαρακτηριστικά, όπως
// όνομα, επώνυμο και υγεία. Επιπλέον, οι Jedi έχουν τη δική τους ιεραρχία (YOUNGLING, PADAWAN,
// KNIGHT, MASTER, GRAND_MASTER), η οποία καθορίζεται από τον βαθμό στον οποίο έχουν “κατακτήσει”
// μια υπερφυσική δύναμη (the Force). Τα ρομπότ (Droid), τα οποία χωρίζονται σε ιατρικά
// (MedicalDroid) και πολεμικά (BattleDroid), έχουν όλα έναν αφέντη (ανθρωποειδές). Τα ιατρικά ρομπότ
// μπορούν να θεραπεύσουν τον αφέντη τους, ενώ τα πολεμικά ρομπότ μπορούν να προστατεύσουν τον
// αφέντη τους σε κάποια μάχη. Σε μία μάχη μπορούν να συμμετέχουν μόνο οντότητες που υλοποιούν τη
// διεπαφή Combatant (δηλαδή Humanoids και BattleDroids). Τα ανθρωποειδή εξειδικεύουν την κλάση
// WeaponCarrier και μπορούν να φέρουν όπλο (Weapon), το οποίο τα καθιστά πιο ισχυρά στη μάχη,
// καθώς τους δίνει επιπλέον δύναμη. Ένα είδος όπλου είναι και το φωτόσπαθο (Lightsaber), το οποίο
// ανάλογα με τον ιδιοκτήτη του έχει και διαφορετικό χρώμα (μπλε για Jedi, κόκκινο για Sith). Παρακάτω
// σας δίνονται ένα ενδεικτικό UML διάγραμμα των ζητούμενων κλάσεων, καθώς και αναλυτική περιγραφή τους:
// < σχήμα στην αρχή της σελίδας 11 του αρχείου με τις οδηγίες >
// Είστε ελεύθεροι να χρησιμοποιήσετε Exceptions, όπου εσείς το κρίνετε.
// [+]
// ---------------------------------------------------------------------------------------------------- //

// Package:
package A25;

// Imports:
import A25.creatures.*;
import A25.weapons.*;
import java.util.Random;

// The class:
public class Main
{
    // Main (edited for better outputs ...):
    public static void main(String[] args)
    {
        System.out.println("[?] csd5748: Starting (Example) Battle ...");
        System.out.println("[*] ----------------------------------------------------------------------------------------------------------------------------- [*]");
        Jedi obiwan = new Jedi("Obiwan", "Kenobi");
        obiwan.setWeapon(new Lightsaber());
        System.out.println("[1] " + obiwan);
        Sith vader = new Sith("Vader");
        vader.setWeapon(new Lightsaber());
        System.out.println("[2] " + vader);
        MedicalDroid docDroid = new MedicalDroid();
        docDroid.setMaster(vader);
        System.out.println("[3] " + docDroid);
        BattleDroid battleDroid = new BattleDroid();
        battleDroid.setMaster(obiwan);
        System.out.println("[4] " + battleDroid);
        System.out.println("[*] ----------------------------------------------------------------------------------------------------------------------------- [*]");
        try
        {
            int round = 0;
            while (!obiwan.isDefeated() && !vader.isDefeated())
            {
                System.out.println("[-] Round " + (++round) + ": ");
                if (new Random().nextBoolean())
                {
                    obiwan.attack(vader);
                    if (!vader.isDefeated())
                    { vader.attack(obiwan); }
                }
                else
                {
                    vader.attack(obiwan);
                    if (!obiwan.isDefeated())
                    { obiwan.attack(vader); }
                }
                if (!docDroid.isFree())
                { docDroid.healMaster(); }
                if (!battleDroid.isFree() && !vader.isDefeated())
                { battleDroid.protectMasterFrom(vader); }
            }
        }
        catch (Exception e)
        {
            System.err.println(e);
        }
        finally
        {
            System.out.println("[*] ----------------------------------------------------------------------------------------------------------------------------- [*]");
            System.out.print("[!] Winner: "); System.out.print(obiwan.isDefeated() ? vader.getCallSign() : obiwan.getCallSign()); System.out.println(" ...");
            System.out.println("[*] ----------------------------------------------------------------------------------------------------------------------------- [*]");
            System.out.println("[?] csd5748: The End ...");
        }
    }
}

// ---------------------------------------------------------------------------------------------------- //

// Expected output (exact example):
// Jedi:
// Obiwan Kenobi
// Rank: KNIGHT
// Weapon: BLUE lightsaber, of power 2
// Sith:
// Darth Vader
// Weapon: RED lightsaber, of power 1
// Droid:
// 5F2G
// Master: Darth Vader
// Droid:
// N3R7
// Master: Obiwan Kenobi
// Obiwan Kenobi attacked Darth Vader
// Darth Vader's condition:6
// Darth Vader attacked Obiwan Kenobi
// Obiwan Kenobi's condition:9
// 5F2G healed 3 units of its master Darth Vader
// Darth Vader's condition:9
// N3R7 attacked Darth Vader
// Darth Vader's condition:8
// N3R7 was just protecting its master Obiwan Kenobi
// Obiwan Kenobi attacked Darth Vader
// Darth Vader's condition:4
// Darth Vader attacked Obiwan Kenobi
// Obiwan Kenobi's condition:8
// 5F2G healed 3 units of its master Darth Vader
// Darth Vader's condition:7
// N3R7 attacked Darth Vader
// Darth Vader's condition:6
// N3R7 was just protecting its master Obiwan Kenobi
// Obiwan Kenobi attacked Darth Vader
// Darth Vader's condition:2
// Darth Vader attacked Obiwan Kenobi
// Obiwan Kenobi's condition:7
// 5F2G healed 3 units of its master Darth Vader
// Darth Vader's condition:5
// N3R7 attacked Darth Vader
// Darth Vader's condition:4
// N3R7 was just protecting its master Obiwan Kenobi
// Darth Vader attacked Obiwan Kenobi
// Obiwan Kenobi's condition:6
// Obiwan Kenobi attacked Darth Vader
// Darth Vader's condition:0
// Obiwan Kenobi won!

// ---------------------------------------------------------------------------------------------------- //
// * Παραδοτέα (10 αρχεία .java): Δημιουργήστε ένα φάκελο Α23 και μέσα 2 υποφακέλους creatures (η
// οποία θα περιέχει τα java αρχεία: BattleDroid.java, Combatant.java, Droid.java, Humanoid.java,
// Jedi.java, MedicalDroid.java, Sith.java, WeaponCarrier.java) και weapons (η οποία θα περιέχει τα java
// αρχεία: Lightsaber.java, Weapon.java) ...
// ---------------------------------------------------------------------------------------------------- //

// [!] The End ...