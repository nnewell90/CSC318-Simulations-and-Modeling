# The Newsboy Problem
## Project Details

Suppose a newsboy knows from past experience that customer demand may be 15, 16, 17, 18, 19, or 20 newspapers a day. He knows that the relative frequency will be:

| Customer Demand | Relative Frequency |
| --------------- | ------------------ |
| 15 | 1/12 |
| 16 | 1/12 | 
| 17 | 5/12 | 
| 18 | 2/12 | 
| 19 | 2/12 | 
| 20 | 1/12 |

The newsboy buys papers for $0.35 and sells them for $1.00. All extra papers he has left at the end of the day can be returned for $0.05 each. 

## Project Requirements
Write a program to determine the best reordering policy of the following:
1. Order 16 papers each day.
2. Order the same number each day as called for the last.
3. Order one less each day than called for the previous day.

Run each reordering policy for 1,000 days and print the results.

Modify the Newsboy simulation. Add to the Newsboy object the ability of maintaining statistics. The new object should be able to return the average papers sold per day, the average papers demanded per day and the variance for both the papers sold and demanded. Further, the new object must product the average profit per day and the variance of the daily profit.
