package structures;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<T extends Comparable<T>> implements
		BSTInterface<T> {
	protected BSTNode<T> root;

	public boolean isEmpty() {
		return root == null;
	}

	public int getSize() {
		// TODO
		Iterator<T> preorder = preorderIterator();
		int size = 0;
		while(preorder.hasNext()){
			preorder.next();
			size = size + 1;
		}
		return size;
	}

	public boolean contains(T t) {
		// TODO
		if(t == null){
			throw new NullPointerException();
		}
		if(isEmpty()){
			return false;
		}
		if(getElement(t) != null){
			return true;
		}
		return false;
	}

	public boolean removeElement(T t) {
		// TODO
		BSTNode<T> parent = null;
		BSTNode<T> cur = root;
		if(t == null){
			throw new NullPointerException();
		}
		while(cur != null){
			if(cur.getData().equals(t)){
				if(cur.getLeft() == null && cur.getRight() == null){
					if(parent == null){
						root = null;
					}
					else if(parent.getLeft() == cur){
						parent.setLeft(null);
					}
					else{
						parent.setRight(null);
					}
				}
				else if(cur.getRight() == null){
					if(parent == null){
						root = cur.getLeft();
					}
					else if(parent.getLeft() == cur){
						parent.setLeft(cur.getLeft());
					}
					else{
						parent.setRight(cur.getLeft());
					}
				}
				else if(cur.getLeft() == null){
					if(parent == null){
						root = cur.getRight();
					}
					else if(parent.getLeft() == cur){
						parent.setLeft(cur.getRight());
					}
					else{
						parent.setRight(cur.getRight());
					}
				}
				else{
					BSTNode<T> suc = cur.getRight();
					while(suc.getLeft() != null){
						suc = suc.getLeft();
					}
					BSTNode<T> successorData = suc;
					removeElement(successorData.getData());
					cur.setData(successorData.getData());
				}
				return true;
			}
			else if(cur.getData().compareTo(t) < 0){
				parent = cur;
				cur = cur.getRight();
			}
			else{
				parent = cur;
				cur = cur.getLeft();
			}
		}
		return false;
	}

	public T getHighestValueFromSubtree(BSTNode<T> node) {
		// node must not be null
		if (node.getRight() == null) {
			return node.getData();
		} 
		else {
			return getHighestValueFromSubtree(node.getRight());
		}
	}

	public T getLowestValueFromSubtree(BSTNode<T> node) {
		// TODO
		if (node.getLeft() == null) {
			return node.getData();
		} 
		else {
			return getLowestValueFromSubtree(node.getLeft());
		}
	}

	private BSTNode<T> removeRightmostFromSubtree(BSTNode<T> node) {
		// node must not be null
		if (node.getRight() == null) {
			return node.getLeft();
		} 
		else {
			node.setRight(removeRightmostFromSubtree(node.getRight()));
			if (node.getRight() != null){
				node.getRight().setParent(node);
			}
			return node;
		}
	}

	public BSTNode<T> removeLeftmostFromSubtree(BSTNode<T> node) {
		// TODO
		if (node.getLeft() == null) {
			return node.getRight();
		} 
		else {
			node.setLeft(removeLeftmostFromSubtree(node.getLeft()));
			if (node.getLeft() != null){
				node.getLeft().setParent(node);
			}
			return node;
		}
	}

	public T getElement(T t) {
		// TODO
		if(t == null){
			throw new NullPointerException();
		}
		return getElementHelper(root, t);
	}
	public T getElementHelper(BSTNode<T> node, T target){
		if(node != null){
			if(node.getData().equals(target)){
				return node.getData();
			}
			else if(node.getData().compareTo(target) > 0 && node.getLeft() != null){
				return getElementHelper(node.getLeft(), target);
			}
			else if(node.getData().compareTo(target) > 0 && node.getLeft() == null && node.getRight() != null){
				return getElementHelper(node.getRight(), target);
			}
			else if(node.getData().compareTo(target) < 0 && node.getRight() != null){
				return getElementHelper(node.getRight(), target);
			}
			else if(node.getData().compareTo(target) < 0 && node.getRight() == null && node.getLeft() != null){
				return getElementHelper(node.getLeft(), target);
			}
		}
		return null;
	}

	public void addElement(T t) {
		// TODO
		if(t == null){
			throw new NullPointerException();
		}
		if(isEmpty()){
			root = new BSTNode(t, null, null);
		}
		else{
			BSTNode<T> cur = root;
			while(cur != null){
				if(t.compareTo(cur.getData()) < 0){
					if(cur.getLeft() == null){
						cur.setLeft(new BSTNode<T>(t, null, null));
						cur = null;
					}
					else{
						cur = cur.getLeft();
					}
				}
				else{
					if(cur.getRight() == null){
						cur.setRight(new BSTNode<T>(t, null, null));
						cur = null;
					}
					else{
						cur = cur.getRight();
					}
				}
			}
		}
	}

	@Override
	public T getMin() {
		// TODO
		return getMinHelper(root);
	}

	public T getMinHelper(BSTNode<T> node){
		if(isEmpty()){
			return null;
		}
		if(node.getLeft() == null){
			return node.getData();
		}
		return getMinHelper(node.getLeft());
	}

	@Override
	public T getMax() {
		// TODO
		return getMaxHelper(root);
	}

	public T getMaxHelper(BSTNode<T> node){
		if(isEmpty()){
			return null;
		}
		if(node.getRight() == null){
			return node.getData();
		}
		return getMaxHelper(node.getRight());
	}

	@Override
	public int height() {
		// TODO
		return heightHelper(root);
	}

	public int heightHelper(BSTNode<T> node){
		if(node == null){
			return -1;
		}
		int leftHeight = heightHelper(node.getLeft());
		int rightHeight = heightHelper(node.getRight());
		return 1 + Math.max(leftHeight, rightHeight);
	}

	public Iterator<T> preorderIterator() {
		// TODO
		Queue<T> queue = new LinkedList<T>();
		preorderTraverse(queue, root);
		return queue.iterator();
	}

	private void preorderTraverse(Queue<T> queue, BSTNode<T> node){
		if(node != null){
			queue.add(node.getData());
			preorderTraverse(queue, node.getLeft());
			preorderTraverse(queue, node.getRight());
		}
	}

	public Iterator<T> inorderIterator() {
		Queue<T> queue = new LinkedList<T>();
		inorderTraverse(queue, root);
		return queue.iterator();
	}

	private void inorderTraverse(Queue<T> queue, BSTNode<T> node) {
		if (node != null) {
			inorderTraverse(queue, node.getLeft());
			queue.add(node.getData());
			inorderTraverse(queue, node.getRight());
		}
	}

	public Iterator<T> postorderIterator() {
		// TODO
		Queue<T> queue = new LinkedList<T>();
		postorderTraverse(queue, root);
		return queue.iterator();
	}

	private void postorderTraverse(Queue<T> queue, BSTNode<T> node){
		if(node != null){
			postorderTraverse(queue, node.getLeft());
			postorderTraverse(queue, node.getRight());
			queue.add(node.getData());
		}
	}
	@Override
	public boolean equals(BSTInterface<T> other) {
		// TODO
		Iterator<T> thisPostorder = postorderIterator();
		Iterator<T> thisPreorder = preorderIterator();
		Iterator<T> otherPostorder = other.postorderIterator();
		Iterator<T> otherPreorder = other.preorderIterator();
		while(thisPostorder.hasNext() && otherPostorder.hasNext()){
			if(!thisPostorder.next().equals(otherPostorder.next())){
				return false;
			}
		}
		if(!thisPostorder.hasNext() && otherPostorder.hasNext()){
			return false;
		}
		if(thisPostorder.hasNext() && !otherPostorder.hasNext()){
			return false;
		}
		while(thisPreorder.hasNext() && otherPreorder.hasNext()){
			if(!thisPreorder.next().equals(otherPreorder.next())){
				return false;
			}
		}
		if(!thisPreorder.hasNext() && otherPreorder.hasNext()){
			return false;
		}
		if(thisPreorder.hasNext() && !otherPreorder.hasNext()){
			return false;
		}
		return true;
	}

	@Override
	public boolean sameValues(BSTInterface<T> other) {
		// TODO
		Queue<T> queue = new LinkedList();
		Queue<T> otherQueue = new LinkedList();
		sameValuesHelper(queue, root);
		sameValuesHelper(otherQueue, other.getRoot());
		if(queue.size() == otherQueue.size()){
			for(int i = 0; i < queue.size(); i++){
				if(queue.peek().compareTo(otherQueue.peek()) != 0){
					return false;
				}
				else{
					queue.remove();
					otherQueue.remove();
				}
			}
			return true;
		}
		else{
			return false;
		}
	}
	public void sameValuesHelper(Queue<T> queue, BSTNode<T> node){
		if(node != null){
			sameValuesHelper(queue, node.getLeft());
			queue.add(node.getData());
			sameValuesHelper(queue, node.getRight());
		}
	}
	
	@Override
	public int countRange(T min, T max) {
    	// TODO
		Queue<T> queue = new LinkedList<T>();
		countRangeHelper(min, max, queue, root);
		return queue.size();
  	}

	public void countRangeHelper(T min, T max, Queue<T> queue, BSTNode<T> node){
		if(node != null){
			countRangeHelper(min, max, queue, node.getLeft());
			countRangeHelper(min, max, queue, node.getRight());
			if(node.getData().compareTo(min) > 0 && node.getData().compareTo(max) < 0){
				queue.add(node.getData());
			}
		}
	}
	@Override
	public BSTNode<T> getRoot() {
        // DO NOT MODIFY
		return root;
	}

	public static <T extends Comparable<T>> String toDotFormat(BSTNode<T> root) {
		// header
		int count = 0;
		String dot = "digraph G { \n";
		dot += "graph [ordering=\"out\"]; \n";
		// iterative traversal
		Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
		queue.add(root);
		BSTNode<T> cursor;
		while (!queue.isEmpty()) {
			cursor = queue.remove();
			if (cursor.getLeft() != null) {
				// add edge from cursor to left child
				dot += cursor.getData().toString() + " -> "
						+ cursor.getLeft().getData().toString() + ";\n";
				queue.add(cursor.getLeft());
			} else {
				// add dummy node
				dot += "node" + count + " [shape=point];\n";
				dot += cursor.getData().toString() + " -> " + "node" + count
						+ ";\n";
				count++;
			}
			if (cursor.getRight() != null) {
				// add edge from cursor to right child
				dot += cursor.getData().toString() + " -> "
						+ cursor.getRight().getData().toString() + ";\n";
				queue.add(cursor.getRight());
			} else {
				// add dummy node
				dot += "node" + count + " [shape=point];\n";
				dot += cursor.getData().toString() + " -> " + "node" + count
						+ ";\n";
				count++;
			}

		}
		dot += "};";
		return dot;
	}

	public static void main(String[] args) {
		for (String r : new String[] { "a", "b", "c", "d", "e", "f", "g" }) {
			BSTInterface<String> tree = new BinarySearchTree<String>();
			for (String s : new String[] { "d", "b", "a", "c", "f", "e", "g" }) {
				tree.addElement(s);
			}
			Iterator<String> iterator = tree.inorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();
			iterator = tree.preorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();
			iterator = tree.postorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();

			System.out.println(tree.removeElement(r));

			iterator = tree.inorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();
		}

		BSTInterface<String> tree = new BinarySearchTree<String>();
		for (String r : new String[] { "a", "b", "c", "d", "e", "f", "g" }) {
			tree.addElement(r);
		}
		System.out.println(tree.getSize());
		System.out.println(tree.height());
		System.out.println(tree.countRange("a", "g"));
		System.out.println(tree.countRange("c", "f"));
	}
}