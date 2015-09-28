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

/** Hadoop word frequency counter */
public final class WordCount {
  /** Utility class */
  private WordCount() {}

  /** Word mapper */
  public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
    private static final IntWritable ONE = new IntWritable(1);

    /** Split text into words
        @param key (unused)
        @param value the text to split
        @param output word emitter
        @param reporter (unused)
        @throws IOException on IO error
     */
    public void map(
      final LongWritable key,
      final Text value,
      final OutputCollector<Text, IntWritable> output,
      final Reporter reporter
    ) throws IOException {
      final String line = value.toString();

      final StringTokenizer tokenizer = new StringTokenizer(line);

      while (tokenizer.hasMoreTokens()) {
        output.collect(new Text(tokenizer.nextToken()), ONE);
      }
    }
  }

  /** Word frequency aggregator */
  public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {
    /** Aggregate word frequencies
        @param key (unused)
        @param values frequency subtotals
        @param output frequency emitter
        @param reporter (unused)
        @throws IOException on IO error
     */
    public void reduce(
      final Text key,
      final Iterator<IntWritable> values,
      final OutputCollector<Text, IntWritable> output,
      final Reporter reporter
    ) throws IOException {
      int sum = 0;

      while (values.hasNext()) {
        sum += values.next().get();
      }

      output.collect(key, new IntWritable(sum));
    }
  }

  /** CLI entry point
      @param args CLI flags
      @throws Exception on error
   */
  public static void main(final String[] args) throws Exception {
    final JobConf conf = new JobConf(WordCount.class);
    conf.setJobName("wordcount");

    conf.setOutputKeyClass(Text.class);
    conf.setOutputValueClass(IntWritable.class);

    conf.setMapperClass(Map.class);
    conf.setCombinerClass(Reduce.class);
    conf.setReducerClass(Reduce.class);

    conf.setInputFormat(TextInputFormat.class);
    conf.setOutputFormat(TextOutputFormat.class);

    FileInputFormat.setInputPaths(conf, new Path(args[0]));
    FileOutputFormat.setOutputPath(conf, new Path(args[1]));

    JobClient.runJob(conf);
  }
}
