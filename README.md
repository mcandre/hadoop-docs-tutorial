# hadoop-docs-tutorial - WordCount example

# ABOUT

There are many Hadoop tutorials and textbooks, but none of the code examples seem to actually compile and run. This minimal WordCount Hadoop job serves as a minimal example, a sort of Hello World for distributed programming in Hadoop, to help programmers get up and running. I learn by example, so I needed to write this and see it run in order to learn more about Hadoop.

# REQUIREMENTS

* [JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Hadoop](http://hadoop.apache.org/)
* `make`
* `sh`

Ensure the `hadoop` script is in `$PATH` (`which hadoop`).

## Mac OS X

1. Launch `App Store.app`, install [Xcode](https://developer.apple.com/xcode/).
2. Launch `Xcode.app`, install the Xcode command line tools.
3. Install [Homebrew](http://brew.sh/).
4. Launch `Terminal.app`, run `brew install hadoop`.

## Windows

1. Launch `cmd.exe`, install [Chocolatey](http://chocolatey.org/).
2. Run `cinst StrawberryPerl`.
3. Append Strawberry Perl's `make` to the Windows `%PATH%` environment variable.
4. Run `cinst mingw` to install MinGW, which provides `sh.exe`.
5. Manually install Hadoop - [good luck](http://alans.se/blog/2010/hadoop-hbase-cygwin-windows-7-x64/).

## Ubuntu

1. Install `make` (`sudo apt-get install build-essential`).
2. Manually install [Oracle Java 7](https://help.ubuntu.com/community/Java#Oracle_Java_7).
3. Manually install Hadoop - [good luck](http://www.michael-noll.com/tutorials/running-hadoop-on-ubuntu-linux-single-node-cluster/).

# EXAMPLE

This Hadoop job performs a word count over the complete works of Arthur Conan Doyle, and displays the frequencies associated with `Watson`.

```
$ make test
...
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

# CREDITS

* [Apache MapReduce Tutorial](https://hadoop.apache.org/docs/stable/mapred_tutorial.html#Source+Code) - Source code for `WordCount.java`
