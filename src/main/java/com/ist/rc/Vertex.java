package main.java.com.ist.rc;

/**
*  A general vertex class. As simple as it can be.
*
*  Only information it holds is its id, at even that is optional
*
*/
public class Vertex{

	String id;
	public Vertex(){
		id = null;
	}

	public Vertex(String _id){
		id = _id;
	}

	public void setid(String _id){
		id = _id;
	}

	public String getId(){
		return id;
	}

	public boolean equals(Object obj){
		if(id == null){
		  return obj.equals(this);
		}
		if(obj instanceof Vertex){
			Vertex v = (Vertex) obj;
			return v.id == id;
		}
		return false;
	}


	public String toString(){
		if(id != null){
			return id;
		}
		return super.toString(); 
	}
}