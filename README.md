Jongo + Jersey
==============

Because Jongo uses Jackson by default, exposing the same objects with Jersey won't work if they have an ObjectId. 
This full working sample shows how to make Jersey expose ObjectId as String (and annoted String with @ObjectId too).

In a nutshell:
- a custom provider to expose ObjectId as String and handle @ObjectId
- a dependency to com.sun.jersey:jersey-json replaced by com.fasterxml.jackson.jaxrs:jackson-jaxrs-json-provider (which have the following impacts: POPJO_MAPPING_FEATURE is no longuer needed and every packages has to be move from Jackson 1.x to 2.x)
