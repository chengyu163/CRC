package com.ist.rc;

/**
* 	Simple Edge class. Doesn't hold any information.
*   Use EdgeFactory to abstract instances use.
*
* doesn't make sense to compare Edges based on anything 
*	else but if they are the same instance.
*
*	In a graph, two edges that share the same characteristics are 
*	not the same edge
*
*	At the same time, you don't want to characterize an edge based on its
*	vertexes since you might want to keep them when chaning the vertexes attached
*	to it... for instance. At least that's not the case for a general edge
**/

public class Edge<Strategy>{
	private Strategy edgeBag;

	public Edge(Strategy edgeBag){
	}

	public Edge(){
		edgeBag = null;
	}

	public Strategy getEdgeBag(){
		return edgeBag;
	}

	public void setEdgeBag(Strategy _edgeBag){
		edgeBag = _edgeBag;
	}

}