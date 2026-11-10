# UOC - CSD - HY225 - EX03(4) - csd5748 !

	.data					       # Initialize data memory:
text1:  .asciz "Enter 8 integers one by one please:"   # The prompt.
strnl:  .asciz "\n"				       # The new line string.
        .align 2       				       # Álign with 2^2=4.
a:	.space 32   				       # Empty space for 8 ints of 4 bytes - array a[].
	.text

main:
    
	la 	x5, a         # Load address of array a[] at register x5.
	add 	x28, x0, x0   # Initialize i=0.
	addi 	x29, x0, 8    # End loop variable for 8 loops.

	addi	x17, x0, 4   # Environment call code for print string.
	la	x10, text1   # Pseudo-instruction: address of string.
        ecall                # Print the string (prompt) from text1.

        addi    x17, x0, 4   # Environment call code for print string.
        la      x10, strnl   # Pseudo-instruction: address of string.
        ecall                # Print a new line.

loop1:

        addi    x17, x0, 5     # Environment call code for read int.
        ecall                  # Read a line containing an integer.
        add     x26, x10, x0   # Copy returned int from x10 to x26.
	
	sw 	x26, 0(x5)   # Save the input (word) to the array (offset=0).
        addi 	x5, x5, 4    # Move to the next memory address (one int - 4 bytes).

	addi	x28, x28, 1       # Increase i=i+1.
	bne 	x28, x29, loop1   # Check if the 8 loops are completed (!=).

# End of loop 1.

        addi    x17, x0, 4   # Environment call code for print string.
        la      x10, strnl   # Pseudo-instruction: address of string.
        ecall                # Print a new line.

	la 	x5, a	     # Load address of array a[] at register x5 again.
    	addi 	x5, x5, 28   # Load address of last element of array a[] ((8-1)*4=28 bytes).

	add 	x28, x0, x0   # Initialize i=0 again for another loop.
	addi 	x29, x0, 8    # End loop variable for 8 loops again for another loop..

loop2:

	lw 	x26, 0(x5)   # Load the last int (word) from array a[] (offset=0).

	add 	x27, x26, x26   # Double the number by adding it with itself.
	add 	x27, x27, x27   # Double again.
	add 	x27, x27, x26   # Add the initial number to the sum (total 5x).

        addi    x17, x0, 1      # Environment call code for print int.
        add     x10, x27, x0    # Copy argument (number times five) to x10.
        ecall                   # Print the integer in x10.

        addi    x17, x0, 4   # Environment call code for print string.
        la      x10, strnl   # Pseudo-instruction: address of string.
        ecall                # Print a new line.

	addi 	x5, x5, -4   # Move to the next (previous) memory address (one int = 4 bytes).
    
	addi	x28, x28, 1       # Increase i=i+1.
	bne 	x28, x29, loop2   # Check if the 8 loops are completed or not (!=).

# End of loop 2.

        addi    x17, x0, 4   # Environment call code for print string.
        la      x10, strnl   # Pseudo-instruction: address of string.
        ecall                # Print a new line.

	j main   # Unconditionally jump back to main (infinity loop).
	
	
# End of my code ...