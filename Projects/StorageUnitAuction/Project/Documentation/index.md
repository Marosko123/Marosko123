--- 
title: "Storage Unit Auction"
author: "Maroš Bednár"
date: "`r Sys.Date()`"
site: bookdown::bookdown_site
documentclass: report
bibliography: [book.bib, packages.bib]
# url: your book url like https://bookdown.org/yihui/bookdown
# cover-image: path to the social sharing image like images/cover.jpg
description: |
  This is a minimal example of using the bookdown package to write a book.
  set in the _output.yml file.
  The HTML output format for this example is bookdown::gitbook,
link-citations: yes
github-repo: rstudio/bookdown-demo
output:  
  github_document:
    toc: true
    toc_depth: 2
  html_document:
    keep_md: true
---

# Project documentation
## Fulfillment of criteria

I have met almost every criterion the professor stated on his website. Also, I have dealt with all of the criteria so I understand them well. Many of them are also applied in the project working version. 

As a result, this project gave me a lot. Now I can say that I understand the database logic, observer patterns and JavaFX graphics GUI. Also, I have met a new program to create a nice documentations in and taught to work with a Virtual Machine. As a big bonus I finally understood that testing is important and without it program cannot be published.

## Certain implementations

I have used inheritance in most of the classes. (Rare, Epic, Mysterious)-Units/Items are all inherited. Polymorphism was applied in Rare, Epic and Mysterious units´ constructors. Aggregation was applied for example to create ActualUnit inside Auction class. All of this is done in the v1.0.0 ProjectFX.

### Main criteria

* encapsulation is user almost everywhere in the code
* polymorphism and inheritance
  * storageUnits.RareUnit.java # 24, # 31
  * storageUnits.EpicUnit.java # 23, # 30
  * storageUnits.MysteriousUnit.java # 24, # 31 
* aggregation
  * auctionClasses.Auction.java # 25, # 29, # 33
  * application.AuctionController.java #59


### Secondary criteria

* Factory pattern
  * auctionClasses.UnitFactory.java # 30
  * auctionClasses.Auction.java # 88
* Strategy pattern
  * auctionClasses.Context.java # 15
  * auctionClasses.OperationAddSubMoney.java # 25
  * auctionClasses.OperationSubMoney.java # 15
  * auctionClasses.OperationAddMoney.java # 15
  * auctionClasses.Auction.java # 187
* Observer pattern
  * auctionClasses.Auction.java # 48
  * application.AuctionObserver.java # 12
  * auctionClasses.User.java # 84
* RTTI
  * auctionClasses.Auction.java # 148
  * auctionClasses.UnitFactory.java # 38
  * storageUnits.CommonUnit.java # 222
* User defined exception
  * application.IncorrectPasswordException.java # 28
  * application.RegistrationController.java # 69
* Serialization
  * application.DBUtils.java # 132 # 133, # 565
* Implicit implementation of the methods inside interface
  * auctionClasses.AuctionFollower.java # 10
  * storageUnits.CommonUnit.java # 64
* Lambda function
  * storageUnits.CommonUnit.java # 185
  * application.EndMenuController.java # 104, # 105, # 106 
* Nested classes and interfaces
  * auctionClasses.Bot.java # 19
* Split GUI and App
  * application.Auction.java 
  * application.AuctionController.java