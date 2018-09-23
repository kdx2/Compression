# Reverse Polish Notation
RPN is a mathematical notation invented by a Polish mathematician called Jan Łukasiewicz in 1924. It is also called __postfix notation__.

It's main idea is that any coefficients or numbers must preceed as much as possible the mathematical operators, e.g. + - * / ... On the other side, the operands must be in the end,
that is where the word 'postfix' in the name comes from.

The rearrangement which the reverse polish notation proposes removes the need for brackets
which we use heavily in the handwritten maths we do (infix notation).

For instance,
3 + 5 is infix notation. The operantor '+' stands in-between the operators 3 and 5.
3 5 + is postfix (RPN) notation, which the program converts the upper one to.

3 + (1-5)\*2 is again what? -> infix notation, just like in school. The same in RPN becomes
3 1 5 -2 \* + in postfix.

As you can see the brackets are no longer used and it is because they are no longer needed.
When the evaluation takes place the exact order of operations will be applied, as normal.
Hence, the RPN is a **lossless** compression method. 


## The algorithm 
In this project I have used the **Shunting-yard** algorithm for conversion between infix to postfix (RPN).



### Example

Let's look at this line of Lisp code:

```
(defun doors (z &optional (w (make-list z)) (n 1)))
```

outputs:

```
defundoorsz&optionalwmakelistz-n1
```

(the Lisp code snippet is taken from Rosetta code)

As we can see further:

| ========= Stats ========= |  |
|---------------------------|--------|
| Original string length | 51 |
| RPN string length | 33 |
| Number characters* saved | 18 |
| Compression | **64.71%** |

Of course, maybe the result of the compressed Lisp code may not be actually executable in the RPN from because the algorithm used to process it follows simply the mathematical dependencies of the precedences of the operations. If Lisp has some extra ones they have not been factored in. Apart, the concept that maybe code can be compressed this way is interesting.