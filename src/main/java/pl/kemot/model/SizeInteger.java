package pl.kemot.model;

public class SizeInteger implements Comparable<SizeInteger> {

    private int size;

    public SizeInteger(int size) {
        this.size = size;
    }

    //in table view there is a toString() method used to display data, so if we want to add suffix to our message size we need to override toString method
    @Override
    public String toString() {
        if(size <= 0){
            return "0";
        } else if(size < 1024){
            return size + "B";
        } else if(size < 1048576){
            return size / 1024 + "kB";
        } else {
            return size / 1048576 + "MB";
        }
    }

    // when two values are compare there is compareTo method invoked, of corse you have to implement Comparable interface first
    @Override
    public int compareTo(SizeInteger otherSizeInteger) {
        if(size > otherSizeInteger.size){
            return 1;
        } else if(size < otherSizeInteger.size){
            return -1;
        } else {
            return 0;
        }
    }
}
