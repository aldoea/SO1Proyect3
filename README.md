# SO1Proyect3
FileManager with more functions

This is a school project, is about read and write files in two computers in a network simulating how will do a Operative system:
  We have a file, we want to erease something in this file then we write all the file text in TMP(file) without the line that we don´t want and 
  then overwrite TMP in the original file
  file with a change ----> TMP -----> overwrited original fie

I write two files with products, expensives and cheaps
The cheap file is local storage and the expensive file is storage in another computer

The path for the epensive file is declared in the  var "String expensivePath".

## This program does:
  Write expensive file with items
  Write cheap file with items
  Write stock file with all items
  Write client file with clients
  Write TMP for modifications
  Write sells file with sells
  
## Commands:
  Write items:
    <itemId> <itemName> <itemPrice> If the price is lower or equal to 100 it will be a cheap item and then it will be in cheap file
  Write clients:
    client <clientId> <clientName>
  Write sells:
    sell <clientId> <productId> <chantity> If the client or the product don't exist the functión won't create a sell.
  Modify prices:
    modify <productName> <newPrice>    
  

