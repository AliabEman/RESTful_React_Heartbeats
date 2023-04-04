/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cst8218.carr0676.heartbeat.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;

/**
 *
 * @author Dylan
 */



@Entity
public class Heart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    
    @NotNull
    @Min(1)
    @Max(SIZE_MAX)
    private int size;
    
    @NotNull
    @Min(1) // 1 to contract max
    @Max(CONTRACTED_MAX)
    private int contractedSize;
    
    @NotNull
    @Min(0)
    @Max(BEATS_TO_EXHAUSTION)
    private int beatCount;
    
    public static final int X_MAX = 800;
    public static final int Y_MAX = 600;
    public static final int SIZE_MAX = 100;
    public static final int CONTRACTED_MAX = 50;
    public static final int INITIAL_SIZE = 40;
    public static final int STOP_SIZE = 10;
    public static final int BEATS_TO_EXHAUSTION = 2;
    public static final int BEAT_INCREMENT = 5;
    public static final int SHRINK_DECREMENT = 5;
    public static final int CONTRACTION_DECREMENT = 1;

    private static final long serialVersionUID = 1L;

    public Heart() {
        this.beatCount = 0;
        this.contractedSize = INITIAL_SIZE;
        this.size = INITIAL_SIZE;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
     
        if (!(object instanceof Heart)) {
            return false;
        }
        Heart other = (Heart) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cst8218.carr0676.heartbeat.entity.Heart[ id=" + id + " ]";
    }
    /** 
     * Updates the properties to simulate the passing of one unit of time.
     */
    public void advanceOneTimeIncrement() {
        if (stillBeating()){                    //if still beating
            if (finishedCurrentBeat()){            //if size has decreased to contracted size
                newBeat();                             //suddenly increase size to begin new beat
                setBeatCount(getBeatCount() + 1);      //increment beat count
                if (exhausted()){                      //if beat count has reached exhausted level
                    shrink();                               //descrease contracted size
                    setBeatCount(0);                        //now not exhausted - reset beat count
                }
            } else {                               //else 
                continueContracting();                 //continue the contracting phase of a beat
            }
        }
    }
    /* 
     *  Returns true if the Heart has not yet stopped and is still beating
     */

    /**
     *
     * @return t/f for if it still beats
     */

    public boolean stillBeating(){
        return getContractedSize() > STOP_SIZE;
    }
    /**
 * Returns true if the current beat is at contracted size or smaller
 */
public boolean finishedCurrentBeat() {
    return getSize() <= getContractedSize();
}
/* 
 *   Sets the beat count to whatever was passed to the method
 */
    public void setBeatCount(int beatCount) {
    this.beatCount = beatCount;
}
/**
 * Increases the size of the heart to begin a new beat. sets the contracted size to half the normal size
 */
public void newBeat() {
    setSize(getSize() + BEAT_INCREMENT);
    setContractedSize(getSize() / 2);
}
/*
 * Returns current beat count
 */
public int getBeatCount() {
    return beatCount;
}
/*
 * gets the hearts contracted size
 */
public int getContractedSize() {
    return contractedSize;
}
/**
 * Get the size of the Heart.
 * @return the size of the Heart
 */
public int getSize() {
    return this.size;
}
/*
*if the contracted size is smaller than the size when it should stop and less than max set it
*/
public void setContractedSize(int contractedSize) {
    if (contractedSize >= STOP_SIZE && contractedSize <= SIZE_MAX) {
        this.contractedSize = contractedSize;
    }
}
/*
*sets the size to the passed value after validation
*/
public void setSize(int size) {
    if (size >= 0 && size <= SIZE_MAX) {
        this.size = size;
    } else if (size < 0) {
        this.size = 0;
    } else {
        this.size = SIZE_MAX;
    }
}
/*
 * takes the size after contraction and sets the size to that
 */
public void continueContracting() {
    int tSize = getSize() - CONTRACTION_DECREMENT;
    if (tSize < getContractedSize()) {
        tSize = getContractedSize();
    }
    setSize(tSize);
}
/*
*checks if the beat count has reached exhaustion
*/
public boolean exhausted() {
    return getBeatCount() == BEATS_TO_EXHAUSTION;
}

/*
* Shrink the contracted size of the heart when called by the given decrement
*/
public void shrink() {
    contractedSize -= SHRINK_DECREMENT;
}

}
