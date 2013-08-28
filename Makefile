all: test

test: WordCount
	grep Watson wc/part-00000

WordCount: WordCount.class
	-rm -rf wc/
	java -classpath .:`hadoop classpath` WordCount authors/ wc/

WordCount.class: WordCount.java
	javac -classpath `hadoop classpath` WordCount.java

clean:
	-rm *.class
	-rm -rf wc/
