# Assignment 3. Maze Escape
**Student ID:** 254224  
**Name:** Khilolakhon Mirzabekova  
**Group:** SE-2519   
**Maze size (rows x columns):** 9 x 10

This file is the report. Fill it in as you go. How to start: START_HERE.md. The task: docs/Assignment3.pdf.

## Program output
The 13 lines your program prints on your data (`java -cp out Main`):
Broken: 89
Kept: 72
Last broken wall: 129
MST cost: 26405
Heaviest broken wall: 742
Path length: 21
DFS visited: 53
Dead ends: 31
Zones: 9
Largest zone: 19
Is DAG: 0
Flow stages: 5
Fastest route cost: 8
My maze: output/maze.html

## Questions
Answer in your own words, two or three sentences each.

**Mission 0. What do find and union do, and what makes them fast in your code?**  
`find` locates the root representative of a set, and `union` merges two separate sets into one. They are extremely fast because path compression flattens the tree during `find`, while union-by-size ensures the smaller tree is attached under the larger one to minimize depth.

**Mission 1. Why is Broken always RC - 1, and Kept always (R-1)(C-1)?**  
`Broken` is always RC - 1 because a spanning tree connecting RC nodes strictly requires exactly RC - 1 edges to ensure full connectivity without cycles. `Kept` is (R-1)(C-1) because subtracting those broken walls from the total internal walls of an R x C grid leaves exactly this mathematical remainder.

**Mission 2. My MST cost from Kruskal and from Prim, and why they are equal:**  
My MST cost from both Kruskal and Prim is 26405. They are equal because all edge weights (wall costs) generated in this graph are unique, guaranteeing that there is only one unique Minimum Spanning Tree possible.

**Mission 3. Why is there exactly one path between two cells? How did you avoid a stack overflow on a huge maze?**  
There is exactly one path between any two cells because the generated maze is a spanning tree, which by definition is a fully connected acyclic graph. I avoided a stack overflow on huge mazes by replacing standard recursion with an iterative DFS approach, utilizing a custom array to simulate the call stack.

**Mission 4. Why does the second pass of Kosaraju go on the reversed graph? Why do the slides have no cycle exactly when Zones equals the number of cells?**  
The second pass uses the reversed graph to restrict the search to nodes that can trace back to the start node, isolating mutually reachable components (zones). The slides have no cycle when Zones equals the number of cells because it means every single cell forms its own isolated, single-node zone without any loops.

**Mission 5. Why is it enough to go through the zones in the order of their numbers? What does this pass cost?**  
It is enough because the zone numbering implicitly represents a topological sort; processing them in this order guarantees all incoming paths are fully evaluated before visiting the current zone. This dynamic programming pass costs linear time, specifically O(V + E) relative to the zones and inter-zone slides.

## AI use
The syllabus requires you to say which AI tools you used and for what. Write the tool (ChatGPT, Copilot, other) and what you asked it: explain an idea, find an error, review code. Write "none" if you did not use any. If you took code or text from a book or a website, give the source here.

* **Tool:** Gemini AI
* **Usage:** I used it as a coding assistant to understand the logic of iterative DFS, implement the Kosaraju algorithm without recursion, debug `cannot find symbol` syntax errors with missing imports, and formulate optimal DP traversal across DAG zones.

## Timing (Mission 7)
Milliseconds, 5 runs each, from `java -cp out Bench`.

| maze | Kruskal | Prim |
| :--- | :--- | :--- |
| 100 x 100 | 22 | 15 |
| 300 x 300 | 138 | 203 |
| 1000 x 1000 | 1248 | 1876 |

**What do the numbers say about how the time grows? Two or three sentences.**  
The execution time scales effectively with the number of edges, aligning with O(E log E) complexity. While Prim performs slightly faster on the smallest grid, Kruskal becomes noticeably faster on the 1000x1000 graph because our Disjoint Set operations run in near O(1) time compared to the heavier PriorityQueue overhead in Prim.

## Complexity
n is the number of cells, m the number of walls (or slides). Fill in every algorithm you wrote.

| algorithm | time | space | assumptions |
| :--- | :--- | :--- | :--- |
| DSU find and union | O(α(n)) | O(n) | α(n) is the inverse Ackermann function (nearly O(1)) |
| Kruskal | O(m log m) | O(n + m) | Sorting the edges is the bottleneck |
| Prim | O(m log n) | O(n + m) | Using a binary heap / Priority Queue |
| DFS | O(n + m) | O(n) | Iterative array stack avoids overhead |
| Kosaraju | O(n + m) | O(n) | Two passes over the cells and slides |
| Route in the zone graph | O(n + m) | O(n) | Zones are processed in topological order |

## Before you submit
- [x] data/walls.txt, data/slides.txt and output/maze.png are in the repository.
- [x] java tools/Check.java shows all examples right.
- [x] This README is filled in, the program output and the section AI use included.
- [x] The work is pushed to main (at least one commit per mission) and the repository is public.
- [x] The link is attached in Moodle.