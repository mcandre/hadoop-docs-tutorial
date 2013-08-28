# hadoop-docs-tutorial - WordCount example

# ABOUT

There are many Hadoop tutorials and textbooks, but none of the code examples seem to actually compile and run. This minimal WordCount Hadoop job serves as a minimal example, a sort of Hello World for distributed programming in Hadoop, to help programmers get up and running. I learn by example, so I needed to write this and see it run in order to learn more about Hadoop.

# REQUIREMENTS

* [Hadoop](http://hadoop.apache.org/)
* `make`

Mac users can `brew install hadoop` with [Homebrew](http://brew.sh/).

Then set the `$HADOOP_PREFIX` and `$HADOOP_VERSION` environment variables.

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
