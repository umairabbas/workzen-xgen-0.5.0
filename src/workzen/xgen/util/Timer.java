/*
 * Timer.java
 * Copyright (c) 2000-2001 Brad D Matlack.  
 * License http://www.gnu.org/copyleft
 */

package workzen.xgen.util;

/**
 * Timer  
 * @author <a href="matlackb@users.sourceforge.net">Brad Matlack</a>
 */
public class Timer {
    long t;
    
    /** constructor */
    public Timer() {
        reset();
    }
    
    /** reset timer */
    public void reset() {
        t = System.currentTimeMillis();
    }
    
    /** return elapsed time */
    public long elapsed() {
        return System.currentTimeMillis() - t;
    }
    
    /** print explanatory string and elapsed time */
    public void print(String s) {
        System.out.println(s + ": " + elapsed());
    }
}

