all: test

test: wordcountjob
	grep Watson wc/part-00000

wordcountjob: WordCount.jar cleanoutput
	hadoop jar WordCount.jar authors/ wc/

WordCount.jar: WordCount.class
	jar cvfm WordCount.jar META-INF/MANIFEST.MF *.class

WordCount.class: WordCount.java
	javac -classpath `hadoop classpath` WordCount.java

clean: cleanoutput
	-rm *.jar
	-rm *.class

cleanoutput:
	-rm -rf wc/
