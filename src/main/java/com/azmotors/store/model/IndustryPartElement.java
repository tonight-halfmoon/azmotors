package com.azmotors.store.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import com.azmotors.store.foundation.DateUtil;

public abstract class IndustryPartElement extends StorableElement
{
    private final transient StringProperty m_KW;
    private final transient StringProperty m_horsePower;
    private final transient StringProperty m_RPM;
    private final transient StringProperty m_Volt;
    private final transient StringProperty m_Hz;
    private final transient StringProperty m_Ho;
    private final transient StringProperty m_priceWholesale;
    private final transient StringProperty m_priceRetail;

    protected IndustryPartElement()
    {
        this(Constants.EMPTY_FAKE, DateUtil.today(), Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY,
                Constants.EMPTY, Constants.EMPTY);
    }

    protected IndustryPartElement(String name, String dateStored, final String kw, final String hp, final String rpm, final String volt,
            final String Hz, final String Ho, final String priceWholesale, final String priceRetail)
    {
        super(name, dateStored);
        m_KW = new SimpleStringProperty(kw);
        m_RPM = new SimpleStringProperty(rpm);
        m_registeredProperties.add(m_KW.get());
        m_horsePower = new SimpleStringProperty(hp);
        m_Volt = new SimpleStringProperty(volt);
        m_Hz = new SimpleStringProperty(Hz);
        m_Ho = new SimpleStringProperty(Ho);
        m_priceWholesale = new SimpleStringProperty(priceWholesale);
        m_priceRetail = new SimpleStringProperty(priceRetail);

        m_registeredProperties.add(m_Volt.get());
        m_registeredProperties.add(m_horsePower.get());
        m_registeredProperties.add(m_RPM.get());

        m_registeredProperties.add(m_Hz.get());
        m_registeredProperties.add(m_Ho.get());
        m_registeredProperties.add(m_priceWholesale.get());
        m_registeredProperties.add(m_priceRetail.get());
    }

    @Override
    public String getPresentationName()
    {
        return toString();
    }

    @Override
    public String getDescription()
    {
        return getStandardName();
    }

    @Override
    public String toString()
    {
        final StringBuilder strBldr = new StringBuilder();
        strBldr.append(this.getClass().getSimpleName());
        strBldr.append(Constants.TOKEN_EQUAL);

        strBldr.append(getStandardName());
        strBldr.append(Constants.TOKEN_INDICATES_INDUSTRY_PARTS_NAME);
        strBldr.append(";\t\t\t KW=");
        strBldr.append(getKW());
        strBldr.append(";\t horsePower=");
        strBldr.append(getHorsePower());
        strBldr.append(specificProperties());
        strBldr.append(";\t RPM=");
        strBldr.append(getRPM());
        strBldr.append(";\t Volt=");
        strBldr.append(getVolt());
        strBldr.append(";\t Hz=");
        strBldr.append(getHz());
        strBldr.append(";\t Ho=");
        strBldr.append(getHo());
        strBldr.append(";\t priceWholesale=");
        strBldr.append(getPriceWholesale());
        strBldr.append(";\t priceRetail=");
        strBldr.append(getPriceRetail());
        strBldr.append(super.toString());
        strBldr.trimToSize();
        return strBldr.toString();
    }

    protected String specificProperties()
    {
        return Constants.EMPTY;
    }

    public StringProperty kwProperty()
    {
        return m_KW;
    }

    public String getKW()
    {
        return m_KW.get();
    }

    public void setKW(final String kw)
    {
        m_KW.set(kw);
    }

    public String getHorsePower()
    {
        return m_horsePower.get();
    }

    public StringProperty hpProperty()
    {
        return m_horsePower;
    }

    public void setHorsePower(final String hp)
    {
        m_horsePower.set(hp);
    }

    public String getRPM()
    {
        return m_RPM.get();
    }

    public StringProperty rpmProperty()
    {
        return m_RPM;
    }

    public void setRPM(final String rpm)
    {
        m_RPM.set(rpm);
    }

    public String getVolt()
    {
        return m_Volt.get();
    }

    public StringProperty voltProperty()
    {
        return m_Volt;
    }

    public void setVolt(final String volt)
    {
        m_Volt.set(volt);
    }

    public String getHz()
    {
        return m_Hz.get();
    }

    public String getHo()
    {
        return m_Ho.get();
    }

    public String getPriceWholesale()
    {
        return m_priceWholesale.get();
    }

    public String getPriceRetail()
    {
        return m_priceRetail.get();
    }

    public StringProperty HzProperty()
    {
        return m_Hz;
    }

    public StringProperty HoProperty()
    {
        return m_Ho;
    }

    public StringProperty priceWholesale()
    {
        return m_priceWholesale;
    }

    public StringProperty priceRetail()
    {
        return m_priceRetail;
    }

    public void setHz(final String Hz)
    {
        m_Hz.set(Hz);
    }

    public void setHo(final String Ho)
    {
        m_Ho.set(Ho);
    }

    public void setPriceWholesale(final String priceWholesale)
    {
        m_priceWholesale.set(priceWholesale);
    }

    public void setPriceRetail(final String priceRetail)
    {
        m_priceRetail.set(priceRetail);
    }

    public boolean isSold()
    {
        return getDateSold() == null || getDateSold().isEmpty() ? false : true;
    }

    public boolean isInStore()
    {
        return !isSold();
    }
}
