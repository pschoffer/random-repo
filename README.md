# random-repo

Airports data from http://ourairports.com/data/ (released under public domain)


Query Option will ask the user for the country name or code and print the airports & runways at each airport. The input can be country code or country name. For bonus points make the test partial/fuzzy. e.g. entering zimb will result in Zimbabwe :)

Choosing Reports will print the following:

    10 countries with highest number of airports (with count) and countries with lowest number of airports.
    Type of runways (as indicated in "surface" column) per country
    Bonus: Print the top 10 most common runway identifications (indicated in "le_ident" column)


TODO:
docker compose for DB
docker run -d -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=airporter --name mysql -p 3306:3306 mysql:latest
