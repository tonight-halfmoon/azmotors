package com.azmotors.store.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import com.azmotors.store.foundation.DateUtil;
import com.azmotors.store.foundation.StringUtil;

public abstract class StorableElement extends NamedElement implements IStorable
{
    private final transient StringProperty m_dateStored;
    private final transient StringProperty m_dateSold;
    private final transient StringProperty m_daysInStore;

    protected StorableElement(final String name, final String dateStored)
    {
        super(name);
        if (StringUtil.isEmpty(dateStored))
        {
            m_dateStored = new SimpleStringProperty(DateUtil.today());
        }
        else
        {
            m_dateStored = new SimpleStringProperty(dateStored);
        }
        m_dateSold = new SimpleStringProperty(Constants.EMPTY);
        /**
         * we subtract one since we do not want to include the day of sell.
         */
        final long daysInStore = DateUtil.calculateDaysSince(dateStored);
        if (0 > daysInStore)
        {
            m_daysInStore = new SimpleStringProperty(String.valueOf(daysInStore - 1));
        }
        else
        {
            m_daysInStore = new SimpleStringProperty(String.valueOf(daysInStore));
        }

        m_registeredProperties.add(m_dateStored.get());
        m_registeredProperties.add(m_dateSold.get());
    }

    //TODO tentative to be removed
    @Override
    public void persist()
    {
        //TODO FileUtil.amend(this.getPresentationName());
    }

    public String getDateStored()
    {
        return m_dateStored.get();
    }

    public String getDateSold()
    {
        return m_dateSold.get();
    }

    public String getDaysInStore()
    {
        return m_daysInStore.get();
    }

    public StringProperty dateStoredProperty()
    {
        return m_dateStored;
    }

    public StringProperty dateSoldProperty()
    {
        return m_dateSold;
    }

    public StringProperty daysInStoreProperty()
    {
        return m_daysInStore;
    }

    public void setDateStored(final String dateStored)
    {
        m_dateStored.set(dateStored);
    }

    public void setDateSold(final String dateSold)
    {
        m_dateSold.set(dateSold);
    }

    public void setDaysInStore(final String daysInStore)
    {
        m_daysInStore.set(daysInStore);
    }

    @Override
    public String toString()
    {
        return ";\t dateStored=" + getDateStored() + ";\t dateSold=" + getDateSold() + ";\t daysInStore=" + getDaysInStore() + ".";
    }
}
