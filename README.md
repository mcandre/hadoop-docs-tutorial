# hadoop-docs-tutorial - distributed wc

# ABOUT

Here's a funny sort of Hello World for distributed Java programming with MapReduce. This Hadoop job performs a word count over the complete Sherlock Holmes, and displays the number of occurrences of the word `Watson`.

# EXAMPLE

Starting with a lot of text in a Hadoop input directory:

```
$ tree resources/sherlock-holmes/
resources/sherlock-holmes/
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

We write a MapReduce job to divide up the work for counting words.

```
$ cat src/main/java/us/yellosoft/hadoop/docs/tutorial/WordCount.java
package us.yellosoft.hadoop.docs.tutorial;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;

import java.util.Iterator;
import java.util.StringTokenizer;
import java.io.IOException;

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

We compile and run the job,

```
$ gradle build
$ hadoop jar build/libs/hadoop-docs-tutorial.jar us.yellosoft.hadoop.docs.tutorial.WordCount resources/sherlock-holmes/ wc/
...
13/08/28 11:34:17 INFO mapred.Task:  Using ResourceCalculatorPlugin : null
13/08/28 11:34:17 INFO mapred.MapTask: Processing split:
file:/Users/apennebaker/Desktop/src/hadoop-docs-tutorial/resources/sherlock-holmes/rholm10.txt:0+627461
13/08/28 11:34:17 INFO mapred.MapTask: numReduceTasks: 1
13/08/28 11:34:17 INFO mapred.MapTask: io.sort.mb = 100
13/08/28 11:34:17 INFO mapred.MapTask: data buffer = 79691776/99614720
13/08/28 11:34:17 INFO mapred.MapTask: record buffer = 262144/327680
13/08/28 11:34:17 INFO mapred.MapTask: Starting flush of map output
13/08/28 11:34:17 INFO mapred.MapTask: Finished spill 0
13/08/28 11:34:17 INFO mapred.Task: Task:attempt_local214141552_0001_m_000001_0 is done. And is in the process of commiting
...
```

Which stores the results in a Hadoop output directory.

```
...
13/08/28 11:34:22 INFO mapred.FileOutputCommitter: Saved output of
task 'attempt_local214141552_0001_r_000000_0' to
file:/Users/apennebaker/Desktop/src/hadoop-docs-tutorial/wc
...
```

We use the data generated by the Hadoop job and notice a funny pattern:

```
$ grep Watson wc/part-00000
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

* [JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html) 1.7+
* [Gradle](http://gradle.org/) 2.1+
* [Hadoop](http://hadoop.apache.org/) 1.2.1+
* `make`
* `sh`
* Ensure the `hadoop` script is in `$PATH` (test with `which hadoop`).

## Optional

* `tree`
* `grep`
* [Sonar](http://www.sonarqube.org/)
* [Infer](http://fbinfer.com/)
* [Ruby](https://www.ruby-lang.org/) 1.9+
* [Guard](http://guardgem.org/) 1.8.2+

Use `bundle` to install Guard.

## Mac OS X

Mac comes with JDK, `sh`, and `grep` by default. The remainder can be obtained thusly:

1. Install [Xcode](https://developer.apple.com/xcode/) via `App Store.app`.
2. Launch `Xcode.app`, install the Xcode command line tools.
3. Install [Homebrew](http://brew.sh/).
4. Launch `Terminal.app`, run `brew install maven hadoop`.

Optionally, run `brew install tree sonar sonar-runner`.

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

Linux comes with `sh` and `grep` by default. The remainder can be obtained thusly:

1. Install `make`. In Ubuntu, this is done with `sudo apt-get install build-essential`).
2. Manually install Oracle Java SE - [good luck](https://help.ubuntu.com/community/Java#Oracle_Java_7).
3. Manually install Hadoop - [good luck](http://www.michael-noll.com/tutorials/running-hadoop-on-ubuntu-linux-single-node-cluster/).

Optionally, install tree (`sudo apt-get install tree`).

## Huh?

Either `git clone https://github.com/mcandre/hadoop-docs-tutorial.git`, or manually download and extract the [ZIP](https://github.com/mcandre/hadoop-docs-tutorial/archive/master.zip)ball.

Consider forking [hadoop-docs-tutorial](https://github.com/mcandre/hadoop-docs-tutorial) and amending the README to help other Hadoopers along the way.

# CREDITS

* [Apache MapReduce Tutorial](https://hadoop.apache.org/docs/stable/mapred_tutorial.html#Source+Code) - Source code for `WordCount.java`

# Git Hooks

See `hooks/`.
