# Environment setup

* Eclipse IDE for Java Developers - 2022-03, Release (4.23.0) Build id: 20220310-1457

* JDK 17
* JavaFX 18
* Scene Builder
* mysql-connector-java-8.0.29

## Installation
* Make sure to change the VM Arguments in the project: open project -> Run Configurations -> Select MainRunConfiguration -> Arguments -> now change VM arguments -> --module-path "YOURPATH\\javafx-sdk-18\\lib" --add-modules javafx.controls,javafx.fxml

* Make sure MySQL is installed properly and running at port 3306 with username 'root' and password 'root'

* If on github follow a shared video installation: 

## Database

* I am using MySQL Workbench 8.0
* Login details to the first user: nick: admin pass:admin
* Default collation: utf8mb4_0900_ai_ci
* Default characterset: utf8mb4