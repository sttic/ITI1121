public interface List<E> {
    void addFirst(E elem);
    int size();
    E get(int index);
    Iterator<E> iterator();
    Iterator<E> iterator(int nextIndex);
    // Iterator<E> iterator(Iterator<E> other);

}
