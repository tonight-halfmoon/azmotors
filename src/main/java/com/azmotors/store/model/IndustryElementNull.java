package com.azmotors.store.model;

public final class IndustryElementNull extends IndustryPartElement
{
    public static final IndustryElementNull SINGLETON = new IndustryElementNull();

    private IndustryElementNull()
    {
        super();
    }

    @Override
    public String toString()
    {
        return Constants.TOKEN_NULL;
    }
}
