package com.azmotors.store.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.azmotors.store.foundation.StringUtil;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class NamedElement extends Element
{
    private final StringProperty m_name;
    protected final transient Set<String> m_registeredProperties;

    protected NamedElement(final String name)
    {
        super();
        assert null != name && !name.isEmpty() : "Parameter 'name' of method 'Element' must not be empty";
        m_name = new SimpleStringProperty(name);
        m_registeredProperties = new HashSet<>(12);
        m_registeredProperties.add(m_name.get());
    }

    public String getStandardName()
    {
        return getName();
    }

    public String getName()
    {
        return m_name.get();
    }

    public StringProperty nameProperty()
    {
        return m_name;
    }

    public void setName(final String name)
    {
        m_name.set(name);
    }

    public abstract String getPresentationName();

    public abstract String getDescription();

    public final Set<String> registeredProperties()
    {
        return Collections.unmodifiableSet(m_registeredProperties);
    }
    
    public final boolean isEmpty()
    {
        return StringUtil.isEmpty(getStandardName()) || Constants.EMPTY_FAKE.equals(getStandardName());
    }
}
