# Assignment: Book Finding

# General explanation what algorithm does

![potter.png](https://www.profit24.pl/images/editor/ZABAWKI/Rebel/3558380064930/3558380064930_4.png)

There are several ways how Harry Potter able to take a book and reach the exit door:

1. Start → Book → Exit
2. Start → Cloak → Book → Exit
3. Start → Book → Cloak → Exit

But due to the fact that Potter are not able to investigate whole map permanently. Potter needs to investigate small parts of each path like:

1. Start → Book (so there’s Potter reaches the book without cloak)
2. Start → Cloak 
3. Cloak → Book
4. Book → Door without wearing cloak (it’s possible if algorithm choose 1st variant in this list)
5. Book → Door with wearing cloak

Then we need properly put all ways together and finally get the path with the minimum steps taking for destination.

# Backtracking

So, inside of method 

```java
first_method(int[][] grid, HashSet<Pair<Integer, Integer>> inspectors_zone, int scenario)
```

Program calls backtracking algorithm several times to count each possible way. Inside backtracking alogorithm I’ve used recursive approach to implement this feature.

### Small improvements of algorithm

 

1. I created a Hash Map that contains key = {Pair<Integer, Integer>}, value = {Pair<Pair<Integer, Integer>, Integer>}. The reason is I need fast recover the parts of whole path
2. In Hash Map I also have some additional Integer value that hold minimum steps for the cell itself.
3. And also I’ve initialized a integer variable min_steps. The thing is why algorithm need check some other ways if my algorithm already found the path with the minimum steps than others just did? I interested in such paths that have current_steps < min_steps. 

## BFS

So, inside of method

```java
second_method(int[][] grid, HashMap<Integer, Character> attributes, HashSet<Pair<Integer, Integer>> inspectors_zone, int scenario)
```

Program make the same stuff as first method but use bfs algorithm and also there are small differences, that’s why I’ve decided to split they up.

### Small improvements of algorithm

1. Instead of creating hash map globally I initialized it here. And it serves as hash map in backtracking but here I do not need to hold some variable that counts min steps for cell. The first path that reaches the object that Harry looking for this will be the answer.

## PEAS model

1. Performance Measure:
    - Time taken to find the path
    - Result: reached destination or fail
2. Environment:
    - Grid
    - Enemies: Filch, Norris
    - Objects: Book, Exit, Cloak
3. Actuator:
    - get_next_nodes
4. Sensors: 
    - check_next_node_1() and check_next_node_2()

## Conclusion

So, as we can see that second scenario is not good way for Potter, because if he noticed some cells ahead he assume that probably the closest cell in this direction also over enemy’s eyes, hence Harry do not want to go there. This is a problem of high lose percentage “There’s no path”. But algorithms almost gave the results, but BFS do work much faster.
