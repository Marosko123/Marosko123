package slle;

public class SLLElement<T> {
	private T data;
	private SLLElement<T> next;

	public SLLElement() { }	
	public SLLElement(T d) {
		data = d;
	}
	public T getData() {
		return data;
	}
	public SLLElement<T> getNext() {
		return next;
	}
	public void setData(T d) {
		data = d;
	}
	public void setNext(SLLElement<T> e) {
		next = e;
	}
	public String toString() {
		return data.toString();
	}
	public boolean deleteNext(SLLElement<T> sll_e) {
		if(sll_e != null) {
			SLLElement<T> tmp = sll_e.getNext();
			if(sll_e.getNext() != null) 
				sll_e.setNext(sll_e.getNext().getNext());
			else 
				sll_e.setNext(sll_e.getNext());
			
			tmp = null;
			return true;
		}
		return false;		
	}

}
