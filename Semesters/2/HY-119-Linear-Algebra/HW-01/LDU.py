# UOC - CSD - Spring2025 - HY119 - Assignment1 - csd5748.

# Σύμφωνα με την εκφώνηση της ένατης άσκησης από την πρώτη σειρά:
# Υλοποιούμε μόνο μια συνάρτηση σε αρχείο "LDU.py" και γλώσσα "python".
# Ορίσματα: τετραγωνικός πίνακας "Α".
# Υπολογίζουμε και επιστρέφουμε τους τέσσερεις πίνακες "P, L, D, U".
# Τρόπος καλέσματος συνάρτησης: "ldu(A)".
# Επιτρέπεται η χρήση έτοιμων συναρτήσεων/τελεστών πολ/μού πινάκων!
# (αφού η άσκηση δεν αναφέρει κάποιο περιορισμό όπως η προηγούμενη)
# Ο κώδικας (συνάρτηση):

import numpy as np

def ldu(A):

    N = len(A) or len(A[0])
    P = np.eye(N)
    Atonos = A.copy()
    L = np.eye(N)
    D = np.zeros((N, N))
    U = np.eye(N)

    for i in range(N):
        odigos = i
        for j in range((i + 1), N):
            if (abs(Atonos[j, i]) > abs(Atonos[odigos, i])):
                odigos = j

        if (odigos != i):
            Atonos[[i, odigos]] = Atonos[[odigos, i]]
            P[[i, odigos]] = P[[odigos, i]]
            L[[i, odigos], :i] = L[[odigos, i], :i]

        D[i, i] = Atonos[i, i]
        if (D[i, i] != 0):
            for j in range(i + 1, N):
                L[j, i] = (Atonos[j, i] / D[i, i])
                Atonos[j, i:] -= L[j, i] * Atonos[i, i:]

    for i in range(N):
        for j in range(i + 1, N):
            if (D[i, i] != 0):
                U[i, j] = (Atonos[i, j] / D[i, i])

    return (P, L, D, U)


# [EXTRA] Παράδειγμα ορθής χρήσης με βάση την παρόμοια άσκηση 11α:
# A = np.array([[0, 1, 1], [1, 0, 1], [4, 5, 6]], dtype=float)
# P, L, D, U = ldu(A)
# print("\nP =\n", P, "\nA =\n", A, "\nL =\n", L, "\nD =\n", D, "\nU =\n", U, "\n")
# Τέλος ...