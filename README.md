*Soccer Fantasy League Application*

<img src='https://github.com/JermelWatson/Soccer_Fantasy_League/blob/master/Final_Project_2021_demo.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />


*For our Spring 2021 Data Structures final group project we have developed a Fantasy League 
football system. The members of Group 3 are Allan Muir, Jermel Watson and Julio Castro. This 
system is an graphical user interface that performs the following functions:*

[x] Automatically select players by selecting highest priced players first

[x] Automatically select players by heist points in previous season.

[x] Show the players per team and per position for the same team while sorted by points in the 
previous season then by price if there is a tie

[x] Search for a player by name

[x] Since the list of all players per position is too long, you should allow the user to show 
more players

[x] Ensure team cost does not exceed £100 and no more than 3 players are selected from any 
one team.

This project includes 9 main classes that are used to achieve the above functionality. 

*Player* – The Player class is an abstract class that outlines all the methods that will be included for 
each player. 

*Defender* – The Defender class is a child class from the Parent Abstract class Player. This class is 
created to store the data of all the defenders from the file. This class has a position field that is used
to uniquely identify the player as a defender.

*Midfielder* - The Midfielder class is another child class from the Parent Abstract class Player. This
class is created to store the data of all the middle field players from the file. This class has a 
position field that is used to uniquely identify the player as a middle fielder.
Forwards - The Forward class is another child class from the Parent Abstract class Player. This 
class is created to store the data of all the forward players from the file. This class has a position 
field that is used to uniquely identify the player as a forward.

*Goalkeeper* - The Goalkeeper class is another child class from the Parent Abstract class Player. 
This class is created to store the data of all the goalkeeper players from the file. This class has a 
position field that is used to uniquely identify the player as a goalkeeper.

*GUI* – This class is the main class used to initiate the graphical user interphase. The four main 
buttons are:

*Readfile or Automatic selection by Price* 
– The action attached to this button is implemented in the 
Readfile.java class. The first function that come in to play in this class is the getFileData() method. 
This method returns a stack of player objects that are separated by player type. This function sequentially 
reads through each file and creates an object for each player based on their position. This method 
then returns a Stack of Player objects to the main action performed method. The stack that was 
returned was then sorted by highest priced player using the collection 
API sort() method using the PlayerComparator. 

- Using this newly sorted stack the highest priced players were chosen and added to a new 
stack. If the price of these players exceeds the budget, then these players are discarded and 
the lessExpensive () method is recursively called and chooses a team that fits the set criteria
of 15 players, 5 defenders, 5 middle fielders, 3 forwards and 2 goalkeepers. The function 
countNumTeamA() is used to ensure that no more than three players are chosen from any 
one team. This is done by saving each team encountered in a team stack. This stack is 
passed with the current team of the selected player. The number of time the team appears in
the stack is checked and the method return true if the team appears 3 times already, false 
otherwise.

*AutoByPoints*
– The actionperformed method on the Automatically select players by points button is 
implemented in this class. The first method encountered in this class is the getDataFile2() 
method and it operates in a similar manner. The returned stack was the sorted by highest priced players 
firsts using the for a similar selection method.

-The algorithm tries to create a team of the highest priced players. This is done by first using
the function findHigh() to identify the highest priced player in the stack. This player is then
added to the new list of players and removed from the stack. If the budget is exceeded then 
the chosen players are discarded and the method getLessPointPlayer () is used to return a 
stack with the chosen players excluded and the second highest players are chosen. The new
selection is done using the recursive method lessPoints() which returns a stack of players of
the highest price players that can be supported by the budget.The countNumTeamA() method is also used to ensure
that no more than three players are chosen from any one team.

*SelectionWindow or Manual Selection*
- The Manual Selection button implements the algorith that allows the user to manually select a list of players. The first method in this class is the getDataFile3() method. This method is slightly differentfrom the others as it returns an ArrayList of Player objects instead of a Stack. The players are the sorted by points using the collection API sort using the PlayerComparatorPoints. This list of players is the seperated in two and the first half is 
saved in a new list. 

- Two Defaultlist model were created to display this list of players unto the graphical 
interphase. The left display was used to display the initial list of players loaded and the right display was used to display the players chosen by the user.

*Load Initial list*
– This button loads the initial list of players to be chosen from by the user.

*Add players* 
- This allows the user to add players unto their list.
o Features to note: 
[x] As the players are being added, their price is deducted from the budget. If 
the user tries to exceed that budget a message dialog box pops up notifying 
them that the budget is exceeded. 
[x] Also, if the user tries to select more than the player limit per position a 
message dialog box is shown that player limit is reached. If the user tries to 
select more than 15 players a dialog box pops up notifying them that the 
player limit is reached.

*Remove Player*
– This button is used to remove a selected player from the list. As the player
is removed their price is re - added to the budget. If the price is not reciprocated properly, 
double tap this button to get £100.

*Remove all* – This button is used to remove all the selected players from the users list of 
selected players. Double tap this button to return the budget to get £100. 

*Show more player*
– This button is used to load the second half of the list of players. 

*Show by team*
– This button will sort the players by team and highest points in the previous
season. This will allow the user to select players from their favorite team.

*Search* 
– This button allows the user to search for a player by name and add them to their 
list of players. 

**BigO Complexity** - 
The overall big oh complexity of using a stack in worst case is o(n) while the best case is 0(1). In 
the Readfile,AutoByPoints and SelectionWindow classes the method getFileData(), getFileData2 
and getFileData3() worst case is O(n) dominant for loop with execution time of 0.00003 best case 
is 0(1)
The method Actionperformed(actionevent e) is O(n) due to a dominant for loop with execution 
time of 0.00003.
function getlesspointplayer()is donimated by the else which is O(log n) with a execution time of 
0.00006.
The overall worst case of the code was decided to be O(N) + O (N log N)
Use of OOP 
Abstract method – we used an abstract class 'Player to design the outline of the specific player 
classes. 

**Polymorphism** – the abstract class player is a main type that has similar types defender, midfielder, goalkeeper and forward that have polymophic methods. eg. tostring, getplayername(), etc.

*Issues Faced -* 
[x] Bugs – infinite looping and type mismatch errors
[x] Files not merging
[x] Files not found
[x] Errors in parsing files due to mismatch data in files. Missing # for spliting files
Solution taken.
[x] Testing codes regularly
[x] Reviewing each others' codes
[x] Files were closely examined and changes made as necessary.

If you have any questions about this project you can contact us via email at 
jermel.watson@udc.edu.
