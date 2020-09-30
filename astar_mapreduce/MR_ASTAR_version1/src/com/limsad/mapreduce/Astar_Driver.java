package com.limsad.mapreduce;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import com.limsad.model.Listitem;

public class Astar_Driver {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		// Use programArgs array to retrieve program arguments.
		String[] programArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		Job job = new Job(conf);
		job.setJarByClass(Astar_Driver.class);
		job.setMapperClass(Astar_Mapper.class);
		job.setReducerClass(Astar_Reducer.class);

		/*job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Listitem.class);*/
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		// TODO: Update the input path for the location of the inputs of the map-reduce job.
		FileInputFormat.addInputPath(job, new Path(programArgs[0]));
		// TODO: Update the output path for the output directory of the map-reduce job.
		FileOutputFormat.setOutputPath(job, new Path(programArgs[1]));

		// Submit the job and wait for it to finish.
		job.waitForCompletion(true);
		// Submit and return immediately: 
		// job.submit();
	}

}
