package com.ist.rc;

/**
*  A general vertex class.  
*
*  Only information it holds is its name, which also identifies it.
*
*  Use VertexFactory to keep consistency between Vertex instances
*/
public class Vertex{

	String name;
	public Vertex(String _name){
		name = _name;
	}

	public void setName(String _name){
		name = _name;
	}

	public String getName(){
		return name;
	}

	public boolean equals(Object obj){
		if(obj instanceof Vertex){
			Vertex v = (Vertex) obj;
			return v.name == name;
		}
		return false;
	}

}