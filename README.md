# checkoutSimulator
The program attempts to optimize the time it takes to process customers in check-out lines inside a grocery store. The general idea is to always place customers in the best checkout linepossible.  This will be based on the total number ofcustomers and the number of items in each cart being processed.  The time to serve a single customer is calculated by the following formula:
          t = 45 + 5*ni;
                where:t, time in seconds that it takes to serve a customer,
                ni, the number of items in the customers cart.

A store consists of 1 or more check-out linesdefined by:
          •n = number of normal check-out lines
          •f = number of expresscheck-out lines(less than or equal to x items)
A store could have up to 4 fast check-out lines.

Customers at the store may have many items in their cart or only a few.  Each new customer is added to the check-out line with the cumulative least amount of time in front of it (the shortest line). Any customer may optionally enter one of fexpresslines if they have less than or equal x items in the cart.  It should be noted that they do not need to enter an express line if another check-out line would result in a shorter service time.  Customers with more than x items must enter one of the other n check-out lines in the store. The total number of check-out lines will be equal to n + f;
