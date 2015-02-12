package ch.elca.training.utils.structures;

/**
 * Class to represent a pair of objects.
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
