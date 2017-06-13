# Airline Reservation System

# REST API, Persistence, and Transactions

In this lab, you build a REST API to implement a simple **airline reservation system** through create, get, update, and delete. This system needs to be hosted on Amazon EC2, Google Cloud Platform (GCP), including Compute Engine and App Engine, or any other cloud offering. You must use Spring&#39;s @RestController for the implementation and use JPA for the persistence. You are encouraged to use Spring Boot as well. This is a group assignment with up to **two** members. You can read this Spring guide on [how to build a RESTful Web Service](https://spring.io/guides/gs/rest-service/).

Please design your data model to hold information for the airline reservation system. We define the following requirements and constraints:

-  Each **passenger** can make one or more **reservations**. **Time overlap is not allowed** among any of his reservation.
-  Each reservation may consist of one or more flights.
-  Each **flight** can carry one or more passengers.
-  Each flight uses one **plane** , which is an embedded object with four fields mapped to the corresponding four columns in the airline table.
-  The total amount of passengers can not exceed the capacity of an plane.
-  When a passenger is deleted, all reservation made by him are automatically canceled for him.
-  A flight can not be deleted if it needs to carry at least one passenger.

**Incomplete** definitions of passenger, reservation, flight and plane are given below..

package edu.sjsu.cmpe275.lab2;

public class Passenger {

    private String id;

    private String firstname;

    private String lastname;

    private int age;

    private String gender;

    private String phone; // Phone numbers must be unique

    ...

}

public class Reservation {

    private String orderNumber;

    private Passenger passenger;

    private int price; // sum of each flight&#39;s price.

    private List&lt;Flight&gt; flights;

    ...

}

public class Flight {

    private String flightNumber; // Each flight has a unique flight number.

    private int price;

    private String from;

    private String to;

    /\*  Date format: **yy-mm-dd-hh,** do not include minutes and seconds.

    \*\* Example: 2017-03-22-19

    \*/ The system only needs to supports PST. You can ignore other time zones.

    private Date departureTime;

    private Date arrivalTime;

    private int seatsLeft;

    private String description;

    private Plane plane;  // Embedded

    private List&lt;Passenger&gt; passengers;

    ...

}

public class Plane {

    private int capacity;

    private String model;

    private String manufacturer;

    private int yearOfManufacture;

}

Your app, running in cloud, must be made accessible to the TA, through DNS (e.g., cmpe275-lab2-minisocial.appspot.com), or an IP address. There are five types of requests your app need to support. For simplicity, no authentication or authorization is enforced for these requests. The specification below uses the **hostname** to represent your DNS or IP.

For the output encoding, you must use XML when &quot;xml=true&quot; is expected, otherwise JSON.

(1) Get a passenger back as JSON

| URL | [https://hostname/](http://hostname/profile)passenger/id?json=true |
| --- | --- |
| Method | GET |
| Return | If the passenger can be found with the given ID, return the passenger&#39;s record in JSON format:{        &quot;passenger&quot;: {                &quot;id&quot;: &quot; 123 &quot;,                &quot;firstname&quot;: &quot; John &quot;,                &quot;lastname&quot;: &quot; Oliver &quot;,                &quot;age&quot;: &quot; 21 &quot;,                &quot;gender&quot;: &quot; male &quot;,                &quot;phone&quot;: &quot; 4445556666 &quot;,                &quot;reservations&quot;: {                        &quot;reservation&quot;: [                                {                                        &quot;orderNumber&quot;: &quot;123&quot;,                                        &quot;price&quot;: &quot;240&quot;,                                        &quot;flights&quot;: {                                                &quot;flight&quot;: [                                                        {                                                                &quot;number&quot;: &quot; GH2Z1 &quot;,                                                                &quot;price&quot;: &quot;120&quot;,                                                                &quot;from&quot;: &quot;Seattle, WA&quot;,                                                                &quot;to&quot;: &quot;San Jose, CA&quot;,                                                                &quot;departureTime&quot;: &quot;2017-04-12-09 &quot;,                                                                &quot;arrivalTime&quot;: &quot;2017-04-12-14&quot;,                                                                &quot;description&quot;: &quot;xxxx&quot;,                                                                &quot;plane&quot;: {                                                                        &quot;capacity&quot;: &quot;120&quot;,                                                                        &quot;model&quot;: &quot;Boeing 757&quot;,                                                                        &quot;manufacturer&quot;: &quot;Boeing&quot;,                                                                        &quot;yearOfManufacture&quot;: &quot;1998&quot;                                                                }                                                        },                                                        {                                                                &quot;number&quot;: &quot; HZ124 &quot;,                                                                &quot;price&quot;: &quot;120&quot;,                                                                &quot;from&quot;: &quot;San Jose, CA&quot;,                                                                &quot;to&quot;: &quot;Seattle, WA&quot;,                                                                &quot;departureTime&quot;: &quot;2017-04-14-09 &quot;,                                                                &quot;arrivalTime&quot;: &quot;2017-04-14-14&quot;,                                                                &quot;description&quot;: &quot;xxxx&quot;,                                                                &quot;plane&quot;: {                                                                        &quot;capacity&quot;: &quot;120&quot;,                                                                        &quot;model&quot;: &quot;Boeing 757&quot;,                                                                        &quot;manufacturer&quot;: &quot;Boeing&quot;,                                                                        &quot;yearOfManufacture&quot;: &quot;1998&quot;                                                                }                                                        }                                               ]                                        }                                },                                {                                        &quot;orderNumber&quot;: &quot;345&quot;,                                        &quot;price&quot;: &quot;100&quot;,                                        &quot;flights&quot;: {                                                &quot;flight&quot;: {                                                        &quot;number&quot;: &quot; KJ124 &quot;,                                                        &quot;price&quot;: &quot;100&quot;,                                                        &quot;from&quot;: &quot;San Jose, CA&quot;,                                                        &quot;to&quot;: &quot;Washton, DC&quot;,                                                        &quot;departureTime&quot;: &quot;2017-04-15-09 &quot;,                                                        &quot;arrivalTime&quot;: &quot;2017-04-15-15&quot;,                                                        &quot;description&quot;: &quot;xxxx&quot;,                                                        &quot;plane&quot;: {                                                                &quot;capacity&quot;: &quot;100&quot;,                                                                &quot;model&quot;: &quot;Boeing 757&quot;,                                                                &quot;manufacturer&quot;: &quot;Boeing Airplanes&quot;,                                                                &quot;yearOfManufacture&quot;: &quot;1999&quot;                                                        }                                                }                                        }                                }                        ]                }        }} Otherwise, return:{        &quot;BadRequest&quot;: {                &quot;code&quot;: &quot; 404 &quot;,                &quot;msg&quot;: &quot; Sorry, the requested passenger with id **XXX** does not exist&quot;        }}Note: XXX is the ID specified in the request, and you **must return HTTP error code 404** as well. |
| Description |
-  This JSON is meant for consumption of APIs, and may not render well in browsers unless extensions/plugs are installed.
 |

(2) Get a passenger back as XML

| URL | [https://hostname/](http://hostname/profile)passenger/id?xml=true   |
| --- | --- |
| Method | GET |
| Return | If the passenger can be found with the given ID, return the passenger&#39;s record in XML format:&lt;passenger&gt;        &lt;id&gt; 123 &lt;/id&gt;        &lt;firstname&gt; John &lt;/firstname&gt;        &lt;lastname&gt; Oliver &lt;/lastname&gt;        &lt;age&gt; 21 &lt;/age&gt;        &lt;gender&gt; male &lt;/gender&gt;        &lt;phone&gt; 4445556666 &lt;/phone&gt;        &lt;reservations&gt;                &lt;reservation&gt;                        &lt;orderNumber&gt;123&lt;/orderNumber&gt;                        &lt;price&gt;240&lt;/price&gt;                        &lt;flights&gt;                                &lt;flight&gt;                                        &lt;number&gt; GH2Z1 &lt;/number&gt;                                        &lt;price&gt;120&lt;/price&gt;                                        &lt;from&gt;Seattle, WA&lt;/from&gt;                                        &lt;to&gt;San Jose, CA&lt;/to&gt;                                        &lt;departureTime&gt;                                                               2017-04-12-09                                                            &lt;/departureTime&gt;                                        &lt;arrivalTime&gt;2017-04-12-14&lt;/arrivalTime&gt;                                        &lt;description&gt;xxxx&lt;/description&gt;                                        &lt;plane&gt;                                                &lt;capacity&gt;120&lt;/capacity&gt;                                                &lt;model&gt;Boeing 757&lt;/model&gt;                                                &lt;manufacturer&gt;Boeing&lt;/manufacturer&gt;                                                &lt;yearOfManufacture&gt;                                                                         1998                                                                       &lt;/yearOfManufacture&gt;                                        &lt;/plane&gt;                                &lt;/flight&gt;                                &lt;flight&gt;                                        &lt;number&gt; HZ124 &lt;/number&gt;                                        &lt;price&gt;120&lt;/price&gt;                                        &lt;from&gt;San Jose, CA&lt;/from&gt;                                        &lt;to&gt;Seattle, WA&lt;/to&gt;                                        &lt;departureTime&gt;                                                                2017-04-14-09                                                            &lt;/departureTime&gt;                                        &lt;arrivalTime&gt;2017-04-14-14&lt;/arrivalTime&gt;                                        &lt;description&gt;xxxx&lt;/description&gt;                                        &lt;plane&gt;                                                &lt;capacity&gt;120&lt;/capacity&gt;                                                &lt;model&gt;Boeing 757&lt;/model&gt;                                                &lt;manufacturer&gt;Boeing&lt;/manufacturer&gt;                                                &lt;yearOfManufacture&gt;                                                                             1998                                                                       &lt;/yearOfManufacture&gt;                                        &lt;/plane&gt;                                &lt;/flight&gt;                        &lt;/flights&gt;                &lt;/reservation&gt;                &lt;reservation&gt;                        &lt;orderNumber&gt;345&lt;/orderNumber&gt;                        &lt;price&gt;100&lt;/price&gt;                        &lt;flights&gt;                                &lt;flight&gt;                                        &lt;number&gt; KJ124 &lt;/number&gt;                                        &lt;price&gt;100&lt;/price&gt;                                        &lt;from&gt;San Jose, CA&lt;/from&gt;                                        &lt;to&gt;Washton, DC&lt;/to&gt;                                        &lt;departureTime&gt;                                                                 2017-04-15-09                                                             &lt;/departureTime&gt;                                        &lt;arrivalTime&gt;2017-04-15-15&lt;/arrivalTime&gt;                                        &lt;description&gt;xxxx&lt;/description&gt;                                        &lt;plane&gt;                                                &lt;capacity&gt;100&lt;/capacity&gt;                                                &lt;model&gt;Boeing 757&lt;/model&gt;                                                &lt;manufacturer&gt;Boeing&lt;/manufacturer&gt;                                                &lt;yearOfManufacture&gt;                                                                            1999                                                                       &lt;/yearOfManufacture&gt;                                        &lt;/plane&gt;                                &lt;/flight&gt;                        &lt;/flights&gt;                &lt;/reservation&gt;        &lt;/reservations&gt;&lt;/passenger&gt; Otherwise return:{        &quot;BadRequest&quot;: {                &quot;code&quot;: &quot; 404 &quot;,                &quot;msg&quot;: &quot; Sorry, the requested passenger with id **XXX** does not exist&quot;        }}Note: XXX is the ID specified in the request, and you **must return HTTP error code 404** as well. |
| Description | This XML is meant for consumption of APIs, and may not render well in browsers unless extensions/plugs are installed. |

(3) Create a passenger

| URL | [https://hostname/passenger?firstname=XX&amp;lastname=YY&amp;age=11&amp;gender=famale&amp;phone=123](https://hostname/user/userId?firstname=XX&amp;lastname=YY&amp;title=abc&amp;street=AAA&amp;city=BBB&amp;state=CCC&amp;zip=95012) |
| --- | --- |
| Method | POST |
| Return | If the passenger is created successfully, the request returns the newly created/updated passenger in **Json** , the same as GET [https://hostname/](http://hostname/profile)passenger/id?json=true. Otherwise, return proper HTTP **error code** and an error message of the following format{       &quot;BadRequest&quot;: {              &quot;code&quot;: &quot;400&quot;,               &quot;msg&quot;: &quot;xxx&quot;       }}Note: xxx here is the failure reason; e.g., &quot;another passenger with the same number already exists.&quot; |
| Description | This request creates a passenger&#39;s record in the system.
-  For simplicity, all the passenger&#39;s fields including the phone number (firstname, lastname, age, and gender) are passed as query parameters, and you can assume the request always comes with all the fields specified.
-  The uniqueness of phone numbers must be enforced here.
 |

(4) Update a passenger

| URL | [https://hostname/passenger/id?firstname=XX&amp;lastname=YY&amp;age=11&amp;gender=famale&amp;phone=123](https://hostname/user/userId?firstname=XX&amp;lastname=YY&amp;title=abc&amp;street=AAA&amp;city=BBB&amp;state=CCC&amp;zip=95012) |
| --- | --- |
| Method | PUT |
| Return | If the passenger is updated successfully, the request returns the newly updated passenger in **Json** , the same as GET [https://hostname/](http://hostname/profile)passenger/id?json=true. Otherwise, return{       &quot;BadRequest&quot;: {              &quot;code&quot;: &quot;404 &quot;,              &quot;msg&quot;: &quot;xxx&quot;       }} |
| Description |
-  This request updates a passenger&#39;s record in the system.
-  For simplicity, all the passenger&#39;s fields including the phone number (firstname, lastname, age, and gender) are passed as query parameters, and you can assume the request always comes with all the fields specified.
 |



(5) Delete a passenger

| URL | [https://hostname/passenger/id](http://hostname/profile/userId?firstname=XX&amp;lastname=YY&amp;...http://hostname/profile) |
| --- | --- |
| Method | DELETE |
| Return | If the passenger does not exist, return:{       &quot;BadRequest&quot;: {              &quot;code&quot;: &quot;404 &quot;,              &quot;msg&quot;: &quot;Passenger with id XXX does not exist&quot;       }}You **must return HTTP error code 404** as well. Otherwise, return:&lt;Response&gt;           &lt;code&gt; 200 &lt;/code&gt;           &lt;msg&gt; Passenger with id XXX is deleted successfully  &lt;/msg&gt;&lt;/Response&gt;Note: xxx here is the given ID in the request |
| Description |
-  This request deletes the user with the given user ID.
-  The reservation made by the passenger should also be deleted.
-  You must update the number of available seats for the involved flights.
-  All successful response will use the same XML format.
 |

(6) Get a reservation back as JSON

| URL | [https://hostname/reservation](http://hostname/profile/userId?firstname=XX&amp;lastname=YY&amp;...http://hostname/profile)/number |
| --- | --- |
| Method | GET |
| Return | If the reservation can not be found with the given number, return:{        &quot;BadRequest&quot;: {                &quot;code&quot;: &quot; 404 &quot;,                &quot;msg&quot;: &quot; Reserveration with number XXX does not exist &quot;        }}You **must return HTTP error code 404** as well. Otherwise, return:{        &quot;reservation&quot;: {                &quot;orderNumber&quot;: &quot;123&quot;,                &quot;price&quot;: &quot;240&quot;,                &quot;passenger&quot;: {                        &quot;id&quot;: &quot; 123 &quot;,                        &quot;firstname&quot;: &quot; John &quot;,                        &quot;lastname&quot;: &quot; Oliver &quot;,                        &quot;age&quot;: &quot; 21 &quot;,                        &quot;gender&quot;: &quot; male &quot;,                        &quot;phone&quot;: &quot; 4445556666 &quot;                },                &quot;flights&quot;: {                        &quot;flight&quot;: [                                {                                        &quot;number&quot;: &quot; GH2Z1 &quot;,                                        &quot;price&quot;: &quot;120&quot;,                                        &quot;from&quot;: &quot;Seattle, WA&quot;,                                        &quot;to&quot;: &quot;San Jose, CA&quot;,                                        &quot;departureTime&quot;: &quot;2017-04-12-09 &quot;,                                        &quot;arrivalTime&quot;: &quot;2017-04-12-14&quot;,                                        &quot;seatsLeft&quot;: &quot;15&quot;,                                        &quot;description&quot;: &quot;xxxx&quot;,                                        &quot;plane&quot;: {                                                &quot;capacity&quot;: &quot;120&quot;,                                                &quot;model&quot;: &quot;Boeing 757&quot;,                                                &quot;manufacturer&quot;: &quot;Boeing Airplanes&quot;,                                                &quot;yearOfManufacture&quot;: &quot;1998&quot;                                        }                                },                                {                                        &quot;number&quot;: &quot; HZ124 &quot;,                                        &quot;price&quot;: &quot;120&quot;,                                        &quot;from&quot;: &quot;San Jose, CA&quot;,                                        &quot;to&quot;: &quot;Seattle, WA&quot;,                                        &quot;departureTime&quot;: &quot;2017-04-14-09 &quot;,                                        &quot;arrivalTime&quot;: &quot;2017-04-14-14&quot;,                                        &quot;seatsLeft&quot;: &quot;15&quot;,                                        &quot;description&quot;: &quot;xxxx&quot;,                                        &quot;plane&quot;: {                                                &quot;capacity&quot;: &quot;120&quot;,                                                &quot;model&quot;: &quot;Boeing 757&quot;,                                                &quot;manufacturer&quot;: &quot;Boeing Airplanes&quot;,                                                &quot;yearOfManufacture&quot;: &quot;1998&quot;                                        }                                }                       ]                }        }}  |
| Description |
-  This JSON is meant for consumption of APIs, and may not render well in browsers unless extensions/plugs are installed.
 |

(7) Make a reservation

| URL | [https://hostname/reservation](http://hostname/profile/userId?firstname=XX&amp;lastname=YY&amp;...http://hostname/profile)/number?passengerId=XX&amp;flightLists=AA,BB,CC |
| --- | --- |
| Method | POST |
| Return | If the reservation is created successfully, the request returns the newly created reservation&#39;s record in **XML** , like:&lt;reservation&gt;        &lt;orderNumber&gt;123&lt;/orderNumber&gt;        &lt;price&gt;240&lt;/price&gt;        &lt;passenger&gt;                &lt;id&gt; 123 &lt;/id&gt;                &lt;firstname&gt; John &lt;/firstname&gt;                &lt;lastname&gt; Oliver &lt;/lastname&gt;                &lt;age&gt; 21 &lt;/age&gt;                &lt;gender&gt; male &lt;/gender&gt;                &lt;phone&gt; 4445556666 &lt;/phone&gt;        &lt;/passenger&gt;        &lt;flights&gt;                &lt;flight&gt;                        &lt;number&gt; GH2Z1 &lt;/number&gt;                        &lt;price&gt;120&lt;/price&gt;                        &lt;from&gt;Seattle, WA&lt;/from&gt;                        &lt;to&gt;San Jose, CA&lt;/to&gt;                        &lt;departureTime&gt;2017-04-12-09 &lt;/departureTime&gt;                        &lt;arrivalTime&gt;2017-04-12-14&lt;/arrivalTime&gt;                        &lt;seatsLeft&gt;15&lt;/seatsLeft&gt;                        &lt;description&gt;xxxx&lt;/description&gt;                        &lt;plane&gt;                                &lt;capacity&gt;120&lt;/capacity&gt;                                &lt;model&gt;Boeing 757&lt;/model&gt;                                &lt;manufacturer&gt;                                                   Boeing Airplanes                                &lt;/manufacturer&gt;                                &lt;yearOfManufacture&gt;                                     1998                                &lt;/yearOfManufacture&gt;                        &lt;/plane&gt;                &lt;/flight&gt;                &lt;flight&gt;                        &lt;number&gt; HZ124 &lt;/number&gt;                        &lt;price&gt;120&lt;/price&gt;                        &lt;from&gt;San Jose, CA&lt;/from&gt;                        &lt;to&gt;Seattle, WA&lt;/to&gt;                        &lt;departureTime&gt;2017-04-14-09 &lt;/departureTime&gt;                        &lt;arrivalTime&gt;2017-04-14-14&lt;/arrivalTime&gt;                        &lt;seatsLeft&gt;15&lt;/seatsLeft&gt;                        &lt;description&gt;xxxx&lt;/description&gt;                        &lt;plane&gt;                                &lt;capacity&gt;120&lt;/capacity&gt;                                &lt;model&gt;Boeing 757&lt;/model&gt;                                &lt;manufacturer&gt;Boeing                                                         Airplanes&lt;/manufacturer&gt;                                &lt;yearOfManufacture&gt;                                     1998                                &lt;/yearOfManufacture&gt;                        &lt;/plane&gt;                &lt;/flight&gt;        &lt;/flights&gt;&lt;/reservation&gt; Otherwise, return:{          &quot;BadRequest&quot;: {                 &quot;code&quot;: &quot;400 &quot;,                  &quot;msg&quot;: &quot;xxx&quot;          }}Note: xxx here is the failure reason, and you **must return HTTP error code 404** as well. |
| Description |
-  This request makes a reservation for a passenger.
-  Time-Overlap is not allowed for a certain passenger.
-  The total amount of passengers can not exceed the capacity of the reserved plane.
-  You would receive a list of flights as input.
 |

(8) Update a reservation

| URL | [https://hostname/reservation](http://hostname/profile/userId?firstname=XX&amp;lastname=YY&amp;...http://hostname/profile)/number?flightsAdded=AA,BB,CC&amp;flightsRemoved=XX,YY |
| --- | --- |
| Method | POST |
| Return | If the reservation is updated successfully, the request returns the newly updated reservation in **Json** , the same as GET [https://hostname/reservation/number](https://hostname/reservation/number). Otherwise, return:{          &quot;BadRequest&quot;: {                 &quot;code&quot;: &quot;404 &quot;,                  &quot;msg&quot;: &quot;xxx&quot;          }}Note: xxx here is the failure reason, and you **must return HTTP error code 404** as well. |
| Description |
-  This request update a reservation by adding and/or removing some flights
-  If flightsAdded (or flightsRemoved) param exists, then its list of values cannot be empty.
-  Flights to be added or removed can be **null**
- ** If both additions and removals exist, the non-overlapping constraint should not consider the flights to be removed.**
- ** Update a reservation triggers a recalculation of its total price by summing up the price of each contained flight.**
 |

(9) Search for reservations

| URL | [https://hostname/reservation](http://hostname/profile/userId?firstname=XX&amp;lastname=YY&amp;...http://hostname/profile)?passengerId=XX&amp;from=YY&amp;to=ZZ&amp;flightNumber=GH2Z1 |
| --- | --- |
| Method | GET |
| Return | Return the search result in XML format:&lt;reservations&gt; &lt;reservation&gt;  &lt;orderNumber&gt;123&lt;/orderNumber&gt;  &lt;price&gt;240&lt;/price&gt;  &lt;passenger&gt;   &lt;id&gt; 123 &lt;/id&gt;   &lt;firstname&gt; John &lt;/firstname&gt;   &lt;lastname&gt; Oliver &lt;/lastname&gt;   &lt;age&gt; 21 &lt;/age&gt;   &lt;gender&gt; male &lt;/gender&gt;   &lt;phone&gt; 4445556666 &lt;/phone&gt;  &lt;/passenger&gt;  &lt;flights&gt;   &lt;flight&gt;    &lt;number&gt; GH2Z1 &lt;/number&gt;    &lt;price&gt;120&lt;/price&gt;    &lt;from&gt;Seattle, WA&lt;/from&gt;    &lt;to&gt;San Jose, CA&lt;/to&gt;    &lt;departureTime&gt;                                                      2017-04-12-09                                                &lt;/departureTime&gt;    &lt;arrivalTime&gt;2017-04-12-14&lt;/arrivalTime&gt;    &lt;description&gt;xxxx&lt;/description&gt;    &lt;plane&gt;     &lt;capacity&gt;120&lt;/capacity&gt;     &lt;model&gt;Boeing 757&lt;/model&gt;     &lt;manufacturer&gt;                                                               Boeing                                                           &lt;/manufacturer&gt;     &lt;yearOfManufacture&gt;                                                                1998                                                           &lt;/yearOfManufacture&gt;    &lt;/plane&gt;   &lt;/flight&gt;  &lt;/flights&gt; &lt;/reservation&gt; &lt;reservation&gt;  &lt;orderNumber&gt;345&lt;/orderNumber&gt;  &lt;passenger&gt;   &lt;id&gt; 234 &lt;/id&gt;   &lt;firstname&gt; Emma &lt;/firstname&gt;   &lt;lastname&gt; Latin &lt;/lastname&gt;   &lt;age&gt; 20 &lt;/age&gt;   &lt;gender&gt; female &lt;/gender&gt;   &lt;phone&gt; 22312332 &lt;/phone&gt;  &lt;/passenger&gt;  &lt;price&gt;100&lt;/price&gt;  &lt;flights&gt;   &lt;number&gt; GH2Z1 &lt;/number&gt;    &lt;price&gt;120&lt;/price&gt;    &lt;from&gt;Seattle, WA&lt;/from&gt;    &lt;to&gt;San Jose, CA&lt;/to&gt;    &lt;departureTime&gt;                                                      2017-04-12-09                                                &lt;/departureTime&gt;    &lt;arrivalTime&gt;2017-04-12-14&lt;/arrivalTime&gt;    &lt;description&gt;xxxx&lt;/description&gt;    &lt;plane&gt;     &lt;capacity&gt;120&lt;/capacity&gt;     &lt;model&gt;Boeing 757&lt;/model&gt;     &lt;manufacturer&gt;                                                               Boeing                                                           &lt;/manufacturer&gt;     &lt;yearOfManufacture&gt;                                                                1998                                                           &lt;/yearOfManufacture&gt;    &lt;/plane&gt;   &lt;/flight&gt;  &lt;/flights&gt; &lt;/reservation&gt;&lt;/reservations&gt; |
| Description |
-  This request allow to search for reservations by any combination of single passenger ID, departing city, arrival city, and flight number
-  You can assume that at least one request parameter is specified
 |

(10) Cancel a reservation

| URL | [https://hostname/reservation](http://hostname/profile/userId?firstname=XX&amp;lastname=YY&amp;...http://hostname/profile)/number |
| --- | --- |
| Method | DELETE |
| Return | If the reservation does not exist, return:{        &quot;BadRequest&quot;: {                &quot;code&quot;: &quot; 404 &quot;,                &quot;msg&quot;: &quot; Reservation with number XXX does not exist &quot;        }}You **must return HTTP error code 404** as well. Otherwise, return:&lt;Response&gt;           &lt;code&gt; 200 &lt;/code&gt;           &lt;msg&gt; Reservation with number XXX is canceled successfully  &lt;/msg&gt;&lt;/Response&gt;Note: xxx here is the given number in the request |
| Description |
-  This request cancel a reservation for a passenger.
-  You need to update the number of available seats for the involved flight.
 |

(11) Get a flight back as JSON

| URL | [https://hostname/](http://hostname/profile)flight/flightNumber?json=true |
| --- | --- |
| Method | GET |
| Return | The flight record with given flight number in JSON format. {        &quot;flight&quot;: {                &quot;flightNumber&quot;: &quot; HX837 &quot;,                &quot;price&quot;: &quot;120&quot;,                &quot;from&quot;: &quot; San Jose, CA &quot;,                &quot;to&quot;: &quot; Seattle, WA &quot;,                &quot;departureTime&quot;: &quot; 2017-03-12-09 &quot;,                &quot;arrivalTime&quot;: &quot; 2017-03-12-14 &quot;,                &quot;description&quot;: &quot; xxx &quot;,                &quot;seatsLeft&quot;: &quot; 11 &quot;,                &quot;plane&quot;: {                        &quot;capacity&quot;: &quot; 150 &quot;,                        &quot;model&quot;: &quot; Boeing 747 &quot;,                        &quot;manufacturer&quot;: &quot;Boeing Commercial Airplanes &quot;,                        &quot;yearOfManufacture&quot;: &quot;1997&quot;                },                &quot;passengers&quot;: {                        &quot;passenger&quot;: [                                {                                        &quot;id&quot;: &quot;123&quot;,                                        &quot;firstname&quot;: &quot; John &quot;,                                        &quot;lastname&quot;: &quot; Oliver &quot;,                                        &quot;age&quot;: &quot; 21 &quot;,                                        &quot;gender&quot;: &quot; male &quot;,                                        &quot;phone&quot;: &quot; 4445556666 &quot;                                },                                {                                        &quot;id&quot;: &quot;234&quot;,                                        &quot;firstname&quot;: &quot; Ali &quot;,                                        &quot;lastname&quot;: &quot; Swan &quot;,                                        &quot;age&quot;: &quot; 30 &quot;,                                        &quot;gender&quot;: &quot; female &quot;,                                        &quot;phone&quot;: &quot; 444555777 &quot;                                }                       ]                }        }} If the flight can not be found with the given number, return:{        &quot;BadRequest&quot;: {                &quot;code&quot;: &quot; 404 &quot;,                &quot;msg&quot;: &quot; Sorry, the requested flight with number **XXX** does not exist&quot;        }}You **must return HTTP error code 404** as well. |
| Description | This JSON is meant for consumption of APIs, and may not render well in browsers unless extensions/plugs are installed. |

(12) Get a flight back as XML

| URL | [https://hostname/](http://hostname/profile)flight/flightNumber?xml=true |
| --- | --- |
| Method | GET |
| Return | The flight record with given flight number in XML format. &lt;flight&gt;        &lt;flightNumber&gt; HX837 &lt;/flightNumber&gt;        &lt;price&gt;120&lt;/price&gt;        &lt;from&gt; San Jose, CA &lt;/from&gt;        &lt;to&gt; Seattle, WA &lt;/to&gt;        &lt;departureTime&gt; 2017-03-12-09 &lt;/departureTime&gt;        &lt;arrivalTime&gt; 2017-03-12-14 &lt;/arrivalTime&gt;        &lt;description&gt; xxx &lt;/description&gt;        &lt;seatsLeft&gt; 11 &lt;/seatsLeft&gt;        &lt;plane&gt;                &lt;capacity&gt; 150 &lt;/capacity&gt;                &lt;model&gt; Boeing 747 &lt;/model&gt;                &lt;manufacturer&gt;Boeing Commercial Airplanes &lt;/manufacturer&gt;                &lt;yearOfManufacture&gt;1997&lt;/yearOfManufacture&gt;        &lt;/plane&gt;        &lt;passengers&gt;                &lt;passenger&gt;                        &lt;id&gt;123&lt;/id&gt;                        &lt;firstname&gt; John &lt;/firstname&gt;                        &lt;lastname&gt; Oliver &lt;/lastname&gt;                        &lt;age&gt; 21 &lt;/age&gt;                        &lt;gender&gt; male &lt;/gender&gt;                        &lt;phone&gt; 4445556666 &lt;/phone&gt;                &lt;/passenger&gt;                &lt;passenger&gt;                        &lt;id&gt;234&lt;/id&gt;                        &lt;firstname&gt; Ali &lt;/firstname&gt;                        &lt;lastname&gt; Swan &lt;/lastname&gt;                        &lt;age&gt; 30 &lt;/age&gt;                        &lt;gender&gt; female &lt;/gender&gt;                        &lt;phone&gt; 444555777 &lt;/phone&gt;                &lt;/passenger&gt;        &lt;/passengers&gt;&lt;/flight&gt; If the flight can not be found with the given number, return:{        &quot;BadRequest&quot;: {                &quot;code&quot;: &quot; 404 &quot;,                &quot;msg&quot;: &quot; Sorry, the requested flight with number XXX does not exist&quot;        }}You **must return HTTP error code 404** as well. |
| Description |
-  This XML is meant for consumption of APIs, and may not render well in browsers unless extensions/plugs are installed.
 |

(13) Create or update a flight

| URL | https://hostname/flight/flightNumber?price=120&amp;from=AA&amp;to=BB&amp;departureTime=CC&amp;arrivalTime=DD&amp;description=EE&amp;capacity=GG&amp;model=HH&amp;manufacturer=II&amp;yearOfManufacture=1997 |
| --- | --- |
| Method | POST |
| Return | If the flight is created/updated successfully, it should return the newly created/updated flight in XML, the same as GET [https://hostname/](http://hostname/profile)flight/flightNumber?xml=true Otherwise, return the appropriate error code, 400 or 404, and error message, e.g., {        &quot;BadRequest&quot;: {                &quot;code&quot;: &quot; 404 &quot;,                &quot;msg&quot;: &quot;xxx&quot;        }}Note: xxx here is the failure reason, and you **must return HTTP error code** as well. |
| Description |
-  This request creates/updates a new flight for the system.
-  For simplicity, all the fields are passed as query parameters, and you can assume the request always comes with all the fields specified.
-  The corresponding flight should be created/updated accordingly.
-  You may need to update the seatsLeft when capacity is modified.
-  When attempting reduce the existing capacity of a flight, the request must fail with error code 400 if active reservation count for this flight is higher than the target capacity.
-  If change of a flight&#39;s departure and/or arrival time causes a passenger to have **overlapping flight time** , this update cannot proceed and hence **fails** with error code 400.
-  Changing the price of a flight will not affect the total price of existing reservations.
 |

(14) Delete a flight

| URL | [https://hostname/airline/flightNumber](https://hostname/airline/flightNumber) |
| --- | --- |
| Method | DELETE |
| Return | If the flight with the given flight number does not exist, return:{        &quot;BadRequest&quot;: {                &quot;code&quot;: &quot; 404 &quot;,                &quot;msg&quot;: &quot; xxx &quot;        }}Note, xxx here is the reason why you can not delete the flight. You **must return HTTP error code 404** as well. Otherwise, return:&lt;Response&gt;           &lt;code&gt; 200 &lt;/code&gt;           &lt;msg&gt; Flight with number XXX is deleted successfully  &lt;/msg&gt;&lt;/Response&gt;Note: xxx here is the given number in the request |
| Description |
-  This request deletes the flight for the given flight number.
-  You can not delete a flight that has one or more reservation, in which case, the deletion should fail with error code 400.
 |

Additional Requirements

1. **a.** All these operations should be **transactional**.
2. **b.** You must use **JPA** and persist the user data into a database. If you are on Amazon EC2, you need to use MySQL; For GCP, you can use either [Cloud Datastore](https://cloud.google.com/datastore/docs/), or [Cloud SQL](https://cloud.google.com/sql/docs/).
3. **c.** Please add proper **JavaDoc comments**.
4. **d.** You must keep your server running for at least three weeks upon submission. Once your code is submitted to Canvas, you cannot make any further deployment/upload to your app on the server, or it will be considered as late submission or even cheating. You may be asked to show the server log and deployment history upon the TA&#39;s request.

Submission

Please submit through Canvas. Do not include jars or compiled class files.

-  This is a group assignment, even though you can be in your own group.
-  You must include a file named readme.pdf, which contains:
  -  Group member info
  -  Cloud Service Choice: Amazon, GCP App Engine, GCP Compute Engine, etc.
  -  Hostname (or IP address)
  -  28 screenshots, one for each properly formatted result returned by the requests, and one for the according error page. For each screenshot, you must also paste the URL text (clearly readable) right before your image, so that it&#39;s handy for the TA to copy and paste as needed for grading purpose

Grading

This lab has a total point of 10, with 9 points for correctness (including persistence, transaction, etc), and 1 point for documentation, code structure, and unit testing.

Additional Info

1. **1.** Since SJSU is an AWS Educate Institution member, students should be able to sign up and apply for free credits at [https://aws.amazon.com/education/awseducate/members](https://aws.amazon.com/education/awseducate/members)
2. **2.** The free tier of GCP should be sufficient for this lab.