package ch.elca.training.utils.structures;

/**
 * Tuple (pair of objects) data structure.
 * 
 * @author DTR
 */
public class Tuple<X, Y> {
	public X first;
	public Y second;
	
	public Tuple(X first, Y second) {
		this.first = first;
		this.second = second;
	}
}
