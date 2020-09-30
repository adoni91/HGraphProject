package com.limsad.mapreduce;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.limsad.model.Graph;
import com.limsad.model.Listitem;

public class Astar_Reducer extends Reducer<Text, Listitem, Text, Text> {

	@Override
	public void reduce(Text key, Iterable<Listitem> values, Context context) throws IOException, InterruptedException {
		Graph graph = new Graph();
		String path="";
		ArrayList<Listitem> close_list=new ArrayList<Listitem>();
		
		for(Listitem value:values){
			close_list.add(value);
		}
		path=graph.extractPathFromCloseList(close_list);
		context.write(new Text(key.toString()), new Text(path));
		
	}

}
