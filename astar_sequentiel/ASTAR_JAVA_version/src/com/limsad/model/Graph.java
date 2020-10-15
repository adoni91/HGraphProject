package com.limsad.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

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

	public void searchNeighboardOfNode(Node current_node,
			ArrayList<Listitem> open_list, ArrayList<Listitem> explored_list,
			String[][] matrix) {
		for (String next_id : current_node.getSuccesorsid()) {
			double G = this.getG(current_node.getId(), next_id, matrix);
			double H = this.getH(current_node.getId(), next_id, matrix);
			if (H != Double.POSITIVE_INFINITY && G != Double.POSITIVE_INFINITY) {
				open_list
						.add(new Listitem(next_id, G, H, current_node.getId()));
				explored_list.add(new Listitem(next_id, G, H, current_node
						.getId()));
			}
		}
	}

	public void extractPathFromCloseList(ArrayList<Listitem> close_list) {
		ArrayList<Listitem> intermediate_close_list = close_list;
		for (int i = 0; i < intermediate_close_list.size(); i++) {
			String prev = intermediate_close_list.get(i).getIdpreviousnode();
			for (int j = 0; j < intermediate_close_list.size(); j++) {
				if (intermediate_close_list.get(j).getIdpreviousnode().equals(prev)&& j != i)
					intermediate_close_list.remove(close_list.get(i));
			}
		}
		System.out.print(intermediate_close_list.get(0).getIdpreviousnode() + " -> ");
		for (Listitem item : intermediate_close_list) {
			if (item.getId().equals(close_list.get(intermediate_close_list.size() - 1).getId()))
				System.out.print(item.getId());
			else
				System.out.print(item.getId() + " -> ");
		}
	}

	public int number_of_select_node(ArrayList<Listitem> close_list) {
		ArrayList<Listitem> revised_close_list = close_list;
		for (int i = 0; i < revised_close_list.size(); i++) {
			String prev = revised_close_list.get(i).getIdpreviousnode();
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

	public void createMatrixFile(long start,long end) throws FileNotFoundException {
		long N = end-start;
		N=N+2L;
		String[][] matrix = new String[(int) N][(int) N];
		matrix[0][0] = "subgraph_"+(start)+"_"+end;
		start-=1;
		for (long i = 1; i < N; i++) {
			matrix[(int) i][0] = i + start+"";
			Random rd = new Random();
			for (long j = 1; j < N; j++) {
				matrix[0][(int) j] = j + start+"";
				if (i == j)
					matrix[(int) i][(int) j] = "X";
				else {
					if (j == i + 1 || j == i + 2 || j == i + 3 || j == i + 4
							|| j == i + 5 || j == i + 6 || j == i + 7
							|| j == i + 9 || j == i+11 || j == i + 12) {
						//if (rd.nextBoolean()) 
							matrix[(int) i][(int) j] = Math.abs(i - j) + rd.nextInt(6)+ "," + (N - j);
						/*else
							matrix[(int) i][(int) j] = "X";*/
					} else
						matrix[(int) i][(int) j] = "X";
				}
			}
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		String day = cal.get(Calendar.YEAR) + "_" + cal.get(Calendar.MONTH)+ "_" + cal.get(Calendar.DAY_OF_MONTH);
		String hour = "_" + cal.get(Calendar.HOUR) + "_"+ cal.get(Calendar.MINUTE) + "_" + cal.get(Calendar.SECOND);
		PrintWriter outputfile = new PrintWriter("/home/biadmin/Desktop/data/matrix" + day + hour+ "__" +(start+1)+"_"+end);
		for (long i = 0; i < N; i++) {
			String line = "";
			for (long j = 0; j < N + 1; j++) {
				if (j == N) {
					//System.out.print("\n");
					line = "\n";
				} else {
				  //System.out.print(matrix[(int) i][(int) j] + " ");
					line = matrix[(int) i][(int) j] + " ";
				}
				outputfile.print(line);
			}
		}
		outputfile.close();
		System.out.println("End creating graph...");
	}
	
	public Listitem getMinOpenListNode(ArrayList<Listitem> open_list, Node goal) {
		double min_f = open_list.get(0).getF();
		Listitem selected_item = open_list.get(0);
		for (int i = 1; i < open_list.size(); i++) {
			if (open_list.get(i).getF() <= min_f) {
				min_f = open_list.get(i).getF();
				selected_item = open_list.get(i);
			}
			if (open_list.get(i).getId() == goal.getId()) {
				selected_item = open_list.get(i);
				break;
			}
		}
		return selected_item;
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
			// System.out.println("Matrix of distance: "+cmax+ " X " +lmax);
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
