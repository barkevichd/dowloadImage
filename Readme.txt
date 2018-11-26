This program gets image url from "https://randomfox.ca/floof/", downloads it into specified folder
and saves parameters like image address, time of download and image size into data base
Configurable parameters:
path to image saving folder,
path to data base,
data base name
Default values of these parameters:
path to image saving folder - C:\\testFolder
path to data base - C:\\testDBFolder
data base name - saveDB

How to execute:
build .jar file if not built yet,

- to run this program with default values navigate to .jar storing folder via console or command line
and execute following command: java -jar *jarFileName*.jar

- to run this program with configurable values navigate to .jar storing folder via console or command line
and execute following command: java -Dimage.folder=*path to image saving folder* -Ddatabase.folder=*path to data base* -Ddatabase.name=*data base name* -jar *jarFileName*.jar

Endpoint of this program execution will be the message: "Program executed successfully" in console or command line log

NB: this program tested with windows env only