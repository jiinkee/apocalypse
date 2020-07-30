# Rogue-like game using the concepts of OOD
This is an assignment project of FIT2099 Object Oriented Design and Implementation 2020. The purpose of the assignment is to implement a text-based Zombie Apocalypse game. The project was done in pairs and students were required to collaborate using git. The src folder contains all the game code while the design-docs folder contains the UML diagrams and javadoc that adhere to the code.

### Contributors
1. Kee Pei Jiin
2. Tan Wei Li

### Game background
Rogue-like games are named after the first such program: a fantasy game named *rogue*. They were very popular in the days before home computers were capable of elaborate graphics, and still have a small but dedicated following. More information about rogue-like games are available on <http://www.roguebasin.com/>.

In this game, you are a survivor of the Zombie Apocalypse. The dead have risen from their graves and destroyed society as we know it. You and a small group of other people have retreated to a compound in the woods to defend it as best you can. The ultimate goal of this game is to survive the apocalypse and eliminate all the zombies.

# Gameplay
### Characters
1. **Player** `@`
- This is you!
1. **Human** `H` 
- NPCs that wander around the ground and attack zombies when they encounter one.
2. **Farmer** `F` 
- NPCs that an sow crops, fertilise unripe crops and harvest ripe crops. And of course, they can do everything a human can do (since farmers are humans too).
3. **Zombie** `Z` 
- They are much more violent than humans.
They will drop limbs occasionally when they are being attacked.
4. **Mambo Marie** `M` 
- The source of the local zombie epidemic! She is a mysterious priestess that will appear out of nowhere. Every 
She is definitely a bad news that need to be get rid of ASAP!

Mambo Marie is a Voodoo priestess and the source of the local zombie epidemic. If she is not currently
on the map, she has a 5% chance per turn of appearing. She starts at the edge of the map and wanders
randomly. Every 10 turns, she will stop and spend a turn chanting. This will cause five new zombies to
appear in random locations on the map. If she is not killed, she will vanish after 30 turns.
Mambo Marie will keep coming back until she is killed.

### Weapons
1. **Club** `~` 
- Can be crafted from zombie's dropped arm. Damage point: 30
1. 

### Other items
1. **Vehicle** `?`
- Transportation used by player to travel between maps.

### Ground types
1. **Dirt** `.`
- Crops can be planted on dirt.
1. **Fence** `#`
- Blocks the characters and items from entering the ground.
1. **Unripe crop** `x`
- ripe after 20 turns. ripe faster if it is fertilised.
1. **Ripe crop** `X`

### Maps
There are two different maps in this game:
1. Compound map
2. Town map
Each map has different terrain and NPCs placements. Each map has a vehicle at a fixed location and player can use the vehicle to travel between maps.

### How to play
At each turn, player an enter a number/letter that is available on the displayed menu to choose an action. After the player avatar has taken an action, all other characters in both maps will take an action sequentially. The actions taken by these NPC are determined by the program. The health point of characters will be deducted/increased/maintained depending on the action carried out at that turn. A character dies when its health point hits 0.
The game continues until one of the following condition is satisfied:
- When the player chooses to quit the game.
- When the player dies *or* when all humans and farmers in both maps died. Player loses.
- When all zombies and Mambo Marie are killed. Player wins.

### Credits
Game engine code is provided by Robyn McNamara.
