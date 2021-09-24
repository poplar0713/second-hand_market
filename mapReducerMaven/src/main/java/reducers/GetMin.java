package reducers;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;
import java.util.StringTokenizer;

public class GetMin {
    /*
    Object, Text : input key-value pair type (always same (to get a line of input file))
    Text, IntWritable : output key-value pair type
    */
    public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

            // value.toString() : get a line + split criterion = ,
            StringTokenizer token = new StringTokenizer(value.toString(), "|");
            token.nextToken(); // date
            token.nextToken(); // id
            token.nextToken(); // link
            token.nextToken(); // link
            token.nextToken(); // location
            token.nextToken(); // market

            IntWritable price = new IntWritable(Integer.parseInt(token.nextToken())); // price
            Text pid = new Text(token.nextToken());
            context.write(pid, price);
        }

                 /*
        Text, IntWritable : input key type and the value type of input value list
        Text, IntWritable : output key-value pair type
        */

//        public static class MaxReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
//            // variables
//            private IntWritable result = new IntWritable();
//
//            public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
//
//                int max = 0;
//                for (IntWritable val : values) {
//                    if (val.get() > max)
//                        max = val.get();
//                }
//                result.set(max);
//                context.write(key, result);
//            }
//        }

        public static class MinReducer extends Reducer<IntWritable, IntWritable, IntWritable, IntWritable> {
            //variables
            private IntWritable result = new IntWritable();

            public void reduce(IntWritable key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
                int min = Integer.MAX_VALUE;
                for (IntWritable val : values) {
                    if (val.get() < min)
                        min = val.get();
                }
                result.set(min);
                context.write(key, result);
            }
        }

        /* Main function */
        public static void main(String[] args) throws Exception {
            Configuration conf = new Configuration();
            String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
            if (otherArgs.length != 2) {
                System.err.println("Usage: <in> <out>");
                System.exit(2);
            }
            Job job = new Job(conf, "get max");
            job.setJarByClass(GetMin.class);

            // let hadoop know my map and reduce classes
            job.setMapperClass(TokenizerMapper.class);
            job.setReducerClass(MinReducer.class);

            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);

            // set number of reduces
            job.setNumReduceTasks(30);

            // set input and output directories
            FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
            FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
            System.exit(job.waitForCompletion(true) ? 0 : 1);
        }
    }
}
