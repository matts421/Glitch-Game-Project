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
 
