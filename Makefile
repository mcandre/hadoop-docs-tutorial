all: test

test: WordCount
	grep Watson wc/part-00000

WordCount: WordCount.class
	-rm -rf wc/
	java -classpath .:$$HADOOP_PREFIX/hadoop-core-$$HADOOP_VERSION.jar:$$HADOOP_PREFIX/lib/commons-logging-1.1:$$HADOOP_PREFIX/lib/commons-configuration-1.6.jar:$$HADOOP_PREFIX/lib/commons-logging-api-1.0.4.jar:$$HADOOP_PREFIX/lib/commons-lang-2.4.jar:$$HADOOP_PREFIX/lib/jackson-mapper-asl-1.8.8.jar:$$HADOOP_PREFIX/lib/jackson-core-asl-1.8.8.jar:$$HADOOP_PREFIX/lib/commons-httpclient-3.0.1.jar WordCount authors/ wc/

WordCount.class: WordCount.java
	javac -classpath $$HADOOP_PREFIX/hadoop-core-$$HADOOP_VERSION.jar WordCount.java

clean:
	-rm *.class
	-rm -rf wc/
