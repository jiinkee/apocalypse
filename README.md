# Rogue-like game using the concepts of OOD
This is an assignment project of FIT2099 Object Oriented Design and Implementation 2020. The purpose of the assignment is to implement a text-based Zombie Apocalypse game. The project was done in pairs and students were required to collaborate using git. The src folder contains all the game code while the design-docs folder contains the UML diagrams and javadoc that adhere to the code.

### Contributors
1. Kee Pei Jiin
2. Tan Wei Li

### Game background
Rogue-like games are named after the first such program: a fantasy game named *rogue*. They were very popular in the days before home computers were capable of elaborate graphics, and still have a small but dedicated following. More information about rogue-like games are available on <http://www.roguebasin.com/>.

In this game, you are a survivor of the Zombie Apocalypse. The dead have risen from their graves and destroyed society as we know it. You and a small group of other people have retreated to a compound in the woods to defend it as best you can. The ultimate goal of this game is to survive the apocalypse and eliminate all the zombies.

## Gameplay
### Characters
**Player** `@`
 - This is you!
**Human** `H` 
- NPCs that wander around the ground and attack zombies when they encounter one. When they are killed, their corpse will revive as a Zombie 5 to 10 turns later.
**Farmer** `F` 
- NPCs that an sow crops, fertilise unripe crops and harvest ripe crops. And of course, they can do everything a human can do (since farmers are humans too).
**Zombie** `Z` 
- They are much more violent than humans. They will hunt down humans and bite/punch humans. They're also smart enough to pick up weapons and attack using them. They will drop limbs occasionally when they are being attacked.
**Mambo Marie** `M` 
- The source of the local zombie epidemic! She is a mysterious Voodoo priestess that will appear out of nowhere. If she is currently not on the map, she has a 5% chance per turn of appearing. After her appearance, she will chant and summon 5 more zombies every 10 turns. If she is not killed, she will vanish after 30 turns. Mambo Marie will keep coming back until she is killed. *She is definitely a bad news that need to be get rid of ASAP!*

### Weapons
**Plank** `)`
- A normal weapon that can be picked up from the ground. Damage point: 20
**Club** `~` 
- Can be crafted from zombie's dropped arm. Damage point: 30
**Mace** `$`
- Can be crafted from zombie's dropped leg. Damage point: 40
**Shotgun** `*`
- A ranged weapon that does area effect damage. The shotgun is fired in a direction and it has a 75% of hitting the characters within the area of effect. The range of a shotgun is 3 squares. So, if the shotgun is fired north, it can hit anything in the three squares north of the shooter, northeast of the shooter, northwest of the shooter, or anything in between. The area of effect is illustrated as below:
```
*******
 *****
  ***
   P
```
- Damage point: 20
**Sniper rifle** `>`
- Another long-ranged weapon that allows the player to take aim on the enemy before shooting. The more turns player takes to aim at the enemy (*max 2 turns*), the higher the accuracy of shot will be and it will also lead to larger damage on the enemy, as described below:
  - No aim: 75% chance to hit, standard damage 
  - One round aiming: 90% chance to hit, double damage
  - Two rounds aiming: 100% chance to hit, instakill
- If the shooter takes any action other than aiming or firing during this process, the aiming concentration is broken and thus shooter loses the target. Shooter will also lose the target if he takes any damage.
- Standard damage point: 30

### Other items
**Ammunition box** `(`
- Boxes that contain bullets used in shotgun and sniper rifle.
**Human corpse** `C`
- Corpse that drop onto ground when human dies.
**Food** `^`
- Human/player that eats a food can restore 10 HP.
**Vehicle** `?`
- Transportation used by player to travel between maps.

### Ground & Plants
**Dirt** `.`
- Crops can be planted on dirt.
**Fence** `#`
- Blocks the characters and items from entering the ground.
**Tree**
- Has 3 life stages:
  1. `+` - Sapling.
  2. `t` - Sapling grows and becomes small tree after 10 turns.
  3. `T` - Small tree grows and becomes large tree after another 10 turns.
**Crop** 
- Has 2 life stages:
  1. `x` - Unripe crop. It will ripe after 20 turns. Crop ripes faster if it is fertilised.
  2. `X` - Ripe crop which can turn into food when it is being harvested.

### Maps
There are two different maps in this game:
1. Compound map
2. Town map
Each map has different terrain and different NPCs & items placements. Each map has a vehicle at a fixed location and player can use the vehicle to travel between maps.

### How to play
At each turn, player an enter a number/letter that is available on the displayed menu to choose an action. After the player avatar has taken an action, all other characters in both maps will take an action sequentially. The actions taken by these NPC are determined by the program. The health point of characters will be deducted/increased/maintained depending on the action carried out at that turn. A character dies when its health point hits 0.
The game continues until one of the following condition is satisfied:
- When the player chooses to quit the game.
- When the player dies *or* when all humans and farmers in both maps died. Player loses.
- When all zombies and Mambo Marie are killed. Player wins.

## Credits
Game engine code is provided by Robyn McNamara.
