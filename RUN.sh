
BINDIR=bin

(javac -Xlint:unchecked -d $BINDIR `find . -name '*.java'`) && (java -cp $BINDIR others.Main)
