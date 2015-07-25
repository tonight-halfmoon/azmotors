package com.azmotors.store.model;

public final class ElementNull extends Element
{
    public final static ElementNull SINGLETON = new ElementNull();

    private ElementNull()
    {
        super();
    }

    @Override
    public String toString()
    {
        return Constants.TOKEN_NULL;
    }
}
