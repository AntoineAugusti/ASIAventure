SCRIPT=scripts

all : compil test

compil : 
	./$(SCRIPT)/build 

test : 
	./$(SCRIPT)/testBuild 

jouer :
	clear;java -cp ./classes/ fr.insarouen.asi.prog.asiaventure.Main