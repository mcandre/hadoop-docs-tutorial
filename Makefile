all: WordCount.class

WordCount.class: WordCount.java
	javac -classpath $$HADOOP_PREFIX/hadoop-core-$$HADOOP_VERSION.jar WordCount.java

clean:
	-rm *.class
