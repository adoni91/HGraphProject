package com.limsad.mapreduce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.limsad.model.Graph;
import com.limsad.model.Listitem;

public class Astar_Reducer extends Reducer<Text, Listitem, Text, Text> {

	@Override
	public void reduce(Text key, Iterable<Listitem> values, Context context) throws IOException, InterruptedException {
		Graph graph = new Graph();
		ArrayList<Listitem> close_list=new ArrayList<Listitem>();
		String path ="";
		String listitem="";
		double coast=0;
		for(Listitem value : values){
			close_list.add((Listitem)value);
			listitem+=value.getId()+","+value.getG()+","+value.getH()+","+value.getIdpreviousnode()+";";
			coast+=Double.parseDouble(value.getG().toString());
		}
		/*StringTokenizer token=new StringTokenizer(listitem,";");
		while(token.hasMoreElements()){
			String str=token.nextToken();
			String[] line = str.split(",");
			Text id=new Text(line[0]);
			DoubleWritable g=new DoubleWritable(Double.parseDouble(line[1]));
			DoubleWritable h=new DoubleWritable(Double.parseDouble(line[2]));
			Text idprevious=new Text(line[3]);
			close_list.add(new Listitem(id,g,h,idprevious));
			
		}*/
		String path_key=key.toString().split("_")[1]+"_"+key.toString().split("_")[2];
		path="Path_"+path_key+" = ["+graph.extractPathFromCloseList(close_list)+"]";
		path+=" ,Coast= ["+coast+"]";
		context.write(new Text(key.toString()), new Text(path));
		
	}

}
