# The Northwest Lumber Company Problem
## Project Details
The Northwest Lumber Company has a new tree called the High Country Pine. This is a fast growing pine, maturing in five years. However, High Country has the following problems:

1. Drought: The pine is a drought resistant tree that will grow in those years of high rain and simply stagnate during those years when drought hits the tree. If it would rain every year, the trees would grow to maturity in five years.

| Growth Stage | Drought (1"-3") |             | Moderate (4"-10") |             | Heavy (11" or greater) |             |
| ------------ | --------------- | ----------- | ----------------- | ----------- | ---------------------- | ----------- |
|              | Dying           | Not Growing | Dying             | Not Growing | Dying                  | Not Growing |
| Year 1       | 10              | 70          | 5                 | 1           | 2                      | 2           |
| Year 2       | 10              | 75          | 5                 | 2           | 3                      | 3           |
| Year 3       | 30              | 60          | 5                 | 2           | 3                      | 3           |
| Year 4       | 35              | 65          | 5                 | 1           | 4                      | 4           |

2. Beetles: The pine is also attacked by beetles. Unfortunately, the beetles thrive in dry weather. The following table describes the beetle's affect on the trees.

| Growth Stage | Drought | Moderate | Heavy |
| ------------ | ------- | -------- | ----- |
| Year 1       | 10      | 5        | 0     |
| Year 2       | 15      | 5        | 0     |
| Year 3       | 30      | 10       | 2     |
| Year 4       | 30      | 10       | 2     |

3. Fire: The pine is also succeptable to forest fires. Northwest can expect the following percentages of it's tres to burn each year (if it has forest fires). As the trees age they become much more vulnerable to burning given there is a forest fire.

| Growth Stage | Drought | Moderate | Heavy |
| ------------ | ------- | -------- | ----- |
| Year 1       | 15      | 10       | 5     |
| Year 2       | 18      | 12       | 7     |
| Year 3       | 22      | 15       | 10    |
| Year 4       | 30      | 20       | 15    |

Northwest has 1 million acres of trees. Currently the trees are distributed in the following manner.

| Equivalent Age | Number of Acres |
| -------------- | --------------- |
| 1              | 400,000         |
| 2              | 300,000         |
| 3              | 200,000         |
| 4              | 100,000         |

If trees die, they are immediately replanted with 1 year old stock. Five trees are planted in each acre. 

The rainfall records for the past ten years provide the following distributions.

| Rainfall Inches | No Seeding % Occurance | Seeding % Occurance | Prob Forest Fire |
| --------------- | ---------------------- | ------------------- | ---------------- |
| 1               | 1                      | 1                   | 90               |
| 2               | 5                      | 1                   | 85               |
| 3               | 5                      | 1                   | 65               |
| 4               | 3                      | 2                   | 55               |
| 5               | 10                     | 10                  | 45               |
| 6               | 15                     | 10                  | 30               |
| 7               | 20                     | 20                  | 15               |
| 8               | 14                     | 10                  | 10               |
| 9               | 10                     | 10                  | 5                |
| 10              | 10                     | 15                  | 3                |
| 11              | 5                      | 15                  | 2                |
| 12              | 2                      | 5                   | 1                |

Each year Northwest harvests all living 4-year old trees and replants them with 1-year old stock. Northwest is considering the following policies:
a. Seed the clouds to change the rain distribution.
b. Spray all 3-year old trees which will make them invulnerable to the beetle.
c. Spray 50% of the 3-year old trees and 50% of the 4-year old trees.

## Project Requirements
Write a program which will simulate the growth of the forest for 100 years. Run the program for each of the above policies and list the mean and variance for all acres of trees in equivalent age categories 1, 2, 3, and 4. 
