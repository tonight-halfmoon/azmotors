package com.azmotors.store.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class RatioIndustryPartElement extends IndustryPartElement
{
    /** e.g., 1/20 **/
    private final StringProperty m_ratio;

    protected RatioIndustryPartElement(String name, String dateStored, final String kw, final String hp, final String ratio, final String rpm,
            final String volt, final String Hz, final String Ho, final String priceWholesale, final String priceRetail)
    {
        super(name, dateStored, kw, hp, rpm, volt, Hz, Ho, priceWholesale, priceRetail);
        m_ratio = new SimpleStringProperty(ratio);
        m_registeredProperties.add(m_ratio.get());
    }

    public StringProperty ratioProperty()
    {
        return m_ratio;
    }

    public String getRatio()
    {
        return m_ratio.get();
    }

    @Override
    protected String specificProperties()
    {
        final StringBuilder strBldr = new StringBuilder();
        strBldr.append(";\t Ratio=");
        strBldr.append(getRatio());
        strBldr.trimToSize();
        return strBldr.toString();
    }
}
