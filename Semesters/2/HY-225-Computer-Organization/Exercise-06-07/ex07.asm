# UOC - CSD - HY225 - EX07 - csd5748!

.data
    text1:   .asciz "Please enter: int > 0 to save - int <= 0 to continue: \n"
    text2:   .asciz "Please enter: int >= 0 to print - int < 0 to exit: \n"
    end:     .asciz "The End - Thanks! \n"
    space:   .asciz " "
    newline: .asciz "\n"
    	     .align 2 
.text

main:
    addi   x17, x0, 4       # Environment call code for print string.
    la     x10, text1       # Pseudo-instruction: address of string.
    ecall                   # Print the string.
    jal    x1, node_alloc   # Call "node_alloc" to create dummy node.
    add    x8, x10, x0      # Save "head ptr" from the previous funct.
    add    x9, x10, x0      # Save "tail ptr" from the previous funct.
    sw     x0, 0(x10)       # Store 0 in node's data (the dummy node).
    sw     x0, 4(x10)       # Store 0 in node's next pointer (NULL).

create_list_loop:
    jal   x1, read_int         # Call "read_int" to get an int from user.
    add   x5, x10, x0          # Copy the user's input from function.
    ble   x5, x0, scan_start   # If x5 <= 0 then goto "scan_start".
    jal   x1, node_alloc       # Call "node_alloc" to create a new node.
    sw    x5, 0(x10)           # Store the input in node's data.
    sw    x0, 4(x10)           # Update the node's next pointer to NULL (0).
    sw    x10, 4(x9)           # Update the previous tail to point new node.
    add   x9, x10, x0          # Update the new node (new tail).
    j     create_list_loop     # Jump back to "construct_loop".

scan_start:
    addi   x17, x0, 4       # Environment call code for print string.
    la     x10, text2       # Pseudo-instruction: address of string.
    ecall                   # Print the string and continue.
scan_loop:
    jal   x1, read_int      # Call "read_int" for the user's int value.
    add   x9, x10, x0       # Copy the value from the function.
    blt   x9, x0, exit      # If x9 < 0 then jump/go to "exit".
    add   x10, x8, x0       # Copy the "head pointer".
    add   x11, x9, x0       # Copy the value again.
    jal   x1, search_list   # Call the "search_list" function.
    j     scan_loop         # Jump back to "scan_loop".

# End of "main".

# Procedure: read_int(void) - Returns: x10 = user's integer input.
read_int:
	addi   x17, x0, 5   # Environment call code for read int.
        ecall               # Read a line containing an integer.
	jr     x1, 0        # Return to the "caller".

# Procedure: node_alloc(void) - Returns: x10 = address of the new node.
node_alloc:
    addi   x17, x0, 9    # Environment call code for "Sbrk".
    addi   x10, x0, 4    # First half of node size (the int).
    addi   x10, x10, 4   # Second half of node size (the ptr).
    ecall                # Allocate 8bytes in memory and return address.
    jr     x1, 0         # Return to the "caller".

# Procedure: print_node(address, value) - Arguments: x10 = node address, x11 = comparison value.
print_node:
    lw     x5, 0(x10)        	     # Load the node's data.
    ble    x5, x11, print_node_end   # If x5 <= x11 then jump to "pn_end".
    add    x6, x10, x0               # The backup node address.
    add    x10, x5, x0       	     # The data to print later.
    addi   x17, x0, 1        	     # Environment call code for print int.
    ecall                    	     # Print (one) integer.
    addi   x17, x0, 4        	     # Environment call code for print string.
    la     x10, space        	     # Pseudo-instruction: address of string.
    ecall                    	     # Print a space for better appearence.
    add    x10, x6, x0       	     # Restore the node's address.
print_node_end:
	jr   x1, 0   # Return to the "caller".

# Procedure: search_list(start, value) - Arguments: x10 = list start, x11 = comparison value.
search_list:
    addi   x2, x2, -12   # Decrease "stack ptr" by 12 (create stack space).
    sw     x1, 0(x2)     # Store "ra" at stack offset 0.
    sw     x8, 4(x2)     # Store "s0" at stack offset 4.
    add    x8, x10, x0   # Copy the current node value.
search_list_loop:
    beq   x8, x0, search_list_end   # If ptr == NULL then jump to "sl_end".
    add   x10, x8, x0        	    # Copy the node address for "print_node".
    jal   x1, print_node            # Call "print_node" to print if int if it's greater.
    lw    x8, 4(x8)          	    # Set next pointer to the next node.
    j     search_list_loop   	    # Jump back to "sl_loop".
search_list_end:
    addi   x17, x0, 4     # Environment call code for print string.
    la     x10, newline   # Pseudo-instruction: address of string.
    ecall                 # Print a new line.
    lw     x1, 0(x2)      # Load "ra" from stack offset 0.
    lw     x8, 4(x2)      # Load "s0" from stack offset 4.
    addi   x2, x2, 12     # Increase "sp" by 12 (free the stack space).
    jr     x1, 0          # Return to "caller".

exit:
    addi   x17, x0, 4     # Environment call code for print string.
    la     x10, end       # Pseudo-instruction: address of string.
    ecall                 # Print the last thing.
    addi   x17, x0, 10    # Environment call code for exit.
    ecall                 # Exit/terminate the program.
    
# The end of the code ...