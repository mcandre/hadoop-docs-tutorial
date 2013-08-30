all: joke

joke: job
	grep Watson wc/part-00000

job: jar cleanoutput
	hadoop jar WordCount.jar sherlock-holmes/ wc/

jar: WordCount.class
	jar cvfe WordCount.jar WordCount *.class

WordCount.class: WordCount.java
	javac -classpath `hadoop classpath` WordCount.java

clean: cleanoutput
	-rm *.jar
	-rm *.class

cleanoutput:
	-rm -rf wc/
