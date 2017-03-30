My details
===========================
Author: Pawel Duda
Email: pawel.adam.duda@gmail.com


Entry point
==========================
OrderService class is the entry point of the library. UI should interact with it.


Business assumptions
===========================
I assumed (as per requirements) that the Live Board doesn't need to display orders, but instead just their summaries (Requirement 3). The requirements 1 and 2 only ask for ways to modify the state of orders and not display them in any way.

Validation was not implemented because requirements haven't specified it. This includes for example: cancelling not existing orders or passing in negative prices.


Test code assumptions
===========================
AcceptanceTest class was introduced assuming a programmer will review it. If we were to write acceptance tests as a documentation for the business people, the code syntax should be most likely replaced with plain English phrases (perhaps supported by a different tool).

AcceptanceTest class covers Requirement 3 (and as a result of that - 1), whilst the requirement 2 is covered at the unit test level in OrderServiceTest. In real application (with a DAO class) an extra AcceptanceTest would be appropriate. For this exercise I would have to essentially duplicate it.


Production code assumptions
===========================
Main driver behind my assumptions was *simplicity*. In a real-life application it's almost certain my *simple* design choices would need to be changed as explained below.

Order class has primitive fields (kilograms represented as double, prices represented as integers, user ids as strings). Whilst that's good enough for such a simple application in pretty much any real project the code will exercise pressure to convert them to proper types for a number of reasons (refactoring, searching, code readability, documentation, removal of duplication, centralised validation, etc.). Especially Money pattern (https://www.martinfowler.com/eaaCatalog/money.html) is absolutely essential to avoid duplication of currency conversions or rounding code all over the code base.

OrderService class stores the orders directly as a field. In a real application some sort of DAO (Repository) class would be used to separate concerns and perhaps have 2 different implementations, for instance: InMemoryOrderDao (used in tests) and MongoOrderDao (used in production).

UI can interact either via polling the library or by being called from the library. For the purposes of this test I implemented for the former but in real-life an event-based system might be more appropriate.


Links
===========================
Money pattern: https://www.martinfowler.com/eaaCatalog/money.html