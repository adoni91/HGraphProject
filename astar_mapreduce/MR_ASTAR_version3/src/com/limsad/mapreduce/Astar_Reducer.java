package com.limsad.mapreduce;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.limsad.model.Graph;
import com.limsad.model.Listitem;

public class Astar_Reducer extends Reducer<Text, Listitem, Text, Text> {
	private ArrayList<Listitem> close_list;
	private Graph graph;
	@Override
	public void reduce(Text key, Iterable<Listitem> values, Context context) throws IOException, InterruptedException {
		graph = new Graph();
		String path="";
		double coast=0;
		close_list=new ArrayList<Listitem>();
		for(Listitem value:values){
			Text id=new Text(value.getId().toString());
			Text idprevious=new Text(value.getIdpreviousnode().toString());
			DoubleWritable g=new DoubleWritable(Double.parseDouble(value.getG().toString()));
			DoubleWritable h=new DoubleWritable(Double.parseDouble(value.getH().toString()));
			coast+=Double.parseDouble(value.getG().toString());
			close_list.add(new Listitem(id, g, h, idprevious));
		}
		path=graph.extractPathFromCloseList(close_list);
		graph.viewListItem(close_list);
		
		String path_key=key.toString().split("_")[1]+"_"+key.toString().split("_")[2];
		path="Path_"+path_key+" = ["+path+"]";
		path+=" ,Coast= ["+coast+"]";
		context.write(new Text(key.toString()), new Text(path));
		
		
	}

}