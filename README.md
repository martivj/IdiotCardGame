# IdiotCardGame

This project is a simple implementation of the card game "Idiot" made using JavaFX. This document includes a description of the game itself, and a guide on how to run the application locally. 

Further documentation on the code structure, design choices and testing approach can be found in the [Code Overview](./idiotCardGame/README.md) document.

## The Game

### Rules

Idiot is a card game that can be played with two or more players using a standard deck of 52 cards. Each player starts with three stacks of cards, where one face-up card is placed on top of a face-down card. The goal of the game is to get rid of all your cards.

Players need to have at least three cards in their hand, unless the deck is empty. Each card played is added to the main pile, and players can only play a card if its face value is equal to or higher than the top card of the main pile, with some exceptions.

The values of 2 or 10 can always be played, with 2 resetting the main pile and allowing the player to play another card, and 10 burning the main pile and allowing the player to play another card. If a player has a card with the same face value as the one just played, they can also play that card.

If there are four cards of the same face value stacked on the top of the main pile, it acts like a 10 and burns the pile. Players can only play cards from their stacks when their hand is empty. Cards on the bottom layer of a stack can only be played if the top layer has been removed.

The ace is the highest card, and if a player has no cards that have a high enough value to be played to the main pile, they need to draw all the cards from the main pile and add them to their hand. Players can always draw from the main pile regardless of whether they have a playable card.

The game continues until one player has gotten rid of all their cards.

### JavaFX Implementation

The code implementation takes this game into a JavaFX GUI environment, where a player can play against a simple AI. After pressing "start" on the game page, available actions appear as buttons with labels "play", "draw pile" and "end turn". When a winner is crowned, the player is prompted to save a replay file of the game. From the main menu, the player can then choose to "Watch Replay", where any replay file in the replay folder can be played back. Simple controls like "go forward", "go backwards" and "go to end" are here provided for navigating through the replay file.

<p align="center">
  <img src="./idiotCardGame/images/game-screenshot.png" alt="Game Screenshot" width="49%" />
  <img src="./idiotCardGame/images/replay-screenshot.png" alt="Replay Screenshot" width="49%" />
</p>

## Usage

To run the application locally, follow these steps:

Clone the repository:
```sh
git clone https://github.com/martivj/IdiotCardGame.git
cd IdiotCardGame/idiotCardGame
```

Build the project using Maven:
```sh
mvn clean install
```

Run the application:
```sh
mvn javafx:run
```

When a game is finished, the player is prompted to save a replay file. The replays are stored as `.txt` files in the [`replays`](./idiotCardGame/replays/) folder in the project directory, and can be exported to another location if desired.

## Additional Documentation

- [Code Overview](./idiotCardGame/README.md)