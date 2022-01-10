# rubicCubeSolver
As a rubic cube's enthusiast sooner or later I would come up with some app about it. <br />
My goal is to learn and play with code combine with my passion.

It's a desktop app that allows user to play with cubes.

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
  
Program is write in JAVA version 12, with help of libraries: AWT, swing, GUAVA, Lombok, JavaTuples. <br />
Build using Maven. Test are written in JUnit5.

## Implementing of cube
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

### Intorduction
FWM methods are the most interesting part of application. <br />
God's number for 2x2x2 it is 11, but generally it will be about 7-9. <br />
The most straight forward solution of finding god's number is checking all possible situation and choosing quickest, but it would take days using normal computer since there are 3,674,160 possible positions!!! <br />
What about multithreading? It will be definitely useful, but still, you don't want to wait hours for solution.
After many hours of testing and thinking I end up with my own method.
### Description
Firstly it starts with list all possible moves of U, R and F walls,
so left-back-down element is the one that will be not moved cause is already solved.
Using that approach makes the method twice faster!
Next there is executorService of 4 threads, on my computer that was the limit where more threads would end up in same or longer computainon time.
Then executor accepts two ascending and two descending approach callable objects.
They are responsible for finding solution. They are initialized with pre-move from available moves
which reduce God's number to 10, therefore number of permutations. Generally 9 objects will be created.
All threads observe AtomicInteger which indicates current shortest solutions (11 if no solution yet)
and if solution is found it is changed, then all threads can check if their solution is worse, so they can abort
and program can proceed with another thread without unnecessary computations.
Another trick to fasten solution is finalEntropy. It contains all possible entropies of cube that is shuffled with at most 3 moves.
Program don't need to waste time checking solutions if its current state is not in set and godsNumber - currentLength <= 3.
For bot ascending and descending approach HashMap of HashSet of moves already done is stored.
It helps track which moves have been done, so algorithm won't use them again. In case of full moves used in one position previous move is v
changed and rest in front is erased. <br />
Ascending starts from the shortest algorithms and goes up, so works similar to brute force solution. In some cases in super efficient
because can find short solutions in blink's of eye. Downgrade si that mvoesDone is cleared after each iteration, 
so moves when godsNumber was 2 won't be used with 3.
On the other hand descending approach finds optimal solution up to length of 11, and if there is no solution checks other solid
by changing current solution. <br />
Every thread is meant to be fast, but in case of lacking of solutions after 2 seconds solutions is aborted.
For now there is no premises that could indicate any problems with that. <br />
When algorithm need to add move to solutions, it goes through all_possible_moves, minus moves done and minus moves that are parallel to previous move,
so if previous move was R, moves R, R' and R2 are omitted.
To find the best move, for every possible move it has to calculate entropy, do the move and reverse it.
It's the most expensive part of algorithm but using previous remarks it is much quicker.
If no move makes entropy better than current, no move is returned. <br />
Entropy is calculated by checking every wall and adding potions if there is patter on it, like all pieces are same color, all are of two parallel color
or only two pieces are next to each other and are of the same color. <br />
Current points:
    - fullWallUni  = 6 
    - fullWall     = 5
    - threeWallUni = 3
    - threeWall    = 2
    - twoWallUni   = 4
    - twoWall      = 1
For now max entropy, therefore cube is solved, is 60.
