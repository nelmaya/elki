package de.lmu.ifi.dbs.data;

import de.lmu.ifi.dbs.linearalgebra.Matrix;

import java.util.BitSet;

/**
 * Provides a BitVector wrapping a BitSet.
 * 
 * @author Arthur Zimek (<a href="mailto:zimek@dbs.ifi.lmu.de">zimek@dbs.ifi.lmu.de</a>)
 */
public class BitVector extends RealVector<Bit>
{
    /**
     * Storing the bits.
     */
    private BitSet bits;
    
    /**
     * Dimensionality of this bit vector.
     */
    private int dimensionality;
    
    /**
     * Provides a new BitVector corresponding to the specified bits and
     * of the specified dimensionality.
     * 
     * @param bits
     * @param dimensionality
     */
    public BitVector(BitSet bits, int dimensionality)
    {
        if(dimensionality < bits.length())
        {
            throw new IllegalArgumentException("Specified dimensionality "+dimensionality+" is to low for specified BitSet of length "+bits.length());
        }
        this.bits = bits;
        this.dimensionality = dimensionality;
    }
    
    /**
     * Provides a new BitVector corresponding to the bits in the given array.
     * 
     * @param bits an array of bits specifiying the bits in this bit vector
     */
    public BitVector(Bit[] bits)
    {
        this.bits = new BitSet(bits.length);
        for(int i = 0; i < bits.length; i++)
        {
            this.bits.set(i, bits[i].bitValue());
        }
        this.dimensionality = bits.length;
    }

    /**
     * 
     * @see de.lmu.ifi.dbs.data.FeatureVector#getDimensionality()
     */
    public int getDimensionality()
    {
        return dimensionality;
    }

    /**
     * 
     * @see de.lmu.ifi.dbs.data.FeatureVector#getValue(int)
     */
    public Bit getValue(int dimension)
    {
        if(dimension < 1 || dimension > dimensionality)
        {
            throw new IllegalArgumentException("illegal dimension: "+dimension);
        }
        return new Bit(bits.get(dimension));
    }

    /**
     * 
     * @see de.lmu.ifi.dbs.data.FeatureVector#getValues()
     */
    public Bit[] getValues()
    {
        Bit[] bitArray = new Bit[dimensionality];
        for(int i = 0; i < dimensionality; i++)
        {
            bitArray[i] = new Bit(bits.get(i));
        }
        return bitArray;
    }

    /**
     * 
     * @see de.lmu.ifi.dbs.data.FeatureVector#getVector()
     */
    public Matrix getVector()
    {
        double[] values = new double[dimensionality];
        for(int i = 0; i < dimensionality; i++)
        {
            values[i] = bits.get(i) ? 1 : 0;
        }
        return new Matrix(values,values.length);
    }

    /**
     * Returns a bit vector equal to this bit vector,
     * if k is not 0, a bit vector with all components
     * equal to zero otherwise.
     * 
     * @see de.lmu.ifi.dbs.data.FeatureVector#multiplicate(double)
     */
    public FeatureVector<Bit> multiplicate(double k)
    {
        if(k == 0)
        {
            return nullVector();
        }
        else
        {
            return this.copy();
        }
    }

    /**
     * Returns the inverse of the bit vector.
     * 
     * @see de.lmu.ifi.dbs.data.FeatureVector#negativeVector()
     */
    public FeatureVector<Bit> negativeVector()
    {
        BitSet newBits = (BitSet) bits.clone();
        newBits.flip(0,dimensionality);
        return new BitVector(newBits,dimensionality);
    }

    /**
     * Returns a bit vector of equal dimensionality but containing 0 only.
     * 
     * @see de.lmu.ifi.dbs.data.FeatureVector#nullVector()
     */
    public FeatureVector<Bit> nullVector()
    {
        return new BitVector(new BitSet(), dimensionality);
    }

    /**
     * Returns a bit vector corresponding to an XOR operation
     * on this and the specified bit vector.
     *     
     * @see FeatureVector#plus(FeatureVector)
     */
    public FeatureVector<Bit> plus(FeatureVector<Bit> fv)
    {
        BitVector bv = new BitVector(fv.getValues());
        bv.bits.xor(this.bits);
        return bv;
    }

    /**
     * 
     * 
     * @see de.lmu.ifi.dbs.data.MetricalObject#copy()
     */
    public FeatureVector<Bit> copy()
    {
        BitVector copy = new BitVector(bits,dimensionality);
        copy.setID(getID());
        return copy;
    }

    public boolean isSet(int index)
    {
        return bits.get(index);
    }
    
    public boolean areSet(int[] indices)
    {
        boolean set = true;
        for(int i = 0; i < indices.length && set; i++)
        {
            set &= bits.get(i);
        }
        return set;
    }
    
    public int[] setBits()
    {
        int[] setBits = new int[bits.size()];
        int index = 0;
        for(int i = bits.nextSetBit(0); i >= 0; i = bits.nextSetBit(i+1))
        {
            setBits[index++] = i;
        }
        return setBits;
    }
}
