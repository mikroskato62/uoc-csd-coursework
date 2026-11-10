# [UOC-CSD]: HY-119: Assignment 2: csd5748.

# Imports:
import matplotlib.pyplot as plt
from PIL import Image
import numpy as np
import os


# Functions:
def compute_column_space(image_matrix):

    # Convert the matrix to floats for precision:
    A = image_matrix.astype(float)

    # Initialization:
    independent_columns = []

    # Iterate through each column:
    for i in range(A.shape[1]):

        # Get the i-th column:
        column = A[:, i]

        # If there are no columns yet:
        if len(independent_columns) == 0:

            # Keep this one for sure:
            independent_columns.append(column)

        # Else:
        else:

            # Create a matrix with the already selected independent columns:
            temp_matrix = np.column_stack(independent_columns)

            # Try to solve the system 'temp_matrix * x = columns':
            try:

                # If a solution exists, the column is not independent:
                solution = np.linalg.lstsq(temp_matrix, column, rcond=None)[0]

                # If it cannot be written exactly, it is independent:
                if not np.allclose(temp_matrix @ solution, column): independent_columns.append(column)

            # Except:
            except:

                # In case the least squares fails, keep the column:
                independent_columns.append(column)

    # Finally, return the matrix containing only the independent columns:
    return np.column_stack(independent_columns)



def plot_images(images, title):
    plt.figure(figsize=(10, 3))
    plt.gcf().canvas.manager.set_window_title('csd5748')
    for i, img in enumerate(images):
        plt.subplot(1, len(images), i + 1)
        plt.imshow(img)
    plt.suptitle(title, fontsize=20)
    plt.tight_layout()
    plt.savefig('images.png')
    plt.show()



# Main:
if __name__ == '__main__':

    # The images and python code must be in the same directory!
    img_directory = 'number_images'
    # I believe there was a typo in 'img_names[2]'; 'one_a.png' (duplicated) instead of 'one_b.png' !?
    img_names = ['one_a.png', 'three.png', 'one_b.png', 'two.png']
    images = []
    for i_n in img_names:
        img = Image.open(os.path.join(img_directory, i_n))
        images.append(np.array(img))
    plot_images(images=images, title='The Original Images:')
    img_col_spaces = []
    for img in images: img_col_spaces.append(compute_column_space(img))
    plot_images(images=img_col_spaces, title= 'The Column Space of Images:')

    # Find the new dimension of each image (for next exercise):
    print('\n')
    for i, img in enumerate(images):
        col_space = compute_column_space(img)
        img_col_spaces.append(col_space)
#       print(f"- Image {i+1}: Dimension of Column Space = {col_space.shape[1]}.")


# End of code.
# --------------------------------------------------
# Σημειώσεις - Υπόλοιπα υποερωτήματα:

# - Γιατί λείπουν ακόμα μερικά εικονοστοιχεία στον χώρο στηλών ?
# Παρόλο που ο υπολογισμός του χώρου στηλών αναπληρώνει αρκετά από τα κενά pixels,
# εξακολουθούν να υπάρχουν ελλείψεις, διότι το column space περιλαμβάνει μόνο τα
# διανύσματα (στήλες) που είναι γραμμικά ανεξάρτητα. Αν ορισμένες πληροφορίες των
# αρχικών pixels ήταν εξαρχής χαμένες ή ασυμπλήρωτες, ο χώρος στηλών δεν μπορεί να
# τις ανακατασκευάσει τέλεια, καθώς δεν υπάρχει αρκετή πληροφορία για να αναπαραχθεί
# όλο το σχήμα της εικόνας.

# - Ποια είναι η διάσταση του χώρου στηλών για κάθε εικόνα ?
# Για την πρώτη εικόνα (one_a.png), η διάσταση του χώρου στηλών είναι 8.
# Για τη δεύτερη εικόνα (three.png), η διάσταση του χώρου στηλών είναι 9.
# Για την τρίτη εικόνα (one_b.png), η διάσταση του χώρου στηλών είναι 4.
# Για την τέταρτη εικόνα (two.png), η διάσταση του χώρου στηλών είναι 13.

# - Εάν υπολογίζατε τους χώρους γραμμών, τι θα συνέβαινε ? Θα διορθώνονταν τα κενά ?
# Εάν υπολογίζαμε τον χώρο γραμμών (row space) αντί για τον χώρο στηλών, θα
# αναπληρώναμε την πληροφορία σε οριζόντιο επίπεδο, δηλαδή κατά μήκος των γραμμών
# της εικόνας. Είναι πιθανό ότι ορισμένα κενά pixels θα διορθώνονταν καλύτερα σε
# γραμμές που έχουν παρόμοια μοτίβα. Ωστόσο, γενικά το αποτέλεσμα δεν θα ήταν τόσο
# καλό όσο με τον χώρο στηλών, διότι η δομή των αριθμών (όπως το σχήμα του ψηφίου)
# τείνει να διατηρείται πιο πιστά στις στήλες και όχι τόσο στις γραμμές.


# Τέλος!