all: test

test: WordCount
	grep Watson wc/part-00000

WordCount: WordCount.jar
	-rm -rf wc/
	hadoop jar WordCount.jar authors/ wc/

WordCount.jar: WordCount.class
	jar cvfm WordCount.jar META-INF/MANIFEST.MF *.class

WordCount.class: WordCount.java
	javac -classpath `hadoop classpath` WordCount.java

clean:
	-rm *.jar
	-rm *.class
	-rm -rf wc/
