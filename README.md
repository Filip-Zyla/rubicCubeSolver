# rubicCubeSolver
As a Rubic cube's enthusiast sooner or later I would come up with some app about it. <br />
My goal is to learn and play with code combine with my passion.

It's a desktop app that allows user to play with cubes.
  
Program is write in JAVA version 12, with help of libraries: AWT, swing, GUAVA, Lombok, JavaTuples. <br />
Build using Maven. Test are written in JUnit5.

## Functionalities:
  - scramble cube with randomly generated algorithm
  - move cube with your own algorithm
  - solve cube using basic solving method
  - finding the shortest algorithm which solves the cube (FWM - fewest moves)
  - shorten algorithm, with option of deleting rotation moves
  - history of actions taken by user
  - setting delay between single moves

## Current type of cubes available:
  - 2x2x2 full functionality

## Implementation of cube
Regular NxNxN cube is implemented using 2x2 matrix of integers. <br />
Example for 2x2x2 cube: <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;     4 4 <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;     4 4 <br />
3 3  0 0  2 2  5 5 <br />
3 3  0 0  2 2  5 5 <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;     1 1 <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;     1 1 <br />
Empty spaces have value 9 but are irrelevant. <br />
Every number corresponds to different colors:
- white = 0
- yellow = 5 
- red = 1 
- orange = 4 
- blue = 2 
- green = 3  <br />

All opposite colors sum up to 5, what ended up as a useful property further. Depends on type of cube, classes can implement different interfaces.
Cube.class implement two interfaces, one which allows cube to be rotated and second concerning moves of outer walls. <br />
Every NxNxN cube allows these two types of moves.  <br />
Moving cube is quite simple process:
 - move(String alg) check if alg is proper, convert it to list and pass each move to moveOneWall(char wall, int rotate) method
 - moveOneWall(char wall, int rotate) determine which method of MoveOneWallInterface or RotateInterface use
 - eg. moveCounterR() changes matrix

Each move has three different methods: normal, counter and double.
Since there are no loops they are very fast, compare to one method which would include loop
and in case od counter move could result in unnecessary repetition.
 
## Algorithm utility class
Algorithm.class contains static methods for manipulating algorithms. Here I explain some of them.
### randomScramble(int min, int max) 
returns random algorithm, taking under consideration that in 2x2x2 cube moves like D and U are on the sam axis and can be combined in one move, counter moves appear in probability of 2/7 and double moves 1/7
### checkIfProper(String alg)
returns boolean, whether algorithm is good or not, it checks if character is from poll "xyzUDRLFB", ' and 2 are not in poll, because method skips them if previous char is in poll, so only when they are repeated there is violation
### skipRotation(String alg)
uses HashBasedTable, which consist all possible permutation of rotation and move. first loop go through alg and adds to xyz list when rotations appear and deleted from alg, if it's not rotation second loop is trigger, current move is changed as many times as rotations occurred in alg before, going through xyz and changing current move based on HashBasedTable, ' adn 2 can be omitted and just added again after changes

## Fewest Moves
### Glossary
| Term in text | Descripttion |
| ------------ | ------------ |
| FWM          | Fewest moves, competition where you have to solve cube in lowest number of moves |
| Algorithm    | Mehtod of findding FWM solve |
| Solve        | Result of algorithm |
| God's number | The lowest number of moved which are necessary to solve cube no matter how it is scrambled |
| Entropy      | Benchmark of how cube is solved/scrambled |
| Notation | Well explained https://www.iberorubik.com/tutoriales/2x2x2/notation/ |

### Introduction
FWM methods are the most interesting part of application. <br />
God's number for 2x2x2 it is 11, but generally it will be about 7-9. <br />
The most straight forward solution of finding god's number is checking all possible situation and choosing quickest, but it would take days using normal computer since there are 3,674,160 possible positions!!! <br />

What about multithreading? It will be definitely useful, but still, you don't want to wait hours for solution.
After many hours of testing and thinking I end up with my own method.
### Description
In general algorithm works by finding the best possible move for current state, which changes cube to be closer to be solved. <br />
To find the best move, entropy has to be calculated every move which is currently possible.
But some moves can be omitted <br />
e.g. when last move is R' next move cannot be of R wall.
```
R'R2 can be shorten to R and R'R dosen't cahnge anything <br />
Even though L wall moves don't exist in algorithm, they alos would be inneficient.
Consecutive moves cannot be parallel!
```
Algorithm will choose the best move from list of all possible moves of U, R and F walls. <br />
In that case left-back-down element is the one that will be not moved cause is already solved. <br />
```
ALL_POSSIBLE_MOVES = {U, U', U2, R, R', R2, F, F', F2} 
instead {U, U', U2, R, R', R2, F, F', F2, D, D', D2, L, L', L2, F, F', F2}
Using that approach makes the method twice faster wihout losese in solution!
```
Calculating entropy is the most expensive part of algorithm.
To do it cube has to be moved and then move has to be reversed to proceed with another calculations. <br />

Every entropy is compared to entropy of current state of cube. If no entropy is better than current, no move is returned, 
otherwise the best move is returned and then cube is moved. Entropy is calculated by checking every wall and adding potions if there is pattern. <br />
Current points ranking, for white and yellow in that example:
  - fullWallUni  = 6 <br />
![image](https://user-images.githubusercontent.com/53094328/148950650-1a19198a-eaa1-476b-ad87-63ba153aa68b.png)
  - fullWall     = 5 <br />
![image](https://user-images.githubusercontent.com/53094328/148950837-1b6a07c8-3a00-49a1-a5b4-36880c0103e2.png)
  - threeWallUni = 3 <br />
![image](https://user-images.githubusercontent.com/53094328/148951133-4c2281a2-40d8-40db-9e4a-503bbc17f622.png)
  - threeWall    = 2 <br />
![image](https://user-images.githubusercontent.com/53094328/148951214-27a7c084-9e45-4e30-9071-bfe873064eb6.png)
  - twoWallUni   = 4 <br />
![image](https://user-images.githubusercontent.com/53094328/148951321-dc0e3962-e2b2-4e6a-ba78-902c97d3906e.png)
  - twoWall      = 1 <br />
![image](https://user-images.githubusercontent.com/53094328/148951652-cf546dd7-d413-4b9c-8b37-9bf0f48458d8.png)
<br />
For now max entropy, therefore cube is solved, is 60. <br />
Entropy can be used to skip some cases. Final entropy contains all possible values of entropies of cube that is shuffled with at most 3 moves.
Program don't need to waste time checking solutions if its current entropy is not in set and godsNumber - currentLength <= 3. <br />

> Even though god's number is constant, in code it indicates current best solution so starts with 11 and can change.
```
For current points ranking FINAL_ENTROPIES = {4, 12, 14, 18, 20, 22, 26, 30, 32, 36, 42, 44}
```
To keep track of moves that have been done, HashMap of HashSets of that moves is stored, so algorithm won't use them again. <br />
Algorithm automatically clears necessary moves and whole map between iterations.
```
movesDone = { 
              1 -> (R, U),
              2 -> (U2, F, F'),
              3 -> (R, R', R2. U),
              4 -> (R, R', R2. U, U', U2, F, F', F2) 
              }
Curretn solution is UF'UF' (with some premove)
If algorithm needs another move it sees that 4th move is full so previous move has to be changed.
4th is cleared and another 3rd move is meant to be find, then addes.
movesDone = { 
              1 -> (R, U),
              2 -> (U2, F, F'),
              3 -> (R, R', R2. U, U2),
              4 -> () 
              }
Curretn solution is UF'U2
```
#### Multithreading
Even though number of permutation and time is significantly lower single threaded solution still would be too long. <br />
Program creates 4 threads, which on my computer that was the limit where more threads would end up in same or longer computation time. <br />
> Other computers could be faster using more thread, or even going through more cases <br/>

There are two types of threads, first with ascending and second with descending approach.
Both are initialized with pre-move from all available moves so 9 threads will be created.
Pre-moves allows algorithm to reduce number of moves to 10, therefore number of permutations.

All threads observe shared god's number (apart from their own) which indicates current shortest solution.
If a better solution is found value will change. Threads are checking if their solution is worse, so they can abort
and program can proceed with another thread without unnecessary computations. Descending thread will set their god's number with that value when they are created and ascending would work until their god's number is lower.

Every thread is meant to be fast, but in case of duration > 2 seconds solutions is aborted.
For now there is no premises that could indicate any problems with that. <br />

##### Ascending vs Descending

In general, they use all mentioned features. Finding best solution

Ascending starts from the shortest algorithms and goes up, so works similar to brute force solution. In some cases in super efficient
because can find short solutions in the blink of an eye. Downgrade si that mvoesDone is cleared after each iteration, 
so moves when godsNumber is 2, it won't be used with 3.

On the other hand descending approach finds optimal solution up to length of 11 (actually 10, pre-move!), and if there is no solution checks other solves
by changing current solution. <br />

