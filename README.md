# ion-java-hello-world
An exercise in using ion-java.

Clone via:
```shell
git clone https://github.com/jobarr-amzn/ion-java-hello-world.git
```

Files available:
* [out.ion](data/out.ion) - A collection of Records as Ion text top-level values
* [out.10n](data/out.10n) - A collection of Records as Ion binary top-level values

Your task is to populate a list of `Record` objects by reading Ion data from one of the above files.

You could start by gaining access to the file contents with e.g.
```java
// Corresponds to the file 'out.10n' - A collection of Records as Ion binary top-level values
InputStream binaryIon = Producer.binaryIonRecordStream();
// Corresponds to the file 'out.ion' - A collection of Records as Ion text top-level values
InputStream textIon = Producer.textIonRecordStream();
```

Work in [App.java](app/src/main/java/App.java). Feel free to use the internet to look for sample code or documentation.
You can use any library you find helpful. Don't over-stress optimality, get it working first.

Test your code by running `App.main` in your IDE, or by executing the app via `./gradlew run` from the project root.

If you have succeeded, the call to `Consumer.processRecords` in `App.main` should make it fairly obvious.