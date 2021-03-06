# 파이썬 코딩 인터뷰
파이썬 코딩 인터뷰 책 정리  


- [3.선형 자료구조](#3-선형-자료구조)  
  - [3.1 배열](#31-배열)
    - [두 수의 합](#두-수의-합)
  - [3.2 연결 리스트](#32-연결-리스트)  
    - [두 수의 합](#두-수의-합)
  - [3.3 스택,큐](#33-스택-큐)  
    - [유효한 괄호](#유효한-괄호)
    - [큐를 이용한 스택 구현](#큐를-이용한-스택-구현)
  - [3.5 해시 테이블](#35-해시-테이블)      
    - [해시맵 디자인](#해시맵-디자인)
- [4.비선형 자료구조](#4-비선형-자료구조)      
  - [4.1 그래프](#41-그래프)   
    - [섬의 개수](#섬의-개수)    
  - [4.3 트리](#43-트리)     
    - [이진 트리 반전](#이진-트리-반전)      
    - [정렬된 배열의 이진 탐색 트리 변환](#정렬된-배열의-이진-탐색-트리-변환)    
- [5.알고리즘](#5-알고리즘)      
  - [5.3 비트조작](#53-비트조작)
    - [싱글넘버](#싱글넘버)
  - [5.4 슬라이딩 윈도우](#54-슬라이딩-윈도우)    
    - [최대 슬라이딩 윈도우](#최대-슬라이딩-윈도우)
  - [5.5 그리디 알고리즘](#55-그리디-알고리즘)
    - [주식으로 살고팔기 가장 좋은 시점2](#주식으로-살고팔기-가장-좋은-시점2)     
  - [5.7 다이나믹 프로그래밍](#57-다이나믹-프로그래밍)  
    - [최대 서브 배열](#최대-서브-배열)       

---  

# 3. 선형 자료구조

## 3.1 배열  

### 두 수의 합  

https://leetcode.com/problems/two-sum/  

**Problem**  

덧셈하여 타겟을 만들 수 있는 배열의 두 숫자 인덱스를 리턴

- 입력  

```
nums = [2,7,11,15], target = 9
```  

- 출력  

```
[0,1]
```  

**Solution**  

1. 브루트 포스로 계산

```java
// brute force
public int[] twoSum_bruteForce(int[] nums, int target) {
    for (int i = 0; i < nums.length - 1; i++) {
        for (int j = i + 1; j < nums.length; j++) {
            if (nums[i] + nums[j] == target) {
                return new int[] { i, j };
            }
        }
    }
    throw new RuntimeException("unreachable code");
}
```  

2. map 이용  
맵의 키를 `value`, 값을 배열의 `index`로 설정  

```java
public int[] twoSum_map(int[] nums, int target) {
  Map<Integer, Integer> map = new HashMap<>();

  for(int i=0; i<nums.length; i++) {
      Integer idx = map.get(target - nums[i]);
      if (idx != null) {
          return new int[] { i, idx };
      }
      map.put(nums[i], i);
  }

  throw new RuntimeException("unreachable code");
}
```  

3. two point 이용
문제는 `index`를 반환해야 하므로 별도로 정렬 후 별도의 `index`와 `value`를 유지한다.  

```java
public int[] twoSum_twoPoint(int[] nums, int target) {
    Pair[] pairs = new Pair[nums.length];
    for (int i = 0; i < nums.length; i++) {
        pairs[i] = new Pair(i, nums[i]);
    }
    Arrays.sort(pairs, Comparator.comparingInt(p -> p.value));
    int left = 0;
    int right = nums.length - 1;
    while (left <= right) {
        int sum = pairs[left].value + pairs[right].value;
        if (sum == target) {
            return new int[] { pairs[left].idx, pairs[right].idx };
        } else if (sum < target) {
            left += 1;
        } else {
            right -= 1;
        }
    }
    throw new RuntimeException("unreachable code");
}

public static class Pair {
    int idx;
    int value;

    public Pair(int l, int r) {
        this.idx = l;
        this.value = r;
    }
}
```


---  

## 3.2 연결 리스트  

### 팰린드롬 연결 리스트

https://leetcode.com/problems/two-sum/  

**Problem**  

연결 리스트가 팰린드롬 구조인지 판별  

> e.g1

- 입력  

```
1->2
```  

- 출력  

```
false
```  

> e.g2

- 입력  

```
1->2->2->1
```  

- 출력  

```
true
```  


**Solution**   

1. Deque를 이용하여 풀이  

```java
public boolean isPalindrome_Deque(ListNode head) {
    if (head == null) {
        return true;
    }
    Deque<Integer> que = new LinkedList<>();
    ListNode cur = head;
    while (cur != null) {
        que.addLast(cur.val);
        cur = cur.next;
    }

    while (que.size() > 1) {
        if (!que.pollFirst().equals(que.pollLast())) {
            return false;
        }
    }
    return true;
}
```  

2. Runner를 이용  
- slow(1칸), fast(2칸)을 이동하는 포인터 및 reverse list 정의
- 홀수 일 경우 slow를 한칸 더 이동(중앙값은 제외되므로)  
- reverse list와 중앙에 위치한 slow가 가리키는 리스트를 비교  

```java
public boolean isPalindrome_Runner(ListNode head) {
    if (head == null) {
        return true;
    }

    ListNode fast = head, slow = head, rev = null;
    while (fast != null && fast.next != null) {
        fast = fast.next.next;
        ListNode temp = slow.next;
        slow.next = rev;
        rev = slow;
        slow = temp;
    }
    if (fast != null) {
        slow = slow.next;
    }

    while (rev != null) {
        if (rev.val != slow.val) {
            return false;
        }
        rev = rev.next;
        slow = slow.next;
    }
    return true;
}
```

---  

## 3.3 스택 큐


### 유효한 괄호  

https://leetcode.com/problems/valid-parentheses/  


**Problem**  

괄호로 된 입력값이 올바른지 판별  

> e.g1  

```
Input: s = "()"
Output: true
```

> e.g2  

```
Input: s = "()[]{}"
Output: true
```  

> e.g3)

```
Input: s = "(]"
Output: false
```

**Solution**  

1. Stack을 이용하여 Open('(', '{','[')일 경우 Push, Close(')', '}', ']')일 경우 Stack에서 Pop후 매칭 여부 검사

```java
public boolean isValid(String s) {
    Stack<Character> stack = new Stack<>();
    char[] chars = s.toCharArray();

    for (int i = 0; i < chars.length; i++) {
        Character ch = chars[i];
        Character open = getOpen(ch);
        if (open == null) { // open
            stack.push(ch);
        } else { // close
            if (stack.isEmpty()) {
                return false;
            }
            if (!stack.pop().equals(open)) {
                return false;
            }
        }
    }
    return stack.isEmpty();
}

public Character getOpen(char ch) {
    switch (ch) {
        case ')':
            return '(';
        case '}':
            return '{';
        case ']':
            return '[';
    }
    return null;
}
```

---  

### 큐를 이용한 스택 구현

https://leetcode.com/problems/implement-stack-using-queues/  

**Problem**  

큐를 이용하여 다음 연산을 지원하는 스택을 구현  

- `push(x)`: 요소 x를 스택에 삽입
- `pop()`: 스택의 첫 번째 요소를 삭제
- `peek()`: 스택의 첫번째 요소 반환
- `empty()`: 스택이 비어 있는지 여부를 리턴  

(큐는 `push`, `top`, `pop`, `empty`만 이용)

```  
MyStack myStack = new MyStack();
myStack.push(1);
myStack.push(2);
myStack.top(); // return 2
myStack.pop(); // return 2
myStack.empty(); // return False
```

**Solution**   

1. push() 할 때 큐를 이용해 재정렬  

```java
public class MyStack2 {
    Queue<Integer> que;

    public MyStack2() {
        que = new LinkedList<>();
    }

    public void push(int x) {
        que.offer(x);
        for (int i = 0; i < que.size() - 1; i++) {
            que.offer(que.poll());
        }
    }

    public int pop() {
        return que.poll();
    }

    public int top() {
        return que.peek();
    }

    public boolean empty() {
        return que.size() == 0;
    }
}
```

2. push() 할 때 요소를 head로 변경  

```java
public class MyStack {
    Queue<Integer> que;

    public MyStack() {
        que = new LinkedList<>();
    }

    public void push(int x) {
        Queue<Integer> temp = new LinkedList<>();
        temp.add(x);
        while (!que.isEmpty()) {
            temp.add(que.poll());
        }
        que = temp;
    }

    public int pop() {
        return que.poll();
    }

    public int top() {
        return que.peek();
    }

    public boolean empty() {
        return que.size() == 0;
    }
}
```  

---  

### 해시맵-디자인

https://leetcode.com/problems/design-hashmap/


**Problem**  

다음의 기능을 제공하는 해시맵을 디자인하여라  

- put(key, value): 키 값을 해시맵에 삽입, 만약 이미 존재하면 업데이트
- get(key): 키에 해당하는 값을 조회, 존재하지 않으면 -1 리턴
- remove(key): 키에 해당하는 키,값을 해시맵에서 삭제

> e.g1  

```
MyHashMap hashMap = new MyHashMap();
hashMap.put(1, 1);          
hashMap.put(2, 2);         
hashMap.get(1);            // returns 1
hashMap.get(3);            // returns -1 (not found)
hashMap.put(2, 1);          // update the existing value
hashMap.get(2);            // returns 1
hashMap.remove(2);          // remove the mapping for 2
hashMap.get(2);            // returns -1 (not found)
```

**Solution**  

1. Separate chaining

```java
class MyHashMap {

    private List<Node>[] nodes;

    /** Initialize your data structure here. */
    public MyHashMap() {
        nodes = new List[1000000 / 10];
    }

    /** value will always be non-negative. */
    public void put(int key, int value) {
        int hashValue = hash(key);

        List<Node> nodeList = nodes[hashValue];

        if (nodeList != null) {
            for (Node node : nodeList) {
                if (node.key == key) {
                    node.value = value;
                    return;
                }
            }
        } else {
            nodeList = new LinkedList<>();
            nodes[hashValue] = nodeList;
        }
        nodeList.add(new Node(key, value));
    }

    /** Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key */
    public int get(int key) {
        int hashValue = hash(key);

        List<Node> nodeList = nodes[hashValue];
        if (nodeList == null) {
            return -1;
        }

        for (Node node : nodeList) {
            if (node.key == key) {
                return node.value;
            }
        }
        return -1;
    }

    /** Removes the mapping of the specified value key if this map contains a mapping for the key */
    public void remove(int key) {
        int hashValue = hash(key);

        List<Node> nodeList = nodes[hashValue];
        if (nodeList == null) {
            return;
        }
        Iterator<Node> itr = nodeList.iterator();
        while (itr.hasNext()) {
            Node node = itr.next();
            if (node.key == key) {
                itr.remove();
                return;
            }
        }
    }

    private int hash(int key) {
        return key % nodes.length;
    }

    static class Node {
        int key;
        int value;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
}
```


---   

# 4. 비선형 자료구조  

## 4.1 그래프  

### 섬의 개수

https://leetcode.com/problems/number-of-islands/


**Problem**  

1을 육지로, 0을 물로 가정한 2D 그리드 맵이 주어졌을때, 섬의 개수를 계산하여라

> e.g1  

```
Input: grid = [
  ["1","1","1","1","0"],
  ["1","1","0","1","0"],
  ["1","1","0","0","0"],
  ["0","0","0","0","0"]
]
Output: 1
```

> e.g2  

```
Input: grid = [
  ["1","1","0","0","0"],
  ["1","1","0","0","0"],
  ["0","0","1","0","0"],
  ["0","0","0","1","1"]
]
Output: 3
```  

**Solution**  

1. DFS

```java
class Solution {
    static int[] dy = { -1, 0, 1, 0 };
    static int[] dx = { 0, 1, 0, -1 };

    public int numIslands(char[][] grid) {
        int answer = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '1') {
                    answer++;
                    dfs(grid, i, j);
                }
            }
        }

        return answer;
    }

    public void dfs(char[][] grid, int y, int x) {
        grid[y][x] = '0';        
        for (int k = 0; k < 4; k++) {
            int newY = y + dy[k];
            int newX = x + dx[k];
            if (newY >= 0 && newY < grid.length && newX >= 0 && newX < grid[newY].length
                && grid[newY][newX] == '1') {
                dfs(grid, newY, newX);
            }
        }
    }
}
```


---  

## 4.3 트리  

### 이진 트리의 최대 깊이

https://leetcode.com/problems


**Problem**  

이진 트리의 최대 깊이를 구하라

> e.g1  

```
Input: [3,9,20,null,null,15,7]
   3
  / \
 9  20
 /  \
15   7

Output: 3
```

**Solution**  

1. Recursive

```java
public int solveByRecursive(TreeNode root) {
    if (root == null) {return 0;}
    return maxDepth(root, 1);
}

public int maxDepth(TreeNode node, int current) {
    if (node.left == null && node.right == null) { return current; }
    int answer = current;
    if (node.left != null) {
        answer = maxDepth(node.left, current + 1);
    }
    if (node.right != null) {
        answer = Math.max(answer, maxDepth(node.right, current + 1));
    }
    return answer;
}
```

2. BFS  

```java
public int solveByBFS(TreeNode root) {
    if (root == null) {
        return 0;
    }

    Queue<TreeNode> que = new LinkedList<>();
    que.offer(root);
    int depth = 0;

    while (!que.isEmpty()) {
        depth++;
        int repeat = que.size();
        for (int i = 0; i < repeat; i++) {
            TreeNode node = que.poll();
            if (node.left != null) {
                que.offer(node.left);
            }
            if (node.right != null) {
                que.offer(node.right);
            }
        }
    }
    return depth;
}
```


---  

### 이진 트리 반전

https://leetcode.com/problems/invert-binary-tree/


**Problem**  

주어진 이진 트리를 반전

> e.g1  

```
Input:
      4
    /   \
   2     7
  / \   / \
1   3 6   9

Output:
     4
   /   \
  7     2
 / \   / \
9   6 3   1
```

**Solution**  

1. DFS

```java
public TreeNode solveByDFS(TreeNode root) {
    if (root == null) {
        return null;
    }

    Stack<TreeNode> stack = new Stack<>();
    stack.push(root);

    while (!stack.isEmpty()) {
        TreeNode node = stack.pop();
        if (node != null) {
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;
            stack.push(node.left);
            stack.push(node.right);
        }
    }
    return root;
}
```

2. BFS  

```java
public TreeNode solveByBFS(TreeNode root) {
    if (root == null) {
        return null;
    }
    Deque<TreeNode> deque = new LinkedList<>();
    deque.addLast(root);

    while (!deque.isEmpty()) {
        TreeNode node = deque.pollLast();
        if (node != null) {
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;

            deque.addLast(node.left);
            deque.addLast(node.right);
        }
    }
    return root;
}
```

---  


### 정렬된 배열의 이진 탐색 트리 변환

https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/


**Problem**  

오름차순으로 정렬된 배열을 높이 균형(Height Balanced) 이진 탐색 트리로 변환하여라  

> e.g1  

```
Given the sorted array: [-10,-3,0,5,9],

One possible answer is: [0,-3,9,-10,null,5], which represents the following height balanced BST:

      0
     / \
   -3   9
   /   /
 -10  5
```

**Solution**  

1. 주어진 `int[] nums`의 중앙값을 구하여 루트 노드로 만든 뒤, Left, Right Subtree를 재귀

```java
public class Solution {

    public TreeNode sortedArrayToBST(int[] nums) {
        return solution1(nums);
    }

    public TreeNode solution1(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        int mid = nums.length >> 1;
        TreeNode root = new TreeNode(nums[mid]);

        // left
        if (mid != 0) {
            int[] leftNums = new int[mid];
            System.arraycopy(nums, 0, leftNums, 0, mid);
            root.left = solution1(leftNums);
        }

        // right
        if (mid != nums.length - 1) {
            int[] rightNums = new int[nums.length - mid - 1];
            System.arraycopy(nums, mid + 1, rightNums, 0, nums.length - mid - 1);
            root.right = solution1(rightNums);
        }

        return root;
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {}

        TreeNode(int val) { this.val = val; }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
```

---  

# 5. 알고리즘  

## 5.3 비트조작  

### 싱글넘버

https://leetcode.com/problems/single-number/


**Problem**  

딱 하나를 제외하고 모든 엘리먼트는 2개씩 있다. 1개인 엘리먼트를 찾기

> examples

```
Input: nums = [2,2,1]
Output: 1
```

> e.g2  

```
Input: nums = [4,1,2,1,2]
Output: 4
```  

> e.g3)

```
Input: nums = [1]
Output: 1
```

**Solution**  

1. 해시맵  
(문제 제약에 추가 메모리 사용하지 않는 제약이 있음)  

```java
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Solution {    
    // Runtime: 10 ms, faster than 19.05% of Java online submissions for Single Number.
    // Memory Usage: 39.9 MB, less than 23.86% of Java online submissions for Single Number.
    public int solveByMap(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            Integer val = map.get(num);
            if (val == null) {
                val = 0;
            }
            val++;
            map.put(num, val);
        }

        for (Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                return entry.getKey();
            }
        }
        throw new RuntimeException("unreachable code");
    }
}

```  

2. XOR  

```Java
// Runtime: 1 ms, faster than 88.71% of Java online submissions for Single Number.
// Memory Usage: 39 MB, less than 79.99% of Java online submissions for Single Number.
public int solveByXOR(int[] nums) {
    int result = 0;
    for (int num : nums) {
        result ^= num;
    }
    return result;
}
```

---  

## 5.4 슬라이딩 윈도우

### 최대 슬라이딩 윈도우

https://leetcode.com/problems/sliding-window-maximum/


**Problem**  

배열 nums가 주어졌을 때 k 크기의 슬라이딩 윈도우를 오른쪽 끝까지 이동하면서 최대 슬라이딩  
윈도우를 구하기

> e.g1  

```
Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
Output: [3,3,5,5,6,7]
Explanation:
Window position                Max
---------------               -----
[1  3  -1] -3  5  3  6  7       3
 1 [3  -1  -3] 5  3  6  7       3
 1  3 [-1  -3  5] 3  6  7       5
 1  3  -1 [-3  5  3] 6  7       5
 1  3  -1  -3 [5  3  6] 7       6
 1  3  -1  -3  5 [3  6  7]      7
```

> e.g2  

```
Input: nums = [1], k = 1
Output: [1]
```  

> e.g3)

```
Input: nums = [9,11], k = 2
Output: [11]
```

**Solution**  

1. 브루트 포스로 계산

```Java
// 	Time Limit Exceeded
public int[] solveByBruteForce(int[] nums, int k) {
    int[] answer = new int[nums.length - k + 1];
    int idx = 0;
    for (int i = 0; i <= nums.length - k; i++) {
        int max = nums[i];
        for (int j = i + 1; j <= i + k - 1; j++) {
            if (max < nums[j]) {
                max = nums[j];
            }
        }
        answer[idx++] = max;
    }
    return answer;
}
```

2. SortedMap을 이용  

```Java
// Runtime: 309 ms, faster than 10.00% of Java online submissions for Sliding Window Maximum.
// Memory Usage: 56 MB, less than 25.50% of Java online submissions for Sliding Window Maximum.
public int[] solveBySortedMap(int[] nums, int k) {
    int[] answer = new int[nums.length - k + 1];
    int idx = 0;
    NavigableMap<Integer, Integer> map = new TreeMap<>();
    for (int i = 0; i < k; i++) {
        Integer val = map.get(nums[i]);
        if (val == null) {
            val = 0;
        }
        map.put(nums[i], val + 1);
    }
    answer[idx++] = map.lastKey();

    for (int i = 1; i <= nums.length - k; i++) {
        // remove left
        int leftVal = map.get(nums[i - 1]) - 1;
        if (leftVal == 0) {
            map.remove(nums[i - 1]);
        } else {
            map.put(nums[i - 1], leftVal);
        }

        // add right
        Integer rightVal = map.get(nums[i + k - 1]);
        if (rightVal == null) {
            rightVal = 0;
        }
        map.put(nums[i + k - 1], rightVal + 1);
        answer[idx++] = map.lastKey();
    }
    return answer;
}
```

---  

## 5.5 그리디 알고리즘

### 주식으로 살고팔기 가장 좋은 시점2

https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/


**Problem**  

여러 번의 거래로 낼 수 있는 최대 이익을 산출하라

> e.g1  

```
Input: [7,1,5,3,6,4]
Output: 7
Explanation: Buy on day 2 (price = 1) and sell on day 3 (price = 5), profit = 5-1 = 4.
             Then buy on day 4 (price = 3) and sell on day 5 (price = 6), profit = 6-3 = 3.
```

> e.g2  

```
Input: [1,2,3,4,5]
Output: 4
Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
             Note that you cannot buy on day 1, buy on day 2 and sell them later, as you are
             engaging multiple transactions at the same time. You must sell before buying again.
```  

> e.g3)

```
Input: [7,6,4,3,1]
Output: 0
Explanation: In this case, no transaction is done, i.e. max profit = 0.
```

**Solution**  

1. 그리디 알고리즘
(전날과 비교하여 가격이 올랐으면 전날 사고, 당일 팔고)  

```Java
public int maxProfit(int[] prices) {
    if (prices.length <= 1) {
        return 0;
    }
    int answer = 0;
    for (int i = 1; i < prices.length; i++) {
        if (prices[i] - prices[i - 1] > 0) {
            answer += prices[i] - prices[i - 1];
        }
    }
    return answer;
}
```

---  



## 5.7 다이나믹 프로그래밍

### 최대 서브 배열

https://leetcode.com/problems/maximum-subarray/


**Problem**  

합이 최대가 되는 연속 서브 배열을 찾아 합을 리턴하라.

> e.g1  

```
Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
Output: 6
Explanation: [4,-1,2,1] has the largest sum = 6.
```

> e.g2  

```
Input: nums = [1]
Output: 1
```  

**Solution**  

1. DP1  

DP[i]: i를 포함하면서 a[0]~a[i]까지의 최대 서브 배열  
=> DP[i] = max(DP[i-1] + a[i], a[i])

```Java
public int maxSubArray(int[] nums) {
    int prevSum = nums[0];
    int max = prevSum;
    for (int i = 1; i < nums.length; i++) {
        prevSum = Math.max(prevSum + nums[i], nums[i]);
        if (max < prevSum) {
            max = prevSum;
        }
    }
    return max;
}
```

---  

<br /><br /><br /><br />

### 정리 탬플릿

https://leetcode.com/problems


**Problem**  

문제 설명

> e.g1  

```
Input: s = "()"
Output: true
```

> e.g2  

```
Input: s = "()[]{}"
Output: true
```  

> e.g3)

```
Input: s = "(]"
Output: false
```

**Solution**  
