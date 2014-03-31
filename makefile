SCRIPT=scripts

all : compil test

compil : 
	./$(SCRIPT)/build 

test : 
	./$(SCRIPT)/testBuild 