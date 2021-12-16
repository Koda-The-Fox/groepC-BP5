# WaterkersProject
![Waterkers Logo](/Media/Images/logo.png)

### Contributors 
* Jordy van Venrooij ([Koda](https://github.com/Koda-The-Fox)) - JavaFX applicatie.
* Charlotte de Groot ([degrootcharlotte](https://github.com/degrootcharlotte)) - Android applicatie.
* Thobian de koning ([TPJThobian](https://github.com/TPJThobian)) - Heroku & Help met de rest van het project.


### Het project
Het project bestaat uit een hardware gedeelte en een software gedeelte.

### Hardware
In de hardware gedeelte hebben wer een arduino met sensoren die data doort stuurt naar een database.
De waardes zijn voornamelijk bedoeld om data voor de quality of life van een plant te verbeteren.
Te grond- temperatuur en vochtigheid, lucht- temperatuur en vochtigheid, en de pH waarde van water.
Als de waardes niet goed zijn zal een er via het net een waarde binnen de arduino komen die de juiste leds aan zet.

### Software
Dit is het gedeelte waar deze git voor is.
Hier hebben we de applicaties die de data van de arduino weer geeft en mogelijk bewerkbaar maakt.
Er zal een desctop applicatie en een android applicatie zijn.
In de desktop versie kan je alles bekijken en een aantal installingen veranderen/bewerken.
in de android zal een versimpelde overzicht zitten waarmee je snel kan zien of er iets moet gebeuren om de waardes goed te krijgen.

### Ons doel
Wij willen aan de hand van ons product het verbruik van schon water verminderen en de teelt van planten/groenten efficiënter maken.

# ToDo
### Documentatie
- [X] Documentatie;
- [x] Analyse (CogNIAM);

### Hardware
- [x] hardware Verzamelen;
- [X] Geteste sansluitschema;
- [X] Hardware Testen;

### Software
#### Interface (UI)
- [X] Simpel UI;
- [X] Volledig opgemaakte UI;
- [X] Ge-optimaliseerd UI;
- [ ] New/Change Device Dialog UI;
- [ ] New/Change User Dialog UI;
- [X] New/Change Password Dialog UI;

#### Backend
- [X] Login;
- [X] Menu;
- [X] Sensor overzicht;
- [X] Beheer & Instellingen;
- [X] New/Change Device Dialog;
- [X] New/Change User Dialog;
- [X] New/Change Password Dialog;

##### Database
- [X] library toevoegen;
- [X] Connecite(DBCP) maken & testen;
- [ ] Connecite maken & testen;
- [X] Integratie van code en Database;  

# Design
### Java
#### Login screen
![Login Screen Java](/Media/Images/Design/UI/Login_java.jpg)
#### Menu screen
![Menu Screen Java](/Media/Images/Design/UI/menu_java.jpg)
#### Sensor screens
The Sensor Screens are dynamic and will use the same design for all the sensors.
![Grondvochtigheid Screen Java](/Media/Images/Design/UI/grondvochtigheid_java.jpg)


### Android
#### Login screen
![Login Screen Android](/Media/Images/Design/UI/Login_android.jpg)
#### Overzicht screen
![Overzicht Screen Android](/Media/Images/Design/UI/overzicht_android_2.jpg)
