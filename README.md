# hadoop-docs-tutorial - distributed wc

# ABOUT

Here's a funny sort of Hello World for distributed Java programming with MapReduce. This Hadoop job performs a word count over the complete Sherlock Holmes, and displays the frequencies associated with `Watson`.

# EXAMPLE

```
$ tree sherlock-holmes/
sherlock-holmes/
├── agrange.txt
├── b-p_plan.txt
├── bascombe.txt
├── beryl.txt
├── blanced.txt
├── blkpeter.txt
├── bluecar.txt
├── cardbox.txt
├── caseide.txt
├── charles.txt
├── copper.txt
├── creeping.txt
├── crookman.txt
├── danceman.txt
├── devilsf.txt
├── doyle-adventures-380.txt
...
```

```
$ cat WordCount.java
import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class WordCount {
  public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
    private final static IntWritable one = new IntWritable(1);

    public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
      String line = value.toString();

      StringTokenizer tokenizer = new StringTokenizer(line);

      while (tokenizer.hasMoreTokens()) {
        output.collect(new Text(tokenizer.nextToken()), one);
      }
    }
  }
...
```

```
$ make
javac -classpath `hadoop classpath` WordCount.java
jar cvfm WordCount.jar META-INF/MANIFEST.MF *.class
added manifest
adding: WordCount$Map.class(in = 1824) (out= 746)(deflated 59%)
adding: WordCount$Reduce.class(in = 1591) (out= 643)(deflated 59%)
adding: WordCount.class(in = 1516) (out= 741)(deflated 51%)
rm -rf wc/
hadoop jar WordCount.jar sherlock-holmes/ wc/
2013-08-28 11:34:16.509 java[61339:1203] Unable to load realm info from SCDynamicStore
13/08/28 11:34:16 WARN util.NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
13/08/28 11:34:16 WARN mapred.JobClient: Use GenericOptionsParser for parsing the arguments. Applications should implement Tool for the same.
13/08/28 11:34:16 WARN snappy.LoadSnappy: Snappy native library not loaded
13/08/28 11:34:16 INFO mapred.FileInputFormat: Total input paths to process : 50
13/08/28 11:34:16 INFO mapred.JobClient: Running job: job_local214141552_0001
13/08/28 11:34:16 INFO mapred.LocalJobRunner: Waiting for map tasks
13/08/28 11:34:16 INFO mapred.LocalJobRunner: Starting task: attempt_local214141552_0001_m_000000_0
13/08/28 11:34:16 INFO mapred.Task:  Using ResourceCalculatorPlugin : null
13/08/28 11:34:16 INFO mapred.MapTask: Processing split: file:/Users/apennebaker/Desktop/src/hadoop-docs-tutorial/sherlock-holmes/rholm11b.txt:0+642289
13/08/28 11:34:16 INFO mapred.MapTask: numReduceTasks: 1
13/08/28 11:34:16 INFO mapred.MapTask: io.sort.mb = 100
13/08/28 11:34:16 INFO mapred.MapTask: data buffer = 79691776/99614720
13/08/28 11:34:16 INFO mapred.MapTask: record buffer = 262144/327680
13/08/28 11:34:17 INFO mapred.MapTask: Starting flush of map output
13/08/28 11:34:17 INFO mapred.MapTask: Finished spill 0
13/08/28 11:34:17 INFO mapred.Task: Task:attempt_local214141552_0001_m_000000_0 is done. And is in the process of commiting
13/08/28 11:34:17 INFO mapred.LocalJobRunner: file:/Users/apennebaker/Desktop/src/hadoop-docs-tutorial/sherlock-holmes/rholm11b.txt:0+642289
13/08/28 11:34:17 INFO mapred.Task: Task 'attempt_local214141552_0001_m_000000_0' done.
13/08/28 11:34:17 INFO mapred.LocalJobRunner: Finishing task: attempt_local214141552_0001_m_000000_0
13/08/28 11:34:17 INFO mapred.LocalJobRunner: Starting task: attempt_local214141552_0001_m_000001_0
13/08/28 11:34:17 INFO mapred.Task:  Using ResourceCalculatorPlugin : null
13/08/28 11:34:17 INFO mapred.MapTask: Processing split: file:/Users/apennebaker/Desktop/src/hadoop-docs-tutorial/sherlock-holmes/rholm10.txt:0+627461
13/08/28 11:34:17 INFO mapred.MapTask: numReduceTasks: 1
13/08/28 11:34:17 INFO mapred.MapTask: io.sort.mb = 100
13/08/28 11:34:17 INFO mapred.MapTask: data buffer = 79691776/99614720
13/08/28 11:34:17 INFO mapred.MapTask: record buffer = 262144/327680
13/08/28 11:34:17 INFO mapred.MapTask: Starting flush of map output
13/08/28 11:34:17 INFO mapred.MapTask: Finished spill 0
13/08/28 11:34:17 INFO mapred.Task: Task:attempt_local214141552_0001_m_000001_0 is done. And is in the process of commiting
...
grep Watson wc/part-00000
"Watson 3
"Watson,    19
"Watson,"   4
Watson  184
Watson! 76
Watson!"    24
Watson" 7
Watson"-    1
Watson"--he 2
Watson's    15
Watson, 899
Watson,"    229
Watson,''   1
Watson- 4
Watson--abduction!  1
Watson--all 1
Watson--her 1
Watson--mental, 1
Watson--quick,  1
Watson--very    1
Watson-I    1
Watson. 266
Watson."    64
Watson; 15
Watson? 36
Watson?"    100
Watson?,    1
```

This example is tested to successfully compile and run in Mac OS X, currently the only OS for which installing Hadoop isn't a painful exercise in *yak shaving*.

# REQUIREMENTS

* [JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Hadoop](http://hadoop.apache.org/)
* `make`
* `sh`
* Ensure the `hadoop` script is in `$PATH` (test with `which hadoop`).

## Optional

* `tree`

## Mac OS X

Mac comes with JDK and `sh` by default. The remainder can be obtained thusly:

1. Install [Xcode](https://developer.apple.com/xcode/) via `App Store.app`.
2. Launch `Xcode.app`, install the Xcode command line tools.
3. Install [Homebrew](http://brew.sh/).
4. Launch `Terminal.app`, run `brew install hadoop`.

Optionally, run `brew install tree`.

## Windows

JDK, `sh`, `make`, and friends are best installed via Chocolatey:

1. Launch a Command Prompt (`cmd.exe`), install [Chocolatey](http://chocolatey.org/).
2. Run `cinst StrawberryPerl`.
3. Append Strawberry Perl's `make` directory to the Windows `%PATH%` environment variable.
4. Install the JDK with `cinst java.jdk`.
5. Add the `javac.exe` directory to the Windows `%PATH` environment variable.
6. Run `cinst mingw` to install MinGW, which provides `sh.exe`.
7. Manually install Hadoop inside MinGW/Cygwin - [good luck](http://alans.se/blog/2010/hadoop-hbase-cygwin-windows-7-x64/).

`cinst` and friends must be run from a bare Command Prompt, but `make`, `hadoop`, and friends must be run from a MinGW/Cygwin shell. Yes, it's terrible.

Optionally, manually install `tree` - [good luck](http://lassauge.free.fr/cygwin/release/tree/).

## Linux

Linux comes with `sh` by default. The remainder can be obtained thusly:

1. Install `make`. In Ubuntu, this is done with `sudo apt-get install build-essential`).
2. Manually install Oracle Java SE - [good luck](https://help.ubuntu.com/community/Java#Oracle_Java_7).
3. Manually install Hadoop - [good luck](http://www.michael-noll.com/tutorials/running-hadoop-on-ubuntu-linux-single-node-cluster/).

Optionally, install tree (`sudo apt-get install tree`).

## Huh?

Either `git clone https://github.com/mcandre/hadoop-docs-tutorial.git`, or manually download and extract the [ZIP](https://github.com/mcandre/hadoop-docs-tutorial/archive/master.zip)ball.

10 Open a terminal, cd to the hadoop-docs-tutorial directory.
20 Run `make`.
30 Something will fail, hopefully with an error message.
40 Use [Google](https://www.google.com/).
50 Fix the error.
60 GOTO 10

Consider forking [hadoop-docs-tutorial](https://github.com/mcandre/hadoop-docs-tutorial) and amending the README to help other Hadoopers along the way.

# CREDITS

* [Apache MapReduce Tutorial](https://hadoop.apache.org/docs/stable/mapred_tutorial.html#Source+Code) - Source code for `WordCount.java`
