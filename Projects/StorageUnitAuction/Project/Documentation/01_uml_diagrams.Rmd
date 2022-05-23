# UML diagram {#uml-diagram}

Project contains the following diagrams: <!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} --> <!-- code_chunk_output -->

-   [UML diagram](#uml-diagram)
    -   [Class diagram](#class-diagram)
        -   [Description of classes](#description-of-classes)
<!-- /code_chunk_output -->

## Class diagram {#class-diagram}

### Description of classes {#description-of-classes}

-   Main -- Program starts here and invokes first Auction object
-   Auction -- This is the most important part of the project. Here are connected all unit scripts together. All the important counting happens in here.
-   Generator -- Randomly generates the Units.
-   AuctionController -- Controller for the Auction GUI
-   LoginController -- Controller which creates the user and saves his data
-   User -- Class which creates the user with all needed data
-   Bot -- Basic bot that bids some money in the auction
-   Brain -- Nested class inside bot that distinguishes if bot is smart or not
-   CommonUnit -- Base unit consisted of all important variables such as value, items ... From this one program inherits higher quality units.
-   RareUnit/EpicUnit/MysteriousUnit -- All of those are inherited from the unit with the higher quality. Common \< Rare \< Epic \< Mysterious. Units generate Items of the less or the same value as the unit is.
-   CommonItem -- Base item for other Items, which inherits from this item. It is consisted of ItemName, Price ...
-   RareItem/EpicItem/MysteriousItem -- Inherited items from the CommonItem. They contain the same data but with different values.
-   DBUtils -- Utility class which manages the connections to the database
-   StorageUnitType -- Interface that is part of the Factory pattern
-   AuctionFollower -- Interface that informs users and displays some messages
-   AuctionObserver -- Class that is taking a part in the Observer pattern and creates the main requirements for its children
-   Context -- It handles all strategies and is calling actual function that is about to be executed. Also it is taking a part in the Strategy design pattern
-   UnitFactory -- Class that generates storage units randomly. It is part of the Factory design pattern
-   StrategyAddSubMoney -- Defines what implemented classes should contain. It is taking a part in the Strategy design pattern
-   EndMenuController -- Class that controls end menu statistics
-   RegistrationController -- Class that controls the flow of the registration
-   Initializable -- Class observing all GUI events such as buttons
-   OperationSubMoney -- It creates an operation subtraction. Part of the Strategy design pattern
-   OperationAddMoney -- It creates an operation addition. Part of the Strategy design pattern
-   IncorrectPasswordFormatException -- It is the user defined exception to evaluate the wrong password input error

![Class diagram](images/UMLv1.0.2.png){alt="image"}
