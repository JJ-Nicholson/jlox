SRC = com/craftinginterpreters/lox
MAIN = $(SRC)/Lox.java

.PHONY: run clean

all:
	javac $(MAIN)

run: all
	java com.craftinginterpreters.lox.Lox

clean:
	rm -f $(SRC)/*.class

