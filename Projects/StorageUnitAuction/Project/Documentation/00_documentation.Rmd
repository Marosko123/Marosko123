---
editor_options: 
  markdown: 
    wrap: 72
---

# Project description

## Storage Unit Auction

Project description: This project is being developed in Java in Eclipse
IDE using standard libraries and JavaFX libraries for better GUI.

Project is about buying storage units via auction. Buyers bid some money
and if they hold the highest offer at the end of the selling round,
storage unit becomes theirs. Unit is sold automatically after round
finishes. 

Firstly, registration is needed to create a new account in the auction.

Secondly, if registered ,user must click the start auction button on the screen if he wants to start the current auction. Storage units are generated randomly. Basic one is called a Common Unit and it can be
consisted only of the cheapest Common Items. These items are creating a
storage unit value. Due to it starting price increases. An unit with
higher quality is a Rare Unit and it is consisted of the values from the
Common Unit but contains a new Rare Items with a higher price. The same
rule applies to an Epic Unit with more attributes. For example, Epic
Items. Lastly, unit can become Mysterious. Most profitable items are
stored inside this one. There is some chance to get original items with
enormous value, but also fake ones with minimum price, it is kind of
unfortunate. Items in units have a name and a price. All is generated
randomly so there is a minimum chance to create two same storage units.
To make it authentic, AI is added to compete with a buyer...

## Login functions:

-   Enter username
-   Enter password
-   Starts the auction only if the user was registered
-   Log In button will display an auction window

## Register functions:

-   Name, wich doesnt exist
-   Password, which must contain at least 6 digits containing 1 or
    more numbers, 1 small and 1 capital letter and 1 special character
-   Back to login, returns the user back to login menu
-   Register, uses actual input data to register the user

## Auction functions:

-   Start auction button, starts the timer and the auction
-   Bid \$ button, bids the amount of money displayed, refreshes the
    window
-   Next auction button, finds a next possible unit
-   Log out, logs out the user
-   Statistics, opens the statistics menu where user can view auction details

## Statistics functions:

-   Show all units, shows every sold unit
-   Show my units, shows users units
-   Sell my units, sells only users units
-   New auction, starts a new auction
-   Log out, logs out the user

## List of features for current version v1.0.2

-   Nice GUI
-   Menus: Login menu, Registration menu, Auction menu, Statistics menu
-   Price unit generation
-   MySQL checker for the existing users and storage units
-   Countdown
-   Images

## Important code

* auctionClasses.UnitFactory.generateNewUnit # 30
* auctionClasses.Auction.calculate.java # 185
* storageUnits.CommonUnit.setUnitInfo.java # 200
* application.AuctionController.nextAuction # 229
* application.DBUtils.getConnection.java # 34

## Project Skeleton

Current active UML Class diagram:

![Project skeleton](images/UMLv1.0.2.png)

Database Schema diagram:

![Database schema](images/database.png){alt="Project skeleton"}

Brief demonstration of key functionality - Main Prototype:
![Demonstration](images/auction_picture.png)
