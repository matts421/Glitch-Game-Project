# `G1!tCh`
![Logo](https://media.github.students.cs.ubc.ca/user/18340/files/1d058e26-90df-42bc-bf9e-25916f108242)

## A roguelike 2D-platformer

`G1!tCh` will be a video game that takes aspects from the roguelike and 2D-platformer genres.
This game can be imagined as _Super Mario Bros._ meeting _Realm of the Mad God_.
`G1!tCh` begins with the main character being teleported into the mythical realm of  *Technolia* in
a *Jumanji*-esque style. The objective for the player is to escape *Technolia* by traversing
through the realm's 3 stages, and defeating any enemies that stand in their way.

Unlike some roguelikes, the level stages of `G1!tCh` will be static and manually generated (not via an algorithm).

### Controls

- `SPACE` to fire a projectile.
- `UP_ARROW` to jump.
- `LEFT_ARROW` to move left.
- `RIGHT_ARROW` to move right.
- `E` to access the inventory
- `F1` to cycle character's class.
- `F2` to prompt a save.
- `F3` to prompt a load.

## User Stories
- As a user, I want to be able to customize my playable character's appearance,
  and choose to specialize in one of the Magic, Warrior, or Ranged classes.
- As a user, I want to be able to add an arbitrary amount of coins to my inventory.
- As a user, I want to be able to traverse the level stage (moving and jumping), and use my weapons to defeat enemies.
- As a user, I want to be able to progress through different level stages after completing one.
- As a user, I want to have the option to save all of my progress in the game at any point.
- As a user, I want to have the option to load a previous save file upon launching the game.

# Instructions for Grader

- You can generate the first required action related to adding Xs to a Y by pressing `E` and
 pressing the `Drop item` button.
- You can generate the second required action related to adding Xs to a Y by pressing `E` and
 pressing the `Buy health [100 coins]` button.
- You can generate the third required action related to adding Xs to a Y by pressing `E` and
    pressing the `Increase mana [100 coins]` button.
- You can locate my visual component by running `Main`. Enemy, barrier, and background assets are displayed
  automatically.
- You can save the state of my application by pressing `F2` and selecting the `Confirm` button.
- You can reload the state of my application by pressing `F3` and selecting the `Confirm` button.

# License Disclaimer

This project includes artistic work from various artists. Under the shared
[creative commons license](https://creativecommons.org/licenses/by/4.0/), these assets can be used in both personal and
commercial projects. The following artist's work has been utilized for this project:

- *Glacial Mountains*
  - **[vnitti](https://vnitti.itch.io/)**
- *Platforms and Coins*
    - **[coloritmic](https://coloritmic.itch.io/)**
- *Icy Slime Enemies*
    - **[hredbird](https://hredbird.itch.io/)**
 

# Phase 4: Task 2

```
Sat Aug 05 22:00:38 PDT 2023
Projectile hit enemy at (330,540).
Sat Aug 05 22:00:39 PDT 2023
Player picked up 7 coins!
Sat Aug 05 22:00:49 PDT 2023
Projectile hit enemy at (450,90).
Sat Aug 05 22:00:50 PDT 2023
Player picked up 13 coins!
Sat Aug 05 22:00:55 PDT 2023
Projectile hit enemy at (90,60).
Sat Aug 05 22:00:55 PDT 2023
Player picked up 6 coins!
Sat Aug 05 22:00:55 PDT 2023
Completed map stage 1
Sat Aug 05 22:01:00 PDT 2023
1 coin(s) were removed from inventory.
Sat Aug 05 22:01:00 PDT 2023
1 coin(s) were removed from inventory.
Sat Aug 05 22:01:00 PDT 2023
1 coin(s) were removed from inventory.
Sat Aug 05 22:01:00 PDT 2023
1 coin(s) were removed from inventory.
Sat Aug 05 22:01:00 PDT 2023
1 coin(s) were removed from inventory.
Sat Aug 05 22:01:00 PDT 2023
1 coin(s) were removed from inventory.
Sat Aug 05 22:01:02 PDT 2023
Player picked up 6 coins!
Sat Aug 05 22:01:13 PDT 2023
100 coin(s) were removed from inventory.
Sat Aug 05 22:01:13 PDT 2023
Max mana set to 6
Sat Aug 05 22:01:25 PDT 2023
Changed class to Mage.
Sat Aug 05 22:01:26 PDT 2023
Changed class to Archer.
Sat Aug 05 22:01:27 PDT 2023
Player took 1 point(s) of damage
Sat Aug 05 22:01:28 PDT 2023
Player took 1 point(s) of damage
Sat Aug 05 22:01:29 PDT 2023
Player took 1 point(s) of damage
Sat Aug 05 22:01:32 PDT 2023
100 coin(s) were removed from inventory.
Sat Aug 05 22:01:32 PDT 2023
Health set to 3
```

# Phase 4: Task 3

Please note that, while the TerminalGame class exists within this project, it has not been included within the UML
diagram. This is due to the fact that `Main` no longer interacts with TerminalGame. This class only exists in this
project for legacy purposes from Phase 1.

Upon looking at the UML diagram for this project, there are a few glaring issues that I would change given more time
and the opportunity to refactor my code. First of all, there are a few classes that I should make abstract. Due to the
nature of the game, the player will always have one of the three classes Mage, Archer, or Warrior. Hence, the
GameCharacter class never needs to be instantiated. The simple fix to this issue would be to make GameCharacter
abstract. Likewise, HasModel should no longer be a normal class. Because it has implementation of some shared methods,
it cannot be declared an interface, and rather should be declared abstract as well. Moreover, far too many classes in
the model package extend HasModel and implement Writable. Because everything that extends HasModel is Writable,
HasModel should instead implement Writable, hence removing the necessity for all of its subclasses needing to implement
Writable. In the same vein, Mage, Archer, and Warrior have no reason to implement Writable given their parent class,
GameCharacter, already implements this interface. Furthermore, while not apparent from the UML diagram alone, a new
class, Barrier, should be implemented in the model package. Currently, all the game map's barriers are represented
by Java's built-in Rectangle class. However, because save and load functionality requires these objects to be saved in
a JSON file, a rectangleToJson method was implemented within the HasModel package, and the GameMap class had to extend
HasModel because of its association with barriers. This doesn't make physical sense, because the map doesn't actually
have a model or hit-box. With this refactor, GameMap would have a new association to a new custom class Barrier, which 
would in turn extend HasModel. Otherwise, the model package follows design principles relatively well, but the UI
package still needs to see some significant changes to confirm with proper practices.

First of all, there is a large number of associations that are unnecessary in the UI package. ScorePanel and
MainGamePanel are two particularly good examples. Because GuiGame has access to Game, ScorePanel and, MainGamePanel,
ScorePanel and MainGamePanel do not need to be associated directly with Game. Instead, a bidirectional association
between GuiGame and ScorePanel or MainGamePanel should be established in order to remove the redundant associations to 
Game. An additional benefit of this change would also modify the ItemButtonListener and PaymentButtonListener's
associations. With this proposed bidirectional, these two classes would no longer need an association to Game. Moreover,
GuiGame relying on two instances of MainGamePanel is misleading. Because those two instances are always one of
GamePanel and one of InventoryPanel, GuiGame should instead have fields with those specific types. There is also
a redundancy in the persistence part of the GUI. Because PersistencePanel has access to GuiGame, if the association
between PersistencePanel and PersistenceButtonListener was bidirectional, PersistenceButtonListener could remove its
association to GuiGame. Finally, the sprite handling is currently done in the MainGamePanel, GamePanel, and
InventoryPanel classes. Because sprites are intrinsic to the objects they are referencing, sprites should
be moved to become fields in the classes they respectively represent.

An additional note is that the UI package could benefit from employing the observer design pattern. Instead of the
ScorePanel having an association with Game, Game could extend a custom Observable class and ScorePanel could implement
a custom Observer interface.

Fortunately, all the classes already follow the principle of single responsibility, so there is minimal need to
introduce new classes (other than the aforementioned Barrier class).

