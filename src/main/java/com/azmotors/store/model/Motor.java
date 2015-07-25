package com.azmotors.store.model;

public final class Motor extends IndustryPartElement
{
    public Motor(final String name, final String dateStored, final String kw, final String hp, final String rpm, final String volt, final String Hz,
            final String Ho, final String priceWholesale, final String priceRetail)
    {
        super(name, dateStored, kw, hp, rpm, volt, Hz, Ho, priceWholesale, priceRetail);
    }
}