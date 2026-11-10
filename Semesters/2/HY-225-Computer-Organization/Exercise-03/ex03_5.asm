# UOC - CSD - HY225 - EX03(5) - csd5748 !

             .data
lowercase:   .asciz "abc"
uppercase:   .asciz "ABC"
numbers:     .asciz "123"
             .text

# --------------------------------------------------
#
# Comments (i):
#
# Character | ASCII (hexadecimal) | ASCII (binary)
#    a      |        0x61         |    01100001
#    b      |        0x62         |    01100010
#    c      |        0x63         |    01100011
#   ...     |        ...          |      ...
#    A      |        0x41         |    01000001 
#    B      |        0x42         |    01000010
#    C      |        0x43         |    01000011
#   ...     |        ...          |      ...
#    1      |        0x31         |    00110001
#    2      |        0x32         |    00110010
#    3      |        0x33         |    00110011
#   ...     |        ...          |      ...
#
# As we can see, the sets of characters start from a
# specific value and increase by one bit each time !
#
# --------------------------------------------------
#
# Comments (ii):
#
# Based on the table above, the string "abc\0" as a
# 32-bit integer will be: 0x61 0x62 0x63 0x00 (hex).
# Or [ 97 98 99 00 ] (10) in decimal ... (0x00 = 0).
#
# - Little Endian (LS -> MS bytes):
# The byte order in memory: [00 99 98 97] (decimal).
# Integer = 6513249 in decimal because of calculation:
# (0 * 256^3) + (99 * 256^2) + (98 * 256^1) + (97 * 256^0)
# = (0) + (99 * 65536) + (98 * 256) + (97 * 1) =
# = 0 + 6488064 + 25088 + 97 = 6513249 (10).
#
# - Big Endian (MS -> LS bytes):
# The byte order in memory: [97 98 99 00] (decimal).
# Integer = 1633837824 in decimal because of calculation:
# (97 * 256^3) + (98 * 256^2) + (99 * 256^1) + (0 * 256^0)
# = (97 * 16777216) + (98 * 65536) + (99 * 256) + (0 * 1)
# = 1627389952 + 6422528 + 25344 + 0 = 1633837824 (10).
# 
# --------------------------------------------------
#
# Question (iii):

main:

        lw  x11, lowercase      # Load the 32-bit word of the null-terminated string into a register.
    
        addi    x17, x0, 1      # Environment call code for print integers.
        add     x10, x11, x0    # Copy argument (whole string!).
        ecall                   # Print the string as integer - Expected "6513249" (Little Endian)...
    
  
# End of my code...