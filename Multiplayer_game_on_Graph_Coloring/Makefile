
all: clean compile 

setenv-local:
	sed '15s/.*/private static String ENV = "local";/' MainMenu/GraphClient.java > tmp.txt
	mv tmp.txt MainMenu/GraphClient.java

setenv-docker:
	sed '15s/.*/private static String ENV = "docker";/' MainMenu/GraphClient.java > tmp.txt
	mv tmp.txt MainMenu/GraphClient.java

setenv-prod:
	sed '15s/.*/private static String ENV = "prod";/' MainMenu/GraphClient.java > tmp.txt
	mv tmp.txt MainMenu/GraphClient.java

clean:
	rm -rf build/*
	rm -rf buildgf/*
	rm -f dist/app.jar
	# find . -name "*.class" -exec rm -rf {} \;

app: compile
	cd build ; jar -cvfe ../dist/app.jar api.GraphGameServer .

appgf: compilegf
	cd buildgf ; jar cvfe ../dist/appgf.jar *

test: compile app
	java -cp dist/restlet.jar:dist/restlet-json.jar:dist/json.jar:dist/app.jar api.GraphGameServer

compile: 
	javac -cp \
	dist/json.jar:\
	dist/restlet.jar:\
	dist/restlet-json.jar:\
	dist/restlet-jackson.jar:\
	dist/jackson-core-2.8.3.jar:\
	dist/jackson-annotations-2.8.3.jar \
	-d build \
	GraphServer/game/*.java \
	api/*.java \

compilegf:
	javac -cp \
	dist/greenfoot.jar:\
	dist/json.jar:\
	dist/restlet.jar:\
	dist/restlet-json.jar:\
	dist/restlet-jackson.jar:\
	dist/jackson-core-2.8.3.jar:\
	dist/jackson-annotations-2.8.3.jar \
	-d buildgf \
	MainMenu/*.java \

run:
	echo Starting Service at:  http://localhost:8080
	java -cp \
	build:\
	dist/restlet.jar:\
	dist/restlet-json.jar:\
	dist/json.jar:\
	dist/restlet-jackson.jar:\
	dist/jackson-core-2.8.3.jar:\
	dist/jackson-annotations-2.8.3.jar:\
	dist/jackson-dataformat-smile-2.8.3.jar:\
	dist/jackson-databind-2.8.3.jar:\
	dist/jackson-dataformat-xml-2.8.3.jar:\
	dist/jackson-dataformat-yaml-2.8.3.jar:\
	dist/jackson-dataformat-csv-2.8.3.jar \
	api.GraphGameServer

runtest:
	cd ../test ; java KnockKnockServer 8080

loadtest:
	echo Starting Load Test on localhost
	java -cp build:dist/restlet.jar:dist/restlet-json.jar:dist/json.jar RunLoadTest 5

docker-build: app
	docker build -t graphgame .
	docker images

docker-clean:
	docker stop graphgame
	docker rm graphgame
	docker rmi graphgame

docker-run:
	docker run --name graphgame -td graphgame
	docker ps

docker-run-host:
	docker run --name graphgame -td --net=host graphgame
	docker ps

docker-run-bridge:
	docker run --name graphgame -td -p 80:8080 -p 9080:9080 graphgame
	docker ps

docker-network:
	docker network inspect host
	docker network inspect bridge

docker-stop:
	docker stop graphgame
	docker rm graphgame

docker-shell:
	docker exec -it graphgame bash 
	
