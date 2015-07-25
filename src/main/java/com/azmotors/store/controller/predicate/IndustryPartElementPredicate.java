package com.azmotors.store.controller.predicate;

import javafx.beans.value.ChangeListener;
import javafx.collections.transformation.FilteredList;

import com.azmotors.store.foundation.StringUtil;
import com.azmotors.store.model.Constants;
import com.azmotors.store.model.IndustryPartElement;

public final class IndustryPartElementPredicate
{
    private final FilteredList<IndustryPartElement> m_filteredList;

    public IndustryPartElementPredicate(final FilteredList<IndustryPartElement> transformationList)
    {
        assert null != transformationList : "Parameter 'transformationList' of method 'Predicate' must not be null";
        m_filteredList = transformationList;
    }

    public ChangeListener<? super String> predicateOn(final String rPMkeyString, final String horsePowerKeyString)
    {
        m_filteredList.setPredicate(motor -> {
            int compareRPMkeyResult = rPMkeyString.compareToIgnoreCase(motor.getRPM().toLowerCase());
            if (StringUtil.isEmpty(rPMkeyString))
            {
                compareRPMkeyResult = 0;
            }
            int compareHorsePowerKeyResult = horsePowerKeyString.compareToIgnoreCase(motor.getHorsePower().toLowerCase());
            if (StringUtil.isEmpty(horsePowerKeyString))
            {
                compareHorsePowerKeyResult = 0;
            }
            if (Constants.ZERO == compareRPMkeyResult && Constants.ZERO == compareHorsePowerKeyResult)
            {
                return true;
            }
            return false;
        });
        return null;
    }

    public ChangeListener<? super String> predicateOnAllProperties()
    {
        return (observable, currentVale, nextValue) -> {

            m_filteredList.setPredicate(motor -> {
                if (null == nextValue || nextValue.isEmpty() || nextValue.toLowerCase().equals(currentVale.toLowerCase()))
                {
                    return true;
                }

                final String lowerCaseFilter = nextValue.toLowerCase();

                for (final String next : motor.registeredProperties())
                {
                    if (Constants.MINUS_ONE != next.toLowerCase().indexOf(lowerCaseFilter))
                    {
                        return true;
                    }
                }
                return false;
            });
        };
    }
}