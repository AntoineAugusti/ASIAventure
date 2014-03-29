#!/bin/bash
cd classes
rm -rf fr
cd ../classestest
rm -rf fr
cd ..
javac -sourcepath ./src -d ./classes ./src/fr/insarouen/asi/prog/asiaventure/elements/objets/serrurerie/*.java ./src/fr/insarouen/asi/prog/asiaventure/elements/objets/*.java ./src/fr/insarouen/asi/prog/asiaventure/elements/structure/*.java ./src/fr/insarouen/asi/prog/asiaventure/elements/*.java ./src/fr/insarouen/asi/prog/asiaventure/*.java
javac -sourcepath ./srctest:./src -cp ./classes:/usr/share/java/junit4-4.11.jar -d ./classestest ./srctest/fr/insarouen/asi/prog/asiaventure/*.java
java -cp ./classes:./classestest:/usr/share/java/junit4-4.11.jar org.junit.runner.JUnitCore fr.insarouen.asi.prog.asiaventure.SuiteASIAventure