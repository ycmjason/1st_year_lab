;
; ***************************************************************
;       SKELETON: INTEL ASSEMBLER MATRIX MULTIPLY (LINUX)
; ***************************************************************
;
;
; --------------------------------------------------------------------------
; class matrix {
;     int ROWS              // ints are 64-bit
;     int COLS
;     int elem [ROWS][COLS]
;
;     void print () {
;         output.newline ()
;         for (int row=0; row < this.ROWS; row++) {
;             for (int col=0; col < this.COLS; cols++) {
;                 output.tab ()
;                 output.int (this.elem[row, col])
;             }
;             output.newline ()
;         }
;     }
;
;     void mult (matrix A, matrix B) {
;         for (int row=0; row < this.ROWS; row++) {
;             for (int col=0; col < this.COLS; cols++) {
;                 int sum = 0
;                 for (int k=0; k < A.COLS; k++)
;                     sum = sum + A.elem[row, k] * B.elem[k, col]
;                 this.elem [row, col] = sum
;             }
;         }
;     }
; }
; ---------------------------------------------------------------------------
; main () {
;     matrix matrixA, matrixB, matrixC  ; Declare and suitably initialise
;                                         matrices A, B and C
;     matrixA.print ()
;     matrixB.print ()
;     matrixC.mult (matrixA, matrixB)
;     matrixC.print ()
; }
; ---------------------------------------------------------------------------
;
; Notes:
; 1. For conditional jump instructions use the form 'Jxx NEAR label'  if label
;    is more than 127 bytes from the jump instruction Jxx.
;    For example use 'JGE NEAR end_for' instead of 'JGE end_for', if the
;    assembler complains that the label end_for is more than 127 bytes from
;    the JGE instruction with a message like 'short jump is out of  range'
;
;
; ---------------------------------------------------------------------------

segment .text
        global  _start
_start:

main:
          mov  rax, matrixA     ; matrixA.print ()
          push rax
          call matrix_print
          add  rsp, 8

          mov  rax, matrixB     ; matrixB.print ()
          push rax
          call matrix_print
          add  rsp, 8

          mov  rax, matrixB     ; matrixC.mult (matrixA, matrixB)
          push rax
          mov  rax, matrixA
          push rax
          mov  rax, matrixC
          push rax
          call matrix_mult
          add  rsp, 24          ; pop parameters & object reference

          mov  rax, matrixC     ; matrixC.print ()
          push rax
          call matrix_print
          add  rsp, 8

          call os_return                ; return to operating system

; ---------------------------------------------------------------------

;-- rax: i, rbx: j, rcx: element pointer, r8: matrix address
matrix_print:                   ; void matrix_print ()
         push rbp                ; setup base pointer
         mov  rbp, rsp

         call output_newline

         mov r8, [rbp+16]      ; set matrix address
         mov rcx, r8
         add rcx, 16
matrix_print_for_rows_init:     ; for(i=0; i< rows; i++){
         mov rax, 0              ; initialise counter to 0
matrix_print_for_rows_next:
         cmp rax, [r8]             ; compare counter and bound 
         jge matrix_print_for_rows_end      ; jump out of loop

matrix_print_for_cols_init:         ; for(j=0; j< cols; j++){
         mov rbx, 0              ; initialise counter to 0
matrix_print_for_cols_next:
;------------check counter and condition-----------
         cmp rbx, [r8+8]             ; compare counter and bound
         jge matrix_print_for_cols_end      ; jump out of loop
;------------/check counter and condition-----------
;------------printing tab-------
         call output_tab
;------------/printing tab-------
;------------printing element-------
         push qword [rcx]
         call output_int
         pop rdx
;------------/printing element-------
;------------increase element pointer-------
         add rcx, 8
;------------/increase element pointer-------
;------------increase j---------
         inc rbx
;------------/increase j---------
         jmp matrix_print_for_cols_next

matrix_print_for_cols_end:
         call output_newline

;------------increase i---------
         inc rax
;------------/increase i---------
         jmp matrix_print_for_rows_next

matrix_print_for_rows_end:
         pop  rbp                ; restore base pointer & return
         ret
         
         

;  --------------------------------------------------------------------------

matrix_mult:                    ; void matix_mult (matrix A, matrix B)

         push rbp                ; setup base pointer
         mov  rbp, rsp

         mov rax, [rbp+24]      ; rax points to matrix A
         mov rbx, [rbp+32]      ; rbx points to matrix B
         mov rcx, [rbp+16]      ; rcx points to matrix C

;-- for(int i=0; i < C.cols; i++){------------
matrix_mult_for_rows_init:
         mov r8, 0              ; initialise i = 0
matrix_mult_for_rows_next:
         cmp r8, [rcx]             ; compare counter and bound 
         jge matrix_mult_for_rows_end      ; jump out of loop



;------- for(int j=0; j < C.cols; j++){-------
matrix_mult_for_cols_init:
         mov r9, 0              ; initialise j = 0
matrix_mult_for_cols_next:
         cmp r9, [rcx+8]             ; compare counter and bound 
         jge matrix_mult_for_cols_end      ; jump out of loop
         
         mov rdx, 0             ; sum = 0
;---------- for(int k=0; k < A.cols; k++){----
matrix_mult_for_Acols_init:
         mov r10, 0            ; initialise k=0
matrix_mult_for_Acols_next:
         cmp r10, [rax+8]             ; compare counter and bound 
         jge matrix_mult_for_Acols_end      ; jump out of loop

;----------- A.getElem(i, k) -------
         push r8
         push r10
         push rax
         call matrix_getElem ; this will put the corresponding element to r12
         pop rax
         pop r10
         pop r8
         
         mov r11, r12

;---------- B.getElem(k, j) -------
         push r10
         push r9
         push rbx
         call matrix_getElem
         pop rbx
         pop r9
         pop r10

;-----------sum = sum + A.elem[i, k] * B.elem[k, j]-----
         imul r11, r12
         add rdx, r11
         
         inc r10
         jmp matrix_mult_for_Acols_next
matrix_mult_for_Acols_end:
;--------- C.elem [i, j] = sum -------
         push rdx
         push r8
         push r9
         push rcx
         call matrix_setElem
         pop rdx
         pop r9
         pop r8
         pop rdx

         inc r9
         jmp matrix_mult_for_cols_next
;------------------------ } 

matrix_mult_for_cols_end:
         inc r8
         jmp matrix_mult_for_rows_next
;------------------ } ------

matrix_mult_for_rows_end:
         pop  rbp                ; restore base pointer & return
         ret
;-------- } ----------------

matrix_getElem:
         push rbp            ; setup base pointer
         mov rbp, rsp

         push rax
         push rbx
         push rcx
         push rdx

         mov rax, [rbp+16]    ; rax points to the input matrix
         mov rbx, [rbp+24]    ; rbx = x to be returned
         mov rcx, [rbp+32]    ; rcx = y to be returned

         mov rdx, [rax+8]     ; rdx = col in matrix
         ; return value should be at (rax + 16 + x*8 + y*8*col)
         
         imul rdx, rcx
         imul rdx, 8
         imul rbx, 8
         add rdx, rbx
         add rdx, 16
         add rdx, rax

         mov r12, [rdx]      ; put element to r12

         pop rdx
         pop rcx
         pop rbx
         pop rax

         pop rbp
         ret

matrix_setElem:
         push rbp            ; setup base pointer
         mov rbp, rsp

         push rax
         push rbx
         push rcx
         push rdx
         push r8

         mov rax, [rbp+16]    ; rax points to the input matrix
         mov rbx, [rbp+24]    ; rbx = x to be set
         mov rcx, [rbp+32]    ; rcx = y to be set
         mov rdx, [rbp+40]    ; rdx = the value to be set

         mov r8, [rax+8]     ; r8 = col in matrix
         ; element to be set should be at (rax + 16 + x*8 + y*8*col)
         
         imul r8, rcx
         imul r8, 8
         imul rbx, 8
         add r8, rbx
         add r8, 16
         add r8, rax

         mov [r8], rdx     ; set element 

         pop r8
         pop rdx
         pop rcx
         pop rbx
         pop rax

         pop rbp
         ret
;
;matrix_getRow:
;         push rbp            ; setup base pointer
;         mov rbp, rsp
;
;         push rax
;
;         mov rax, [rbp+16]    ; rax points to the input matrix
;         mov r8, [rax+MATRIX_ROW_OFFSET]      ; put row value to r8
;
;         pop rax
;
;         pop rbp
;         ret
;
;matrix_getCol:
;         push rbp            ; setup base pointer
;         mov rbp, rsp
;
;         push rax
;
;         mov rax, [rbp+16]    ; rax points to the input matrix
;         mov r8, [rax+MATRIX_COL_OFFSET]     ; put col value to r8
;
;         pop rax
;
;         pop rbp
;         ret

; -------------------------------------------------------------------
;                    MY OWN CONSTANTS
;MATRIX_ROW_OFFSET equ 0
;MATRIX_COL_OFFSET equ 8

; ---------------------------------------------------------------------
;                    ADDITIONAL METHODS

CR      equ     13              ; carriage-return
LF      equ     10              ; line-feed
TAB     equ     9               ; tab
MINUS   equ     '-'             ; minus

LINUX   equ     80H             ; interupt number for entering Linux kernel
EXIT    equ     1               ; Linux system call 1 i.e. exit ()
WRITE   equ     4               ; Linux system call 4 i.e. write ()
STDOUT  equ     1               ; File descriptor 1 i.e. standard output

; ------------------------

os_return:
        mov  rax, EXIT          ; Linux system call 1 i.e. exit ()
        mov  rbx, 0             ; Error code 0 i.e. no errors
        int  LINUX              ; Interrupt Linux kernel

output_char:                    ; void output_char (ch)
        push rax
        push rbx
        push rcx
        push rdx
        push r8                ; r8..r11 are altered by Linux kernel interrupt
        push r9
        push r10
        push r11
        push qword [octetbuffer] ; (just to make output_char() re-entrant...)

        mov  rax, WRITE         ; Linux system call 4; i.e. write ()
        mov  rbx, STDOUT        ; File descriptor 1 i.e. standard output
        mov  rcx, [rsp+80]      ; fetch char from non-I/O-accessible segment
        mov  [octetbuffer], rcx ; load into 1-octet buffer
        lea  rcx, [octetbuffer] ; Address of 1-octet buffer
        mov  rdx, 1             ; Output 1 character only
        int  LINUX              ; Interrupt Linux kernel

        pop qword [octetbuffer]
        pop  r11
        pop  r10
        pop  r9
        pop  r8
        pop  rdx
        pop  rcx
        pop  rbx
        pop  rax
        ret

; ------------------------

output_newline:                 ; void output_newline ()
       push qword LF
       call output_char
       add rsp, 8
       ret

; ------------------------

output_tab:                     ; void output_tab ()
       push qword TAB
       call output_char
       add  rsp, 8
       ret

; ------------------------

output_minus:                   ; void output_minus()
       push qword MINUS
       call output_char
       add  rsp, 8
       ret

; ------------------------

output_int:                     ; void output_int (int N)
       push rbp
       mov  rbp, rsp

       ; rax=N then N/10, rdx=N%10, rbx=10

       push rax                ; save registers
       push rbx
       push rdx

       cmp  qword [rbp+16], 0 ; minus sign for negative numbers
       jge  L88

       call output_minus
       neg  qword [rbp+16]

L88:
       mov  rax, [rbp+16]       ; rax = N
       mov  rdx, 0              ; rdx:rax = N (unsigned equivalent of "cqo")
       mov  rbx, 10
       idiv rbx                ; rax=N/10, rdx=N%10

       cmp  rax, 0              ; skip if N<10
       je   L99

       push rax                ; output.int (N / 10)
       call output_int
       add  rsp, 8

L99:
       add  rdx, '0'           ; output char for digit N % 10
       push rdx
       call output_char
       add  rsp, 8

       pop  rdx                ; restore registers
       pop  rbx
       pop  rax
       pop  rbp
       ret


; ---------------------------------------------------------------------

segment .data

        ; Declare test matrices
matrixA DQ 2                    ; ROWS
        DQ 3                    ; COLS
        DQ 1, 2, 3              ; 1st row
        DQ 4, 5, 6              ; 2nd row

matrixB DQ 3                    ; ROWS
        DQ 2                    ; COLS
        DQ 1, 2                 ; 1st row
        DQ 3, 4                 ; 2nd row
        DQ 5, 6                 ; 3rd row

matrixC DQ 2                    ; ROWS
        DQ 2                    ; COLS
        DQ 0, 0                 ; space for ROWS*COLS ints
        DQ 0, 0                 ; (for filling in with matrixA*matrixB)

; ---------------------------------------------------------------------

        ; The following is used by output_char - do not disturb
        ;
        ; space in I/O-accessible segment for 1-octet output buffer
octetbuffer     DQ 0            ; (qword as choice of size on stack)
