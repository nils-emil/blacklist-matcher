## Blacklist-matcher

This project was developed with Java 8. 

### Running the project with Docker: 
1. Build the jar with gradle `gradle jar` (or use the included gradle wrapper `gradlew.bat jar` for windows and `./gradlew jar` for unix-like systems)
2. Build the docker image `docker build . -t matcher`
3. Run the image `docker run matcher` (The container will stop after the process completes, but the output from main method will be visible)

The class that is run, is defined in build.gradle under manifest attributes. 

If any changes were to be made, all steps need to be repeated.

### Running the project from command-line:
1. Build the jar with gradle `gradle jar` (or use the included gradle wrapper `gradlew.bat jar` for windows and `./gradlew jar` for unix-like systems)
2. Run the jar `java -jar ./build/libs/matcher-1.0.jar`

### Running the project from IntelliJ:
1. Run the `Main.java` file in `src/java/main`

### Resources:
The csv files are in `resources` folder and contain the following files:
* names.csv - list of the blacklisted names, 1 full name per line
* noise_words.csv - list of noise words, 1 word per line
     

