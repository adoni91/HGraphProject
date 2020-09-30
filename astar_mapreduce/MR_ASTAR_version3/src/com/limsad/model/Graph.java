package com.limsad.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Random;
import java.util.StringTokenizer;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;

public class Graph {
	private ArrayList<Node> nodes;

	public Graph() {
		super();
	}

	public Graph(ArrayList<Node> nodes) {
		super();
		this.setNodes(nodes);
	}

	public double getG(String current_node_id, String neighbour_node_id,
			String[][] matrix) {
		double G;
		int c = 0, l = 0;
		for (int i = 0; i < matrix.length; i++) {
			if (matrix[i][0].equals(current_node_id))
				l = i;
			if (matrix[i][0].equals(neighbour_node_id))
				c = i;
		}
		if (matrix[l][c].equals("X"))
			G = Double.POSITIVE_INFINITY;
		else
			G = Double.parseDouble(matrix[l][c].split(",")[0]);
		return G;
	}

	public double getH(String current_node_id, String neighbour_node_id,
			String[][] matrix) {
		double H;
		int c = 0, l = 0;
		for (int i = 0; i < matrix.length; i++) {
			if (matrix[i][0].equals(current_node_id))
				l = i;
			if (matrix[i][0].equals(neighbour_node_id))
				c = i;
		}
		if (matrix[l][c].equals("X"))
			H = Double.POSITIVE_INFINITY;
		else
			H = Double.parseDouble(matrix[l][c].split(",")[1]);
		return H;
	}

	public Node searchNewCurrentNode(ArrayList<Node> node_list, String node_id) {
		Node current_node = new Node();
		for (Node node : node_list) {
			if (node.getId().equals(node_id)) {
				current_node = node;
				break;
			}
		}
		return current_node;
	}

	public void viewListItem(ArrayList<Listitem> list_item) {
		for (Listitem item : list_item) {
			System.out.println(item.toString());
		}
	}

	public void viewNodeList(ArrayList<Node> node_list) {
		for (Node node : node_list) {
			System.out.println(node.toString());
		}
	}

	public void searchNeighboardOfNode(Node current_node, ArrayList<Listitem> open_list, ArrayList<Listitem> explored_list, String[][] matrix) {
		for (String next_id : current_node.getSuccesorsid()) {
			double G = this.getG(current_node.getId(), next_id, matrix);
			double H = this.getH(current_node.getId(), next_id, matrix);
			if (H != Double.POSITIVE_INFINITY && G != Double.POSITIVE_INFINITY) {
				open_list.add(new Listitem(new Text(next_id), new DoubleWritable(G), new DoubleWritable(H), new Text(current_node.getId())));
				explored_list.add(new Listitem(new Text(next_id), new DoubleWritable(G), new DoubleWritable(H), new Text(current_node.getId())));
			}
		}
	}

	public String extractPathFromCloseList(ArrayList<Listitem> close_list) {
		//1: Sort close list by first node visited order
		ArrayList<Integer> l=new ArrayList<Integer>();
		ArrayList<Listitem> close_list_2=new ArrayList<Listitem>();
		
		for(Listitem list: close_list){
			l.add(Integer.parseInt(list.getId().toString()));
			}
		
		Collections.sort(l, new Comparator<Integer>() {
		    @Override
		    public int compare(Integer tc1, Integer tc2) {
		        return tc1.compareTo(tc2);
		    }
		});
		for(int i=0;i<l.size();i++){
			for(int j=0;j<close_list.size();j++){
				if(l.get(i).toString().equals(close_list.get(j).getId().toString())){
					close_list_2.add(close_list.get(j));
					close_list.remove(j);
				}
			}
		}
		
		
		//2: Revised close list
		String path="";
		ArrayList<Listitem> revised_close_list = close_list_2;
		for (int i = 0; i < revised_close_list.size(); i++) {
			Text prev = revised_close_list.get(i).getIdpreviousnode();
			for (int j = 0; j < revised_close_list.size(); j++) {
				if (revised_close_list.get(j).getIdpreviousnode().equals(prev) && j != i)
					revised_close_list.remove(close_list_2.get(i));
			}
		}
		
		//3: Extract path from revised close list
		path+=revised_close_list.get(0).getIdpreviousnode() + " -> ".toString();
		for (Listitem item : revised_close_list) {
			if (item.getId().equals(close_list_2.get(revised_close_list.size() - 1).getId()))
				path+=item.getId().toString();
			else
				path+=item.getId() + " -> ".toString();
		}
		return path;
	}
	
	public int number_of_select_node(ArrayList<Listitem> close_list) {
		ArrayList<Listitem> revised_close_list = close_list;
		for (int i = 0; i < revised_close_list.size(); i++) {
			Text prev = revised_close_list.get(i).getIdpreviousnode();
			for (int j = 0; j < revised_close_list.size(); j++) {
				if (revised_close_list.get(j).getIdpreviousnode().equals(prev)
						&& j != i)
					revised_close_list.remove(close_list.get(i));
			}
		}
		return revised_close_list.size();
	}

	public ArrayList<Node> loadNodefromMatrix(String[][] matrix) {
		ArrayList<Node> node_list = new ArrayList<Node>();
		for (int i = 1; i < matrix.length; i++) {
			ArrayList<String> succesorid = new ArrayList<String>();
			for (int j = 1; j < matrix.length; j++) {
				if (getG(matrix[i][0], matrix[0][j], matrix) != Double.POSITIVE_INFINITY)
					succesorid.add(matrix[0][j]);
			}
			String[] succesors = new String[succesorid.size()];
			for (int k = 0; k < succesorid.size(); k++)
				succesors[k] = succesorid.get(k);
			node_list.add(new Node(matrix[i][0], succesors));
		}
		return node_list;

	}

	public void createMatrixFile(long N) throws FileNotFoundException {
		N = N+1L;
		String[][] matrix = new String[(int) N][(int) N];
		matrix[0][0] = "";
		for (long i = 1; i < N; i++) {
			matrix[(int) i][0] = i + "";
			Random rd = new Random();
			for (long j = 1; j < N; j++) {
				matrix[0][(int) j] = j + "";
				if (i == j)
					matrix[(int) i][(int) j] = "X";
				else {
					if (j == i + 1 || j == i + 2 || j == i + 3 || j == i + 4
							|| j == i + 5 || j == i + 6 || j == i + 7
							|| j == i + 9 || j == i+11 || j == i + 12) {
						if (rd.nextBoolean()) 
							matrix[(int) i][(int) j] = Math.abs(i - j) + rd.nextInt(6)+ "," + (N - j);
						else
							matrix[(int) i][(int) j] = "X";
					} else
						matrix[(int) i][(int) j] = "X";
				}
			}
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		String day = cal.get(Calendar.YEAR) + "_" + cal.get(Calendar.MONTH)+ "_" + cal.get(Calendar.DAY_OF_MONTH);
		String hour = "_" + cal.get(Calendar.HOUR) + "_"+ cal.get(Calendar.MINUTE) + "_" + cal.get(Calendar.SECOND);
		PrintWriter outputfile = new PrintWriter("data/matrix" + day + hour+ "__" + (N - 1));
		for (long i = 0; i < N; i++) {
			String line = "";
			for (long j = 0; j < N + 1; j++) {
				if (j == N) {
					System.out.print("\n");
					line = "\n";
				} else {
				  System.out.print(matrix[(int) i][(int) j] + " ");
					line = matrix[(int) i][(int) j] + " ";
				}
				outputfile.print(line);
			}
		}
		outputfile.close();
	}
	
	public Listitem getMinOpenListNode(ArrayList<Listitem> open_list, Node goal) {
		DoubleWritable min_f = open_list.get(0).getF();
		Listitem selected_item = open_list.get(0);
		for (int i = 1; i < open_list.size(); i++) {
			if (Double.parseDouble(open_list.get(i).getF().toString()) <= Double.parseDouble(min_f.toString())) {
				min_f = open_list.get(i).getF();
				selected_item = open_list.get(i);
			}
			if (open_list.get(i).getId() == new Text(goal.getId())) {
				selected_item = open_list.get(i);
				break;
			}
		}
		return selected_item;
	}

	public String[][] loadMatrix(String line) {
		ArrayList<String> frame = new ArrayList<String>();
		String[][] matrix = null;
		StringTokenizer token=new StringTokenizer(line,";");
		while(token.hasMoreElements()){
			String str=token.nextToken();
			frame.add(str);
		}
			int cmax = frame.get(0).split("\\s+").length;
			int lmax = frame.get(0).split("\\s+").length;
			matrix = new String[lmax][cmax];
			for (int l = 0; l < lmax; l++) {
				String[] split = frame.get(l).split("\\s+");
				for (int c = 0; c < cmax; c++) {
					matrix[l][c] = split[c];
				}
			}
		
		return matrix;
	}
	
	public String[][] loadMatrixOfDistance(String path) {
		BufferedReader in = null;
		ArrayList<String> frame = new ArrayList<String>();
		String[][] matrix = null;
		try {
			in = new BufferedReader(new FileReader(path));
			String str;
			while ((str = in.readLine()) != null) {
				frame.add(str);
			}
			int cmax = frame.get(0).split("\\s+").length;
			int lmax = frame.size();
			matrix = new String[lmax][cmax];
			for (int l = 0; l < lmax; l++) {
				String[] split = frame.get(l).split("\\s+");
				for (int c = 0; c < cmax; c++) {
					matrix[l][c] = split[c];
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return matrix;
	}

	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}

}
