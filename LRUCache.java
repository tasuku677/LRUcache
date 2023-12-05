import java.util.HashMap;
class Node<E>{
    public Node<E> preNode, nextNode;
    public int key;
    public E data;
    public Node(int key, E data){
        this.key = key;
        this.data = data;
    }
}
class LRUCache<E>{
    public Node<E> head;
    public Node<E> tail;
    public int capacity;
    HashMap<Integer, Node<E>> hashmap;
    public LRUCache(int capacity, int key, E data){
        this.capacity = capacity;
        this.hashmap = new HashMap<Integer, Node<E>>(capacity);
        // System.out.println(capacity);
        // this.head.preNode = null;
        this.head = new Node<E>(key, data);
        this.tail = new Node<E>(key, data);
        this.head.nextNode = this.tail;
        this.tail.preNode = this.head;
        // this.tail.nextNode = null;
    }
    private void insert(int key, Node<E> newNode){
        Node<E> temp = this.head.nextNode;
        this.head.nextNode = newNode;
        newNode.preNode = this.head;
        newNode.nextNode = temp;
        temp.preNode = newNode;
    }
    private void delete(Node<E> deletedNode){
        if(deletedNode.preNode != null) deletedNode.preNode.nextNode = deletedNode.nextNode;
        if(deletedNode.nextNode != null) deletedNode.nextNode.preNode = deletedNode.preNode;
    }
    public E lruget(int key){
        if(this.hashmap.containsKey(key)){
            Node<E> getNode = this.hashmap.get(key);
            delete(getNode);
            insert(key, getNode);
            return getNode.data;
        }
        else{
            return null;
        }
    }
    public void lruput(int key, E data){
        Node<E> newNode = new Node<E>(key, data);
        // if(this.hashmap.containsKey(key)){
        //     //既に存在するキーならば、データを更新するために一度削除
        //     delete(this.hashmap.get(key));
        //     this.hashmap.remove(key);
        // }
        if(this.hashmap.size() < this.capacity){
            //容量以内なら新しいNodeの情報を双方向リストとハッシュマップを追加
            this.hashmap.put(key, newNode);
            insert(key, newNode);
        }
        else{
            //最後尾のNode(最も古い)の情報を双方向リストとハッシュマップから削除
            Node<E> deletedNode = this.tail.preNode;
            delete(deletedNode);
            this.hashmap.remove(deletedNode.key);
            //次に、新しいNodeの情報を双方向リストとハッシュマップに追加
            insert(key, newNode);
            this.hashmap.put(key, newNode);
        }
    }
    // public String toString(){
    //     Node currentNode = this.head;
    //     return "iii";
    // }
}
class Main{
    public static void main(String[] args){
        LRUCache<Integer> cashes = new LRUCache<Integer>(6, 0, 0);
        cashes.lruput(1,5);
        cashes.lruput(2,3);
        cashes.lruput(3,4);
        cashes.lruput(4,8);
        cashes.lruput(5,8);
        cashes.lruput(6,8);
        cashes.lruput(7,8);
        cashes.lruget(2);
        // System.out.println(cashes.hashmap.size());
        Node<Integer> currentNode = cashes.head;
        for(int i = 0; i < cashes.hashmap.size(); i++){
            currentNode = currentNode.nextNode;
            System.out.println(currentNode.data);
        }

        LRUCache<String> cashesStr= new LRUCache<String>(6, 0, "hoge");
        cashesStr.lruput(1, "a");
        cashesStr.lruput(2, "b");
        Node<String> currentNodeStr = cashesStr.head;
        for(int i = 0; i < cashesStr.hashmap.size(); i++){
            currentNodeStr = currentNodeStr.nextNode;
            System.out.println(currentNodeStr.data);
        }
    }
}