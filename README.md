Jongo + Jersey
==============

Because Jongo uses Jackson by default, exposing the same objects with Jersey won't work if they have an ObjectId. 
This full working sample shows how to make Jersey expose ObjectId as String (and annoted String with @ObjectId too).
