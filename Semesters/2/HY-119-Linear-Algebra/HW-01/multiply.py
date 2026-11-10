# UOC - CSD - Spring2025 - HY119 - Assignment1 - csd5748.

# Σύμφωνα με την εκφώνηση της τέταρτης άσκησης από την πρώτη σειρά:
# Υλοποιούμε μια συνάρτηση σε ένα αρχείο "multiply.py" και γλώσσα "python".
# Ορίσματα (λογικά - δεν είναι σαφές): πίνακες "Anxm" και "Bmxk".
# Έλεγχος εφικτού πολ/μού: εκτυπώνουμε μήνυμα και επιστρέφουμε κενό πίνακα.
# Αλλιώς: δημιουργία και επιστροφή αρχικά κενού πίνακα με κατάλληλες διαστάσεις.
# Δεν επιτρέπεται η χρήση έτοιμων συναρτήσεων/τελεστών πολλαπλασιασμού.
# Ο κώδικας (συνάρτηση):

def multiplymatrix(A, B):

    Na, Ma = len(A), len(A[0])
    Mb, Kb = len(B), len(B[0])

    if (Ma != Mb):
        print("Μη εφικτός πολλαπλασιασμός πινάκων.")
        return []

    C = []
    for i in range(Na):
        newrow = []
        for j in range(Kb):
            newrow.append(0)
        C.append(newrow)

    for i in range(Na):
        for j in range(Kb):
            for k in range(Ma):
                C[i][j] += A[i][k] * B[k][j]

    return C

# Τέλος ...