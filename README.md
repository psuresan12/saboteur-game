## DESIGN PATTERNS USED

# Builder Pattern
- Intent: Separate the construction of a complex object from its representation so that the same construction process can create different representations.

- Purpose:
A complex aggregate of cards from enumerator used to build the card deck for a new game. PathCards, ActionCards, PersonalCards, ActionCardPairingEnum (used to find key-value pairings quickly), and entire deck was returned as the final product.

- Application:
Director is the Deck, which needs to initialise a shuffled deck upon creation. The DeckBuilderImpl concrete class is called (via type inheritance of the DeckBuilder interface) from the Deck to build the card deck. The steps to build each type of card is returned and the game is able to proceed with a working deck.

- Deck, <<Interface>>DeckBuilder, DeckBuilderImpl

# Singleton Pattern
- Intent: Ensure a class only has one instance, and provide a global point of access to it.
- GameManager, UI

# Prototype Pattern
- Intent: Specify the kinds of objects to create using a prototypical instance, and create new objects by copying this prototype.
- GameOperations, GameStatusImpl

- Purpose:
The references of game items(Player,deck,grid) are to be stored for operations like  UNDO and save/Resume game operation.Also, It is critical to ensure that stored references are not to be affected by game play.

- Application:
There is a necessity of deep cloning the above  game items. Hence, prototype pattern is used for creating references(deep cloning , with the help of serialization) to achieve the above listed operation


# Bridge Pattern
- Intent: 
Decouple an abstraction from its implementation so that the two can vary independently.
Publish interface in an inheritance hierarchy, and bury implementation in its own inheritance hierarchy.
- Grid, <<Interface>>Shape,<<Interface>>Card, PathCard
- Purpose:
A complex aggregate of cards from enumerator used to build the card deck for a new game. PathCards, ActionCards, PersonalCards, ActionCardPairingEnum (used to find key-value pairings quickly), and entire deck was returned as the final product.

- Application:
Director is the Deck, which needs to initialise a shuffled deck upon creation. The DeckBuilderImpl concrete class is called (via type inheritance of the DeckBuilder interface) from the Deck to build the card deck. The steps to build each type of card is returned and the game is able to proceed with a working deck.

# Decorator Pattern
- Intent: Attach additional responsibilities to an object dynamically. Decorators provide a flexible alternative to subclassing for extending functionality.
- <<Interface>>Player, AbstractPlayer, SimplePlayer, LuckyPlayer, PowerPlayer

- Purpose:
Dynamically adding operation to player, but not to make unnecessary subclasses for player.Also, the added operation should perform recursive forwarding.

- Application:
The two decorators like Powerplayer decorator and lucky player decorator are used. 
Power Player Decorator: Decorates the simple player with power, so that player gets the chance to play every turn twice.As the simple player keeps on decorated, the power level increases and there by player continue to play twice for every turn.

- Lucky Player Decorator: 
Decorates the simple player with lucky coin, so that the player gets the additional coin by just participating the turn. As the simple player keeps on decorated, the lucky level increases and there by the additional coin also increases as the player continues his turn.

Because of these decorator pattern, The additional operation of either or both of the above decorator wraps the simple player, achieving the recursive forwarding implementation.

- Removing Decorator:
All decorator(s) has(have) been removed from the stack (from last) until it reaches the correct decorator. After the correct decorator has been removed , all the other removed decorator(s) has/have added back to the original player.


# Command Pattern
- Intent: Encapsulate a request in an object. Allows the parameterization of clients with different requestsallows saving the requests in a queue.

- Purpose:
Encapsulate the request, and reduce the effort of creating multiple objects for same operation.

- Application:
Save Command 
- execute() - used to store the references of game items
- undo() - used to remove the references of game items

Save command:
- execute() - Used to save the game state in the external source
- undo() - Used to delete the game state in the external source


# Visitor Pattern
- Intent: Represent an operation to be performed on the elements of an object structure. Visitor lets you define a new operation without changing the classes of the elements on which it operates.
- <<Interface>>Visitor, <<Interface>>Element, PaintVisitor,ColorVisitor
- Purpose: Adding additional operation, without altering the existing code.

- Application:
From view perspective, both path cards and the other normal cards are to be treated separately ,especially when comes to path(s).Two additional operations like painting and getting the color references are to be considered. This is achieved with two visitor (paintVisitor and colorVisitor).
- paintVisitor: Decides the path and painting logic to different among different cards
- colorVisitor: Decides the color among different cards.

# Template Method Pattern
- Intent: Define the skeleton of an algorithm in an operation, deferring some steps to subclasses. Template Method lets subclasses redefine certain steps of an algorithm without changing the algorithm's structure.
- Grid.isAdjacentLeaf(), <<Interface>>PathSolver, HexPathSolver

- Purpose:
Required to separate the algorithm implementation for a given type from the template method. The PathSolver interface is the abstraction whereby the template method can call the appropriate algorithm. This allows multiple algorithms to be implemented for different shaped cards, each implementation buried in each sub-class. At the moment, HexPathSolver implements the Hexagon path card solver.

- Application:
Grid.isAdjacentLeaf() checks surrounding grid locations and checks whether each location is an adjacent leaf to the current node list. This is when the template method is called (HexPathSolver) solver.isAdjacentLeaf(). The PathSolver is the abstraction and HexPathSolver is the concrete sub-class, containing the appropriate algorithm for hexagon-shaped cards. In future, if the grid is setup to be rectangular, the RecPathSolver would be called instead.


## DbC - COFOJA used as in below line number
#Grid 
- line 222 ... @Requires({ "card.size() > 1" })
- line 238 ... @Requires({ "c.getRow() >= 0 && c.getCol() >= 0" })

