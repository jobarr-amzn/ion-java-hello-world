# ion-java-hello-world
An exercise in using ion-java.

Files available:
* [out.ion](app/out.ion) - A collection of Records as Ion text top-level values
* [out.10n](app/out.10n) - A collection of Records as Ion binary top-level values

Your task is to populate a list of `Record` objects by reading Ion data from one of the above files.

You could start by gaining access to the file contents with e.g.
```java
InputStream in = new FileInputStream("out.10n");
```

Work in `App.java`. Feel free to use the internet to look for sample code or documentation.
You can use any library you find helpful.

Test your code by running `App.main` in your IDE, or by executing the app via `./gradlew run` from the project root.

If you have succeeded, the call to `processRecords` in `App.main` should make it fairly obvious.