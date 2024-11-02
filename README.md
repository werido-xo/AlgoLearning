# Contents of This Repo

*Date*: 2024-11-02
*Authors*: weirdo-xo
*Desription*: 本仓库主要是记录算法学习过程中有关的算法，可以作为一个参考。

## 1. Binary Tree Algorithm
本部分介绍二叉树的相关算法：
+ 非递归的先序遍历
+ 非递归的中序遍历
+ 非递归的后续遍历
+ 非递归的层次遍历

### 1.1 inorder, preoder and postorder traversal

​	非递归的先序遍历、中序遍历和后序列遍历都是基于二叉树的左树分解的。至于二叉树左树分解的细节请参考其他资料。本节介绍二叉树三种遍历方式的思路和实现。下面这个函数的功能是将一颗树的左边界推入栈中，并根据参数`ptflag` 来判断是否需要输出：

```java
	public static void push_left(LinkedList<Node> stack, Node node, int ptflag) {
		while (node != null) {
			if (ptflag == 1) 
				System.out.printf("%c", node.val);
			stack.addFirst(node);
			node = node.left;
		}
	}
```



**A. Preorder Traversal**

先序遍历实现步骤：

+ 将输入的根节点的左边界逐个输出并入栈，直到叶子节点没有左孩子。（对应遍历序中：第一次遇见就输出）
+ 取出top节点（包括移出栈）。如果该节点存在右孩子节点，则将该右孩子作为头节点输入第一步；否则，继续执行这一步的操作。

```java
	public static void preorder_traversal(Node head) {
		if (head == null)
			return;
		
		Node cur = head;
		LinkedList<Node> stack = new LinkedList<Node>();
		
		push_left(stack, head, 1);
		while (stack.size() != 0) {
			cur = stack.pollFirst();
			if (cur.right == null)
				continue;
			
			push_left(stack, cur.right, 1);
		}		
	}
```



**B. Inorder Traversal**

中序遍历的设计思路：

+ 将输入节点的左边界逐个入栈，直到叶子节点没有左孩子。（对应第一次遇见）
+ 将栈中的top元素取出并输出，然后该元素是否存在右孩子（对应第二次遇见）。如果存在，则将该元素的右孩子作为根节点跳转至第一步；否则，继续执行第二步。

```c
	/* inorder_traversal(head)
	 * @head: the root node of the two-branch tree
	 * 
	 * @funcs: non-recursively traversal a binary tree. Stack base operations as 
	 *  follows:
	 *   1. print all left nodes and push to the stack
	 *   2. pop a data from stack, and do same thing for the data.right
	 */
	public static void inorder_traversal(Node head) {
		if (head == null)
			return;
		
		Node cur = head;
		LinkedList<Node> stack = new LinkedList<Node>();
		
		push_left(stack, head, 0);
		while (stack.size() != 0) {
			cur = stack.pollFirst();
			System.out.printf("%c", cur.val);
			
			if (cur.right == null)
				continue;
			
			push_left(stack, cur.right, 0);
		}
	}
```



**C. Postorder Traversal**

后续遍历的设计思路：输入为根节点

+ 将根节点的左边界逐个元素的入栈（对应第一次遇见）
+ 取出top元素（不出栈），并判断该节点的右孩子是否存在（对应第二次遇见）；如果存在右孩子【需要判断是否已经访问过】，那么将该右孩子作为根节点跳转到第一步；否则，输出该节点并出栈（第三次遇见），然后继续执行第二步。

```java
	public static void postorder_traversal(Node head) {
		if (head == null)
			return;
		
		Node cur = head, prev = null;
		LinkedList<Node> stack = new LinkedList<Node>();
		
		push_left(stack, cur, 0);
		while (stack.size() != 0) {
			cur = stack.getFirst();
			
			if (cur.right != null && cur.right != prev)
				push_left(stack, cur.right, 0);
			
			cur = stack.pollFirst();
			System.out.printf("%c", cur.val);
			prev = cur;
		}
	}
```

上面的代码中，使用`prev`来记录上一次输出的节点；这样就可以判断当前节点的右孩子是否已经访问过。究其原因，就是因为右孩子会先于当前的节点被访问/输出。



**D. Level Traversal**

层次遍历的思路比较好理解，如下：

+ 将二叉树的头节点入栈。
+ 出栈top元素并输出，然后将top元素的左右孩子入栈；重复第二步的操作，知道栈中的元素为空。

```java
	public static void level_traversal(Node head) {
		if (head == null)
			return;
		
		Node cur;
		LinkedList<Node> fifo = new LinkedList<Node>();
		
		fifo.addFirst(head);
		while (fifo.size() != 0) {
			cur = fifo.pollLast();
			System.out.printf("%c", cur.val);
			
			if (cur.left != null)
				fifo.addFirst(cur.left);
			if (cur.right != null)
				fifo.addFirst(cur.right);
		}
	}
```

